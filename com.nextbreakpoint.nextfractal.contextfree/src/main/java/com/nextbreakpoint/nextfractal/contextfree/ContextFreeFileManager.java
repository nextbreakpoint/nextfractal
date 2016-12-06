package com.nextbreakpoint.nextfractal.contextfree;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.FileManifest;
import com.nextbreakpoint.nextfractal.core.Session;

import java.util.LinkedList;
import java.util.List;

public class ContextFreeFileManager extends FileManager {
    @Override
    protected Try<List<FileManagerEntry>, Exception> saveEntries(Session session) {
        return Try.of(() -> createEntries((ContextFreeSession) session));
    }

    @Override
    protected Try<Session, Exception> loadEntries(List<FileManagerEntry> entries) {
        return createSession(entries);
    }

    private Try<Session, Exception> createSession(List<FileManagerEntry> entries) {
        ObjectMapper mapper = new ObjectMapper();

        Try<String, Exception> script = entries.stream().filter(entry -> entry.getName().equals("script"))
                .findFirst().map(scriptEntry -> Try.success(new String(scriptEntry.getData())))
                .orElse(Try.failure(new Exception("Script entry is required")));

        Try<ContextFreeMetadata, Exception> metadata = entries.stream().filter(entry -> entry.getName().equals("metadata"))
                .findFirst().map(metadataEntry -> Try.of(() -> mapper.readValue(metadataEntry.getData(), ContextFreeMetadata.class)))
                .orElse(Try.failure(new Exception("Metadata entry is required")));

        return Try.of(() -> new ContextFreeSession(metadata.orThrow(), script.orThrow()));
    }

    private List<FileManagerEntry> createEntries(ContextFreeSession session) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<FileManagerEntry> entries = new LinkedList<>();

        FileManifest manifest = new FileManifest(ContextFreeFactory.PLUGIN_ID);

        entries.add(new FileManagerEntry("manifest", mapper.writeValueAsBytes(manifest)));
        entries.add(new FileManagerEntry("metadata", mapper.writeValueAsBytes(session.getMetadata())));
        entries.add(new FileManagerEntry("script", session.getScript().getBytes()));

        return entries;
    }
}
