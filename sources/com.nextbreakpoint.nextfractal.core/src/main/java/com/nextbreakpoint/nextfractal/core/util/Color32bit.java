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

/**
 * @author Andrea Medeghini
 */
public class Color32bit implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	public static final Color32bit BLACK = new Color32bit(0xFF000000);
	public static final Color32bit WHITE = new Color32bit(0xFFFFFFFF);
	private final int argb;

	/**
	 * @param argb
	 */
	public Color32bit(final int argb) {
		this.argb = argb;
	}

	/**
	 * @return
	 */
	public int getAlpha() {
		return Colors.getAlpha(argb);
	}

	/**
	 * @return
	 */
	public int getRed() {
		return Colors.getRed(argb);
	}

	/**
	 * @return
	 */
	public int getGreen() {
		return Colors.getGreen(argb);
	}

	/**
	 * @return
	 */
	public int getBlue() {
		return Colors.getBlue(argb);
	}

	/**
	 * @return
	 */
	public int getARGB() {
		return argb;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Color32bit(argb);
	}

	/**
	 * @param value
	 * @return
	 */
	public static final Color32bit valueOf(final String value) {
		return new Color32bit((int) Long.parseLong(value, 16));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != Color32bit.class) {
			return false;
		}
		return argb == ((Color32bit) obj).argb;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return argb;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toHexString(argb);
	}
}
