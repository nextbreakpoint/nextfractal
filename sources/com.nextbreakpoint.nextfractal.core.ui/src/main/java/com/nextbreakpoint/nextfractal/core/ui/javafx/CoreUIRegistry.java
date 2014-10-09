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
package com.nextbreakpoint.nextfractal.core.ui.javafx;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.editor.extensioin.EditorExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.ui.javafx.editor.extensioin.EditorExtensionRuntime;

/**
 * The twister registry.
 * 
 * @author Andrea Medeghini
 */
public class CoreUIRegistry {
	private ExtensionRegistry<EditorExtensionRuntime> editorRegistry;

	private static class RegistryHolder {
		private static final CoreUIRegistry instance = new CoreUIRegistry();
	}

	private CoreUIRegistry() {
		setEditorRegistry(new EditorExtensionRegistry());
	}

	/**
	 * @return
	 */
	public static CoreUIRegistry getInstance() {
		return RegistryHolder.instance;
	}

	/**
	 * Returns a editor extension.
	 * 
	 * @param extensionId the extensionId.
	 * @return the extension.
	 * @throws ExtensionNotFoundException
	 */
	public Extension<EditorExtensionRuntime> getEditorExtension(final String extensionId) throws ExtensionNotFoundException {
		return editorRegistry.getExtension(extensionId);
	}

	private void setEditorRegistry(final ExtensionRegistry<EditorExtensionRuntime> editorRegistry) {
		this.editorRegistry = editorRegistry;
	}

	/**
	 * @return the editorRegistry
	 */
	public ExtensionRegistry<EditorExtensionRuntime> getEditorRegistry() {
		return editorRegistry;
	}
}
