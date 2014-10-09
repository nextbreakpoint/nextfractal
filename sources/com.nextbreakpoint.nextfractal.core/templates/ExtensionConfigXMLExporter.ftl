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
public class ${extension.extensionConfigClassName}XMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<${extension.extensionConfigClassName}> createXMLExporter() {
		return new ${extension.extensionConfigClassName}XMLExporter();
	}

	private class ${extension.extensionConfigClassName}XMLExporter extends XMLExporter<${extension.extensionConfigClassName}> {
		protected String getConfigElementClassId() {
			return "${extension.extensionConfigClassName}";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final ${extension.extensionConfigClassName} extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
			final Element element = this.createElement(builder, this.getConfigElementClassId());
			try {
				exportProperties(extensionConfig, element, builder);
			}
			catch (final ExtensionException e) {
				throw new XMLExportException(e);
			}
			return element;
		}
	
		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder, java.lang.String)
		 */
		protected void exportProperties(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			<#list subelements as subelement>
			<#if subelement.extensionElement || subelement.configurableExtensionElement>
			export${subelement.elementName?cap_first}(extensionConfig, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
			<#elseif subelement.complexElement>
			<#if subelement.cardinality == "NONE">
			export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
			<#elseif subelement.cardinality == "ONE">
			export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
			<#elseif subelement.cardinality == "MANY">
			export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, createProperty(builder, element, "${subelement.elementName?uncap_first}List"), builder);
			</#if>
			<#elseif subelement.simpleElement>
			export${subelement.elementName?cap_first}(extensionConfig, createProperty(builder, element, "${subelement.elementName?uncap_first}"), builder);
			</#if>
			</#list>
		}
	
		<#list subelements as subelement>
		<#if subelement.extensionElement>
		private void export${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(extensionConfig.get${subelement.elementName?cap_first}Element(), builder));
		}
		<#elseif subelement.configurableExtensionElement>
		private void export${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ${subelement.configElementClassName}XMLExporter<${subelement.extensionConfigClassName}>().exportToElement(extensionConfig.get${subelement.elementName?cap_first}Element(), builder));
		}
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(extensionConfig.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(), builder));
		}
		<#elseif subelement.cardinality == "ONE">
		private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ${subelement.configElementClassName}XMLExporter ${subelement.elementName?uncap_first}Exporter = new ${subelement.configElementClassName}XMLExporter();
			element.appendChild(${subelement.elementName?uncap_first}Exporter.exportToElement(extensionConfig.get${subelement.elementName?cap_first}ConfigElement(), builder));
		}
		<#elseif subelement.cardinality == "MANY">
		private void export${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ${subelement.configElementClassName}XMLExporter ${subelement.elementName?uncap_first}Exporter = new ${subelement.configElementClassName}XMLExporter();
			for (int i = 0; i < extensionConfig.get${subelement.elementName?cap_first}ConfigElementCount(); i++) {
				element.appendChild(${subelement.elementName?uncap_first}Exporter.exportToElement(extensionConfig.get${subelement.elementName?cap_first}ConfigElement(i), builder));
			}
		}
		</#if>
		<#elseif subelement.simpleElement>
		private void export${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ${subelement.configElementClassName}XMLExporter().exportToElement(extensionConfig.get${subelement.elementName?cap_first}Element(), builder));
		}
		</#if>
		</#list>
	}
}
</#if>