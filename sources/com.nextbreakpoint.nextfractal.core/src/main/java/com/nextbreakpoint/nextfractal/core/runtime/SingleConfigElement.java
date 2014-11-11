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
package com.nextbreakpoint.nextfractal.core.runtime;

/**
 * Value element.
 * 
 * @author Andrea Medeghini
 */
public class SingleConfigElement<T extends ConfigElement> extends ValueConfigElement<T> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new element.
	 */
	public SingleConfigElement(final String configElementId) {
		super(configElementId);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.AbstractConfigElement#setContext(com.nextbreakpoint.nextfractal.core.runtime.ConfigContext)
	 */
	@Override
	public void setContext(final ConfigContext context) {
		super.setContext(context);
		if (getValue() != null) {
			getValue().setContext(context);
		}
	}

	/**
	 * Ses the value.
	 * 
	 * @param value the value to set.
	 */
	@Override
	public void setValue(final T value) {
		if (value != null) {
			if (checkContext()) {
				value.setContext(getContext());
			}
		}
		super.setValue(value);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public SingleConfigElement<T> clone() {
		final SingleConfigElement<T> element = new SingleConfigElement<T>(getClassId());
		element.setValue((T) getValue().clone());
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.ConfigElement#copyFrom(com.nextbreakpoint.nextfractal.core.runtime.ConfigElement)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void copyFrom(ConfigElement source) {
		SingleConfigElement<T> element = (SingleConfigElement<T>) source;
		if (element.getValue() != null) {
			setValue((T) element.getValue().clone());
		}
	}
}
