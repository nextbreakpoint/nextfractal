package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;

/**
 * @author Andrea Medeghini
 */
public interface RendererStrategy {
	/**
	 * 
	 */
	public void prepare();

	/**
	 * @param p
	 * @return the color
	 */
	public int renderColor(RendererPoint p);
	
	/**
	 * @param rendererFractal 
	 * @param p
	 * @param x
	 * @param w
	 * @return the color
	 */
	public int renderPoint(RendererPoint p, Number x, Number w);
	
	/**
	 * @return
	 */
	public boolean isSolidGuessSupported();

	/**
	 * @return true if vertical symetry is supported.
	 */
	public boolean isVerticalSymetrySupported();

	/**
	 * @return true if horizontal symetry is supported.
	 */
	public boolean isHorizontalSymetrySupported();

	/**
	 * @return
	 */
	public double getVerticalSymetryPoint();

	/**
	 * @return
	 */
	public double getHorizontalSymetryPoint();
}