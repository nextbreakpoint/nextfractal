/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package ${packageName};

import com.nextbreakpoint.nextfractal.core.extension.sl.SLConfigurableExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.extension.sl.SLConfigurableExtensionRegistry;
import ${runtimePackage}.${runtimeType};
import ${configPackage}.${configType};

/**
 * @author Andrea Medeghini
 */
public class ${elementName}ExtensionRegistry extends SLConfigurableExtensionRegistry<${runtimeType}<? extends ${configType}>, ${configType}> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "${extensionPointId}";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "${elementName}";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends ${elementName}ExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = ${elementName}ExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public ${elementName}ExtensionRegistry() {
		super(${elementName}ExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, ${elementName}ExtensionRegistry.EXTENSION_POINT_NAME, new SLConfigurableExtensionBuilder<${runtimeType}<? extends ${configType}>, ${configType}>(${elementName}ExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
