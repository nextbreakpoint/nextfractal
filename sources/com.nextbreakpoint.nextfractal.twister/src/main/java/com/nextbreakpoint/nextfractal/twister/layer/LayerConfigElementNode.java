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
package com.nextbreakpoint.nextfractal.twister.layer;

import com.nextbreakpoint.nextfractal.core.common.StringElementNode;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.util.ConfigElementListNodeValue;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.util.LockedElementNode;
import com.nextbreakpoint.nextfractal.twister.util.OpacityElementNode;
import com.nextbreakpoint.nextfractal.twister.util.VisibleElementNode;

/**
 * @author Andrea Medeghini
 */
public abstract class LayerConfigElementNode<T extends LayerConfigElement> extends AbstractConfigElementNode<T> {
	private final T layerElement;

	/**
	 * @param layerElement
	 */
	protected LayerConfigElementNode(final String nodeId, final T layerElement) {
		super(nodeId);
		if (layerElement == null) {
			throw new IllegalArgumentException("layerElement is null");
		}
		this.layerElement = layerElement;
		this.createChildNodes();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public T getConfigElement() {
		return layerElement;
	}

	/**
	 * 
	 */
	protected void createChildNodes() {
		createAttributeNodes(this.layerElement);
		createFilterNodes(this.layerElement);
	}

	private void createFilterNodes(final T layerElement) {
		appendChildNode(new FilterListNode(layerElement));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object o) {
		if (o instanceof LayerConfigElementNode) {
			return (this.layerElement == ((LayerConfigElementNode) o).layerElement);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#isMutable()
	 */
	@Override
	public boolean isMutable() {
		return false;
	}

	/**
	 * @param layerElement
	 */
	protected void createAttributeNodes(final T layerElement) {
		appendChildNode(new LayerOpacityElementNode(layerElement));
		appendChildNode(new LayerLockedElementNode(layerElement));
		appendChildNode(new LayerVisibleElementNode(layerElement));
		appendChildNode(new LayerLabelElementNode(layerElement));
	}

	private class LayerLockedElementNode extends LockedElementNode {
		/**
		 * @param layerElement
		 */
		public LayerLockedElementNode(final T layerElement) {
			super(LayerConfigElementNode.this.getNodeId() + ".locked", layerElement.getLockedElement());
		}
	}

	private class LayerVisibleElementNode extends VisibleElementNode {
		/**
		 * @param layerElement
		 */
		public LayerVisibleElementNode(final T layerElement) {
			super(LayerConfigElementNode.this.getNodeId() + ".visible", layerElement.getVisibleElement());
		}
	}

	private class LayerOpacityElementNode extends OpacityElementNode {
		/**
		 * @param layerElement
		 */
		public LayerOpacityElementNode(final T layerElement) {
			super(LayerConfigElementNode.this.getNodeId() + ".opacity", layerElement.getOpacityElement());
		}
	}

	private class LayerLabelElementNode extends StringElementNode {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LabelElement");

		/**
		 * @param layerElement
		 */
		public LayerLabelElementNode(final T layerElement) {
			super(LayerConfigElementNode.this.getNodeId() + ".label", layerElement.getLabelElement());
			setNodeLabel(NODE_LABEL);
		}
	}

	private class FilterListNode extends AbstractConfigElementListNode<LayerFilterConfigElement> {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LayerFilterElementList");
		public static final String NODE_CLASS = "node.class.LayerFilterElementList";

		/**
		 * @param frameElement
		 */
		public FilterListNode(final T frameElement) {
			super(LayerConfigElementNode.this.getNodeId() + ".layers", frameElement.getFilterListElement());
			setNodeLabel(NODE_LABEL);
			setNodeClass(FilterListNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
		 */
		@Override
		protected AbstractConfigElementNode<LayerFilterConfigElement> createChildNode(final LayerFilterConfigElement value) {
			return new LayerFilterConfigElementNode(value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
		 */
		@Override
		public Class<?> getChildValueType() {
			return LayerFilterConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
		 */
		@Override
		public NodeValue<LayerFilterConfigElement> createNodeValue(final Object value) {
			// return new LayerFilterConfigElementNodeValue((LayerFilterConfigElement) value != null ? ((LayerFilterConfigElement) value).clone() : null);
			return new LayerFilterConfigElementNodeValue((LayerFilterConfigElement) value);
		}

		private class LayerFilterConfigElementNodeValue extends ConfigElementListNodeValue<LayerFilterConfigElement> {
			private static final long serialVersionUID = 1L;

			/**
			 * @param value
			 */
			public LayerFilterConfigElementNodeValue(final LayerFilterConfigElement value) {
				super(value);
			}
		}
	}
}
