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
public class TargetBrightnessPathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<TargetBrightnessPathAdjustmentConfig> createXMLImporter() {
		return new TargetBrightnessPathAdjustmentConfigXMLImporter();
	}

	private class TargetBrightnessPathAdjustmentConfigXMLImporter extends XMLImporter<TargetBrightnessPathAdjustmentConfig> {
		protected TargetBrightnessPathAdjustmentConfig createExtensionConfig() {
			return new TargetBrightnessPathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "TargetBrightnessPathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public TargetBrightnessPathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final TargetBrightnessPathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final TargetBrightnessPathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importValue(extensionConfig, propertyElements.get(0));
		}
	
		private void importValue(final TargetBrightnessPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> valueElements = this.getElements(element, FloatElement.CLASS_ID);
			if (valueElements.size() == 1) {
				extensionConfig.setValue(new FloatElementXMLImporter().importFromElement(valueElements.get(0)).getValue());
			}
		}
	}
}
