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
package com.nextbreakpoint.nextfractal.core.objectmap;

/**
 * @author Andrea Medeghini
 */
public class IntegerObjectKey implements ObjectKey, Comparable<IntegerObjectKey> {
	private static final long serialVersionUID = 1L;
	private final String keyText;
	private final Integer[] keyFields;

	/**
	 * @param field
	 */
	public IntegerObjectKey(final Integer field) {
		this(new Integer[] { field });
	}

	/**
	 * @param fields
	 */
	public IntegerObjectKey(final Integer[] fields) {
		if (fields[0] == null) {
			throw new IllegalArgumentException("Invalid field 0");
		}
		StringBuilder builder = new StringBuilder();
		builder.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			if (fields[i] == null) {
				throw new IllegalArgumentException("Invalid field " + i);
			}
			builder.append("_");
			builder.append(Integer.toHexString(fields[i]).toUpperCase());
		}
		keyText = builder.toString();
		keyFields = fields;
	}

	/**
	 * @param i
	 * @return
	 */
	public Integer getKeyField(final int i) {
		return keyFields[i];
	}

	/**
	 * @return
	 */
	public int getKeyFieldCount() {
		return keyFields.length;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object key) {
		for (int i = keyFields.length - 1; i >= 0; i--) {
			if (keyFields[i].intValue() != ((IntegerObjectKey) key).keyFields[i].intValue()) {
				return false;
			}
		}
		return true;
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
	public int compareTo(final IntegerObjectKey key) {
		for (int i = 0; i < keyFields.length; i++) {
			if (keyFields[i].intValue() != key.keyFields[i].intValue()) {
				return keyFields[i].intValue() - key.keyFields[i].intValue();
			}
		}
		return 0;
	}
}
