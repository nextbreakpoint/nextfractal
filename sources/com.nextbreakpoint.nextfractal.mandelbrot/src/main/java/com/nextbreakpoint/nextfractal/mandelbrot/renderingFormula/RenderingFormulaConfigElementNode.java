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
package com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class RenderingFormulaConfigElementNode extends AbstractConfigElementNode<RenderingFormulaConfigElement> {
	public static final String NODE_ID = RenderingFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.RenderingFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.RenderingFormulaElement");
	private final RenderingFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public RenderingFormulaConfigElementNode(final RenderingFormulaConfigElement formulaElement) {
		super(RenderingFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		setNodeLabel(RenderingFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(RenderingFormulaConfigElementNode.NODE_CLASS);
		setNodeValue(new RenderingFormulaConfigElementNodeValue(formulaElement));
	}

	protected void createChildNodes(final RenderingFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new RenderingFormulaReferenceNode(RenderingFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class RenderingFormulaReferenceNode extends ConfigurableExtensionReferenceElementNode<RenderingFormulaExtensionConfig> {
		public static final String NODE_CLASS = "node.class.RenderingFormulaReference";

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public RenderingFormulaReferenceNode(final String nodeId, final RenderingFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(RenderingFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<RenderingFormulaExtensionConfig> value) {
			// return new RenderingFormulaExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new RenderingFormulaExtensionReferenceNodeValue(value);
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
	// return ((RenderingFormulaConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public RenderingFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((RenderingFormulaConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new FormulaNodeEditor(this);
	}

	private static class FormulaNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public FormulaNodeEditor(final Node node) {
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
			// return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value != null ? ((RenderingFormulaConfigElement) value).clone() : null);
			return new RenderingFormulaConfigElementNodeValue((RenderingFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return RenderingFormulaConfigElementNodeValue.class;
		}
	}
}
