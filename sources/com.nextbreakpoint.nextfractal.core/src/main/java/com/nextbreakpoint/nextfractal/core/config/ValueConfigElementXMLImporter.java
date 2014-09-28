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
package com.nextbreakpoint.nextfractal.core.config;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * Abstract value element importer.
 * 
 * @author Andrea Medeghini
 */
public abstract class ValueConfigElementXMLImporter<V extends Serializable, T extends ValueConfigElement<V>> extends XMLImporter<T> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public T importFromElement(final Element element) throws XMLImportException {
		final T configElement = this.createDefaultConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
			this.importProperties(configElement, propertyElements);
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final T configElement, final List<Element> propertyElements) throws XMLImportException {
		this.importValue(configElement, propertyElements.get(0));
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importValue(final T configElement, final Element element) throws XMLImportException {
		try {
			configElement.setValue(this.parseValue(XML.getStringElementValue(element, "value")));
		}
		catch (final Exception e) {
			throw new XMLImportException(e);
		}
	}

	/**
	 * Parses the value.
	 * 
	 * @param value the value to parse.
	 * @return the value.
	 */
	protected abstract V parseValue(String value);

	/**
	 * Creates the default configuration element.
	 * 
	 * @return the default configuration element.
	 */
	protected abstract T createDefaultConfigElement();
}
