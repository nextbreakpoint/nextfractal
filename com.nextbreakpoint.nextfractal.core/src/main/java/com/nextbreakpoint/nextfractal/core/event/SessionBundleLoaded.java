package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.common.Bundle;
import lombok.Builder;

@Builder
public record SessionBundleLoaded(Bundle bundle, boolean continuous, boolean appendToHistory) {}
