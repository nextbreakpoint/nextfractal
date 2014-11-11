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

import com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.Rectangle;

/**
 * @author Andrea Medeghini
 */
public class RectangleElementXMLImporter extends ValueConfigElementXMLImporter<Rectangle, RectangleElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected RectangleElement createDefaultConfigElement() {
		return new RectangleElement(new Rectangle(0, 0, 0, 0));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected Rectangle parseValue(final String value) {
		return Rectangle.parseRectangle(value);
	}
}
