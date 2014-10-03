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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementNode;
import com.nextbreakpoint.nextfractal.core.common.ComplexElementNode;
import com.nextbreakpoint.nextfractal.core.common.IntegerElementNode;
import com.nextbreakpoint.nextfractal.core.common.RectangleElementNode;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.twister.common.SpeedElementNode;
import com.nextbreakpoint.nextfractal.twister.common.ViewElementNode;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotConfigNodeBuilder implements NodeBuilder {
	private final MandelbrotConfig config;

	/**
	 * Constructs a new tree builder.
	 * 
	 * @param config the config.
	 */
	public MandelbrotConfigNodeBuilder(final MandelbrotConfig config) {
		this.config = config;
	}

	/**
	 * Creates the nodes.
	 * 
	 * @param parentNode
	 */
	@Override
	public void createNodes(final Node parentNode) {
		final MandelbrotFractalConfigElement fractalElement = config.getMandelbrotFractal();
		final MandelbrotFractalElementNode fractalNode = new MandelbrotFractalElementNode(fractalElement);
		parentNode.appendChildNode(fractalNode);
		parentNode.appendChildNode(new ViewNode(config));
		parentNode.appendChildNode(new ImageModeNode(config));
		parentNode.appendChildNode(new InputModeNode(config));
		parentNode.appendChildNode(new ConstantNode(config));
		parentNode.appendChildNode(new ShowOrbitNode(config));
		parentNode.appendChildNode(new ShowPreviewNode(config));
		parentNode.appendChildNode(new PreviewAreaNode(config));
		parentNode.appendChildNode(new SpeedNode(config));
		parentNode.appendChildNode(new ShowOrbitTrapNode(config));
	}

	private static class SpeedNode extends SpeedElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.SpeedElement");

		/**
		 * @param config
		 */
		public SpeedNode(final MandelbrotConfig config) {
			super("attribute.speed", config.getSpeedElement());
			setNodeLabel(SpeedNode.NODE_LABEL);
		}
	}

	private static class ViewNode extends ViewElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ViewElement");

		/**
		 * @param config
		 */
		public ViewNode(final MandelbrotConfig config) {
			super("attribute.view", config.getViewElement());
			setNodeLabel(ViewNode.NODE_LABEL);
		}
	}

	private static class ImageModeNode extends IntegerElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ImageModeElement");
		private static final String NODE_CLASS = "node.class.ImageModeElement";

		/**
		 * @param config
		 */
		public ImageModeNode(final MandelbrotConfig config) {
			super("attribute.imageMode", config.getImageModeElement());
			setNodeLabel(ImageModeNode.NODE_LABEL);
			setNodeClass(ImageModeNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.IntegerElementNode#getValueAsString()
		 */
		@Override
		public String getValueAsString() {
			return ((Integer) getNodeValue().getValue() == 0) ? "Mandelbrot" : "Julia/Fatou";
		}
	}

	private static class InputModeNode extends IntegerElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.InputModeElement");
		private static final String NODE_CLASS = "node.class.InputModeElement";

		/**
		 * @param config
		 */
		public InputModeNode(final MandelbrotConfig config) {
			super("attribute.inputMode", config.getInputModeElement());
			setNodeLabel(InputModeNode.NODE_LABEL);
			setNodeClass(InputModeNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.IntegerElementNode#getValueAsString()
		 */
		@Override
		public String getValueAsString() {
			return ((Integer) getNodeValue().getValue() == 0) ? "Zoom" : "Select";
		}
	}

	private static class ConstantNode extends ComplexElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ConstantElement");

		/**
		 * @param config
		 */
		public ConstantNode(final MandelbrotConfig config) {
			super("attribute.constant", config.getConstantElement());
			setNodeLabel(ConstantNode.NODE_LABEL);
		}
	}

	private static class ShowOrbitNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ShowOrbitElement");

		/**
		 * @param config
		 */
		public ShowOrbitNode(final MandelbrotConfig config) {
			super("attribute.showOrbit", config.getShowOrbitElement());
			setNodeLabel(ShowOrbitNode.NODE_LABEL);
		}
	}

	private static class ShowOrbitTrapNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ShowOrbitTrapElement");

		/**
		 * @param config
		 */
		public ShowOrbitTrapNode(final MandelbrotConfig config) {
			super("attribute.showOrbitTrap", config.getShowOrbitTrapElement());
			setNodeLabel(ShowOrbitTrapNode.NODE_LABEL);
		}
	}

	private static class ShowPreviewNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ShowPreviewElement");

		/**
		 * @param config
		 */
		public ShowPreviewNode(final MandelbrotConfig config) {
			super("attribute.showPreview", config.getShowPreviewElement());
			setNodeLabel(ShowPreviewNode.NODE_LABEL);
		}
	}

	private static class PreviewAreaNode extends RectangleElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.PreviewAreaElement");

		/**
		 * @param config
		 */
		public PreviewAreaNode(final MandelbrotConfig config) {
			super("attribute.previewArea", config.getPreviewAreaElement());
			setNodeLabel(PreviewAreaNode.NODE_LABEL);
		}
	}

	private class MandelbrotFractalElementNode extends MandelbrotFractalConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public MandelbrotFractalElementNode(final MandelbrotFractalConfigElement imgeElement) {
			super(imgeElement);
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
			if (config.getFractalSingleElement() != null) {
				config.getFractalSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new MandelbrotFractalConfigElementNodeValue(getConfigElement()));
			config.getFractalSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			config.getFractalSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new MandelbrotFractalNodeEditor(this);
		}

		protected class MandelbrotFractalNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public MandelbrotFractalNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				config.getFractalSingleElement().removeChangeListener(listener);
				config.setMandelbrotFractal(((MandelbrotFractalConfigElementNodeValue) value).getValue());
				config.getFractalSingleElement().addChangeListener(listener);
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
				return MandelbrotFractalConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new MandelbrotFractalConfigElementNodeValue((MandelbrotFractalConfigElement) value != null ? ((MandelbrotFractalConfigElement) value).clone() : null);
				return new MandelbrotFractalConfigElementNodeValue((MandelbrotFractalConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			@Override
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new MandelbrotFractalConfigElementNodeValue((MandelbrotFractalConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((MandelbrotFractalConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((MandelbrotFractalConfigElement) e.getParams()[1]).clone() : null));
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
