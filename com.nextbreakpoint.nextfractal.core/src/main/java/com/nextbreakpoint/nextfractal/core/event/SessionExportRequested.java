package com.nextbreakpoint.nextfractal.core.event;

import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import lombok.Builder;

import java.io.File;

@Builder
public record SessionExportRequested(RendererSize size, String format) {}
