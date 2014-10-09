/*
 * $Id:$
 *
 */
package ${nodeActionXMLExporterPackageName};

<#list imports as import>
import ${import};
</#list>
<#if extension.extension>
/**
 * @author ${author}
 */
public class ${extension.elementName?cap_first}ReferenceNodeActionXMLExporterRuntime extends ExtensionReferenceElementNodeActionXMLExporterRuntime {
}
<#elseif extension.configurableExtension>
/**
 * @author ${author}
 */
public class ${extension.elementName?cap_first}ReferenceNodeActionXMLExporterRuntime extends ConfigurableExtensionReferenceElementNodeActionXMLExporterRuntime<${extension.elementName?cap_first}ExtensionConfig> {
}
</#if>
 