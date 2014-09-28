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
package com.nextbreakpoint.nextfractal.mandelbrot.fractal;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalConfigElementXMLImporter extends XMLImporter<MandelbrotFractalConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public MandelbrotFractalConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, MandelbrotFractalConfigElement.CLASS_ID);
		final MandelbrotFractalConfigElement configElement = new MandelbrotFractalConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (isVersion(element, 2) && (propertyElements.size() == 6)) {
			importProperties1(configElement, propertyElements);
		}
		else if (isVersion(element, 1) && (propertyElements.size() == 4)) {
			importProperties0(configElement, propertyElements);
		}
		else if (isVersion(element, 0) && (propertyElements.size() == 4)) {
			importProperties0(configElement, propertyElements);
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties0(final MandelbrotFractalConfigElement configElement, final List<Element> propertyElements) throws XMLImportException {
		importRenderingFormula(configElement, propertyElements.get(0));
		importTransformingFormula(configElement, propertyElements.get(1));
		importIncolouringFormulas(configElement, propertyElements.get(2));
		importOutcolouringFormulas(configElement, propertyElements.get(3));
		configElement.setProcessingFormulaConfigElement(new ProcessingFormulaConfigElement());
		configElement.setOrbitTrapConfigElement(new OrbitTrapConfigElement());
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	protected void importProperties1(final MandelbrotFractalConfigElement configElement, final List<Element> propertyElements) throws XMLImportException {
		importRenderingFormula(configElement, propertyElements.get(0));
		importTransformingFormula(configElement, propertyElements.get(1));
		importIncolouringFormulas(configElement, propertyElements.get(2));
		importOutcolouringFormulas(configElement, propertyElements.get(3));
		importProcessingFormula(configElement, propertyElements.get(4));
		importOrbitTrap(configElement, propertyElements.get(5));
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importRenderingFormula(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, RenderingFormulaConfigElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setRenderingFormulaConfigElement(new RenderingFormulaConfigElementXMLImporter().importFromElement(elements.get(0)));
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importTransformingFormula(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, TransformingFormulaConfigElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setTransformingFormulaConfigElement(new TransformingFormulaConfigElementXMLImporter().importFromElement(elements.get(0)));
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importIncolouringFormulas(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final IncolouringFormulaConfigElementXMLImporter formulaImporter = new IncolouringFormulaConfigElementXMLImporter();
		final List<Element> formulaElements = this.getElements(element, IncolouringFormulaConfigElement.CLASS_ID);
		for (int i = 0; i < formulaElements.size(); i++) {
			configElement.appendIncolouringFormulaConfigElement(formulaImporter.importFromElement(formulaElements.get(i)));
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importOutcolouringFormulas(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final OutcolouringFormulaConfigElementXMLImporter formulaImporter = new OutcolouringFormulaConfigElementXMLImporter();
		final List<Element> formulaElements = this.getElements(element, OutcolouringFormulaConfigElement.CLASS_ID);
		for (int i = 0; i < formulaElements.size(); i++) {
			configElement.appendOutcolouringFormulaConfigElement(formulaImporter.importFromElement(formulaElements.get(i)));
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importProcessingFormula(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, ProcessingFormulaConfigElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setProcessingFormulaConfigElement(new ProcessingFormulaConfigElementXMLImporter().importFromElement(elements.get(0)));
		}
	}

	/**
	 * @param config
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importOrbitTrap(final MandelbrotFractalConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> elements = this.getElements(element, OrbitTrapConfigElement.CLASS_ID);
		if (elements.size() == 1) {
			configElement.setOrbitTrapConfigElement(new OrbitTrapConfigElementXMLImporter().importFromElement(elements.get(0)));
		}
	}
}
