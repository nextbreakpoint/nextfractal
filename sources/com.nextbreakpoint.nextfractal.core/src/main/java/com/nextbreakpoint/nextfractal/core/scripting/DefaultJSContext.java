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

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.constructor.ConstructorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extensionPoints.enumerator.EnumeratorExtensionRuntime;

/**
 * @author Andrea Medeghini
 */
public abstract class DefaultJSContext implements JSContext {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#getConstructor(java.lang.String)
	 */
	@Override
	public ConstructorExtensionRuntime getConstructor(final String elementClassId) throws JSException {
		try {
			return CoreRegistry.getInstance().getConstructorExtension(elementClassId).createExtensionRuntime();
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
		catch (ExtensionException e) {
			throw new JSException(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#getEnumerator(java.lang.String)
	 */
	@Override
	public EnumeratorExtensionRuntime getEnumerator(final String elementClassId) throws JSException {
		try {
			return CoreRegistry.getInstance().getEnumeratorExtension(elementClassId).createExtensionRuntime();
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
		catch (ExtensionException e) {
			throw new JSException(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#println(java.lang.String)
	 */
	@Override
	public void println(final String s) {
		System.out.println(s);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#sleep(long)
	 */
	@Override
	public boolean sleep(long time) {
		try {
			if (Thread.interrupted()) {
				return true;
			}
			Thread.sleep(time);
			return false;
		}
		catch (InterruptedException e) {
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.scripting.JSContext#showMessage(java.lang.String, float, float, float, long, boolean)
	 */
	@Override
	public void showMessage(String message, float size, float x, float y, long tim, boolean hasBackground) {
	}
}
