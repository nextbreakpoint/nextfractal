package com.nextbreakpoint.nextfractal.mandelbrot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.util.LinkedList;
import java.util.List;

public class MandelbrotFileManager implements FileManager {
//    public Session load(File file) throws Exception {
//        MandelbrotDataStore service = new MandelbrotDataStore();
//        MandelbrotData data = service.loadFromFile(file);
//        return new MandelbrotSession(data);
//    }
//
//    public Session save(File file, Object data) throws Exception {
//        MandelbrotDataStore service = new MandelbrotDataStore();
//        service.saveToFile(file, ((MandelbrotData) data));
//        return new MandelbrotSession((MandelbrotData) data);
//    }

    @Override
    public Try<List<FileManagerEntry>, Exception> writeEntries(Object data) {
        return Try.of(() -> createEntries((MandelbrotData) data));
    }

    @Override
    public Try<Session, Exception> readEntries(List<FileManagerEntry> entries) {
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

        entries.add(new FileManagerEntry("manifest", MandelbrotFactory.PLUGIN_ID.getBytes()));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(data.getView())));
        entries.add(new FileManagerEntry("script", data.getSource().getBytes()));

        return entries;
    }
}
