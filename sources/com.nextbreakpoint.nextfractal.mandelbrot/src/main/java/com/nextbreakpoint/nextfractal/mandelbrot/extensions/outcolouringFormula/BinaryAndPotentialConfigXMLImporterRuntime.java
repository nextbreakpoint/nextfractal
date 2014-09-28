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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula;

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
public class BinaryAndPotentialConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<BinaryAndPotentialConfig> createXMLImporter() {
		return new BinaryAndPotentialConfigXMLImporter();
	}

	private class BinaryAndPotentialConfigXMLImporter extends AbstractOutcolouringPaletteConfigXMLImporter<BinaryAndPotentialConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected BinaryAndPotentialConfig createExtensionConfig() {
			return new BinaryAndPotentialConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "BinaryAndPotentialConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLImporter#getPropertiesSize()
		 */
		@Override
		protected int getPropertiesSize() {
			return super.getPropertiesSize() + 1;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.outcolouringFormula.AbstractOutcolouringFormulaConfigXMLImporter#importProperties(com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.extension.OutcolouringFormulaExtensionConfig, java.util.List)
		 */
		@Override
		protected void importProperties(final BinaryAndPotentialConfig config, final List<Element> propertyElements) throws XMLImportException {
			super.importProperties(config, propertyElements);
			importOffset(config, propertyElements.get(0));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importOffset(final BinaryAndPotentialConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, PercentageElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getOffsetElement().setValue(new PercentageElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
