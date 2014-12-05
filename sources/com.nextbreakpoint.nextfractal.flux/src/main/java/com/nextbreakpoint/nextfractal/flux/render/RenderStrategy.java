package com.nextbreakpoint.nextfractal.flux.render;

import com.nextbreakpoint.nextfractal.core.math.Complex;

/**
 * @author Andrea Medeghini
 */
public interface RenderStrategy {
	/**
	 * @param p
	 * @param pw
	 * @param px
	 * @return the time
	 */
	public int renderPoint(RenderedPoint p, Complex px, Complex pw);

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