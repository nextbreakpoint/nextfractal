/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.elements.IntegerElement;
import com.nextbreakpoint.nextfractal.core.elements.IntegerElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class MultiPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<MultiPathReplacementConfig> createXMLImporter() {
		return new MultiPathReplacementConfigXMLImporter();
	}

	private class MultiPathReplacementConfigXMLImporter extends XMLImporter<MultiPathReplacementConfig> {
		protected MultiPathReplacementConfig createExtensionConfig() {
			return new MultiPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "MultiPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public MultiPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final MultiPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 3) {
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
		protected void importProperties(final MultiPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importTimes(extensionConfig, propertyElements.get(0));
			importPathReplacementListElement(extensionConfig, propertyElements.get(1));
			importPathAdjustmentListElement(extensionConfig, propertyElements.get(2));
		}
	
		private void importTimes(final MultiPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> timesElements = this.getElements(element, IntegerElement.CLASS_ID);
			if (timesElements.size() == 1) {
				extensionConfig.setTimes(new IntegerElementXMLImporter().importFromElement(timesElements.get(0)).getValue());
			}
		}
		private void importPathReplacementListElement(final MultiPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final PathReplacementConfigElementXMLImporter pathReplacementImporter = new PathReplacementConfigElementXMLImporter();
			final List<Element> pathReplacementElements = this.getElements(element, PathReplacementConfigElement.CLASS_ID);
			for (int i = 0; i < pathReplacementElements.size(); i++) {
				extensionConfig.appendPathReplacementConfigElement(pathReplacementImporter.importFromElement(pathReplacementElements.get(i)));
			}
		}
		private void importPathAdjustmentListElement(final MultiPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final PathAdjustmentConfigElementXMLImporter pathAdjustmentImporter = new PathAdjustmentConfigElementXMLImporter();
			final List<Element> pathAdjustmentElements = this.getElements(element, PathAdjustmentConfigElement.CLASS_ID);
			for (int i = 0; i < pathAdjustmentElements.size(); i++) {
				extensionConfig.appendPathAdjustmentConfigElement(pathAdjustmentImporter.importFromElement(pathAdjustmentElements.get(i)));
			}
		}
	}
}
