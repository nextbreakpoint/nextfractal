/*
 * $Id:$
 *
 */
package ${element.configElementPackageName};

<#if element.simpleElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.configElementClassName}XMLExporter extends ValueConfigElementXMLExporter<${element.valueClassName}, ${element.configElementClassName}> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final ${element.valueClassName} value) {
		<#if element.valuePackageName == "java.lang" && element.valueClassName == "String">
		return value;
		<#else>
		return ${element.valueClassName}.toString(value);
		</#if>
	}
}
<#elseif element.complexElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.configElementClassName}XMLExporter extends XMLExporter<${element.configElementClassName}> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final ${element.configElementClassName} configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, ${element.configElementClassName}.CLASS_ID);
		try {
			exportProperties(configElement, element, builder);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder, java.lang.String)
	 */
	protected void exportProperties(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		export${subelement.elementName?cap_first}(configElement, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
		<#elseif subelement.cardinality == "ONE">
		export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
		<#elseif subelement.cardinality == "MANY">
		export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, createProperty(builder, element, "${subelement.elementName?uncap_first}List"), builder);
		</#if>
		<#elseif subelement.simpleElement>
		export${subelement.elementName?cap_first}(configElement, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
		</#if>
		</#list>
	}

	<#list subelements as subelement>
	<#if subelement.extensionElement>
	private void export${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(configElement.get${subelement.elementName?cap_first}Element(), builder));
	}
	<#elseif subelement.configurableExtensionElement>
	private void export${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ${subelement.configElementClassName}XMLExporter<${subelement.extensionConfigClassName}>().exportToElement(configElement.get${subelement.elementName?cap_first}Element(), builder));
	}
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(configElement.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(), builder));
	}
	<#elseif subelement.cardinality == "ONE">
	private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final ${subelement.configElementClassName}XMLExporter ${subelement.elementName?uncap_first}Exporter = new ${subelement.configElementClassName}XMLExporter();
		element.appendChild(${subelement.elementName?uncap_first}Exporter.exportToElement(configElement.get${subelement.elementName?cap_first}ConfigElement(), builder));
	}
	<#elseif subelement.cardinality == "MANY">
	private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final ${subelement.configElementClassName}XMLExporter ${subelement.elementName?uncap_first}Exporter = new ${subelement.configElementClassName}XMLExporter();
		for (int i = 0; i < configElement.get${subelement.elementName?cap_first}ConfigElementCount(); i++) {
			element.appendChild(${subelement.elementName?uncap_first}Exporter.exportToElement(configElement.get${subelement.elementName?cap_first}ConfigElement(i), builder));
		}
	}
	</#if>
	<#elseif subelement.simpleElement>
	private void export${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(configElement.get${subelement.elementName?cap_first}Element(), builder));
	}
	</#if>
	</#list>
}
</#if>