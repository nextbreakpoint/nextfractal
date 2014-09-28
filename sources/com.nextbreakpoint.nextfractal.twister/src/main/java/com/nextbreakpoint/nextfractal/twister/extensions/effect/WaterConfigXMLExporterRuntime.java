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
package com.nextbreakpoint.nextfractal.twister.extensions.effect;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLExporter;

/**
 * @author Andrea Medeghini
 */
public class WaterConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<WaterConfig> createXMLExporter() {
		return new WaterConfigXMLExporter();
	}

	private class WaterConfigXMLExporter extends AbstractEffectConfigXMLExporter<WaterConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.AbstractEffectConfigXMLExporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "WaterConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.AbstractEffectConfigXMLExporter#exportToElement(com.nextbreakpoint.nextfractal.twister.layerFilter.extension.EffectExtensionConfig, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final WaterConfig config, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final WaterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			exportIntensity(config, createProperty(builder, element, "intensity"), builder);
		}

		/**
		 * @param config
		 * @param element
		 * @param builder
		 * @throws XMLExportException
		 */
		protected void exportIntensity(final WaterConfig config, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new PercentageElementXMLExporter().exportToElement(config.getIntensityElement(), builder));
		}
	}
}
