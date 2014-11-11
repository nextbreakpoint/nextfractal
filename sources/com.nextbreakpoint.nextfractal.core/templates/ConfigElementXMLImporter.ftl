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
public class ${element.configElementClassName}XMLImporter extends ValueConfigElementXMLImporter<${element.valueClassName}, ${element.configElementClassName}> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected ${element.valueClassName} parseValue(final String value) {
		return ${element.valueClassName}.valueOf(value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected ${element.configElementClassName} createDefaultConfigElement() {
		return new ${element.configElementClassName}(${element.defaultValue});
	}
}
<#elseif element.complexElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.configElementClassName}XMLImporter extends XMLImporter<${element.configElementClassName}> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public ${element.configElementClassName} importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, ${element.configElementClassName}.CLASS_ID);
		final ${element.configElementClassName} configElement = new ${element.configElementClassName}();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == ${subelements?size}) {
			try {
				importProperties(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final ${element.configElementClassName} configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		import${subelement.elementName?cap_first}(configElement, propertyElements.get(${subelement_index}));
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, propertyElements.get(${subelement_index}));
		<#elseif subelement.cardinality == "ONE">
		import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, propertyElements.get(${subelement_index}));
		<#elseif subelement.cardinality == "MANY">
		import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(configElement, propertyElements.get(${subelement_index}));
		</#if>
		<#elseif subelement.simpleElement>
		import${subelement.elementName?cap_first}(configElement, propertyElements.get(${subelement_index}));
		</#if>
		</#list>
	}

	<#list subelements as subelement>
	<#if subelement.extensionElement>
	private void import${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
		if (${subelement.elementName?uncap_first}Elements.size() == 1) {
			configElement.set${subelement.elementName?cap_first}Reference(new ${subelement.configElementClassName}XMLImporter(${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Registry()).importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getReference());
		}
	}
	<#elseif subelement.configurableExtensionElement>
	private void import${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
		if (${subelement.elementName?uncap_first}Elements.size() == 1) {
			configElement.set${subelement.elementName?cap_first}Reference(new ${subelement.configElementClassName}XMLImporter<${subelement.extensionConfigClassName}>(${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Registry()).importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getReference());
		}
	}
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
		if (${subelement.elementName?uncap_first}Elements.size() == 1) {
			configElement.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}().copyFrom(new ${subelement.configElementClassName}XMLImporter().importFromElement(${subelement.elementName?uncap_first}Elements.get(0)));
		}
	}
	<#elseif subelement.cardinality == "ONE">
	private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final ${subelement.elementName?cap_first}ConfigElementXMLImporter ${subelement.elementName?uncap_first}Importer = new ${subelement.elementName?cap_first}ConfigElementXMLImporter();
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
		if (${subelement.elementName?uncap_first}Elements.size() == 1) {
			configElement.set${subelement.elementName?cap_first}ConfigElement(${subelement.elementName?uncap_first}Importer.importFromElement(${subelement.elementName?uncap_first}Elements.get(0)));
		}
	}
	<#elseif subelement.cardinality == "MANY">
	private void import${subelement.elementName?cap_first}${subelement.fieldNameSuffix}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final ${subelement.elementName?cap_first}ConfigElementXMLImporter ${subelement.elementName?uncap_first}Importer = new ${subelement.elementName?cap_first}ConfigElementXMLImporter();
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.elementName?cap_first}ConfigElement.CLASS_ID);
		for (int i = 0; i < ${subelement.elementName?uncap_first}Elements.size(); i++) {
			configElement.append${subelement.elementName?cap_first}ConfigElement(${subelement.elementName?uncap_first}Importer.importFromElement(${subelement.elementName?uncap_first}Elements.get(i)));
		}
	}
	</#if>
	<#elseif subelement.simpleElement>
	private void import${subelement.elementName?cap_first}(final ${element.configElementClassName} configElement, final Element element) throws XMLImportException {
		final List<Element> ${subelement.elementName?uncap_first}Elements = this.getElements(element, ${subelement.configElementClassName}.CLASS_ID);
		if (${subelement.elementName?uncap_first}Elements.size() == 1) {
			configElement.${subelement.setMethodPrefix}${subelement.elementName?cap_first}(new ${subelement.configElementClassName}XMLImporter().importFromElement(${subelement.elementName?uncap_first}Elements.get(0)).getValue());
		}
	}
	</#if>
	</#list>
}
</#if>