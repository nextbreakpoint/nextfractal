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
package com.nextbreakpoint.nextfractal.twister.layer;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractLayerConfigElementXMLExporter<T extends LayerConfigElement> extends XMLExporter<T> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final T configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, configElement.getClassId());
		this.exportProperties(configElement, element, builder);
		return element;
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		this.exportLocked(configElement, createProperty(builder, element, "locked"), builder);
		this.exportVisible(configElement, createProperty(builder, element, "visible"), builder);
		this.exportOpacity(configElement, createProperty(builder, element, "opacity"), builder);
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportLocked(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getLockedElement(), builder));
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportVisible(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getVisibleElement(), builder));
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportOpacity(final T configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new PercentageElementXMLExporter().exportToElement(configElement.getOpacityElement(), builder));
	}
}
