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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ComplexElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ThresholdElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.renderingFormula.RenderingFormulaExtensionConfig;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractRenderingFormulaConfigXMLExporter<T extends RenderingFormulaExtensionConfig> extends XMLExporter<T> {
	/**
	 * @return
	 */
	protected abstract String getConfigElementClassId();

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final T config, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, this.getConfigElementClassId(), 1, 0);
		this.exportProperties(config, element, builder);
		return element;
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		this.exportIterations(config, createProperty(builder, element, "iterations"), builder);
		this.exportThreshold(config, createProperty(builder, element, "threshold"), builder);
		this.exportCenter(config, createProperty(builder, element, "center"), builder);
		this.exportScale(config, createProperty(builder, element, "scale"), builder);
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportIterations(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new IterationsElementXMLExporter().exportToElement(config.getIterationsElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportThreshold(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ThresholdElementXMLExporter().exportToElement(config.getThresholdElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportCenter(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ComplexElementXMLExporter().exportToElement(config.getCenterElement(), builder));
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportScale(final T config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ComplexElementXMLExporter().exportToElement(config.getScaleElement(), builder));
	}
}
