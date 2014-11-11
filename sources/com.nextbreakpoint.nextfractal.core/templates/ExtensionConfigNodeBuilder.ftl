/*
 * $Id:$
 *
 */
package ${extension.extensionConfigPackageName};

<#if extension.configurableExtension>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${extension.extensionConfigClassName}NodeBuilderRuntime extends NodeBuilderExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.extension.NodeBuilderExtensionRuntime#createNodeBuilder(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public NodeBuilder createNodeBuilder(final ExtensionConfig config) {
		return new ConfigNodeBuilder((${extension.extensionConfigClassName}) config);
	}

	private class ConfigNodeBuilder extends AbstractExtensionConfigNodeBuilder<${extension.extensionConfigClassName}> {
		/**
		 * @param config
		 */
		public ConfigNodeBuilder(final ${extension.extensionConfigClassName} config) {
			super(config);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.AbstractExtensionConfigNodeBuilder#createNodes(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject)
		 */
		@Override
		public void createNodes(final Node parentNode) {
			<#list subelements as subelement>
			<#if subelement.configurableExtensionElement>
			parentNode.appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode(getConfig()));
			<#elseif subelement.extensionElement>
			parentNode.appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode(getConfig()));
			<#elseif subelement.simpleElement>
			parentNode.appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(getConfig()));
			<#elseif subelement.complexElement>
			parentNode.appendChildNode(new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(getConfig()));
			</#if>
			</#list>
		}

		<#list subelements as subelement>
		<#if subelement.configurableExtensionElement>
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode extends ConfigurableExtensionReferenceElementNode<${subelement.extensionConfigClassName}> {
			public static final String NODE_CLASS = "node.class.${subelement.elementName?cap_first}Reference";
			
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode(final ${extension.extensionConfigClassName} config) {
				super(config.getExtensionId() + ".${subelement.elementName?uncap_first}", config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode.NODE_CLASS);
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			protected NodeValue<?> createNodeValue(final ConfigurableExtensionReference<${extension.extensionConfigClassName}> value) {
				return new ExtensionReferenceElementNodeValue<ConfigurableExtensionReference<${extension.extensionConfigClassName}>>(value);
			}
		}
		<#elseif subelement.extensionElement>
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode extends ExtensionReferenceElementNode {
			public static final String NODE_CLASS = "node.class.${subelement.elementName?cap_first}Reference";
			
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode(final ${extension.extensionConfigClassName} config) {
				super(config.getExtensionId() + ".${subelement.elementName?uncap_first}", config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}ReferenceNode.NODE_CLASS);
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			protected NodeValue<?> createNodeValue(final ExtensionReference value) {
				return new ExtensionReferenceElementNodeValue<ExtensionReference>(value);
			}
		}
		<#elseif subelement.simpleElement>
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends ${subelement.configElementClassName}Node {
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final ${extension.extensionConfigClassName} config) {
				super(config.getExtensionId() + ".${subelement.elementName?uncap_first}", config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}
		}
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends ${subelement.configElementClassName}Node {
			public static final String NODE_CLASS = "node.class.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}";
			
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final ${extension.extensionConfigClassName} config) {
				super(config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node.NODE_CLASS);
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}
		}
		<#elseif subelement.cardinality == "ONE">
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends AbstractConfigElementSingleNode<${subelement.configElementClassName}> {
			public static final String NODE_CLASS = "node.class.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}";
			
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final ${extension.extensionConfigClassName} config) {
				super(config.getExtensionId() + ".${subelement.elementName?uncap_first}s", config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node.NODE_CLASS);
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<${subelement.configElementClassName}> createChildNode(final ${subelement.configElementClassName} value) {
				return new ${subelement.configElementClassName}Node(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<${subelement.configElementClassName}> createNodeValue(final Object value) {
				return new ${subelement.configElementClassName}NodeValue((${subelement.configElementClassName}) value);
			}
	
			private class ${subelement.configElementClassName}NodeValue extends ConfigElementSingleNodeValue<${subelement.configElementClassName}> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public ${subelement.configElementClassName}NodeValue(final ${subelement.configElementClassName} value) {
					super(value);
				}
			}
		}
		<#elseif subelement.cardinality == "MANY">
		private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node extends AbstractConfigElementListNode<${subelement.configElementClassName}> {
			public static final String NODE_CLASS = "node.class.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}";
			
			/**
			 * @param config
			 */
			public ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node(final ${extension.extensionConfigClassName} config) {
				super(config.getExtensionId() + ".${subelement.elementName?uncap_first}List", config.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
				setNodeClass(${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Node.NODE_CLASS);
				setNodeLabel(${extension.resourcesClassName}.getInstance().getString("node.label.${subelement.elementName?cap_first}${subelement.fieldNameSuffix}"));
			}

			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createChildNode(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
			 */
			@Override
			protected AbstractConfigElementNode<${subelement.configElementClassName}> createChildNode(final ${subelement.configElementClassName} value) {
				return new ${subelement.configElementClassName}Node(value);
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#getChildValueType()
			 */
			@Override
			public Class<?> getChildValueType() {
				return ${subelement.configElementClassName}NodeValue.class;
			}
	
			/**
			 * @see com.nextbreakpoint.nextfractal.core.util.AbstractConfigElementListNode#createNodeValue(Object)
			 */
			@Override
			public NodeValue<${subelement.configElementClassName}> createNodeValue(final Object value) {
				return new ${subelement.configElementClassName}NodeValue((${subelement.configElementClassName}) value);
			}
	
			private class ${subelement.configElementClassName}NodeValue extends ConfigElementListNodeValue<${subelement.configElementClassName}> {
				private static final long serialVersionUID = 1L;
	
				/**
				 * @param value
				 */
				public ${subelement.configElementClassName}NodeValue(final ${subelement.configElementClassName} value) {
					super(value);
				}
			}
		}
		</#if>
		</#if>
		</#list>
	}
}
</#if>