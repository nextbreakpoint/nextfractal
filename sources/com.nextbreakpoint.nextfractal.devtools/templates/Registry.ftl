/*
 * $Id:$
 *
 */
package ${registry.registryPackageName};

<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${registry.registryClassName} {
	<#list extensions as extension>
	<#if extension.extension>
	private ExtensionRegistry<${extension.extensionRuntimeClassName}> ${extension.elementName?uncap_first}Registry;
	<#elseif extension.configurableExtension>
	private ConfigurableExtensionRegistry<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}> ${extension.elementName?uncap_first}Registry;
	</#if>
	</#list>

	private static class RegistryHolder {
		private static final ${registry.registryClassName} instance = new ${registry.registryClassName}();
	}

	private ${registry.registryClassName}() {
		<#list extensions as extension>
		set${extension.elementName?cap_first}Registry(new ${extension.extensionRegistryClassName}());
		</#list>
	}

	/**
	 * @return
	 */
	public static ${registry.registryClassName} getInstance() {
		return RegistryHolder.instance;
	}
	
	<#list extensions as extension>
	<#if extension.extension>
	private void set${extension.elementName?cap_first}Registry(final ExtensionRegistry<${extension.extensionRuntimeClassName}> ${extension.elementName?uncap_first}Registry) {
		this.${extension.elementName?uncap_first}Registry = ${extension.elementName?uncap_first}Registry;
	}
	<#elseif extension.configurableExtension>
	private void set${extension.elementName?cap_first}Registry(final ConfigurableExtensionRegistry<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}> ${extension.elementName?uncap_first}Registry) {
		this.${extension.elementName?uncap_first}Registry = ${extension.elementName?uncap_first}Registry;
	}
	</#if>
	</#list>
	
	<#list extensions as extension>
	<#if extension.extension>
	/**
	 * Returns a ${extension.elementName} extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<${extension.extensionRuntimeClassName}> get${extension.elementName?cap_first}Extension(final String extensionId) throws ExtensionNotFoundException {
		return ${extension.elementName?uncap_first}Registry.getExtension(extensionId);
	}
	<#elseif extension.configurableExtension>
	/**
	 * Returns a ${extension.elementName} extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}> get${extension.elementName?cap_first}Extension(final String extensionId) throws ExtensionNotFoundException {
		return ${extension.elementName?uncap_first}Registry.getConfigurableExtension(extensionId);
	}
	</#if>
	</#list>
	
	<#list extensions as extension>
	<#if extension.extension>
	/**
	 * @return the ${extension.elementName?uncap_first}Registry
	 */
	public ExtensionRegistry<${extension.extensionRuntimeClassName}> get${extension.elementName?cap_first}Registry() {
		return ${extension.elementName?uncap_first}Registry;
	}
	<#elseif extension.configurableExtension>
	/**
	 * @return the ${extension.elementName?uncap_first}Registry
	 */
	public ConfigurableExtensionRegistry<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}> get${extension.elementName?cap_first}Registry() {
		return ${extension.elementName?uncap_first}Registry;
	}
	</#if>
	</#list>
}
