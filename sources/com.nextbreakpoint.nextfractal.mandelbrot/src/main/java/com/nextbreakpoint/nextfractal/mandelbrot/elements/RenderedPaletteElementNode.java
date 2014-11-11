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
package com.nextbreakpoint.nextfractal.mandelbrot.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent;
import com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.tree.AttributeNode;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteElementNode extends AttributeNode {
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.RenderedPaletteElement");
	public static final String NODE_CLASS = "node.class.RenderedPaletteElement";
	private final ConfigElementListener listener;
	private final RenderedPaletteElement configElement;

	/**
	 * @param nodeId
	 * @param configElement
	 */
	public RenderedPaletteElementNode(final String nodeId, final RenderedPaletteElement configElement) {
		super(nodeId);
		this.configElement = configElement;
		setNodeLabel(RenderedPaletteElementNode.NODE_LABEL);
		setNodeClass(RenderedPaletteElementNode.NODE_CLASS);
		listener = new ConfigElementListener();
		setNodeValue(new RenderedPaletteElementNodeValue(configElement.getValue()));
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
		setNodeValue(new RenderedPaletteElementNodeValue(configElement.getValue()));
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
		if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
			return ((RenderedPalette) getNodeValue().getValue()).getName();
		}
		return "";
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new PaletteNodeEditor(this);
	}

	protected class PaletteNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public PaletteNodeEditor(final NodeObject node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#doSetValue(java.lang.NodeValue)
		 */
		@Override
		protected void doSetValue(final NodeValue<?> value) {
			configElement.removeChangeListener(listener);
			configElement.setValue(((RenderedPaletteElementNodeValue) value).getValue());
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
			return RenderedPaletteElementNodeValue.class;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new RenderedPaletteElementNodeValue((RenderedPalette) value);
		}
	}

	protected class ConfigElementListener implements ElementChangeListener {
		@Override
		public void valueChanged(final ElementChangeEvent e) {
			cancel();
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					setNodeValue(new RenderedPaletteElementNodeValue((RenderedPalette) e.getParams()[0]));
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
