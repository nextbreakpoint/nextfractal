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
public class RotatePathAdjustmentConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<RotatePathAdjustmentConfig> createXMLImporter() {
		return new RotatePathAdjustmentConfigXMLImporter();
	}

	private class RotatePathAdjustmentConfigXMLImporter extends XMLImporter<RotatePathAdjustmentConfig> {
		protected RotatePathAdjustmentConfig createExtensionConfig() {
			return new RotatePathAdjustmentConfig();
		}

		protected String getConfigElementClassId() {
			return "RotatePathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public RotatePathAdjustmentConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final RotatePathAdjustmentConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final RotatePathAdjustmentConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importAngle(extensionConfig, propertyElements.get(0));
		}
	
		private void importAngle(final RotatePathAdjustmentConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> angleElements = this.getElements(element, FloatElement.CLASS_ID);
			if (angleElements.size() == 1) {
				extensionConfig.setAngle(new FloatElementXMLImporter().importFromElement(angleElements.get(0)).getValue());
			}
		}
	}
}
