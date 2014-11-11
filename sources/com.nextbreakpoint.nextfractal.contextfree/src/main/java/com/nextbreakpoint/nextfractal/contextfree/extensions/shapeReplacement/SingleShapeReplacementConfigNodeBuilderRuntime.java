/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementNode;
import com.nextbreakpoint.nextfractal.core.common.StringElementNode;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.tree.NodeValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.core.util.ConfigElementListNodeValue;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((SingleShapeReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<SingleShapeReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final SingleShapeReplacementConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new ShapeElementNode(getConfig()));
			parentNode.appendChildNode(new ShapeAdjustmentListElementNode(getConfig()));
		}

		private class ShapeElementNode extends StringElementNode {
			/**
			 * @param config
			 */
			public ShapeElementNode(final SingleShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shape", config.getShapeElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShapeElement"));
			}
		}
		private class ShapeAdjustmentListElementNode extends AbstractConfigElementListNode<ShapeAdjustmentConfigElement> {
			public static final String NODE_CLASS = "node.class.ShapeAdjustmentListElement";
			
			/**
			 * @param config
			 */
			public ShapeAdjustmentListElementNode(final SingleShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shapeAdjustmentList", config.getShapeAdjustmentListElement());
				setNodeClass(ShapeAdjustmentListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShapeAdjustmentListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<ShapeAdjustmentConfigElement> createChildNode(final ShapeAdjustmentConfigElement value) {
				return new ShapeAdjustmentConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ShapeAdjustmentConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<ShapeAdjustmentConfigElement> createNodeValue(final Object value) {
				return new ShapeAdjustmentConfigElementNodeValue((ShapeAdjustmentConfigElement) value);
			}
	
			private class ShapeAdjustmentConfigElementNodeValue extends ConfigElementListNodeValue<ShapeAdjustmentConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public ShapeAdjustmentConfigElementNodeValue(final ShapeAdjustmentConfigElement value) {
					super(value);
				}
			}
		}
	}
}
