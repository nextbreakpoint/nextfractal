package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.export.ExportState;
import lombok.Builder;

@Builder
public record ExportSessionStateChanged(ExportSession session, ExportState state, float progress) {}
