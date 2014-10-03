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
package com.nextbreakpoint.nextfractal.twister;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class TwisterBookmarkXMLExporter extends XMLExporter<TwisterBookmark> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final TwisterBookmark bookmark, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, "bookmark");
		exportProperties(bookmark, element, builder);
		return element;
	}

	/**
	 * @param bookmark
	 * @param element
	 * @param builder
	 * @throws XMLExportException
	 */
	protected void exportProperties(final TwisterBookmark bookmark, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		exportConfig(bookmark, createProperty(builder, element, "config"), builder);
	}

	/**
	 * @param bookmark
	 * @param builder
	 * @param element
	 * @throws XMLExportException
	 */
	protected void exportConfig(final TwisterBookmark bookmark, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final TwisterConfigXMLExporter configExporter = new TwisterConfigXMLExporter();
		element.appendChild(configExporter.exportToElement(bookmark.getConfig(), builder));
	}
}
