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
package com.nextbreakpoint.nextfractal.core.scripting;

import com.nextbreakpoint.nextfractal.core.extension.Extension;

/**
 * @author Andrea Medeghini
 */
public class JSExtension {
	private final Extension<?> extension;

	/**
	 * @param extension
	 */
	public JSExtension(final Extension<?> extension) {
		this.extension = extension;
	}

	public Extension<?> getExtension() {
		return extension;
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#getExtensionId()
	 */
	public String getExtensionId() {
		return extension.getExtensionId();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.core.extension.Extension#getExtensionName()
	 */
	public String getExtensionName() {
		return extension.getExtensionName();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new JSExtension(extension);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != JSNodeValue.class) {
			return false;
		}
		return extension.equals(((JSExtension) obj).extension);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return extension.hashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return extension.toString();
	}
}
