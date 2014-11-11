/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import com.nextbreakpoint.nextfractal.contextfree.extensions.ContextFreeExtensionResources;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementNode;
import com.nextbreakpoint.nextfractal.core.elements.StringElementNode;
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
public class PathFigureConfigNodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.nodeBuilder.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((PathFigureConfig) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<PathFigureConfig> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final PathFigureConfig config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject)
		 */
		@Override
		public void createNodes(final NodeObject parentNode) {
			parentNode.appendChildNode(new NameElementNode(getConfig()));
			parentNode.appendChildNode(new PathReplacementListElementNode(getConfig()));
		}

		private class NameElementNode extends StringElementNode {
			/**
			 * @param config
			 */
			public NameElementNode(final PathFigureConfig config) {
				super(config.getExtensionId() + ".name", config.getNameElement());
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.NameElement"));
			}
		}
		private class PathReplacementListElementNode extends AbstractConfigElementListNode<PathReplacementConfigElement> {
			public static final String NODE_CLASS = "node.class.PathReplacementListElement";
			
			/**
			 * @param config
			 */
			public PathReplacementListElementNode(final PathFigureConfig config) {
				super(config.getExtensionId() + ".pathReplacementList", config.getPathReplacementListElement());
				setNodeClass(PathReplacementListElementNode.NODE_CLASS);
				setNodeLabel(ContextFreeExtensionResources.getInstance().getString("node.label.PathReplacementListElement"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<PathReplacementConfigElement> createChildNode(final PathReplacementConfigElement value) {
				return new PathReplacementConfigElementNode(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return PathReplacementConfigElementNodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<PathReplacementConfigElement> createNodeValue(final Object value) {
				return new PathReplacementConfigElementNodeValue((PathReplacementConfigElement) value);
			}
	
			private class PathReplacementConfigElementNodeValue extends ConfigElementListNodeValue<PathReplacementConfigElement> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public PathReplacementConfigElementNodeValue(final PathReplacementConfigElement value) {
					super(value);
				}
			}
		}
	}
}
