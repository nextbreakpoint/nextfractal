/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.core.extension;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ExtensionConfigXMLImporter<V extends ExtensionConfig> extends XMLImporter<V> {
	/**
	 * 
	 */
	public ExtensionConfigXMLImporter() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public V importFromElement(final Element element) throws XMLImportException {
		checkClassId(element, "reference");
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == 1) {
			try {
				return importProperties(propertyElements, getExtensionId(element));
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return null;
	}

	/**
	 * @param propertyElements
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLImportException
	 * @return
	 */
	protected V importProperties(final List<Element> propertyElements, final String extensionId) throws ExtensionException, XMLImportException {
		return this.importReference(propertyElements.get(0), extensionId);
	}

	/**
	 * @param element
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLImportException
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected V importReference(final Element element, final String extensionId) throws ExtensionException, XMLImportException {
		final List<Element> configElements = XML.getElementsByName(element, XMLExporter.ELEMENT_TAG_NAME);
		if (configElements.size() == 1) {
			final Extension<ExtensionConfigXMLImporterExtensionRuntime> importerExtension = CoreRegistry.getInstance().getXMLExtensionConfigImporterExtension(extensionId);
			final XMLImporter<V> importer = (XMLImporter<V>) importerExtension.createExtensionRuntime().createXMLImporter();
			return importer.importFromElement(configElements.get(0));
		}
		return null;
	}
}
