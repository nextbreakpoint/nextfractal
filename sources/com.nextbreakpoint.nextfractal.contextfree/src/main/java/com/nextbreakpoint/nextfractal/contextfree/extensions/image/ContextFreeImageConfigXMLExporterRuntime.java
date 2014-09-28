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
package com.nextbreakpoint.nextfractal.contextfree.extensions.image;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeConfigXMLExporter;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeImageConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<ContextFreeImageConfig> createXMLExporter() {
		return new ContextFreetImageConfigXMLExporter();
	}

	public class ContextFreetImageConfigXMLExporter extends XMLExporter<ContextFreeImageConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final ContextFreeImageConfig config, final XMLNodeBuilder builder) throws XMLExportException {
			final Element element = this.createElement(builder, ContextFreeImageConfig.CLASS_ID);
			exportProperties(config, element, builder);
			return element;
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportProperties(final ContextFreeImageConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportContextFreetConfig(config, createProperty(builder, element, "contextFreeConfig"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportContextFreetConfig(final ContextFreeImageConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ContextFreeConfigXMLExporter().exportToElement(config.getContextFreeConfig(), builder));
		}
	}
}
