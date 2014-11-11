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
package com.nextbreakpoint.nextfractal.core.runtime.common;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLExportException;
import com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractConfigurableExtensionReferenceElementNodeActionXMLExporterRuntime<V extends ExtensionConfig> extends AbstractActionXMLExporterRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.common.AbstractActionXMLExporterRuntime#exportParams(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeActionValue, org.w3c.dom.Element, com.nextbreakpoint.nextfractal.core.runtime.xml.XMLNodeBuilder)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void exportParams(final NodeActionValue action, final Element element, final XMLNodeBuilder builder) throws XMLExportException {
		final ConfigurableExtensionReferenceElementXMLExporter<V> exporter = new ConfigurableExtensionReferenceElementXMLExporter<V>();
		final ConfigurableExtensionReferenceElement<V> configElement0 = new ConfigurableExtensionReferenceElement<V>();
		final ConfigurableExtensionReferenceElement<V> configElement1 = new ConfigurableExtensionReferenceElement<V>();
		configElement0.setReference((ConfigurableExtensionReference<V>) action.getActionParams()[0]);
		configElement1.setReference((ConfigurableExtensionReference<V>) action.getActionParams()[1]);
		element.appendChild(exporter.exportToElement(configElement0, builder));
		element.appendChild(exporter.exportToElement(configElement1, builder));
	}
}
