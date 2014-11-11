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

/**
 * @author Andrea Medeghini
 */
public abstract class DefaultNode extends NodeObject {
	/**
	 * Constructs a new node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public DefaultNode(final String nodeId) {
		super(nodeId);
	}

	/**
	 * Constructs a new node.
	 * 
	 * @param nodeId the nodeId.
	 * @param value the node value.
	 */
	public DefaultNode(final String nodeId, final NodeValue<?> value) {
		this(nodeId);
		setNodeValue(value);
		accept();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * Returns the node editor.
	 * 
	 * @return the node editor.
	 */
	@Override
	public NodeEditor getNodeEditor() {
		if (super.getNodeEditor() == null) {
			setNodeEditor(createNodeEditor());
		}
		return super.getNodeEditor();
	}

	/**
	 * Creates the node editor.
	 * 
	 * @return the node editor.
	 */
	protected abstract NodeEditor createNodeEditor();

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		return getNodeValue() != null ? getNodeValue().toString() : "";
	}
}
