package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

public interface PixelStrategy {
	public int renderPixel(RendererPoint p, Number x, Number w, int offset);
}