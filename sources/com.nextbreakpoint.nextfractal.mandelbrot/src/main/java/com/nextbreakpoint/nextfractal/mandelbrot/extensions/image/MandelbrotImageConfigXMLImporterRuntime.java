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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.image;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfigXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotImageConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<MandelbrotImageConfig> createXMLImporter() {
		return new MandelbrotImageConfigXMLImporter();
	}

	public class MandelbrotImageConfigXMLImporter extends XMLImporter<MandelbrotImageConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public MandelbrotImageConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, MandelbrotImageConfig.CLASS_ID);
			final MandelbrotImageConfig config = new MandelbrotImageConfig();
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
		protected void importProperties(final MandelbrotImageConfig config, final List<Element> propertyElements) throws XMLImportException {
			importMandelbrotConfig(config, propertyElements.get(0));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importMandelbrotConfig(final MandelbrotImageConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, MandelbrotConfig.CLASS_ID);
			if (elements.size() == 1) {
				final MandelbrotConfig mandelbrotConfig = new MandelbrotConfigXMLImporter().importFromElement(elements.get(0));
				config.setMandelbrotConfig(mandelbrotConfig);
			}
		}
	}
}
