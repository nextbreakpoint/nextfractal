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
package com.nextbreakpoint.nextfractal.core.xml;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extensionPoints.actionXMLExporter.ActionXMLExporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;

/**
 * @author Andrea Medeghini
 */
public class ActionXMLExporter extends XMLExporter<NodeActionValue> {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.xml.XMLExporter#exportToElement(java.lang.Object, com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder)
	 */
	@Override
	public Element exportToElement(final NodeActionValue action, final XMLNodeBuilder builder) throws XMLExportException {
		try {
			final Extension<ActionXMLExporterExtensionRuntime> extension = CoreRegistry.getInstance().getXMLActionExporterExtension(action.getActionId());
			final XMLExporter<NodeActionValue> exporter = extension.createExtensionRuntime().createXMLExporter();
			return exporter.exportToElement(action, builder);
		}
		catch (final ExtensionException e) {
			throw new XMLExportException(e);
		}
	}
}
