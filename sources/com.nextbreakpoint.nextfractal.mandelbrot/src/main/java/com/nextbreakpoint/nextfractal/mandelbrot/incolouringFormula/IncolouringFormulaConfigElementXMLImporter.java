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
package com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class IncolouringFormulaConfigElementXMLImporter extends XMLImporter<IncolouringFormulaConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public IncolouringFormulaConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, IncolouringFormulaConfigElement.CLASS_ID);
		final IncolouringFormulaConfigElement configElement = new IncolouringFormulaConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (isVersion(element, 1) && (propertyElements.size() == 7)) {
			try {
				importProperties1(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		else if (isVersion(element, 0) && (propertyElements.size() == 6)) {
			try {
				importProperties0(configElement, propertyElements);
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
	protected void importProperties0(final IncolouringFormulaConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
		importLocked(configElement, propertyElements.get(1));
		importEnabled(configElement, propertyElements.get(2));
		importOpacity(configElement, propertyElements.get(3));
		importIterations(configElement, propertyElements.get(4));
		importAutoIterations(configElement, propertyElements.get(5));
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties1(final IncolouringFormulaConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
		importLocked(configElement, propertyElements.get(1));
		importEnabled(configElement, propertyElements.get(2));
		importOpacity(configElement, propertyElements.get(3));
		importIterations(configElement, propertyElements.get(4));
		importAutoIterations(configElement, propertyElements.get(5));
		importLabel(configElement, propertyElements.get(6));
	}

	private void importExtension(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setReference(new ConfigurableExtensionReferenceElementXMLImporter<IncolouringFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getIncolouringFormulaRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}

	private void importOpacity(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> percentElements = this.getElements(element, PercentageElement.CLASS_ID);
		if (percentElements.size() == 1) {
			configElement.setOpacity(new PercentageElementXMLImporter().importFromElement(percentElements.get(0)).getValue());
		}
	}

	private void importIterations(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> iterationsElements = this.getElements(element, IterationsElement.CLASS_ID);
		if (iterationsElements.size() == 1) {
			configElement.setIterations(new IterationsElementXMLImporter().importFromElement(iterationsElements.get(0)).getValue());
		}
	}

	private void importLocked(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setLocked(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importEnabled(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setEnabled(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importAutoIterations(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setAutoIterations(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importLabel(final IncolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> stringElements = this.getElements(element, StringElement.CLASS_ID);
		if (stringElements.size() == 1) {
			configElement.setLabel(new StringElementXMLImporter().importFromElement(stringElements.get(0)).getValue());
		}
	}
}
