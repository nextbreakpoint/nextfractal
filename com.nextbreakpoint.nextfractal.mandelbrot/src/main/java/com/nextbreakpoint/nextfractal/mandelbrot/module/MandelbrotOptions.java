/*
 * NextFractal 2.2.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.module;

import com.nextbreakpoint.nextfractal.core.common.Double2D;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder(setterPrefix = "with", toBuilder = true)
public class MandelbrotOptions {
	private final boolean showPreview;
	private final boolean showTraps;
	private final boolean showOrbit;
	private final boolean showPoint;
	private final Double2D previewOrigin;
	private final Double2D previewSize;

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) return true;
//		if (o == null || getClass() != o.getClass()) return false;
//
//		MandelbrotOptions that = (MandelbrotOptions) o;
//
//		if (showPreview != that.showPreview) return false;
//		if (showTraps != that.showTraps) return false;
//		if (showOrbit != that.showOrbit) return false;
//		if (showPoint != that.showPoint) return false;
//		if (previewOrigin != null ? !previewOrigin.equals(that.previewOrigin) : that.previewOrigin != null) return false;
//		return previewSize != null ? previewSize.equals(that.previewSize) : that.previewSize == null;
//	}
//
//	@Override
//	public int hashCode() {
//		int result = (showPreview ? 1 : 0);
//		result = 31 * result + (showTraps ? 1 : 0);
//		result = 31 * result + (showOrbit ? 1 : 0);
//		result = 31 * result + (showPoint ? 1 : 0);
//		result = 31 * result + (previewOrigin != null ? previewOrigin.hashCode() : 0);
//		result = 31 * result + (previewSize != null ? previewSize.hashCode() : 0);
//		return result;
//	}

	public MandelbrotOptions() {
		this.showPreview = false;
		this.showTraps = false;
		this.showOrbit = false;
		this.showPoint = false;
		this.previewOrigin = new Double2D(0, 0);
		this.previewSize = new Double2D(0.25, 0.25);
	}

	public MandelbrotOptions(boolean showPreview, boolean showTraps, boolean showOrbit, boolean showPoint, Double2D previewOrigin, Double2D previewSize) {
		this.showPreview = showPreview;
		this.showTraps = showTraps;
		this.showOrbit = showOrbit;
		this.showPoint = showPoint;
		this.previewOrigin = previewOrigin;
		this.previewSize = previewSize;
	}

	public boolean isShowPreview() {
		return showPreview;
	}

	public boolean isShowTraps() {
		return showTraps;
	}

	public boolean isShowOrbit() {
		return showOrbit;
	}

	public boolean isShowPoint() {
		return showPoint;
	}

	public Double2D getPreviewOrigin() {
		return previewOrigin;
	}

	public Double2D getPreviewSize() {
		return previewSize;
	}

	@Override
	public String toString() {
		return "[showJulia=" + showPreview + ", showTraps=" + showTraps +	", showOrbit=" + showOrbit + ", showPoint=" + showPoint + ", previewOrigin=" + previewOrigin + ", previewSize=" + previewSize +	"]";
	}
}
