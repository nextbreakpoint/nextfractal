/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeAdjustment;

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
public class SizeShapeAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SizeShapeAdjustmentConfig> createXMLImporter() {
		return new SizeShapeAdjustmentConfigXMLImporter();
	}

	private class SizeShapeAdjustmentConfigXMLImporter extends XMLImporter<SizeShapeAdjustmentConfig> {
		protected SizeShapeAdjustmentConfig createExtensionConfig() {
			return new SizeShapeAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "SizeShapeAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SizeShapeAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SizeShapeAdjustmentConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 1) {
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
		protected void importProperties(final SizeShapeAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importScale(extensionConfig, propertyElements.get(0));
		}
	
		private void importScale(final SizeShapeAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> scaleElements = this.getElements(element, FloatElement.CLASS_ID);
			if (scaleElements.size() == 1) {
				extensionConfig.setScale(new FloatElementXMLImporter().importFromElement(scaleElements.get(0)).getValue());
			}
		}
	}
}
