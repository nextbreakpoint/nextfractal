package com.nextbreakpoint.nextfractal.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class TileUtils {
    private TileUtils() {}

    public static Bundle parseData(String manifest, String metadata, String script) throws Exception {
        final FileManagerEntry manifestEntry = new FileManagerEntry("manifest", manifest.getBytes());
        final FileManagerEntry metadataEntry = new FileManagerEntry("metadata", metadata.getBytes());
        final FileManagerEntry scriptEntry = new FileManagerEntry("script", script.getBytes());

        final List<FileManagerEntry> entries = Arrays.asList(manifestEntry, metadataEntry, scriptEntry);

        final ObjectMapper mapper = new ObjectMapper();

        final FileManifest decodedManifest = mapper.readValue(manifestEntry.getData(), FileManifest.class);

        return Plugins.tryFindFactory(decodedManifest.getPluginId())
                .flatMap(factory -> factory.createFileManager().loadEntries(entries)).orThrow();
    }
}
