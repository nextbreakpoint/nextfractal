/*
 * NextFractal 1.0.5
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import java.util.Arrays;

public class MandelbrotView {
	private final double[] traslation;
	private final double[] rotation;
	private final double[] scale;
	private final double[] point;
	private final boolean julia;

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale, double[] point, boolean julia) {
		this.traslation = traslation;
		this.rotation = rotation;
		this.scale = scale;
		this.point = point;
		this.julia = julia;
	}

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale, boolean julia) {
		this(traslation, rotation, scale, new double[] { 0, 0 }, julia);
	}

	public MandelbrotView(double[] traslation, double[] rotation, double[] scale) {
		this(traslation, rotation, scale, new double[] { 0, 0 }, false);
	}
	
	public double[] getTraslation() {
		return traslation;
	}

	public double[] getRotation() {
		return rotation;
	}

	public double[] getScale() {
		return scale;
	}
	
	public boolean isJulia() {
		return julia;
	}
	
	public double[] getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "[traslation=" + Arrays.toString(traslation)	+ ", rotation=" + Arrays.toString(rotation) + ", scale=" + Arrays.toString(scale) + ", point=" + Arrays.toString(point) + ", julia=" + julia + "]";
	}
}
