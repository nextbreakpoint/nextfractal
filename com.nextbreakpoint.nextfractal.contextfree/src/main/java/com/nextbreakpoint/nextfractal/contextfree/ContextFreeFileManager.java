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
package com.nextbreakpoint.nextfractal.contextfree;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.Clip;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.FileManifest;

import java.util.LinkedList;
import java.util.List;

public class ContextFreeFileManager extends FileManager {
    @Override
    protected Try<List<FileManagerEntry>, Exception> saveEntries(Bundle bundle) {
        return Try.of(() -> createEntries(bundle));
    }

    @Override
    protected Try<Bundle, Exception> loadEntries(List<FileManagerEntry> entries) {
        return Try.of(() -> createBundle(entries));
    }

    private Bundle createBundle(List<FileManagerEntry> entries) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Try<String, Exception> script = entries.stream().filter(entry -> entry.getName().equals("script"))
                .findFirst().map(scriptEntry -> Try.success(new String(scriptEntry.getData())))
                .orElse(Try.failure(new Exception("Script entry is required")));

        Try<ContextFreeMetadata, Exception> metadata = entries.stream().filter(entry -> entry.getName().equals("metadata"))
                .findFirst().map(metadataEntry -> Try.of(() -> mapper.readValue(metadataEntry.getData(), ContextFreeMetadata.class)))
                .orElse(Try.failure(new Exception("Metadata entry is required")));

        Try<List<Clip>, Exception> clips = entries.stream().filter(entry -> entry.getName().equals("clips"))
                .findFirst().map(clipsEntry -> decodeClips(clipsEntry.getData()))
                .orElse(Try.success(new LinkedList<>()));

        return new Bundle(new ContextFreeSession(metadata.orThrow(), script.orThrow()), clips.orThrow());
    }

    private List<FileManagerEntry> createEntries(Bundle bundle) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        List<FileManagerEntry> entries = new LinkedList<>();

        FileManifest manifest = new FileManifest(ContextFreeFactory.PLUGIN_ID);

        ContextFreeSession session = (ContextFreeSession) bundle.getSession();

        entries.add(new FileManagerEntry("manifest", mapper.writeValueAsBytes(manifest)));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(session.getMetadata())));
        entries.add(new FileManagerEntry("script", session.getScript().getBytes()));
        entries.add(new FileManagerEntry("clips", encodeClips(bundle.getClips()).orThrow()));

        return entries;
    }

    @Override
    protected Object decodeMetadata(String metadata) throws Exception {
        return new ObjectMapper().readValue(metadata, ContextFreeMetadata.class);
    }

    @Override
    protected String encodeMetadata(Object metadata) throws Exception {
        return new ObjectMapper().writeValueAsString((ContextFreeMetadata) metadata);
    }
}
