/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.IntegerElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class MultiShapeReplacementConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<MultiShapeReplacementConfig> createXMLExporter() {
		return new MultiShapeReplacementConfigXMLExporter();
	}

	private class MultiShapeReplacementConfigXMLExporter extends XMLExporter<MultiShapeReplacementConfig> {
		protected String getConfigElementClassId() {
			return "MultiShapeReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final MultiShapeReplacementConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
			final Element element = this.createElement(builder, this.getConfigElementClassId());
			try {
				exportProperties(extensionConfig, element, builder);
			}
			catch (final ExtensionException e) {
				throw new XMLExportException(e);
			}
			return element;
		}
	
		/**
		 * @see com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder, java.lang.String)
		 */
		protected void exportProperties(final MultiShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportTimes(extensionConfig, createProperty(builder, element, "times"), builder);
			exportShapeReplacementListElement(extensionConfig, createProperty(builder, element, "shapeReplacementList"), builder);
			exportShapeAdjustmentListElement(extensionConfig, createProperty(builder, element, "shapeAdjustmentList"), builder);
		}
	
		private void exportTimes(final MultiShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new IntegerElementXMLExporter().exportToElement(extensionConfig.getTimesElement(), builder));
		}
		private void exportShapeReplacementListElement(final MultiShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ShapeReplacementConfigElementXMLExporter shapeReplacementExporter = new ShapeReplacementConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getShapeReplacementConfigElementCount(); i++) {
				element.appendChild(shapeReplacementExporter.exportToElement(extensionConfig.getShapeReplacementConfigElement(i), builder));
			}
		}
		private void exportShapeAdjustmentListElement(final MultiShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ShapeAdjustmentConfigElementXMLExporter shapeAdjustmentExporter = new ShapeAdjustmentConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getShapeAdjustmentConfigElementCount(); i++) {
				element.appendChild(shapeAdjustmentExporter.exportToElement(extensionConfig.getShapeAdjustmentConfigElement(i), builder));
			}
		}
	}
}
