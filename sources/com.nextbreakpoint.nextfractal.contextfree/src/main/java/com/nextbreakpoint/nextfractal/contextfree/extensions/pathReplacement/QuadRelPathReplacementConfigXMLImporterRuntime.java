/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class QuadRelPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<QuadRelPathReplacementConfig> createXMLImporter() {
		return new QuadRelPathReplacementConfigXMLImporter();
	}

	private class QuadRelPathReplacementConfigXMLImporter extends XMLImporter<QuadRelPathReplacementConfig> {
		protected QuadRelPathReplacementConfig createExtensionConfig() {
			return new QuadRelPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "QuadRelPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public QuadRelPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final QuadRelPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 4) {
				try {
					importProperties(extensionConfig, propertyElements);
				}
				catch (final ExtensionException e) {
					throw new XMLImportException(e);
				}
			}
			return extensionConfig;
		}
	
		/**
		 * @param extensionConfig
		 * @param propertyElements
		 * @throws ExtensionException
		 * @throws XMLImportException
		 */
		protected void importProperties(final QuadRelPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importX1(extensionConfig, propertyElements.get(2));
			importY1(extensionConfig, propertyElements.get(3));
		}
	
		private void importX(final QuadRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final QuadRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importX1(final QuadRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x1Elements.size() == 1) {
				extensionConfig.setX1(new FloatElementXMLImporter().importFromElement(x1Elements.get(0)).getValue());
			}
		}
		private void importY1(final QuadRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y1Elements.size() == 1) {
				extensionConfig.setY1(new FloatElementXMLImporter().importFromElement(y1Elements.get(0)).getValue());
			}
		}
	}
}
