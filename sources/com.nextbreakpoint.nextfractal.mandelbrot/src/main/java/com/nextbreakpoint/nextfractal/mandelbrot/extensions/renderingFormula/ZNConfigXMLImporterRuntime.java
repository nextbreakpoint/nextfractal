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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ExponentElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.ExponentElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ZNConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<ZNConfig> createXMLImporter() {
		return new ZNConfigXMLImporter();
	}

	private class ZNConfigXMLImporter extends AbstractRenderingFormulaConfigXMLImporter<ZNConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaConfigXMLImporter#getPropertiesSize()
		 */
		@Override
		protected int getPropertiesSize() {
			return super.getPropertiesSize() + 1;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaConfigXMLImporter#importProperties(com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.extension.RenderingFormulaExtensionConfig, java.util.List)
		 */
		@Override
		protected void importProperties(final ZNConfig config, final List<Element> propertyElements) throws XMLImportException {
			super.importProperties(config, propertyElements);
			importExponent(config, propertyElements.get(super.getPropertiesSize()));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importExponent(final ZNConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, ExponentElement.CLASS_ID);
			if (elements.size() == 1) {
				config.setExponent(new ExponentElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected ZNConfig createExtensionConfig() {
			return new ZNConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.renderingFormula.AbstractRenderingFormulaConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "ZNConfig";
		}
	}
}
