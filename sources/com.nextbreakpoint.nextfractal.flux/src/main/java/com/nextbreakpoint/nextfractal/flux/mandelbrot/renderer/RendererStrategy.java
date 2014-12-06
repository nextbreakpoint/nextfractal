package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;

/**
 * @author Andrea Medeghini
 */
public interface RendererStrategy {
	/**
	 * @param rendererFractal 
	 * @param p
	 * @param x
	 * @param w
	 * @return the time
	 */
	public int renderPoint(RendererFractal rendererFractal, RendererPoint p, Number x, Number w);

	/**
	 * @return true if vertical symetry is supported.
	 */
	public boolean isVerticalSymetrySupported();

	/**
	 * @return true if horizontal symetry is supported.
	 */
	public boolean isHorizontalSymetrySupported();

	/**
	 * 
	 */
	public void updateParameters();
}