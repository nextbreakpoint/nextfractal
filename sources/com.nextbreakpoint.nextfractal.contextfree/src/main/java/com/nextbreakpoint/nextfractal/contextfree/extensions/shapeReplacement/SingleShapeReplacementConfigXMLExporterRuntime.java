/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.shapeReplacement;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.shapeAdjustment.ShapeAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class SingleShapeReplacementConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<SingleShapeReplacementConfig> createXMLExporter() {
		return new SingleShapeReplacementConfigXMLExporter();
	}

	private class SingleShapeReplacementConfigXMLExporter extends XMLExporter<SingleShapeReplacementConfig> {
		protected String getConfigElementClassId() {
			return "SingleShapeReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final SingleShapeReplacementConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final SingleShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportShape(extensionConfig, createProperty(builder, element, "shape"), builder);
			exportShapeAdjustmentListElement(extensionConfig, createProperty(builder, element, "shapeAdjustmentList"), builder);
		}
	
		private void exportShape(final SingleShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StringElementXMLExporter().exportToElement(extensionConfig.getShapeElement(), builder));
		}
		private void exportShapeAdjustmentListElement(final SingleShapeReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ShapeAdjustmentConfigElementXMLExporter shapeAdjustmentExporter = new ShapeAdjustmentConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getShapeAdjustmentConfigElementCount(); i++) {
				element.appendChild(shapeAdjustmentExporter.exportToElement(extensionConfig.getShapeAdjustmentConfigElement(i), builder));
			}
		}
	}
}
