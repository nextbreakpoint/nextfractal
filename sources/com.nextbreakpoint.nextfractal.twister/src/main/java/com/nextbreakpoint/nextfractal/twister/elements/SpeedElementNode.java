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
package com.nextbreakpoint.nextfractal.twister.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.util.Speed;

/**
 * @author Andrea Medeghini
 */
public class SpeedElementNode extends DefaultNode {
	public static final String NODE_CLASS = "node.class.SpeedElement";
	private final ConfigElementListener listener;
	private final ValueConfigElement<Speed> configElement;

	/**
	 * @param nodeId
	 */
	public SpeedElementNode(final String nodeId, final ValueConfigElement<Speed> configElement) {
		super(nodeId);
		setNodeClass(SpeedElementNode.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		setNodeValue(new SpeedElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#dispose()
	 */
	@Override
	public void dispose() {
		if (configElement != null) {
			configElement.removeChangeListener(listener);
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#setSession(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			configElement.addChangeListener(listener);
		}
		else {
			configElement.removeChangeListener(listener);
		}
		super.setSession(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		setNodeValue(new SpeedElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		// if (getNodeValue() != null) {
		// return ((SpeedElementNodeValue) getNodeValue()).getValue().toString();
		// }
		// else {
		return "";
		// }
	}

	/**
	 * @see SpeedElementNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new SpeedNodeEditor(this);
	}

	protected class SpeedNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public SpeedNodeEditor(final DefaultNode node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((SpeedElementNodeValue) value).getValue());
			configElement.addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue)
		 */
		@Override
		protected NodeObject createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return SpeedElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new SpeedElementNodeValue((Speed) value);
		}
	}

	protected class ConfigElementListener implements ElementChangeListener {
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new SpeedElementNodeValue((Speed) e.getParams()[0]));
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
