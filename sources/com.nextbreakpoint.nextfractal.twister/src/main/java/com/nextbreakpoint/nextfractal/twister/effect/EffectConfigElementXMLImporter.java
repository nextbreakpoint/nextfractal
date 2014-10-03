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
package com.nextbreakpoint.nextfractal.twister.effect;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.TwisterRegistry;
import com.nextbreakpoint.nextfractal.twister.effect.extension.EffectExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public class EffectConfigElementXMLImporter extends XMLImporter<EffectConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public EffectConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, EffectConfigElement.CLASS_ID);
		final EffectConfigElement configElement = new EffectConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 3) {
			try {
				importProperties(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final EffectConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
		importEnabled(configElement, propertyElements.get(1));
		importLocked(configElement, propertyElements.get(2));
	}

	private void importExtension(final EffectConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setReference(new ConfigurableExtensionReferenceElementXMLImporter<EffectExtensionConfig>(TwisterRegistry.getInstance().getEffectRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}

	private void importEnabled(final EffectConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setEnabled(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importLocked(final EffectConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setLocked(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}
}
