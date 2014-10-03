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
package com.nextbreakpoint.nextfractal.twister.extensions.effect;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElement;
import com.nextbreakpoint.nextfractal.twister.common.PercentageElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class WaterConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<WaterConfig> createXMLImporter() {
		return new WaterConfigXMLImporter();
	}

	private class WaterConfigXMLImporter extends AbstractEffectConfigXMLImporter<WaterConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.AbstractLayerFilterConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected WaterConfig createExtensionConfig() {
			return new WaterConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.AbstractLayerFilterConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "WaterConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.layerFilter.AbstractLayerFilterConfigXMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public WaterConfig importFromElement(final Element element) throws XMLImportException {
			final WaterConfig config = super.importFromElement(element);
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 1) {
				importProperties(config, propertyElements);
			}
			return config;
		}

		/**
		 * @param config
		 * @param propertyElements
		 * @throws XMLImportException
		 */
		protected void importProperties(final WaterConfig config, final List<Element> propertyElements) throws XMLImportException {
			importIntensity(config, propertyElements.get(0));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importIntensity(final WaterConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, PercentageElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getIntensityElement().setValue(new PercentageElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
