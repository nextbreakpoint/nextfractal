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
package com.nextbreakpoint.nextfractal.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.config.ConfigContext;

/**
 * @author Andrea Medeghini
 */
public abstract class NodeObject {
	private static final Logger logger = Logger.getLogger(NodeObject.class.getName());
	private final Map<String, Object> map = new HashMap<String, Object>();
	private List<NodeCommand> commandList = new LinkedList<NodeCommand>();
	private List<NodeObject> childList;
	private NodeEditor editor;
	private NodeObject parentNode;
	private boolean changed;
	private final String nodeId;
	private String nodeLabel;
	private String nodeClass;
	private String extensionId;
	private NodeValue<?> value;
	private NodeValue<?> previousValue;
	private ConfigContext context;
	private NodeSession session;

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
		if (commandList != null) {
			commandList.clear();
			commandList = null;
		}
		if (childList != null) {
			for (NodeObject child : childList) {
				child.dispose();
			}
			childList.clear();
			childList = null;
		}
		value = null;
		previousValue = null;
		parentNode = null;
		session = null;
		context = null;
		editor = null;
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

	/**
	 * @param e the node event.
	 */
	void fireNodeChanged(final NodeEvent e) {
		if (parentNode != null) {
			parentNode.fireNodeChanged(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	void fireNodeAdded(final NodeEvent e) {
		if (parentNode != null) {
			parentNode.fireNodeAdded(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	void fireNodeRemoved(final NodeEvent e) {
		if (parentNode != null) {
			parentNode.fireNodeRemoved(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	void fireNodeAccepted(final NodeEvent e) {
		if (parentNode != null) {
			parentNode.fireNodeAccepted(e);
		}
	}

	/**
	 * @param e the node event.
	 */
	void fireNodeCancelled(final NodeEvent e) {
		if (parentNode != null) {
			parentNode.fireNodeCancelled(e);
		}
	}

	/**
	 * @param command the node command.
	 */
	void fireNodeCommandCreated(final NodeCommand command) {
		commandList.add(command);
		if (parentNode != null) {
			parentNode.fireNodeCommandCreated(command);
		}
	}

	// /**
	// * @param command the node command.
	// */
	// void fireNodeCommandDisposed(final NodeCommand command) {
	// commandList.remove(command);
	// if (parentNode != null) {
	// parentNode.fireNodeCommandDisposed(command);
	// }
	// }
	/**
	 * 
	 */
	protected void fireNodeChanged() {
		this.fireNodeChanged(new NodeEvent(this, getNodePath()));
	}

	/**
	 * 
	 */
	protected void fireNodeAdded(final NodePath path) {
		// if (parentNode != null) {
		// parentNode.fireNodeChanged();
		// }
		this.fireNodeAdded(new NodeEvent(this, path));
	}

	/**
	 * 
	 */
	protected void fireNodeRemoved(final NodePath path) {
		// if (parentNode != null) {
		// parentNode.fireNodeChanged();
		// }
		this.fireNodeRemoved(new NodeEvent(this, path));
	}

	/**
	 * 
	 */
	protected void fireNodeAccepted(final NodePath path) {
		this.fireNodeAccepted(new NodeEvent(this, path));
	}

	/**
	 * 
	 */
	protected void fireNodeCancelled(final NodePath path) {
		this.fireNodeCancelled(new NodeEvent(this, path));
	}

	/**
	 * Returns the node path.
	 * 
	 * @return the path.
	 */
	public NodePath getNodePath() {
		NodePath path;
		if (parentNode != null) {
			path = parentNode.getNodePath();
			path.addPathElement(parentNode.getChildList().indexOf(this));
		}
		else {
			path = new NodePath();
		}
		return path;
	}

	private void setParentNode(final NodeObject parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @param session
	 */
	public void setSession(final NodeSession session) {
		this.session = session;
		// if (NodeObject.logger.isDebugEnabled()) {
		// if (session != null) {
		// NodeObject.logger.debug("Set session to \"" + session.getSessionName() + "\" for node \"" + getNodeId() + "\"");
		// }
		// else {
		// NodeObject.logger.debug("Set session to null for node \"" + getNodeId() + "\"");
		// }
		// }
		for (final NodeObject node : getChildList()) {
			node.setSession(session);
		}
	}

	/**
	 * @return
	 */
	public NodeSession getSession() {
		if (session == null) {
			throw new IllegalStateException("Session is not defined");
		}
		return session;
	}

	public boolean existsSession() {
		return session != null;
	}

	/**
	 * @param context
	 */
	public void setContext(final ConfigContext context) {
		this.context = context;
		for (final NodeObject node : getChildList()) {
			node.setContext(context);
		}
	}

	/**
	 * @return
	 */
	public ConfigContext getContext() {
		if (context == null) {
			throw new IllegalStateException("Context is not defined");
		}
		return context;
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

	/**
	 * Returns the number of childs.
	 * 
	 * @return the number of childs.
	 */
	public int getChildNodeCount() {
		return getChildList().size();
	}

	/**
	 * Returns the node value.
	 * 
	 * @return the node value.
	 */
	public NodeValue<?> getNodeValue() {
		return value;
	}

	/**
	 * Returns the previous node value.
	 * 
	 * @return the previous node value.
	 */
	public NodeValue<?> getPreviousNodeValue() {
		return previousValue;
	}

	/**
	 * Sets the node value.
	 * 
	 * @param value the node value to set.
	 */
	protected final void setNodeValue(final NodeValue<?> value) {
		if (NodeObject.isValueChanged(value, this.value)) {
			previousValue = this.value;
			this.value = value;
			updateNode();
			this.fireNodeChanged();
		}
	}

	/**
	 * @param value
	 * @param prevValue
	 * @return
	 */
	protected static boolean isValueChanged(final Object value, final Object prevValue) {
		return ((value == null) && (prevValue != null)) || ((value != null) && !value.equals(prevValue));
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
	 * Returns the node editor.
	 * 
	 * @return the node editor.
	 */
	public NodeEditor getNodeEditor() {
		return editor;
	}

	/**
	 * Sets the node editor.
	 * 
	 * @param editor the node editor to set.
	 */
	protected void setNodeEditor(final NodeEditor editor) {
		this.editor = editor;
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
	 * Accepts node value.
	 * 
	 * @param session
	 */
	public final void accept() {
		for (int i = 0; i < commandList.size(); i++) {
			final NodeCommand command = commandList.get(i);
			if (!command.isConsumed()) {
				command.accept(getSession(), getContext().getTimestamp());
				command.consume();
			}
		}
		doAccept();
	}

	/**
	 * Cancels node value.
	 */
	public final void cancel() {
		for (int i = commandList.size() - 1; i >= 0; i--) {
			final NodeCommand command = commandList.get(i);
			if (!command.isConsumed()) {
				command.cancel();
				command.consume();
			}
		}
		doCancel();
	}

	/**
	 * 
	 */
	protected final void doAccept() {
		commandList.clear();
		for (int i = 0; i < getChildNodeCount(); i++) {
			getChildNode(i).doAccept();
		}
		if (changed) {
			changed = false;
			this.fireNodeChanged();
		}
		fireNodeAccepted(getNodePath());
	}

	/**
	 * 
	 */
	protected final void doCancel() {
		commandList.clear();
		for (int i = 0; i < getChildNodeCount(); i++) {
			getChildNode(i).doCancel();
		}
		if (changed) {
			changed = false;
			this.fireNodeChanged();
		}
		fireNodeCancelled(getNodePath());
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
		if (isAttribute() && (getNodeEditor() != null)) {
			builder.append(" value = [");
			builder.append(getNodeEditor().getNodeValueAsString());
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
		node.setContext(context);
		node.setSession(session);
		if (getChildList().contains(node)) {
			if (NodeObject.logger.isLoggable(Level.FINE)) {
				NodeObject.logger.fine("NodeObject " + node.getLabel() + " is already in the list");
			}
			return;
		}
		getChildList().add(node);
		final NodePath path = node.getNodePath();
		node.fireNodeAdded(path);
		node.nodeAdded();
	}

	/**
	 * Removes a child.
	 * 
	 * @param index the child to remove.
	 */
	public void removeChildNode(final int nodeIndex) {
		final NodeObject node = getChildList().get(nodeIndex);
		final NodePath path = node.getNodePath();
		node.fireNodeRemoved(path);
		getChildList().remove(nodeIndex);
		node.nodeRemoved();
		node.setContext(null);
		node.setSession(null);
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
		node.setContext(context);
		node.setSession(session);
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
		final NodePath path = node.getNodePath();
		node.fireNodeAdded(path);
		node.nodeAdded();
	}

	/**
	 * @param index
	 * @param node
	 */
	public void insertNodeAfter(final int index, final NodeObject node) {
		node.setParentNode(this);
		node.setContext(context);
		node.setSession(session);
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
		final NodePath path = node.getNodePath();
		node.fireNodeAdded(path);
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
	 * @param command
	 */
	void appendCommand(final NodeCommand command) {
		changed = true;
		fireNodeCommandCreated(command);
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
				builder.append(node.getNodePath().toString());
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
				builder.append(node.getNodePath().toString());
				builder.append("]");
			}
			builder.append("\n");
		}
	}

	/**
	 * @param path
	 * @return
	 */
	public NodeObject getNodeByPath(final String path) {
		NodePath nodePath = NodePath.valueOf(path);
		final Integer[] pe = nodePath.getPathElements();
		NodeObject node = this;
		for (final Integer element : pe) {
			node = node.getChildNode(element);
		}
		return node;
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
	public RootNode getRootNode() {
		if (parentNode != null) {
			return parentNode.getRootNode();
		}
		return null;
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
}
