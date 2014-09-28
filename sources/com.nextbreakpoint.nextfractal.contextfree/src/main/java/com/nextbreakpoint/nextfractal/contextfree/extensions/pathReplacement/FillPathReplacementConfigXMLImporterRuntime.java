/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElement;
import com.nextbreakpoint.nextfractal.contextfree.common.FillRuleElementXMLImporter;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class FillPathReplacementConfigXMLImporterRuntime extends ExtensionConfigXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime#createXMLImporter()
	 */
	@Override
	public XMLImporter<FillPathReplacementConfig> createXMLImporter() {
		return new FillPathReplacementConfigXMLImporter();
	}

	private class FillPathReplacementConfigXMLImporter extends XMLImporter<FillPathReplacementConfig> {
		protected FillPathReplacementConfig createExtensionConfig() {
			return new FillPathReplacementConfig();
		}

		protected String getConfigElementClassId() {
			return "FillPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public FillPathReplacementConfig importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, this.getConfigElementClassId());
			final FillPathReplacementConfig extensionConfig = this.createExtensionConfig();
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
		protected void importProperties(final FillPathReplacementConfig extensionConfig, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
			importRule(extensionConfig, propertyElements.get(0));
			importPathAdjustmentListElement(extensionConfig, propertyElements.get(1));
		}
	
		private void importRule(final FillPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final List<Element> ruleElements = this.getElements(element, FillRuleElement.CLASS_ID);
			if (ruleElements.size() == 1) {
				extensionConfig.setRule(new FillRuleElementXMLImporter().importFromElement(ruleElements.get(0)).getValue());
			}
		}
		private void importPathAdjustmentListElement(final FillPathReplacementConfig extensionConfig, final Element element) throws XMLImportException {
			final PathAdjustmentConfigElementXMLImporter pathAdjustmentImporter = new PathAdjustmentConfigElementXMLImporter();
			final List<Element> pathAdjustmentElements = this.getElements(element, PathAdjustmentConfigElement.CLASS_ID);
			for (int i = 0; i < pathAdjustmentElements.size(); i++) {
				extensionConfig.appendPathAdjustmentConfigElement(pathAdjustmentImporter.importFromElement(pathAdjustmentElements.get(i)));
			}
		}
	}
}
