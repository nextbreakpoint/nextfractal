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
package com.nextbreakpoint.nextfractal.core.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Medeghini
 */
public abstract class XMLImporter<T> {
	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	public abstract T importFromElement(Element element) throws XMLImportException;

	/**
	 * @param element
	 * @param expectedClassId
	 * @throws XMLImportException
	 */
	protected void checkClassId(final Element element, final String expectedClassId) throws XMLImportException {
		final String classId = this.getClassId(element);
		if (!classId.equals(expectedClassId)) {
			throw new XMLImportException("Invalid value: " + XMLExporter.ATTRIBUTE_CLASS_ID + " = " + expectedClassId);
		}
	}

	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	protected String getClassId(final Element element) throws XMLImportException {
		final String classId = element.getAttribute(XMLExporter.ATTRIBUTE_CLASS_ID);
		if (classId == null) {
			throw new XMLImportException("Missing attribute " + XMLExporter.ATTRIBUTE_CLASS_ID);
		}
		return classId;
	}

	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	protected String getExtensionId(final Element element) throws XMLImportException {
		final String extensionId = element.getAttribute(XMLExporter.ATTRIBUTE_EXTENSION_ID);
		if (extensionId == null) {
			throw new XMLImportException("Missing attribute " + XMLExporter.ATTRIBUTE_EXTENSION_ID);
		}
		return extensionId;
	}

	/**
	 * @param element
	 * @param version
	 * @return
	 */
	protected boolean isVersion(final Element element, final Integer version) {
		if ((element.getAttribute(XMLExporter.ATTRIBUTE_VER) == null) || "".equals(element.getAttribute(XMLExporter.ATTRIBUTE_VER))) {
			return version == 0;
		}
		return version.toString().equals(element.getAttribute(XMLExporter.ATTRIBUTE_VER));
	}

	/**
	 * @param parentElement
	 * @param classId
	 * @return
	 * @throws XMLImportException
	 */
	protected List<Element> getElements(final Element parentElement, final String classId) throws XMLImportException {
		try {
			final NodeList nodeList = XML.findNodesByName(parentElement.getChildNodes(), XMLExporter.ELEMENT_TAG_NAME);
			final List<Element> elementList = new ArrayList<Element>(nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Element element = (Element) nodeList.item(i);
				if (classId.equals(element.getAttribute(XMLExporter.ATTRIBUTE_CLASS_ID))) {
					elementList.add(element);
				}
			}
			return elementList;
		}
		catch (final Exception e) {
			throw new XMLImportException(e);
		}
	}

	/**
	 * @param parentElement
	 * @param classIds
	 * @return
	 * @throws XMLImportException
	 */
	protected List<Element> getElements(final Element parentElement, final String[] classIds) throws XMLImportException {
		try {
			final NodeList nodeList = XML.findNodesByName(parentElement.getChildNodes(), XMLExporter.ELEMENT_TAG_NAME);
			final List<Element> elementList = new ArrayList<Element>(nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Element element = (Element) nodeList.item(i);
				for (final String classId : classIds) {
					if (classId.equals(element.getAttribute(XMLExporter.ATTRIBUTE_CLASS_ID))) {
						elementList.add(element);
						break;
					}
				}
			}
			return elementList;
		}
		catch (final Exception e) {
			throw new XMLImportException(e);
		}
	}

	/**
	 * @param element
	 * @return
	 * @throws XMLImportException
	 */
	protected List<Element> getProperties(final Element parentElement) throws XMLImportException {
		try {
			final NodeList nodeList = XML.findNodesByName(parentElement.getChildNodes(), XMLExporter.PROPERTY_TAG_NAME);
			final List<Element> elementList = new ArrayList<Element>(nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++) {
				final Element element = (Element) nodeList.item(i);
				elementList.add(element);
			}
			return elementList;
		}
		catch (final Exception e) {
			throw new XMLImportException(e);
		}
	}
}
