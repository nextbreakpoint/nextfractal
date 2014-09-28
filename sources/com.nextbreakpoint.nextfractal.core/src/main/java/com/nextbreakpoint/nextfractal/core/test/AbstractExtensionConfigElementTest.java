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
package com.nextbreakpoint.nextfractal.core.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementXMLExporter;
import com.nextbreakpoint.nextfractal.core.common.ExtensionReferenceElementXMLImporter;
import com.nextbreakpoint.nextfractal.core.config.ConfigContext;
import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNode;
import com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.tree.Node;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeEditor;
import com.nextbreakpoint.nextfractal.core.tree.NodePath;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.RootNode;
import com.nextbreakpoint.nextfractal.core.tree.Tree;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.core.xml.XMLNodeBuilder;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractExtensionConfigElementTest extends AbstractTest {
	private ConfigContext context;
	private ExtensionReferenceElement configElement;

	@Before
	public void setup() {
		context = new DefaultConfigContext();
		configElement = createConfigElement(getFirstReference());
		configElement.setContext(context);
	}

	protected abstract ExtensionReference getFirstReference();

	protected abstract ExtensionReference getSecondReference();

	protected abstract ExtensionReferenceElement createConfigElement(ExtensionReference defaultValue);

	protected abstract Node createElementNode();

	protected abstract ExtensionReferenceElementXMLImporter createXMLImporter();

	protected abstract ExtensionReferenceElementXMLExporter createXMLExporter();

	protected ExtensionReferenceElement getConfigElement() {
		return configElement;
	}

	protected ConfigContext getContext() {
		return context;
	}

	protected void testSetReference() {
		Assert.assertEquals(getFirstReference(), configElement.getReference());
		configElement.setReference(getSecondReference());
		Assert.assertEquals(getSecondReference(), configElement.getReference());
	}

	protected void testNode() {
		final Tree tree = new Tree(new RootNode("test", "test"));
		final NodeSession session = new DefaultNodeSession("test");
		final ConfigContext context = configElement.getContext();
		tree.getRootNode().setSession(session);
		tree.getRootNode().setContext(context);
		DefaultNode parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		tree.getRootNode().appendChildNode(parentNode);
		parentNode = new DefaultNode("element") {
			@Override
			protected NodeEditor createNodeEditor() {
				return null;
			}
		};
		tree.getRootNode().appendChildNode(parentNode);
		final Node node = createElementNode();
		parentNode.appendChildNode(node);
		Assert.assertEquals(getFirstReference(), node.getNodeValue().getValue());
		configElement.setReference(getSecondReference());
		Assert.assertEquals(getSecondReference(), node.getNodeValue().getValue());
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getFirstReference()));
		Assert.assertEquals(getSecondReference(), configElement.getReference());
		node.accept();
		Assert.assertEquals(getFirstReference(), configElement.getReference());
		final List<NodeAction> actions = session.getActions();
		Assert.assertEquals(2, actions.size());
		NodeAction action = actions.get(0);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getSecondReference(), action.getActionParams()[0]);
		Assert.assertEquals(getFirstReference(), action.getActionParams()[1]);
		action = actions.get(1);
		Assert.assertEquals(new NodePath(new Integer[] { 1, 0 }), action.getActionTarget());
		Assert.assertEquals(getFirstReference(), action.getActionParams()[0]);
		Assert.assertEquals(getSecondReference(), action.getActionParams()[1]);
	}

	protected void testClone() {
		configElement.setReference(getSecondReference());
		final ExtensionReferenceElement clonedConfigElement = configElement.clone();
		Assert.assertNotSame(clonedConfigElement, configElement);
		Assert.assertEquals(clonedConfigElement, configElement);
	}

	protected void testSerialization() {
		configElement.setReference(getSecondReference());
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			final ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(configElement);
			oos.close();
			final ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
			final ObjectInputStream ois = new ObjectInputStream(is);
			final ExtensionReferenceElement deserializedConfigElement = (ExtensionReferenceElement) ois.readObject();
			ois.close();
			Assert.assertNotSame(deserializedConfigElement, configElement);
			Assert.assertEquals(deserializedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected void testXML() {
		final ExtensionReferenceElementXMLExporter exporter = createXMLExporter();
		final ExtensionReferenceElementXMLImporter importer = createXMLImporter();
		configElement.setReference(getSecondReference());
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
			final ExtensionReferenceElement importedConfigElement = importer.importFromElement(importedDoc.getDocumentElement());
			Assert.assertNotSame(importedConfigElement, configElement);
			Assert.assertEquals(importedConfigElement, configElement);
		}
		catch (final Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
