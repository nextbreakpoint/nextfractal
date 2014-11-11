/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.elements.FloatElement;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class CurrentBrightnessPathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<CurrentBrightnessPathAdjustmentConfig> createXMLImporter() {
		return new CurrentBrightnessPathAdjustmentConfigXMLImporter();
	}

	private class CurrentBrightnessPathAdjustmentConfigXMLImporter extends XMLImporter<CurrentBrightnessPathAdjustmentConfig> {
		protected CurrentBrightnessPathAdjustmentConfig createExtensionConfig() {
			return new CurrentBrightnessPathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "CurrentBrightnessPathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public CurrentBrightnessPathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final CurrentBrightnessPathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final CurrentBrightnessPathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importValue(extensionConfig, propertyElements.get(0));
			importTarget(extensionConfig, propertyElements.get(1));
		}
	
		private void importValue(final CurrentBrightnessPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> valueElements = this.getElements(element, FloatElement.CLASS_ID);
			if (valueElements.size() == 1) {
				extensionConfig.setValue(new FloatElementXMLImporter().importFromElement(valueElements.get(0)).getValue());
			}
		}
		private void importTarget(final CurrentBrightnessPathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> targetElements = this.getElements(element, BooleanElement.CLASS_ID);
			if (targetElements.size() == 1) {
				extensionConfig.setTarget(new BooleanElementXMLImporter().importFromElement(targetElements.get(0)).getValue());
			}
		}
	}
}
