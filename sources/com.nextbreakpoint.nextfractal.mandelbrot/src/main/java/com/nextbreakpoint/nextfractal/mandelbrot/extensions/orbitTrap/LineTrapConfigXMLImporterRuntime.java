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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.orbitTrap;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.common.DoubleElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElement;
import com.nextbreakpoint.nextfractal.mandelbrot.common.CriteriaElementXMLImporter;

/**
 * @author Andrea Medeghini
 */
public class LineTrapConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<LineTrapConfig> createXMLImporter() {
		return new LineTrapConfigXMLImporter();
	}

	private class LineTrapConfigXMLImporter extends AbstractOrbitTrapConfigXMLImporter<LineTrapConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected LineTrapConfig createExtensionConfig() {
			return new LineTrapConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "LineTrapConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#getPropertiesSize()
		 */
		@Override
		protected int getPropertiesSize() {
			return 4;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.incolouringFormula.AbstractOrbitTrapConfigXMLImporter#importProperties(com.nextbreakpoint.nextfractal.mandelbrot.extensionPoints.incolouringFormula.OrbitTrapExtensionConfig, java.util.List)
		 */
		@Override
		protected void importProperties(final LineTrapConfig config, final List<Element> propertyElements) throws XMLImportException {
			importThreshold(config, propertyElements.get(0));
			importRotation(config, propertyElements.get(1));
			importLength(config, propertyElements.get(2));
			importCriteria(config, propertyElements.get(3));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importThreshold(final LineTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getThresholdElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importLength(final LineTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getLengthElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importRotation(final LineTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getRotationElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importCriteria(final LineTrapConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, CriteriaElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getCriteriaElement().setValue(new CriteriaElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
