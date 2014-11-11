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
package com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.elements.StringElement;
import com.nextbreakpoint.nextfractal.core.elements.StringElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRegistry;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.IterationsElement;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.IterationsElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.outcolouringFormula.OutcolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.elements.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.elements.PercentageElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class OutcolouringFormulaConfigElementXMLImporter extends XMLImporter<OutcolouringFormulaConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public OutcolouringFormulaConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, OutcolouringFormulaConfigElement.CLASS_ID);
		final OutcolouringFormulaConfigElement configElement = new OutcolouringFormulaConfigElement();
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
	protected void importProperties0(final OutcolouringFormulaConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
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
	protected void importProperties1(final OutcolouringFormulaConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
		importLocked(configElement, propertyElements.get(1));
		importEnabled(configElement, propertyElements.get(2));
		importOpacity(configElement, propertyElements.get(3));
		importIterations(configElement, propertyElements.get(4));
		importAutoIterations(configElement, propertyElements.get(5));
		importLabel(configElement, propertyElements.get(6));
	}

	private void importExtension(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setReference(new ConfigurableExtensionReferenceElementXMLImporter<OutcolouringFormulaExtensionConfig>(MandelbrotRegistry.getInstance().getOutcolouringFormulaRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}

	private void importOpacity(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> percentElements = this.getElements(element, PercentageElement.CLASS_ID);
		if (percentElements.size() == 1) {
			configElement.setOpacity(new PercentageElementXMLImporter().importFromElement(percentElements.get(0)).getValue());
		}
	}

	private void importIterations(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> iterationsElements = this.getElements(element, IterationsElement.CLASS_ID);
		if (iterationsElements.size() == 1) {
			configElement.setIterations(new IterationsElementXMLImporter().importFromElement(iterationsElements.get(0)).getValue());
		}
	}

	private void importLocked(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setLocked(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importEnabled(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setEnabled(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importAutoIterations(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> booleanElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (booleanElements.size() == 1) {
			configElement.setAutoIterations(new BooleanElementXMLImporter().importFromElement(booleanElements.get(0)).getValue());
		}
	}

	private void importLabel(final OutcolouringFormulaConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> stringElements = this.getElements(element, StringElement.CLASS_ID);
		if (stringElements.size() == 1) {
			configElement.setLabel(new StringElementXMLImporter().importFromElement(stringElements.get(0)).getValue());
		}
	}
}
