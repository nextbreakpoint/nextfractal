/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.core;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author Andrea Medeghini
 */
public class DoubleVector4D implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private final double x;
	private final double y;
	private final double z;
	private final double w;

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public DoubleVector4D(final double x, final double y, final double z, final double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * @return the w
	 */
	public double getW() {
		return w;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append(", ");
		builder.append(y);
		builder.append(", ");
		builder.append(z);
		builder.append(", ");
		builder.append(w);
		return builder.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	public static DoubleVector4D valueOf(final String value) {
		final StringTokenizer tkn = new StringTokenizer(value, ",");
		final String x = tkn.nextToken().trim();
		final String y = tkn.nextToken().trim();
		final String z = tkn.nextToken().trim();
		final String w = tkn.nextToken().trim();
		return new DoubleVector4D(Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Double.valueOf(w));
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
		final DoubleVector4D other = (DoubleVector4D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DoubleVector4D clone() {
		return new DoubleVector4D(x, y, z, w);
	}
}
