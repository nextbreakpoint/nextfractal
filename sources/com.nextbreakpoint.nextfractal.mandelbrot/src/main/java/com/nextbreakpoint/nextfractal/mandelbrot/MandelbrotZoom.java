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

/**
 * @author Andrea Medeghini
 */
public class MandelbrotZoom {
	public boolean isDynamic;
	public boolean zoomEnabled;
	public boolean shiftEnabled;
	public boolean rotationEnabled;
	public int zoomDirection;
	public int shiftDirection;
	public int rotationDirection;
	public double lastX;
	public double lastY;
	public double lastA;
	public double startX;
	public double startY;
	public double startA;
	public double offsetX;
	public double offsetY;
	public double offsetA;

	/**
	 * @param isDynamic
	 * @param zoomEnabled
	 * @param shiftEnabled
	 * @param rotationEnabled
	 * @param zoomDirection
	 * @param shiftDirection
	 * @param rotationDirection
	 * @param lastX
	 * @param lastY
	 * @param startX
	 * @param starty
	 */
	public MandelbrotZoom(final boolean isDynamic, final boolean zoomEnabled, final boolean shiftEnabled, final boolean rotationEnabled, final int zoomDirection, final int shiftDirection, final int rotationDirection, final double lastX, final double lastY, final double startX, final double startY) {
		this.isDynamic = isDynamic;
		this.zoomEnabled = zoomEnabled;
		this.shiftEnabled = shiftEnabled;
		this.rotationEnabled = rotationEnabled;
		this.zoomDirection = zoomDirection;
		this.shiftDirection = shiftDirection;
		this.rotationDirection = rotationDirection;
		this.lastX = lastX;
		this.lastY = lastY;
		this.startX = startX;
		this.startY = startY;
	}

	/**
	 * @param isDynamic
	 * @param zoomEnabled
	 * @param shiftEnabled
	 * @param rotationEnabled
	 * @param zoomDirection
	 * @param shiftDirection
	 * @param rotationDirection
	 * @param lastX
	 * @param lastY
	 * @param lastA
	 * @param startX
	 * @param startY
	 * @param startA
	 * @param offsetX
	 * @param offsetY
	 * @param offsetA
	 */
	public MandelbrotZoom(final boolean isDynamic, final boolean zoomEnabled, final boolean shiftEnabled, final boolean rotationEnabled, final int zoomDirection, final int shiftDirection, final int rotationDirection, final double lastX, final double lastY, final double lastA, final double startX, final double startY, final double startA, final double offsetX, final double offsetY, final double offsetA) {
		this.isDynamic = isDynamic;
		this.zoomEnabled = zoomEnabled;
		this.shiftEnabled = shiftEnabled;
		this.rotationEnabled = rotationEnabled;
		this.zoomDirection = zoomDirection;
		this.shiftDirection = shiftDirection;
		this.rotationDirection = rotationDirection;
		this.lastX = lastX;
		this.lastY = lastY;
		this.lastA = lastA;
		this.startX = startX;
		this.startY = startY;
		this.startA = startA;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetA = offsetA;
	}
}
