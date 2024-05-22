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
package com.nextbreakpoint.nextfractal.core.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.nextbreakpoint.nextfractal.core.common.Plugins.tryFindFactory;

public abstract class FileManager {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Try<Bundle, Exception> loadBundle(File file) {
        try (InputStream is = new FileInputStream(file)) {
            return loadBundle(is);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    public static Try<Bundle, Exception> saveBundle(File file, Bundle bundle) {
        try (OutputStream os = new FileOutputStream(file)) {
            return saveBundle(os, bundle).execute();
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    public static Try<Bundle, Exception> loadBundle(InputStream stream) {
        try (ZipInputStream is = new ZipInputStream(stream)) {
            return loadBundle(is).execute();
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    public static Try<Bundle, Exception> saveBundle(OutputStream stream, Bundle bundle) {
        try (ZipOutputStream os = new ZipOutputStream(stream)) {
            return saveBundle(os, bundle).execute();
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static Try<Bundle, Exception> loadBundle(ZipInputStream is) {
        return readEntries(is).flatMap(FileManager::decodeBundle);
    }

    private static Try<Bundle, Exception> saveBundle(ZipOutputStream os, Bundle bundle) {
        return encodeBundle(bundle).flatMap(entries -> writeEntries(os, entries)).map(x -> bundle);
    }

    public static Try<Bundle, Exception> decodeBundle(List<FileEntry> entries) {
        return decodeManifest(entries)
                .flatMap(manifest -> decodeScript(entries)
                        .flatMap(script -> decodeMetadata(entries, getPluginId(manifest))
                                .flatMap(metadata -> createBundle(getPluginId(manifest), script, metadata)
                                        .flatMap(bundle -> decodeClips(entries)
                                                .flatMap(clips -> createBundle(bundle, clips))
                                        )
                                )
                        )
                );
    }

    private static Try<Bundle, Exception> createBundle(String pluginId, String script, Metadata metadata) {
        return tryFindFactory(pluginId).map(factory -> new Bundle(factory.createSession(script, metadata), List.of()));
    }

    private static Try<Bundle, Exception> createBundle(Bundle bundle, List<Clip> clips) {
        return Try.of(() -> new Bundle(bundle.getSession(), clips));
    }

    public static Try<List<FileEntry>, Exception> encodeBundle(Bundle bundle) {
        return encodeManifest(bundle)
                .flatMap(manifest -> encodeScript(bundle)
                        .flatMap(script -> encodeMetadata(bundle)
                                .flatMap(metadata -> encodeClips(bundle)
                                        .flatMap(clips -> createEntries(manifest, script, metadata, clips))
                                )
                        )
                );
    }

    private static Try<List<FileEntry>, Exception> createEntries(FileEntry manifest, FileEntry script, FileEntry metadata, FileEntry clips) {
        return Try.success(List.of(manifest, script, metadata, clips));
    }

    private static Try<Map<String, String>, Exception> decodeManifest(List<FileEntry> entries) {
        return entries.stream()
                .filter(entry -> entry.getName().equals("manifest"))
                .findFirst()
                .map(entry -> decodeManifest(entry.getData()))
                .orElse(Try.failure(new Exception("Manifest entry is required")));
    }

    private static Try<String, Exception> decodeScript(List<FileEntry> entries) {
        return entries.stream()
                .filter(entry -> entry.getName().equals("script"))
                .findFirst()
                .map(entry -> decodeScript(entry.getData()))
                .orElse(Try.failure(new Exception("Script entry is required")));
    }

    private static Try<Metadata, Exception> decodeMetadata(List<FileEntry> entries, String pluginId) {
        return entries.stream()
                .filter(entry -> entry.getName().equals("metadata"))
                .findFirst()
                .map(entry -> decodeMetadata(pluginId, entry.getData()))
                .orElse(Try.failure(new Exception("Metadata entry is required")));
    }

    private static Try<List<Clip>, Exception> decodeClips(List<FileEntry> entries) {
        return entries.stream()
                .filter(entry -> entry.getName().equals("clips"))
                .findFirst()
                .map(entry1 -> decodeClips(entry1.getData()))
                .orElse(Try.success(List.of()));
    }

    private static Try<Map<String, String>, Exception> decodeManifest(byte[] data) {
        return Try.of(() -> MAPPER.readValue(data, new TypeReference<>() {}));
    }

    private static Try<String, Exception> decodeScript(byte[] data) {
        return Try.success(new String(data));
    }

    private static Try<Metadata, Exception> decodeMetadata(String pluginId, byte[] data) {
        return decodeMetadata(pluginId, new String(data));
    }

    private static Try<List<Clip>, Exception> decodeClips(byte[] data) {
        return Try.of(() -> MAPPER.readTree(data)).flatMap(FileManager::decodeClips);
    }

    private static Try<List<Clip>, Exception> decodeClips(JsonNode clips) {
        final List<Try<Clip, Exception>> results = JsonUtils.getClips(clips)
                .map(FileManager::decodeClip)
                .takeWhile(Try::isSuccess)
                .toList();
        final Optional<Try<Clip, Exception>> error = results.stream().filter(Try::isFailure).findFirst();
        return error.<Try<List<Clip>, Exception>>map(result -> createFailure("Can't decode clips", result))
                .orElseGet(() -> Try.success(results.stream().map(Try::get).toList()));
    }

    private static Try<Clip, Exception> decodeClip(JsonNode clip) {
        final Map<String, Object> stateMap = new HashMap<>();
        final List<Try<ClipEvent, Exception>> results = JsonUtils.getEvents(clip.get("events"))
                .map(clipEvent -> decodeClipEvent(stateMap, clipEvent))
                .takeWhile(Try::isSuccess)
                .toList();
        final Optional<Try<ClipEvent, Exception>> error = results.stream().filter(Try::isFailure).findFirst();
        return error.<Try<Clip, Exception>>map(result -> createFailure("Can't decode clip", result))
                .orElseGet(() -> Try.success(new Clip(results.stream().map(Try::get).toList())));
    }

    private static Try<ClipEvent, Exception> decodeClipEvent(Map<String, Object> stateMap, JsonNode clipEvent) {
        try {
            final String pluginId = JsonUtils.getString(clipEvent, "pluginId");
            final String script = JsonUtils.getString(clipEvent, "script");
            final String data = JsonUtils.getString(clipEvent, "metadata");
            final Long date =JsonUtils. getLong(clipEvent, "date");

            if (pluginId != null) {
                stateMap.put("pluginId", pluginId);
            }

            if (script != null) {
                stateMap.put("script", script);
            }

            final Metadata metadata = data != null ? decodeMetadata((String) stateMap.get("pluginId"), new String(data.getBytes())).orThrow() : null;

            if (metadata != null) {
                stateMap.put("metadata", metadata);
            }

            if (date != null) {
                stateMap.put("date", new Date(date));
            }

            return Try.success(new ClipEvent((Date) stateMap.get("date"), (String) stateMap.get("pluginId"), (String) stateMap.get("script"), (Metadata) stateMap.get("metadata")));
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static Try<FileEntry, Exception> encodeManifest(Bundle bundle) {
        return encodeManifest(bundle.getSession()).map(entry -> new FileEntry("manifest", entry));
    }

    private static Try<FileEntry, Exception> encodeScript(Bundle bundle) {
        return encodeScript(bundle.getSession()).map(entry -> new FileEntry("script", entry));
    }

    private static Try<FileEntry, Exception> encodeMetadata(Bundle bundle) {
        return encodeMetadata(bundle.getSession()).map(entry -> new FileEntry("metadata", entry));
    }

    private static Try<FileEntry, Exception> encodeClips(Bundle bundle) {
        return encodeClips(bundle.getSession(), bundle.getClips()).map(entry -> new FileEntry("clips", entry));
    }

    private static Try<byte[], Exception> encodeManifest(Session session) {
        return Try.of(() -> MAPPER.writeValueAsBytes(new FileManifest(session.getPluginId())));
    }

    private static Try<byte[], Exception> encodeScript(Session session) {
        return Try.of(() -> session.getScript().getBytes());
    }

    private static Try<byte[], Exception> encodeMetadata(Session session) {
        return encodeMetadata(session.getPluginId(), session.getMetadata()).map(String::getBytes);
    }

    private static Try<byte[], Exception> encodeClips(Session session, List<Clip> clips) {
        return encodeClips(clips).flatMap(data -> Try.of(() -> MAPPER.writeValueAsBytes(data)));
    }

    private static Try<Object, Exception> encodeClips(List<Clip> clips) {
        final List<Try<Object, Exception>> results = clips.stream()
                .map(FileManager::encodeClip)
                .takeWhile(Try::isSuccess)
                .toList();
        final Optional<Try<Object, Exception>> error = results.stream().filter(Try::isFailure).findFirst();
        return error.map(result -> createFailure("Can't encode clips", result))
                .orElseGet(() -> Try.success(results.stream().map(Try::get).toList()));
    }

    private static Try<Object, Exception> encodeClip(Clip clip) {
        final Map<String, Object> stateMap = new HashMap<>();
        final List<Try<Object, Exception>> results = clip.getEvents().stream()
                .map(clipEvent -> encodeClipEvent(stateMap, clipEvent))
                .takeWhile(Try::isSuccess)
                .toList();
        final Optional<Try<Object, Exception>> error = results.stream().filter(Try::isFailure).findFirst();
        return error.map(result -> createFailure("Can't encode clip", result))
                .orElseGet(() -> Try.success(Map.of("events", results.stream().map(Try::get).toList())));
    }

    private static Try<Object, Exception> encodeClipEvent(Map<String, Object> stateMap, ClipEvent clipEvent) {
        try {
            final Map<String, Object> eventMap = new HashMap<>();

            if (!clipEvent.getPluginId().equals(stateMap.get("pluginId"))) {
                eventMap.put("pluginId", clipEvent.getPluginId());
            }

            if (!clipEvent.getScript().equals(stateMap.get("script"))) {
                eventMap.put("script", clipEvent.getScript());
            }

            final String metadata = encodeMetadata(clipEvent.getPluginId(), clipEvent.getMetadata()).orThrow();

            if (!metadata.equals(stateMap.get("metadata"))) {
                eventMap.put("metadata", metadata);
            }

            final Long date = clipEvent.getDate().getTime();

            if (!date.equals(stateMap.get("date"))) {
                eventMap.put("date", date);
            }

            stateMap.putAll(eventMap);

            return Try.success(eventMap);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static Try<Metadata, Exception> decodeMetadata(String pluginId, String metadata) {
        return tryFindFactory(pluginId).flatMap(factory -> Try.of(() -> factory.createMetadataCodec().decodeMetadata(metadata)));
    }

    private static Try<String, Exception> encodeMetadata(String pluginId, Metadata metadata) {
        return tryFindFactory(pluginId).flatMap(factory -> Try.of(() -> factory.createMetadataCodec().encodeMetadata(metadata)));
    }

    private static Try<List<FileEntry>, Exception> readEntries(ZipInputStream is) {
        try {
            final List<FileEntry> entries = new LinkedList<>();
            for (ZipEntry entry = is.getNextEntry(); entry != null; entry = is.getNextEntry())
                entries.add(readEntry(is, entry));
            return Try.success(entries);
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static FileEntry readEntry(ZipInputStream is, ZipEntry entry) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            IOUtils.copyBytes(is, baos);
            return new FileEntry(entry.getName(), baos.toByteArray());
        }
    }

    private static Try<Void, Exception> writeEntries(ZipOutputStream os, List<FileEntry> entries) {
        return entries.stream()
                .map(entry -> Try.of(() -> writeEntry(os, entry)))
                .filter(Try::isFailure)
                .findFirst()
                .orElseGet(() -> Try.success(null));
    }

    private static Void writeEntry(ZipOutputStream os, FileEntry entry) throws IOException {
        final ZipEntry zipEntry = new ZipEntry(entry.getName());
        os.putNextEntry(zipEntry);
        os.write(entry.getData());
        os.closeEntry();
        return null;
    }

    private static String getPluginId(Map<String, String> manifest) {
        return manifest.getOrDefault("pluginId", "");
    }

    private static <T> Try<T, Exception> createFailure(String message, Try<?, Exception> error) {
        final Exception[] cause = new Exception[1];
        error.ifFailure(e -> cause[0] = e);
        return Try.failure(new RuntimeException(message, cause[0]));
    }
}
