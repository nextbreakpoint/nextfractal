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
package com.nextbreakpoint.nextfractal.core.extension;

import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;

/**
 * Interface of extension runtimes.
 * 
 * @author Andrea Medeghini
 */
public abstract class ConfigurableExtensionRuntime<T extends ExtensionConfig> extends ExtensionRuntime implements ValueChangeListener {
	private T config;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#dispose()
	 */
	@Override
	public void dispose() {
		if (this.config != null) {
			this.config.removeChangeListener(this);
			this.config = null;
		}
	}

	/**
	 * Sets the config.
	 * 
	 * @param config the config to set.
	 */
	public final void setConfig(final T config) {
		this.config = config;
		if (this.config != null) {
			this.config.addChangeListener(this);
		}
		this.configReloaded();
	}

	/**
	 * Returns the config.
	 * 
	 * @return the config.
	 */
	public final T getConfig() {
		return this.config;
	}

	/**
	 * Called when config is changed.
	 */
	public void configReloaded() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueChangeListener#valueChanged(com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent)
	 */
	public void valueChanged(final ValueChangeEvent e) {
		fireChanged();
	}
}
