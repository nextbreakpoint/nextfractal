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
public class Rectangle implements Serializable {
	private static final long serialVersionUID = 1L;
	private final double x;
	private final double y;
	private final double w;
	private final double h;

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Rectangle(final double x, final double y, final double w, final double h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * @return the h
	 */
	public double getH() {
		return h;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(x);
		builder.append(", ");
		builder.append(y);
		builder.append(", ");
		builder.append(w);
		builder.append(", ");
		builder.append(h);
		return builder.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	public static Rectangle parseRectangle(final String value) {
		final StringTokenizer tkn = new StringTokenizer(value, ",");
		final String x = tkn.nextToken().trim();
		final String y = tkn.nextToken().trim();
		final String w = tkn.nextToken().trim();
		final String h = tkn.nextToken().trim();
		return new Rectangle(Double.valueOf(x), Double.valueOf(y), Double.valueOf(w), Double.valueOf(h));
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
		final Rectangle other = (Rectangle) obj;
		if (Double.doubleToLongBits(h) != Double.doubleToLongBits(other.h)) {
			return false;
		}
		if (Double.doubleToLongBits(w) != Double.doubleToLongBits(other.w)) {
			return false;
		}
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}
}
