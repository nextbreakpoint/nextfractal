package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.Session;
import lombok.Builder;

@Builder
public record PlaybackDataLoaded(Session session, boolean continuous, boolean timeAnimation) {}
