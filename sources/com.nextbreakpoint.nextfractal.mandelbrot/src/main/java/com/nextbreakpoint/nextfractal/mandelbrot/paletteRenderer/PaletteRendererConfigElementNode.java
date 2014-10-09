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
package com.nextbreakpoint.nextfractal.mandelbrot.paletteRenderer;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.paletteRenderer.PaletteRendererExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererConfigElementNode extends AbstractConfigElementNode<PaletteRendererConfigElement> {
	public static final String NODE_ID = PaletteRendererConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.PaletteRendererElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.PaletteRendererElement");
	private final PaletteRendererConfigElement rendererElement;

	/**
	 * @param rendererElement
	 */
	public PaletteRendererConfigElementNode(final PaletteRendererConfigElement rendererElement) {
		super(PaletteRendererConfigElementNode.NODE_ID);
		if (rendererElement == null) {
			throw new IllegalArgumentException("rendererElement is null");
		}
		this.rendererElement = rendererElement;
		setNodeLabel(PaletteRendererConfigElementNode.NODE_LABEL);
		setNodeClass(PaletteRendererConfigElementNode.NODE_CLASS);
		setNodeValue(new PaletteRendererConfigElementNodeValue(rendererElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public PaletteRendererConfigElement getConfigElement() {
		return rendererElement;
	}

	protected void createChildNodes(final PaletteRendererConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new PaletteRendererReferenceNode(PaletteRendererConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class PaletteRendererReferenceNode extends ConfigurableExtensionReferenceElementNode<PaletteRendererExtensionConfig> {
		public static final String NODE_CLASS = "node.class.PaletteRendererReference";

		/**
		 * @param nodeId
		 * @param rendererElement
		 */
		public PaletteRendererReferenceNode(final String nodeId, final PaletteRendererConfigElement rendererElement) {
			super(nodeId, rendererElement.getExtensionElement());
			setNodeClass(PaletteRendererReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<PaletteRendererExtensionConfig> value) {
			// return new PaletteRendererExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new PaletteRendererExtensionReferenceNodeValue(value);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((PaletteRendererConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((PaletteRendererConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new RendererNodeEditor(this);
	}

	private static class RendererNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public RendererNodeEditor(final Node node) {
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
			// return new PaletteRendererConfigElementNodeValue((PaletteRendererConfigElement) value != null ? ((PaletteRendererConfigElement) value).clone() : null);
			return new PaletteRendererConfigElementNodeValue((PaletteRendererConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return PaletteRendererConfigElementNodeValue.class;
		}
	}
}
