package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

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
	public int renderColor(RendererState p);
	
	/**
	 * @param p
	 * @param x
	 * @param w
	 * @return the color
	 */
	public int renderPoint(RendererState p, Number x, Number w);
	
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