package com.nextbreakpoint.nextfractal.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public abstract class FileManager {
    protected abstract Try<List<FileManagerEntry>, Exception> saveEntries(Session session);

    protected abstract Try<Session, Exception> loadEntries(List<FileManagerEntry> entries);

    public static Try<Session, Exception> loadFile(File file) {
        if (file.getName().endsWith(".m")) {
            return loadMandelbrotSession(file);
        } else {
            try (ZipInputStream is = new ZipInputStream(new FileInputStream(file))) {
                return loadSession(is).execute();
            } catch (Exception e) {
                return Try.failure(e);
            }
        }
    }

    public static Try<Session, Exception> saveFile(File file, Session session) {
        try (ZipOutputStream os = new ZipOutputStream(new FileOutputStream(file))) {
            return writeSession(os, session).execute();
        } catch (Exception e) {
            return Try.failure(e);
        }
    }

    private static Try<Session, Exception> loadMandelbrotSession(File file) {
        List<FileManagerEntry> entries = new LinkedList<>();
        entries.add(new FileManagerEntry("m-script", file.getAbsolutePath().getBytes()));
        return tryFindFactory("Mandelbrot").map(FractalFactory::createFileManager)
            .flatMap(manager -> manager.loadEntries(entries));
    }

    private static Try<Session, Exception> loadSession(ZipInputStream is) {
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

    private static Try<Session, Exception> writeSession(ZipOutputStream os, Session session) {
        return tryFindFactory(session.getPluginId()).map(FractalFactory::createFileManager)
            .flatMap(manager -> manager.saveEntries(session)).flatMap(entries -> putEntries(os, session, entries));
    }

    private static Try<Session, Exception> putEntries(ZipOutputStream os, Session session, List<FileManagerEntry> entries) {
        return entries.stream().map(entry -> Try.of(() -> putEntry(os, session, entry)))
            .filter(result -> result.isFailure()).findFirst().orElse(Try.success(session));
    }

    private static Session putEntry(ZipOutputStream os, Session session, FileManagerEntry entry) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entry.getName());
        os.putNextEntry(zipEntry);
        os.write(entry.getData());
        os.closeEntry();
        return session;
    }
}
