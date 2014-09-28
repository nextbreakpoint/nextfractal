/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension;

import java.util.List;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.extension.ProcessingFormulaExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public abstract class RenderingFormulaExtensionRuntime<T extends RenderingFormulaExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * Prepares formula for rendering.
	 */
	public abstract void prepareForRendering(ProcessingFormulaExtensionRuntime formulaRuntime, OrbitTrapExtensionRuntime<?> orbitTrapRuntime);

	/**
	 * @return true if mandelbrot mode is allowed.
	 */
	public abstract boolean isMandelbrotModeAllowed();

	/**
	 * Returns the threshold.
	 * 
	 * @return the threshold.
	 */
	public abstract double getThreshold();

	/**
	 * Returns the iterations.
	 * 
	 * @return the iterations.
	 */
	public abstract int getIterations();

	/**
	 * Returns the default center.
	 * 
	 * @return the default center.
	 */
	public abstract DoubleVector2D getDefaultCenter();

	/**
	 * Returns the default scale.
	 * 
	 * @return the default scale.
	 */
	public abstract DoubleVector2D getDefaultScale();

	/**
	 * Returns the center.
	 * 
	 * @return the center.
	 */
	public abstract DoubleVector2D getCenter();

	/**
	 * Returns the scale.
	 * 
	 * @return the scale.
	 */
	public abstract DoubleVector2D getScale();

	/**
	 * Returns the initial point.
	 * 
	 * @return the initial point.
	 */
	public abstract Complex getInitialPoint();

	/**
	 * @return true if horizontal symetry is allowed.
	 */
	public abstract boolean isHorizontalSymetryAllowed();

	/**
	 * @return true if vertical symetry is allowed.
	 */
	public abstract boolean isVerticalSymetryAllowed();

	/**
	 * Returns the horizontal symetry point.
	 * 
	 * @return the horizontal symetry point.
	 */
	public abstract double getHorizontalSymetryPoint();

	/**
	 * Returns the vertical symetry point.
	 * 
	 * @return the vertical symetry point.
	 */
	public abstract double getVerticalSymetryPoint();

	/**
	 * @param cp
	 * @return the time.
	 */
	public abstract int renderPoint(RenderedPoint cp);

	/**
	 * @param cp
	 * @return the orbit.
	 */
	public abstract List<Complex> renderOrbit(RenderedPoint cp);

	/**
	 * @param cp
	 * @return
	 */
	public abstract double getNormalizedIterationCount(RenderedPoint cp);
}
