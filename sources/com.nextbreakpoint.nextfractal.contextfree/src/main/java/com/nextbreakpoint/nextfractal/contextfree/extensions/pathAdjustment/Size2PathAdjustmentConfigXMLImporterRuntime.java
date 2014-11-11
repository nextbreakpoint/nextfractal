/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

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
public class Size2PathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<Size2PathAdjustmentConfig> createXMLImporter() {
		return new Size2PathAdjustmentConfigXMLImporter();
	}

	private class Size2PathAdjustmentConfigXMLImporter extends XMLImporter<Size2PathAdjustmentConfig> {
		protected Size2PathAdjustmentConfig createExtensionConfig() {
			return new Size2PathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "Size2PathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public Size2PathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final Size2PathAdjustmentConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 2) {
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
		protected void importProperties(final Size2PathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importScaleX(extensionConfig, propertyElements.get(0));
			importScaleY(extensionConfig, propertyElements.get(1));
		}
	
		private void importScaleX(final Size2PathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> scaleXElements = this.getElements(element, FloatElement.CLASS_ID);
			if (scaleXElements.size() == 1) {
				extensionConfig.setScaleX(new FloatElementXMLImporter().importFromElement(scaleXElements.get(0)).getValue());
			}
		}
		private void importScaleY(final Size2PathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> scaleYElements = this.getElements(element, FloatElement.CLASS_ID);
			if (scaleYElements.size() == 1) {
				extensionConfig.setScaleY(new FloatElementXMLImporter().importFromElement(scaleYElements.get(0)).getValue());
			}
		}
	}
}
