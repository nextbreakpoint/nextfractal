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
import com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ViewElementNode extends DefaultNode {
	public static final String NODE_CLASS = "node.class.ViewElement";
	private final ConfigElementListener listener;
	private final ValueConfigElement<View> configElement;

	/**
	 * @param nodeId
	 */
	public ViewElementNode(final String nodeId, final ValueConfigElement<View> configElement) {
		super(nodeId);
		setNodeClass(ViewElementNode.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		// appendChildNode(new StatusNode());
		// appendChildNode(new PositionNode());
		// appendChildNode(new RotationNode());
		setNodeValue(new ViewElementNodeValue(configElement.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isHighFrequency()
	 */
	@Override
	public boolean isHighFrequency() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#dispose()
	 */
	@Override
	public void dispose() {
		if (configElement != null) {
			configElement.removeChangeListener(listener);
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#setSession(com.nextbreakpoint.nextfractal.core.runtime.model.NodeSession)
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
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ViewNodeEditor(this);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#getValueAsString()
	 */
	@Override
	public String getValueAsString() {
		return "";
	}

	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#updateChildNodes()
	// */
	// @Override
	// protected void updateChildNodes() {
	// ((PositionNode) getChildNode(0)).update();
	// ((RotationNode) getChildNode(1)).update();
	// }
	protected class ViewNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ViewNodeEditor(final NodeObject node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#isRefreshRequired()
		 */
		@Override
		public boolean isRefreshRequired() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((ViewElementNodeValue) value).getValue());
			configElement.addChangeListener(listener);
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
		public Class<?> getNodeValueType() {
			return ViewElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new ViewElementNodeValue((View) value);
		}
	}

	protected class ConfigElementListener implements ElementChangeListener {
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new ViewElementNodeValue((View) e.getParams()[0]));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), false, getNodePath(), e.getParams()[0], e.getParams()[1]));
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	// private class PositionNode extends AttributeNode {
	// public static final String NODE_CLASS = "node.class.RotationElement";
	//
	// /**
	// * @param configElement
	// */
	// public PositionNode() {
	// super("attribute.position");
	// setNodeClass(PositionNode.NODE_CLASS);
	// setNodeLabel(TwisterResources.getInstance().getString("node.label.PositionElement"));
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
	// */
	// @Override
	// public boolean isEditable() {
	// return false;
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#getNodeValue()
	// */
	// @Override
	// public NodeValue<?> getNodeValue() {
	// return new ViewVector4DNodeValue(configElement.getValue().getPosition());
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#getValueAsString()
	// */
	// @Override
	// public String getValueAsString() {
	// if (configElement.getValue() != null) {
	// return String.valueOf(configElement.getValue().getPosition());
	// }
	// else {
	// return "";
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	// */
	// @Override
	// protected NodeEditor createNodeEditor() {
	// return new ViewVector4DNodeEditor(this);
	// }
	//
	// /**
	// *
	// */
	// public void update() {
	// fireNodeChanged();
	// }
	// }
	//
	// private class RotationNode extends AttributeNode {
	// public static final String NODE_CLASS = "node.class.RotationElement";
	//
	// /**
	// * @param configElement
	// */
	// public RotationNode() {
	// super("attribute.rotation");
	// setNodeClass(RotationNode.NODE_CLASS);
	// setNodeLabel(TwisterResources.getInstance().getString("node.label.RotationElement"));
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#isEditable()
	// */
	// @Override
	// public boolean isEditable() {
	// return false;
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#getNodeValue()
	// */
	// @Override
	// public NodeValue<?> getNodeValue() {
	// return new ViewVector4DNodeValue(configElement.getValue().getRotation());
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#getValueAsString()
	// */
	// @Override
	// public String getValueAsString() {
	// if (configElement.getValue() != null) {
	// return String.valueOf(configElement.getValue().getRotation());
	// }
	// else {
	// return "";
	// }
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	// */
	// @Override
	// protected NodeEditor createNodeEditor() {
	// return new ViewVector4DNodeEditor(this);
	// }
	//
	// /**
	// *
	// */
	// public void update() {
	// fireNodeChanged();
	// }
	// }
	// protected class ViewVector4DNodeEditor extends NodeEditor {
	// /**
	// * @param node
	// */
	// public ViewVector4DNodeEditor(final NodeObject node) {
	// super(node);
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	// */
	// @Override
	// protected NodeObject createChildNode(final NodeValue<?> value) {
	// return null;
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
	// */
	// @Override
	// public Class<?> getNodeValueType() {
	// return ViewVector4DNodeValue.class;
	// }
	//
	// /**
	// * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
	// */
	// @Override
	// public NodeValue<?> createNodeValue(final Object value) {
	// return new ViewVector4DNodeValue((DoubleVector4D) value);
	// }
	// }
}
