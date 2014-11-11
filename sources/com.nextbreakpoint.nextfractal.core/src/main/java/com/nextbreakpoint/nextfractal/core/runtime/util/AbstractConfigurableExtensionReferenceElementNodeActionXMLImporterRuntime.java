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
package com.nextbreakpoint.nextfractal.core.runtime.util;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XML;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLImportException;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime<V extends ExtensionConfig> extends AbstractActionXMLImporterRuntime {
	private final ConfigurableExtensionRegistry<? extends ConfigurableExtensionRuntime<?>, V> registry;

	/**
	 * 
	 */
	public AbstractConfigurableExtensionReferenceElementNodeActionXMLImporterRuntime(final ConfigurableExtensionRegistry<? extends ConfigurableExtensionRuntime<?>, V> registry) {
		this.registry = registry;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.util.AbstractActionXMLImporterRuntime#importParams(com.nextbreakpoint.nextfractal.core.runtime.model.NodeActionValue, org.w3c.dom.Element)
	 */
	@Override
	protected void importParams(final NodeActionValue action, final Element element) throws XMLImportException {
		final ConfigurableExtensionReferenceElementXMLImporter<V> importer = new ConfigurableExtensionReferenceElementXMLImporter<V>(registry);
		final List<Element> elements = XML.getElementsByName(element, "element");
		if (elements.size() == 2) {
			final ConfigurableExtensionReferenceElement<?> configElement0 = importer.importFromElement(elements.get(0));
			final ConfigurableExtensionReferenceElement<?> configElement1 = importer.importFromElement(elements.get(1));
			action.setActionParams(new Serializable[] { configElement0.getReference(), configElement1.getReference() });
		}
	}
}
