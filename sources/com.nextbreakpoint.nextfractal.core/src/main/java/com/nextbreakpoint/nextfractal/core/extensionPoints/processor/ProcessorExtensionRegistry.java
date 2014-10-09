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
package com.nextbreakpoint.nextfractal.core.extensionPoints.processor;

import com.nextbreakpoint.nextfractal.core.extension.sl.SLExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.extension.sl.SLExtensionRegistry;

/**
 * @author Andrea Medeghini
 */
public class ProcessorExtensionRegistry extends SLExtensionRegistry<ProcessorExtensionRuntime> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "com.nextbreakpoint.nextfractal.core.devtools.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "Processor";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends ProcessorExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = ProcessorExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public ProcessorExtensionRegistry() {
		super(ProcessorExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, ProcessorExtensionRegistry.EXTENSION_POINT_NAME, new SLExtensionBuilder<ProcessorExtensionRuntime>(ProcessorExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
