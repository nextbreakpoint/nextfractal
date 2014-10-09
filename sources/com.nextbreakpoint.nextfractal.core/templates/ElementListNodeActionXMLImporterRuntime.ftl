/*
 * $Id:$
 *
 */
package ${nodeActionXMLImporterPackageName};

<#list imports as import>
import ${import};
</#list>
/**
 * @author ${author}
 */
public class ${element.elementName?cap_first}ElementListNodeActionXMLImporterRuntime extends ConfigElementListNodeActionXMLImporterRuntime<${element.elementName?cap_first}ConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.ConfigElementListNodeActionXMLImporterRuntime#createImporter()
	 */
	@Override
	protected ${element.elementName?cap_first}ConfigElementXMLImporter createImporter() {
		return new ${element.elementName?cap_first}ConfigElementXMLImporter();
	}
}
