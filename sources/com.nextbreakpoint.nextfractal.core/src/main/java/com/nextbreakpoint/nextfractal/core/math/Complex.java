/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.core.math;

import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Library for complex operations.
 * 
 * @author Andrea Medeghini
 */
public class Complex implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static final Complex ur = new Complex(1.0, 0.0);
	/**
	 * 
	 */
	public static final Complex ui = new Complex(0.0, 1.0);
	/**
	 * 
	 */
	public double r = 0;
	/**
	 * 
	 */
	public double i = 0;

	/**
	 * 
	 */
	public Complex() {
	}

	/**
	 * @param r
	 * @param i
	 */
	public Complex(final double r, final double i) {
		this.r = r;
		this.i = i;
	}

	/**
	 * @param r
	 */
	public Complex(final double r) {
		this.r = r;
	}

	/**
	 * @param z
	 */
	public Complex(final Complex z) {
		r = z.r;
		i = z.i;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Complex add(final Complex a, final Complex b, final Complex c) {
		a.r = b.r + c.r;
		a.i = b.i + c.i;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Complex sub(final Complex a, final Complex b, final Complex c) {
		a.r = b.r - c.r;
		a.i = b.i - c.i;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Complex mul(final Complex a, final Complex b, final Complex c) {
		a.r = (b.r * c.r) - (b.i * c.i);
		a.i = (b.r * c.i) + (b.i * c.r);
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static Complex div(final Complex a, final Complex b, final Complex c) {
		return Complex.mul(a, b, Complex.inv(c));
	}

	/**
	 * @param a
	 * @param b
	 * @param s
	 * @return
	 */
	public static Complex mul(final Complex a, final Complex b, final double s) {
		a.r = b.r * s;
		a.i = b.i * s;
		return a;
	}

	/**
	 * @param a
	 * @param b
	 * @param s
	 * @return
	 */
	public static Complex div(final Complex a, final Complex b, final double s) {
		a.r = b.r / s;
		a.i = b.i / s;
		return a;
	}

	/**
	 * @param a
	 * @return
	 */
	public static Complex neg(final Complex a) {
		a.r = -a.r;
		a.i = -a.i;
		return a;
	}

	/**
	 * @param a
	 * @return
	 */
	public static Complex cng(final Complex a) {
		a.i = -a.i;
		return a;
	}

	/**
	 * @param a
	 * @return
	 */
	public static Complex inv(final Complex a) {
		final double m = 1 / ((a.r * a.r) + (a.i * a.i));
		a.r = +a.r * m;
		a.i = -a.i * m;
		return a;
	}

	/**
	 * @param a
	 * @return
	 */
	public static double mod(final Complex a) {
		return Math.sqrt((a.r * a.r) + (a.i * a.i));
	}

	/**
	 * @param a
	 * @return
	 */
	public static double arg(final Complex a) {
		return Math.atan2(a.i, a.r);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(r);
		builder.append(", ");
		builder.append(i);
		return builder.toString();
	}

	/**
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeDouble(r);
		out.writeDouble(i);
	}

	/**
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		r = in.readDouble();
		i = in.readDouble();
	}

	/**
	 * @param value
	 * @return
	 */
	public static Complex valueOf(final String value) {
		final StringTokenizer tkn = new StringTokenizer(value, ",");
		final String r = tkn.nextToken();
		final String i = tkn.nextToken();
		return new Complex(Double.valueOf(r), Double.valueOf(i));
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Complex clone() {
		return new Complex(r, i);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final Complex other = (Complex) obj;
		if (Double.doubleToLongBits(i) != Double.doubleToLongBits(other.i)) {
			return false;
		}
		if (Double.doubleToLongBits(r) != Double.doubleToLongBits(other.r)) {
			return false;
		}
		return true;
	}
}
