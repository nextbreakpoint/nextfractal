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
package com.nextbreakpoint.nextfractal.core.extensions.enumerator;

import java.util.LinkedList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.creator.extension.CreatorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.scripting.JSException;
import com.nextbreakpoint.nextfractal.core.scripting.JSExtension;

/**
 * @author Andrea Medeghini
 */
public class CreatorEnumeratorRuntime extends EnumeratorExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime#listExtensions()
	 */
	@Override
	public List<String> listExtensions() throws JSException {
		List<Extension<CreatorExtensionRuntime>> extensions = CoreRegistry.getInstance().getCreatorRegistry().getExtensionList();
		List<String> references = new LinkedList<String>();
		for (Extension<CreatorExtensionRuntime> extension : extensions) {
			references.add(extension.getExtensionId());
		}
		return references;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.enumerator.extension.EnumeratorExtensionRuntime#getExtension(java.lang.String)
	 */
	@Override
	public JSExtension getExtension(final String extensionId) throws JSException {
		try {
			return new JSExtension(CoreRegistry.getInstance().getCreatorExtension(extensionId));
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
	}
}
