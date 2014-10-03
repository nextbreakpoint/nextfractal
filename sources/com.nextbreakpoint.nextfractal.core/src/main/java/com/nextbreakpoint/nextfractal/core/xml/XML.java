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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.nextbreakpoint.nextfractal.core.math.Complex;

/**
 * @author Andrea Medeghini
 */
public class XML {
	/**
	 * @param list
	 * @param name
	 * @return
	 */
	public static final NodeList findNodesByName(final NodeList list, final String name) {
		final ArrayNodeList nodes = new ArrayNodeList();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(name)) {
				nodes.add(list.item(i));
			}
		}
		return nodes;
	}

	/**
	 * @param list
	 * @param type
	 * @return
	 */
	public static final NodeList findNodesByType(final NodeList list, final short type) {
		final ArrayNodeList nodes = new ArrayNodeList();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == type) {
				nodes.add(list.item(i));
			}
		}
		return nodes;
	}

	/**
	 * @param list
	 * @param value
	 * @return
	 */
	public static final NodeList findNodesByValue(final NodeList list, final String value) {
		final ArrayNodeList nodes = new ArrayNodeList();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeValue().equals(value)) {
				nodes.add(list.item(i));
			}
		}
		return nodes;
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final String getStringElementValue(final Element element, final String name) {
		Node firstChild = ((Element) element.getElementsByTagName(name).item(0)).getFirstChild();
		if (firstChild != null) {
			return firstChild.getNodeValue();
		}
		return "";
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final int getIntegerElementValue(final Element element, final String name) {
		return Integer.parseInt(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final short getShortElementValue(final Element element, final String name) {
		return Short.parseShort(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final long getLongElementValue(final Element element, final String name) {
		return Long.parseLong(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final float getFloatElementValue(final Element element, final String name) {
		return Float.parseFloat(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final double getDoubleElementValue(final Element element, final String name) {
		return Double.parseDouble(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final boolean getBooleanElementValue(final Element element, final String name) {
		return Boolean.valueOf(XML.getStringElementValue(element, name)).booleanValue();
	}

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	public static final Complex getComplexElementValue(final Element element, final String name) {
		return Complex.valueOf(XML.getStringElementValue(element, name));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final String getStringElementValue(final Element element, final String name, final int index) {
		Node firstChild = ((Element) element.getElementsByTagName(name).item(index)).getFirstChild();
		if (firstChild != null) {
			return firstChild.getNodeValue();
		}
		return "";
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final int getIntegerElementValue(final Element element, final String name, final int index) {
		return Integer.parseInt(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final short getShortElementValue(final Element element, final String name, final int index) {
		return Short.parseShort(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final long getLongElementValue(final Element element, final String name, final int index) {
		return Long.parseLong(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final float getFloatElementValue(final Element element, final String name, final int index) {
		return Float.parseFloat(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final double getDoubleElementValue(final Element element, final String name, final int index) {
		return Double.parseDouble(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final boolean getBooleanElementValue(final Element element, final String name, final int index) {
		return Boolean.valueOf(XML.getStringElementValue(element, name, index)).booleanValue();
	}

	/**
	 * @param element
	 * @param name
	 * @param index
	 * @return
	 */
	public static final Complex getComplexElementValue(final Element element, final String name, final int index) {
		return Complex.valueOf(XML.getStringElementValue(element, name, index));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createStringElement(final XMLNodeBuilder builder, final String name, final String value) {
		final Element element = builder.createElement(name);
		final Text text = builder.createTextNode(String.valueOf(value));
		element.appendChild(text);
		return element;
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createIntegerElement(final XMLNodeBuilder builder, final String name, final int value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createShortElement(final XMLNodeBuilder builder, final String name, final short value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createLongElement(final XMLNodeBuilder builder, final String name, final long value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createFloatElement(final XMLNodeBuilder builder, final String name, final float value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createDoubleElement(final XMLNodeBuilder builder, final String name, final double value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param name
	 * @param value
	 * @return
	 */
	public static final Element createBooleanElement(final XMLNodeBuilder builder, final String name, final boolean value) {
		return XML.createStringElement(builder, name, String.valueOf(value));
	}

	/**
	 * @param builder
	 * @param string
	 * @param value
	 * @return
	 */
	public static final Node createComplexElement(final XMLNodeBuilder builder, final String name, final Complex value) {
		return XML.createStringElement(builder, name, value.toString());
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendStringElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final String value) {
		parentElement.appendChild(XML.createStringElement(builder, name, value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendIntegerElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final int value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendShortElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final short value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendLongElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final long value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendFloatElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final float value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendDoubleElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final double value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendBooleanElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final boolean value) {
		XML.appendStringElement(parentElement, builder, name, String.valueOf(value));
	}

	/**
	 * @param parentElement
	 * @param builder
	 * @param name
	 * @param value
	 */
	public static final void appendComplexElement(final Element parentElement, final XMLNodeBuilder builder, final String name, final Complex value) {
		XML.appendStringElement(parentElement, builder, name, value.toString());
	}

	/**
	 * @param os
	 * @param name
	 * @param doc
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static final void saveDocument(final OutputStream os, final String name, final Document doc) throws TransformerFactoryConfigurationError, IOException, TransformerException {
		final Transformer transformer = TransformerFactory.newInstance().newTransformer();
		final ZipOutputStream zos = new ZipOutputStream(os);
		final ZipEntry entry = new ZipEntry(name);
		entry.setMethod(ZipEntry.DEFLATED);
		zos.putNextEntry(entry);
		transformer.transform(new DOMSource(doc), new StreamResult(zos));
		zos.closeEntry();
		zos.close();
	}

	/**
	 * @param os
	 * @param names
	 * @param docs
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static final void saveDocuments(final OutputStream os, final String[] names, final Document[] docs) throws TransformerFactoryConfigurationError, IOException, TransformerException {
		final Transformer transformer = TransformerFactory.newInstance().newTransformer();
		final ZipOutputStream zos = new ZipOutputStream(os);
		for (int i = 0; i < names.length; i++) {
			final ZipEntry entry = new ZipEntry(names[i]);
			entry.setMethod(ZipEntry.DEFLATED);
			zos.putNextEntry(entry);
			transformer.transform(new DOMSource(docs[i]), new StreamResult(zos));
			zos.closeEntry();
		}
		zos.close();
	}

	/**
	 * @param is
	 * @param name
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final Document loadDocument(final InputStream is, final String name) throws SAXException, IOException, ParserConfigurationException {
		final ZipInputStream zis = new ZipInputStream(is);
		final ZipEntry entry = zis.getNextEntry();
		if (!name.equals(entry.getName())) {
			throw new IOException("The name doesn't match. Expected " + name + ", found " + entry.getName());
		}
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = zis.read(buffer)) > 0) {
			baos.write(buffer, 0, length);
		}
		final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		zis.read(buffer);
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bais);
	}

	/**
	 * @param is
	 * @param names
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static final Document[] loadDocuments(final InputStream is, final String[] names) throws SAXException, IOException, ParserConfigurationException {
		final ZipInputStream zis = new ZipInputStream(is);
		final Document[] docs = new Document[names.length];
		for (int i = 0; i < names.length; i++) {
			final ZipEntry entry = zis.getNextEntry();
			if (!names[i].equals(entry.getName())) {
				throw new IOException("The name doesn't match. Expected " + names[i] + ", found " + entry.getName());
			}
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = zis.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			zis.read(buffer);
			docs[i] = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bais);
			zis.closeEntry();
		}
		return docs;
	}

	/**
	 * @param doc
	 * @return
	 */
	public static final XMLNodeBuilder createDefaultXMLNodeBuilder(final Document doc) {
		return new DefaultXMLNodeBuilder(doc);
	}

	/**
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static final Document createDocument() throws ParserConfigurationException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.newDocument();
	}

	private static class ArrayNodeList implements NodeList {
		private final ArrayList<Node> list = new ArrayList<Node>();

		/**
		 * @param node
		 */
		private void add(final Node node) {
			list.add(node);
		}

		/**
		 * @see org.w3c.dom.NodeList#getLength()
		 */
		@Override
		public int getLength() {
			return list.size();
		}

		/**
		 * @see org.w3c.dom.NodeList#item(int)
		 */
		@Override
		public Node item(final int i) {
			return list.get(i);
		}
	}

	private static class DefaultXMLNodeBuilder implements XMLNodeBuilder {
		private final Document doc;

		public DefaultXMLNodeBuilder(final Document doc) {
			this.doc = doc;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder#createElement(java.lang.String)
		 */
		@Override
		public Element createElement(final String tagName) {
			return doc.createElement(tagName);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder#createTextNode(java.lang.String)
		 */
		@Override
		public Text createTextNode(final String nodeValue) {
			return doc.createTextNode(nodeValue);
		}
	}

	/**
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static List<Element> getElementsByName(final Element element, final String tagName) {
		final List<Element> elements = new ArrayList<Element>();
		final NodeList nodeList = XML.findNodesByName(element.getChildNodes(), tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			elements.add((Element) nodeList.item(i));
		}
		return elements;
	}
}
