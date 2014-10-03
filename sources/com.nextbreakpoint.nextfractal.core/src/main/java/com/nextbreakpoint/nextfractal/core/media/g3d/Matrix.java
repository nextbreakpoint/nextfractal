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

public final class Matrix {
	public static final Matrix unit = new Matrix();
	float m00 = 1f;
	float m01 = 0f;
	float m02 = 0f;
	float m03 = 0f;
	float m10 = 0f;
	float m11 = 1f;
	float m12 = 0f;
	float m13 = 0f;
	float m20 = 0f;
	float m21 = 0f;
	float m22 = 1f;
	float m23 = 0f;
	float m30 = 0f;
	float m31 = 0f;
	float m32 = 0f;
	float m33 = 1f;

	public Matrix() {
	}

	public Matrix(final Matrix m) {
		m00 = m.m00;
		m10 = m.m10;
		m20 = m.m20;
		m30 = m.m30;
		m01 = m.m01;
		m11 = m.m11;
		m21 = m.m21;
		m31 = m.m31;
		m02 = m.m02;
		m12 = m.m12;
		m22 = m.m22;
		m32 = m.m32;
		m03 = m.m03;
		m13 = m.m13;
		m23 = m.m23;
		m33 = m.m33;
	}

	public Matrix(final Vector a, final Vector b, final Vector c) {
		m00 = a.x;
		m10 = a.y;
		m20 = a.z;
		m01 = b.x;
		m11 = b.y;
		m21 = b.z;
		m02 = c.x;
		m12 = c.y;
		m22 = c.z;
	}

	public Matrix(final Vector a, final Vector b, final Vector c, final Vector d) {
		m00 = a.x;
		m10 = a.y;
		m20 = a.z;
		m01 = b.x;
		m11 = b.y;
		m21 = b.z;
		m02 = c.x;
		m12 = c.y;
		m22 = c.z;
		m03 = d.x;
		m13 = d.y;
		m23 = d.z;
	}

	public static Matrix shiftMatrix(final float dx, final float dy, final float dz) {
		final Matrix m = new Matrix();
		m.m03 = dx;
		m.m13 = dy;
		m.m23 = dz;
		return (m);
	}

	public static Matrix scaleMatrix(final float dx, final float dy, final float dz) {
		final Matrix m = new Matrix();
		m.m00 = dx;
		m.m11 = dy;
		m.m22 = dz;
		return (m);
	}

	public static Matrix rotateMatrix(final float dx, final float dy, final float dz) {
		final Matrix q = new Matrix();
		if (dx != 0f) {
			final Matrix m = new Matrix();
			final float SIN = Math.sin(dx);
			final float COS = Math.cos(dx);
			m.m11 = +COS;
			m.m12 = +SIN;
			m.m21 = -SIN;
			m.m22 = +COS;
			q.transform(m, true);
		}
		if (dy != 0f) {
			final Matrix m = new Matrix();
			final float SIN = Math.sin(dy);
			final float COS = Math.cos(dy);
			m.m00 = +COS;
			m.m02 = +SIN;
			m.m20 = -SIN;
			m.m22 = +COS;
			q.transform(m, true);
		}
		if (dz != 0f) {
			final Matrix m = new Matrix();
			final float SIN = Math.sin(dz);
			final float COS = Math.cos(dz);
			m.m00 = +COS;
			m.m01 = +SIN;
			m.m10 = -SIN;
			m.m11 = +COS;
			q.transform(m, true);
		}
		return (q);
	}

	public void shift(final float dx, final float dy, final float dz) {
		transform(Matrix.shiftMatrix(dx, dy, dz), true);
	}

	public void scale(final float dx, final float dy, final float dz) {
		transform(Matrix.scaleMatrix(dx, dy, dz), true);
	}

	public void rotate(final float dx, final float dy, final float dz) {
		transform(Matrix.rotateMatrix(dx, dy, dz), true);
	}

	public void shiftSelf(final float dx, final float dy, final float dz) {
		transform(Matrix.shiftMatrix(dx, dy, dz), false);
	}

	public void scaleSelf(final float dx, final float dy, final float dz) {
		transform(Matrix.scaleMatrix(dx, dy, dz), false);
	}

	public void rotateSelf(final float dx, final float dy, final float dz) {
		transform(Matrix.rotateMatrix(dx, dy, dz), false);
	}

	public void transform(final Matrix n, final boolean left) {
		Matrix m = null;
		if (left) {
			m = Matrix.multiply(n, this);
		}
		else {
			m = Matrix.multiply(this, n);
		}
		m00 = m.m00;
		m01 = m.m01;
		m02 = m.m02;
		m03 = m.m03;
		m10 = m.m10;
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m20 = m.m20;
		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
	}

	public final void reset() {
		m00 = 1f;
		m01 = 0f;
		m02 = 0f;
		m03 = 0f;
		m10 = 0f;
		m11 = 1f;
		m12 = 0f;
		m13 = 0f;
		m20 = 0f;
		m21 = 0f;
		m22 = 1f;
		m23 = 0f;
		m30 = 0f;
		m31 = 0f;
		m32 = 0f;
		m33 = 1f;
	}

	public static Matrix multiply(final Matrix m1, final Matrix m2) {
		final Matrix m = new Matrix();
		m.m00 = (m1.m00 * m2.m00) + (m1.m01 * m2.m10) + (m1.m02 * m2.m20);
		m.m01 = (m1.m00 * m2.m01) + (m1.m01 * m2.m11) + (m1.m02 * m2.m21);
		m.m02 = (m1.m00 * m2.m02) + (m1.m01 * m2.m12) + (m1.m02 * m2.m22);
		m.m03 = (m1.m00 * m2.m03) + (m1.m01 * m2.m13) + (m1.m02 * m2.m23) + m1.m03;
		m.m10 = (m1.m10 * m2.m00) + (m1.m11 * m2.m10) + (m1.m12 * m2.m20);
		m.m11 = (m1.m10 * m2.m01) + (m1.m11 * m2.m11) + (m1.m12 * m2.m21);
		m.m12 = (m1.m10 * m2.m02) + (m1.m11 * m2.m12) + (m1.m12 * m2.m22);
		m.m13 = (m1.m10 * m2.m03) + (m1.m11 * m2.m13) + (m1.m12 * m2.m23) + m1.m13;
		m.m20 = (m1.m20 * m2.m00) + (m1.m21 * m2.m10) + (m1.m22 * m2.m20);
		m.m21 = (m1.m20 * m2.m01) + (m1.m21 * m2.m11) + (m1.m22 * m2.m21);
		m.m22 = (m1.m20 * m2.m02) + (m1.m21 * m2.m12) + (m1.m22 * m2.m22);
		m.m23 = (m1.m20 * m2.m03) + (m1.m21 * m2.m13) + (m1.m22 * m2.m23) + m1.m23;
		return (m);
	}

	@Override
	public String toString() {
		final StringBuffer s = new StringBuffer("<matrix:\r\n");
		s.append(m00 + "," + m01 + "," + m02 + "," + m03 + ",\r\n");
		s.append(m10 + "," + m11 + "," + m12 + "," + m13 + ",\r\n");
		s.append(m20 + "," + m21 + "," + m22 + "," + m23 + ",\r\n");
		s.append(m30 + "," + m31 + "," + m32 + "," + m33 + ">");
		return (s.toString());
	}
}
