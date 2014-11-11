/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.cfdg;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.BooleanElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.ColorElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.FloatElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.elements.StringElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public class CFDGConfigElementXMLExporter extends XMLExporter<CFDGConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final CFDGConfigElement configElement, final XMLNodeBuilder builder) throws XMLExportException {
		final Element element = this.createElement(builder, CFDGConfigElement.CLASS_ID);
		try {
			exportProperties(configElement, element, builder);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
		return element;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLExporter#exportProperties(com.nextbreakpoint.nextfractal.twister.util.ConfigurableExtensionConfigElement, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder, java.lang.String)
	 */
	protected void exportProperties(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws ExtensionException, XMLExportException {
		exportBaseDir(configElement, createProperty(builder, element, "baseDir"), builder);
		exportVariation(configElement, createProperty(builder, element, "variation"), builder);
		exportStartshape(configElement, createProperty(builder, element, "startshape"), builder);
		exportBackground(configElement, createProperty(builder, element, "background"), builder);
		exportUseSize(configElement, createProperty(builder, element, "useSize"), builder);
		exportX(configElement, createProperty(builder, element, "x"), builder);
		exportY(configElement, createProperty(builder, element, "y"), builder);
		exportWidth(configElement, createProperty(builder, element, "width"), builder);
		exportHeight(configElement, createProperty(builder, element, "height"), builder);
		exportUseTile(configElement, createProperty(builder, element, "useTile"), builder);
		exportTileWidth(configElement, createProperty(builder, element, "tileWidth"), builder);
		exportTileHeight(configElement, createProperty(builder, element, "tileHeight"), builder);
		exportFigureListElement(configElement, createProperty(builder, element, "figureList"), builder);
	}

	private void exportBaseDir(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getBaseDirElement(), builder));
	}
	private void exportVariation(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getVariationElement(), builder));
	}
	private void exportStartshape(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new StringElementXMLExporter().exportToElement(configElement.getStartshapeElement(), builder));
	}
	private void exportBackground(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new ColorElementXMLExporter().exportToElement(configElement.getBackgroundElement(), builder));
	}
	private void exportUseSize(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getUseSizeElement(), builder));
	}
	private void exportX(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getXElement(), builder));
	}
	private void exportY(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getYElement(), builder));
	}
	private void exportWidth(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getWidthElement(), builder));
	}
	private void exportHeight(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getHeightElement(), builder));
	}
	private void exportUseTile(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new BooleanElementXMLExporter().exportToElement(configElement.getUseTileElement(), builder));
	}
	private void exportTileWidth(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getTileWidthElement(), builder));
	}
	private void exportTileHeight(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		element.appendChild(new FloatElementXMLExporter().exportToElement(configElement.getTileHeightElement(), builder));
	}
	private void exportFigureListElement(final CFDGConfigElement configElement, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final FigureConfigElementXMLExporter figureExporter = new FigureConfigElementXMLExporter();
		for (int i = 0; i < configElement.getFigureConfigElementCount(); i++) {
			element.appendChild(figureExporter.exportToElement(configElement.getFigureConfigElement(i), builder));
		}
	}
}
