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
package com.nextbreakpoint.nextfractal.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.TileGenerator;
import com.nextbreakpoint.nextfractal.core.TileRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Base64;

public class TileHandler implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        final LambdaLogger logger = context.getLogger();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final JSONParser parser = new JSONParser();

            final JSONObject event = (JSONObject)parser.parse(reader);

            Integer size = 0;
            Integer rows = 0;
            Integer cols = 0;
            Integer row = 0;
            Integer col = 0;
            String manifest = null;
            String metadata = null;
            String script = null;

            JSONObject params = (JSONObject)event.get("params");
            if (params != null) {
                JSONObject querystring = (JSONObject)params.get("querystring");
                if (querystring != null) {
                    if (querystring.get("size") != null) {
                        size = Integer.parseInt((String) querystring.get("size"));
                    }
                    if (querystring.get("rows") != null) {
                        rows = Integer.parseInt((String) querystring.get("rows"));
                    }
                    if (querystring.get("cols") != null) {
                        cols = Integer.parseInt((String) querystring.get("cols"));
                    }
                    if (querystring.get("row") != null) {
                        row = Integer.parseInt((String) querystring.get("row"));
                    }
                    if (querystring.get("col") != null) {
                        col = Integer.parseInt((String) querystring.get("col"));
                    }
                    if (querystring.get("manifest") != null) {
                        manifest = URLDecoder.decode((String) querystring.get("manifest"), "UTF-8");
                    }
                    if (querystring.get("metadata") != null) {
                        metadata = URLDecoder.decode((String) querystring.get("metadata"), "UTF-8");
                    }
                    if (querystring.get("script") != null) {
                        script = URLDecoder.decode((String) querystring.get("script"), "UTF-8");
                    }
                }
            }

            final TileRequest request = TileGenerator.createTileRequest(size, rows, cols, row, col, manifest, metadata, script);

            final byte[] image = Try.of(() -> TileGenerator.generateImage(request)).onFailure(e -> logger.log(e.getMessage())).orThrow();

//            final JSONObject responseJson = new JSONObject();
//            final JSONObject headerJson = new JSONObject();
//            headerJson.put("content-type", "image/png");
//
//            responseJson.put("statusCode", "200");
//            responseJson.put("headers", headerJson);
//
//            responseJson.put("body", Base64.getEncoder().encodeToString(image));
//
//            final OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
//            writer.write(responseJson.toJSONString());
//            writer.close();

            final OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(Base64.getEncoder().encodeToString(image));
            writer.close();
        } catch (Exception e) {
            final JSONObject responseJson = new JSONObject();
            responseJson.put("statusCode", "400");
            responseJson.put("exception", e);
            final OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        }
    }
}
