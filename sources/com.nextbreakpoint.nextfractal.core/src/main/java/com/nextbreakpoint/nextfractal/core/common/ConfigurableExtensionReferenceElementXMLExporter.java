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
package com.nextbreakpoint.nextfractal.core.common;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class ConfigurableExtensionReferenceElementXMLExporter<V extends ExtensionConfig> extends XMLExporter<ConfigurableExtensionReferenceElement<V>> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final ConfigurableExtensionReferenceElement<V> configElement, final XMLNodeBuilder builder) throws XMLExportException {
		if (configElement.getReference() != null) {
			final String extensionId = configElement.getReference().getExtensionId();
			final Element element = this.createElement(builder, configElement.getClassId(), extensionId);
			try {
				this.exportProperties(configElement, element, builder, extensionId);
			}
			catch (final ExtensionException e) {
				throw new XMLExportException(e);
			}
			return element;
		}
		else {
			return this.createElement(builder, configElement.getClassId());
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLExportException
	 */
	protected void exportProperties(final ConfigurableExtensionReferenceElement<V> configElement, final Element element, final XMLNodeBuilder builder, final String extensionId) throws ExtensionException, XMLExportException {
		this.exportReference(configElement, createProperty(builder, element, "extensionConfig"), builder, extensionId);
	}

	/**
	 * @param configElement
	 * @param element
	 * @param builder
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLExportException
	 */
	@SuppressWarnings("unchecked")
	protected void exportReference(final ConfigurableExtensionReferenceElement<V> configElement, final Element element, final XMLNodeBuilder builder, final String extensionId) throws ExtensionException, XMLExportException {
		final Extension<ExtensionConfigXMLExporterExtensionRuntime> exporterExtension = CoreRegistry.getInstance().getXMLExtensionConfigExporterExtension(extensionId);
		final XMLExporter<V> exporter = (XMLExporter<V>) (exporterExtension.createExtensionRuntime()).createXMLExporter();
		element.appendChild(exporter.exportToElement(configElement.getReference().getExtensionConfig(), builder));
	}
}
