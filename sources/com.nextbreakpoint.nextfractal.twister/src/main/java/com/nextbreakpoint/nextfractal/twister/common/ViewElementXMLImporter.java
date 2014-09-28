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
package com.nextbreakpoint.nextfractal.twister.common;

import java.util.StringTokenizer;

import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ViewElementXMLImporter extends ValueConfigElementXMLImporter<View, ViewElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected View parseValue(final String value) {
		final StringTokenizer tknz = new StringTokenizer(value, ";");
		if (tknz.hasMoreTokens()) {
			final IntegerVector4D status = IntegerVector4D.valueOf(tknz.nextToken());
			if (tknz.hasMoreTokens()) {
				final DoubleVector4D position = DoubleVector4D.valueOf(tknz.nextToken());
				if (tknz.hasMoreTokens()) {
					final DoubleVector4D rotation = DoubleVector4D.valueOf(tknz.nextToken());
					return new View(status, position, rotation);
				}
			}
		}
		throw new RuntimeException("Invalid format: " + value);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected ViewElement createDefaultConfigElement() {
		return new ViewElement(new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 0, 0)));
	}
}
