/*
 * NextFractal 1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

/*
 * JAME 6.2
 * http://jame.sourceforge.net
 *
 * Copyright 2001, 2015 Andrea Medeghini
 *
 * This file is part of JAME.
 *
 * JAME is an application for creating fractals and other graphics artifacts.
 *
 * JAME is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JAME is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JAME.  If not, see <http://www.gnu.org/licenses/>.
 *
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
