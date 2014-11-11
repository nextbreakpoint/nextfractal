/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeResources;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeAdjustment.ShapeAdjustmentExtensionConfig;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public class ShapeAdjustmentConfigElementNode extends AbstractConfigElementNode<ShapeAdjustmentConfigElement> {
	private static final String NODE_ID = ShapeAdjustmentConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.ShapeAdjustmentElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.ShapeAdjustmentElement");
	private final ShapeAdjustmentConfigElement shapeAdjustment;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param shapeAdjustment the shapeAdjustment element.
	 */
	public ShapeAdjustmentConfigElementNode(final ShapeAdjustmentConfigElement shapeAdjustment) {
		super(ShapeAdjustmentConfigElementNode.NODE_ID);
		if (shapeAdjustment == null) {
			throw new IllegalArgumentException("shapeAdjustment is null");
		}
		this.shapeAdjustment = shapeAdjustment;
		setNodeLabel(ShapeAdjustmentConfigElementNode.NODE_LABEL);
		setNodeClass(ShapeAdjustmentConfigElementNode.NODE_CLASS);
		setNodeValue(new ShapeAdjustmentConfigElementNodeValue(shapeAdjustment));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ShapeAdjustmentConfigElementNode) {
			return (shapeAdjustment == ((ShapeAdjustmentConfigElementNode) o).shapeAdjustment);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ShapeAdjustmentConfigElement getConfigElement() {
		return shapeAdjustment;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#addDescription(java.lang.StringBuilder)
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
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ShapeAdjustmentConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.model.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ShapeAdjustmentNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final ShapeAdjustmentConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ExtensionElementNode(ShapeAdjustmentConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class ShapeAdjustmentNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ShapeAdjustmentNodeEditor(final NodeObject node) {
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
			return new ShapeAdjustmentConfigElementNodeValue((ShapeAdjustmentConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.model.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ShapeAdjustmentConfigElementNodeValue.class;
		}
	}

	private static class ExtensionElementNode extends ConfigurableExtensionReferenceElementNode<ShapeAdjustmentExtensionConfig> {
		public static final String NODE_CLASS = "node.class.ShapeAdjustmentReference";

		/**
		 * @param nodeId
		 * @param shapeAdjustment
		 */
		public ExtensionElementNode(final String nodeId, final ShapeAdjustmentConfigElement shapeAdjustment) {
			super(nodeId, shapeAdjustment.getExtensionElement());
			setNodeClass(ExtensionElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ShapeAdjustmentExtensionConfig> value) {
			return new ShapeAdjustmentExtensionReferenceNodeValue(value);
		}
	}
}
