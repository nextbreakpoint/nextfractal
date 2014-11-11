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
package com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula;

import com.nextbreakpoint.nextfractal.core.elements.ExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;

/**
 * @author Andrea Medeghini
 */
public class PaletteRendererFormulaConfigElementNode extends AbstractConfigElementNode<PaletteRendererFormulaConfigElement> {
	public static final String NODE_ID = PaletteRendererFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.PaletteRendererFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.PaletteRendererFormulaElement");
	private final PaletteRendererFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public PaletteRendererFormulaConfigElementNode(final PaletteRendererFormulaConfigElement formulaElement) {
		super(PaletteRendererFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		if (formulaElement.getReference() != null) {
			setNodeValue(new PaletteRendererFormulaConfigElementNodeValue(formulaElement));
		}
		setNodeLabel(PaletteRendererFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(PaletteRendererFormulaConfigElementNode.NODE_CLASS);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public PaletteRendererFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((PaletteRendererFormulaConfigElementNodeValue) getNodeValue());
	}

	protected void createChildNodes(final PaletteRendererFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new PaletteRendererFormulaReferenceNode(PaletteRendererFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class PaletteRendererFormulaReferenceNode extends ExtensionReferenceElementNode {
		public static final String NODE_CLASS = "node.class.PaletteRendererFormulaReference";

		/**
		 * @param nodeId
		 * @param filterElement
		 */
		public PaletteRendererFormulaReferenceNode(final String nodeId, final PaletteRendererFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(PaletteRendererFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ExtensionReference value) {
			// return new PaletteRendererFormulaExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new PaletteRendererFormulaExtensionReferenceNodeValue(value);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	// private ExtensionReference getReference() {
	// if ((getNodeValue() != null) && (getNodeValue().getValue() != null)) {
	// return ((PaletteRendererFormulaConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @return the formulaElement
	 */
	public PaletteRendererFormulaConfigElement getFormulaElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
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
			// return new PaletteRendererFormulaConfigElementNodeValue((PaletteRendererFormulaConfigElement) value != null ? ((PaletteRendererFormulaConfigElement) value).clone() : null);
			return new PaletteRendererFormulaConfigElementNodeValue((PaletteRendererFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return PaletteRendererFormulaConfigElementNodeValue.class;
		}
	}
}
