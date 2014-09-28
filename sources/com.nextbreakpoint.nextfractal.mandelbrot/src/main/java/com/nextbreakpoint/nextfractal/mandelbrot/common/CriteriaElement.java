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
package com.nextbreakpoint.nextfractal.mandelbrot.common;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;

/**
 * @author Andrea Medeghini
 */
public class CriteriaElement extends ValueConfigElement<Integer> {
	public static final String CLASS_ID = "OrbitTrapCriteria";
	private static final long serialVersionUID = 1L;
	public static final int FIRST_OUT = 0;

	/**
	 * @param defaultValue
	 */
	public CriteriaElement(final Integer defaultValue) {
		super(CriteriaElement.CLASS_ID, defaultValue);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElement#clone()
	 */
	@Override
	public CriteriaElement clone() {
		CriteriaElement criteriaElement = new CriteriaElement(getValue());
		return criteriaElement;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.config.ConfigElement)
	 */
	public void copyFrom(ConfigElement source) {
		final CriteriaElement element = (CriteriaElement) source;
		setValue(element.getValue());
	}
}
