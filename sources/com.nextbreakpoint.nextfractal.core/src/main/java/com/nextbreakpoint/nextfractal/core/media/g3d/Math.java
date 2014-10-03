/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is based on code from idx3dIII
 * Copyright 1999, 2000 Peter Walser
 * http://www.idx3d.ch/idx3d/idx3d.html
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
package com.nextbreakpoint.nextfractal.core.media.g3d;

public final class Math {
	private static final float[] sin = new float[4096];
	private static final float[] cos = new float[4096];
	public final static float PI = 3.14159265f;
	private static final float rad2scale = 4096f / 3.14159265f / 2f;
	private static final float pad = 3.14159265f * 2f;
	private static boolean dirty = false;

	private Math() {
	}

	private static void build() {
		for (int i = 0; i < 4096; i++) {
			Math.sin[i] = (float) java.lang.Math.sin(i / Math.rad2scale);
			Math.cos[i] = (float) java.lang.Math.cos(i / Math.rad2scale);
		}
		Math.dirty = true;
	}

	public static final float sin(final float angle) {
		if (!Math.dirty) {
			Math.build();
		}
		return Math.sin[(int) ((angle + Math.pad) * Math.rad2scale) & 0xFFF];
	}

	public static final float cos(final float angle) {
		if (!Math.dirty) {
			Math.build();
		}
		return Math.cos[(int) ((angle + Math.pad) * Math.rad2scale) & 0xFFF];
	}

	public static final float deg2rad(final float deg) {
		return deg * 0.0174532925194f;
	}

	public static final float rad2deg(final float rad) {
		return rad * 57.295779514719f;
	}

	public static int float2fixedpoint(final float f) {
		return ((int) java.lang.Math.rint(f * 65536d));
	}

	public static float fixedpoint2float(final int i) {
		return (i / 65536f);
	}

	public static int crop(final int i, final int min, final int max) {
		return ((i < min) ? min : ((i > max) ? max : i));
	}

	public static float crop(final float i, final float min, final float max) {
		return ((i < min) ? min : ((i > max) ? max : i));
	}

	public static final boolean hit(final int i, final int min, final int max) {
		return ((i >= min) && (i < max));
	}

	public static final float random(final float min, final float max) {
		return (float) ((java.lang.Math.random() * (max - min)) + min);
	}
}
