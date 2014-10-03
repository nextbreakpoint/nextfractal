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

import java.util.EventObject;

/**
 * @author Andrea Medeghini
 */
public class NodeEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private final NodePath path;

	NodeEvent(final Node source, final NodePath path) {
		super(source);
		this.path = path;
	}

	/**
	 * Returns the node.
	 * 
	 * @return the node.
	 */
	public Node getNode() {
		return (Node) getSource();
	}

	/**
	 * Returns the path.
	 * 
	 * @return the path.
	 */
	public NodePath getPath() {
		return path;
	}

	/**
	 * @see java.util.EventObject#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("path = ");
		builder.append(path.toString());
		builder.append(", value = ");
		builder.append(getNode().getNodeValue());
		builder.append(", previousValue = ");
		builder.append(getNode().getPreviousNodeValue());
		return builder.toString();
	}
}
