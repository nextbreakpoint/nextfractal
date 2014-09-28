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
package com.nextbreakpoint.nextfractal.core.extension.sl;

import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionDescriptor;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime;

/**
 * SL extension.
 * 
 * @author Andrea Medeghini
 * @param <T> the extension runtime type.
 */
public class SLExtension<T extends ExtensionRuntime> implements Extension<T> {
	/**
	 * the name of the extensionId property.
	 */
	public static final String EXTENSION_ID_PROPERTY_NAME = "id";
	/**
	 * the name of the extensionName property.
	 */
	public static final String EXTENSION_NAME_PROPERTY_NAME = "name";
	/**
	 * the name of the extension runtime class property.
	 */
	public static final String EXTENSION_RUNTIME_CLASS_PROPERTY_NAME = "runtimeClass";
	private ExtensionDescriptor<T> extensionDescriptor;
	private final ExtensionReference reference;

	/**
	 * Constructs a new extension from a configuration element.
	 * 
	 * @param extensionDescriptor the extension descriptor.
	 * @throws ExtensionException if the extension can't be created.
	 */
	protected SLExtension(final ExtensionDescriptor<T> extensionDescriptor) throws ExtensionException {
		this.extensionDescriptor = extensionDescriptor;
		this.reference = new ExtensionReference(extensionDescriptor.getExtensionId(), extensionDescriptor.getExtensionName());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#createExtensionRuntime()
	 */
	public final T createExtensionRuntime() throws ExtensionException {
		return this.extensionDescriptor.getExtensionRuntime();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#getExtensionReference()
	 */
	public ExtensionReference getExtensionReference() {
		return this.reference;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#getExtensionId()
	 */
	public String getExtensionId() {
		return this.reference.getExtensionId();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#getExtensionName()
	 */
	public String getExtensionName() {
		return this.reference.getExtensionName();
	}

	/**
	 * Returns the extension descriptor class.
	 * @return the extension descriptor class.
	 */
	protected ExtensionDescriptor<T> getExtensionDescriptor() {
		return extensionDescriptor;
	}
}
