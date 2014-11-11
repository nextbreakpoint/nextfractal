/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.figure;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeResources;
import com.nextbreakpoint.nextfractal.contextfree.extensionPoints.figure.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class FigureConfigElementNode extends AbstractConfigElementNode<FigureConfigElement> {
	private static final String NODE_ID = FigureConfigElement.CLASS_ID;
	private static final String NODE_CLASS = "node.class.FigureElement";
	private static final String NODE_LABEL = ContextFreeResources.getInstance().getString("node.label.FigureElement");
	private final FigureConfigElement figure;

	/**
	 * Constructs a new effect node.
	 * 
	 * @param figure the figure element.
	 */
	public FigureConfigElementNode(final FigureConfigElement figure) {
		super(FigureConfigElementNode.NODE_ID);
		if (figure == null) {
			throw new IllegalArgumentException("figure is null");
		}
		this.figure = figure;
		setNodeLabel(FigureConfigElementNode.NODE_LABEL);
		setNodeClass(FigureConfigElementNode.NODE_CLASS);
		setNodeValue(new FigureConfigElementNodeValue(figure));
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof FigureConfigElementNode) {
			return (figure == ((FigureConfigElementNode) o).figure);
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode#getConfigElement()
	 */
	@Override
	public FigureConfigElement getConfigElement() {
		return figure;
	}

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
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject#updateNode()
	 */
	@Override
	protected void updateChildNodes() {
		createChildNodes((FigureConfigElementNodeValue) getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNode#createNodeEditor()
	 */
	@Override
	protected NodeEditor createNodeEditor() {
		return new FigureNodeEditor(this);
	}

	/**
	 * @param value
	 */
	protected void createChildNodes(final FigureConfigElementNodeValue value) {
		removeAllChildNodes();
		appendChildNode(new ExtensionElementNode(FigureConfigElementNode.NODE_ID + ".extension", value.getValue()));
	}

	private static class FigureNodeEditor extends NodeEditor {
		/**
		 * @param node
		 */
		public FigureNodeEditor(final NodeObject node) {
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
			return new FigureConfigElementNodeValue((FigureConfigElement) value);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeEditor#getNodeValueType()
		 */
		@Override
		public Class<?> getNodeValueType() {
			return FigureConfigElementNodeValue.class;
		}
	}

	private static class ExtensionElementNode extends ConfigurableExtensionReferenceElementNode<FigureExtensionConfig> {
		public static final String NODE_CLASS = "node.class.FigureReference";

		/**
		 * @param nodeId
		 * @param figure
		 */
		public ExtensionElementNode(final String nodeId, final FigureConfigElement figure) {
			super(nodeId, figure.getExtensionElement());
			setNodeClass(ExtensionElementNode.NODE_CLASS);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementNode#createNodeValue(com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference)
		 */
		@Override
		protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<FigureExtensionConfig> value) {
			return new FigureExtensionReferenceNodeValue(value);
		}
	}
}
