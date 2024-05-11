/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.Bundle;
import com.nextbreakpoint.nextfractal.core.common.Clip;
import com.nextbreakpoint.nextfractal.core.common.FileManager;
import com.nextbreakpoint.nextfractal.core.common.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.common.FileManifest;
import com.nextbreakpoint.nextfractal.core.common.Metadata;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MandelbrotFileManager extends FileManager {
    @Override
    public Try<List<FileManagerEntry>, Exception> saveEntries(Bundle bundle) {
        return Try.of(() -> createEntries(bundle));
    }

    @Override
    public Try<Bundle, Exception> loadEntries(List<FileManagerEntry> entries) {
        return Try.of(() -> createBundle(entries));
    }

    public List<String> getSupportedFiles() {
        return Arrays.asList(".m");
    }

    public boolean canImportFile(File file) {
        return file.getName().endsWith(".m");
    }

    public Try<Bundle, Exception> importBundle(File file) {
        return Try.of(() -> file).filter(f -> ((File)f).getName().endsWith(".m"))
            .map(this::loadMandelbrotBundle).orElseGet(() -> createImportFailure());
    }

    private Try<Bundle, Exception> createImportFailure() {
        return Try.failure(new Exception("File format not recognized"));
    }

    private Try<Bundle, Exception> loadMandelbrotBundle(File file) {
        List<FileManagerEntry> entries = new LinkedList<>();
        entries.add(new FileManagerEntry("m-script", file.getAbsolutePath().getBytes()));
        return loadEntries(entries);
    }

    private Bundle createBundle(List<FileManagerEntry> entries) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Try<String, Exception> script = entries.stream().filter(entry -> entry.getName().equals("script"))
                .findFirst().map(scriptEntry -> Try.success(new String(scriptEntry.getData())))
                .orElse(Try.failure(new Exception("Script entry is required")));

        Try<MandelbrotMetadata, Exception> metadata = entries.stream().filter(entry -> entry.getName().equals("metadata"))
                .findFirst().map(metadataEntry -> Try.of(() -> mapper.readValue(metadataEntry.getData(), MandelbrotMetadata.class)))
                .orElse(Try.failure(new Exception("Metadata entry is required")));

        Try<List<Clip>, Exception> clips = entries.stream().filter(entry -> entry.getName().equals("clips"))
                .findFirst().map(clipsEntry -> decodeClips(clipsEntry.getData()))
                .orElse(Try.success(new LinkedList<>()));

        return new Bundle(new MandelbrotSession(script.orThrow(), metadata.orThrow()), clips.orThrow());
    }

    private List<FileManagerEntry> createEntries(Bundle bundle) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        List<FileManagerEntry> entries = new LinkedList<>();

        FileManifest manifest = new FileManifest(MandelbrotFactory.PLUGIN_ID);

        MandelbrotSession session = (MandelbrotSession) bundle.getSession();

        entries.add(new FileManagerEntry("manifest", mapper.writeValueAsBytes(manifest)));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(session.getMetadata())));
        entries.add(new FileManagerEntry("script", session.getScript().getBytes()));
        entries.add(new FileManagerEntry("clips", encodeClips(bundle.getClips()).orThrow()));

        return entries;
    }

    @Override
    protected Metadata decodeMetadata(String metadata) throws Exception {
        return new ObjectMapper().readValue(metadata, MandelbrotMetadata.class);
    }

    @Override
    protected String encodeMetadata(Object metadata) throws Exception {
        return new ObjectMapper().writeValueAsString((MandelbrotMetadata) metadata);
    }

//    protected void updateEncodedData(TextArea textArea, MandelbrotSession session) {
//        if (Boolean.getBoolean("mandelbrot.encode.data")) {
//            try {
//                MandelbrotDataStore service = new MandelbrotDataStore();
//                StringWriter writer = new StringWriter();
//                service.saveToWriter(writer, data);
//                String plainData = writer.toString();
//                String encodedData = Base64.getEncoder().encodeToString(plainData.getBytes());
//                encodedData = URLEncoder.encode(encodedData, "UTF-8");
//                logger.info("Update encoded data");
//            } catch (Exception e) {
//                logger.log(Level.FINE, "Can't encode data", e);
//            }
//        }
//    }
}
