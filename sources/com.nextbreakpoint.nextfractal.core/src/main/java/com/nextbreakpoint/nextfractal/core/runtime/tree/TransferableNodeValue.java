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
package com.nextbreakpoint.nextfractal.core.runtime.tree;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class TransferableNodeValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Class<?> type;
	private final NodeValue<?> value;

	/**
	 * @param type
	 * @param value
	 */
	public TransferableNodeValue(final Class<?> type, final NodeValue<?> value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public NodeValue<?> getValue() {
		return value;
	}
}
