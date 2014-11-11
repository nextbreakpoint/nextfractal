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
package com.nextbreakpoint.nextfractal.core.runtime.xml;

import org.w3c.dom.Element;

/**
 * @author Andrea Medeghini
 */
public abstract class XMLExporter<T> {
	public static final String PROPERTY_TAG_NAME = "property";
	public static final String ELEMENT_TAG_NAME = "element";
	public static final String VALUE_TAG_NAME = "value";
	public static final String ATTRIBUTE_NAME = "name";
	public static final String ATTRIBUTE_CLASS_ID = "classId";
	public static final String ATTRIBUTE_EXTENSION_ID = "extensionId";
	public static final String ATTRIBUTE_VER = "ver";

	/**
	 * @param object
	 * @param builder
	 * @return
	 * @throws XMLExportException
	 */
	public abstract Element exportToElement(T object, XMLNodeBuilder builder) throws XMLExportException;

	/**
	 * @param builder
	 * @param elementId
	 * @return
	 */
	protected Element createElement(final XMLNodeBuilder builder, final String elementId) {
		return this.createElement(builder, elementId, 1, 0);
	}

	/**
	 * @param builder
	 * @param elementId
	 * @param extensionId
	 * @return
	 */
	protected Element createElement(final XMLNodeBuilder builder, final String elementId, final String extensionId) {
		return this.createElement(builder, elementId, extensionId, 1, 0);
	}

	/**
	 * @param builder
	 * @param elementId
	 * @param ver
	 * @param rev
	 * @return
	 */
	protected Element createElement(final XMLNodeBuilder builder, final String elementId, final Integer ver, final Integer rev) {
		final Element element = builder.createElement(XMLExporter.ELEMENT_TAG_NAME);
		element.setAttribute(XMLExporter.ATTRIBUTE_CLASS_ID, elementId);
		if (ver > 0) {
			element.setAttribute(ATTRIBUTE_VER, ver.toString());
		}
		return element;
	}

	/**
	 * @param builder
	 * @param elementId
	 * @param extensionId
	 * @param ver
	 * @param rev
	 * @return
	 */
	protected Element createElement(final XMLNodeBuilder builder, final String elementId, final String extensionId, final Integer ver, final Integer rev) {
		final Element element = builder.createElement(XMLExporter.ELEMENT_TAG_NAME);
		element.setAttribute(XMLExporter.ATTRIBUTE_EXTENSION_ID, extensionId);
		element.setAttribute(XMLExporter.ATTRIBUTE_CLASS_ID, elementId);
		if (ver > 0) {
			element.setAttribute(ATTRIBUTE_VER, ver.toString());
		}
		return element;
	}

	/**
	 * @param builder
	 * @param element
	 * @param propertyName
	 * @return
	 */
	protected Element createProperty(final XMLNodeBuilder builder, final Element element, final String propertyName) {
		final Element propertyElement = builder.createElement(XMLExporter.PROPERTY_TAG_NAME);
		propertyElement.setAttribute(XMLExporter.ATTRIBUTE_NAME, propertyName);
		element.appendChild(propertyElement);
		return propertyElement;
	}
}
