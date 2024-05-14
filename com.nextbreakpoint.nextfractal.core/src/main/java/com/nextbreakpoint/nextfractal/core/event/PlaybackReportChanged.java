package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.ParserResult;
import com.nextbreakpoint.nextfractal.core.common.Session;
import lombok.Builder;

@Builder
public record PlaybackReportChanged(Session session, boolean continuous, boolean appendToHistory, ParserResult result) {}
