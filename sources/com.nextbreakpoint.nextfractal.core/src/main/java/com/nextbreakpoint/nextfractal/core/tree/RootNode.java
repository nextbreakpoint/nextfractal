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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class RootNode extends Node {
	private List<NodeListener> listeners = new ArrayList<NodeListener>();

	/**
	 * Constructs a new root node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public RootNode(final String nodeId) {
		super(nodeId);
		setNodeClass("ROOT");
	}

	/**
	 * Constructs a new root node.
	 * 
	 * @param nodeId the nodeId.
	 * @param nodeLabel the nodeLabel.
	 */
	public RootNode(final String nodeId, final String nodeLabel) {
		super(nodeId);
		setNodeLabel(nodeLabel);
	}

	/**
	 * Adds a listener.
	 * 
	 * @param listener the listener to add.
	 */
	public void addNodeListener(final NodeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener.
	 * 
	 * @param listener the listener to remove.
	 */
	public void removeTreeListener(final NodeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param e the node event.
	 */
	@Override
	protected void fireNodeAdded(final NodeEvent e) {
		for (final NodeListener listener : listeners) {
			listener.nodeAdded(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	@Override
	protected void fireNodeRemoved(final NodeEvent e) {
		for (final NodeListener listener : listeners) {
			listener.nodeRemoved(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	@Override
	protected void fireNodeChanged(final NodeEvent e) {
		for (final NodeListener listener : listeners) {
			listener.nodeChanged(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	@Override
	protected void fireNodeAccepted(final NodeEvent e) {
		for (final NodeListener listener : listeners) {
			listener.nodeAccepted(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	@Override
	protected void fireNodeCancelled(final NodeEvent e) {
		for (final NodeListener listener : listeners) {
			listener.nodeCancelled(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#dispose()
	 */
	@Override
	public void dispose() {
		if (listeners != null) {
			listeners.clear();
			listeners = null;
		}
		super.dispose();
	}

	/**
	 * @return
	 */
	@Override
	public final RootNode getRootNode() {
		return this;
	}
}
