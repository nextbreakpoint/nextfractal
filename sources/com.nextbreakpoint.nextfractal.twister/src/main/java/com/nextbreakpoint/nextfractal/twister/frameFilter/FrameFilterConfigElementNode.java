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
package com.nextbreakpoint.nextfractal.twister.frameFilter;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElementNode;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.elements.StringElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.frameFilter.FrameFilterExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class FrameFilterConfigElementNode extends AbstractConfigElementNode<FrameFilterConfigElement> {
	public static final String NODE_ID = FrameFilterConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.FrameFilterElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.FrameFilterElement");
	protected FrameFilterConfigElement filterElement;

	/**
	 * Constructs a new filter node.
	 * 
	 * @param filterElement the filter element.
	 */
	public FrameFilterConfigElementNode(final FrameFilterConfigElement filterElement) {
		super(FrameFilterConfigElementNode.NODE_ID);
		if (filterElement == null) {
			throw new IllegalArgumentException("filterElement is null");
		}
		this.filterElement = filterElement;
		setNodeLabel(FrameFilterConfigElementNode.NODE_LABEL);
		setNodeClass(FrameFilterConfigElementNode.NODE_CLASS);
		setNodeValue(new FrameFilterConfigElementNodeValue(filterElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public FrameFilterConfigElement getConfigElement() {
		return filterElement;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof FrameFilterConfigElementNode) {
			return (filterElement == ((FrameFilterConfigElementNode) o).filterElement);
		}
		return false;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((FrameFilterConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#addDescription(java.lang.StringBuilder)
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
	protected void createChildNodes(final FrameFilterConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new FilterReferenceNode(FrameFilterConfigElementNode.NODE_ID + ".extension", value.getValue()));
		appendChildNode(new EnabledElementNode(FrameFilterConfigElementNode.NODE_ID + ".enabled", value.getValue()));
		appendChildNode(new LockedElementNode(FrameFilterConfigElementNode.NODE_ID + ".locked", value.getValue()));
		appendChildNode(new LabelElementNode(FrameFilterConfigElementNode.NODE_ID + ".label", value.getValue()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((FrameFilterConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new FrameFilterNodeEditor(this);
	}

	private static class FilterReferenceNode extends ConfigurableExtensionReferenceElementNode<FrameFilterExtensionConfig> {
		public static final String NODE_CLASS = "node.class.FrameFilterReference";

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public FilterReferenceNode(final String nodeId, final FrameFilterConfigElement filterElement) {
			super(nodeId, filterElement.getExtensionElement());
			setNodeClass(FilterReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<FrameFilterExtensionConfig> value) {
			// return new FrameFilterExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new FrameFilterExtensionReferenceNodeValue(value);
		}
	}

	private static class LockedElementNode extends BooleanElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LockedElement");

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public LockedElementNode(final String nodeId, final FrameFilterConfigElement filterElement) {
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
		public EnabledElementNode(final String nodeId, final FrameFilterConfigElement filterElement) {
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
		public LabelElementNode(final String nodeId, final FrameFilterConfigElement filterElement) {
			super(nodeId, filterElement.getLabelElement());
			setNodeLabel(LabelElementNode.NODE_LABEL);
		}
	}

	private static class FrameFilterNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public FrameFilterNodeEditor(final NodeObject node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue)
		 */
		@Override
		protected NodeObject createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new FrameFilterConfigElementNodeValue((FrameFilterConfigElement) value != null ? ((FrameFilterConfigElement) value).clone() : null);
			return new FrameFilterConfigElementNodeValue((FrameFilterConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return FrameFilterConfigElementNodeValue.class;
		}
	}
}
