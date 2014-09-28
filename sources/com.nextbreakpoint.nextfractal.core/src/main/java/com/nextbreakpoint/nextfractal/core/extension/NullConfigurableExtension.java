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

/**
 * Null configuration extension.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 */
@SuppressWarnings("rawtypes")
public class NullConfigurableExtension implements ConfigurableExtension {
	private static NullConfigurableExtension instance = new NullConfigurableExtension();

	private NullConfigurableExtension() {
	}

	public ConfigurableExtensionRuntime<ExtensionConfig> createExtensionRuntime() throws ExtensionException {
		return null;
	}

	public String getExtensionId() {
		return "null";
	}

	public String getExtensionName() {
		return "-";
	}

	public ExtensionReference getExtensionReference() {
		return null;
	}

	public ConfigurableExtensionReference<ExtensionConfig> createConfigurableExtensionReference(final ExtensionConfig extensionConfig) {
		return null;
	}

	public ConfigurableExtensionReference<ExtensionConfig> createConfigurableExtensionReference() throws ExtensionException {
		return null;
	}

	public ExtensionConfig createDefaultExtensionConfig() throws ExtensionException {
		return null;
	}

	public static NullConfigurableExtension getInstance() {
		return instance;
	}
}
