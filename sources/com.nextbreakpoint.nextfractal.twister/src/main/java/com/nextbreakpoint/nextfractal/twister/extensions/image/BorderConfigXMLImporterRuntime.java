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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class BorderConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<BorderConfig> createXMLImporter() {
		return new BorderConfigXMLImporter();
	}

	private class BorderConfigXMLImporter extends AbstractImageConfigXMLImporter<BorderConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected BorderConfig createExtensionConfig() {
			return new BorderConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "BorderConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public BorderConfig importFromElement(final Element element) throws XMLImportException {
			final BorderConfig config = super.importFromElement(element);
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 2) {
				importProperties(config, propertyElements);
			}
			return config;
		}

		/**
		 * @param config
		 * @param propertyElements
		 * @throws XMLImportException
		 */
		protected void importProperties(final BorderConfig config, final List<Element> propertyElements) throws XMLImportException {
			importColor(config, propertyElements.get(0));
			importSize(config, propertyElements.get(1));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importColor(final BorderConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, ColorElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getColorElement().setValue(new ColorElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importSize(final BorderConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getSizeElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
