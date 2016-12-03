package com.nextbreakpoint.nextfractal.mandelbrot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class MandelbrotFileManager extends FileManager {
    @Override
    protected Try<List<FileManagerEntry>, Exception> saveEntries(Session session) {
        return Try.of(() -> createEntries((MandelbrotData) session.getData()));
    }

    @Override
    protected Try<Session, Exception> loadEntries(List<FileManagerEntry> entries) {
        return entries.stream().filter(entry -> entry.getName().equals("m-script")).findFirst()
            .map(entry -> Try.of(() -> loadMscript(new FileInputStream(new String(entry.getData()))))).orElseGet(() -> createSession(entries));
    }

    private Try<Session, Exception> createSession(List<FileManagerEntry> entries) {
        ObjectMapper mapper = new ObjectMapper();

        Try<String, Exception> source = entries.stream().filter(entry -> entry.getName().equals("script"))
                .findFirst().map(script -> Try.success(new String(script.getData())))
                .orElse(Try.failure(new Exception("Script entry is required")));

        Try<MandelbrotView, Exception> view = entries.stream().filter(entry -> entry.getName().equals("metadata"))
                .findFirst().map(metadata -> Try.of(() -> mapper.readValue(metadata.getData(), MandelbrotView.class)))
                .orElse(Try.failure(new Exception("Metadata entry is required")));

        MandelbrotData data = new MandelbrotData();

        return Try.of(() -> data)
            .and(() -> { data.setSource(source.orThrow()); return data; })
            .and(() -> { data.setView(view.orThrow()); return data; })
            .map(d -> new MandelbrotSession(d));
    }

    private List<FileManagerEntry> createEntries(MandelbrotData data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<FileManagerEntry> entries = new LinkedList<>();

        FileManifest manifest = new FileManifest(MandelbrotFactory.PLUGIN_ID);

        entries.add(new FileManagerEntry("manifest", mapper.writeValueAsBytes(manifest)));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(data.getView())));
        entries.add(new FileManagerEntry("script", data.getSource().getBytes()));

        return entries;
    }

    private Session loadMscript(InputStream is) throws Exception {
        MandelbrotDataStore service = new MandelbrotDataStore();
        MandelbrotData data = service.loadFromStream(is);
        return new MandelbrotSession(data);
    }
}
