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
package com.nextbreakpoint.nextfractal.twister.extensionPoints.effect;

import com.nextbreakpoint.nextfractal.core.runtime.extension.sl.SLConfigurableExtensionBuilder;
import com.nextbreakpoint.nextfractal.core.runtime.extension.sl.SLConfigurableExtensionRegistry;

/**
 * @author Andrea Medeghini
 */
public class EffectExtensionRegistry extends SLConfigurableExtensionRegistry<EffectExtensionRuntime<? extends EffectExtensionConfig>, EffectExtensionConfig> {
	/**
	 * the extension point name.
	 */
	public static final String EXTENSION_POINT_NAME = "com.nextbreakpoint.nextfractal.twister.extensions";
	/**
	 * the configuration element name.
	 */
	public static final String CONFIGURATION_ELEMENT_NAME = "Effect";
	/**
	 * the extension descriptor class.
	 */
	public static final Class<? extends EffectExtensionDescriptor> EXTENSION_DESCRIPTOR_CLASS = EffectExtensionDescriptor.class;

	/**
	 * Constructs a new registry.
	 */
	public EffectExtensionRegistry() {
		super(EffectExtensionRegistry.EXTENSION_DESCRIPTOR_CLASS, EffectExtensionRegistry.EXTENSION_POINT_NAME, new SLConfigurableExtensionBuilder<EffectExtensionRuntime<? extends EffectExtensionConfig>, EffectExtensionConfig>(EffectExtensionRegistry.CONFIGURATION_ELEMENT_NAME));
	}
}
