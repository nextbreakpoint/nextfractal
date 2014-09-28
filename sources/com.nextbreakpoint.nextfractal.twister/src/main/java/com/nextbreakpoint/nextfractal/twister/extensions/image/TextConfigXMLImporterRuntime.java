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
import com.nextbreakpoint.nextfractal.core.common.FontElement;
import com.nextbreakpoint.nextfractal.core.common.FontElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class TextConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<TextConfig> createXMLImporter() {
		return new TextConfigXMLImporter();
	}

	private class TextConfigXMLImporter extends AbstractImageConfigXMLImporter<TextConfig> {
		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#createExtensionConfig()
		 */
		@Override
		protected TextConfig createExtensionConfig() {
			return new TextConfig();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#getConfigElementClassId()
		 */
		@Override
		protected String getConfigElementClassId() {
			return "TextConfig";
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.twister.extensions.image.AbstractImageConfigXMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public TextConfig importFromElement(final Element element) throws XMLImportException {
			final TextConfig config = super.importFromElement(element);
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 7) {
				importProperties(config, propertyElements);
			}
			return config;
		}

		/**
		 * @param config
		 * @param propertyElements
		 * @throws XMLImportException
		 */
		protected void importProperties(final TextConfig config, final List<Element> propertyElements) throws XMLImportException {
			importColor(config, propertyElements.get(0));
			importFont(config, propertyElements.get(1));
			importSize(config, propertyElements.get(2));
			importText(config, propertyElements.get(3));
			importLeft(config, propertyElements.get(4));
			importTop(config, propertyElements.get(5));
			importRotation(config, propertyElements.get(6));
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importColor(final TextConfig config, final Element element) throws XMLImportException {
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
		protected void importFont(final TextConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, FontElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getFontElement().setValue(new FontElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importSize(final TextConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getSizeElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importText(final TextConfig config, final Element element) throws XMLImportException {
			List<Element> elements = this.getElements(element, StringElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getTextElement().setValue(new StringElementXMLImporter().importFromElement(elements.get(0)).getValue());
			} else {
				//TODO orrore: per retrocompatibilit� devo gestire la classe label
				elements = this.getElements(element, "Label");
				if (elements.size() == 1) {
					config.getTextElement().setValue(new StringElementXMLImporter().importFromElement(elements.get(0)).getValue());
				}
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importLeft(final TextConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getLeftElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importTop(final TextConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getTopElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}

		/**
		 * @param config
		 * @param element
		 * @throws XMLImportException
		 */
		protected void importRotation(final TextConfig config, final Element element) throws XMLImportException {
			final List<Element> elements = this.getElements(element, DoubleElement.CLASS_ID);
			if (elements.size() == 1) {
				config.getRotationElement().setValue(new DoubleElementXMLImporter().importFromElement(elements.get(0)).getValue());
			}
		}
	}
}
