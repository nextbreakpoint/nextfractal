package com.nextbreakpoint.nextfractal.core.event;

import lombok.Builder;

@Builder
public record EditorSourceChanged(String source) {}
