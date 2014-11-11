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
package com.nextbreakpoint.nextfractal.core.elements;

import com.nextbreakpoint.nextfractal.core.runtime.ConfigElement;
import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;

/**
 * @author Andrea Medeghini
 */
public class ColorElement extends ValueConfigElement<Color32bit> {
	public static final String CLASS_ID = "Color";
	private static final long serialVersionUID = 1L;

	/**
	 * @param defaultValue
	 */
	public ColorElement(final Color32bit defaultValue) {
		super(ColorElement.CLASS_ID, defaultValue);
	}

	/**
	 * @return
	 */
	@Override
	public ColorElement clone() {
		return new ColorElement(getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	public void copyFrom(ConfigElement source) {
		final ColorElement element = (ColorElement) source;
		setValue(element.getValue());
	}
}
