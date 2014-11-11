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
package com.nextbreakpoint.nextfractal.twister.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Palette;

/**
 * @author Andrea Medeghini
 */
public class PaletteElement<T extends Palette> extends ValueConfigElement<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * @param defaultValue
	 */
	public PaletteElement(final String classId, final T defaultValue) {
		super(classId, defaultValue);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement#clone()
	 */
	@Override
	public PaletteElement<T> clone() {
		return new PaletteElement<T>(getClassId(), getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void copyFrom(ConfigElement source) {
		PaletteElement<T> element = (PaletteElement<T>) source;
		setValue(element.getValue());
	}
}
