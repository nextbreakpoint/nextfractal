package com.nextbreakpoint.nextfractal.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

public class TileGenerator {
    private static final ThreadFactory threadFactory = new DefaultThreadFactory(TileGenerator.class.getName(), true, Thread.MIN_PRIORITY);

    private TileGenerator() {}

    public static TileRequest createTileRequest(int size, int rows, int cols, int row, int col, String manifest, String metadata, String script) throws Exception {
        validateParameters(size, cols, rows, row, col);

        final Bundle bundle = decodeData(manifest, script, metadata);

        return TileRequest.builder()
                .withRows(rows)
                .withCols(cols)
                .withRow(row)
                .withCol(col)
                .withSize(size)
                .withTaskId(UUID.randomUUID())
                .withSession(bundle.getSession())
                .build();
    }

    private static void validateParameters(int size, int rows, int cols, int row, int col) {
        if (rows < 0 || rows > 16) {
            throw new RuntimeException("Invalid rows number");
        }
        if (cols < 0 || cols > 16) {
            throw new RuntimeException("Invalid cols number");
        }
        if (rows == 1 && cols == 1 && (size < 64 || size > 512)) {
            throw new RuntimeException("Invalid image size");
        }
        if ((rows > 1 || cols > 1) && (size < 64 || size > 512)) {
            throw new RuntimeException("Invalid image size");
        }
        if (row < 0 || row > rows - 1) {
            throw new RuntimeException("Invalid row index");
        }
        if (col < 0 || col > cols - 1) {
            throw new RuntimeException("Invalid col index");
        }
    }

    private static TileContext createTileContext(TileRequest request) {
        final int tileSize = request.getSize();
        final int rows = request.getRows();
        final int cols = request.getCols();
        final int row = request.getRow();
        final int col = request.getCol();
        final TileContext context = TileContext.builder()
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
        return context;
    }

    private static Bundle decodeData(String encodedManifest, String encodedScript, String encodedMetadata) throws Exception {
        final FileManagerEntry manifest = new FileManagerEntry("manifest", Base64.getDecoder().decode(encodedManifest));
        final FileManagerEntry metadata = new FileManagerEntry("metadata", Base64.getDecoder().decode(encodedMetadata));
        final FileManagerEntry script = new FileManagerEntry("script", Base64.getDecoder().decode(encodedScript));

        final List<FileManagerEntry> entries = Arrays.asList(manifest, script, metadata);

        final ObjectMapper mapper = new ObjectMapper();

        final FileManifest decodedManifest = mapper.readValue(manifest.getData(), FileManifest.class);

        return Plugins.tryFindFactory(decodedManifest.getPluginId())
                .flatMap(factory -> factory.createFileManager().loadEntries(entries)).orThrow();
    }

    private static void writePNGImage(ByteArrayOutputStream baos, IntBuffer pixels, RendererSize tileSize) throws IOException {
        final BufferedImage image =  new BufferedImage(tileSize.getWidth(), tileSize.getHeight(), BufferedImage.TYPE_INT_ARGB);

        final int[] buffer = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

        System.arraycopy(pixels.array(), 0, buffer, 0, tileSize.getWidth() * tileSize.getHeight());

        ImageIO.write(image, "PNG", baos);
    }

    public static byte[] generateImage(TileRequest request) throws Exception {
        final TileContext context = createTileContext(request);

        final ImageComposer composer = Plugins.tryFindFactory(context.getSession().getPluginId())
                .map(factory -> factory.createImageComposer(threadFactory, context.getTile(), true)).orThrow();

        final IntBuffer pixels = composer.renderImage(context.getSession().getScript(), context.getSession().getMetadata());

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        writePNGImage(baos, pixels, context.getTile().getTileSize());

        return baos.toByteArray();
    }
}
