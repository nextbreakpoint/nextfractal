/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

public abstract class Solid {
	Handler handler;
	Assembly parent;
	Matrix mp = new Matrix();
	Matrix mq = new Matrix();
	Matrix mr = new Matrix();
	Matrix ml = new Matrix();
	String name;
	public boolean hidden = false;

	public Solid(final String name) {
		this.name = name;
	}

	public final String getName() {
		return (name);
	}

	public final void rotate(final float dx, final float dy, final float dz) {
		mp.rotate(dx, dy, dz);
		mr.rotate(dx, dy, dz);
	}

	public final void shift(final float dx, final float dy, final float dz) {
		mp.shift(dx, dy, dz);
	}

	public final void rotateSelf(final float dx, final float dy, final float dz) {
		mp.rotateSelf(dx, dy, dz);
		mr.rotateSelf(dx, dy, dz);
	}

	public final void shiftSelf(final float dx, final float dy, final float dz) {
		mp.shiftSelf(dx, dy, dz);
	}

	public final void reset() {
		mp = new Matrix(mq);
		mr = new Matrix(ml);
	}

	public final void setHandler(final Handler handler) {
		this.handler = handler;
	}

	abstract void project(Matrix ml, Matrix mt, float vx, float vy, float vz);

	abstract public void transform(Matrix mt);

	abstract void rebuild();
}
