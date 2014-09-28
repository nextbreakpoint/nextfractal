/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class CurrentSaturationPathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<CurrentSaturationPathAdjustmentConfig> createXMLImporter() {
		return new CurrentSaturationPathAdjustmentConfigXMLImporter();
	}

	private class CurrentSaturationPathAdjustmentConfigXMLImporter extends XMLImporter<CurrentSaturationPathAdjustmentConfig> {
		protected CurrentSaturationPathAdjustmentConfig createExtensionConfig() {
			return new CurrentSaturationPathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "CurrentSaturationPathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public CurrentSaturationPathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final CurrentSaturationPathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final CurrentSaturationPathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importValue(extensionConfig, propertyElements.get(0));
			importTarget(extensionConfig, propertyElements.get(1));
		}
	
		private void importValue(final CurrentSaturationPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> valueElements = this.getElements(element, FloatElement.CLASS_ID);
			if (valueElements.size() == 1) {
				extensionConfig.setValue(new FloatElementXMLImporter().importFromElement(valueElements.get(0)).getValue());
			}
		}
		private void importTarget(final CurrentSaturationPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> targetElements = this.getElements(element, BooleanElement.CLASS_ID);
			if (targetElements.size() == 1) {
				extensionConfig.setTarget(new BooleanElementXMLImporter().importFromElement(targetElements.get(0)).getValue());
			}
		}
	}
}
