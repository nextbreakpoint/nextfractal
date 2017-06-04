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
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.auth.jwt.impl.JWTUser;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CSRFHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.handler.TimeoutHandler;
import io.vertx.ext.web.templ.PebbleTemplateEngine;
import io.vertx.ext.web.templ.TemplateEngine;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FractalsVerticle extends AbstractVerticle {
    private static final Logger logger = Logger.getLogger(FractalsVerticle.class.getName());

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String TYPE_IMAGE_PNG = "image/png";
    private static final String TYPE_APPLICATION_JSON = "application/json";
    private static final int TILE_SIZE = 256;

    private WorkerExecutor executor;

    private Set<String> adminUsers;
    private Bundle defaultBundle;
    private UUID defaultUuid;

    public static void main(String[] args) {
        try {
            new FractalsVerticle().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Future<Void> startFuture) {
        final JsonObject config = Vertx.currentContext().config();

        final String defaultManifest = config.getString("default_manifest");
        final String defaultMetadata = config.getString("default_metadata");
        final String defaultScript = config.getString("default_script");

        adminUsers = config.getJsonArray("admin_users").stream().map(o -> String.valueOf(o)).collect(Collectors.toSet());

        defaultUuid = new UUID(0L, 0L);

        defaultBundle = Try.of(() -> TileUtils.parseData(defaultManifest, defaultMetadata, defaultScript))
                .onFailure(e -> logger.warning("Can't create default bundle: " + e.getMessage()))
                .orElse(null);

        final int poolSize = Runtime.getRuntime().availableProcessors() * 4;
        final long maxExecuteTime = config.getInteger("max_execution_time_in_millis", 2000) * 1000000L;
        executor = vertx.createSharedWorkerExecutor("worker", poolSize, maxExecuteTime);

        Vertx.currentContext().executeBlocking(future -> {
            Try.of(() -> initServer(config))
                    .onFailure(e -> future.fail(e))
                    .ifSuccess(s -> future.complete());
        }, result -> {
            if (result.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.failed();
            }
        });
    }

    private HttpServer initServer(JsonObject config) {
        final Router router = Router.router(vertx);
        router.route().handler(LoggerHandler.create());
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(TimeoutHandler.create(30000));

        final String accessKey = config.getString("s3_access_key");
        final String secretKey = config.getString("s3_secret_key");
        final String bucketName = config.getString("s3_bucket_name");
        final String clientId = config.getString("github_client_id");
        final String clientSecret = config.getString("github_client_secret");
        final String callbackUrl = config.getString("github_callback_url");
        final String csrfSecret = config.getString("csrf_secret");
        final String jksStorePath = config.getString("jks_store_path");
        final String jksStoreSecret = config.getString("jks_store_secret");

        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final AmazonS3 s3client = AmazonS3Client.builder().withCredentials(credentialsProvider).withRegion("eu-west-1").build();

        final OAuth2ClientOptions oauth2Options = new OAuth2ClientOptions()
                .setClientID(clientId)
                .setClientSecret(clientSecret)
                .setSite("https://github.com/login")
                .setTokenPath("/oauth/access_token")
                .setAuthorizationPath("/oauth/authorize");

        final OAuth2Auth oauth2Provider = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE, oauth2Options);
        final OAuth2AuthHandler oauth2 = OAuth2AuthHandler.create(oauth2Provider, callbackUrl);

        final JWTAuth jwtProvider = JWTAuth.create(vertx, config);

        final PebbleTemplateEngine engine = PebbleTemplateEngine.create(vertx);
        engine.setExtension(".html");

        final CSRFHandler csrfHandler = CSRFHandler.create(csrfSecret);

        router.route("/static/*").handler(StaticHandler.create());
        router.route("/static/*").failureHandler(this::emitNotFoundResponse);

        router.get("/callback").handler(routingContext -> handleOAuthCallback(clientId, clientSecret, jwtProvider, routingContext));

        oauth2.addAuthority("user:email");
        oauth2.setupCallback(router.get("/callback"));
        router.route("/login/*").handler(oauth2);

        router.route("/admin/*").handler(csrfHandler);
        router.route("/admin/*").handler(routingContext -> handleCookie(jwtProvider, routingContext, rc -> rc.reroute("/login" + routingContext.request().path())));
        router.route("/admin/*").handler(TemplateHandler.create(engine, "webroot", "text/html")).failureHandler(this::emitNotFoundResponse);

        router.get("/fractals/:uuid").handler(new FractalTemplateHandler(engine, "webroot", "text/html")).failureHandler(this::emitNotFoundResponse);

        router.get("/api/fractals/:uuid/:zoom/:x/:y").handler(routingContext -> handleGetTileAsync(routingContext, bucketName, s3client));

        router.get("/api/fractals").handler(csrfHandler);
        router.get("/api/fractals").handler(routingContext -> handleCookie(jwtProvider, routingContext, rc -> rc.fail(403)));
        router.get("/api/fractals").handler(routingContext -> handleListBundlesAsync(routingContext, bucketName, s3client));

        router.post("/api/fractals").consumes(TYPE_APPLICATION_JSON).handler(csrfHandler);
        router.post("/api/fractals").consumes(TYPE_APPLICATION_JSON).handler(routingContext -> handleCookie(jwtProvider, routingContext, rc -> rc.fail(403)));
        router.post("/api/fractals").consumes(TYPE_APPLICATION_JSON).handler(routingContext -> handleCreateBundleAsync(routingContext, bucketName, s3client));

        router.delete("/api/fractals").handler(csrfHandler);
        router.delete("/api/fractals/:uuid").handler(routingContext -> handleCookie(jwtProvider, routingContext, rc -> rc.fail(403)));
        router.delete("/api/fractals/:uuid").handler(routingContext -> handleRemoveBundleAsync(routingContext, bucketName, s3client));

        router.route("/api/*").failureHandler(routingContext -> emitFailureResponse(routingContext, "Error"));

        router.route().failureHandler(ErrorHandler.create(false));

        final HttpServerOptions options = new HttpServerOptions()
                .setSsl(true)
                .setKeyStoreOptions(new JksOptions().setPath(jksStorePath).setPassword(jksStoreSecret));

        final HttpServer httpServer = vertx.createHttpServer(options);
        httpServer.requestHandler(router::accept).listen(4443);

        return httpServer;
    }

    private void handleOAuthCallback(String clientId, String clientSecret, JWTAuth jwtProvider, RoutingContext routingContext) {
        final String code = routingContext.request().getParam("code");
        final String state = routingContext.request().getParam("state");
        if (code != null  && state != null && state.startsWith("/login/")) {
            handleOAuthCode(routingContext, jwtProvider, clientId, clientSecret, code, state.substring(7));
        } else {
            routingContext.fail(400);
        }
    }

    private void handleCookie(JWTAuth jwtProvider, RoutingContext routingContext, Consumer<RoutingContext> onAccessDenied) {
        final Cookie cookie = routingContext.getCookie("token");
        if (cookie != null) {
            final JsonObject json = new JsonObject();
            json.put("jwt", cookie.getValue());
            jwtProvider.authenticate(json, userAsyncResult -> {
                if (userAsyncResult.succeeded()) {
                    ((JWTUser)userAsyncResult.result()).doIsPermitted("admin", result -> {
                        if (result.succeeded()) {
                            routingContext.next();
                        } else {
                            onAccessDenied.accept(routingContext);
                        }
                    });
                } else {
                    onAccessDenied.accept(routingContext);
                }
            });
        } else {
            onAccessDenied.accept(routingContext);
        }
    }

    private void handleOAuthCode(RoutingContext routingContext, JWTAuth jwtProvider, String clientId, String clientSecret, String code, String path) {
        final WebClient client = WebClient.create(vertx);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.put("client_id", clientId);
        jsonObject.put("client_secret", clientSecret);
        jsonObject.put("code", code);
        client.postAbs("https://github.com/login/oauth/access_token")
            .putHeader("accept", TYPE_APPLICATION_JSON)
            .sendJson(jsonObject, result -> {
                if (result.succeeded()) {
                    HttpResponse<Buffer> response = result.result();
                    if (response.statusCode() == 200) {
                        final JsonObject object = response.bodyAsJsonObject();
                        final String accessToken = object.getString("access_token");
                        handleAccessToken(routingContext, jwtProvider, accessToken, path);
                    } else {
                        routingContext.fail(404);
                    }
                } else {
                    routingContext.fail(500);
                }
            });
    }

    private void handleAccessToken(RoutingContext routingContext, JWTAuth jwtProvider, String accessToken, String path) {
        final WebClient client = WebClient.create(vertx);
        client.getAbs("https://api.github.com/user")
            .putHeader("Authorization", "Bearer " + accessToken)
            .send(result -> {
                if (result.succeeded()) {
                    HttpResponse<Buffer> response = result.result();
                    if (response.statusCode() == 200) {
                        final JsonObject jsonObject = response.bodyAsJsonObject();
                        final String login = jsonObject.getString("login");
                        handleAccess(routingContext, jwtProvider, login, path);
                    } else {
                        routingContext.fail(404);
                    }
                } else {
                    routingContext.fail(500);
                }
            });
    }

    private void handleAccess(RoutingContext routingContext, JWTAuth jwtProvider, String login, String path) {
        if (adminUsers.contains(login)) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.put("user", login);
            final JWTOptions jwtOptions = new JWTOptions();
            jwtOptions.setExpiresInMinutes(30L);
            jwtOptions.setSubject("fractals");
            jwtOptions.addPermission("admin");
            final String token = jwtProvider.generateToken(jsonObject, jwtOptions);
            final Cookie cookie = createCookie(token);
            routingContext.response()
                    .putHeader("Set-Cookie", cookie.encode())
                    .putHeader("location", "/" + path)
                    .setStatusCode(303)
                    .end();
        } else {
            routingContext.fail(403);
        }
    }

    private Cookie createCookie(String token) {
        Cookie cookie = Cookie.cookie("token", token);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        return cookie;
    }

    @Override
    public void stop() throws Exception {
        if (executor != null) {
            executor.close();
        }
    }

    private void handleGetTileAsync(RoutingContext routingContext, String bucketName, AmazonS3 s3client) {
        executor.<byte[]>executeBlocking(future -> handleGetTile(routingContext, bucketName, s3client, future),false, result -> handleGetTileResult(routingContext, result));
    }

    private void handleGetTile(RoutingContext routingContext, String bucketName, AmazonS3 s3client, Future<byte[]> future) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();

            final UUID uuid = UUID.fromString(serverRequest.getParam("uuid"));
            final int zoom = Integer.parseInt(serverRequest.getParam("zoom"));
            final int x = Integer.parseInt(serverRequest.getParam("x"));
            final int y = Integer.parseInt(serverRequest.getParam("y"));

            final int side = 1 << zoom;

            if (uuid.equals(defaultUuid)) {
                final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, defaultBundle);

                Try.of(() -> TileGenerator.generateImage(request)).onFailure(future::fail).ifPresent(future::complete);
            } else {
                final String objectAsString = s3client.getObjectAsString(bucketName, uuid.toString());

                final JsonObject json = new JsonObject(objectAsString);

                final String manifest = json.getString("manifest");
                final String metadata = json.getString("metadata");
                final String script = json.getString("script");

                final Bundle bundle = TileUtils.parseData(manifest, metadata, script);

                final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, bundle);

                Try.of(() -> TileGenerator.generateImage(request)).onFailure(future::fail).ifPresent(future::complete);
            }
        } catch (Exception e) {
            future.fail(e);
        }
    }

    private void handleGetTileResult(RoutingContext routingContext, AsyncResult<byte[]> result) {
        if (result.succeeded()) {
            emitGetTileResponse(routingContext, result.result());
        } else {
            logger.log(Level.WARNING, "Failed to generate tile", result.cause());

            emitBadRequestResponse(routingContext, "Failed to generate tile");
        }
    }

    private void handleListBundlesAsync(RoutingContext routingContext, String bucketName, AmazonS3 s3client) {
        executor.<List<String>>executeBlocking(future -> handleListBundles(routingContext, bucketName, s3client, future),false, result -> handleListBundlesResult(routingContext, result));
    }

    private void handleListBundles(RoutingContext routingContext, String bucketName, AmazonS3 s3client, Future<List<String>> future) {
        try {
            List<String> uuids = listBundles(bucketName, s3client);

            future.complete(uuids);
        } catch (Exception e) {
            future.fail(e);
        }
    }

    private void handleListBundlesResult(RoutingContext routingContext, AsyncResult<List<String>> result) {
        if (result.succeeded()) {
            emitListBundlesResponse(routingContext, result.result());
        } else {
            logger.log(Level.WARNING, "Failed to list bundles", result.cause());

            emitBadRequestResponse(routingContext, "Failed to list bundles");
        }
    }

    private void handleCreateBundleAsync(RoutingContext routingContext, String bucketName, AmazonS3 s3client) {
        executor.<UUID>executeBlocking(future -> handleCreateBundle(routingContext, bucketName, s3client, future),false, result -> handleCreateBundlesResult(routingContext, result));
    }

    private void handleCreateBundle(RoutingContext routingContext, String bucketName, AmazonS3 s3client, Future<UUID> future) {
        try {
            final JsonObject body = routingContext.getBodyAsJson();

            final String manifest = body.getString("manifest");
            final String metadata = body.getString("metadata");
            final String script = body.getString("script");

            final UUID uuid = UUID.randomUUID();

            TileUtils.parseData(manifest, metadata, script);

            storeBundle(routingContext, bucketName, uuid, s3client);

            future.complete(uuid);
        } catch (Exception e) {
            future.fail(e);
        }
    }

    private void handleCreateBundlesResult(RoutingContext routingContext, AsyncResult<UUID> result) {
        if (result.succeeded()) {
            emitCreateBundleResponse(routingContext, result.result());
        } else {
            logger.log(Level.WARNING, "Failed to create bundle", result.cause());

            emitBadRequestResponse(routingContext, "Failed to create bundle");
        }
    }

    private void handleRemoveBundleAsync(RoutingContext routingContext, String bucketName, AmazonS3 s3client) {
        executor.<UUID>executeBlocking(future -> handleRemoveBundle(routingContext, bucketName, s3client, future),false, result -> handleRemoveBundleResult(routingContext, result));
    }

    private void handleRemoveBundle(RoutingContext routingContext, String bucketName, AmazonS3 s3client, Future<UUID> future) {
        try {
            final HttpServerRequest serverRequest = routingContext.request();

            final UUID uuid = UUID.fromString(serverRequest.getParam("uuid"));

            deleteBundle(bucketName, uuid, s3client);

            future.complete(uuid);
        } catch (Exception e) {
            future.fail(e);
        }
    }

    private void handleRemoveBundleResult(RoutingContext routingContext, AsyncResult<UUID> result) {
        if (result.succeeded()) {
            emitRemoveBundleResponse(routingContext, result.result());
        } else {
            logger.log(Level.WARNING, "Failed to delete bundle", result.cause());

            emitBadRequestResponse(routingContext, "Failed to delete bundle");
        }
    }

    private void storeBundle(RoutingContext routingContext, String bucketName, UUID uuid, AmazonS3 s3client) {
        final byte[] bytes = routingContext.getBody().getBytes();

        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(TYPE_APPLICATION_JSON);
        metadata.setContentLength(bytes.length);

        final ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        s3client.putObject(new PutObjectRequest(bucketName, uuid.toString(), input, metadata));
    }

    private void deleteBundle(String bucketName, UUID uuid, AmazonS3 s3client) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, uuid.toString()));
    }

    private List<String> listBundles(String bucketName, AmazonS3 s3client) {
        final ObjectListing objects = s3client.listObjects(bucketName);

        return objects.getObjectSummaries().stream().map(s -> s.getKey()).sorted().collect(Collectors.toList());
    }

    private void emitListBundlesResponse(RoutingContext routingContext, List<String> uuids) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .putHeader("Cache-Control", "no-cache, no-store")
                .setStatusCode(200)
                .end(new JsonArray(uuids).encode());
    }

    private void emitCreateBundleResponse(RoutingContext routingContext, UUID uuid) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .putHeader("Cache-Control", "no-cache, no-store")
                .setStatusCode(201)
                .end(createBundleResponseObject(uuid).encode());
    }

    private void emitRemoveBundleResponse(RoutingContext routingContext, UUID result) {
        routingContext.response()
                .putHeader("Cache-Control", "no-cache, no-store")
                .setStatusCode(200)
                .end();
    }

    private void emitGetTileResponse(RoutingContext routingContext, byte[] pngImage) {
        final SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        final Date today = Calendar.getInstance().getTime();
        final Date tomorrow = new Date(today.getTime() + 86400000);
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_IMAGE_PNG)
                .putHeader("Cache-Control", "public, max-age:86400")
                .putHeader("Last-Modified", df.format(today) + " GMT")
                .putHeader("Expires", df.format(tomorrow) + " GMT")
                .setStatusCode(200)
                .end(Buffer.buffer(pngImage));
    }

    private void emitBadRequestResponse(RoutingContext routingContext, String error) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(400)
                .end(createErrorResponseObject(error).encode());
    }

    private void emitFailureResponse(RoutingContext routingContext, String error) {
        routingContext.response()
                .putHeader(CONTENT_TYPE, TYPE_APPLICATION_JSON)
                .setStatusCode(routingContext.statusCode())
                .end(createErrorResponseObject(error).encode());
    }

    private void emitNotFoundResponse(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(404)
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

    private class FractalTemplateHandler implements TemplateHandler {
        private final TemplateEngine engine;
        private final String templateDirectory;
        private final String contentType;
        private final String fileName = "fractal";

        public FractalTemplateHandler(TemplateEngine engine, String templateDirectory, String contentType) {
            this.engine = engine;
            this.templateDirectory = templateDirectory;
            this.contentType = contentType;
        }

        public void handle(RoutingContext context) {
            final String file = this.templateDirectory + "/" + fileName;
            this.engine.render(context, file, (res) -> {
                if (res.succeeded()) {
                    context.response().putHeader(HttpHeaders.CONTENT_TYPE, this.contentType).end(res.result());
                } else {
                    context.fail(res.cause());
                }
            });
        }
    }
}
