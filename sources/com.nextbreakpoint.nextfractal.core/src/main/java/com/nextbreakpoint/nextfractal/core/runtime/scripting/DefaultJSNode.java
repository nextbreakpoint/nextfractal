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
package com.nextbreakpoint.nextfractal.core.runtime.scripting;

import com.nextbreakpoint.nextfractal.core.CoreRegistry;
import com.nextbreakpoint.nextfractal.core.extensionPoints.creator.CreatorExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.model.NodeObject;

/**
 * @author Andrea Medeghini
 */
public class DefaultJSNode implements JSNode {
	private final NodeObject node;

	/**
	 * @param node
	 */
	public DefaultJSNode(final NodeObject node) {
		this.node = node;
		if (node == null) {
			throw new IllegalArgumentException("node is null");
		}
	}

	private CreatorExtensionRuntime getCreator(final String elementClassId) throws JSException {
		try {
			return CoreRegistry.getInstance().getCreatorExtension(elementClassId).createExtensionRuntime();
		}
		catch (ExtensionNotFoundException e) {
			throw new JSException(e);
		}
		catch (ExtensionException e) {
			throw new JSException(e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getChildNode(int)
	 */
	@Override
	public JSNode getChildNode(final int index) {
		NodeObject childNode = node.getChildNode(index);
		if (childNode != null) {
			JSNode jsNode = (JSNode) childNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(childNode);
				childNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		else {
			return null;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getChildNodeCount()
	 */
	@Override
	public int getChildNodeCount() {
		return node.getChildNodeCount();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getIndex()
	 */
	@Override
	public int getIndex() {
		return node.getParentNode().indexOf(node);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getClassId()
	 */
	@Override
	public String getClassId() {
		return node.getNodeClass();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getId()
	 */
	@Override
	public String getId() {
		return node.getNodeId();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getPath()
	 */
	@Override
	public String getPath() {
		return node.getNodePath().toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getLabel()
	 */
	@Override
	public String getLabel() {
		return node.getNodeLabel();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getValue()
	 */
	@Override
	public JSNodeValue getValue() {
		return new JSNodeValue(node.getNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getPreviousValue()
	 */
	@Override
	public JSNodeValue getPreviousValue() {
		return new JSNodeValue(node.getPreviousNodeValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#hasValue()
	 */
	@Override
	public boolean hasValue() {
		return node.getNodeValue() != null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#isAttribute()
	 */
	@Override
	public boolean isAttribute() {
		return node.isAttribute();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return node.isEditable();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#isMutable()
	 */
	@Override
	public boolean isMutable() {
		return node.isMutable();
	}

	Class<?> getNodeValueType() throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		return node.getNodeEditor().getNodeValueType();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#createValueByArgs(java.lang.Object[])
	 */
	@Override
	public JSNodeValue createValueByArgs(final Object... args) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		return new JSNodeValue(node.getNodeEditor().createNodeValue(getCreator(getClassId()).create(args)));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#appendChildNode(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	public void appendChildNode(final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().appendChildNode(value.getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#insertChildNodeAfter(int, com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	public void insertChildNodeAfter(final int index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeAfter(index, value.getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#insertChildNodeAt(java.lang.Integer, com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	public void insertChildNodeAt(final Integer index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeAt(index, value.getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#insertChildNodeBefore(int, com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	public void insertChildNodeBefore(final int index, final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().insertChildNodeBefore(index, value.getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#moveDownChildNode(int)
	 */
	@Override
	public void moveDownChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().moveDownChildNode(index);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#moveUpChildNode(int)
	 */
	@Override
	public void moveUpChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().moveUpChildNode(index);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#removeAllChildNodes()
	 */
	@Override
	public void removeAllChildNodes() throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().removeAllChildNodes();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#removeChildNode(int)
	 */
	@Override
	public void removeChildNode(final int index) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().removeChildNode(index);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#setValue(com.nextbreakpoint.nextfractal.core.runtime.model.NodeValue)
	 */
	@Override
	public void setValue(final JSNodeValue value) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().setNodeValue(value.getValue());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#setValueByArgs(java.lang.Object[])
	 */
	@Override
	public void setValueByArgs(final Object... args) throws JSException {
		if (node.getNodeEditor() == null) {
			throw new JSException("Editor not defined");
		}
		node.getNodeEditor().setNodeValue(node.getNodeEditor().createNodeValue(getCreator(getClassId()).create(args)));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return node.toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getParentNode()
	 */
	@Override
	public JSNode getParentNode() {
		NodeObject tmpNode = node.getParentNode();
		if (tmpNode != null) {
			JSNode jsNode = (JSNode) tmpNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(tmpNode);
				tmpNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#dump()
	 */
	@Override
	public String dump() {
		return node.dump();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.scripting.JSNode#getNodeByPath(java.lang.String)
	 */
	@Override
	public JSNode getNodeByPath(final String path) {
		NodeObject tmpNode = node.getNodeByPath(path);
		if (tmpNode != null) {
			JSNode jsNode = (JSNode) tmpNode.getObject("scripting.jsNode");
			if (jsNode == null) {
				jsNode = new DefaultJSNode(tmpNode);
				tmpNode.putObject("scripting.jsNode", jsNode);
			}
			return jsNode;
		}
		return null;
	}
}
