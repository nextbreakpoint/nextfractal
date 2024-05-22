package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import lombok.Builder;

@Builder
public record ExportSessionStopped(ExportSession session) {}
