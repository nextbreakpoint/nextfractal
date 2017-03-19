package com.nextbreakpoint.nextfractal.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.FileManifest;
import com.nextbreakpoint.nextfractal.core.ImageComposer;
import com.nextbreakpoint.nextfractal.core.Plugins;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import sun.misc.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODOs
 * - Accept requests only for valid tokens
 * - Limit rate for a single user or token
 * - Add support for cancellation
 */
public class Application extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private static final ThreadFactory threadFactory = new DefaultThreadFactory(Application.class.getName(), true, Thread.MIN_PRIORITY);

    private static final long SECOND_TIME_UNIT = 1000;
    private static final String TMP_PATH = "/tmp/nextfractal";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_IMAGE_PNG = "image/png";

    public static void main(String[] args) {
        new Application().start();
    }

    private WorkerExecutor executor;

    public void start() {
        final JsonObject config = Vertx.currentContext().config();

        final File tmpDir = new File(TMP_PATH);

        tmpDir.mkdirs();

        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.post("/tiles").handler(this::handleCreateTiles);
        router.get("/tiles/:id/state").handler(this::handleGetTileState);
        router.get("/tiles/:id/image").handler(this::handleGetTileImage);

        final int poolSize = Runtime.getRuntime().availableProcessors() * 4;
        final long maxExecuteTime = 60 * SECOND_TIME_UNIT;
        final String workerName = "worker";
        executor = vertx.createSharedWorkerExecutor(workerName, poolSize, maxExecuteTime);

        final HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(router::accept).listen(8080);
    }

    private void handleCreateTiles(RoutingContext routingContext) {
        try {
            final int size = Integer.parseInt(routingContext.request().getFormAttribute("size"));
            final int rows = Integer.parseInt(routingContext.request().getFormAttribute("rows"));
            final int cols = Integer.parseInt(routingContext.request().getFormAttribute("cols"));
            final String manifest = routingContext.request().getFormAttribute("manifest");
            final String metadata = routingContext.request().getFormAttribute("metadata");
            final String script = routingContext.request().getFormAttribute("script");

            final Bundle bundle = decodeData(manifest, script, metadata);

            validateRequest(bundle.getSession(), size, cols, rows);

            final UUID taskId = UUID.randomUUID();

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    final TileRequest request = TileRequest.builder()
                            .withRows(rows)
                            .withCols(cols)
                            .withRow(row)
                            .withCol(col)
                            .withSize(size)
                            .withTaskId(taskId)
                            .withSession(bundle.getSession())
                            .build();

                    executor.<TileRequest>executeBlocking(future -> {
                        Try.of(() -> generateImage(createJob(request)))
                                .onSuccess(v -> future.complete(request))
                                .onFailure(e -> future.fail(e))
                                .execute();
                    }, result -> {
                        if (result.succeeded()) {
                            onTileSucceeded(result);
                        } else {
                            onTileFailed(result);
                        }
                    });
                }
            }

            routingContext.response()
                    .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)
                    .end(createTask(taskId.toString()).encode());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot post tiles", e);
        }
    }

    private void onTileSucceeded(AsyncResult<TileRequest> result) {
        TileRequest request = (TileRequest) result.result();
        logger.log(Level.INFO, "Completed tile " + request.getTaskId() + "-" + request.getRow() + "-" + request.getCol());
    }

    private void onTileFailed(AsyncResult<TileRequest> result) {
        logger.log(Level.WARNING, "Failed tile " + result.cause().getMessage());
    }

    private JsonObject createTask(String taskId) {
        JsonObject json = new JsonObject();
        json.put("task_id", taskId);
        return json;
    }

    private void handleGetTileState(RoutingContext routingContext) {
        try {
            final String taskId = routingContext.request().getParam("id");
            final int row = Integer.parseInt(routingContext.request().getParam("row"));
            final int col = Integer.parseInt(routingContext.request().getParam("col"));

            logger.log(Level.INFO, "Get tile state " + taskId + "-" + row + "-" + col);

            // validateRequest(rows, cols, row, col);

            File file = new File(TMP_PATH + "/" + taskId + "-" + row + "-" + col + ".png");

            if (file.exists()) {
                routingContext.response()
                        .setStatusCode(200)
                        .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)
                        .end(createState(taskId, row, col).encode());
            } else {
                routingContext.response()
                        .setStatusCode(408)
                        .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)
                        .end(createState(taskId, row, col).encode());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING,"Cannot get tile", e);

            routingContext.response().setStatusCode(500).end();
        }
    }

    private JsonObject createState(String taskId, int row, int col) {
        JsonObject json = new JsonObject();
        json.put("task_id", taskId);
        json.put("row", row);
        json.put("col", col);
        return json;
    }

    private void handleGetTileImage(RoutingContext routingContext) {
        try {
            final String taskId = routingContext.request().getParam("id");
            final int row = Integer.parseInt(routingContext.request().getParam("row"));
            final int col = Integer.parseInt(routingContext.request().getParam("col"));

            logger.log(Level.INFO, "Get tile image " + taskId + "-" + row + "-" + col);

//          validateRequest(rows, cols, row, col);

            File file = new File(TMP_PATH + "/" + taskId + "-" + row + "-" + col + ".png");

            if (file.exists()) {
                routingContext.response()
                        .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_IMAGE_PNG)
                        .end(Buffer.buffer(loadBytes(file)));
            } else {
                routingContext.response().setStatusCode(404).end();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING,"Cannot get tile data", e);

            routingContext.response().setStatusCode(500).end();
        }
    }

    private byte[] loadBytes(File file) throws IOException {
        return IOUtils.readFully(new FileInputStream(file), -1, true);
    }

    private void validateRequest(Session session, int size, int cols, int rows) {
        if (rows < 0 || rows > 16) {
            throw new RuntimeException("Invalid rows number");
        }
        if (cols < 0 || cols > 16) {
            throw new RuntimeException("Invalid cols number");
        }
        if (rows == 1 && cols == 1 && (size < 32 || size > 1024)) {
            throw new RuntimeException("Invalid image size");
        }
        if ((rows > 1 || cols > 1) && (size < 32 || size > 256)) {
            throw new RuntimeException("Invalid image size");
        }
        if (session == null) {
            throw new RuntimeException("Invalid data");
        }
    }

    private void validateRequest(int rows, int cols, int row, int col) {
        if (row < 0 || row > rows - 1) {
            throw new RuntimeException("Invalid row index");
        }
        if (col < 0 || col > cols - 1) {
            throw new RuntimeException("Invalid col index");
        }
    }

    private RemoteJob createJob(TileRequest request) {
        final int tileSize = request.getSize();
        final int rows = request.getRows();
        final int cols = request.getCols();
        final int row = request.getRow();
        final int col = request.getCol();
        final RemoteJob job = RemoteJob.builder()
                .withQuality(1)
                .withImageWidth(tileSize * cols)
                .withImageHeight(tileSize * rows)
                .withTileWidth(tileSize)
                .withTileHeight(tileSize)
                .withTileOffsetX(tileSize * col)
                .withTileOffsetY(tileSize * row)
                .withBorderWidth(0)
                .withBorderHeight(0)
                .withRow(row)
                .withCol(col)
                .withRows(rows)
                .withCols(cols)
                .withSession(request.getSession())
                .withTaskId(request.getTaskId())
                .build();
        return job;
    }

    private Bundle decodeData(String encodedManifest, String encodedScript, String encodedMetadata) throws Exception {
        final FileManagerEntry manifest = new FileManagerEntry("manifest", Base64.getDecoder().decode(encodedManifest));
        final FileManagerEntry metadata = new FileManagerEntry("metadata", Base64.getDecoder().decode(encodedMetadata));
        final FileManagerEntry script = new FileManagerEntry("script", Base64.getDecoder().decode(encodedScript));

        final List<FileManagerEntry> entries = Arrays.asList(manifest, script, metadata);

        final ObjectMapper mapper = new ObjectMapper();

        final FileManifest decodedManifest = mapper.readValue(manifest.getData(), FileManifest.class);

        return Plugins.tryFindFactory(decodedManifest.getPluginId())
                .flatMap(factory -> factory.createFileManager().loadEntries(entries)).orThrow();
    }

    private void writeImageAsPNG(RemoteJob job, IntBuffer pixels) throws IOException {
        final int tileWidth = job.getTile().getTileSize().getWidth();
        final int tileHeight = job.getTile().getTileSize().getHeight();

        final BufferedImage image =  new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        final int[] buffer = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        System.arraycopy(pixels.array(), 0, buffer, 0, tileWidth * tileHeight);

        ImageIO.write(image, "PNG", new FileOutputStream(new File(createFileName(job))));
    }

    private String createFileName(RemoteJob job) {
        return TMP_PATH + "/" + job.getTaskId() + "-" + job.getRow() + "-" + job.getCol() + ".png";
    }

    private IntBuffer generateImage(RemoteJob job) throws Exception {
        final ImageComposer composer = Plugins.tryFindFactory(job.getSession().getPluginId())
                .map(factory -> factory.createImageComposer(threadFactory, job.getTile(), true)).orThrow();

        final IntBuffer pixels = composer.renderImage(job.getSession().getScript(), job.getSession().getMetadata());

        writeImageAsPNG(job, pixels);

        return pixels;
    }
}
