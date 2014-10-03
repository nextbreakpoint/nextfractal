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
package com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension;

import java.awt.Shape;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public abstract class OrbitTrapExtensionRuntime<T extends OrbitTrapExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * @param center
	 */
	public abstract void prepareForProcessing(DoubleVector2D center);

	/**
	 * @param cp
	 * @return
	 */
	public abstract boolean processPoint(RenderedPoint cp);

	/**
	 * @param cp
	 */
	public abstract void renderPoint(RenderedPoint cp);

	/**
	 * @param sx
	 * @param sy
	 * @param theta
	 * @return
	 */
	public abstract Shape renderOrbitTrap(double sx, double sy, double theta);
}
