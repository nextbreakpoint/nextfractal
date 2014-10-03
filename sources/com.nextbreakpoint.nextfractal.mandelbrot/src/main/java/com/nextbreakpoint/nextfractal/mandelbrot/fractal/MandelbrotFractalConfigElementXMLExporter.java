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
package com.nextbreakpoint.nextfractal.mandelbrot.fractal;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.orbitTrap.OrbitTrapConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.processingFormula.ProcessingFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.transformingFormula.TransformingFormulaConfigElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotFractalConfigElementXMLExporter extends XMLExporter<MandelbrotFractalConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final MandelbrotFractalConfigElement configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, MandelbrotFractalConfigElement.CLASS_ID, 2, 0);
		exportProperties(configElement, element, builder);
		return element;
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportRenderingFormula(configElement, createProperty(builder, element, "renderingFormula"), builder);
		exportTransformingFormula(configElement, createProperty(builder, element, "transformingFormula"), builder);
		exportIncolouringFormulas(configElement, createProperty(builder, element, "incolouringFormulaList"), builder);
		exportOutcolouringFormulas(configElement, createProperty(builder, element, "outcolouringFormulaList"), builder);
		exportProcessingFormula(configElement, createProperty(builder, element, "processingFormula"), builder);
		exportOrbitTrap(configElement, createProperty(builder, element, "orbitTrap"), builder);
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportRenderingFormula(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new RenderingFormulaConfigElementXMLExporter().exportToElement(configElement.getRenderingFormulaConfigElement(), builder));
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportTransformingFormula(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new TransformingFormulaConfigElementXMLExporter().exportToElement(configElement.getTransformingFormulaConfigElement(), builder));
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportIncolouringFormulas(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final IncolouringFormulaConfigElementXMLExporter filterExporter = new IncolouringFormulaConfigElementXMLExporter();
		for (int i = 0; i < configElement.getIncolouringFormulaConfigElementCount(); i++) {
			element.appendChild(filterExporter.exportToElement(configElement.getIncolouringFormulaConfigElement(i), builder));
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportOutcolouringFormulas(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final OutcolouringFormulaConfigElementXMLExporter filterExporter = new OutcolouringFormulaConfigElementXMLExporter();
		for (int i = 0; i < configElement.getOutcolouringFormulaConfigElementCount(); i++) {
			element.appendChild(filterExporter.exportToElement(configElement.getOutcolouringFormulaConfigElement(i), builder));
		}
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProcessingFormula(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ProcessingFormulaConfigElementXMLExporter().exportToElement(configElement.getProcessingFormulaConfigElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportOrbitTrap(final MandelbrotFractalConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new OrbitTrapConfigElementXMLExporter().exportToElement(configElement.getOrbitTrapConfigElement(), builder));
	}
}
