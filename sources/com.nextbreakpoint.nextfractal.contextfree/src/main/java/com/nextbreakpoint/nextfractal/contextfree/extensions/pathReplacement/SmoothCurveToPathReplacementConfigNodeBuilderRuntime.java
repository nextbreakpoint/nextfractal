/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

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
public class SmoothCurveToPathReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.nodeBuilder.extension.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SmoothCurveToPathReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SmoothCurveToPathReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SmoothCurveToPathReplacementConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.Node)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			parentNode.appendChildNode(new XElementNode(getConfig()));
			parentNode.appendChildNode(new YElementNode(getConfig()));
			parentNode.appendChildNode(new X2ElementNode(getConfig()));
			parentNode.appendChildNode(new Y2ElementNode(getConfig()));
		}

		private class XElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public XElementNode(final SmoothCurveToPathReplacementConfig config) {
				super(config.getExtensionId() + ".x", config.getXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.XElement"));
			}
		}
		private class YElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public YElementNode(final SmoothCurveToPathReplacementConfig config) {
				super(config.getExtensionId() + ".y", config.getYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.YElement"));
			}
		}
		private class X2ElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public X2ElementNode(final SmoothCurveToPathReplacementConfig config) {
				super(config.getExtensionId() + ".x2", config.getX2Element());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.X2Element"));
			}
		}
		private class Y2ElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public Y2ElementNode(final SmoothCurveToPathReplacementConfig config) {
				super(config.getExtensionId() + ".y2", config.getY2Element());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.Y2Element"));
			}
		}
	}
}
