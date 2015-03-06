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
package com.nextbreakpoint.nextfractal.core.javaFX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andrea Medeghini
 */
public abstract class NodeObject {
	private static final Logger logger = Logger.getLogger(NodeObject.class.getName());
	private final Map<String, Object> map = new HashMap<String, Object>();
	private List<NodeObject> childList;
	private NodeObject parentNode;
	private boolean changed;
	private final String nodeId;
	private String nodeLabel;
	private String nodeClass;
	private String extensionId;

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * @return
	 */
	public boolean isHighFrequency() {
		return false;
	}

	/**
	 * 
	 */
	public void dispose() {
		if (childList != null) {
			for (NodeObject child : childList) {
				child.dispose();
			}
			childList.clear();
			childList = null;
		}
		parentNode = null;
	}

	/**
	 * Constructs a new node.
	 * 
	 * @param nodeId the nodeId.
	 */
	public NodeObject(final String nodeId) {
		if (nodeId == null) {
			throw new IllegalArgumentException("nodeId is null");
		}
		this.nodeId = nodeId;
	}

	private void setParentNode(final NodeObject parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * Returns the parent.
	 * 
	 * @return the parent.
	 */
	public NodeObject getParentNode() {
		return parentNode;
	}

	/**
	 * @param node
	 * @return
	 */
	public boolean isChildNode(final NodeObject node) {
		return childList.contains(node);
	}

	/**
	 * Returns a child.
	 * 
	 * @param index the child index.
	 * @return the child.
	 */
	public NodeObject getChildNode(final int index) {
		if ((index < 0) || (index >= getChildList().size())) {
			return null;
		}
		return getChildList().get(index);
	}

	/**
	 * Returns a child.
	 * 
	 * @param nodeClass the child class.
	 * @return the child.
	 */
	public NodeObject getChildNodeByClass(final String nodeClass) {
		for (NodeObject node : getChildList()) {
			if (node.getNodeClass().equals(nodeClass)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Returns a child.
	 * 
	 * @param nodeId the child id.
	 * @return the child.
	 */
	public NodeObject getChildNodeById(final String nodeId) {
		for (NodeObject node : getChildList()) {
			if (node.getNodeId().equals(nodeId)) {
				return node;
			}
		}
		return null;
	}
	
	public abstract NodeObject createNode(Object element);

	/**
	 * Returns the number of childs.
	 * 
	 * @return the number of childs.
	 */
	public int getChildNodeCount() {
		return getChildList().size();
	}

	/**
	 * 
	 */
	protected void updateNode() {
		updateChildNodes();
	}

	/**
	 * 
	 */
	protected void updateChildNodes() {
	}

	/**
	 * Returns the nodeId.
	 * 
	 * @return the nodeId.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * Returns the nodeClass.
	 * 
	 * @return the nodeClass.
	 */
	public String getNodeClass() {
		return nodeClass;
	}

	/**
	 * Sets the nodeClass.
	 * 
	 * @param the nodeClass.
	 */
	protected void setNodeClass(final String nodeClass) {
		this.nodeClass = nodeClass;
	}

	/**
	 * Returns the nodeLabel.
	 * 
	 * @return the nodeLabel
	 */
	public String getNodeLabel() {
		return nodeLabel;
	}

	/**
	 * Sets the nodeClass.
	 * 
	 * @param the nodeClass.
	 */
	protected void setNodeLabel(final String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	/**
	 * Returns true if node has pending commands.
	 * 
	 * @return true if node has pending commands.
	 */
	public boolean hasPendingCommands() {
		return changed;
	}

	/**
	 * Returns true if the node is mutable.
	 * 
	 * @return true if the node is mutable.
	 */
	public boolean isMutable() {
		return false;
	}

	/**
	 * Returns true if the node is editable.
	 * 
	 * @return true if the node is editable.
	 */
	public boolean isEditable() {
		return false;
	}

	/**
	 * Returns true if the node is an attribute.
	 * 
	 * @return true if the node is an attribute.
	 */
	public boolean isAttribute() {
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		if (hasPendingCommands()) {
			builder.append("*");
		}
		builder.append(nodeId);
		builder.append(" (");
		builder.append(nodeClass != null ? nodeClass : "<no class>");
		builder.append(")");
		if (extensionId != null) {
			builder.append(" [");
			builder.append(extensionId);
			builder.append("]");
		}
		return builder.toString();
	}

	/**
	 * Appends a child to parent.
	 * 
	 * @param node the child to append.
	 */
	protected void appendChildNodeToParent(final NodeObject node) {
		if (parentNode != null) {
			parentNode.appendChildNode(node);
		}
	}

	/**
	 * Appends a child.
	 * 
	 * @param node the child to append.
	 */
	public void appendChildNode(final NodeObject node) {
		node.setParentNode(this);
		if (getChildList().contains(node)) {
			if (NodeObject.logger.isLoggable(Level.FINE)) {
				NodeObject.logger.fine("NodeObject " + node.getLabel() + " is already in the list");
			}
			return;
		}
		getChildList().add(node);
		node.nodeAdded();
	}

	/**
	 * Removes a child.
	 * 
	 * @param index the child to remove.
	 */
	public void removeChildNode(final int nodeIndex) {
		final NodeObject node = getChildList().get(nodeIndex);
		getChildList().remove(nodeIndex);
		node.nodeRemoved();
		node.setParentNode(null);
	}

	/**
	 * Removes all the children.
	 */
	public void removeAllChildNodes() {
		for (int i = getChildNodeCount() - 1; i >= 0; i--) {
			removeChildNode(i);
		}
	}

	/**
	 * @param index
	 * @param node
	 */
	public void insertNodeBefore(final int index, final NodeObject node) {
		node.setParentNode(this);
		if (getChildList().contains(node)) {
			if (NodeObject.logger.isLoggable(Level.FINE)) {
				NodeObject.logger.fine("NodeObject " + node.getLabel() + " is already in the list");
			}
			return;
		}
		if ((index < 0) || (index > getChildList().size())) {
			throw new IllegalArgumentException("index out of bounds");
		}
		getChildList().add(index, node);
		node.nodeAdded();
	}

	/**
	 * @param index
	 * @param node
	 */
	public void insertNodeAfter(final int index, final NodeObject node) {
		node.setParentNode(this);
		if (getChildList().contains(node)) {
			if (NodeObject.logger.isLoggable(Level.FINE)) {
				NodeObject.logger.fine("NodeObject " + node.getLabel() + " is already in the list");
			}
			return;
		}
		if ((index < 0) || (index > getChildList().size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		if (index < getChildList().size() - 1) {
			getChildList().add(index + 1, node);
		}
		else {
			getChildList().add(node);
		}
		node.nodeAdded();
	}

	/**
	 * @param index
	 * @param node
	 */
	public void insertChildNodeAt(final int index, final NodeObject node) {
		if (index < getChildList().size()) {
			insertNodeBefore(index, node);
		}
		else if (index > 0) {
			insertNodeAfter(index - 1, node);
		}
		else {
			appendChildNode(node);
		}
	}

	/**
	 * @param index
	 */
	public void moveUpChildNode(final int index) {
		final NodeObject node = getChildList().get(index);
		if (index > 0) {
			removeChildNode(index);
			insertNodeBefore(index - 1, node);
		}
	}

	/**
	 * @param index
	 */
	public void moveDownChildNode(final int index) {
		final NodeObject node = getChildList().get(index);
		if (index < getChildList().size() - 1) {
			removeChildNode(index);
			insertNodeAfter(index, node);
		}
	}

	/**
	 * @param index
	 */
	public void moveChildNode(final int index, final int newIndex) {
		final NodeObject node = getChildList().get(index);
		if (index < getChildList().size() - 1) {
			removeChildNode(index);
			insertNodeAfter(newIndex, node);
		}
	}

	/**
	 * @param index
	 */
	public void setChildNode(final int index, final NodeObject node) {
		if ((index < 0) || (index > getChildList().size() - 1)) {
			throw new IllegalArgumentException("index out of bounds");
		}
		removeChildNode(index);
		insertNodeAfter(index, node);
	}

	/**
	 * Returns the node value as string.
	 * 
	 * @return the string.
	 */
	public String getValueAsString() {
		return toString();
	}

	/**
	 * @return the label.
	 */
	public final String getLabel() {
		final StringBuilder builder = new StringBuilder();
		if (hasPendingCommands()) {
			builder.append("*");
		}
		addLabel(builder);
		return builder.toString();
	}

	/**
	 * @param builder
	 */
	protected void addLabel(final StringBuilder builder) {
		if (nodeLabel != null) {
			builder.append(nodeLabel);
		}
	}

	/**
	 * @return the description.
	 */
	public final String getDescription() {
		final StringBuilder builder = new StringBuilder();
		addDescription(builder);
		if (parentNode != null) {
			builder.append(" [");
			builder.append(parentNode.getChildList().indexOf(this));
			builder.append("]");
		}
		return builder.toString();
	}

	/**
	 * @param builder
	 */
	protected void addDescription(final StringBuilder builder) {
		addLabel(builder);
	}

	/**
	 * @param node
	 * @return the index.
	 */
	public int indexOf(final NodeObject node) {
		return getChildList().indexOf(node);
	}

	private List<NodeObject> getChildList() {
		if (childList == null) {
			childList = new ArrayList<NodeObject>();
		}
		return childList;
	}

	/**
	 * 
	 */
	protected void nodeAdded() {
	}

	/**
	 * 
	 */
	protected void nodeRemoved() {
	}

	/**
	 * @return
	 */
	public String dump() {
		final StringBuilder builder = new StringBuilder();
		dumpNode(builder, this, 0);
		return builder.toString();
	}

	private void dumpNode(final StringBuilder builder, final NodeObject node, final int level) {
		for (int i = 0; i < level; i++) {
			builder.append(" ");
		}
		builder.append(node);
		if (node.getChildNodeCount() > 0) {
			if (node.getParentNode() != null) {
				builder.append(" path = [");
				builder.append("]");
			}
			builder.append(" {\n");
			for (int i = 0; i < node.getChildNodeCount(); i++) {
				dumpNode(builder, node.getChildNode(i), level + 1);
			}
			for (int i = 0; i < level; i++) {
				builder.append(" ");
			}
			builder.append("}\n");
		}
		else {
			if (node.getParentNode() != null) {
				builder.append(" path = [");
				builder.append("]");
			}
			builder.append("\n");
		}
	}

	/**
	 * @param key
	 * @param value
	 */
	public void putObject(final String key, final Object value) {
		map.put(key, value);
	}

	/**
	 * @param key
	 * @return
	 */
	public Object getObject(final String key) {
		return map.get(key);
	}

	/**
	 * @param key
	 */
	public void removeObject(final String key) {
		map.remove(key);
	}

	/**
	 * @return
	 */
	public String getExtensionId() {
		return extensionId;
	}

	/**
	 * @param extensionId
	 */
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
}