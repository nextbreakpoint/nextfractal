/*
 * NextFractal 2.1.2-ea+1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.core.common;

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
