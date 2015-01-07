package com.nextbreakpoint.nextfractal.flux;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;

public interface FractalParser {
	/**
	 * @param packageName
	 * @param className
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public RendererFractal parse(String packageName, String className, String source) throws Exception;
}
