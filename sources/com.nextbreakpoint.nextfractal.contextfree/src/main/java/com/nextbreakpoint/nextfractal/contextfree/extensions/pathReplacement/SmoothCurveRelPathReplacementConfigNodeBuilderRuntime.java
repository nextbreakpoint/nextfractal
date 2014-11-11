/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

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
public class SmoothCurveRelPathReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SmoothCurveRelPathReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SmoothCurveRelPathReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SmoothCurveRelPathReplacementConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new XElementNode(getConfig()));
			parentNode.appendChildNode(new YElementNode(getConfig()));
			parentNode.appendChildNode(new X2ElementNode(getConfig()));
			parentNode.appendChildNode(new Y2ElementNode(getConfig()));
		}

		private class XElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public XElementNode(final SmoothCurveRelPathReplacementConfig config) {
				super(config.getExtensionId() + ".x", config.getXElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.XElement"));
			}
		}
		private class YElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public YElementNode(final SmoothCurveRelPathReplacementConfig config) {
				super(config.getExtensionId() + ".y", config.getYElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.YElement"));
			}
		}
		private class X2ElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public X2ElementNode(final SmoothCurveRelPathReplacementConfig config) {
				super(config.getExtensionId() + ".x2", config.getX2Element());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.X2Element"));
			}
		}
		private class Y2ElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public Y2ElementNode(final SmoothCurveRelPathReplacementConfig config) {
				super(config.getExtensionId() + ".y2", config.getY2Element());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.Y2Element"));
			}
		}
	}
}
