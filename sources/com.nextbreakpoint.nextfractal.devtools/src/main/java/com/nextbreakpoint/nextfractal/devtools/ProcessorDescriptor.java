/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.devtools;

public class ProcessorDescriptor {
	private String elementName;
	private String elementType;
	private String elementClassId;
	private String configElementPackageName;
	private String configElementClassName;
	private String runtimeElementPackageName;
	private String runtimeElementClassName;
	private String extensionConfigPackageName;
	private String extensionConfigClassName;
	private String extensionRuntimePackageName;
	private String extensionRuntimeClassName;
	private String extensionRegistryPackageName;
	private String extensionRegistryClassName;
	private String registryPackageName;
	private String registryClassName;
	private String resourcesPackageName;
	private String resourcesClassName;
	private String valuePackageName;
	private String valueClassName;
	private String defaultValue;
	private String getMethodPrefix;
	private String setMethodPrefix;
	private ProcessorCardinality cardinality;

	public ProcessorDescriptor(String elementName, String elementType, String elementClassId, String configElementPackageName, String configElementClassName, String runtimeElementPackageName, String runtimeElementClassName, String extensionConfigPackageName, String extensionConfigClassName, String extensionRuntimePackageName, String extensionRuntimeClassName, String extensionRegistryPackageName, String extensionRegistryClassName, String registryPackageName, String registryClassName, String resourcesPackageName, String resourcesClassName, String valuePackageName, String valueClassName, String defaultValue, String getMethodPrefix, String setMethodPrefix, ProcessorCardinality cardinality) {
		this.elementName = elementName;
		this.elementType = elementType;
		this.elementClassId = elementClassId;
		this.configElementPackageName = configElementPackageName;
		this.configElementClassName = configElementClassName;
		this.runtimeElementPackageName = runtimeElementPackageName;
		this.runtimeElementClassName = runtimeElementClassName;
		this.extensionConfigPackageName = extensionConfigPackageName;
		this.extensionConfigClassName = extensionConfigClassName;
		this.extensionRuntimePackageName = extensionRuntimePackageName;
		this.extensionRuntimeClassName = extensionRuntimeClassName;
		this.extensionRegistryPackageName = extensionRegistryPackageName;
		this.extensionRegistryClassName = extensionRegistryClassName;
		this.registryPackageName = registryPackageName;
		this.registryClassName = registryClassName;
		this.resourcesPackageName = resourcesPackageName;
		this.resourcesClassName = resourcesClassName;
		this.valuePackageName = valuePackageName;
		this.valueClassName = valueClassName;
		this.defaultValue = defaultValue;
		this.getMethodPrefix = getMethodPrefix;
		this.setMethodPrefix = setMethodPrefix;
		this.cardinality = cardinality;
	}

	public String getElementName() {
		return elementName;
	}

	public String getElementType() {
		return elementType;
	}

	public String getElementClassId() {
		return elementClassId;
	}

	public String getConfigElementPackageName() {
		return configElementPackageName;
	}

	public String getConfigElementClassName() {
		return configElementClassName;
	}

	public String getRuntimeElementPackageName() {
		return runtimeElementPackageName;
	}

	public String getRuntimeElementClassName() {
		return runtimeElementClassName;
	}
	
	public String getExtensionConfigPackageName() {
		return extensionConfigPackageName;
	}
	
	public String getExtensionConfigClassName() {
		return extensionConfigClassName;
	}

	public String getExtensionRuntimePackageName() {
		return extensionRuntimePackageName;
	}

	public String getExtensionRuntimeClassName() {
		return extensionRuntimeClassName;
	}

	public String getExtensionRegistryPackageName() {
		return extensionRegistryPackageName;
	}

	public String getExtensionRegistryClassName() {
		return extensionRegistryClassName;
	}

	public String getRegistryPackageName() {
		return registryPackageName;
	}

	public String getRegistryClassName() {
		return registryClassName;
	}

	public String getResourcesPackageName() {
		return resourcesPackageName;
	}

	public String getResourcesClassName() {
		return resourcesClassName;
	}
	
	public String getValuePackageName() {
		return valuePackageName;
	}
	
	public String getValueClassName() {
		return valueClassName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public boolean isSimpleElement() {
		return elementName != null && elementType != null && elementClassId != null && configElementPackageName != null && configElementClassName != null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName == null && extensionRuntimeClassName == null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName == null && registryClassName == null && valuePackageName != null && valueClassName != null && getMethodPrefix != null && setMethodPrefix != null;
	}
	
	public boolean isComplexElement() {
		return elementName != null && elementType != null && elementClassId != null && configElementPackageName != null && configElementClassName != null && runtimeElementPackageName != null && runtimeElementClassName != null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName == null && extensionRuntimeClassName == null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName == null && registryClassName == null && valuePackageName == null && valueClassName == null && getMethodPrefix != null && setMethodPrefix != null && resourcesPackageName != null && resourcesClassName != null;
	}
	
	public boolean isExtensionElement() {
		return elementName != null && elementType != null && elementClassId != null && configElementPackageName != null && configElementClassName != null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName != null && extensionRuntimeClassName != null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName != null && registryClassName != null && valuePackageName == null && valueClassName == null && getMethodPrefix != null && setMethodPrefix != null;
	}
	
	public boolean isConfigurableExtensionElement() {
		return elementName != null && elementType != null && elementClassId != null && configElementPackageName != null && configElementClassName != null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName != null && extensionConfigClassName != null && extensionRuntimePackageName != null && extensionRuntimeClassName != null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName != null && registryClassName != null && valuePackageName == null && valueClassName == null && getMethodPrefix != null && setMethodPrefix != null;
	}
	
	public boolean isExtension() {
		return elementName != null && elementType != null && elementClassId == null && configElementPackageName == null && configElementClassName == null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName != null && extensionRuntimeClassName != null && valuePackageName == null && valueClassName == null && getMethodPrefix == null && setMethodPrefix == null;
	}
	
	public boolean isConfigurableExtension() {
		return elementName != null && elementType != null && elementClassId == null && configElementPackageName == null && configElementClassName == null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName != null && extensionConfigClassName != null && extensionRuntimePackageName != null && extensionRuntimeClassName != null && valuePackageName == null && valueClassName == null && getMethodPrefix == null && setMethodPrefix == null;
	}
	
	public boolean hasExtensionRegistry() {
		return extensionRegistryPackageName != null && extensionRegistryClassName != null;
	}
	
	public boolean isRegistry() {
		return elementName != null && elementType != null && elementClassId == null && configElementPackageName == null && configElementClassName == null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName == null && extensionRuntimeClassName == null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName != null && registryClassName != null && valuePackageName == null && valueClassName == null && getMethodPrefix == null && setMethodPrefix == null;
	}

	public boolean isResources() {
		return elementName != null && elementType != null && elementClassId == null && configElementPackageName == null && configElementClassName == null && runtimeElementPackageName == null && runtimeElementClassName == null && extensionConfigPackageName == null && extensionConfigClassName == null && extensionRuntimePackageName == null && extensionRuntimeClassName == null && extensionRegistryPackageName == null && extensionRegistryClassName == null && registryPackageName == null && registryClassName == null && valuePackageName == null && valueClassName == null && resourcesPackageName != null && resourcesClassName != null && getMethodPrefix == null && setMethodPrefix == null;
	}
	
	public boolean getSimpleElement() {
		return isSimpleElement();
	}
	
	public boolean getComplexElement() {
		return isComplexElement();
	}
	
	public boolean getExtensionElement() {
		return isExtensionElement();
	}
	
	public boolean getConfigurableExtensionElement() {
		return isConfigurableExtensionElement();
	}
	
	public boolean getExtension() {
		return isExtension();
	}
	
	public boolean getConfigurableExtension() {
		return isConfigurableExtension();
	}

	public String getGetMethodPrefix() {
		return getMethodPrefix;
	}

	public String getSetMethodPrefix() {
		return setMethodPrefix;
	}

	public String getFieldNameSuffix() {
		if (cardinality == ProcessorCardinality.NONE) {
			return "Element";
		}
		else if (cardinality == ProcessorCardinality.ONE) {
			return "SingleElement";
		}
		else if (cardinality == ProcessorCardinality.MANY) {
			return "ListElement";
		}
		return "Element";
	}

	public ProcessorCardinality getCardinality() {
		return cardinality;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[ elementName = ");
		builder.append(elementName);
		builder.append(", elementType = ");
		builder.append(elementName);
		builder.append(", elementClassId = ");
		builder.append(elementClassId);
		builder.append(", configElementPackageName = ");
		builder.append(configElementPackageName);
		builder.append(", configElementClassName = ");
		builder.append(configElementClassName);
		builder.append(", runtimeElementPackageName = ");
		builder.append(runtimeElementPackageName);
		builder.append(", runtimeElementClassName = ");
		builder.append(runtimeElementClassName);
		builder.append(", extensionConfigPackageName = ");
		builder.append(extensionConfigPackageName);
		builder.append(", extensionConfigClassName = ");
		builder.append(extensionConfigClassName);
		builder.append(", extensionRuntimePackageName = ");
		builder.append(extensionRuntimePackageName);
		builder.append(", extensionRuntimeClassName = ");
		builder.append(extensionRuntimeClassName);
		builder.append(", extensionRegistryPackageName = ");
		builder.append(extensionRegistryPackageName);
		builder.append(", extensionRegistryClassName = ");
		builder.append(extensionRegistryClassName);
		builder.append(", registryPackageName = ");
		builder.append(registryPackageName);
		builder.append(", registryClassName = ");
		builder.append(registryClassName);
		builder.append(", valuePackageName = ");
		builder.append(valuePackageName);
		builder.append(", valueClassName = ");
		builder.append(valueClassName);
		builder.append(", defaultValue = ");
		builder.append(defaultValue);
		builder.append(", getMethodPrefix = ");
		builder.append(getMethodPrefix);
		builder.append(", setMethodPrefix = ");
		builder.append(setMethodPrefix);
		builder.append(", cardinality = ");
		builder.append(cardinality);
		builder.append(" ]");
		return builder.toString();
	}
}
