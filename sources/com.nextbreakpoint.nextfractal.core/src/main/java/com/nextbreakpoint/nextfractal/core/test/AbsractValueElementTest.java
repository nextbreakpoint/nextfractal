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
package com.nextbreakpoint.nextfractal.core.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElement;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.config.ValueConfigElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.tree.NodePath;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public abstract class AbsractValueElementTest<V extends Serializable, T extends ValueConfigElement<V>> extends AbstractTest {
	private ConfigContext context;
	private T configElement;

	@Before
	public void setup() {
		context = new DefaultConfigContext();
		configElement = createConfigElement(getFirstValue());
		configElement.setContext(context);
	}

	protected abstract V getFirstValue();

	protected abstract V getSecondValue();

	protected abstract T createConfigElement(V defaultValue);

	protected abstract NodeObject createElementNode();

	protected abstract ValueConfigElementXMLImporter<V, T> createXMLImporter();

	protected abstract ValueConfigElementXMLExporter<V, T> createXMLExporter();

	protected T getConfigElement() {
		return configElement;
	}

	protected ConfigContext getContext() {
		return context;
	}

	protected void testSetValue() {
		Assert.assertEquals(getFirstValue(), configElement.getValue());
		configElement.setValue(getSecondValue());
		Assert.assertEquals(getSecondValue(), configElement.getValue());
	}

	protected void testNode() {
		final RootNode rootNode = new RootNode("test", "test");
		final NodeSession session = new DefaultNodeSession("test");
		final ConfigContext context = configElement.getContext();
		rootNode.setSession(session);
		rootNode.setContext(context);
		DefaultNode parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		rootNode.appendChildNode(parentNode);
		parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		rootNode.appendChildNode(parentNode);
		final NodeObject node = createElementNode();
		parentNode.appendChildNode(node);
		Assert.assertEquals(getFirstValue(), node.getNodeValue().getValue());
		configElement.setValue(getSecondValue());
		Assert.assertEquals(getSecondValue(), node.getNodeValue().getValue());
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getFirstValue()));
		Assert.assertEquals(getSecondValue(), configElement.getValue());
		node.accept();
		Assert.assertEquals(getFirstValue(), configElement.getValue());
		final List<NodeAction> actions = session.getActions();
		Assert.assertEquals(2, actions.size());
		NodeAction action = actions.get(0);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getSecondValue(), action.getActionParams()[0]);
		Assert.assertEquals(getFirstValue(), action.getActionParams()[1]);
		action = actions.get(1);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getFirstValue(), action.getActionParams()[0]);
		Assert.assertEquals(getSecondValue(), action.getActionParams()[1]);
	}

	@SuppressWarnings("unchecked")
	protected void testClone() {
		configElement.setValue(getSecondValue());
		final T clonedConfigElement = (T) configElement.clone();
		Assert.assertNotSame(clonedConfigElement, configElement);
		Assert.assertEquals(clonedConfigElement, configElement);
	}

	@SuppressWarnings("unchecked")
	protected void testSerialization() {
		configElement.setValue(getSecondValue());
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(configElement);
			oos.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final ObjectInputStream ois = new ObjectInputStream(is);
			final T deserializedConfigElement = (T) ois.readObject();
			ois.close();
			Assert.assertNotSame(deserializedConfigElement, configElement);
			Assert.assertEquals(deserializedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testXML() {
		final ValueConfigElementXMLExporter<V, T> exporter = createXMLExporter();
		final ValueConfigElementXMLImporter<V, T> importer = createXMLImporter();
		configElement.setValue(getSecondValue());
		try {
			final Document exportedDoc = XML.createDocument();
			final XMLNodeBuilder builder = XML.createDefaultXMLNodeBuilder(exportedDoc);
			final Element element = exporter.exportToElement(configElement, builder);
			exportedDoc.appendChild(element);
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			XML.saveDocument(os, "test.xml", exportedDoc);
			os.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final Document importedDoc = XML.loadDocument(is, "test.xml");
			is.close();
			final T importedConfigElement = importer.importFromElement(importedDoc.getDocumentElement());
			Assert.assertNotSame(importedConfigElement, configElement);
			Assert.assertEquals(importedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testActions() {
	}
}
