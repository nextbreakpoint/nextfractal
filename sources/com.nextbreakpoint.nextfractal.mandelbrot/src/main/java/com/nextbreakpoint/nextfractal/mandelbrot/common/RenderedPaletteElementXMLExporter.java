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
package com.nextbreakpoint.nextfractal.mandelbrot.common;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.util.Color32bit;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteElementXMLExporter extends ValueConfigElementXMLExporter<RenderedPalette, RenderedPaletteElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final RenderedPaletteElement configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, configElement.getClassId());
		exportProperties(configElement, element, builder);
		return element;
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	@Override
	protected void exportProperties(final RenderedPaletteElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportPalette(configElement, createProperty(builder, element, "palette"), builder);
	}

	/**
	 * @param palette
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	private void exportPalette(final RenderedPaletteElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final RenderedPalette palette = configElement.getValue();
		for (int i = 0; i < palette.getParamCount(); i++) {
			exportParam(palette.getParam(i), createProperty(builder, element, "paletteParam"), builder);
		}
	}

	/**
	 * @param param
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportParam(final RenderedPaletteParam param, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportFormulas(param, createProperty(builder, element, "formulas"), builder);
		exportColors(param, createProperty(builder, element, "colors"), builder);
		exportSize(param, createProperty(builder, element, "size"), builder);
	}

	/**
	 * @param param
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	private void exportSize(final RenderedPaletteParam param, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final DoubleElementXMLExporter doubleExporter = new DoubleElementXMLExporter();
		final DoubleElement sizeElement = new DoubleElement(0d);
		sizeElement.setValue(new Double(param.getSize()));
		element.appendChild(doubleExporter.exportToElement(sizeElement, builder));
	}

	/**
	 * @param param
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	private void exportColors(final RenderedPaletteParam param, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final ColorElementXMLExporter colorExporter = new ColorElementXMLExporter();
		final ColorElement colorElement = new ColorElement(Color32bit.BLACK);
		colorElement.setValue(new Color32bit(param.getColor(0)));
		element.appendChild(colorExporter.exportToElement(colorElement, builder));
		colorElement.setValue(new Color32bit(param.getColor(1)));
		element.appendChild(colorExporter.exportToElement(colorElement, builder));
	}

	/**
	 * @param param
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	private void exportFormulas(final RenderedPaletteParam param, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final PaletteRendererFormulaConfigElementXMLExporter formulaExporter = new PaletteRendererFormulaConfigElementXMLExporter();
		final PaletteRendererFormulaConfigElement formulaElement = new PaletteRendererFormulaConfigElement();
		formulaElement.setReference(param.getFormula(0));
		element.appendChild(formulaExporter.exportToElement(formulaElement, builder));
		formulaElement.setReference(param.getFormula(1));
		element.appendChild(formulaExporter.exportToElement(formulaElement, builder));
		formulaElement.setReference(param.getFormula(2));
		element.appendChild(formulaExporter.exportToElement(formulaElement, builder));
		formulaElement.setReference(param.getFormula(3));
		element.appendChild(formulaExporter.exportToElement(formulaElement, builder));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter#formatValue(java.io.Serializable)
	 */
	@Override
	protected String formatValue(final RenderedPalette value) {
		return null;
	}
}
