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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ObjectMap<K extends ObjectKey, T extends ObjectMapEntry<K>> {
	private final Map<K, T> map = new HashMap<K, T>();

	/**
	 * @return
	 */
	public int size() {
		return map.size();
	}

	/**
	 * 
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * @param object
	 */
	public void putObject(final T object) {
		map.put(object.getKey(), object);
	}

	/**
	 * @param objectKey
	 * @return
	 */
	public T getObject(final K objectKey) {
		return map.get(objectKey);
	}

	/**
	 * @param objectKey
	 * @return
	 */
	public T removeObject(final K objectKey) {
		return map.remove(objectKey);
	}

	/**
	 * @return
	 */
	public Set<T> findObjects() {
		Set<T> set = new HashSet<T>();
		if (map.values() == null) {
			return set;
		}
		for (T object : map.values()) {
			set.add(object);
		}
		return set;
	}

	/**
	 * @param <E>
	 * @param extractor
	 * @return
	 */
	public <E> Set<E> findObjects(final ObjectExtractor<E, T> extractor) {
		Set<E> list = new HashSet<E>();
		if (map.values() == null) {
			return list;
		}
		for (T object : map.values()) {
			list.add(extractor.extract(object));
		}
		return list;
	}

	/**
	 * @param matcher
	 * @return
	 */
	public Set<T> findObjects(final ObjectMatcher<T> matcher) {
		Set<T> set = new HashSet<T>();
		if (map.values() == null) {
			return set;
		}
		for (T object : map.values()) {
			if (matcher.match(object)) {
				set.add(object);
			}
		}
		return set;
	}

	/**
	 * @param <E>
	 * @param matcher
	 * @param extractor
	 * @return
	 */
	public <E> Set<E> findObjects(final ObjectMatcher<T> matcher, final ObjectExtractor<E, T> extractor) {
		Set<E> set = new HashSet<E>();
		if (map.values() == null) {
			return set;
		}
		for (T object : map.values()) {
			if (matcher.match(object)) {
				set.add(extractor.extract(object));
			}
		}
		return set;
	}

	/**
	 * @param matcher
	 * @return
	 */
	public Set<T> findObjectsByKey(final ObjectKeyMatcher<K> matcher) {
		Set<T> set = new HashSet<T>();
		if (map.keySet() == null) {
			return set;
		}
		for (K key : map.keySet()) {
			if (matcher.match(key)) {
				set.add(getObject(key));
			}
		}
		return set;
	}
}
