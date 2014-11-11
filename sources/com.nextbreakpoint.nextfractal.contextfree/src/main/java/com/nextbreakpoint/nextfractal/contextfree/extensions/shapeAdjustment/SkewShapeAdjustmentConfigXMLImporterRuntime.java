/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

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
public class SkewShapeAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SkewShapeAdjustmentConfig> createXMLImporter() {
		return new SkewShapeAdjustmentConfigXMLImporter();
	}

	private class SkewShapeAdjustmentConfigXMLImporter extends XMLImporter<SkewShapeAdjustmentConfig> {
		protected SkewShapeAdjustmentConfig createExtensionConfig() {
			return new SkewShapeAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "SkewShapeAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SkewShapeAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SkewShapeAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final SkewShapeAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importShearX(extensionConfig, propertyElements.get(0));
			importShearY(extensionConfig, propertyElements.get(1));
		}
	
		private void importShearX(final SkewShapeAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> shearXElements = this.getElements(element, FloatElement.CLASS_ID);
			if (shearXElements.size() == 1) {
				extensionConfig.setShearX(new FloatElementXMLImporter().importFromElement(shearXElements.get(0)).getValue());
			}
		}
		private void importShearY(final SkewShapeAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> shearYElements = this.getElements(element, FloatElement.CLASS_ID);
			if (shearYElements.size() == 1) {
				extensionConfig.setShearY(new FloatElementXMLImporter().importFromElement(shearYElements.get(0)).getValue());
			}
		}
	}
}
