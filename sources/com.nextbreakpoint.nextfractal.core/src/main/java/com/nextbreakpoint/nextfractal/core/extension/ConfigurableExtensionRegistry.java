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

import java.util.List;

/**
 * Interface of configurable extension registries.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 * @param <V> the extension configuration type.
 */
public interface ConfigurableExtensionRegistry<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends ExtensionRegistry<T> {
	/**
	 * Returns an extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException if the extension can't be found.
	 */
	public ConfigurableExtension<T, V> getConfigurableExtension(String extensionId) throws ExtensionNotFoundException;

	/**
	 * Returns the list of extensions.
	 * 
	 * @return the list of extensions.
	 */
	public List<ConfigurableExtension<T, V>> getConfigurableExtensionList();
}
