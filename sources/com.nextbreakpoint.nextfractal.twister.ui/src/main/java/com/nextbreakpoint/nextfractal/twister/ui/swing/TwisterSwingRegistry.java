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
package com.nextbreakpoint.nextfractal.twister.ui.swing;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.ui.swing.inputAdapter.extension.InputAdapterExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.ui.swing.inputAdapter.extension.InputAdapterExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.ui.swing.view.extension.ViewExtensionRegistry;
import com.nextbreakpoint.nextfractal.twister.ui.swing.view.extension.ViewExtensionRuntime;

/**
 * The twister registry.
 * 
 * @author Andrea Medeghini
 */
public class TwisterSwingRegistry {
	private ExtensionRegistry<ViewExtensionRuntime> viewRegistry;
	private ExtensionRegistry<InputAdapterExtensionRuntime> inputAdapterRegistry;

	private static class RegistryHolder {
		private static final TwisterSwingRegistry instance = new TwisterSwingRegistry();
	}

	private TwisterSwingRegistry() {
		setViewRegistry(new ViewExtensionRegistry());
		setInputAdapterRegistry(new InputAdapterExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static TwisterSwingRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a view extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<ViewExtensionRuntime> getViewExtension(final String extensionId) throws ExtensionNotFoundException {
		return viewRegistry.getExtension(extensionId);
	}

	/**
	 * Returns a inputAdapter extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<InputAdapterExtensionRuntime> getInputAdapterExtension(final String extensionId) throws ExtensionNotFoundException {
		return inputAdapterRegistry.getExtension(extensionId);
	}

	private void setViewRegistry(final ExtensionRegistry<ViewExtensionRuntime> viewRegistry) {
		this.viewRegistry = viewRegistry;
	}

	private void setInputAdapterRegistry(final ExtensionRegistry<InputAdapterExtensionRuntime> inputAdapterRegistry) {
		this.inputAdapterRegistry = inputAdapterRegistry;
	}

	/**
	 * @return the viewRegistry
	 */
	public ExtensionRegistry<ViewExtensionRuntime> getViewRegistry() {
		return viewRegistry;
	}

	/**
	 * @return the inputAdapterRegistry
	 */
	public ExtensionRegistry<InputAdapterExtensionRuntime> getInputAdapterRegistry() {
		return inputAdapterRegistry;
	}
}
