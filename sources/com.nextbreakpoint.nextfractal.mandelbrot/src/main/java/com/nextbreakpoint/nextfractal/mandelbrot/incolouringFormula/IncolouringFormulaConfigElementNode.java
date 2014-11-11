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
package com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElementNode;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.elements.IntegerElementNode;
import com.nextbreakpoint.nextfractal.core.elements.StringElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterResources;
import com.nextbreakpoint.nextfractal.twister.util.OpacityElementNode;

/**
 * @author Andrea Medeghini
 */
public class IncolouringFormulaConfigElementNode extends AbstractConfigElementNode<IncolouringFormulaConfigElement> {
	public static final String NODE_ID = IncolouringFormulaConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.IncolouringFormulaElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.IncolouringFormulaElement");
	private final IncolouringFormulaConfigElement formulaElement;

	/**
	 * @param formulaElement
	 */
	public IncolouringFormulaConfigElementNode(final IncolouringFormulaConfigElement formulaElement) {
		super(IncolouringFormulaConfigElementNode.NODE_ID);
		if (formulaElement == null) {
			throw new IllegalArgumentException("formulaElement is null");
		}
		this.formulaElement = formulaElement;
		setNodeLabel(IncolouringFormulaConfigElementNode.NODE_LABEL);
		setNodeClass(IncolouringFormulaConfigElementNode.NODE_CLASS);
		setNodeValue(new IncolouringFormulaConfigElementNodeValue(formulaElement));
	}

	protected void createChildNodes(final IncolouringFormulaConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new IncolouringFormulaReferenceNode(IncolouringFormulaConfigElementNode.NODE_ID + ".extension", value.getValue()));
		appendChildNode(new IncolouringFormulaLockedNode(IncolouringFormulaConfigElementNode.NODE_ID + ".locked", value.getValue()));
		appendChildNode(new IncolouringFormulaEnabledNode(IncolouringFormulaConfigElementNode.NODE_ID + ".enabled", value.getValue()));
		appendChildNode(new IncolouringFormulaOpacityNode(IncolouringFormulaConfigElementNode.NODE_ID + ".opacity", value.getValue()));
		appendChildNode(new IncolouringFormulaIterationsNode(IncolouringFormulaConfigElementNode.NODE_ID + ".iterations", value.getValue()));
		appendChildNode(new IncolouringFormulaAutoIterationsNode(IncolouringFormulaConfigElementNode.NODE_ID + ".autoIterations", value.getValue()));
		appendChildNode(new IncolouringFormulaLabelNode(IncolouringFormulaConfigElementNode.NODE_ID + ".label", value.getValue()));
	}

	private static class IncolouringFormulaReferenceNode extends ConfigurableExtensionReferenceElementNode<IncolouringFormulaExtensionConfig> {
		public static final String NODE_CLASS = "node.class.IncolouringFormulaReference";

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaReferenceNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getExtensionElement());
			setNodeClass(IncolouringFormulaReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<IncolouringFormulaExtensionConfig> value) {
			// return new IncolouringFormulaExtensionReferenceNodeValue(value != null ? value.clone() : null);
			return new IncolouringFormulaExtensionReferenceNodeValue(value);
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
	// return ((IncolouringFormulaConfigElementNodeValue) getNodeValue()).getValue().getReference();
	// }
	// return null;
	// }
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#addDescription(java.lang.StringBuilder)
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
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public IncolouringFormulaConfigElement getConfigElement() {
		return formulaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((IncolouringFormulaConfigElementNodeValue) getNodeValue());
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
			// return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value != null ? ((IncolouringFormulaConfigElement) value).clone() : null);
			return new IncolouringFormulaConfigElementNodeValue((IncolouringFormulaConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return IncolouringFormulaConfigElementNodeValue.class;
		}
	}

	private static class IncolouringFormulaOpacityNode extends OpacityElementNode {
		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaOpacityNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getOpacityElement());
		}
	}

	private static class IncolouringFormulaIterationsNode extends IntegerElementNode {
		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaIterationsNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getIterationsElement());
		}
	}

	private static class IncolouringFormulaLockedNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.LockedElement");

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaLockedNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getLockedElement());
			setNodeLabel(IncolouringFormulaLockedNode.NODE_LABEL);
		}
	}

	private static class IncolouringFormulaEnabledNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.EnabledElement");

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaEnabledNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getEnabledElement());
			setNodeLabel(IncolouringFormulaEnabledNode.NODE_LABEL);
		}
	}

	private static class IncolouringFormulaAutoIterationsNode extends BooleanElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.AutoIterationsElement");

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaAutoIterationsNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getAutoIterationsElement());
			setNodeLabel(IncolouringFormulaAutoIterationsNode.NODE_LABEL);
		}
	}

	private static class IncolouringFormulaLabelNode extends StringElementNode {
		private static final String NODE_LABEL = TwisterResources.getInstance().getString("node.label.LabelElement");

		/**
		 * @param nodeId
		 * @param formulaElement
		 */
		public IncolouringFormulaLabelNode(final String nodeId, final IncolouringFormulaConfigElement formulaElement) {
			super(nodeId, formulaElement.getLabelElement());
			setNodeLabel(IncolouringFormulaLabelNode.NODE_LABEL);
		}
	}
}
