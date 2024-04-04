package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.Session;
import lombok.Builder;

@Builder
public record EditorDataChanged(Session session, boolean continuous, boolean timeAnimation) {}
