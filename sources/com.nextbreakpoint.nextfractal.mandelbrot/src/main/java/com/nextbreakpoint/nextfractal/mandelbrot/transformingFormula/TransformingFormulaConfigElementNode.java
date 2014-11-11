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
package com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.transformingFormula.TransformingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class TransformingFormulaConfigElementNode extends AbstractConfigElementNode<TransformingFormulaConfigElement> {
	public static final String NODE_ID = TransformingFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.TransformingFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.TransformingFormulaElement");
	private final TransformingFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public TransformingFormulaConfigElementNode(final TransformingFormulaConfigElement formulaElement) {
		super(TransformingFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		setNodeLabel(TransformingFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(TransformingFormulaConfigElementNode.NODE_CLASS);
		setNodeValue(new TransformingFormulaConfigElementNodeValue(formulaElement));
	}

	protected void createChildNodes(final TransformingFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new TransformingFormulaReferenceNode(TransformingFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class TransformingFormulaReferenceNode extends ConfigurableExtensionReferenceElementNode<TransformingFormulaExtensionConfig> {
		public static final String NODE_CLASS = "node.class.TransformingFormulaReference";

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public TransformingFormulaReferenceNode(final String nodeId, final TransformingFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(TransformingFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<TransformingFormulaExtensionConfig> value) {
			// return new TransformingFormulaExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new TransformingFormulaExtensionReferenceNodeValue(value);
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
	// return ((TransformingFormulaConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public TransformingFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((TransformingFormulaConfigElementNodeValue) getNodeValue());
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
			// return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value != null ? ((TransformingFormulaConfigElement) value).clone() : null);
			return new TransformingFormulaConfigElementNodeValue((TransformingFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return TransformingFormulaConfigElementNodeValue.class;
		}
	}
}
