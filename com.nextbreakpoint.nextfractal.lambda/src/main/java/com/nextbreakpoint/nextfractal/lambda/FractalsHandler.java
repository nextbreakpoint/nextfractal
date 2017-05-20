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
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.TileGenerator;
import com.nextbreakpoint.nextfractal.core.TileRequest;
import com.nextbreakpoint.nextfractal.core.TileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FractalsHandler implements RequestStreamHandler {
    private static final int TILE_SIZE = 256;

    // TODO retrieve data from S3 or DynamoDB or other source
    private static final String manifest = "{\"pluginId\":\"Mandelbrot\"}";
    private static final String metadata = "{\"translation\":{\"x\":0.0,\"y\":0.0,\"z\":1.0,\"w\":0.0},\"rotation\":{\"x\":0.0,\"y\":0.0,\"z\":0.0,\"w\":0.0},\"scale\":{\"x\":1.0,\"y\":1.0,\"z\":1.0,\"w\":1.0},\"point\":{\"x\":0.0,\"y\":0.0},\"julia\":false,\"options\":{\"showPreview\":false,\"showTraps\":false,\"showOrbit\":false,\"showPoint\":false,\"previewOrigin\":{\"x\":0.0,\"y\":0.0},\"previewSize\":{\"x\":0.25,\"y\":0.25}}}";
    private static final String script = "fractal {\norbit [-2.0 - 2.0i,+2.0 + 2.0i] [x,n] {\nloop [0, 200] (mod2(x) > 40) {\nx = x * x + w;\n}\n}\ncolor [#FF000000] {\npalette gradient {\n[#FFFFFFFF > #FF000000, 100];\n[#FF000000 > #FFFFFFFF, 100];\n}\ninit {\nm = 100 * (1 + sin(mod(x) * 0.2 / pi));\n}\nrule (n > 0) [1] {\ngradient[m - 1]\n}\n}\n}\n";

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        final LambdaLogger logger = context.getLogger();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final JSONParser parser = new JSONParser();

            final JSONObject event = (JSONObject)parser.parse(reader);

            UUID uuid = null;
            int zoom = 0;
            int x = 0;
            int y = 0;

            String resource = (String)event.get("resource");
            if (resource != null) {
                Matcher matcher = Pattern
                        .compile("([0-9a-f\\-]+)/([0-9]+)/([0-9]+)/([0-9]+)[.]png")
                        .matcher(resource);

                if (matcher.matches()) {
                    uuid = UUID.fromString(matcher.group(1));
                    zoom = Integer.parseInt(matcher.group(2));
                    x = Integer.parseInt(matcher.group(3));
                    y = Integer.parseInt(matcher.group(4));
                }
            }

            if (uuid == null) {
                final JSONObject responseJson = new JSONObject();
                responseJson.put("statusCode", "400");
                final OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                writer.write(responseJson.toJSONString());
                writer.close();
            } else {
                final Bundle bundle = TileUtils.parseData(manifest, metadata, script);

                final int side = 1 << zoom;

                final TileRequest request = TileGenerator.createTileRequest(TILE_SIZE, side, side,y % side,x % side, bundle);

                final byte[] image = Try.of(() -> TileGenerator.generateImage(request)).onFailure(e -> logger.log(e.getMessage())).orThrow();

                outputStream.write(image);
            }
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
