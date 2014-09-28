/*
 * $Id:$
 *
 */
package com.nextbreakpoint.nextfractal.contextfree.figure;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRegistry;
import com.nextbreakpoint.nextfractal.contextfree.figure.extension.FigureExtensionConfig;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class FigureConfigElementXMLImporter extends XMLImporter<FigureConfigElement> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public FigureConfigElement importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, FigureConfigElement.CLASS_ID);
		final FigureConfigElement configElement = new FigureConfigElement();
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
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
	protected void importProperties(final FigureConfigElement configElement, final List<Element> propertyElements) throws ExtensionException, XMLImportException {
		importExtension(configElement, propertyElements.get(0));
	}

	private void importExtension(final FigureConfigElement configElement, final Element element) throws XMLImportException {
		final List<Element> extensionElements = this.getElements(element, ConfigurableExtensionReferenceElement.CLASS_ID);
		if (extensionElements.size() == 1) {
			configElement.setExtensionReference(new ConfigurableExtensionReferenceElementXMLImporter<FigureExtensionConfig>(ContextFreeRegistry.getInstance().getFigureRegistry()).importFromElement(extensionElements.get(0)).getReference());
		}
	}
}
