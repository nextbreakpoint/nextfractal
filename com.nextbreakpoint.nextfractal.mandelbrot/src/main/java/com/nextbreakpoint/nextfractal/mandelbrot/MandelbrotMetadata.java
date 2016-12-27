/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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
	private final MandelbrotOptions options;

	public MandelbrotMetadata() {
		this(new Double4D(0,0,1,0), new Double4D(0,0,0,0), new Double4D(1,1,1,1), new Double2D(0, 0), false, new MandelbrotOptions());
	}

	public MandelbrotMetadata(Double4D translation, Double4D rotation, Double4D scale, Double2D point, boolean julia, MandelbrotOptions options) {
		this.translation = translation.clone();
		this.rotation = rotation.clone();
		this.scale = scale.clone();
		this.point = point.clone();
		this.julia = julia;
		this.options = options;
	}

	public MandelbrotMetadata(double[] translation, double[] rotation, double[] scale, double[] point, boolean julia, MandelbrotOptions options) {
		this(new Double4D(translation), new Double4D(rotation), new Double4D(scale), new Double2D(point), julia, options);
	}

	public MandelbrotMetadata(Double[] translation, Double[] rotation, Double[] scale, Double[] point, boolean julia, MandelbrotOptions options) {
		this(new Double4D(translation), new Double4D(rotation), new Double4D(scale), new Double2D(point), julia, options);
	}

	public MandelbrotMetadata(MandelbrotMetadata other) {
		this(other.getTranslation(), other.getRotation(), other.getScale(), other.getPoint(), other.isJulia(), other.getOptions());
	}

	public MandelbrotMetadata(MandelbrotMetadata other, MandelbrotOptions options) {
		this(other.getTranslation(), other.getRotation(), other.getScale(), other.getPoint(), other.isJulia(), options);
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

	public MandelbrotOptions getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "[translation=" + translation + ", rotation=" + rotation + ", scale=" + scale + ", point=" + point + ", julia=" + julia + ", options=" + options + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MandelbrotMetadata that = (MandelbrotMetadata) o;

		if (julia != that.julia) return false;
		if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;
		if (rotation != null ? !rotation.equals(that.rotation) : that.rotation != null) return false;
		if (scale != null ? !scale.equals(that.scale) : that.scale != null) return false;
		if (point != null ? !point.equals(that.point) : that.point != null) return false;
		return options != null ? options.equals(that.options) : that.options == null;
	}

	@Override
	public int hashCode() {
		int result = translation != null ? translation.hashCode() : 0;
		result = 31 * result + (rotation != null ? rotation.hashCode() : 0);
		result = 31 * result + (scale != null ? scale.hashCode() : 0);
		result = 31 * result + (point != null ? point.hashCode() : 0);
		result = 31 * result + (julia ? 1 : 0);
		result = 31 * result + (options != null ? options.hashCode() : 0);
		return result;
	}
}
