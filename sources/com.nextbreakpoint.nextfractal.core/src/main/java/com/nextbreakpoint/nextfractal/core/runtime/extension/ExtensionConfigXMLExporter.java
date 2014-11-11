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
package com.nextbreakpoint.nextfractal.core.runtime.extension;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLExporter.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class ExtensionConfigXMLExporter<V extends ExtensionConfig> extends XMLExporter<V> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final V config, final XMLNodeBuilder builder) throws XMLExportException {
		final String extensionId = config.getExtensionId();
		final Element element = this.createElement(builder, "reference", extensionId);
		try {
			exportProperties(config, element, builder, extensionId);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
		return element;
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLExportException
	 */
	protected void exportProperties(final V config, final Element element, final XMLNodeBuilder builder, final String extensionId) throws ExtensionException, XMLExportException {
		this.exportReference(config, createProperty(builder, element, "extensionConfig"), builder, extensionId);
	}

	/**
	 * @param config
	 * @param element
	 * @param builder
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLExportException
	 */
	@SuppressWarnings("unchecked")
	protected void exportReference(final V config, final Element element, final XMLNodeBuilder builder, final String extensionId) throws ExtensionException, XMLExportException {
		final Extension<ExtensionConfigXMLExporterExtensionRuntime> exporterExtension = CoreRegistry.getInstance().getXMLExtensionConfigExporterExtension(extensionId);
		final XMLExporter<V> exporter = (XMLExporter<V>) (exporterExtension.createExtensionRuntime()).createXMLExporter();
		element.appendChild(exporter.exportToElement(config, builder));
	}
}
