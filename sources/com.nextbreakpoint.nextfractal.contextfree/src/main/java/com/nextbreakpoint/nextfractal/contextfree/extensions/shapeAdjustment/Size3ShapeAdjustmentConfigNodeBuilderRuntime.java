/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

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
public class Size3ShapeAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((Size3ShapeAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<Size3ShapeAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final Size3ShapeAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new ScaleXElementNode(getConfig()));
			parentNode.appendChildNode(new ScaleYElementNode(getConfig()));
			parentNode.appendChildNode(new ScaleZElementNode(getConfig()));
		}

		private class ScaleXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleXElementNode(final Size3ShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleX", config.getScaleXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleXElement"));
			}
		}
		private class ScaleYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleYElementNode(final Size3ShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleY", config.getScaleYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleYElement"));
			}
		}
		private class ScaleZElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleZElementNode(final Size3ShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleZ", config.getScaleZElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleZElement"));
			}
		}
	}
}
