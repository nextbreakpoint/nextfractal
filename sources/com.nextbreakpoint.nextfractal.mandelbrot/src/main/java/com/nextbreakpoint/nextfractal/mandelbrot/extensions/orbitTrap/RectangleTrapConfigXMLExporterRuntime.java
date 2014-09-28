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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class RectangleTrapConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<RectangleTrapConfig> createXMLExporter() {
		return new RectangleTrapConfigXMLExporter();
	}

	private class RectangleTrapConfigXMLExporter extends AbstractOrbitTrapConfigXMLExporter<RectangleTrapConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "RectangleTrapConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.extension.OrbitTrapExtensionConfig, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		protected void exportProperties(final RectangleTrapConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportWidth(config, createProperty(builder, element, "width"), builder);
			exportHeight(config, createProperty(builder, element, "height"), builder);
			exportRotation(config, createProperty(builder, element, "rotation"), builder);
			exportCriteria(config, createProperty(builder, element, "criteria"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportWidth(final RectangleTrapConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getWidthElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportHeight(final RectangleTrapConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getHeightElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportRotation(final RectangleTrapConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new DoubleElementXMLExporter().exportToElement(config.getRotationElement(), builder));
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportCriteria(final RectangleTrapConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new CriteriaElementXMLExporter().exportToElement(config.getCriteriaElement(), builder));
		}
	}
}
