/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathReplacement;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.pathAdjustment.PathAdjustmentConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.contextfree.pathReplacement.PathReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.IntegerElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLExporter.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class MultiPathReplacementConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.extensionConfigXMLExporter.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<MultiPathReplacementConfig> createXMLExporter() {
		return new MultiPathReplacementConfigXMLExporter();
	}

	private class MultiPathReplacementConfigXMLExporter extends XMLExporter<MultiPathReplacementConfig> {
		protected String getConfigElementClassId() {
			return "MultiPathReplacementConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final MultiPathReplacementConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final MultiPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportTimes(extensionConfig, createProperty(builder, element, "times"), builder);
			exportPathReplacementListElement(extensionConfig, createProperty(builder, element, "pathReplacementList"), builder);
			exportPathAdjustmentListElement(extensionConfig, createProperty(builder, element, "pathAdjustmentList"), builder);
		}
	
		private void exportTimes(final MultiPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new IntegerElementXMLExporter().exportToElement(extensionConfig.getTimesElement(), builder));
		}
		private void exportPathReplacementListElement(final MultiPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final PathReplacementConfigElementXMLExporter pathReplacementExporter = new PathReplacementConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getPathReplacementConfigElementCount(); i++) {
				element.appendChild(pathReplacementExporter.exportToElement(extensionConfig.getPathReplacementConfigElement(i), builder));
			}
		}
		private void exportPathAdjustmentListElement(final MultiPathReplacementConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final PathAdjustmentConfigElementXMLExporter pathAdjustmentExporter = new PathAdjustmentConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getPathAdjustmentConfigElementCount(); i++) {
				element.appendChild(pathAdjustmentExporter.exportToElement(extensionConfig.getPathAdjustmentConfigElement(i), builder));
			}
		}
	}
}
