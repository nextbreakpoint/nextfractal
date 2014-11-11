/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementNode;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementNode;
import com.nextbreakpoint.nextfractal.core.elements.IntegerElementNode;
import com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementNode;
import com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.util.ConfigElementListNodeValue;

/**
 * @author Andrea Medeghini
 */
public class MultiShapeReplacementConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((MultiShapeReplacementConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<MultiShapeReplacementConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final MultiShapeReplacementConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new TimesElementNode(getConfig()));
			parentNode.appendChildNode(new ShapeReplacementListElementNode(getConfig()));
			parentNode.appendChildNode(new ShapeAdjustmentListElementNode(getConfig()));
		}

		private class TimesElementNode extends IntegerElementNode {
			/**
			 * @param config
			 */
			public TimesElementNode(final MultiShapeReplacementConfig config) {
				super(config.getExtensionId() + ".times", config.getTimesElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.TimesElement"));
			}
		}
		private class ShapeReplacementListElementNode extends AbstractConfigElementListNode<ShapeReplacementConfigElement> {
			public static final String NODE_CLASS = "node.class.ShapeReplacementListElement";
			
			/**
			 * @param config
			 */
			public ShapeReplacementListElementNode(final MultiShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shapeReplacementList", config.getShapeReplacementListElement());
				setNodeClass(ShapeReplacementListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShapeReplacementListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<ShapeReplacementConfigElement> createChildNode(final ShapeReplacementConfigElement value) {
				return new ShapeReplacementConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ShapeReplacementConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<ShapeReplacementConfigElement> createNodeValue(final Object value) {
				return new ShapeReplacementConfigElementNodeValue((ShapeReplacementConfigElement) value);
			}
	
			private class ShapeReplacementConfigElementNodeValue extends ConfigElementListNodeValue<ShapeReplacementConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public ShapeReplacementConfigElementNodeValue(final ShapeReplacementConfigElement value) {
					super(value);
				}
			}
		}
		private class ShapeAdjustmentListElementNode extends AbstractConfigElementListNode<ShapeAdjustmentConfigElement> {
			public static final String NODE_CLASS = "node.class.ShapeAdjustmentListElement";
			
			/**
			 * @param config
			 */
			public ShapeAdjustmentListElementNode(final MultiShapeReplacementConfig config) {
				super(config.getExtensionId() + ".shapeAdjustmentList", config.getShapeAdjustmentListElement());
				setNodeClass(ShapeAdjustmentListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShapeAdjustmentListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<ShapeAdjustmentConfigElement> createChildNode(final ShapeAdjustmentConfigElement value) {
				return new ShapeAdjustmentConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ShapeAdjustmentConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createNodeValue(Object)
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
