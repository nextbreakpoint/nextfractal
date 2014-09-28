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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class DefaultNodeEditor<T extends Serializable> extends NodeEditor {
	private final Class<? extends NodeValue<T>> nodeClass;

	/**
	 * @param node
	 * @param nodeClass
	 */
	public DefaultNodeEditor(final Node node, final Class<? extends NodeValue<T>> nodeClass) {
		super(node);
		if (nodeClass == null) {
			throw new IllegalArgumentException("nodeClass is null");
		}
		this.nodeClass = nodeClass;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
	 */
	@Override
	protected Node createChildNode(final NodeValue<?> value) {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
	 */
	@Override
	public Class<? extends NodeValue<T>> getNodeValueType() {
		return nodeClass;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
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
