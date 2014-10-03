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
package com.nextbreakpoint.nextfractal.twister.layerFilter;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.common.StringElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.layerFilter.extension.LayerFilterExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class LayerFilterConfigElementNode extends AbstractConfigElementNode<LayerFilterConfigElement> {
	public static final String NODE_ID = LayerFilterConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.LayerFilterElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LayerFilterElement");
	private final LayerFilterConfigElement filterElement;

	/**
	 * Constructs a new filter node.
	 * 
	 * @param filterElement the filter element.
	 */
	public LayerFilterConfigElementNode(final LayerFilterConfigElement filterElement) {
		super(LayerFilterConfigElementNode.NODE_ID);
		if (filterElement == null) {
			throw new IllegalArgumentException("filterElement is null");
		}
		this.filterElement = filterElement;
		setNodeLabel(LayerFilterConfigElementNode.NODE_LABEL);
		setNodeClass(LayerFilterConfigElementNode.NODE_CLASS);
		setNodeValue(new LayerFilterConfigElementNodeValue(filterElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public LayerFilterConfigElement getConfigElement() {
		return filterElement;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof LayerFilterConfigElementNode) {
			return (filterElement == ((LayerFilterConfigElementNode) o).filterElement);
		}
		return false;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((LayerFilterConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
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
	 * @param value
	 */
	protected void createChildNodes(final LayerFilterConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new FilterReferenceNode(LayerFilterConfigElementNode.NODE_ID + ".extension", value.getValue()));
		appendChildNode(new EnabledElementNode(LayerFilterConfigElementNode.NODE_ID + ".enabled", value.getValue()));
		appendChildNode(new LockedElementNode(LayerFilterConfigElementNode.NODE_ID + ".locked", value.getValue()));
		appendChildNode(new LabelElementNode(LayerFilterConfigElementNode.NODE_ID + ".label", value.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((LayerFilterConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new LayerFilterNodeEditor(this);
	}

	private static class FilterReferenceNode extends ConfigurableExtensionReferenceElementNode<LayerFilterExtensionConfig> {
		public static final String NODE_CLASS = "node.class.LayerFilterReference";

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public FilterReferenceNode(final String nodeId, final LayerFilterConfigElement filterElement) {
			super(nodeId, filterElement.getExtensionElement());
			setNodeClass(FilterReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<LayerFilterExtensionConfig> value) {
			// return new LayerFilterExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new LayerFilterExtensionReferenceNodeValue(value);
		}
	}

	private static class LockedElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LockedElement");

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public LockedElementNode(final String nodeId, final LayerFilterConfigElement filterElement) {
			super(nodeId, filterElement.getLockedElement());
			setNodeLabel(LockedElementNode.NODE_LABEL);
		}
	}

	private static class EnabledElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.EnabledElement");

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public EnabledElementNode(final String nodeId, final LayerFilterConfigElement filterElement) {
			super(nodeId, filterElement.getEnabledElement());
			setNodeLabel(EnabledElementNode.NODE_LABEL);
		}
	}

	private static class LabelElementNode extends StringElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LabelElement");

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public LabelElementNode(final String nodeId, final LayerFilterConfigElement filterElement) {
			super(nodeId, filterElement.getLabelElement());
			setNodeLabel(LabelElementNode.NODE_LABEL);
		}
	}

	private static class LayerFilterNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public LayerFilterNodeEditor(final Node node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@Override
		protected Node createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new LayerFilterConfigElementNodeValue((LayerFilterConfigElement) value != null ? ((LayerFilterConfigElement) value).clone() : null);
			return new LayerFilterConfigElementNodeValue((LayerFilterConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return LayerFilterConfigElementNodeValue.class;
		}
	}
}
