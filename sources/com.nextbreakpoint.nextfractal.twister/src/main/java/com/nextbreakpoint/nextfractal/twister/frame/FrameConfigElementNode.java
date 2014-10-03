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
package com.nextbreakpoint.nextfractal.twister.frame;

import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.util.ConfigElementListNodeValue;
import com.nextbreakpoint.nextfractal.core.util.DefaultNodeEditor;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public class FrameConfigElementNode extends AbstractConfigElementNode<FrameConfigElement> {
	public static final String NODE_ID = FrameConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.FrameElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.FrameElement");
	protected FrameConfigElement frameElement;

	/**
	 * Constructs a new frame node.
	 * 
	 * @param frameElement the frame element.
	 */
	public FrameConfigElementNode(final FrameConfigElement frameElement) {
		super(FrameConfigElementNode.NODE_ID);
		setNodeClass(FrameConfigElementNode.NODE_CLASS);
		setNodeLabel(FrameConfigElementNode.NODE_LABEL);
		if (frameElement == null) {
			throw new IllegalArgumentException("frameElement is null");
		}
		this.frameElement = frameElement;
		setNodeValue(new FrameConfigElementNodeValue(frameElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public FrameConfigElement getConfigElement() {
		return frameElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((FrameConfigElementNodeValue) getNodeValue());
	}

	/**
	 * 
	 */
	protected void createChildNodes(final FrameConfigElementNodeValue value) {
		removeAllChildNodes();
		createLayerNodes(value.getValue());
		createFilterNodes(value.getValue());
		createAttributeNodes(value.getValue());
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof FrameConfigElementNode) {
			return (frameElement == ((FrameConfigElementNode) o).frameElement);
		}
		return false;
	}

	/**
	 * Creates layer nodes.
	 * 
	 * @param frameElement the frame element.
	 */
	protected void createLayerNodes(final FrameConfigElement frameElement) {
		appendChildNode(new LayerListNode(frameElement));
	}

	/**
	 * Creates filter nodes.
	 * 
	 * @param frameElement the frame element.
	 */
	protected void createFilterNodes(final FrameConfigElement frameElement) {
		appendChildNode(new FilterListNode(frameElement));
	}

	/**
	 * Creates attribute nodes.
	 * 
	 * @param frameElement the frame element.
	 */
	protected void createAttributeNodes(final FrameConfigElement frameElement) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new DefaultNodeEditor<FrameConfigElement>(this, FrameConfigElementNodeValue.class);
	}

	private class FilterListNode extends AbstractConfigElementListNode<FrameFilterConfigElement> {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.FrameFilterElementList");
		public static final String NODE_CLASS = "node.class.FrameFilterElementList";

		/**
		 * @param frameElement
		 */
		public FilterListNode(final FrameConfigElement frameElement) {
			super(FrameConfigElementNode.this.getNodeId() + ".filters", frameElement.getFilterListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(FilterListNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<FrameFilterConfigElement> createChildNode(final FrameFilterConfigElement value) {
			return new FrameFilterConfigElementNode(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return FrameFilterConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<FrameFilterConfigElement> createNodeValue(final Object value) {
			// return new FrameFilterConfigElementNodeValue((FrameFilterConfigElement) value != null ? ((FrameFilterConfigElement) value).clone() : null);
			return new FrameFilterConfigElementNodeValue((FrameFilterConfigElement) value);
		}

		private class FrameFilterConfigElementNodeValue extends ConfigElementListNodeValue<FrameFilterConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public FrameFilterConfigElementNodeValue(final FrameFilterConfigElement value) {
				super(value);
			}
		}
	}

	private class LayerListNode extends AbstractConfigElementListNode<GroupLayerConfigElement> {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.GroupLayerElementList");
		public static final String NODE_CLASS = "node.class.GroupLayerElementList";

		/**
		 * @param frameElement
		 */
		public LayerListNode(final FrameConfigElement frameElement) {
			super(FrameConfigElementNode.this.getNodeId() + ".layers", frameElement.getLayerListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(LayerListNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<GroupLayerConfigElement> createChildNode(final GroupLayerConfigElement value) {
			return new GroupLayerConfigElementNode(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return GroupLayerConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<GroupLayerConfigElement> createNodeValue(final Object value) {
			// return new GroupLayerConfigElementNodeValue((GroupLayerConfigElement) value != null ? ((GroupLayerConfigElement) value).clone() : null);
			return new GroupLayerConfigElementNodeValue((GroupLayerConfigElement) value);
		}

		private class GroupLayerConfigElementNodeValue extends ConfigElementListNodeValue<GroupLayerConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public GroupLayerConfigElementNodeValue(final GroupLayerConfigElement value) {
				super(value);
			}
		}
	}
}
