/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class IncolouringFormulaExtensionRuntime<T extends IncolouringFormulaExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @return true if solid guess is allowed.
	 */
	public abstract boolean isSolidGuessAllowed();

	/**
	 * @return true if horizontal symetry is allowed.
	 */
	public abstract boolean isHorizontalSymetryAllowed();

	/**
	 * @return true if vertical symetry is allowed.
	 */
	public abstract boolean isVerticalSymetryAllowed();

	/**
	 * Sets the iterations.
	 * 
	 * @param maxColors the number of colours.
	 */
	public abstract void prepareForRendering(RenderingFormulaExtensionRuntime<?> formulaRuntime, int maxColors);

	/**
	 * @param cp
	 * @return the color.
	 */
	public abstract int renderColor(RenderedPoint cp);

	/**
	 * @param cp
	 * @param shift
	 * @return the color.
	 */
	public abstract int renderColor(RenderedPoint cp, int shift);
}
