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
package com.nextbreakpoint.nextfractal.mandelbrot.colorRenderer;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaConfigElementNodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.colorRenderer.ColorRendererExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class ColorRendererConfigElementNode extends AbstractConfigElementNode<ColorRendererConfigElement> {
	public static final String NODE_ID = ColorRendererConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.ColorRendererElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ColorRendererElement");
	private final ColorRendererConfigElement rendererElement;

	/**
	 * @param rendererElement
	 */
	public ColorRendererConfigElementNode(final ColorRendererConfigElement rendererElement) {
		super(ColorRendererConfigElementNode.NODE_ID);
		if (rendererElement == null) {
			throw new IllegalArgumentException("rendererElement is null");
		}
		this.rendererElement = rendererElement;
		setNodeLabel(ColorRendererConfigElementNode.NODE_LABEL);
		setNodeClass(ColorRendererConfigElementNode.NODE_CLASS);
		setNodeValue(new ColorRendererConfigElementNodeValue(rendererElement));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ColorRendererConfigElement getConfigElement() {
		return rendererElement;
	}

	protected void createChildNodes(final ColorRendererConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ColorRendererReferenceNode(ColorRendererConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class ColorRendererReferenceNode extends ConfigurableExtensionReferenceElementNode<ColorRendererExtensionConfig> {
		public static final String NODE_CLASS = "node.class.ColorRendererReference";

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public ColorRendererReferenceNode(final String nodeId, final ColorRendererConfigElement rendererElement) {
			super(nodeId, rendererElement.getExtensionElement());
			setNodeClass(ColorRendererReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ColorRendererExtensionConfig> value) {
			// return new ColorRendererExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new ColorRendererExtensionReferenceNodeValue(value);
		}
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((ColorRendererConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @return the rendererElement
	 */
	public ColorRendererConfigElement getRendererElement() {
		return rendererElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ColorRendererConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new RendererNodeEditor(this);
	}

	private static class RendererNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public RendererNodeEditor(final NodeObject node) {
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
			// return new ColorRendererFormulaConfigElementNodeValue((ColorRendererFormulaConfigElement) value != null ? ((ColorRendererFormulaConfigElement) value).clone() : null);
			return new ColorRendererFormulaConfigElementNodeValue((ColorRendererFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ColorRendererFormulaConfigElementNodeValue.class;
		}
	}
}
