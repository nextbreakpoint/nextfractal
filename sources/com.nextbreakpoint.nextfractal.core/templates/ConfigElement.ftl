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
public class ${element.configElementClassName} extends ValueConfigElement<${element.valueClassName}> {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "${element.elementClassId}";

	/**
	 * Constructs a new element.
	 */
	public ${element.configElementClassName}(${element.valueClassName} defaultValue) {
		super(${element.configElementClassName}.CLASS_ID, defaultValue);
	}

	/**
	 * @return
	 */
	@Override
	public ${element.configElementClassName} clone() {
		return new ${element.configElementClassName}(getValue());
	}

	/**
	 *
	 */
	public void copyFrom(ConfigElement source) {
		${element.configElementClassName} element = (${element.configElementClassName}) source;
		setValue(element.getValue());
	}
}
<#elseif element.complexElement>
<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
public class ${element.configElementClassName} extends AbstractConfigElement {
	private static final long serialVersionUID = 1L;
	public static final String CLASS_ID = "${element.elementClassId}";
	<#list subelements as subelement>
	<#if subelement.configurableExtensionElement>
	private final ConfigurableExtensionReferenceElement<${subelement.extensionConfigClassName}> ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ConfigurableExtensionReferenceElement<${subelement.extensionConfigClassName}>();
	<#elseif subelement.extensionElement>
	private final ExtensionReferenceElement ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ExtensionReferenceElement();
	<#elseif subelement.simpleElement>
	private final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ${subelement.configElementClassName}(${subelement.defaultValue?if_exists});
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ${subelement.configElementClassName}();
	<#elseif subelement.cardinality == "ONE">
	private final SingleConfigElement<${subelement.configElementClassName}> ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new SingleConfigElement<${subelement.configElementClassName}>("${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}");
	<#elseif subelement.cardinality == "MANY">
	private final ListConfigElement<${subelement.configElementClassName}> ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ListConfigElement<${subelement.configElementClassName}>("${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}");
	</#if>
	</#if>
	</#list>

	/**
	 * Constructs a new element.
	 */
	public ${element.configElementClassName}() {
		super(${element.configElementClassName}.CLASS_ID);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.runtime.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement || subelement.complexElement || subelement.simpleElement>
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setContext(context);
		</#if>
		</#list>
	}

	/**
	 * @return
	 */
	@Override
	public ${element.configElementClassName} clone() {
		final ${element.configElementClassName} element = new ${element.configElementClassName}();
		<#list subelements as subelement>
		<#if subelement.simpleElement>
		element.${subelement.setMethodPrefix}${subelement.elementName?cap_first}(${subelement.getMethodPrefix}${subelement.elementName?cap_first}());
		</#if>
		</#list>
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		if (get${subelement.elementName?cap_first}Reference() != null) {
			element.set${subelement.elementName?cap_first}Reference(get${subelement.elementName?cap_first}Reference().clone());
		}
		</#if>
		</#list>
		<#list subelements as subelement>
		<#if subelement.complexElement>
		element.${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.copyFrom(get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
		</#if>
		</#list>
		return element;
	}
	
	/**
	 *
	 */
	public void copyFrom(ConfigElement source) {
		${element.configElementClassName} ${element.elementName?uncap_first}Element = (${element.configElementClassName}) source;
		<#list subelements as subelement>
		<#if subelement.simpleElement>
		${subelement.setMethodPrefix}${subelement.elementName?cap_first}(${element.elementName?uncap_first}Element.${subelement.getMethodPrefix}${subelement.elementName?cap_first}());
		</#if>
		</#list>
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		if (${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Reference() != null) {
			set${subelement.elementName?cap_first}Reference(${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Reference().clone());
		}
		</#if>
		</#list>
		<#list subelements as subelement>
		<#if subelement.complexElement>
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.copyFrom(${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}());
		</#if>
		</#list>
	}

	<#list subelements as subelement>
	<#if subelement.configurableExtensionElement>
	/**
	 * @return
	 */
	public ConfigurableExtensionReferenceElement<${subelement.extensionConfigClassName}> get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}
	
	/**
	 * @return
	 */
	public ConfigurableExtensionReference<${subelement.extensionConfigClassName}> get${subelement.elementName?cap_first}Reference() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getReference();
	}

	/**
	 * @param reference
	 */
	public void set${subelement.elementName?cap_first}Reference(final ConfigurableExtensionReference<${subelement.extensionConfigClassName}> reference) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setReference(reference);
	}
	<#elseif subelement.extensionElement>
	/**
	 * @return
	 */
	public ExtensionReferenceElement get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}
	
	/**
	 * @return
	 */
	public ExtensionReference get${subelement.elementName?cap_first}Reference() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getReference();
	}

	/**
	 * @param reference
	 */
	public void set${subelement.elementName?cap_first}Reference(final ExtensionReference reference) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setReference(reference);
	}
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	/**
	 * @return
	 */
	public ${subelement.configElementClassName} get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}
	<#elseif subelement.cardinality == "ONE">
	/**
	 * @return
	 */
	public SingleConfigElement<${subelement.configElementClassName}> get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}

	/**
	 * @return
	 */
	public ${subelement.configElementClassName} get${subelement.elementName?cap_first}ConfigElement() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getValue();
	}

	/**
	 * @param value
	 */
	public void set${subelement.elementName?cap_first}ConfigElement(${subelement.configElementClassName} element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setValue(element);
	}
	<#elseif subelement.cardinality == "MANY">
	/**
	 * @return
	 */
	public ListConfigElement<${subelement.configElementClassName}> get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}

	/**
	 * Returns a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param index the ${subelement.elementName?uncap_first} index.
	 * @return the ${subelement.elementName?uncap_first}.
	 */
	public ${subelement.configElementClassName} get${subelement.elementName?cap_first}ConfigElement(final int index) {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getElement(index);
	}

	/**
	 * Returns a ${subelement.elementName?uncap_first} element index.
	 * 
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} element.
	 * @return the index.
	 */
	public int indexOf${subelement.elementName?cap_first}ConfigElement(final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}Element) {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.indexOfElement(${subelement.elementName?uncap_first}Element);
	}

	/**
	 * Returns the number of ${subelement.elementName?uncap_first} elements.
	 * 
	 * @return the number of ${subelement.elementName?uncap_first} elements.
	 */
	public int get${subelement.elementName?cap_first}ConfigElementCount() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getElementCount();
	}

	/**
	 * Adds a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} to add.
	 */
	public void append${subelement.elementName?cap_first}ConfigElement(final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.appendElement(${subelement.elementName?uncap_first}Element);
	}

	/**
	 * Adds a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param index the index.
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} to add.
	 */
	public void insert${subelement.elementName?cap_first}ConfigElementAfter(final int index, final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.insertElementAfter(index, ${subelement.elementName?uncap_first}Element);
	}

	/**
	 * Adds a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param index the index.
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} to add.
	 */
	public void insert${subelement.elementName?cap_first}ConfigElementBefore(final int index, final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.insertElementBefore(index, ${subelement.elementName?uncap_first}Element);
	}

	/**
	 * Removes a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param index the element index to remove.
	 */
	public void remove${subelement.elementName?cap_first}ConfigElement(final int index) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.removeElement(index);
	}

	/**
	 * Removes a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} to remove.
	 */
	public void remove${subelement.elementName?cap_first}ConfigElement(final ${subelement.configElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.removeElement(${subelement.elementName?uncap_first}Element);
	}
	</#if>
	<#elseif subelement.simpleElement>
	/**
	 * @return
	 */
	public ${subelement.configElementClassName} get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	}
	
	/**
	 * @return
	 */
	public ${subelement.valueClassName} ${subelement.getMethodPrefix}${subelement.elementName?cap_first}() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getValue();
	}

	/**
	 * @param value
	 */
	public void ${subelement.setMethodPrefix}${subelement.elementName?cap_first}(final ${subelement.valueClassName} value) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setValue(value);
	}
	</#if>
	</#list>
		
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final ${element.configElementClassName} other = (${element.configElementClassName}) obj;
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement || subelement.complexElement || subelement.simpleElement>
		if (${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} == null) {
			if (other.${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} != null) {
				return false;
			}
		}
		else if (!${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.equals(other.${subelement.elementName?uncap_first}${subelement.fieldNameSuffix})) {
			return false;
		}
		</#if>
		</#list>
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement || subelement.complexElement || subelement.simpleElement>
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.dispose();
		</#if>
		</#list>
		super.dispose();
	}
}
</#if>