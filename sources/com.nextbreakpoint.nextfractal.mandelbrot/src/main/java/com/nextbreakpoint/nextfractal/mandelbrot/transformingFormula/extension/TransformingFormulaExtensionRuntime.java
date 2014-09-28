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
package com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.extension;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.math.Complex;

/**
 * @author Andrea Medeghini
 */
public abstract class TransformingFormulaExtensionRuntime<T extends TransformingFormulaExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @return true if horizontal symetry is allowed.
	 */
	public abstract boolean isHorizontalSymetryAllowed();

	/**
	 * @return true if vertical symetry is allowed.
	 */
	public abstract boolean isVerticalSymetryAllowed();

	/**
	 * Transform a point.
	 * 
	 * @param w the point to transform.
	 * @return the transformed point.
	 */
	public abstract Complex renderPoint(Complex w);
}
