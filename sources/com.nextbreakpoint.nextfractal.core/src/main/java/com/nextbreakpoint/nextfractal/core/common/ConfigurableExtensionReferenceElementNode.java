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
package com.nextbreakpoint.nextfractal.core.common;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.CoreResources;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public abstract class ConfigurableExtensionReferenceElementNode<T extends ExtensionConfig> extends DefaultNode {
	protected ConfigElementListener elementListener;
	protected ConfigurableExtensionReferenceElement<T> referenceElement;

	/**
	 * Constructs a new filter node.
	 * 
	 * @param nodeId
	 * @param referenceElement
	 */
	public ConfigurableExtensionReferenceElementNode(final String nodeId, final ConfigurableExtensionReferenceElement<T> referenceElement) {
		super(nodeId);
		if (referenceElement == null) {
			throw new IllegalArgumentException("referenceElement is null");
		}
		this.referenceElement = referenceElement;
		elementListener = new ConfigElementListener();
		setNodeValue(createNodeValue(referenceElement.getReference()));
		setNodeLabel(createNodeLabel());
		if (referenceElement.getReference() != null) {
			setExtensionId(referenceElement.getReference().getExtensionId());
		}
	}

	/**
	 * @param value
	 * @return
	 */
	protected abstract NodeValue<?> createNodeValue(ConfigurableExtensionReference<T> value);

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#dispose()
	 */
	@Override
	public void dispose() {
		if (referenceElement != null) {
			referenceElement.removeChangeListener(elementListener);
		}
		referenceElement = null;
		elementListener = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#setSession(com.nextbreakpoint.nextfractal.core.tree.NodeSession)
	 */
	@Override
	public void setSession(final NodeSession session) {
		if (session != null) {
			referenceElement.addChangeListener(elementListener);
		}
		else {
			referenceElement.removeChangeListener(elementListener);
		}
		super.setSession(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeAdded()
	 */
	@Override
	protected void nodeAdded() {
		setNodeValue(createNodeValue(referenceElement.getReference()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeRemoved()
	 */
	@Override
	protected void nodeRemoved() {
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ConfigurableExtensionReferenceElementNode) {
			return (referenceElement == ((ConfigurableExtensionReferenceElementNode) o).referenceElement);
		}
		return false;
	}

	/**
	 * @param value
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void createChildNodes(final NodeValue value) {
		removeAllChildNodes();
		if ((value != null) && (value.getValue() != null)) {
			try {
				final ExtensionConfig config = ((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>) value).getValue().getExtensionConfig();
				final Extension<NodeBuilderExtensionRuntime> nodeBuilderExtension = CoreRegistry.getInstance().getNodeBuilderExtension(config.getExtensionId());
				final NodeBuilder builder = nodeBuilderExtension.createExtensionRuntime().createNodeBuilder(config);
				builder.createNodes(this);
			}
			catch (final ExtensionException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ExtensionConfig getNodeValeAsExtensionConfig() {
		if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
			return ((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>) getNodeValue()).getValue().getExtensionConfig();
		}
		return null;
	}

	private String createNodeLabel() {
		if (getNodeValeAsExtensionConfig() != null) {
			return getNodeValeAsExtensionConfig().getExtensionReference().getExtensionName() + " extension";
		}
		else {
			return "Extension not defined";
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#addDescription(java.lang.StringBuilder)
	 */
	@Override
	protected void addDescription(final StringBuilder builder) {
		if (getChildNodeCount() > 0) {
			builder.append(getChildNode(0).getLabel());
		}
		else {
			super.addDescription(builder);
		}
	}

	/**
	 * @return the filterElement
	 */
	public ConfigurableExtensionReferenceElement<T> getReferenceElement() {
		return referenceElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateNode() {
		setNodeLabel(createNodeLabel());
		if (referenceElement.getReference() != null) {
			setExtensionId(referenceElement.getReference().getExtensionId());
		}
		super.updateNode();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes(getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ConfigElementNodeEditor(this);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#getValueAsString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getValueAsString() {
		return ((getNodeValue() != null) && (getNodeValue().getValue() != null)) ? ((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>) getNodeValue()).getValue().getExtensionName() : CoreResources.getInstance().getString("label.undefined");
	}

	protected class ConfigElementNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ConfigElementNodeEditor(final DefaultNode node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void doSetValue(final NodeValue value) {
			referenceElement.removeChangeListener(elementListener);
			if (((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>) value).getValue() != null) {
				ConfigurableExtensionReference<T> reference = ((ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>) value).getValue();
				// reference.getExtensionConfig().setContext(((ExtensionReferenceNodeValue<ConfigurableExtensionReference<T>>) value).getValue().getExtensionConfig().getContext());
				referenceElement.setReference(reference);
			}
			else {
				referenceElement.setReference(null);
			}
			referenceElement.addChangeListener(elementListener);
			setNodeLabel(ConfigurableExtensionReferenceElementNode.this.createNodeLabel());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(java.lang.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ExtensionReferenceElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return ConfigurableExtensionReferenceElementNode.this.createNodeValue((ConfigurableExtensionReference<T>) value);
		}
	}

	protected class ConfigElementListener implements ValueChangeListener {
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					setNodeValue(new ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<T>>((ConfigurableExtensionReference<T>) e.getParams()[0]));
					getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((ConfigurableExtensionReference<T>) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((ConfigurableExtensionReference<T>) e.getParams()[1]).clone() : null));
					break;
				}
				default: {
					break;
				}
			}
			setNodeLabel(ConfigurableExtensionReferenceElementNode.this.createNodeLabel());
		}
	}
}
