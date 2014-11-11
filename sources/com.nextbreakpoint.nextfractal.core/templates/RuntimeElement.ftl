/*
 * $Id:$
 *
 */
package ${element.runtimeElementPackageName};

<#list imports as import>
import ${import};
</#list>

/**
 * @author ${author}
 */
 public class ${element.runtimeElementClassName} extends RuntimeElement {
	private ${element.configElementClassName} ${element.elementName?uncap_first}Element;
	<#list subelements as subelement>
	<#if subelement.extensionElement>
	private ${subelement.extensionRuntimeClassName} ${subelement.elementName?uncap_first}Runtime;
	private ${subelement.elementName?cap_first}Listener ${subelement.elementName?uncap_first}Listener;
	<#elseif subelement.configurableExtensionElement>
	private ${subelement.extensionRuntimeClassName}<?> ${subelement.elementName?uncap_first}Runtime;
	private ${subelement.elementName?cap_first}Listener ${subelement.elementName?uncap_first}Listener;
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private ${subelement.runtimeElementClassName} ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	<#elseif subelement.cardinality == "ONE">
	private SingleRuntimeElement<${subelement.runtimeElementClassName}> ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	<#elseif subelement.cardinality == "MANY">
	private ListRuntimeElement<${subelement.runtimeElementClassName}> ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix};
	</#if>
	private ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Listener ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener;
	<#elseif subelement.simpleElement>
	private ${subelement.valueClassName} ${subelement.elementName?uncap_first};
	private ${subelement.elementName?cap_first}Listener ${subelement.elementName?uncap_first}Listener;
	</#if>
	</#list>

	/**
	 * Constructs a new ${element.runtimeElementClassName}.
	 * 
	 * @param registry
	 * @param ${element.runtimeElementClassName}Element
	 */
	public ${element.runtimeElementClassName}(final ${element.configElementClassName} ${element.elementName?uncap_first}Element) {
		if (${element.elementName?uncap_first}Element == null) {
			throw new IllegalArgumentException("${element.elementName?uncap_first}Element is null");
		}
		this.${element.elementName?uncap_first}Element = ${element.elementName?uncap_first}Element;
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		createRuntime(${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Reference());
		${subelement.elementName?uncap_first}Listener = new ${subelement.elementName?cap_first}Listener();
		${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Element().addChangeListener(${subelement.elementName?uncap_first}Listener);
		<#elseif subelement.complexElement>
		<#if subelement.cardinality == "NONE">
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ${subelement.runtimeElementClassName}(${element.elementName?uncap_first}Element.${subelement.getMethodPrefix}${subelement.elementName?cap_first}Element());
		<#elseif subelement.cardinality == "ONE">
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new SingleRuntimeElement<${subelement.runtimeElementClassName}>();
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setElement(new ${subelement.runtimeElementClassName}(${element.elementName?uncap_first}Element.${subelement.getMethodPrefix}${subelement.elementName?cap_first}Element()));
		<#elseif subelement.cardinality == "MANY">
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix} = new ListRuntimeElement<${subelement.runtimeElementClassName}>();
		for (int i = 0; i < ${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}ConfigElementCount(); i++) {
			${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.appendElement(new ${subelement.runtimeElementClassName}(${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}ConfigElement(i)));
		}
		</#if>
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener = new ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Listener();
		${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}().addChangeListener(${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener);
		<#elseif subelement.simpleElement>
		${subelement.setMethodPrefix}${subelement.elementName?cap_first}(${element.elementName?uncap_first}Element.${subelement.getMethodPrefix}${subelement.elementName?cap_first}());
		${subelement.elementName?uncap_first}Listener = new ${subelement.elementName?cap_first}Listener();
		${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Element().addChangeListener(${subelement.elementName?uncap_first}Listener);
		</#if>
		</#list>
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		if ((${element.elementName?uncap_first}Element != null) && (${subelement.elementName?uncap_first}Listener != null)) {
			${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Element().removeChangeListener(${subelement.elementName?uncap_first}Listener);
		}
		${subelement.elementName?uncap_first}Listener = null;
		<#elseif subelement.complexElement>
		if ((${element.elementName?uncap_first}Element != null) && (${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener != null)) {
			${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}${subelement.fieldNameSuffix}().removeChangeListener(${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener);
		}
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.dispose();
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}Listener = null;
		<#elseif subelement.simpleElement>
		if ((${element.elementName?uncap_first}Element != null) && (${subelement.elementName?uncap_first}Listener != null)) {
			${element.elementName?uncap_first}Element.get${subelement.elementName?cap_first}Element().removeChangeListener(${subelement.elementName?uncap_first}Listener);
		}
		${subelement.elementName?uncap_first}Listener = null;
		</#if>
		</#list>
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		if (${subelement.elementName?uncap_first}Runtime != null) {
			${subelement.elementName?uncap_first}Runtime.dispose();
			${subelement.elementName?uncap_first}Runtime = null;
		}
		</#if>
		</#list>
		${element.elementName?uncap_first}Element = null;
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean ${element.elementName?uncap_first}Changed = false;
		<#list subelements as subelement>
		<#if subelement.extensionElement || subelement.configurableExtensionElement>
		${element.elementName?uncap_first}Changed |= (${subelement.elementName?uncap_first}Runtime != null) && ${subelement.elementName?uncap_first}Runtime.isChanged();
		</#if>
		</#list>
		return super.isChanged() || ${element.elementName?uncap_first}Changed;
	}

	<#list subelements as subelement>
	<#if subelement.extensionElement || subelement.configurableExtensionElement>
	<#if subelement.extensionElement>
	@SuppressWarnings("unchecked")
	private void createRuntime(final ExtensionReference reference) {
		try {
			if (reference != null) {
				final ${subelement.extensionRuntimeClassName} ${subelement.elementName?uncap_first}Runtime = ${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Extension(reference.getExtensionId()).createExtensionRuntime();
				set${subelement.elementName?cap_first}Runtime(${subelement.elementName?uncap_first}Runtime);
			}
			else {
				set${subelement.elementName?cap_first}Runtime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * @return the ${subelement.extensionRuntimeClassName}Runtime
	 */
	public ${subelement.extensionRuntimeClassName} get${subelement.elementName?cap_first}Runtime() {
		return ${subelement.elementName?uncap_first}Runtime;
	}

	private void set${subelement.elementName?cap_first}Runtime(final ${subelement.extensionRuntimeClassName} ${subelement.elementName?uncap_first}Runtime) {
		if (this.${subelement.elementName?uncap_first}Runtime != null) {
			this.${subelement.elementName?uncap_first}Runtime.dispose();
		}
		this.${subelement.elementName?uncap_first}Runtime = ${subelement.elementName?uncap_first}Runtime;
	}
	
	private class ${subelement.elementName?cap_first}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ExtensionReference) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	<#elseif subelement.configurableExtensionElement>
	@SuppressWarnings("unchecked")
	private void createRuntime(final ConfigurableExtensionReference<${subelement.extensionConfigClassName}> reference) {
		try {
			if (reference != null) {
				final ${subelement.extensionRuntimeClassName} ${subelement.elementName?uncap_first}Runtime = ${subelement.registryClassName}.getInstance().get${subelement.elementType?cap_first}Extension(reference.getExtensionId()).createExtensionRuntime();
				final ${subelement.extensionConfigClassName} ${subelement.elementName?uncap_first}Config = reference.getExtensionConfig();
				${subelement.elementName?uncap_first}Runtime.setConfig(${subelement.elementName?uncap_first}Config);
				set${subelement.elementName?cap_first}Runtime(${subelement.elementName?uncap_first}Runtime);
			}
			else {
				set${subelement.elementName?cap_first}Runtime(null);
			}
		}
		catch (final ExtensionNotFoundException e) {
			e.printStackTrace();
		}
		catch (final ExtensionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the ${subelement.extensionRuntimeClassName}Runtime
	 */
	public ${subelement.extensionRuntimeClassName}<?> get${subelement.elementName?cap_first}Runtime() {
		return ${subelement.elementName?uncap_first}Runtime;
	}

	private void set${subelement.elementName?cap_first}Runtime(final ${subelement.extensionRuntimeClassName}<?> ${subelement.elementName?uncap_first}Runtime) {
		if (this.${subelement.elementName?uncap_first}Runtime != null) {
			this.${subelement.elementName?uncap_first}Runtime.dispose();
		}
		this.${subelement.elementName?uncap_first}Runtime = ${subelement.elementName?uncap_first}Runtime;
	}
	
	private class ${subelement.elementName?cap_first}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ExtensionReferenceElement.EXTENSION_REFERENCE_CHANGED: {
					createRuntime((ConfigurableExtensionReference<${subelement.extensionConfigClassName}>) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	</#if>
	<#elseif subelement.complexElement>
	<#if subelement.cardinality == "NONE">
	private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			fireChanged();
		}
	}
	<#elseif subelement.cardinality == "ONE">
	/**
	 * @return
	 */
	public ${subelement.runtimeElementClassName} get${subelement.elementName?cap_first}Element() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getElement();
	}

	private void set${subelement.elementName?cap_first}Element(${subelement.runtimeElementClassName} element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setElement(element);
	}
	
	private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case SingleConfigElement.VALUE_CHANGED: {
					set${subelement.elementName?cap_first}Element(new ${subelement.runtimeElementClassName} ((${subelement.configElementClassName}) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	<#elseif subelement.cardinality == "MANY">
	/**
	 * Returns a ${subelement.elementName?uncap_first} element.
	 * 
	 * @param index the ${subelement.elementName?uncap_first} index.
	 * @return the ${subelement.elementName?uncap_first}.
	 */
	public ${subelement.runtimeElementClassName} get${subelement.elementName?cap_first}Element(final int index) {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getElement(index);
	}

	/**
	 * Returns a ${subelement.elementName?uncap_first} element index.
	 * 
	 * @param ${subelement.elementName?uncap_first}Element the ${subelement.elementName?uncap_first} element.
	 * @return the index.
	 */
	public int indexOf${subelement.elementName?cap_first}Element(final ${subelement.runtimeElementClassName} ${subelement.elementName?uncap_first}Element) {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.indexOfElement(${subelement.elementName?uncap_first}Element);
	}

	/**
	 * Returns the number of ${subelement.elementName?uncap_first} elements.
	 * 
	 * @return the number of ${subelement.elementName?uncap_first} elements.
	 */
	public int get${subelement.elementName?cap_first}ElementCount() {
		return ${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.getElementCount();
	}

	private void set${subelement.elementName?cap_first}Element(final int index, ${subelement.runtimeElementClassName} element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.setElement(index, element);
	}

	private void append${subelement.elementName?cap_first}Element(final ${subelement.runtimeElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.appendElement(${subelement.elementName?uncap_first}Element);
	}

	private void insert${subelement.elementName?cap_first}ElementAfter(final int index, final ${subelement.runtimeElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.insertElementAfter(index, ${subelement.elementName?uncap_first}Element);
	}

	private void insert${subelement.elementName?cap_first}ElementBefore(final int index, final ${subelement.runtimeElementClassName} ${subelement.elementName?uncap_first}Element) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.insertElementBefore(index, ${subelement.elementName?uncap_first}Element);
	}

	private void remove${subelement.elementName?cap_first}Element(final int index) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.removeElement(index);
	}

	private void moveUp${subelement.elementName?cap_first}Element(final int index) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.moveElementUp(index);
	}

	private void moveDown${subelement.elementName?cap_first}Element(final int index) {
		${subelement.elementName?uncap_first}${subelement.fieldNameSuffix}.moveElementDown(index);
	}
	
	private class ${subelement.elementName?cap_first}${subelement.fieldNameSuffix}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ListConfigElement.ELEMENT_ADDED: {
					append${subelement.elementName?cap_first}Element(new ${subelement.runtimeElementClassName} ((${subelement.configElementClassName}) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_AFTER: {
					insert${subelement.elementName?cap_first}ElementAfter(((Integer) e.getParams()[1]).intValue(), new ${subelement.runtimeElementClassName} ((${subelement.configElementClassName}) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_INSERTED_BEFORE: {
					insert${subelement.elementName?cap_first}ElementBefore(((Integer) e.getParams()[1]).intValue(), new ${subelement.runtimeElementClassName} ((${subelement.configElementClassName}) e.getParams()[0]));
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_REMOVED: {
					remove${subelement.elementName?cap_first}Element(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_UP: {
					moveUp${subelement.elementName?cap_first}Element(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_MOVED_DOWN: {
					moveDown${subelement.elementName?cap_first}Element(((Integer) e.getParams()[1]).intValue());
					fireChanged();
					break;
				}
				case ListConfigElement.ELEMENT_CHANGED: {
					set${subelement.elementName?cap_first}Element(((Integer) e.getParams()[1]).intValue(), new ${subelement.runtimeElementClassName} ((${subelement.configElementClassName}) e.getParams()[0]));
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	</#if>
	<#elseif subelement.simpleElement>
	/**
	 * @return the ${subelement.elementName?uncap_first}.
	 */
	public ${subelement.valueClassName} ${subelement.getMethodPrefix}${subelement.elementName?cap_first}() {
		return ${subelement.elementName?uncap_first};
	}

	private void ${subelement.setMethodPrefix}${subelement.elementName?cap_first}(final ${subelement.valueClassName} ${subelement.elementName?uncap_first}) {
		this.${subelement.elementName?uncap_first} = ${subelement.elementName?uncap_first};
	}
	
	private class ${subelement.elementName?cap_first}Listener implements ValueChangeListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.ElementChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.runtime.ElementChangeEvent)
		 */
		public void valueChanged(final ValueChangeEvent e) {
			switch (e.getEventType()) {
				case ValueConfigElement.VALUE_CHANGED: {
					${subelement.setMethodPrefix}${subelement.elementName?cap_first}((${subelement.valueClassName}) e.getParams()[0]);
					fireChanged();
					break;
				}
				default: {
					break;
				}
			}
		}
	}
	</#if>
	</#list>
}
