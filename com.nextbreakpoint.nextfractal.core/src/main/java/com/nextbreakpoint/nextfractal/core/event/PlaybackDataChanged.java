package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.Session;
import lombok.Builder;

@Builder
public record PlaybackDataChanged(Session session, boolean continuous, boolean appendToHistory) {}
