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
package com.nextbreakpoint.nextfractal.core.tree;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 * @param <T>
 */
public abstract class NodeValue<T extends Serializable> implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private T value;

	/**
	 * @param value
	 */
	public NodeValue(final T value) {
		this.value = value;
	}

	/**
	 * @return the value.
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * @return the clone.
	 */
	@Override
	public abstract NodeValue<T> clone();

	/**
	 * @return the value clone.
	 */
	public abstract T getValueClone();

	/**
	 * @param value the value.
	 */
	protected void setValue(final T value) {
		this.value = value;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (NodeValue.class.isInstance(o)) {
			return this.value == ((NodeValue<?>) o).value;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("nodeValue = (");
		if (this.getValue() != null) {
			builder.append(this.getValue().toString());
		}
		builder.append(") ");
		return builder.toString();
	}
}
