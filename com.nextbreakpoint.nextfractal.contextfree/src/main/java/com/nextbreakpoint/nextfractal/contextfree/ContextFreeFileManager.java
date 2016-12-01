package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.util.List;

public class ContextFreeFileManager implements FileManager {
//    public Session load(File file) throws Exception {
//        ContextFreeDataStore service = new ContextFreeDataStore();
//        ContextFreeData data = service.loadFromFile(file);
//        return new ContextFreeSession(data);
//    }
//
//    public Session save(File file, Object data) throws Exception {
//        ContextFreeDataStore service = new ContextFreeDataStore();
//        service.saveToFile(file, ((ContextFreeData) data));
//        return new ContextFreeSession((ContextFreeData) data);
//    }

    @Override
    public Try<List<FileManagerEntry>, Exception> writeEntries(Object data) {
        return null;
    }

    @Override
    public Try<Session, Exception> readEntries(List<FileManagerEntry> entries) {
        return null;
    }
}
