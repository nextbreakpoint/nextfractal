/*
 * NextFractal 2.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.verticle;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.TileGenerator;
import com.nextbreakpoint.nextfractal.core.TileRequest;
import com.nextbreakpoint.nextfractal.core.TileUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.TemplateEngine;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FractalsVerticle extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(FractalsVerticle.class.getName());

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TYPE_IMAGE_PNG = "image/png";
    private static final String TYPE_APPLICATION_JSON = "application/json";
    private static final int TILE_SIZE = 256;

    private WorkerExecutor executor;

    private Bundle defaultBundle;
    private UUID defaultUuid;

    public static void main(String[] args) {
        new FractalsVerticle().start();
    }

    @Override
    public void start() {
        final JsonObject config = Vertx.currentContext().config();

        final String defaultManifest = config.getString("default_manifest");
        final String defaultMetadata = config.getString("default_metadata");
        final String defaultScript = config.getString("default_script");
        final String accessKey = config.getString("s3_access_key");
        final String secretKey = config.getString("s3_secret_key");
        final String bucketName = config.getString("s3_bucket_name");
        final String clientId = config.getString("github_client_id");
        final String clientSecret = config.getString("github_client_secret");

        defaultUuid = new UUID(0L, 0L);

        defaultBundle = Try.of(() -> TileUtils.parseData(defaultManifest, defaultMetadata, defaultScript))
                .onFailure(e -> logger.warning("Can't create default bundle: " + e.getMessage()))
                .orElse(null);

        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        router.get("/api/fractals").handler(routingContext -> handleListBundles(routingContext));
        router.post("/api/fractals").handler(routingContext -> handleCreateBundle(routingContext, bucketName, credentials));
        router.delete("/api/fractals/:uuid").handler(routingContext -> handleRemoveBundle(routingContext, bucketName, credentials));
        router.get("/api/fractals/:uuid/:zoom/:x/:y").handler(routingContext -> handleGetTile(routingContext, bucketName, credentials));

        router.route("/static/*").handler(StaticHandler.create());

        final TemplateEngine engine = ThymeleafTemplateEngine.create();

        final TemplateHandler templateHandler = TemplateHandler.create(engine);

        router.get("/fractals/:uuid").handler(this::handleViewPage);

        router.get("/pages/*").handler(templateHandler);

        router.get("/admin/*").handler(templateHandler);

        final OAuth2ClientOptions auth2ClientOptions = new OAuth2ClientOptions()
                .setClientID(clientId)
                .setClientSecret(clientSecret)
                .setSite("https://github.com/login")
                .setTokenPath("/oauth/access_token")
                .setAuthorizationPath("/oauth/authorize");

        final OAuth2Auth authProvider = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE, auth2ClientOptions);
        final OAuth2AuthHandler oauth2 = OAuth2AuthHandler.create(authProvider, "http://localhost:8080");
        oauth2.setupCallback(router.get("/callback"));
        router.route("/admin/*").handler(oauth2);

        final int poolSize = Runtime.getRuntime().availableProcessors() * 4;
        final long maxExecuteTime = config.getInteger("max_execution_time_in_millis");
        executor = vertx.createSharedWorkerExecutor("worker", poolSize, maxExecuteTime);

        final HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(8080);
    }

    private Try<UUID, Exception> handleViewPage(RoutingContext routingContext) {
        return Try.of(() -> forwardHandleViewPage(routingContext))
                .onFailure(e -> emitErrorResponse(routingContext, "Can't generate page"))
                .execute();
    }

    private UUID forwardHandleViewPage(RoutingContext routingContext) {
        final UUID uuid = UUID.fromString(routingContext.request().getParam("uuid"));
        routingContext.put("uuid", uuid.toString());
        routingContext.reroute("/pages/fractal");
        return uuid;
    }

    @Override
    public void stop() throws Exception {
        if (executor != null) {
            executor.close();
        }
    }

    private void handleGetTile(RoutingContext routingContext, String bucketName, BasicAWSCredentials credentials) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();

            final UUID uuid = UUID.fromString(serverRequest.getParam("uuid"));
            final int zoom = Integer.parseInt(serverRequest.getParam("zoom"));
            final int x = Integer.parseInt(serverRequest.getParam("x"));
            final int y = Integer.parseInt(serverRequest.getParam("y"));

            final int side = 1 << zoom;

            if (uuid.equals(defaultUuid)) {
                final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, defaultBundle);

                executor.<byte[]>executeBlocking(future -> handleGetTile(request, future),false, result -> handleGetTileResult(routingContext, result));
            } else {
                final JsonObject json = new JsonObject(fetchBundle(bucketName, credentials, uuid.toString()));

                final String manifest = json.getString("manifest");
                final String metadata = json.getString("metadata");
                final String script = json.getString("script");

                final Bundle bundle = TileUtils.parseData(manifest, metadata, script);

                if (bundle != null) {
                    final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, bundle);

                    executor.<byte[]>executeBlocking(future -> handleGetTile(request, future),false, result -> handleGetTileResult(routingContext, result));
                } else {
                    emitNotFoundResponse(routingContext);
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create tile", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private void handleListBundles(RoutingContext routingContext) {
        try {
            emitListBundlesResponse(routingContext);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to list bundles", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private void handleCreateBundle(RoutingContext routingContext, String bucketName, BasicAWSCredentials credentials) {
        try {
            final JsonObject body = routingContext.getBodyAsJson();

            final String manifest = body.getString("manifest");
            final String metadata = body.getString("metadata");
            final String script = body.getString("script");

            final UUID uuid = UUID.randomUUID();

            TileUtils.parseData(manifest, metadata, script);

            storeBundle(routingContext, bucketName, credentials, uuid);

            emitCreateBundleResponse(routingContext, uuid);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create bundle", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private void handleRemoveBundle(RoutingContext routingContext, String bucketName, BasicAWSCredentials credentials) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();

            final UUID uuid = UUID.fromString(serverRequest.getParam("uuid"));

            deleteBundle(bucketName, credentials, uuid);

            emitRemoveBundleResponse(routingContext);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create bundle", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private String fetchBundle(String bucketName, BasicAWSCredentials credentials, String uuid) {
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final AmazonS3 s3client = AmazonS3Client.builder().withCredentials(credentialsProvider).build();

        final S3Object object = s3client.getObject(new GetObjectRequest(bucketName, uuid));

        return new String(getBytes(object.getObjectContent()));
    }

    private void storeBundle(RoutingContext routingContext, String bucketName, BasicAWSCredentials credentials, UUID uuid) {
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final AmazonS3 s3client = AmazonS3Client.builder().withCredentials(credentialsProvider).build();

        final byte[] bytes = routingContext.getBody().getBytes();

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/json");
        metadata.setContentLength(bytes.length);

        final ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        s3client.putObject(new PutObjectRequest(bucketName, uuid.toString(), input, metadata));
    }

    private void deleteBundle(String bucketName, BasicAWSCredentials credentials, UUID uuid) {
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final AmazonS3 s3client = AmazonS3Client.builder().withCredentials(credentialsProvider).build();

        s3client.deleteObject(new DeleteObjectRequest(bucketName, uuid.toString()));
    }

    private void handleGetTile(TileRequest request, Future<byte[]> future) {
        Try.of(() -> TileGenerator.generateImage(request)).onFailure(future::fail).ifPresent(future::complete);
    }

    private void handleGetTileResult(RoutingContext routingContext, AsyncResult<byte[]> result) {
        if (result.succeeded()) {
            emitGetTileResponse(routingContext, result.result());
        } else {
            emitErrorResponse(routingContext, "Failed to generate image");
        }
    }

    private void emitListBundlesResponse(RoutingContext routingContext) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(200)
                .end();
    }

    private void emitCreateBundleResponse(RoutingContext routingContext, UUID uuid) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(201)
                .end(createBundleResponseObject(uuid).encode());
    }

    private void emitRemoveBundleResponse(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(200)
                .end();
    }

    private void emitGetTileResponse(RoutingContext routingContext, byte[] pngImage) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_IMAGE_PNG)
                .setStatusCode(200)
                .end(Buffer.buffer(pngImage));
    }

    private void emitErrorResponse(RoutingContext routingContext, String error) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(500)
                .end(createErrorResponseObject(error).encode());
    }

    private void emitNotFoundResponse(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(404)
                .end();
    }

    private byte[] getBytes(InputStream stream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (BufferedInputStream is = new BufferedInputStream(stream)) {
            byte[] buffer = new byte[4096];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                baos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            logger.warning("Can't read page: " + e.getMessage());
        }
        return baos.toByteArray();
    }

    private JsonObject createBundleResponseObject(UUID uuid) {
        JsonObject json = new JsonObject();
        json.put("uuid", uuid.toString());
        return json;
    }

    private JsonObject createErrorResponseObject(String error) {
        JsonObject json = new JsonObject();
        json.put("error", error);
        return json;
    }
}
