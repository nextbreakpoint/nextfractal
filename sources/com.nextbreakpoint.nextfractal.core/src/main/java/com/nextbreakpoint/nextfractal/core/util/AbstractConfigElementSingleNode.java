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
package com.nextbreakpoint.nextfractal.core.util;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.SingleConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigElementSingleNode<T extends ConfigElement> extends DefaultNode {
	protected ConfigElementListener listListener;
	protected SingleConfigElement<T> singleElement;

	/**
	 * Constructs a new list node.
	 * 
	 * @param singleElement the frame element.
	 */
	public AbstractConfigElementSingleNode(final String nodeId, final SingleConfigElement<T> singleElement) {
		super(nodeId);
		if (singleElement == null) {
			throw new IllegalArgumentException("singleElement is null");
		}
		this.singleElement = singleElement;
		listListener = new ConfigElementListener();
	}

	/**
	 * @param value
	 * @return
	 */
	protected abstract AbstractConfigElementNode<T> createChildNode(T value);

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#dispose()
	 */
	@Override
	public void dispose() {
		if (singleElement != null) {
			singleElement.removeChangeListener(listListener);
		}
		singleElement = null;
		listListener = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#setSession(com.nextbreakpoint.nextfractal.core.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			singleElement.addChangeListener(listListener);
		}
		else {
			singleElement.removeChangeListener(listListener);
		}
		super.setSession(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		createChildNodes();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	protected void createChildNodes() {
		createConfigElementNodes(singleElement);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean equals(final Object o) {
		if (o instanceof AbstractConfigElementSingleNode) {
			return (singleElement == ((AbstractConfigElementSingleNode) o).singleElement);
		}
		return false;
	}

	/**
	 * Creates the nodes.
	 * 
	 * @param singleElement the frame element.
	 */
	protected void createConfigElementNodes(final SingleConfigElement<T> singleElement) {
		final NodeObject configElementNode = createChildNode(singleElement.getValue());
		appendChildNode(configElementNode);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ListNodeEditor();
	}

	/**
	 * @param value
	 * @return
	 */
	public abstract NodeValue<?> createNodeValue(final Object value);

	protected class ListNodeEditor extends NodeEditor {
		/**
		 * 
		 */
		public ListNodeEditor() {
			super(AbstractConfigElementSingleNode.this);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected NodeObject createChildNode(final NodeValue value) {
			final T configElement = ((NodeValue<T>) value).getValue();
			configElement.setContext(getContext());
			return AbstractConfigElementSingleNode.this.createChildNode(configElement);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ConfigElementListNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public NodeValue createNodeValue(final Object value) {
			return AbstractConfigElementSingleNode.this.createNodeValue(value);
		}
	}

	protected class ConfigElementListener implements ValueChangeListener {
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(createNodeValue(createChildNode((T) e.getParams()[0])));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0], e.getParams()[1]));
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
