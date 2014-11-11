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
package com.nextbreakpoint.nextfractal.core.runtime.extension.sl;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionDescriptor;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;

/**
 * SL configurable extension.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 * @param <V> the extension configuration type.
 */
public class SLConfigurableExtension<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends SLExtension<T> implements ConfigurableExtension<T, V> {
	/**
	 * the name of the extension configuration class property.
	 */
	public static final String EXTENSION_CONFIG_CLASS_PROPERTY_NAME = "configClass";

	/**
	 * Constructs a new extension from a configuration element.
	 * 
	 * @param cfgElement the configuration element.
	 * @throws ExtensionException if the extension can't be created.
	 */
	protected SLConfigurableExtension(final ConfigurableExtensionDescriptor<T, V> extensionDescriptor) throws ExtensionException {
		super(extensionDescriptor);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension#createDefaultExtensionConfig()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final V createDefaultExtensionConfig() throws ExtensionException {
		return ((ConfigurableExtensionDescriptor<T, V>) getExtensionDescriptor()).getExtensionConfig();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension#createConfigurableExtensionReference(com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig)
	 */
	@Override
	public ConfigurableExtensionReference<V> createConfigurableExtensionReference(final V extensionConfig) {
		return new ConfigurableExtensionReference<V>(getExtensionId(), getExtensionName(), extensionConfig);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension#createConfigurableExtensionReference()
	 */
	@Override
	public ConfigurableExtensionReference<V> createConfigurableExtensionReference() throws ExtensionException {
		return new ConfigurableExtensionReference<V>(getExtensionId(), getExtensionName(), this.createDefaultExtensionConfig());
	}
}
