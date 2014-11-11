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
package com.nextbreakpoint.nextfractal.contextfree;

import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElementNode;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.common.SpeedElementNode;
import com.nextbreakpoint.nextfractal.twister.common.ViewElementNode;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeConfigNodeBuilder implements NodeBuilder {
	private final ContextFreeConfig config;

	/**
	 * Constructs a new tree builder.
	 * 
	 * @param config
	 *        the config.
	 */
	public ContextFreeConfigNodeBuilder(final ContextFreeConfig config) {
		this.config = config;
	}

	/**
	 * Creates the nodes.
	 * 
	 * @param parentNode
	 */
	@Override
	public void createNodes(final NodeObject parentNode) {
//		parentNode.appendChildNode(new ViewNode(config));
//		parentNode.appendChildNode(new SpeedNode(config));
		parentNode.appendChildNode(new CFDGElementNode(config.getCFDG()));
	}

	private static class SpeedNode extends SpeedElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.SpeedElement");

		/**
		 * @param config
		 */
		public SpeedNode(final ContextFreeConfig config) {
			super("attribute.speed", config.getSpeedElement());
			setNodeLabel(SpeedNode.NODE_LABEL);
		}
	}

	private static class ViewNode extends ViewElementNode {
		private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.ViewElement");

		/**
		 * @param config
		 */
		public ViewNode(final ContextFreeConfig config) {
			super("attribute.view", config.getViewElement());
			setNodeLabel(ViewNode.NODE_LABEL);
		}
	}

	private class CFDGElementNode extends CFDGConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public CFDGElementNode(final CFDGConfigElement imgeElement) {
			super(imgeElement);
			listener = new ConfigListener();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#isEditable()
		 */
		@Override
		public boolean isEditable() {
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#dispose()
		 */
		@Override
		public void dispose() {
			if (config.getCFDGSingleElement() != null) {
				config.getCFDGSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new CFDGConfigElementNodeValue(getConfigElement()));
			config.getCFDGSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			config.getCFDGSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new CFDGNodeEditor(this);
		}

		protected class CFDGNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public CFDGNodeEditor(final NodeObject node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				config.getCFDGSingleElement().removeChangeListener(listener);
				config.setCFDG(((CFDGConfigElementNodeValue) value).getValue());
				config.getCFDGSingleElement().addChangeListener(listener);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
			 */
			@Override
			protected NodeObject createChildNode(final NodeValue<?> value) {
				return null;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
			 */
			@Override
			public Class<?> getNodeValueType() {
				return CFDGConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new CFDGConfigElementNodeValue((CFDGConfigElement) value != null ? ((CFDGConfigElement) value).clone() : null);
				return new CFDGConfigElementNodeValue((CFDGConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			@Override
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new CFDGConfigElementNodeValue((CFDGConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((CFDGConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((CFDGConfigElement) e.getParams()[1]).clone() : null));
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
