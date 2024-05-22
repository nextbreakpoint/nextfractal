package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.Clip;
import lombok.Builder;

@Builder
public record CaptureSessionStarted(Clip clip) {}
