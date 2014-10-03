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

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaConfigElement;
import com.nextbreakpoint.nextfractal.mandelbrot.paletteRendererFormula.PaletteRendererFormulaConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.util.DefaultRenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPalette;
import com.nextbreakpoint.nextfractal.mandelbrot.util.RenderedPaletteParam;

/**
 * @author Andrea Medeghini
 */
public class RenderedPaletteElementXMLImporter extends ValueConfigElementXMLImporter<RenderedPalette, RenderedPaletteElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public RenderedPaletteElement importFromElement(final Element element) throws XMLImportException {
		final RenderedPaletteElement configElement = createDefaultConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
			importProperties(configElement, propertyElements);
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws XMLImportException
	 */
	@Override
	protected void importProperties(final RenderedPaletteElement configElement, final List<Element> propertyElements) throws XMLImportException {
		importPalette(configElement, propertyElements.get(0));
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected void importPalette(final RenderedPaletteElement configElement, final Element element) throws XMLImportException {
		final List<Element> propertyElements = getProperties(element);
		final RenderedPaletteParam[] params = new RenderedPaletteParam[propertyElements.size()];
		for (int i = 0; i < propertyElements.size(); i++) {
			final List<Element> paramPropertyElements = getProperties(propertyElements.get(i));
			if (paramPropertyElements.size() == 3) {
				params[i] = importParam(paramPropertyElements);
			}
		}
		final RenderedPalette palette = new RenderedPalette(params);
		configElement.setValue(palette);
	}

	/**
	 * @param configElement
	 * @param element
	 * @throws XMLImportException
	 */
	protected RenderedPaletteParam importParam(final List<Element> paramPropertyElements) throws XMLImportException {
		final ExtensionReference[] formulas = new ExtensionReference[4];
		final int[] colors = new int[2];
		final double[] size = new double[1];
		importFormulas(paramPropertyElements.get(0), formulas);
		importColors(paramPropertyElements.get(1), colors);
		importSize(paramPropertyElements.get(2), size);
		final RenderedPaletteParam param = new RenderedPaletteParam(formulas, colors, size[0]);
		return param;
	}

	/**
	 * @param element
	 * @param size
	 * @throws XMLImportException
	 */
	private void importSize(final Element element, final double[] size) throws XMLImportException {
		final DoubleElementXMLImporter doubleImporter = new DoubleElementXMLImporter();
		final List<Element> doubleElements = this.getElements(element, DoubleElement.CLASS_ID);
		if (doubleElements.size() == 1) {
			size[0] = doubleImporter.importFromElement(doubleElements.get(0)).getValue();
		}
	}

	/**
	 * @param element
	 * @param colors
	 * @throws XMLImportException
	 */
	private void importColors(final Element element, final int[] colors) throws XMLImportException {
		final List<Element> colorElements = this.getElements(element, ColorElement.CLASS_ID);
		final ColorElementXMLImporter colorImporter = new ColorElementXMLImporter();
		for (int i = 0; i < colorElements.size(); i++) {
			colors[i] = colorImporter.importFromElement(colorElements.get(i)).getValue().getARGB();
		}
	}

	/**
	 * @param element
	 * @param formulas
	 * @throws XMLImportException
	 */
	private void importFormulas(final Element element, final ExtensionReference[] formulas) throws XMLImportException {
		final PaletteRendererFormulaConfigElementXMLImporter formulaImporter = new PaletteRendererFormulaConfigElementXMLImporter();
		final List<Element> formulaElements = this.getElements(element, PaletteRendererFormulaConfigElement.CLASS_ID);
		for (int i = 0; i < formulaElements.size(); i++) {
			formulas[i] = formulaImporter.importFromElement(formulaElements.get(i)).getReference();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#createDefaultConfigElement()
	 */
	@Override
	protected RenderedPaletteElement createDefaultConfigElement() {
		return new RenderedPaletteElement(new DefaultRenderedPalette());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter#parseValue(java.lang.String)
	 */
	@Override
	protected RenderedPalette parseValue(final String value) {
		return null;
	}
}
