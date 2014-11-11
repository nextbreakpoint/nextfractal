/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.elements;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeResources;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.tree.AttributeNode;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NumberNodeEditor;

/**
 * @author Andrea Medeghini
 */
public abstract class StrokeWidthElementNode extends AttributeNode {
	public static final String NODE_CLASS = "node.class.StrokeWidthElement";
	private final ConfigElementListener listener;
	private final StrokeWidthElement configElement;

	/**
	 * @param nodeId
	 * @param configElement
	 */
	public StrokeWidthElementNode(final String nodeId, final StrokeWidthElement configElement) {
		super(nodeId);
		setNodeClass(StrokeWidthElementNode.NODE_CLASS);
		this.configElement = configElement;
		listener = new ConfigElementListener();
		setNodeValue(new StrokeWidthElementNodeValue(configElement.getValue()));
		setNodeLabel(ContextFreeResources.getInstance().getString("node.label.StrokeWidthElement"));
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
		setNodeValue(new StrokeWidthElementNodeValue(configElement.getValue()));
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
		if (getNodeValue() != null) {
			return String.valueOf(getNodeValue().getValue());
		}
		else {
			return "";
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new StrokeWidthNodeEditor(this);
	}

	protected class StrokeWidthNodeEditor extends NumberNodeEditor {
		/**
		 * @param node
		 */
		public StrokeWidthNodeEditor(final AttributeNode node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NumberNodeEditor#getMaximum()
		 */
		@Override
		public Float getMaximum() {
			return configElement.getMaximum();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NumberNodeEditor#getMinimum()
		 */
		@Override
		public Float getMinimum() {
			return configElement.getMinimum();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NumberNodeEditor#getStep()
		 */
		@Override
		public Number getStep() {
			return configElement.getStep();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((StrokeWidthElementNodeValue) value).getValue());
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
			return StrokeWidthElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new StrokeWidthElementNodeValue((Float) value);
		}
	}

	protected class ConfigElementListener implements ElementChangeListener {
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new StrokeWidthElementNodeValue((Float) e.getParams()[0]));
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
