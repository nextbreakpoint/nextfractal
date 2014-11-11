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
package com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula;

import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;

/**
 * @author Andrea Medeghini
 */
public class ColorRendererFormulaConfigElementNode extends AbstractConfigElementNode<ColorRendererFormulaConfigElement> {
	public static final String NODE_ID = ColorRendererFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.ColorRendererFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ColorRendererFormulaElement");
	private final ColorRendererFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public ColorRendererFormulaConfigElementNode(final ColorRendererFormulaConfigElement formulaElement) {
		super(ColorRendererFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		if (formulaElement.getReference() != null) {
			setNodeValue(new ColorRendererFormulaConfigElementNodeValue(formulaElement));
		}
		setNodeLabel(ColorRendererFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(ColorRendererFormulaConfigElementNode.NODE_CLASS);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ColorRendererFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	protected void createChildNodes(final ColorRendererFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ColorRendererFormulaReferenceNode(ColorRendererFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class ColorRendererFormulaReferenceNode extends ExtensionReferenceElementNode {
		public static final String NODE_CLASS = "node.class.ColorRendererFormulaReference";

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public ColorRendererFormulaReferenceNode(final String nodeId, final ColorRendererFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(ColorRendererFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ExtensionReference value) {
			// return new ColorRendererFormulaExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new ColorRendererFormulaExtensionReferenceNodeValue(value);
		}
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((ColorRendererFormulaConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @return the formulaElement
	 */
	public ColorRendererFormulaConfigElement getFormulaElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ColorRendererFormulaConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new FormulaNodeEditor(this);
	}

	private static class FormulaNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public FormulaNodeEditor(final NodeObject node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
		 */
		@Override
		protected NodeObject createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			// return new ColorRendererFormulaConfigElementNodeValue((ColorRendererFormulaConfigElement) value != null ? ((ColorRendererFormulaConfigElement) value).clone() : null);
			return new ColorRendererFormulaConfigElementNodeValue((ColorRendererFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ColorRendererFormulaConfigElementNodeValue.class;
		}
	}
}
