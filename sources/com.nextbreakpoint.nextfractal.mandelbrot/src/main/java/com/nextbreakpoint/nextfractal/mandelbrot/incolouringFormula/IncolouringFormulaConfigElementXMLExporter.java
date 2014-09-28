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

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.common.IterationsElementXMLExporter;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.IncolouringFormulaExtensionConfig;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class IncolouringFormulaConfigElementXMLExporter extends XMLExporter<IncolouringFormulaConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final IncolouringFormulaConfigElement configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, IncolouringFormulaConfigElement.CLASS_ID);
		try {
			exportProperties(configElement, element, builder);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder, java.lang.String)
	 */
	protected void exportProperties(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
		exportExtension(configElement, createProperty(builder, element, "extension"), builder);
		exportLocked(configElement, createProperty(builder, element, "locked"), builder);
		exportEnabled(configElement, createProperty(builder, element, "enabled"), builder);
		exportOpacity(configElement, createProperty(builder, element, "opacity"), builder);
		exportIterations(configElement, createProperty(builder, element, "iterations"), builder);
		exportAutoIterations(configElement, createProperty(builder, element, "autoIterations"), builder);
		exportLabel(configElement, createProperty(builder, element, "label"), builder);
	}

	private void exportExtension(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ConfigurableExtensionReferenceElementXMLExporter<IncolouringFormulaExtensionConfig>().exportToElement(configElement.getExtensionElement(), builder));
	}

	private void exportOpacity(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new PercentageElementXMLExporter().exportToElement(configElement.getOpacityElement(), builder));
	}

	private void exportIterations(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new IterationsElementXMLExporter().exportToElement(configElement.getIterationsElement(), builder));
	}

	private void exportEnabled(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getEnabledElement(), builder));
	}

	private void exportLocked(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getLockedElement(), builder));
	}

	private void exportAutoIterations(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getAutoIterationsElement(), builder));
	}

	private void exportLabel(final IncolouringFormulaConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getLabelElement(), builder));
	}
}
