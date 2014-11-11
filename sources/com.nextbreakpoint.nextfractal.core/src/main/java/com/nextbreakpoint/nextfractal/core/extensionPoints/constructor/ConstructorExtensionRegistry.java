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
package com.nextbreakpoint.nextfractal.core.extensionPoints.constructor;

import com.nextbreakpoint.nextfractal.core.runtime.extension.sl.SLExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.extension.sl.SLExtensionRegistry;

/**
 * @author Andrea Medeghini
 */
public class ConstructorExtensionRegistry extends SLExtensionRegistry<ConstructorExtensionRuntime> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "com.nextbreakpoint.nextfractal.core.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "Constructor";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends ConstructorExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = ConstructorExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public ConstructorExtensionRegistry() {
		super(ConstructorExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, ConstructorExtensionRegistry.EXTENSION_POINT_NAME, new SLExtensionBuilder<ConstructorExtensionRuntime>(ConstructorExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
