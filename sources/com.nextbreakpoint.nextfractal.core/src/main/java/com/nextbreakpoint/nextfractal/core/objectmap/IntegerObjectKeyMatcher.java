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
public class IntegerObjectKeyMatcher implements ObjectKeyMatcher<IntegerObjectKey> {
	private final Integer[] fields;

	/**
	 * @param key
	 */
	public IntegerObjectKeyMatcher(final IntegerObjectKey key) {
		fields = new Integer[key.getKeyFieldCount()];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = key.getKeyField(i);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.objectmap.ObjectMatcher#match(java.lang.Object)
	 */
	public boolean match(final IntegerObjectKey key) {
		for (int i = 0; i < fields.length; i++) {
			Integer field = key.getKeyField(i);
			if (!field.equals(fields[i])) {
				return false;
			}
		}
		return true;
	}
}
