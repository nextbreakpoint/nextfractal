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
package com.nextbreakpoint.nextfractal.core.extension;

import java.io.Serializable;

/**
 * An extension reference is a value object that identifies an extension.
 * 
 * @author Andrea Medeghini
 */
public class ExtensionReference implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String dumpString;
	private final String extensionId;
	private final String extensionName;

	/**
	 * Constructs a new extension reference.
	 * 
	 * @param extensionId the extensionId.
	 * @param extensionName the extensionName.
	 */
	public ExtensionReference(final String extensionId, final String extensionName) {
		this.extensionId = extensionId;
		this.extensionName = extensionName;
	}

	/**
	 * Constructs a new extension reference from a given reference.
	 * 
	 * @param reference the reference.
	 */
	public ExtensionReference(final ExtensionReference reference) {
		this(reference.getExtensionId(), reference.getExtensionName());
	}

	/**
	 * Returns the extensionId.
	 * 
	 * @return the extensionId.
	 */
	public String getExtensionId() {
		return extensionId;
	}

	/**
	 * Returns the extensionName.
	 * 
	 * @return the extensionName.
	 */
	public String getExtensionName() {
		return extensionName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (dumpString == null) {
			dumpString = dump(new StringBuilder()).toString();
		}
		return dumpString;
	}

	/**
	 * Builds the dump string.
	 * 
	 * @param builder the string builder.
	 * @return the dump string.
	 */
	protected StringBuilder dump(final StringBuilder builder) {
		builder.append("id = ");
		builder.append(extensionId);
		builder.append(", name = ");
		builder.append(extensionName);
		return builder;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ExtensionReference clone() {
		return new ExtensionReference(extensionId, extensionName);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		return (obj != null) && extensionId.equals(((ExtensionReference) obj).extensionId) && extensionName.equals(((ExtensionReference) obj).extensionName);
	}
}
