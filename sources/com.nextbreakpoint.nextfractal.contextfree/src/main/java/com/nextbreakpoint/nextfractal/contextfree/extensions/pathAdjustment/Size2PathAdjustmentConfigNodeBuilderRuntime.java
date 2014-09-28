/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.core.common.FloatElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class Size2PathAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((Size2PathAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<Size2PathAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final Size2PathAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new ScaleXElementNode(getConfig()));
			parentNode.appendChildNode(new ScaleYElementNode(getConfig()));
		}

		private class ScaleXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleXElementNode(final Size2PathAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleX", config.getScaleXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleXElement"));
			}
		}
		private class ScaleYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleYElementNode(final Size2PathAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleY", config.getScaleYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleYElement"));
			}
		}
	}
}
