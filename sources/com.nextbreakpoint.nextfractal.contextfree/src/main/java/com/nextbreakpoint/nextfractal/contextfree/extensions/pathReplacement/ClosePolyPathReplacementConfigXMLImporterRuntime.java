/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ClosePolyPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<ClosePolyPathReplacementConfig> createXMLImporter() {
		return new ClosePolyPathReplacementConfigXMLImporter();
	}

	private class ClosePolyPathReplacementConfigXMLImporter extends XMLImporter<ClosePolyPathReplacementConfig> {
		protected ClosePolyPathReplacementConfig createExtensionConfig() {
			return new ClosePolyPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "ClosePolyPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public ClosePolyPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final ClosePolyPathReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final ClosePolyPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importAlign(extensionConfig, propertyElements.get(0));
		}
	
		private void importAlign(final ClosePolyPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> alignElements = this.getElements(element, BooleanElement.CLASS_ID);
			if (alignElements.size() == 1) {
				extensionConfig.setAlign(new BooleanElementXMLImporter().importFromElement(alignElements.get(0)).getValue());
			}
		}
	}
}
