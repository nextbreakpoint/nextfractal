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
package com.nextbreakpoint.nextfractal.core.objectmap;

/**
 * @author Andrea Medeghini
 */
public class StringObjectKey implements ObjectKey, Comparable<StringObjectKey> {
	private static final long serialVersionUID = 1L;
	private final String keyText;

	/**
	 * @param field
	 */
	public StringObjectKey(final String keyText) {
		if (keyText == null) {
			throw new IllegalArgumentException("keyText == null");
		}
		this.keyText = keyText;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object key) {
		if (key == null) {
			return false;
		}
		if (key == this) {
			return true;
		}
		return keyText.equals(((StringObjectKey) key).keyText);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return keyText.hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return keyText;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final StringObjectKey key) {
		return keyText.compareTo(key.keyText);
	}

	/**
	 * @return the keyText
	 */
	public String getKeyText() {
		return keyText;
	}
}
