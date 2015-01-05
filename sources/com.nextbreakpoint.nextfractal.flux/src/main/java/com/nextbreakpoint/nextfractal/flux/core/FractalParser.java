package com.nextbreakpoint.nextfractal.flux.core;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public interface FractalParser {
	public RendererFractal parse(String source);
}
