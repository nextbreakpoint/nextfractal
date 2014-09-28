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
package com.nextbreakpoint.nextfractal.twister.layer;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractLayerConfigElementXMLImporter<T extends LayerConfigElement> extends XMLImporter<T> {
	/**
	 * @return
	 */
	protected abstract String getConfigElementId();

	/**
	 * @return
	 */
	protected abstract T createConfigElement();

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties(final T configElement, final List<Element> propertyElements) throws XMLImportException {
		this.importLocked(configElement, propertyElements.get(0));
		this.importVisible(configElement, propertyElements.get(1));
		this.importOpacity(configElement, propertyElements.get(2));
	}

	/**
	 * @return
	 */
	protected int getPropertiesSize() {
		return 3;
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importLocked(final T configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, BooleanElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setLocked(new BooleanElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importVisible(final T configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, BooleanElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setVisible(new BooleanElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importOpacity(final T configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, PercentageElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setOpacity(new PercentageElementXMLImporter().importFromElement(elements.get(0)).getValue());
		}
	}
}
