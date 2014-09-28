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
package com.nextbreakpoint.nextfractal.twister.frame;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class FrameConfigElementXMLImporter extends XMLImporter<FrameConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public FrameConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, FrameConfigElement.CLASS_ID);
		final FrameConfigElement configElement = new FrameConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 2) {
			importProperties(configElement, propertyElements);
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final FrameConfigElement configElement, final List<Element> propertyElements) throws XMLImportException {
		importFilters(configElement, propertyElements.get(0));
		importLayers(configElement, propertyElements.get(1));
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importFilters(final FrameConfigElement configElement, final Element element) throws XMLImportException {
		final FrameFilterConfigElementXMLImporter filterImporter = new FrameFilterConfigElementXMLImporter();
		final List<Element> filterElements = this.getElements(element, FrameFilterConfigElement.CLASS_ID);
		for (int i = 0; i < filterElements.size(); i++) {
			configElement.appendFilterConfigElement(filterImporter.importFromElement(filterElements.get(i)));
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importLayers(final FrameConfigElement configElement, final Element element) throws XMLImportException {
		final GroupLayerConfigElementXMLImporter groupLayerImporter = new GroupLayerConfigElementXMLImporter();
		final List<Element> layerElements = this.getElements(element, GroupLayerConfigElement.CLASS_ID);
		for (int i = 0; i < layerElements.size(); i++) {
			configElement.appendLayerConfigElement(groupLayerImporter.importFromElement(layerElements.get(i)));
		}
	}
}
