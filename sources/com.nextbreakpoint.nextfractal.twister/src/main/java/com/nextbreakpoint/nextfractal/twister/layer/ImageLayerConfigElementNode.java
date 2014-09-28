/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class ImageLayerConfigElementNode extends LayerConfigElementNode<ImageLayerConfigElement> {
	public static final String NODE_ID = ImageLayerConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.ImageLayerElement";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.ImageLayerElement");

	/**
	 * Constructs a new layer node.
	 * 
	 * @param layerElement the layer element.
	 */
	public ImageLayerConfigElementNode(final ImageLayerConfigElement layerElement) {
		super(ImageLayerConfigElementNode.NODE_ID, layerElement);
		setNodeClass(ImageLayerConfigElementNode.NODE_CLASS);
		setNodeLabel(ImageLayerConfigElementNode.NODE_LABEL);
		setNodeValue(new ImageLayerConfigElementNodeValue(layerElement));
	}

	/**
	 * 
	 */
	@Override
	protected void createChildNodes() {
		createImageNode(getConfigElement());
		super.createChildNodes();
	}

	/**
	 * @param layerElement
	 */
	private void createImageNode(final ImageLayerConfigElement layerElement) {
		appendChildNode(new ImageElementNode(layerElement.getImageConfigElement()));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ImageLayerNodeEditor(this);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#addLabel(java.lang.StringBuilder)
	 */
	@Override
	protected void addLabel(final StringBuilder builder) {
		builder.append(ImageLayerConfigElementNode.NODE_LABEL);
	}

	private class ImageLayerNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ImageLayerNodeEditor(final Node node) {
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
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ImageLayerConfigElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new ImageLayerConfigElementNodeValue((ImageLayerConfigElement) value != null ? ((ImageLayerConfigElement) value).clone() : null);
			return new ImageLayerConfigElementNodeValue((ImageLayerConfigElement) value);
		}
	}

	private class ImageElementNode extends ImageConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param imageElement
		 */
		public ImageElementNode(final ImageConfigElement imageElement) {
			super(imageElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#dispose()
		 */
		@Override
		public void dispose() {
			if (ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement() != null) {
				ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new ImageConfigElementNodeValue(getConfigElement()));
			ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new ImageNodeEditor(this);
		}

		protected class ImageNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public ImageNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement().removeChangeListener(listener);
				ImageLayerConfigElementNode.this.getConfigElement().setImageConfigElement(((ImageConfigElementNodeValue) value).getValue());
				ImageLayerConfigElementNode.this.getConfigElement().getImageSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
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
				return ImageConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new ImageConfigElementNodeValue((ImageConfigElement) value != null ? ((ImageConfigElement) value).clone() : null);
				return new ImageConfigElementNodeValue((ImageConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new ImageConfigElementNodeValue((ImageConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((ImageConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((ImageConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}
}
