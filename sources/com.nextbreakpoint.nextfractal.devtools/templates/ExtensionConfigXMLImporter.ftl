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
public class ${extension.extensionConfigClassName}XMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<${extension.extensionConfigClassName}> createXMLImporter() {
		return new ${extension.extensionConfigClassName}XMLImporter();
	}

	private class ${extension.extensionConfigClassName}XMLImporter extends XMLImporter<${extension.extensionConfigClassName}> {
		protected ${extension.extensionConfigClassName} createExtensionConfig() {
			return new ${extension.extensionConfigClassName}();
		}

		protected String getConfigElementClassId() {
			return "${extension.extensionConfigClassName}";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public ${extension.extensionConfigClassName} importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final ${extension.extensionConfigClassName} extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == ${subelements?size}) {
				try {
					importProperties(extensionConfig, propertyElements);
				}
				catch (final ExtensionException e) {
					throw new XMLImportException(e);
				}
			}
			return extensionConfig;
		}
	
		/**
		 * @param extensionConfig
		 * @param propertyElements
		 * @throws ExtensionException
		 * @throws XMLImportException
		 */
		protected void importProperties(final ${extension.extensionConfigClassName} extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			<#list subelements as subelement>
			<#if subelement.extensionElement || subelement.configurableExtensionElement>
			import${subelement.elementName?cap_first}(extensionConfig, propertyElements.get(${subelement_index}));
			<#elseif subelement.complexElement>
			<#if subelement.cardinality == "NONE">
			import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, propertyElements.get(${subelement_index}));
			<#elseif subelement.cardinality == "ONE">
			import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, propertyElements.get(${subelement_index}));
			<#elseif subelement.cardinality == "MANY">
			import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(extensionConfig, propertyElements.get(${subelement_index}));
			</#if>
			<#elseif subelement.simpleElement>
			import${subelement.elementName?cap_first}(extensionConfig, propertyElements.get(${subelement_index}));
			</#if>
			</#list>
		}
	
		<#list subelements as subelement>
		<#if subelement.extensionElement>
		private void import${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.elementConfigClassName}.CLASS_ID);
			if (${subelement.elementName?uncap_first}Elements.size() == 1) {
				extensionConfig.set${subelement.elementName?cap_first}Reference(new ${subelement.configElementClassName}XMLImporter(${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Registry()).importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getReference());
			}
		}
		<#elseif subelement.configurableExtensionElement>
		private void import${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
			if (${subelement.elementName?uncap_first}Elements.size() == 1) {
				extensionConfig.set${subelement.elementName?cap_first}Reference(new ${subelement.configElementClassName}XMLImporter<${subelement.extensionConfigClassName}>(${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Registry()).importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getReference());
			}
		}
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
			if (${subelement.elementName?uncap_first}Elements.size() == 1) {
				extensionConfig.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}().copyFrom(new ${subelement.configElementClassName}XMLImporter().importFromElement(${subelement.elementName?uncap_first}Elements.get(0)));
			}
		}
		<#elseif subelement.cardinality == "ONE">
		private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final ${subelement.elementName?cap_first}ConfigElementXMLImporter ${subelement.elementName?uncap_first}Importer = new ${subelement.elementName?cap_first}ConfigElementXMLImporter();
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
			if (${subelement.elementName?uncap_first}Elements.size() == 1) {
				extensionConfig.set${subelement.elementName?cap_first}ConfigElement(${subelement.elementName?uncap_first}Importer.importFromElement(${subelement.elementName?uncap_first}Elements.get(0)));
			}
		}
		<#elseif subelement.cardinality == "MANY">
		private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final ${subelement.elementName?cap_first}ConfigElementXMLImporter ${subelement.elementName?uncap_first}Importer = new ${subelement.elementName?cap_first}ConfigElementXMLImporter();
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.elementName?cap_first}ConfigElement.CLASS_ID);
			for (int i = 0; i < ${subelement.elementName?uncap_first}Elements.size(); i++) {
				extensionConfig.append${subelement.elementName?cap_first}ConfigElement(${subelement.elementName?uncap_first}Importer.importFromElement(${subelement.elementName?uncap_first}Elements.get(i)));
			}
		}
		</#if>
		<#elseif subelement.simpleElement>
		private void import${subelement.elementName?cap_first}(final ${extension.extensionConfigClassName} extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
			if (${subelement.elementName?uncap_first}Elements.size() == 1) {
				extensionConfig.${subelement.setMethodPrefix}${subelement.elementName?cap_first}(new ${subelement.configElementClassName}XMLImporter().importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getValue());
			}
		}
		</#if>
		</#list>
	}
}
</#if>