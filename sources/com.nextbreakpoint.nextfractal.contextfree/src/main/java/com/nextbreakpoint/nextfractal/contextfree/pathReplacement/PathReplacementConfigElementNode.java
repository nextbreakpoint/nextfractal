/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeResources;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.pathReplacement.PathReplacementExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;

/**
 * @author Andrea Medeghini
 */
public class PathReplacementConfigElementNode extends AbstractConfigElementNode<PathReplacementConfigElement> {
	private static final String NODE_ID = PathReplacementConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.PathReplacementElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.PathReplacementElement");
	private final PathReplacementConfigElement pathReplacement;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param pathReplacement the pathReplacement element.
	 */
	public PathReplacementConfigElementNode(final PathReplacementConfigElement pathReplacement) {
		super(PathReplacementConfigElementNode.NODE_ID);
		if (pathReplacement == null) {
			throw new IllegalArgumentException("pathReplacement is null");
		}
		this.pathReplacement = pathReplacement;
		setNodeLabel(PathReplacementConfigElementNode.NODE_LABEL);
		setNodeClass(PathReplacementConfigElementNode.NODE_CLASS);
		setNodeValue(new PathReplacementConfigElementNodeValue(pathReplacement));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof PathReplacementConfigElementNode) {
			return (pathReplacement == ((PathReplacementConfigElementNode) o).pathReplacement);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public PathReplacementConfigElement getConfigElement() {
		return pathReplacement;
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
	 * @see com.nextbreakpoint.nextfractal.core.tree.Node#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((PathReplacementConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new PathReplacementNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final PathReplacementConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ExtensionElementNode(PathReplacementConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class PathReplacementNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public PathReplacementNodeEditor(final Node node) {
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
			return new PathReplacementConfigElementNodeValue((PathReplacementConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return PathReplacementConfigElementNodeValue.class;
		}
	}

	private static class ExtensionElementNode extends ConfigurableExtensionReferenceElementNode<PathReplacementExtensionConfig> {
		public static final String NODE_CLASS = "node.class.PathReplacementReference";

		/**
		 * @param nodeId
		 * @param pathReplacement
		 */
		public ExtensionElementNode(final String nodeId, final PathReplacementConfigElement pathReplacement) {
			super(nodeId, pathReplacement.getExtensionElement());
			setNodeClass(ExtensionElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<PathReplacementExtensionConfig> value) {
			return new PathReplacementExtensionReferenceNodeValue(value);
		}
	}
}
