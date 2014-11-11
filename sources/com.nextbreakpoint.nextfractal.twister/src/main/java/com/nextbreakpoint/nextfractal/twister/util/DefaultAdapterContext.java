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
package com.nextbreakpoint.nextfractal.twister.util;

import java.util.HashMap;
import java.util.Map;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class DefaultAdapterContext implements AdapterContext {
	private static final long serialVersionUID = 1L;
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	private final ExtensionConfig config;

	/**
	 * @param config
	 */
	public DefaultAdapterContext(final ExtensionConfig config) {
		this.config = config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.util.AdapterContext.InputAdapterContext#getFinalConfig()
	 */
	@Override
	public ExtensionConfig getConfig() {
		return config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.util.AdapterContext.InputAdapterContext#setAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setAttribute(final String name, final Object value) {
		attributes.put(name, value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.util.AdapterContext.InputAdapterContext#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(final String name) {
		return attributes.get(name);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.util.AdapterContext#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(final String name) {
		attributes.remove(name);
	}
}
