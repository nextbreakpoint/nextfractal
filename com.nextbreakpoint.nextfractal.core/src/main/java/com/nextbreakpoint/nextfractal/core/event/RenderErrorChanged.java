package com.nextbreakpoint.nextfractal.core.event;

import lombok.Builder;

@Builder
public record RenderErrorChanged(String error) {}
