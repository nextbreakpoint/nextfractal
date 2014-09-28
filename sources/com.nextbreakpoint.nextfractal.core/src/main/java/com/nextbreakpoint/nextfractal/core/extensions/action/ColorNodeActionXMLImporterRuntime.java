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
package com.nextbreakpoint.nextfractal.core.extensions.action;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.util.AbstractActionXMLImporterRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;

/**
 * @author Andrea Medeghini
 */
public class ColorNodeActionXMLImporterRuntime extends AbstractActionXMLImporterRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.util.AbstractActionXMLImporterRuntime#importParams(com.nextbreakpoint.nextfractal.core.tree.NodeActionValue, org.w3c.dom.Element)
	 */
	@Override
	protected void importParams(final NodeActionValue action, final Element element) throws XMLImportException {
		final ColorElementXMLImporter importer = new ColorElementXMLImporter();
		final List<Element> elements = XML.getElementsByName(element, "element");
		if (elements.size() == 2) {
			final ColorElement configElement0 = importer.importFromElement(elements.get(0));
			final ColorElement configElement1 = importer.importFromElement(elements.get(1));
			action.setActionParams(new Serializable[] { configElement0.getValue(), configElement1.getValue() });
		}
	}
}
