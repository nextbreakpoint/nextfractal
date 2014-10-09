/*
 * $Id:$
 *
 */
package ${extension.extensionRegistryPackageName};

<#list imports as import>
import ${import};
</#list>

<#if extension.extension>
/**
 * @author ${author}
 */
public class ${extension.extensionRegistryClassName} extends OSGiExtensionRegistry<${extension.extensionRuntimeClassName}> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "${extensionPointId}";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "${extension.elementName}";

	/**
	 * Constructs a new registry.
	 */
	public ${extension.extensionRegistryClassName}() {
		super(${extension.extensionRegistryClassName}.EXTENSION_POINT_NAME, new OSGiExtensionBuilder<${extension.extensionRuntimeClassName}>(${extension.extensionRegistryClassName}.CONFIGURATION_ELEMENT_NAME));
	}
}
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class ${extension.extensionRegistryClassName} extends OSGiConfigurableExtensionRegistry<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "${extensionPointId}";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "${extension.elementName}";

	/**
	 * Constructs a new registry.
	 */
	public ${extension.extensionRegistryClassName}() {
		super(${extension.extensionRegistryClassName}.EXTENSION_POINT_NAME, new OSGiConfigurableExtensionBuilder<${extension.extensionRuntimeClassName}<?>, ${extension.extensionConfigClassName}>(${extension.extensionRegistryClassName}.CONFIGURATION_ELEMENT_NAME));
	}
}
</#if>
