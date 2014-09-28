/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.common.StrokeCapElementXMLExporter;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeJoinElementXMLExporter;
import com.nextbreakpoint.nextfractal.contextfree.common.StrokeWidthElementXMLExporter;
import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class StrokePathReplacementConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<StrokePathReplacementConfig> createXMLExporter() {
		return new StrokePathReplacementConfigXMLExporter();
	}

	private class StrokePathReplacementConfigXMLExporter extends XMLExporter<StrokePathReplacementConfig> {
		protected String getConfigElementClassId() {
			return "StrokePathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final StrokePathReplacementConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final StrokePathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportWidth(extensionConfig, createProperty(builder, element, "width"), builder);
			exportCap(extensionConfig, createProperty(builder, element, "cap"), builder);
			exportJoin(extensionConfig, createProperty(builder, element, "join"), builder);
			exportPathAdjustmentListElement(extensionConfig, createProperty(builder, element, "pathAdjustmentList"), builder);
		}
	
		private void exportWidth(final StrokePathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StrokeWidthElementXMLExporter().exportToElement(extensionConfig.getWidthElement(), builder));
		}
		private void exportCap(final StrokePathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StrokeJoinElementXMLExporter().exportToElement(extensionConfig.getCapElement(), builder));
		}
		private void exportJoin(final StrokePathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StrokeCapElementXMLExporter().exportToElement(extensionConfig.getJoinElement(), builder));
		}
		private void exportPathAdjustmentListElement(final StrokePathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final PathAdjustmentConfigElementXMLExporter pathAdjustmentExporter = new PathAdjustmentConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getPathAdjustmentConfigElementCount(); i++) {
				element.appendChild(pathAdjustmentExporter.exportToElement(extensionConfig.getPathAdjustmentConfigElement(i), builder));
			}
		}
	}
}
