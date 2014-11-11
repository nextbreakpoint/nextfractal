/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

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
public class SkewPathAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SkewPathAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SkewPathAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SkewPathAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ShearXElementNode(getConfig()));
			parentNode.appendChildNode(new ShearYElementNode(getConfig()));
		}

		private class ShearXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ShearXElementNode(final SkewPathAdjustmentConfig config) {
				super(config.getExtensionId() + ".shearX", config.getShearXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShearXElement"));
			}
		}
		private class ShearYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ShearYElementNode(final SkewPathAdjustmentConfig config) {
				super(config.getExtensionId() + ".shearY", config.getShearYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShearYElement"));
			}
		}
	}
}
