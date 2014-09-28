/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class SkewPathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SkewPathAdjustmentConfig> createXMLImporter() {
		return new SkewPathAdjustmentConfigXMLImporter();
	}

	private class SkewPathAdjustmentConfigXMLImporter extends XMLImporter<SkewPathAdjustmentConfig> {
		protected SkewPathAdjustmentConfig createExtensionConfig() {
			return new SkewPathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "SkewPathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SkewPathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SkewPathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final SkewPathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importShearX(extensionConfig, propertyElements.get(0));
			importShearY(extensionConfig, propertyElements.get(1));
		}
	
		private void importShearX(final SkewPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> shearXElements = this.getElements(element, FloatElement.CLASS_ID);
			if (shearXElements.size() == 1) {
				extensionConfig.setShearX(new FloatElementXMLImporter().importFromElement(shearXElements.get(0)).getValue());
			}
		}
		private void importShearY(final SkewPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> shearYElements = this.getElements(element, FloatElement.CLASS_ID);
			if (shearYElements.size() == 1) {
				extensionConfig.setShearY(new FloatElementXMLImporter().importFromElement(shearYElements.get(0)).getValue());
			}
		}
	}
}
