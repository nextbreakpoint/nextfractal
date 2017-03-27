/*
 * NextFractal 2.0.0
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
import com.nextbreakpoint.nextfractal.core.TileGenerator;
import com.nextbreakpoint.nextfractal.core.TileRequest;
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

import java.util.logging.Level;
import java.util.logging.Logger;

public class TileVerticle extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(TileVerticle.class.getName());

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TYPE_IMAGE_PNG = "image/png";
    private static final String TYPE_APPLICATION_JSON = "application/json";

    public static void main(String[] args) {
        new TileVerticle().start();
    }

    private WorkerExecutor executor;

    @Override
    public void start() {
        final JsonObject config = Vertx.currentContext().config();

        final Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/tile").handler(this::handleCreateTile);

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

    private void handleCreateTile(RoutingContext routingContext) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();
            final int size = Integer.parseInt(serverRequest.getParam("size"));
            final int rows = Integer.parseInt(serverRequest.getParam("rows"));
            final int cols = Integer.parseInt(serverRequest.getParam("cols"));
            final int row = Integer.parseInt(serverRequest.getParam("row"));
            final int col = Integer.parseInt(serverRequest.getParam("col"));
            final String manifest = serverRequest.getParam("manifest");
            final String metadata = serverRequest.getParam("metadata");
            final String script = serverRequest.getParam("script");

            final TileRequest request = TileGenerator.createTileRequest(size, rows, cols, row, col, manifest, metadata, script);

            executor.<byte[]>executeBlocking(future -> handleTileRequest(request, future), false, result -> handleResult(routingContext, result));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to create tile", e);

            handleError(routingContext, e.getMessage());
        }
    }

    private void handleTileRequest(TileRequest request, Future<byte[]> future) {
        Try.of(() -> TileGenerator.generateImage(request)).onFailure(future::fail).ifPresent(future::complete);
    }

    private void handleResult(RoutingContext routingContext, AsyncResult<byte[]> result) {
        if (result.succeeded()) {
            handleImage(routingContext, result.result());
        } else {
            handleError(routingContext, "Failed to generate image");
        }
    }

    private void handleImage(RoutingContext routingContext, byte[] pngImage) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_IMAGE_PNG)
                .setStatusCode(200)
                .end(Buffer.buffer(pngImage));
    }

    private void handleError(RoutingContext routingContext, String error) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(500)
                .end(createError(error).encode());
    }

    private JsonObject createError(String error) {
        JsonObject json = new JsonObject();
        json.put("error", error);
        return json;
    }
}
