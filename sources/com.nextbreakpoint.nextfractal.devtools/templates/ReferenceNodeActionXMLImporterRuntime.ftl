/*
 * $Id:$
 *
 */
package ${nodeActionXMLImporterPackageName};

<#list imports as import>
import ${import};
</#list>
<#if extension.extension>
/**
 * @author ${author}
 */
public class ${extension.elementName?cap_first}ReferenceNodeActionXMLImporterRuntime extends ExtensionReferenceElementNodeActionXMLImporterRuntime {
	/**
	 * 
	 */
	public ${extension.elementName?cap_first}ReferenceNodeActionXMLImporterRuntime() {
		super(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry());
	}
}
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class ${extension.elementName?cap_first}ReferenceNodeActionXMLImporterRuntime extends ConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<${extension.elementName?cap_first}ExtensionConfig> {
	/**
	 * 
	 */
	public ${extension.elementName?cap_first}ReferenceNodeActionXMLImporterRuntime() {
		super(${extension.registryClassName}.getInstance().get${extension.elementName?cap_first}Registry());
	}
}
</#if>
 