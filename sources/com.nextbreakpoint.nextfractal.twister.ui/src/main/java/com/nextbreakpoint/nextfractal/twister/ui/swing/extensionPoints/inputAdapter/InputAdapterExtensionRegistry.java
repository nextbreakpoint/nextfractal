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
package com.nextbreakpoint.nextfractal.twister.ui.swing.extensionPoints.inputAdapter;

import com.nextbreakpoint.nextfractal.core.extension.sl.SLExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.extension.sl.SLExtensionRegistry;

/**
 * @author Andrea Medeghini
 */
public class InputAdapterExtensionRegistry extends SLExtensionRegistry<InputAdapterExtensionRuntime> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "com.nextbreakpoint.nextfractal.twister.swing.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "InputAdapter";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends InputAdapterExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = InputAdapterExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public InputAdapterExtensionRegistry() {
		super(InputAdapterExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, InputAdapterExtensionRegistry.EXTENSION_POINT_NAME, new SLExtensionBuilder<InputAdapterExtensionRuntime>(InputAdapterExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
