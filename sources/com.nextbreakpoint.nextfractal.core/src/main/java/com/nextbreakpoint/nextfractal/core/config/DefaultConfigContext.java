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
package com.nextbreakpoint.nextfractal.core.config;

/**
 * Default implementation of a configuration context.
 * 
 * @author Andrea Medeghini
 */
public class DefaultConfigContext implements ConfigContext {
	private ConfigContext context;
	private long timestamp;

	/**
	 * Constructs a new context.
	 */
	public DefaultConfigContext() {
		updateTimestamp();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigContext#getTimestamp()
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigContext#updateTimestamp()
	 */
	@Override
	public void updateTimestamp() {
		timestamp = System.currentTimeMillis();
		if (context != null) {
			context.updateTimestamp();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigContext#setParentConfigContext(com.nextbreakpoint.nextfractal.core.config.ConfigContext)
	 */
	@Override
	public void setParentConfigContext(final ConfigContext context) {
		this.context = context;
	}
}
