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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.FontElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLExporter.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class TextConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLExporter.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<TextConfig> createXMLExporter() {
		return new TextConfigXMLExporter();
	}

	private class TextConfigXMLExporter extends AbstractImageConfigXMLExporter<TextConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "TextConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLExporter#exportToElement(com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionConfig, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final TextConfig config, final XMLNodeBuilder builder) throws XMLExportException {
			final Element element = super.exportToElement(config, builder);
			exportProperties(config, element, builder);
			return element;
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportProperties(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportColor(config, createProperty(builder, element, "color"), builder);
			exportFont(config, createProperty(builder, element, "font"), builder);
			exportSize(config, createProperty(builder, element, "size"), builder);
			exportText(config, createProperty(builder, element, "text"), builder);
			exportLeft(config, createProperty(builder, element, "left"), builder);
			exportTop(config, createProperty(builder, element, "top"), builder);
			exportRotation(config, createProperty(builder, element, "rotation"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportColor(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new ColorElementXMLExporter().exportToElement(config.getColorElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportFont(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FontElementXMLExporter().exportToElement(config.getFontElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportSize(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getSizeElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportText(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StringElementXMLExporter().exportToElement(config.getTextElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportLeft(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getLeftElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportTop(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getTopElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportRotation(final TextConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getRotationElement(), builder));
		}
	}
}
