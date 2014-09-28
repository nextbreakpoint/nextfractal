/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.cfdg;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElement;
import com.nextbreakpoint.nextfractal.contextfree.figure.FigureConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.BooleanElement;
import com.nextbreakpoint.nextfractal.core.common.BooleanElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.ColorElement;
import com.nextbreakpoint.nextfractal.core.common.ColorElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.FloatElement;
import com.nextbreakpoint.nextfractal.core.common.FloatElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.common.StringElement;
import com.nextbreakpoint.nextfractal.core.common.StringElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class CFDGConfigElementXMLImporter extends XMLImporter<CFDGConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public CFDGConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, CFDGConfigElement.CLASS_ID);
		final CFDGConfigElement configElement = new CFDGConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 13) {
			try {
				importProperties(configElement, propertyElements);
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final CFDGConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importBaseDir(configElement, propertyElements.get(0));
		importVariation(configElement, propertyElements.get(1));
		importStartshape(configElement, propertyElements.get(2));
		importBackground(configElement, propertyElements.get(3));
		importUseSize(configElement, propertyElements.get(4));
		importX(configElement, propertyElements.get(5));
		importY(configElement, propertyElements.get(6));
		importWidth(configElement, propertyElements.get(7));
		importHeight(configElement, propertyElements.get(8));
		importUseTile(configElement, propertyElements.get(9));
		importTileWidth(configElement, propertyElements.get(10));
		importTileHeight(configElement, propertyElements.get(11));
		importFigureListElement(configElement, propertyElements.get(12));
	}

	private void importBaseDir(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> baseDirElements = this.getElements(element, StringElement.CLASS_ID);
		if (baseDirElements.size() == 1) {
			configElement.setBaseDir(new StringElementXMLImporter().importFromElement(baseDirElements.get(0)).getValue());
		}
	}
	private void importVariation(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> variationElements = this.getElements(element, StringElement.CLASS_ID);
		if (variationElements.size() == 1) {
			configElement.setVariation(new StringElementXMLImporter().importFromElement(variationElements.get(0)).getValue());
		}
	}
	private void importStartshape(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> startshapeElements = this.getElements(element, StringElement.CLASS_ID);
		if (startshapeElements.size() == 1) {
			configElement.setStartshape(new StringElementXMLImporter().importFromElement(startshapeElements.get(0)).getValue());
		}
	}
	private void importBackground(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> backgroundElements = this.getElements(element, ColorElement.CLASS_ID);
		if (backgroundElements.size() == 1) {
			configElement.setBackground(new ColorElementXMLImporter().importFromElement(backgroundElements.get(0)).getValue());
		}
	}
	private void importUseSize(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> useSizeElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (useSizeElements.size() == 1) {
			configElement.setUseSize(new BooleanElementXMLImporter().importFromElement(useSizeElements.get(0)).getValue());
		}
	}
	private void importX(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> xElements = this.getElements(element, FloatElement.CLASS_ID);
		if (xElements.size() == 1) {
			configElement.setX(new FloatElementXMLImporter().importFromElement(xElements.get(0)).getValue());
		}
	}
	private void importY(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> yElements = this.getElements(element, FloatElement.CLASS_ID);
		if (yElements.size() == 1) {
			configElement.setY(new FloatElementXMLImporter().importFromElement(yElements.get(0)).getValue());
		}
	}
	private void importWidth(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> widthElements = this.getElements(element, FloatElement.CLASS_ID);
		if (widthElements.size() == 1) {
			configElement.setWidth(new FloatElementXMLImporter().importFromElement(widthElements.get(0)).getValue());
		}
	}
	private void importHeight(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> heightElements = this.getElements(element, FloatElement.CLASS_ID);
		if (heightElements.size() == 1) {
			configElement.setHeight(new FloatElementXMLImporter().importFromElement(heightElements.get(0)).getValue());
		}
	}
	private void importUseTile(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> useTileElements = this.getElements(element, BooleanElement.CLASS_ID);
		if (useTileElements.size() == 1) {
			configElement.setUseTile(new BooleanElementXMLImporter().importFromElement(useTileElements.get(0)).getValue());
		}
	}
	private void importTileWidth(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> tileWidthElements = this.getElements(element, FloatElement.CLASS_ID);
		if (tileWidthElements.size() == 1) {
			configElement.setTileWidth(new FloatElementXMLImporter().importFromElement(tileWidthElements.get(0)).getValue());
		}
	}
	private void importTileHeight(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> tileHeightElements = this.getElements(element, FloatElement.CLASS_ID);
		if (tileHeightElements.size() == 1) {
			configElement.setTileHeight(new FloatElementXMLImporter().importFromElement(tileHeightElements.get(0)).getValue());
		}
	}
	private void importFigureListElement(final CFDGConfigElement configElement, final Element element) throws XMLImportException {
		final FigureConfigElementXMLImporter figureImporter = new FigureConfigElementXMLImporter();
		final List<Element> figureElements = this.getElements(element, FigureConfigElement.CLASS_ID);
		for (int i = 0; i < figureElements.size(); i++) {
			configElement.appendFigureConfigElement(figureImporter.importFromElement(figureElements.get(i)));
		}
	}
}
