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

import com.nextbreakpoint.nextfractal.core.constructor.extension.ConstructorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public interface JSContext {
	/**
	 * @param elementClassId
	 * @return
	 * @throws JSException
	 */
	public ConstructorExtensionRuntime getConstructor(final String elementClassId) throws JSException;

	/**
	 * @param elementClassId
	 * @return
	 * @throws JSException
	 */
	public EnumeratorExtensionRuntime getEnumerator(final String elementClassId) throws JSException;

	/**
	 * @param s
	 */
	public void println(String s);

	/**
	 * @param time
	 */
	public boolean sleep(long time);

	/**
	 * @param message
	 * @param size
	 * @param x
	 * @param y
	 * @param time
	 * @param hasBackground
	 */
	public void showMessage(String message, float size, float x, float y, long time, boolean hasBackground);

	/**
	 * 
	 */
	public void loadDefaultConfig();
}
