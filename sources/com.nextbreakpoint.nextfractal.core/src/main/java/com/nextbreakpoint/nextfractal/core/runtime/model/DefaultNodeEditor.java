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
package com.nextbreakpoint.nextfractal.core.runtime.model;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Andrea Medeghini
 */
public class DefaultNodeEditor<T extends Serializable> extends NodeEditor {
	private final Class<? extends NodeValue<T>> nodeClass;

	/**
	 * @param node
	 * @param nodeClass
	 */
	public DefaultNodeEditor(final NodeObject node, final Class<? extends NodeValue<T>> nodeClass) {
		super(node);
		if (nodeClass == null) {
			throw new IllegalArgumentException("nodeClass is null");
		}
		this.nodeClass = nodeClass;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	protected NodeObject createChildNode(final NodeValue<?> value) {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
	 */
	@Override
	public Class<? extends NodeValue<T>> getNodeValueType() {
		return nodeClass;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
	 */
	@Override
	public NodeValue<T> createNodeValue(final Object value) {
		try {
			final Constructor<? extends NodeValue<T>> constructor = nodeClass.getConstructor(new Class[] { value.getClass() });
			return constructor.newInstance(value);
		}
		catch (final InstantiationException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final SecurityException e) {
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
