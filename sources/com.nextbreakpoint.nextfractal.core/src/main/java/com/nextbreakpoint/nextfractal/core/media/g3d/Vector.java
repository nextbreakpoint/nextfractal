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

public class Vector {
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float r = 0;
	public float t = 0;

	public Vector() {
	}

	public Vector(final Vector v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public Vector(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	final void buildCylindric() {
		r = (float) java.lang.Math.sqrt((x * x) + (y * y));
		t = (float) java.lang.Math.atan2(x, y);
	}

	final void buildCartesian() {
		x = r * (float) java.lang.Math.cos(t);
		y = r * (float) java.lang.Math.sin(t);
	}

	public final Vector normalize() {
		final Vector v = new Vector(x, y, z);
		final float m = Vector.length(this);
		if (m == 0f) {
			return (v);
		}
		final float t = 1f / m;
		v.x = x * t;
		v.y = y * t;
		v.z = z * t;
		return (v);
	}

	public final Vector reverse() {
		return (new Vector(-x, -y, -z));
	}

	public final Vector transform(final Matrix m) {
		final float nx = (x * m.m00) + (y * m.m01) + (z * m.m02) + m.m03;
		final float ny = (x * m.m10) + (y * m.m11) + (z * m.m12) + m.m13;
		final float nz = (x * m.m20) + (y * m.m21) + (z * m.m22) + m.m23;
		return (new Vector(nx, ny, nz));
	}

	public final Vector rotate(final Matrix m) {
		final float nx = (x * m.m00) + (y * m.m01) + (z * m.m02);
		final float ny = (x * m.m10) + (y * m.m11) + (z * m.m12);
		final float nz = (x * m.m20) + (y * m.m21) + (z * m.m22);
		return (new Vector(nx, ny, nz));
	}

	public final static Vector normal(final Vector a, final Vector b) {
		return (Vector.product(a, b).normalize());
	}

	public final static Vector normal(final Vector a, final Vector b, final Vector c) {
		return (Vector.product(Vector.sub(a, c), Vector.sub(b, c)).normalize());
	}

	public final static Vector weightedNormal(final Vector a, final Vector b) {
		return (Vector.product(a, b));
	}

	public final static Vector weightedNormal(final Vector a, final Vector b, final Vector c) {
		return (Vector.product(Vector.sub(a, c), Vector.sub(b, c)));
	}

	public final static Vector product(final Vector a, final Vector b) {
		return (new Vector((a.y * b.z) - (b.y * a.z), (a.z * b.x) - (b.z * a.x), (a.x * b.y) - (b.x * a.y)));
	}

	public final static float length(final Vector v) {
		return ((float) java.lang.Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z)));
	}

	public final static float cos(final Vector a, final Vector b) {
		final Vector na = a.normalize();
		final Vector nb = b.normalize();
		return ((na.x * nb.x) + (na.y * nb.y) + (na.z * nb.z));
	}

	public final static Vector add(final Vector a, final Vector b) {
		return (new Vector(a.x + b.x, a.y + b.y, a.z + b.z));
	}

	public final static Vector sub(final Vector a, final Vector b) {
		return (new Vector(a.x - b.x, a.y - b.y, a.z - b.z));
	}

	public final static Vector mul(final Vector a, final float f) {
		return (new Vector(f * a.x, f * a.y, f * a.z));
	}

	public final boolean equals(final Vector v) {
		if ((x == v.x) && (y == v.y) && (z == v.z)) {
			return (true);
		}
		return (false);
	}

	public final boolean equals(final Vector v, final float tolerance) {
		if (java.lang.Math.abs(x - v.x) < tolerance) {
			if (java.lang.Math.abs(y - v.y) < tolerance) {
				if (java.lang.Math.abs(z - v.z) < tolerance) {
					return (true);
				}
			}
		}
		return (false);
	}

	@Override
	public String toString() {
		return new String("<vector: x =" + x + " y =" + y + " z =" + z + ">");
	}
}
