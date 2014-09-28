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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.colorRenderer;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.colorRendererFormula.ColorRendererFormulaConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractPeriodicConfigXMLExporter<T extends AbstractPeriodicConfig> extends AbstractColorRendererConfigXMLExporter<T> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final T config, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, getConfigElementClassId(), 1, 0);
		this.exportProperties(config, element, builder);
		return element;
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	@Override
	protected void exportProperties(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		this.exportFormula(config, createProperty(builder, element, "formula"), builder);
		this.exportAmplitude(config, createProperty(builder, element, "amplitude"), builder);
		this.exportFrequency(config, createProperty(builder, element, "frequency"), builder);
		this.exportScale(config, createProperty(builder, element, "scale"), builder);
		this.exportTimeEnabled(config, createProperty(builder, element, "timeEnabled"), builder);
		this.exportAbsoluteEnabled(config, createProperty(builder, element, "absoluteEnabled"), builder);
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportFormula(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ColorRendererFormulaConfigElementXMLExporter().exportToElement(config.getColorRendererFormulaElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportAmplitude(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new PercentageElementXMLExporter().exportToElement(config.getAmplitudeElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportFrequency(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getFrequencyElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportScale(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getScaleElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportTimeEnabled(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(config.getTimeEnabledElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportAbsoluteEnabled(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(config.getAbsoluteEnabledElement(), builder));
	}
}
