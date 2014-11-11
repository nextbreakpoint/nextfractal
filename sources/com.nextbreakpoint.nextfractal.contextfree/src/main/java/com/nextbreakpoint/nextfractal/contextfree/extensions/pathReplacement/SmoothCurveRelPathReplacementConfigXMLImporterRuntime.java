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
public class SmoothCurveRelPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SmoothCurveRelPathReplacementConfig> createXMLImporter() {
		return new SmoothCurveRelPathReplacementConfigXMLImporter();
	}

	private class SmoothCurveRelPathReplacementConfigXMLImporter extends XMLImporter<SmoothCurveRelPathReplacementConfig> {
		protected SmoothCurveRelPathReplacementConfig createExtensionConfig() {
			return new SmoothCurveRelPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "SmoothCurveRelPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SmoothCurveRelPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SmoothCurveRelPathReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final SmoothCurveRelPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importX2(extensionConfig, propertyElements.get(2));
			importY2(extensionConfig, propertyElements.get(3));
		}
	
		private void importX(final SmoothCurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final SmoothCurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importX2(final SmoothCurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> x2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (x2Elements.size() == 1) {
				extensionConfig.setX2(new FloatElementXMLImporter().importFromElement(x2Elements.get(0)).getValue());
			}
		}
		private void importY2(final SmoothCurveRelPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> y2Elements = this.getElements(element, FloatElement.CLASS_ID);
			if (y2Elements.size() == 1) {
				extensionConfig.setY2(new FloatElementXMLImporter().importFromElement(y2Elements.get(0)).getValue());
			}
		}
	}
}
