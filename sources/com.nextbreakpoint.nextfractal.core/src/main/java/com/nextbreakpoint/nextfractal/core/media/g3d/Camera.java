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

public class Camera {
	Screen screen;
	Matrix mp;
	Matrix mq;
	Vector d;
	float vx = 0;
	float vy = 0;
	float vz = 0;
	String name;

	public Camera(final String name, final Screen screen, final float px, final float py, final float pz, final float dx, final float dy, final float dz, final float p, final float r) {
		if (name == null) {
			throw new IllegalArgumentException("illegal argument ! [name == null]");
		}
		if (screen == null) {
			throw new IllegalArgumentException("illegal argument ! [screen == null]");
		}
		this.name = name;
		this.screen = screen;
		mp = Matrix.shiftMatrix(px, py, pz);
		mp.rotate(dx, dy, dz);
		mq = new Matrix(mp);
		d = new Vector(0f, 0f, 1f);
		vx = screen.width >> 1;
		vy = -((screen.height >> 1) * r);
		vz = 1f / p;
	}

	public final String getName() {
		return (name);
	}

	public final Screen getScreen() {
		return (screen);
	}

	public final void rotate(final float dx, final float dy, final float dz) {
		mp.rotate(dx, dy, dz);
	}

	public final void shift(final float dx, final float dy, final float dz) {
		mp.shift(dx, dy, dz);
	}

	public final void rotateSelf(final float dx, final float dy, final float dz) {
		mp.rotateSelf(dx, dy, dz);
	}

	public final void shiftSelf(final float dx, final float dy, final float dz) {
		mp.shiftSelf(dx, dy, dz);
	}

	public final void reset() {
		mp = new Matrix(mq);
	}

	public final Vector getDirection() {
		return d.rotate(mp);
	}
}
