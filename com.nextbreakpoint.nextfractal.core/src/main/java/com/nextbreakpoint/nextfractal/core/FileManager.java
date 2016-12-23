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
package com.nextbreakpoint.nextfractal.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public abstract class FileManager {
    protected abstract Try<List<FileManagerEntry>, Exception> saveEntries(Bundle bundle);

    protected abstract Try<Bundle, Exception> loadEntries(List<FileManagerEntry> entries);

    public static Try<Bundle, Exception> loadFile(File file) {
        if (file.getName().endsWith(".m")) {
            return loadMandelbrotBundle(file);
        } else {
            try (ZipInputStream is = new ZipInputStream(new FileInputStream(file))) {
                return loadBundle(is).execute();
            } catch (Exception e) {
                return Try.failure(e);
            }
        }
    }

    public static Try<Bundle, Exception> saveFile(File file, Bundle bundle) {
        try (ZipOutputStream os = new ZipOutputStream(new FileOutputStream(file))) {
            return writeBundle(os, bundle).execute();
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static Try<Bundle, Exception> loadMandelbrotBundle(File file) {
        List<FileManagerEntry> entries = new LinkedList<>();
        entries.add(new FileManagerEntry("m-script", file.getAbsolutePath().getBytes()));
        return tryFindFactory("Mandelbrot").map(FractalFactory::createFileManager)
            .flatMap(manager -> manager.loadEntries(entries));
    }

    private static Try<Bundle, Exception> loadBundle(ZipInputStream is) {
        return Try.of(() -> readEntries(is)).flatMap(entries -> readManifest(entries))
            .flatMap(result -> tryFindFactory((String)result[1]).map(FractalFactory::createFileManager)
            .flatMap(manager -> manager.loadEntries((List<FileManagerEntry>)result[0])));
    }

    private static Try<Object[], Exception> readManifest(List<FileManagerEntry> entries) {
        return entries.stream().filter(entry -> entry.getName().equals("manifest"))
            .findFirst().map(manifest -> parseManifest(entries, manifest.getData()))
            .orElseGet(() -> Try.failure(new Exception("Manifest is required")));
    }

    private static Try<Object[], Exception> parseManifest(List<FileManagerEntry> entries, byte[] data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map properties = mapper.readValue(data, HashMap.class);
            String pluginId = (String) properties.get("pluginId");
            return Try.success(new Object[] { entries , Objects.requireNonNull(pluginId) });
        } catch (Exception e) {
            return Try.failure(new Exception("Plugin id not defined"));
        }
    }

    private static List<FileManagerEntry> readEntries(ZipInputStream is) throws IOException {
        LinkedList<FileManagerEntry> entries = new LinkedList<>();
        for (ZipEntry entry = is.getNextEntry(); entry != null; entry = is.getNextEntry()) entries.add(readEntry(is, entry));
        return entries;
    }

    private static FileManagerEntry readEntry(ZipInputStream is, ZipEntry entry) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copyBytes(is, baos);
        return new FileManagerEntry(entry.getName(), baos.toByteArray());
    }

    private static void copyBytes(InputStream is, OutputStream os) throws IOException {
        byte[] data = new byte[4096];
        int length = 0;
        while ((length = is.read(data)) > 0) {
            os.write(data, 0, length);
        }
    }

    private static Try<Bundle, Exception> writeBundle(ZipOutputStream os, Bundle bundle) {
        return tryFindFactory(bundle.getSession().getPluginId()).map(FractalFactory::createFileManager)
            .flatMap(manager -> manager.saveEntries(bundle).flatMap(entries -> putEntries(os, bundle, entries)));
    }

    private static Try<Bundle, Exception> putEntries(ZipOutputStream os, Bundle bundle, List<FileManagerEntry> entries) {
        return entries.stream().map(entry -> Try.of(() -> putEntry(os, bundle, entry)))
            .filter(result -> result.isFailure()).findFirst().orElse(Try.success(bundle));
    }

    private static Bundle putEntry(ZipOutputStream os, Bundle bundle, FileManagerEntry entry) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entry.getName());
        os.putNextEntry(zipEntry);
        os.write(entry.getData());
        os.closeEntry();
        return bundle;
    }

    protected Try<byte[], Exception> encodeClips(List<Clip> clips) throws IOException {
        List<Try<List<Map<String, Object>>, Exception>> result = clips.stream().map(this::encodeClip).collect(Collectors.toList());

        return result.stream().filter(Try::isFailure).findFirst().map(this::encodeClipsFailure)
            .orElseGet(() -> Try.success(result.stream().map(Try::get).collect(Collectors.toList()))).flatMap(list -> writeEncodedClips(list));
    }

    private Try<byte[], Exception> writeEncodedClips(List<List<Map<String, Object>>> list) {
        return Try.of(() -> new ObjectMapper().writeValueAsBytes(list));
    }

    private Try<List<Map<String, Object>>, Exception> encodeClip(Clip decodedClip) {
        Map<String, Object> lastMap = new HashMap<>();

        List<Try<Map<String, Object>, Exception>> encodedEvents = decodedClip.events()
            .map(decodedEvent -> Try.of(() -> encodeEvent(lastMap, decodedEvent)).execute()).collect(Collectors.toList());

        return encodedEvents.stream().filter(Try::isFailure).findFirst().map(this::encodeClipFailure)
            .orElseGet(() -> Try.success(encodedEvents.stream().map(Try::get).collect(Collectors.toList())));
    }

    protected Try<List<List<Map<String, Object>>>, Exception> encodeClipsFailure(Try<List<Map<String, Object>>, Exception> result) {
        return Try.failure(new Exception("Cannot encode clips"));
    }

    protected Try<List<Map<String, Object>>, Exception> encodeClipFailure(Try<Map<String, Object>, Exception> result) {
        return Try.failure(new Exception("Cannot encode clip"));
    }

    private Map<String, Object> encodeEvent(Map<String, Object> lastMap, ClipEvent event) throws Exception {
        Map<String, Object> eventMap = new HashMap<>();

        if (!event.getPluginId().equals(lastMap.get("pluginId"))) {
            eventMap.put("pluginId", event.getPluginId());
        }

        if (!event.getScript().equals(lastMap.get("script"))) {
            eventMap.put("script", event.getScript());
        }

        String metadata = encodeMetadata(event.getMetadata());

        if (!metadata.equals(lastMap.get("metadata"))) {
            eventMap.put("metadata", metadata);
        }

        Long time = event.getDate().getTime();

        if (!time.equals(lastMap.get("date"))) {
            eventMap.put("date", time);
        }

        lastMap.putAll(eventMap);

        return eventMap;
    }

    protected Try<List<Clip>, Exception> decodeClips(byte[] data) {
        Try<List<Try<Clip, Exception>>, Exception> result = readEncodedClips(data)
            .map(encodedClips -> encodedClips.stream().map(this::decodeClip).collect(Collectors.toList()));

        return result.flatMap(decodedClips -> decodedClips.stream().filter(Try::isFailure).findFirst().map(this::decodeClipsFailure)
            .orElseGet(() -> Try.success(new LinkedList(decodedClips.stream().map(Try::get).collect(Collectors.toList())))));
    }

    private Try<List<List<Map<String, Object>>>, Exception> readEncodedClips(byte[] data) {
        return Try.of(() -> new ObjectMapper().readValue(data, new TypeReference<List<List<Map<String, Object>>>>() {}));
    }

    protected Try<Clip, Exception> decodeClip(List<Map<String, Object>> encodedClip) {
        Map<String, Object> lastMap = new HashMap<>();

        List<Try<ClipEvent, Exception>> decodedEvents = encodedClip.stream()
            .map(encodedEvent -> Try.of(() -> decodeEvent(lastMap, encodedEvent)).execute()).collect(Collectors.toList());

        return decodedEvents.stream().filter(Try::isFailure).findFirst().map(this::decodeClipFailure)
            .orElseGet(() -> Try.success(new Clip(decodedEvents.stream().map(Try::get).collect(Collectors.toList()))));
    }

    private Try<List<Clip>, Exception> decodeClipsFailure(Try<Clip, Exception> result) {
        return Try.failure(new Exception("Cannot decode clips"));
    }

    private Try<Clip, Exception> decodeClipFailure(Try<ClipEvent, Exception> result) {
        return Try.failure(new Exception("Cannot decode clip"));
    }

    private ClipEvent decodeEvent(Map<String, Object> lastMap, Map<String, Object> eventMap) throws Exception {
        String pluginId = (String)eventMap.getOrDefault("pluginId", lastMap.get("pluginId"));
        String script = (String)eventMap.getOrDefault("script", lastMap.get("script"));
        String metadata = (String)eventMap.getOrDefault("metadata", lastMap.get("metadata"));
        Long date = (Long)eventMap.getOrDefault("date", lastMap.get("date"));

        lastMap.put("pluginId", pluginId);
        lastMap.put("script", script);
        lastMap.put("metadata", metadata);
        lastMap.put("date", date);

        return new ClipEvent(new Date(date), pluginId, script, decodeMetadata(metadata));
    }

    protected abstract Object decodeMetadata(String metadata) throws Exception;

    protected abstract String encodeMetadata(Object metadata) throws Exception;
}
