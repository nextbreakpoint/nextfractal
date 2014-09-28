/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.extensions.pathAdjustment;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class CurrentHuePathAdjustmentConfigXMLExporterRuntime extends ExtensionConfigXMLExporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionConfigXMLExporter.extension.ExtensionConfigXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLExporter<CurrentHuePathAdjustmentConfig> createXMLExporter() {
		return new CurrentHuePathAdjustmentConfigXMLExporter();
	}

	private class CurrentHuePathAdjustmentConfigXMLExporter extends XMLExporter<CurrentHuePathAdjustmentConfig> {
		protected String getConfigElementClassId() {
			return "CurrentHuePathAdjustmentConfig";
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
		 */
		@Override
		public Element exportToElement(final CurrentHuePathAdjustmentConfig extensionConfig, final XMLNodeBuilder builder) throws XMLExportException {
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
		protected void exportProperties(final CurrentHuePathAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
			exportValue(extensionConfig, createProperty(builder, element, "value"), builder);
			exportTarget(extensionConfig, createProperty(builder, element, "target"), builder);
		}
	
		private void exportValue(final CurrentHuePathAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new FloatElementXMLExporter().exportToElement(extensionConfig.getValueElement(), builder));
		}
		private void exportTarget(final CurrentHuePathAdjustmentConfig extensionConfig, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
			element.appendChild(new BooleanElementXMLExporter().exportToElement(extensionConfig.getTargetElement(), builder));
		}
	}
}
