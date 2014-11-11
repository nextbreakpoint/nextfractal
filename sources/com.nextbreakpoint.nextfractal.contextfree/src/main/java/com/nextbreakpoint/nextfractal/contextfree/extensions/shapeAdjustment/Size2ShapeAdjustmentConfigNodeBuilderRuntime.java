/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class Size2ShapeAdjustmentConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((Size2ShapeAdjustmentConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<Size2ShapeAdjustmentConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final Size2ShapeAdjustmentConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ScaleXElementNode(getConfig()));
			parentNode.appendChildNode(new ScaleYElementNode(getConfig()));
		}

		private class ScaleXElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleXElementNode(final Size2ShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleX", config.getScaleXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleXElement"));
			}
		}
		private class ScaleYElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ScaleYElementNode(final Size2ShapeAdjustmentConfig config) {
				super(config.getExtensionId() + ".scaleY", config.getScaleYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ScaleYElement"));
			}
		}
	}
}
