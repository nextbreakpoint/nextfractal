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
package com.nextbreakpoint.nextfractal.core.common;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ExtensionReferenceElementXMLImporter extends XMLImporter<ExtensionReferenceElement> {
	private final ExtensionRegistry<? extends ExtensionRuntime> registry;

	/**
	 * @param registry
	 */
	public ExtensionReferenceElementXMLImporter(final ExtensionRegistry<? extends ExtensionRuntime> registry) {
		this.registry = registry;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public ExtensionReferenceElement importFromElement(final Element element) throws XMLImportException {
		final ExtensionReferenceElement configElement = new ExtensionReferenceElement();
		checkClassId(element, configElement.getClassId());
		try {
			String extensionId = getExtensionId(element);
			if (!"".equals(extensionId)) {
				configElement.setReference(registry.getExtension(extensionId).getExtensionReference());
			}
		}
		catch (ExtensionNotFoundException e) {
			throw new XMLImportException(e);
		}
		return configElement;
	}
}
