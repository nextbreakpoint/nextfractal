/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;

/**
 * @author Andrea Medeghini
 */
public class SizeShapeAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SizeShapeAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SizeShapeAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SizeShapeAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ScaleElementNode(getConfig()));
		}

		private class ScaleElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleElementNode(final SizeShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scale", config.getScaleElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleElement"));
			}
		}
	}
}
