package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.util.List;

public class ContextFreeFileManager extends FileManager {
    @Override
    protected Try<List<FileManagerEntry>, Exception> saveEntries(Session data) {
        return null;
    }

    @Override
    protected Try<Session, Exception> loadEntries(List<FileManagerEntry> entries) {
        return null;
    }
}
