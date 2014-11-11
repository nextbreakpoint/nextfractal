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
package com.nextbreakpoint.nextfractal.core.extensions.action;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.IntegerElement;
import com.nextbreakpoint.nextfractal.core.elements.IntegerElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.common.AbstractActionXMLImporterRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XML;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;

/**
 * @author Andrea Medeghini
 */
public class IntegerNodeActionXMLImporterRuntime extends AbstractActionXMLImporterRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.twister.util.AbstractActionXMLImporterRuntime#importParams(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeActionValue, org.w3c.dom.Element)
	 */
	@Override
	protected void importParams(final NodeActionValue action, final Element element) throws XMLImportException {
		final IntegerElementXMLImporter importer = new IntegerElementXMLImporter();
		final List<Element> elements = XML.getElementsByName(element, "element");
		if (elements.size() == 2) {
			final IntegerElement configElement0 = importer.importFromElement(elements.get(0));
			final IntegerElement configElement1 = importer.importFromElement(elements.get(1));
			action.setActionParams(new Serializable[] { configElement0.getValue(), configElement1.getValue() });
		}
	}
}
