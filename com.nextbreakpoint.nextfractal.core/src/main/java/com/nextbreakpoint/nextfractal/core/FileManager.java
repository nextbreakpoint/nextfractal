package com.nextbreakpoint.nextfractal.core;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.session.Session;

import java.util.List;

public interface FileManager {
    Try<List<FileManagerEntry>, Exception> writeEntries(Object data);

    Try<Session, Exception> readEntries(List<FileManagerEntry> entries);
}
