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
package com.nextbreakpoint.nextfractal.queue;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.queue.encoder.extension.EncoderExtensionConfig;
import com.nextbreakpoint.nextfractal.queue.encoder.extension.EncoderExtensionRegistry;
import com.nextbreakpoint.nextfractal.queue.encoder.extension.EncoderExtensionRuntime;
import com.nextbreakpoint.nextfractal.queue.spool.extension.SpoolExtensionConfig;
import com.nextbreakpoint.nextfractal.queue.spool.extension.SpoolExtensionRegistry;
import com.nextbreakpoint.nextfractal.queue.spool.extension.SpoolExtensionRuntime;

/**
 * The service registry.
 * 
 * @author Andrea Medeghini
 */
public class RenderServiceRegistry {
	private ConfigurableExtensionRegistry<EncoderExtensionRuntime<?>, EncoderExtensionConfig> encoderRegistry;
	private ConfigurableExtensionRegistry<SpoolExtensionRuntime<?>, SpoolExtensionConfig> spoolRegistry;

	private static class RegistryHolder {
		private static final RenderServiceRegistry instance = new RenderServiceRegistry();
	}

	private RenderServiceRegistry() {
		setEncoderRegistry(new EncoderExtensionRegistry());
		setSpoolRegistry(new SpoolExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static RenderServiceRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns an encoder extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<EncoderExtensionRuntime<?>, EncoderExtensionConfig> getEncoderExtension(final String extensionId) throws ExtensionNotFoundException {
		return encoderRegistry.getConfigurableExtension(extensionId);
	}

	/**
	 * Returns a spool extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public ConfigurableExtension<SpoolExtensionRuntime<?>, SpoolExtensionConfig> getSpoolExtension(final String extensionId) throws ExtensionNotFoundException {
		return spoolRegistry.getConfigurableExtension(extensionId);
	}

	private void setEncoderRegistry(final ConfigurableExtensionRegistry<EncoderExtensionRuntime<?>, EncoderExtensionConfig> encoderRegistry) {
		this.encoderRegistry = encoderRegistry;
	}

	private void setSpoolRegistry(final ConfigurableExtensionRegistry<SpoolExtensionRuntime<?>, SpoolExtensionConfig> spoolRegistry) {
		this.spoolRegistry = spoolRegistry;
	}

	/**
	 * @return the encoderRegistry
	 */
	public ConfigurableExtensionRegistry<EncoderExtensionRuntime<?>, EncoderExtensionConfig> getEncoderRegistry() {
		return encoderRegistry;
	}

	/**
	 * @return the encoderRegistry
	 */
	public ConfigurableExtensionRegistry<SpoolExtensionRuntime<?>, SpoolExtensionConfig> getSpoolRegistry() {
		return spoolRegistry;
	}
}
