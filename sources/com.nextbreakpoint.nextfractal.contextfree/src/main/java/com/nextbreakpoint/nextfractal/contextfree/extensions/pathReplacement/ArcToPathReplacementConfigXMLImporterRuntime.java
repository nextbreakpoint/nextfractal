/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

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
public class ArcToPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<ArcToPathReplacementConfig> createXMLImporter() {
		return new ArcToPathReplacementConfigXMLImporter();
	}

	private class ArcToPathReplacementConfigXMLImporter extends XMLImporter<ArcToPathReplacementConfig> {
		protected ArcToPathReplacementConfig createExtensionConfig() {
			return new ArcToPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "ArcToPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public ArcToPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final ArcToPathReplacementConfig extensionConfig = this.createExtensionConfig();
			final List<Element> propertyElements = getProperties(element);
			if (propertyElements.size() == 7) {
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
		protected void importProperties(final ArcToPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importX(extensionConfig, propertyElements.get(0));
			importY(extensionConfig, propertyElements.get(1));
			importRx(extensionConfig, propertyElements.get(2));
			importRy(extensionConfig, propertyElements.get(3));
			importR(extensionConfig, propertyElements.get(4));
			importSweep(extensionConfig, propertyElements.get(5));
			importLarge(extensionConfig, propertyElements.get(6));
		}
	
		private void importX(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
			if (xElements.size() == 1) {
				extensionConfig.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
			}
		}
		private void importY(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
			if (yElements.size() == 1) {
				extensionConfig.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
			}
		}
		private void importRx(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> rxElements = this.getElements(element, FloatElement.CLASS_ID);
			if (rxElements.size() == 1) {
				extensionConfig.setRx(new FloatElementXMLImporter().importFromElement(rxElements.get(0)).getValue());
			}
		}
		private void importRy(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ryElements = this.getElements(element, FloatElement.CLASS_ID);
			if (ryElements.size() == 1) {
				extensionConfig.setRy(new FloatElementXMLImporter().importFromElement(ryElements.get(0)).getValue());
			}
		}
		private void importR(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> rElements = this.getElements(element, FloatElement.CLASS_ID);
			if (rElements.size() == 1) {
				extensionConfig.setR(new FloatElementXMLImporter().importFromElement(rElements.get(0)).getValue());
			}
		}
		private void importSweep(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> sweepElements = this.getElements(element, BooleanElement.CLASS_ID);
			if (sweepElements.size() == 1) {
				extensionConfig.setSweep(new BooleanElementXMLImporter().importFromElement(sweepElements.get(0)).getValue());
			}
		}
		private void importLarge(final ArcToPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> largeElements = this.getElements(element, BooleanElement.CLASS_ID);
			if (largeElements.size() == 1) {
				extensionConfig.setLarge(new BooleanElementXMLImporter().importFromElement(largeElements.get(0)).getValue());
			}
		}
	}
}
