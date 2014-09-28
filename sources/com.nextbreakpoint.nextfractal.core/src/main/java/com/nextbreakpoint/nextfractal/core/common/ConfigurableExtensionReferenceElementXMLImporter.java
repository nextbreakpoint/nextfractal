/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.core.common;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionConfigXMLImporter.extension.ExtensionConfigXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLExporter;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public class ConfigurableExtensionReferenceElementXMLImporter<V extends ExtensionConfig> extends XMLImporter<ConfigurableExtensionReferenceElement<V>> {
	private final ConfigurableExtensionRegistry<? extends ConfigurableExtensionRuntime<?>, V> registry;

	/**
	 * @param registry
	 */
	public ConfigurableExtensionReferenceElementXMLImporter(final ConfigurableExtensionRegistry<? extends ConfigurableExtensionRuntime<?>, V> registry) {
		this.registry = registry;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
	 */
	@Override
	public ConfigurableExtensionReferenceElement<V> importFromElement(final Element element) throws XMLImportException {
		final ConfigurableExtensionReferenceElement<V> configElement = new ConfigurableExtensionReferenceElement<V>();
		checkClassId(element, configElement.getClassId());
		final List<Element> propertyElements = getProperties(element);
		if (propertyElements.size() == getPropertyCount()) {
			try {
				this.importProperties(configElement, propertyElements, getExtensionId(element));
			}
			catch (final ExtensionException e) {
				throw new XMLImportException(e);
			}
		}
		return configElement;
	}

	/**
	 * @return
	 */
	protected int getPropertyCount() {
		return 1;
	}

	/**
	 * @param configElement
	 * @param propertyElements
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	protected void importProperties(final ConfigurableExtensionReferenceElement<V> configElement, final List<Element> propertyElements, final String extensionId) throws ExtensionException, XMLImportException {
		if (!"".equals(extensionId)) {
			this.importReference(configElement, propertyElements.get(0), extensionId);
		}
	}

	/**
	 * @param configElement
	 * @param element
	 * @param extensionId
	 * @throws ExtensionException
	 * @throws XMLImportException
	 */
	@SuppressWarnings("unchecked")
	protected void importReference(final ConfigurableExtensionReferenceElement<V> configElement, final Element element, final String extensionId) throws ExtensionException, XMLImportException {
		final List<Element> configElements = XML.getElementsByName(element, XMLExporter.ELEMENT_TAG_NAME);
		if (configElements.size() == 1) {
			final Extension<ExtensionConfigXMLImporterExtensionRuntime> importerExtension = CoreRegistry.getInstance().getXMLExtensionConfigImporterExtension(extensionId);
			final XMLImporter<V> importer = (XMLImporter<V>) importerExtension.createExtensionRuntime().createXMLImporter();
			final V extensionConfig = importer.importFromElement(configElements.get(0));
			configElement.setReference(registry.getConfigurableExtension(extensionId).createConfigurableExtensionReference(extensionConfig));
		}
	}
}
