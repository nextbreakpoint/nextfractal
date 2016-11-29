/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
	private final double[] translation;
	private final double[] rotation;
	private final double[] scale;
	private final double[] point;
	private final boolean julia;

	public MandelbrotView(double[] translation, double[] rotation, double[] scale, double[] point, boolean julia) {
		this.translation = translation.clone();
		this.rotation = rotation.clone();
		this.scale = scale.clone();
		this.point = point.clone();
		this.julia = julia;
	}

	public MandelbrotView(double[] translation, double[] rotation, double[] scale, boolean julia) {
		this(translation, rotation, scale, new double[] { 0, 0 }, julia);
	}

	public MandelbrotView(double[] translation, double[] rotation, double[] scale) {
		this(translation, rotation, scale, new double[] { 0, 0 }, false);
	}

	public MandelbrotView(MandelbrotView otherView) {
		this(otherView.getTranslation(), otherView.getRotation(), otherView.getScale(), otherView.getPoint(), otherView.isJulia());
	}

	public double[] getTranslation() {
		return translation;
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
		return "[translation=" + Arrays.toString(translation)	+ ", rotation=" + Arrays.toString(rotation) + ", scale=" + Arrays.toString(scale) + ", point=" + Arrays.toString(point) + ", julia=" + julia + "]";
	}

	public MandelbrotView butWithPoint(double[] point) {
		return new MandelbrotView(translation, rotation, scale, point, julia);
	}

	public MandelbrotView butWithJulia(boolean julia) {
		return new MandelbrotView(translation, rotation, scale, point, julia);
	}
}
