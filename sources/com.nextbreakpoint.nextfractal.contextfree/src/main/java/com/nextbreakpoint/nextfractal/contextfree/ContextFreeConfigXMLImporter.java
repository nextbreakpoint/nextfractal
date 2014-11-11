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
package com.nextbreakpoint.nextfractal.contextfree;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.elements.SpeedElement;
import com.nextbreakpoint.nextfractal.twister.elements.SpeedElementXMLImporter;
import com.nextbreakpoint.nextfractal.twister.elements.ViewElement;
import com.nextbreakpoint.nextfractal.twister.elements.ViewElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeConfigXMLImporter extends XMLImporter<ContextFreeConfig> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public ContextFreeConfig importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, ContextFreeConfig.CLASS_ID);
		final ContextFreeConfig config = new ContextFreeConfig();
		final List<Element> propertyElements = getProperties(element);
		if (isVersion(element, 0) && (propertyElements.size() == 3)) {
			importProperties(config, propertyElements);
		}
		return config;
	}

	/**
	 * @param config
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final ContextFreeConfig config, final List<Element> propertyElements) throws XMLImportException {
		importCFDG(config, propertyElements.get(0));
		importView(config, propertyElements.get(1));
		importSpeed(config, propertyElements.get(2));
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importCFDG(final ContextFreeConfig config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, CFDGConfigElement.CLASS_ID);
		if (elements.size() == 1) {
			final CFDGConfigElement contextFreeFractal = new CFDGConfigElementXMLImporter().importFromElement(elements.get(0));
			config.setCFDG(contextFreeFractal);
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importView(final ContextFreeConfig config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, ViewElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setView(new ViewElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importSpeed(final ContextFreeConfig config, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, SpeedElement.CLASS_ID);
		if (elements.size() == 1) {
			config.setSpeed(new SpeedElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}
}
