package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;

public interface JobsDelegate {
    void sessionSuspended(ExportSession session);

    void sessionResumed(ExportSession session);

    void sessionStopped(ExportSession session);
}
