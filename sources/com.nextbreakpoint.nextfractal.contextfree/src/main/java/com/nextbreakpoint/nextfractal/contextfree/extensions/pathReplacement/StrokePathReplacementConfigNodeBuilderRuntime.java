/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeCapElementNode;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeJoinElementNode;
import com.nextbreakpoint.nextfractal.contextfree.elements.StrokeWidthElementNode;
import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.common.ConfigElementListNodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeValue;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((StrokePathReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<StrokePathReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final StrokePathReplacementConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new WidthElementNode(getConfig()));
			parentNode.appendChildNode(new CapElementNode(getConfig()));
			parentNode.appendChildNode(new JoinElementNode(getConfig()));
			parentNode.appendChildNode(new PathAdjustmentListElementNode(getConfig()));
		}

		private class WidthElementNode extends StrokeWidthElementNode {
			/**
			 * @param config
			 */
			public WidthElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".width", config.getWidthElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.WidthElement"));
			}
		}
		private class CapElementNode extends StrokeJoinElementNode {
			/**
			 * @param config
			 */
			public CapElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".cap", config.getCapElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.CapElement"));
			}
		}
		private class JoinElementNode extends StrokeCapElementNode {
			/**
			 * @param config
			 */
			public JoinElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".join", config.getJoinElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.JoinElement"));
			}
		}
		private class PathAdjustmentListElementNode extends AbstractConfigElementListNode<PathAdjustmentConfigElement> {
			public static final String NODE_CLASS = "node.class.PathAdjustmentListElement";
			
			/**
			 * @param config
			 */
			public PathAdjustmentListElementNode(final StrokePathReplacementConfig config) {
				super(config.getExtensionId() + ".pathAdjustmentList", config.getPathAdjustmentListElement());
				setNodeClass(PathAdjustmentListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.PathAdjustmentListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<PathAdjustmentConfigElement> createChildNode(final PathAdjustmentConfigElement value) {
				return new PathAdjustmentConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return PathAdjustmentConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<PathAdjustmentConfigElement> createNodeValue(final Object value) {
				return new PathAdjustmentConfigElementNodeValue((PathAdjustmentConfigElement) value);
			}
	
			private class PathAdjustmentConfigElementNodeValue extends ConfigElementListNodeValue<PathAdjustmentConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public PathAdjustmentConfigElementNodeValue(final PathAdjustmentConfigElement value) {
					super(value);
				}
			}
		}
	}
}
