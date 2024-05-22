package com.nextbreakpoint.nextfractal.core.javafx.event;

import com.nextbreakpoint.nextfractal.core.javafx.Tool;
import lombok.Builder;

@Builder
public record ActiveToolChanged(Tool tool) {}
