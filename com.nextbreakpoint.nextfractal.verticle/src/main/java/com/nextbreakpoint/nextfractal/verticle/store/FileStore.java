package com.nextbreakpoint.nextfractal.verticle.store;

import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.TileUtils;
import io.vertx.core.json.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStore implements BundleStore {
    private final File path;

    public FileStore(File path) {
        this.path = path;
        path.mkdirs();
    }

    @Override
    public Bundle getBundle(UUID uuid) {
        final File location = new File(path, uuid.toString());

        try (BufferedReader reader = new BufferedReader(new FileReader(location))) {
            final String objectAsString = reader.lines().collect(Collectors.joining("/n"));

            final JsonObject json = new JsonObject(objectAsString);

            final String manifest = json.getString("manifest");
            final String metadata = json.getString("metadata");
            final String script = json.getString("script");

            return TileUtils.parseData(manifest, metadata, script);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void saveBundle(UUID uuid, byte[] bytes) {
        final File location = new File(path, uuid.toString());

        try {
            try (OutputStream output = new FileOutputStream(location)) {
                output.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBundle(UUID uuid) {
        final File location = new File(path, uuid.toString());

        location.delete();
    }

    @Override
    public List<String> listBundles() {
        final File[] files = path.listFiles();

        if (files != null) {
            return Stream.of(files).map(File::getName).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
