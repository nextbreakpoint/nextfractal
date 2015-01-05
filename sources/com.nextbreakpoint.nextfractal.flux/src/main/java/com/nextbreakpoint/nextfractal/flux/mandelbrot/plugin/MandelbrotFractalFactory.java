package com.nextbreakpoint.nextfractal.flux.mandelbrot.plugin;

import com.nextbreakpoint.nextfractal.flux.core.FractalFactory;
import com.nextbreakpoint.nextfractal.flux.core.FractalParser;
import com.nextbreakpoint.nextfractal.flux.core.FractalSession;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotFractalSession;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.parser.MandelbrotFractalParser;

public class MandelbrotFractalFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.core.FractalFactory#getId()
	 */
	public String getId() {
		return "MandelbrotFractalFactory";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.flux.core.FractalFactory#createParser()
	 */
	@Override
	public FractalParser createParser() {
		return new MandelbrotFractalParser();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.core.FractalFactory#createSession()
	 */
	@Override
	public FractalSession createSession() {
		return new MandelbrotFractalSession();
	}
}
