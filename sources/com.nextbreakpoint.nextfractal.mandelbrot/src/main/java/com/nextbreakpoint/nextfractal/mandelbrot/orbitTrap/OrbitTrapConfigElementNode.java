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
package com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap;

import com.nextbreakpoint.nextfractal.core.common.ComplexElementNode;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotResources;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.extension.OrbitTrapExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class OrbitTrapConfigElementNode extends AbstractConfigElementNode<OrbitTrapConfigElement> {
	public static final String NODE_ID = OrbitTrapConfigElement.CLASS_ID;
	public static final String NODE_CLASS = "node.class.OrbitTrapElement";
	private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.OrbitTrapElement");
	private final OrbitTrapConfigElement trapElement;

	/**
	 * @param trapElement
	 */
	public OrbitTrapConfigElementNode(final OrbitTrapConfigElement trapElement) {
		super(OrbitTrapConfigElementNode.NODE_ID);
		if (trapElement == null) {
			throw new IllegalArgumentException("trapElement is null");
		}
		this.trapElement = trapElement;
		setNodeLabel(OrbitTrapConfigElementNode.NODE_LABEL);
		setNodeClass(OrbitTrapConfigElementNode.NODE_CLASS);
		setNodeValue(new OrbitTrapConfigElementNodeValue(trapElement));
	}

	protected void createChildNodes(final OrbitTrapConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new OrbitTrapReferenceNode(OrbitTrapConfigElementNode.NODE_ID + ".extension", value.getValue()));
		appendChildNode(new OrbitTrapCenterNode(OrbitTrapConfigElementNode.NODE_ID + ".center", value.getValue()));
	}

	private static class OrbitTrapReferenceNode extends ConfigurableExtensionReferenceElementNode<OrbitTrapExtensionConfig> {
		public static final String NODE_CLASS = "node.class.OrbitTrapReference";

		/**
		 * @param nodeId
		 * @param trapElement
		 */
		public OrbitTrapReferenceNode(final String nodeId, final OrbitTrapConfigElement trapElement) {
			super(nodeId, trapElement.getExtensionElement());
			setNodeClass(OrbitTrapReferenceNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<OrbitTrapExtensionConfig> value) {
			return new OrbitTrapExtensionReferenceNodeValue(value);
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
	public OrbitTrapConfigElement getConfigElement() {
		return trapElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateChildNodes()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((OrbitTrapConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new TrapNodeEditor(this);
	}

	private static class TrapNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public TrapNodeEditor(final Node node) {
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
			return new OrbitTrapConfigElementNodeValue((OrbitTrapConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return OrbitTrapConfigElementNodeValue.class;
		}
	}

	private static class OrbitTrapCenterNode extends ComplexElementNode {
		private static final String NODE_LABEL = MandelbrotResources.getInstance().getString("node.label.CenterElement");

		/**
		 * @param nodeId
		 * @param trapElement
		 */
		public OrbitTrapCenterNode(final String nodeId, final OrbitTrapConfigElement trapElement) {
			super(nodeId, trapElement.getCenterElement());
			setNodeLabel(OrbitTrapCenterNode.NODE_LABEL);
		}
	}
}
