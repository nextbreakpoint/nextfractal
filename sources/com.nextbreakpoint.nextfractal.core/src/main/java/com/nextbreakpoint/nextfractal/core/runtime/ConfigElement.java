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

import java.io.Serializable;

/**
 * Configuration element.
 * 
 * @author Andrea Medeghini
 */
public interface ConfigElement extends Serializable, Cloneable {
	/**
	 * Sets the configuration context.
	 * 
	 * @param context the configuration context to set.
	 */
	public void setContext(ConfigContext context);

	/**
	 * Returns the classId.
	 * 
	 * @return the classId.
	 */
	public String getClassId();

	/**
	 * Adds a listener.
	 * 
	 * @param listener the listener to add.
	 */
	public void addChangeListener(ElementChangeListener listener);

	/**
	 * Removes a listener.
	 * 
	 * @param listener the listener to remove.
	 */
	public void removeChangeListener(ElementChangeListener listener);

	/**
	 * @return
	 */
	public ConfigElement clone();

	/**
	 * 
	 */
	public void dispose();

	/**
	 * @return
	 */
	public Object getUserData();

	/**
	 * @param data
	 */
	public void setUserData(Object userData);
	
	/**
	 * @param source
	 */
	public void copyFrom(ConfigElement source);
}
