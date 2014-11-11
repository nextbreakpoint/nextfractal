/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.FloatElement;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class CurveRelPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<CurveRelPathReplacementConfig> createXMLImporter() {
		return new CurveRelPathReplacementConfigXMLImporter();
	}

	private class CurveRelPathReplacementConfigXMLImporter extends XMLImporter<CurveRelPathReplacementConfig> {
		protected CurveRelPathReplacementConfig createExtensionConfig() {
			return new CurveRelPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "CurveRelPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public CurveRelPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final CurveRelPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 6) {
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
		protected void importProperties(final CurveRelPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importX1(extensionConfig, propertyElements.get(2));
			importY1(extensionConfig, propertyElements.get(3));
			importX2(extensionConfig, propertyElements.get(4));
			importY2(extensionConfig, propertyElements.get(5));
		}
	
		private void importX(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importX1(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x1Elements.size() == 1) {
				extensionConfig.setX1(new FloatElementXMLImporter().importFromElement(x1Elements.get(0)).getValue());
			}
		}
		private void importY1(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y1Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y1Elements.size() == 1) {
				extensionConfig.setY1(new FloatElementXMLImporter().importFromElement(y1Elements.get(0)).getValue());
			}
		}
		private void importX2(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x2Elements.size() == 1) {
				extensionConfig.setX2(new FloatElementXMLImporter().importFromElement(x2Elements.get(0)).getValue());
			}
		}
		private void importY2(final CurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y2Elements.size() == 1) {
				extensionConfig.setY2(new FloatElementXMLImporter().importFromElement(y2Elements.get(0)).getValue());
			}
		}
	}
}
