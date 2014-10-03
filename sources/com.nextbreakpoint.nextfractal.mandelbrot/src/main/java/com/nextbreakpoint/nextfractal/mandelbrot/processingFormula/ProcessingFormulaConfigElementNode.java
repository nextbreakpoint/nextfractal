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
package com.nextbreakpoint.nextfractal.mandelbrot.processingFormula;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;

/**
 * @author Andrea Medeghini
 */
public class ProcessingFormulaConfigElementNode extends AbstractConfigElementNode<ProcessingFormulaConfigElement> {
	public static final String NODE_ID = ProcessingFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.ProcessingFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.ProcessingFormulaElement");
	private final ProcessingFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public ProcessingFormulaConfigElementNode(final ProcessingFormulaConfigElement formulaElement) {
		super(ProcessingFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		setNodeLabel(ProcessingFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(ProcessingFormulaConfigElementNode.NODE_CLASS);
		setNodeValue(new ProcessingFormulaConfigElementNodeValue(formulaElement));
	}

	protected void createChildNodes(final ProcessingFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ProcessingFormulaReferenceNode(ProcessingFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class ProcessingFormulaReferenceNode extends ExtensionReferenceElementNode {
		public static final String NODE_CLASS = "node.class.ProcessingFormulaReference";

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public ProcessingFormulaReferenceNode(final String nodeId, final ProcessingFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(ProcessingFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ExtensionReference value) {
			return new ProcessingFormulaExtensionReferenceNodeValue(value);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#addDescription(java.lang.StringBuilder)
	 */
	@Override
	protected void addDescription(final StringBuilder builder) {
		if (getChildNodeCount() > 0) {
			builder.append(getChildNode(0).getLabel());
		}
		else {
			super.addDescription(builder);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ProcessingFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ProcessingFormulaConfigElementNodeValue) getNodeValue());
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
			return new ProcessingFormulaConfigElementNodeValue((ProcessingFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ProcessingFormulaConfigElementNodeValue.class;
		}
	}
}
