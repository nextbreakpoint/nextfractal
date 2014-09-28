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
public class SizePathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SizePathAdjustmentConfig> createXMLImporter() {
		return new SizePathAdjustmentConfigXMLImporter();
	}

	private class SizePathAdjustmentConfigXMLImporter extends XMLImporter<SizePathAdjustmentConfig> {
		protected SizePathAdjustmentConfig createExtensionConfig() {
			return new SizePathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "SizePathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SizePathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SizePathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final SizePathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importScale(extensionConfig, propertyElements.get(0));
		}
	
		private void importScale(final SizePathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> scaleElements = this.getElements(element, FloatElement.CLASS_ID);
			if (scaleElements.size() == 1) {
				extensionConfig.setScale(new FloatElementXMLImporter().importFromElement(scaleElements.get(0)).getValue());
			}
		}
	}
}
