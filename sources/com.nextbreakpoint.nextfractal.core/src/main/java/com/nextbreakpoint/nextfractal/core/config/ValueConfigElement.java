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
package com.nextbreakpoint.nextfractal.core.config;

import java.io.Serializable;

/**
 * Value element.
 * 
 * @author Andrea Medeghini
 */
public abstract class ValueConfigElement<T extends Serializable> extends AbstractConfigElement {
	public static final int VALUE_CHANGED = 1;
	private static final long serialVersionUID = 1L;
	private T value;
	private T defaultValue;

	/**
	 * Constructs a new element.
	 */
	public ValueConfigElement(final String configElementId) {
		super(configElementId);
	}

	/**
	 * Constructs a new element.
	 * 
	 * @param defaultValue the default value.
	 */
	public ValueConfigElement(final String configElementId, final T defaultValue) {
		this(configElementId);
		this.value = defaultValue;
		this.defaultValue = defaultValue;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ConfigElement#dispose()
	 */
	@Override
	public void dispose() {
		value = null;
		defaultValue = null;
		super.dispose();
	}

	/**
	 * Ses the value.
	 * 
	 * @param value the value to set.
	 */
	public void setValue(final T value) {
		final T prevValue = this.getValue();
		this.value = value;
		if (checkContext()) {
			fireConfigChanged(new ValueChangeEvent(ValueConfigElement.VALUE_CHANGED, getContext().getTimestamp(), value, prevValue));
		}
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value.
	 */
	public T getValue() {
		return this.value;
	}

	/**
	 * Returns the default value.
	 * 
	 * @return the default value.
	 */
	public T getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Tests the values.
	 * 
	 * @param value new value.
	 * @param prevValue old value.
	 * @return true if the values are different.
	 */
	protected static boolean isValueChanged(final Object value, final Object prevValue) {
		return ((value == null) && (prevValue != null)) || ((value != null) && !value.equals(prevValue));
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract ValueConfigElement<T> clone();

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		return getValue().equals(((ValueConfigElement<T>) obj).getValue());
	}
}
