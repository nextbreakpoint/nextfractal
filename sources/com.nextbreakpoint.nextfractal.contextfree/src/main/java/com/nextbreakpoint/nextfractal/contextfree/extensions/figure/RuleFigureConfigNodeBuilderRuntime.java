/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementNode;
import com.nextbreakpoint.nextfractal.core.common.FloatElementNode;
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
public class RuleFigureConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((RuleFigureConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<RuleFigureConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final RuleFigureConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.tree.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new NameElementNode(getConfig()));
			parentNode.appendChildNode(new ProbabilityElementNode(getConfig()));
			parentNode.appendChildNode(new ShapeReplacementListElementNode(getConfig()));
		}

		private class NameElementNode extends StringElementNode {
			/**
			 * @param config
			 */
			public NameElementNode(final RuleFigureConfig config) {
				super(config.getExtensionId() + ".name", config.getNameElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.NameElement"));
			}
		}
		private class ProbabilityElementNode extends FloatElementNode {
			/**
			 * @param config
			 */
			public ProbabilityElementNode(final RuleFigureConfig config) {
				super(config.getExtensionId() + ".probability", config.getProbabilityElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ProbabilityElement"));
			}
		}
		private class ShapeReplacementListElementNode extends AbstractConfigElementListNode<ShapeReplacementConfigElement> {
			public static final String NODE_CLASS = "node.class.ShapeReplacementListElement";
			
			/**
			 * @param config
			 */
			public ShapeReplacementListElementNode(final RuleFigureConfig config) {
				super(config.getExtensionId() + ".shapeReplacementList", config.getShapeReplacementListElement());
				setNodeClass(ShapeReplacementListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.ShapeReplacementListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<ShapeReplacementConfigElement> createChildNode(final ShapeReplacementConfigElement value) {
				return new ShapeReplacementConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ShapeReplacementConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
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
	}
}
