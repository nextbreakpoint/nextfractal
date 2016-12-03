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

import com.nextbreakpoint.nextfractal.core.utils.Double2D;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;

public class MandelbrotMetadata {
	private final Double4D translation;
	private final Double4D rotation;
	private final Double4D scale;
	private final Double2D point;
	private final boolean julia;

	public MandelbrotMetadata(Double4D translation, Double4D rotation, Double4D scale, Double2D point, boolean julia) {
		this.translation = translation.clone();
		this.rotation = rotation.clone();
		this.scale = scale.clone();
		this.point = point.clone();
		this.julia = julia;
	}

	public MandelbrotMetadata(double[] translation, double[] rotation, double[] scale, double[] point, boolean julia) {
		this.translation = new Double4D(translation);
		this.rotation = new Double4D(rotation);
		this.scale = new Double4D(scale);
		this.point = new Double2D(point);
		this.julia = julia;
	}

	public MandelbrotMetadata(MandelbrotMetadata otherView) {
		this(otherView.getTranslation(), otherView.getRotation(), otherView.getScale(), otherView.getPoint(), otherView.isJulia());
	}

	public MandelbrotMetadata() {
		this(new double[4], new double[4], new double[4], new double[2], false);
	}

	public Double4D getTranslation() {
		return translation;
	}

	public Double4D getRotation() {
		return rotation;
	}

	public Double4D getScale() {
		return scale;
	}

	public Double2D getPoint() {
		return point;
	}

	public boolean isJulia() {
		return julia;
	}
	
	@Override
	public String toString() {
		return "[translation=" + translation + ", rotation=" + rotation + ", scale=" + scale + ", point=" + point + ", julia=" + julia + "]";
	}

	public MandelbrotMetadata butWithPoint(double[] point) {
		return new MandelbrotMetadata(translation, rotation, scale, new Double2D(point), julia);
	}

	public MandelbrotMetadata butWithJulia(boolean julia) {
		return new MandelbrotMetadata(translation, rotation, scale, point, julia);
	}
}
