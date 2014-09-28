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
package com.nextbreakpoint.nextfractal.core.util;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * @author Andrea Medeghini
 */
public class DoubleVector2D implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private double x;
	private double y;

	/**
	 * 
	 */
	public DoubleVector2D() {
	}

	/**
	 * @param x
	 * @param y
	 */
	public DoubleVector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append(", ");
		builder.append(y);
		return builder.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	public static DoubleVector2D valueOf(final String value) {
		final StringTokenizer tkn = new StringTokenizer(value, ",");
		final String x = tkn.nextToken().trim();
		final String y = tkn.nextToken().trim();
		return new DoubleVector2D(Double.valueOf(x), Double.valueOf(y));
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
		final DoubleVector2D other = (DoubleVector2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DoubleVector2D clone() {
		return new DoubleVector2D(x, y);
	}
}
