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
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FractalsVerticle extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(FractalsVerticle.class.getName());

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TYPE_IMAGE_PNG = "image/png";
    private static final String TYPE_APPLICATION_JSON = "application/json";
    private static final int TILE_SIZE = 256;

    private Map<UUID, Bundle> bundles = new ConcurrentHashMap<>();

    private WorkerExecutor executor;

    @Override
    public void start() {
        final JsonObject config = Vertx.currentContext().config();

        final String defaultManifest = config.getString("default_manifest");
        final String defaultMetadata = config.getString("default_metadata");
        final String defaultScript = config.getString("default_script");

        final UUID uuid = new UUID(0L, 0L);

        Try.of(() -> bundles.put(uuid, TileUtils.parseData(defaultManifest, defaultMetadata, defaultScript)))
                .ifFailure(e -> logger.warning("Can't create default bundle: " + e.getMessage()));

        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/fractals/:uuid/:zoom/:x/:y.png").handler(this::handleGetFractalTile);
        router.post("/fractals").handler(this::handleCreateFractalBundle);
        router.delete("/fractals").handler(this::handleRemoveFractalBundles);

        final int poolSize = Runtime.getRuntime().availableProcessors() * 4;
        final long maxExecuteTime = config.getInteger("max_execution_time_in_millis");
        executor = vertx.createSharedWorkerExecutor("worker", poolSize, maxExecuteTime);

        final HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept).listen(8080);
    }

    @Override
    public void stop() throws Exception {
        if (executor != null) {
            executor.close();
        }
    }

    private void handleGetFractalTile(RoutingContext routingContext) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();

            final UUID uuid = UUID.fromString(serverRequest.getParam("uuid"));
            final int zoom = Integer.parseInt(serverRequest.getParam("zoom"));
            final int x = Integer.parseInt(serverRequest.getParam("x"));
            final int y = Integer.parseInt(serverRequest.getParam("y"));

            final int side = 1 << zoom;

            final Bundle bundle = bundles.get(uuid);

            if (bundle != null) {
                final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, bundle);

                executor.<byte[]>executeBlocking(future -> handleGetTile(request, future),false, result -> handleGetTileResult(routingContext, result));
            } else {
                emitNotFoundResponse(routingContext);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create tile", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private void handleCreateFractalBundle(RoutingContext routingContext) {
        try {
            final JsonObject body = routingContext.getBodyAsJson();

            final String manifest = body.getString("manifest");
            final String metadata = body.getString("metadata");
            final String script = body.getString("script");

            final UUID uuid = UUID.randomUUID();

            if (bundles.size() < 10) {
                bundles.put(uuid, TileUtils.parseData(manifest, metadata, script));

                emitCreateBundleResponse(routingContext, uuid);
            } else {
                emitLimitExeededResponse(routingContext);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create bundle", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
    }

    private void handleRemoveFractalBundles(RoutingContext routingContext) {
        try {
            bundles.clear();

            emitRemoveBundlesResponse(routingContext);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create bundle", e);

            emitErrorResponse(routingContext, e.getMessage());
        }
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

    private void emitCreateBundleResponse(RoutingContext routingContext, UUID uuid) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(201)
                .end(createBundleResponseObject(uuid).encode());
    }

    private void emitRemoveBundlesResponse(RoutingContext routingContext) {
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

    private void emitLimitExeededResponse(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(500)//TODO replace code
                .end();
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
