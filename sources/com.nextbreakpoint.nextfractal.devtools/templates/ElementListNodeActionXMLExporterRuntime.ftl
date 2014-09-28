/*
 * $Id:$
 *
 */
package ${nodeActionXMLExporterPackageName};

<#list imports as import>
import ${import};
</#list>
/**
 * @author ${author}
 */
public class ${element.elementName?cap_first}ElementListNodeActionXMLExporterRuntime extends ConfigElementListNodeActionXMLExporterRuntime<${element.elementName?cap_first}ConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.ConfigElementListNodeActionXMLExporterRuntime#createExporter()
	 */
	@Override
	protected ${element.elementName?cap_first}ConfigElementXMLExporter createExporter() {
		return new ${element.elementName?cap_first}ConfigElementXMLExporter();
	}
}
 