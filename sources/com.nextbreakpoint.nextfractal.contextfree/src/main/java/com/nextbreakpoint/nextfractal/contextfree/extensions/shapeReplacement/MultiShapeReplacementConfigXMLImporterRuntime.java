/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.IntegerElement;
import com.nextbreakpoint.nextfractal.core.common.IntegerElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class MultiShapeReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<MultiShapeReplacementConfig> createXMLImporter() {
		return new MultiShapeReplacementConfigXMLImporter();
	}

	private class MultiShapeReplacementConfigXMLImporter extends XMLImporter<MultiShapeReplacementConfig> {
		protected MultiShapeReplacementConfig createExtensionConfig() {
			return new MultiShapeReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "MultiShapeReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public MultiShapeReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final MultiShapeReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final MultiShapeReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importTimes(extensionConfig, propertyElements.get(0));
			importShapeReplacementListElement(extensionConfig, propertyElements.get(1));
			importShapeAdjustmentListElement(extensionConfig, propertyElements.get(2));
		}
	
		private void importTimes(final MultiShapeReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> timesElements = this.getElements(element, IntegerElement.CLASS_ID);
			if (timesElements.size() == 1) {
				extensionConfig.setTimes(new IntegerElementXMLImporter().importFromElement(timesElements.get(0)).getValue());
			}
		}
		private void importShapeReplacementListElement(final MultiShapeReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final ShapeReplacementConfigElementXMLImporter shapeReplacementImporter = new ShapeReplacementConfigElementXMLImporter();
			final List<Element> shapeReplacementElements = this.getElements(element, ShapeReplacementConfigElement.CLASS_ID);
			for (int i = 0; i < shapeReplacementElements.size(); i++) {
				extensionConfig.appendShapeReplacementConfigElement(shapeReplacementImporter.importFromElement(shapeReplacementElements.get(i)));
			}
		}
		private void importShapeAdjustmentListElement(final MultiShapeReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final ShapeAdjustmentConfigElementXMLImporter shapeAdjustmentImporter = new ShapeAdjustmentConfigElementXMLImporter();
			final List<Element> shapeAdjustmentElements = this.getElements(element, ShapeAdjustmentConfigElement.CLASS_ID);
			for (int i = 0; i < shapeAdjustmentElements.size(); i++) {
				extensionConfig.appendShapeAdjustmentConfigElement(shapeAdjustmentImporter.importFromElement(shapeAdjustmentElements.get(i)));
			}
		}
	}
}
