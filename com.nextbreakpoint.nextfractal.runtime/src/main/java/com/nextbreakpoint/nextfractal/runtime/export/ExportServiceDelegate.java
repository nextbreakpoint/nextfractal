package com.nextbreakpoint.nextfractal.runtime.export;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.export.ExportState;

public interface ExportServiceDelegate {
    void notifyUpdate(ExportSession session, ExportState state, float progress);
}
