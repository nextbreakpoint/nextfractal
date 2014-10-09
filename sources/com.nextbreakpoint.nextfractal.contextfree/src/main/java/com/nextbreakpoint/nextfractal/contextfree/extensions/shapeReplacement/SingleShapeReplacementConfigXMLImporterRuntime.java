/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLImporter.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<SingleShapeReplacementConfig> createXMLImporter() {
		return new SingleShapeReplacementConfigXMLImporter();
	}

	private class SingleShapeReplacementConfigXMLImporter extends XMLImporter<SingleShapeReplacementConfig> {
		protected SingleShapeReplacementConfig createExtensionConfig() {
			return new SingleShapeReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "SingleShapeReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public SingleShapeReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final SingleShapeReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final SingleShapeReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importShape(extensionConfig, propertyElements.get(0));
			importShapeAdjustmentListElement(extensionConfig, propertyElements.get(1));
		}
	
		private void importShape(final SingleShapeReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> shapeElements = this.getElements(element, StringElement.CLASS_ID);
			if (shapeElements.size() == 1) {
				extensionConfig.setShape(new StringElementXMLImporter().importFromElement(shapeElements.get(0)).getValue());
			}
		}
		private void importShapeAdjustmentListElement(final SingleShapeReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final ShapeAdjustmentConfigElementXMLImporter shapeAdjustmentImporter = new ShapeAdjustmentConfigElementXMLImporter();
			final List<Element> shapeAdjustmentElements = this.getElements(element, ShapeAdjustmentConfigElement.CLASS_ID);
			for (int i = 0; i < shapeAdjustmentElements.size(); i++) {
				extensionConfig.appendShapeAdjustmentConfigElement(shapeAdjustmentImporter.importFromElement(shapeAdjustmentElements.get(i)));
			}
		}
	}
}
