/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.figure;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.shapeReplacement.ShapeReplacementConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class RuleFigureConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<RuleFigureConfig> createXMLExporter() {
		return new RuleFigureConfigXMLExporter();
	}

	private class RuleFigureConfigXMLExporter extends XMLExporter<RuleFigureConfig> {
		protected String getConfigElementClassId() {
			return "RuleFigureConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final RuleFigureConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final RuleFigureConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportName(extensionConfig, createProperty(builder, element, "name"), builder);
			exportProbability(extensionConfig, createProperty(builder, element, "probability"), builder);
			exportShapeReplacementListElement(extensionConfig, createProperty(builder, element, "shapeReplacementList"), builder);
		}
	
		private void exportName(final RuleFigureConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new StringElementXMLExporter().exportToElement(extensionConfig.getNameElement(), builder));
		}
		private void exportProbability(final RuleFigureConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getProbabilityElement(), builder));
		}
		private void exportShapeReplacementListElement(final RuleFigureConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			final ShapeReplacementConfigElementXMLExporter shapeReplacementExporter = new ShapeReplacementConfigElementXMLExporter();
			for (int i = 0; i < extensionConfig.getShapeReplacementConfigElementCount(); i++) {
				element.appendChild(shapeReplacementExporter.exportToElement(extensionConfig.getShapeReplacementConfigElement(i), builder));
			}
		}
	}
}
