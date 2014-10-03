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
package com.nextbreakpoint.nextfractal.twister;

import com.nextbreakpoint.nextfractal.core.common.ColorElementNode;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElement;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.effect.EffectConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElement;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElementNode;
import com.nextbreakpoint.nextfractal.twister.frame.FrameConfigElementNodeValue;

/**
 * @author Andrea Medeghini
 */
public class TwisterConfigNode extends DefaultNode {
	public static final String NODE_ID = "node.twister.TwisterConfig";
	public static final String NODE_CLASS = "node.class.TwisterConfig";
	private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.TwisterConfig");
	protected TwisterConfig config;

	/**
	 * Constructs a new config node.
	 * 
	 * @param config the config.
	 */
	public TwisterConfigNode(final TwisterConfig config) {
		super(TwisterConfigNode.NODE_ID);
		setNodeClass(TwisterConfigNode.NODE_CLASS);
		setNodeLabel(TwisterConfigNode.NODE_LABEL);
		if (config == null) {
			throw new IllegalArgumentException("config is null");
		}
		this.config = config;
		createChildNodes();
	}

	/**
	 * 
	 */
	protected void createChildNodes() {
		appendChildNode(new FrameElementNode(config.getFrameConfigElement()));
		appendChildNode(new EffectElementNode(config.getEffectConfigElement()));
		appendChildNode(new BackgroundNode(config));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof TwisterConfigNode) {
			return (config == ((TwisterConfigNode) o).config);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return null;
	}

	private class BackgroundNode extends ColorElementNode {
		private final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.BackgroundElement");

		/**
		 * @param config
		 */
		public BackgroundNode(final TwisterConfig config) {
			super(TwisterConfigNode.this.getNodeId() + ".background", config.getBackgroundElement());
			setNodeLabel(NODE_LABEL);
		}
	}

	private class FrameElementNode extends FrameConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param frameElement
		 */
		public FrameElementNode(final FrameConfigElement frameElement) {
			super(frameElement);
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
			if (config.getFrameSingleElement() != null) {
				config.getFrameSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#setSession(com.nextbreakpoint.nextfractal.core.tree.NodeSession)
		 */
		@Override
		public void setSession(final NodeSession session) {
			if (session != null) {
				config.getFrameSingleElement().addChangeListener(listener);
			}
			else {
				config.getFrameSingleElement().removeChangeListener(listener);
			}
			super.setSession(session);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new FrameConfigElementNodeValue(config.getFrameConfigElement()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new FrameNodeEditor(this);
		}

		protected class FrameNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public FrameNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				config.getFrameSingleElement().removeChangeListener(listener);
				config.setFrameConfigElement(((FrameConfigElementNodeValue) value).getValue());
				config.getFrameSingleElement().addChangeListener(listener);
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
				return FrameConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new FrameConfigElementNodeValue((FrameConfigElement) value != null ? ((FrameConfigElement) value).clone() : null);
				return new FrameConfigElementNodeValue((FrameConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			@Override
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new FrameConfigElementNodeValue((FrameConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((FrameConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((FrameConfigElement) e.getParams()[1]).clone() : null));
						break;
					}
					default: {
						break;
					}
				}
			}
		}
	}

	private class EffectElementNode extends EffectConfigElementNode {
		private final ConfigListener listener;

		/**
		 * @param effectElement
		 */
		public EffectElementNode(final EffectConfigElement effectElement) {
			super(effectElement);
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
			if (config.getEffectSingleElement() != null) {
				config.getEffectSingleElement().removeChangeListener(listener);
			}
			super.dispose();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeAdded()
		 */
		@Override
		protected void nodeAdded() {
			setNodeValue(new EffectConfigElementNodeValue(config.getEffectConfigElement()));
			config.getEffectSingleElement().addChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.Node#nodeRemoved()
		 */
		@Override
		protected void nodeRemoved() {
			config.getEffectSingleElement().removeChangeListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
		 */
		@Override
		protected NodeEditor createNodeEditor() {
			return new EffectNodeEditor(this);
		}

		protected class EffectNodeEditor extends NodeEditor {
			/**
			 * @param node
			 */
			public EffectNodeEditor(final Node node) {
				super(node);
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#doSetValue(java.lang.NodeValue)
			 */
			@Override
			protected void doSetValue(final NodeValue<?> value) {
				config.getEffectSingleElement().removeChangeListener(listener);
				config.setEffectConfigElement(((EffectConfigElementNodeValue) value).getValue());
				config.getEffectSingleElement().addChangeListener(listener);
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
				return EffectConfigElementNodeValue.class;
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
			 */
			@Override
			public NodeValue<?> createNodeValue(final Object value) {
				// return new EffectConfigElementNodeValue((EffectConfigElement) value != null ? ((EffectConfigElement) value).clone() : null);
				return new EffectConfigElementNodeValue((EffectConfigElement) value);
			}
		}

		protected class ConfigListener implements ValueChangeListener {
			@Override
			public void valueChanged(final ValueChangeEvent e) {
				cancel();
				switch (e.getEventType()) {
					case ValueConfigElement.VALUE_CHANGED: {
						setNodeValue(new EffectConfigElementNodeValue((EffectConfigElement) e.getParams()[0]));
						getSession().appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, e.getTimestamp(), getNodePath(), e.getParams()[0] != null ? ((EffectConfigElement) e.getParams()[0]).clone() : null, e.getParams()[1] != null ? ((EffectConfigElement) e.getParams()[1]).clone() : null));
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
