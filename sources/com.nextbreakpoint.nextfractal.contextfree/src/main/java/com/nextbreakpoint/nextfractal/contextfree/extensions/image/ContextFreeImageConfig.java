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
package com.nextbreakpoint.nextfractal.contextfree.extensions.image;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfig;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfigBuilder;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeImageConfig extends ImageExtensionConfig {
	public static final String CLASS_ID = "ContextFreeImageConfig";
	private static final long serialVersionUID = 1L;
	private ContextFreeConfig config;

	/**
	 * 
	 */
	@Override
	protected void createConfigElements() {
		try {
			final ContextFreeConfigBuilder configBuilder = new ContextFreeConfigBuilder(this);
			config = configBuilder.createDefaultConfig();
		}
		catch (final ExtensionException e) {
			throw new Error(e);
		}
	}

	/**
	 * @return the config
	 */
	public ContextFreeConfig getContextFreeConfig() {
		return config;
	}

	/**
	 * @param config
	 */
	protected void setContextFreeConfig(final ContextFreeConfig config) {
		this.config = config;
	}

	/**
	 * @return
	 */
	@Override
	public ContextFreeImageConfig clone() {
		final ContextFreeImageConfig config = new ContextFreeImageConfig();
		config.setContextFreeConfig(getContextFreeConfig().clone());
		return config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig#setContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		if (config != null) {
			config.setContext(getContext());
		}
	}

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
		final ContextFreeImageConfig other = (ContextFreeImageConfig) obj;
		if (config == null) {
			if (other.config != null) {
				return false;
			}
		}
		else if (!config.equals(other.config)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	@Override
	public void dispose() {
		if (config != null) {
			config.dispose();
			config = null;
		}
		super.dispose();
	}
}
