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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.twister.util.InputHandler;

/**
 * @author Andrea Medeghini
 */
public interface MandelbrotInputHandler extends InputHandler {
	public static final int MODE_MANUAL = 0;
	public static final int MODE_AUTOMATIC = 1;
	public static final int DIRECTION_UNDEFINED = 0;
	public static final int DIRECTION_FORWARD = 1;
	public static final int DIRECTION_BACKWARD = 2;

	/**
	 * @param x
	 * @param y
	 */
	public void setLastMousePosition(int x, int y);

	/**
	 * @param x
	 * @param y
	 */
	public void setStartMousePosition(int x, int y);

	/**
	 * @return
	 */
	public double getNormalizedLastMousePositionX();

	/**
	 * @return
	 */
	public double getNormalizedLastMousePositionY();

	/**
	 * @return
	 */
	public double getNormalizedStartMousePositionX();

	/**
	 * @return
	 */
	public double getNormalizedStartMousePositionY();

	/**
	 * @param mode
	 */
	public void setZoomMode(int mode);

	/**
	 * @param mode
	 */
	public void setShiftMode(int mode);

	/**
	 * @param mode
	 */
	public void setRotationMode(int mode);

	/**
	 * @param direction
	 */
	public void setZoomDirection(int direction);

	/**
	 * @param direction
	 */
	public void setShiftDirection(int direction);

	/**
	 * @param direction
	 */
	public void setRotationDirection(int direction);

	/**
	 * @param value
	 */
	public void setZoomEnabled(boolean value);

	/**
	 * @param value
	 */
	public void setShiftEnabled(boolean value);

	/**
	 * @param value
	 */
	public void setRotationEnabled(boolean value);
}
