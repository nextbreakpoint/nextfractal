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

import java.util.Hashtable;

/**
 * Interface of registries.
 * 
 * @author Andrea Medeghini
 * @param <T> the registry elements type.
 */
public class Registry<T> {
	private final Hashtable<String, T> registry = new Hashtable<String, T>();

	/**
	 * Puts an object into the registry.
	 * 
	 * @param objectId the objectId.
	 * @param object the object.
	 */
	public void put(final String objectId, final T object) {
		this.registry.put(objectId, object);
	}

	/**
	 * Gets an object by its objectId.
	 * 
	 * @param objectId the objectId.
	 * @return the object.
	 */
	public T get(final String objectId) {
		return this.registry.get(objectId);
	}

	/**
	 * Checks if the registry contains a given objectId.
	 * 
	 * @param objectId the objectId.
	 * @return true if the registry contains the objectId.
	 */
	public boolean contains(final String objectId) {
		return this.registry.containsKey(objectId);
	}

	/**
	 * 
	 */
	public void clear() {
		this.registry.clear();
	}
}
