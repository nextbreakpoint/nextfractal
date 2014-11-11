/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeResources;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.shapeReplacement.ShapeReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public class ShapeReplacementConfigElementNode extends AbstractConfigElementNode<ShapeReplacementConfigElement> {
	private static final String NODE_ID = ShapeReplacementConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.ShapeReplacementElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.ShapeReplacementElement");
	private final ShapeReplacementConfigElement shapeReplacement;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param shapeReplacement the shapeReplacement element.
	 */
	public ShapeReplacementConfigElementNode(final ShapeReplacementConfigElement shapeReplacement) {
		super(ShapeReplacementConfigElementNode.NODE_ID);
		if (shapeReplacement == null) {
			throw new IllegalArgumentException("shapeReplacement is null");
		}
		this.shapeReplacement = shapeReplacement;
		setNodeLabel(ShapeReplacementConfigElementNode.NODE_LABEL);
		setNodeClass(ShapeReplacementConfigElementNode.NODE_CLASS);
		setNodeValue(new ShapeReplacementConfigElementNodeValue(shapeReplacement));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof ShapeReplacementConfigElementNode) {
			return (shapeReplacement == ((ShapeReplacementConfigElementNode) o).shapeReplacement);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public ShapeReplacementConfigElement getConfigElement() {
		return shapeReplacement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#addDescription(java.lang.StringBuilder)
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
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeObject#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((ShapeReplacementConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new ShapeReplacementNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final ShapeReplacementConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ExtensionElementNode(ShapeReplacementConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class ShapeReplacementNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public ShapeReplacementNodeEditor(final NodeObject node) {
			super(node);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createChildNode(com.nextbreakpoint.nextfractal.core.tree.NodeValue)
		 */
		@Override
		protected NodeObject createChildNode(final NodeValue<?> value) {
			return null;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#createNodeValue(Object)
		 */
		@Override
		public NodeValue<?> createNodeValue(final Object value) {
			return new ShapeReplacementConfigElementNodeValue((ShapeReplacementConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return ShapeReplacementConfigElementNodeValue.class;
		}
	}

	private static class ExtensionElementNode extends ConfigurableExtensionReferenceElementNode<ShapeReplacementExtensionConfig> {
		public static final String NODE_CLASS = "node.class.ShapeReplacementReference";

		/**
		 * @param nodeId
		 * @param shapeReplacement
		 */
		public ExtensionElementNode(final String nodeId, final ShapeReplacementConfigElement shapeReplacement) {
			super(nodeId, shapeReplacement.getExtensionElement());
			setNodeClass(ExtensionElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<ShapeReplacementExtensionConfig> value) {
			return new ShapeReplacementExtensionReferenceNodeValue(value);
		}
	}
}
