package com.nextbreakpoint.nextfractal.flux.mandelbrot.parser;

import com.nextbreakpoint.nextfractal.flux.core.FractalParser;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.fractal.RendererFractalAdapter;

public class MandelbrotFractalParser implements FractalParser {
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.core.FractalParser#parse(java.lang.String)
	 */
	@Override
	public RendererFractal parse(String source) {
		return new RendererFractalAdapter(); 
	}
}
