/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.core.common.FloatElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class SkewShapeAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SkewShapeAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SkewShapeAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SkewShapeAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new ShearXElementNode(getConfig()));
			parentNode.appendChildNode(new ShearYElementNode(getConfig()));
		}

		private class ShearXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ShearXElementNode(final SkewShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".shearX", config.getShearXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShearXElement"));
			}
		}
		private class ShearYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ShearYElementNode(final SkewShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".shearY", config.getShearYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShearYElement"));
			}
		}
	}
}
