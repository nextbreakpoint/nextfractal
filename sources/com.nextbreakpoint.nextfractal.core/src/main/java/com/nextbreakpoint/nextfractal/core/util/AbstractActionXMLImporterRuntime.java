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
package com.nextbreakpoint.nextfractal.core.util;

import java.util.List;

import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.extensionPoints.actionXMLImporter.ActionXMLImporterExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.tree.NodePath;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLImportException;
import com.nextbreakpoint.nextfractal.core.xml.XMLImporter;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractActionXMLImporterRuntime extends ActionXMLImporterExtensionRuntime {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.extensionPoints.actionXMLExporter.ActionXMLExporterExtensionRuntime#createXMLExporter()
	 */
	@Override
	public XMLImporter<NodeActionValue> createXMLImporter() {
		return new ActionImporter();
	}

	private class ActionImporter extends XMLImporter<NodeActionValue> {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLImporter#importFromElement(org.w3c.dom.Element)
		 */
		@Override
		public NodeActionValue importFromElement(final Element element) throws XMLImportException {
			checkClassId(element, "action");
			final NodeActionValue action = new NodeActionValue();
			action.setRefreshRequired(true);
			action.setActionId(getExtensionId(element));
			final List<Element> propertyElements = getProperties(element);
			if (isVersion(element, 1) && (propertyElements.size() == 5)) {
				importProperties1(action, propertyElements);
			}
			else if (isVersion(element, 0) && (propertyElements.size() == 4)) {
				importProperties0(action, propertyElements);
			}
			return action;
		}

		/**
		 * @param action
		 * @param propertyElements
		 * @throws XMLImportException
		 */
		protected void importProperties0(final NodeActionValue action, final List<Element> propertyElements) throws XMLImportException {
			importActionType(action, propertyElements.get(0));
			importTimestamp(action, propertyElements.get(1));
			importTarget(action, propertyElements.get(2));
			importParams(action, propertyElements.get(3));
		}

		/**
		 * @param action
		 * @param propertyElements
		 * @throws XMLImportException
		 */
		protected void importProperties1(final NodeActionValue action, final List<Element> propertyElements) throws XMLImportException {
			importActionType(action, propertyElements.get(0));
			importTimestamp(action, propertyElements.get(1));
			importRefreshRequired(action, propertyElements.get(2));
			importTarget(action, propertyElements.get(3));
			importParams(action, propertyElements.get(4));
		}

		private void importActionType(final NodeActionValue action, final Element element) {
			action.setActionType(XML.getIntegerElementValue(element, "value"));
		}

		private void importTimestamp(final NodeActionValue action, final Element element) {
			action.setTimestamp(XML.getLongElementValue(element, "value"));
		}

		private void importRefreshRequired(final NodeActionValue action, final Element element) {
			action.setRefreshRequired(XML.getBooleanElementValue(element, "value"));
		}

		private void importTarget(final NodeActionValue action, final Element element) {
			action.setActionTarget(NodePath.valueOf(XML.getStringElementValue(element, "value")));
		}
	}

	/**
	 * @param action
	 * @param element
	 */
	protected abstract void importParams(NodeActionValue action, Element element) throws XMLImportException;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime#dispose()
	 */
	@Override
	public void dispose() {
	}
}
