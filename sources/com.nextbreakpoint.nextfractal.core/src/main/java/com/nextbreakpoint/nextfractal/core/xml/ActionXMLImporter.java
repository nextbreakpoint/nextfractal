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
package com.nextbreakpoint.nextfractal.core.xml;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.actionXMLImporter.extension.ActionXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;

/**
 * @author Andrea Medeghini
 */
public class ActionXMLImporter extends XMLImporter<NodeActionValue> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public NodeActionValue importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, "action");
		try {
			final Extension<ActionXMLImporterExtensionRuntime> extension = CoreRegistry.getInstance().getXMLActionImporterExtension(getExtensionId(element));
			final XMLImporter<NodeActionValue> importer = extension.createExtensionRuntime().createXMLImporter();
			return importer.importFromElement(element);
		}
		catch (final ExtensionException e) {
			throw new XMLImportException(e);
		}
	}
}
