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

import com.nextbreakpoint.nextfractal.core.config.ConfigContext;

/**
 * @author Andrea Medeghini
 */
public abstract class NodeEditor {
	protected NodeObject node;

	/**
	 * Constructs a new editor.
	 * 
	 * @param node the node.
	 */
	public NodeEditor(final NodeObject node) {
		this.node = node;
	}

	/**
	 * @return
	 */
	public ConfigContext getContext() {
		return node.getContext();
	}

	/**
	 * @return the nodeId.
	 */
	public String getNodeId() {
		return node.getNodeId();
	}

	/**
	 * @return
	 */
	public String getNodeLabel() {
		return node.getNodeLabel();
	}

	/**
	 * @return
	 */
	public String getNodeClass() {
		return node.getNodeClass();
	}

	/**
	 * @return true if the node is editable.
	 */
	public boolean isNodeEditable() {
		return node.isEditable();
	}

	/**
	 * @return true if the node is mutable.
	 */
	public boolean isNodeMutable() {
		return node.isMutable();
	}

	/**
	 * @return true if the node is an attribute.
	 */
	public boolean isNodeAttribute() {
		return node.isAttribute();
	}

	/**
	 * @return true if refresh is required.
	 */
	public boolean isRefreshRequired() {
		return true;
	}

	/**
	 * Returns the node type.
	 * 
	 * @return the node type.
	 */
	public abstract Class<?> getNodeValueType();

	/**
	 * @param value
	 * @return
	 */
	public abstract NodeValue<?> createNodeValue(Object value);

	/**
	 * Returns the previous node value.
	 * 
	 * @return the previous node value.
	 */
	public final NodeValue<?> getPreviousNodeValue() {
		return node.getPreviousNodeValue();
	}

	/**
	 * Returns the node value.
	 * 
	 * @return the node value.
	 */
	public final NodeValue<?> getNodeValue() {
		return node.getNodeValue();
	}

	/**
	 * Returns true if value is set.
	 * 
	 * @return true if value is set.
	 */
	public final boolean hasValue() {
		return getNodeValue() != null;
	}

	/**
	 * Sets the node value.
	 * 
	 * @param value the node value to set.
	 */
	public final void setNodeValue(final NodeValue<?> value) {
		if (!node.isEditable()) {
			throw new UnsupportedOperationException();
		}
		final NodeValue<?> prevValue = node.getNodeValue();
		final ForwardCommand command = new ForwardCommand();
		node.appendCommand(command);
		node.setNodeValue(value);
		command.setCommand(new SetValueCommand(value, prevValue));
		if (node.getSession().isAcceptImmediatly()) {
			node.getContext().updateTimestamp();
			node.getSession().fireSessionAccepted();
			node.accept();
		}
	}

	/**
	 * Returns the node value as string.
	 * 
	 * @return the string.
	 */
	public String getNodeValueAsString() {
		return node.getValueAsString();
	}

	/**
	 * Returns the node value as transferable value.
	 * 
	 * @return the transferable value.
	 */
	public TransferableNodeValue getNodeValueAsTransferable() {
		return new TransferableNodeValue(getNodeValueType(), getNodeValue());
	}

	/**
	 * @return the node index.
	 */
	public int getIndex() {
		return node.getParentNode().indexOf(node);
	}

	/**
	 * Returns the number of childs.
	 * 
	 * @return the number of childs.
	 */
	public int getChildNodeCount() {
		return node.getChildNodeCount();
	}

	/**
	 * @param index
	 * @return the editor of the child.
	 */
	public NodeEditor getChildNodeEditor(final int index) {
		return node.getChildNode(index).getNodeEditor();
	}

	public String getChildNodeValueAsString(int index) {
		return node.getChildNode(index).getValueAsString();
	}

	/**
	 * @param value
	 */
	public final void appendChildNode(final NodeValue<?> value) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		final NodeObject newNode = createChildNode(value);
		final ForwardCommand command = new ForwardCommand();
		node.appendCommand(command);
		node.appendChildNode(newNode);
		command.setCommand(new AppendCommand(newNode));
		if (node.getSession().isAcceptImmediatly()) {
			node.getContext().updateTimestamp();
			node.getSession().fireSessionAccepted();
			node.accept();
		}
	}

	/**
	 * @param index
	 * @param value
	 */
	public final void insertChildNodeBefore(final int index, final NodeValue<?> value) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		final NodeObject newNode = createChildNode(value);
		final ForwardCommand command = new ForwardCommand();
		node.appendCommand(command);
		node.insertNodeBefore(index, newNode);
		command.setCommand(new InsertBeforeCommand(index, newNode));
		if (node.getSession().isAcceptImmediatly()) {
			node.getContext().updateTimestamp();
			node.getSession().fireSessionAccepted();
			node.accept();
		}
	}

	/**
	 * @param index
	 * @param value
	 */
	public final void insertChildNodeAfter(final int index, final NodeValue<?> value) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		final NodeObject newNode = createChildNode(value);
		final ForwardCommand command = new ForwardCommand();
		node.appendCommand(command);
		node.insertNodeAfter(index, newNode);
		command.setCommand(new InsertAfterCommand(index, newNode));
		if (node.getSession().isAcceptImmediatly()) {
			node.getSession().fireSessionAccepted();
			node.accept();
		}
	}

	/**
	 * @param index
	 * @param value
	 */
	public void insertChildNodeAt(final Integer index, final NodeValue<?> value) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		final NodeObject newNode = createChildNode(value);
		if (index < node.getChildNodeCount()) {
			final ForwardCommand command = new ForwardCommand();
			node.appendCommand(command);
			node.insertNodeBefore(index, newNode);
			command.setCommand(new InsertBeforeCommand(index, newNode));
		}
		else if (index > 0) {
			final ForwardCommand command = new ForwardCommand();
			node.appendCommand(command);
			node.insertNodeAfter(index - 1, newNode);
			command.setCommand(new InsertAfterCommand(index - 1, newNode));
		}
		else {
			final ForwardCommand command = new ForwardCommand();
			node.appendCommand(command);
			node.appendChildNode(newNode);
			command.setCommand(new AppendCommand(newNode));
		}
		if (node.getSession().isAcceptImmediatly()) {
			node.getContext().updateTimestamp();
			node.getSession().fireSessionAccepted();
			node.accept();
		}
	}

	/**
	 * @param index
	 */
	public final void removeChildNode(final int index) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		if ((index >= 0) && (index < node.getChildNodeCount())) {
			final NodeObject nodeToRemove = node.getChildNode(index);
			final ForwardCommand command = new ForwardCommand();
			command.setCommand(new RemoveCommand(index, nodeToRemove));
			node.removeChildNode(index);
			node.appendCommand(command);
			if (node.getSession().isAcceptImmediatly()) {
				node.getContext().updateTimestamp();
				node.getSession().fireSessionAccepted();
				node.accept();
			}
		}
	}

	/**
	 * @param index
	 */
	public final void moveUpChildNode(final int index) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		if ((index >= 0) && (index < node.getChildNodeCount())) {
			final ForwardCommand command = new ForwardCommand();
			command.setCommand(new MoveUpCommand(index));
			node.moveUpChildNode(index);
			node.appendCommand(command);
			if (node.getSession().isAcceptImmediatly()) {
				node.getContext().updateTimestamp();
				node.getSession().fireSessionAccepted();
				node.accept();
			}
		}
	}

	/**
	 * @param index
	 */
	public final void moveDownChildNode(final int index) {
		if (!node.isMutable()) {
			throw new UnsupportedOperationException();
		}
		if ((index >= 0) && (index < node.getChildNodeCount())) {
			final ForwardCommand command = new ForwardCommand();
			command.setCommand(new MoveDownCommand(index));
			node.moveDownChildNode(index);
			node.appendCommand(command);
			if (node.getSession().isAcceptImmediatly()) {
				node.getContext().updateTimestamp();
				node.getSession().fireSessionAccepted();
				node.accept();
			}
		}
	}

	/**
	 * 
	 */
	public void removeAllChildNodes() {
		while (getChildNodeCount() > 0) {
			removeChildNode(0);
		}
	}

	/**
	 * @return the parent node editor.
	 */
	public NodeEditor getParentNodeEditor() {
		return node.getParentNode().getNodeEditor();
	}

	/**
	 * @return true if node is removable.
	 */
	public boolean isParentMutable() {
		return node.getParentNode().isMutable();
	}

	/**
	 * @param value
	 */
	protected abstract NodeObject createChildNode(NodeValue<?> value);

	/**
	 * @param value
	 */
	protected void doSetValue(final NodeValue<?> value) {
	}

	/**
	 * @param node
	 */
	protected void doAppendNode(final NodeObject node) {
	}

	/**
	 * @param index
	 * @param node
	 */
	protected void doInsertNodeAfter(final int index, final NodeObject node) {
	}

	/**
	 * @param index
	 * @param node
	 */
	protected void doInsertNodeBefore(final int index, final NodeObject node) {
	}

	/**
	 * @param nodeIndex
	 */
	protected void doRemoveNode(final int nodeIndex) {
	}

	/**
	 * @param nodeIndex
	 */
	protected void doMoveUpNode(final int nodeIndex) {
	}

	/**
	 * @param nodeIndex
	 */
	protected void doMoveDownNode(final int nodeIndex) {
	}

	/**
	 * @param nodeIndex
	 */
	protected void doChangeNode(final int nodeIndex, final NodeObject node) {
	}

	private abstract class AbstractCommand implements NodeCommand {
		private boolean consumed;

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#consume()
		 */
		@Override
		public void consume() {
			consumed = true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#isConsumed()
		 */
		@Override
		public boolean isConsumed() {
			return consumed;
		}
	}

	private class SetValueCommand extends AbstractCommand {
		private final NodeValue<?> value;
		private final NodeValue<?> prevValue;
		private final NodePath target;

		/**
		 * @param value
		 * @param prevValue
		 */
		public SetValueCommand(final NodeValue<?> value, final NodeValue<?> prevValue) {
			this.value = value;
			this.prevValue = prevValue;
			target = node.getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doSetValue(value);
			if (isRefreshRequired()) {
				session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, timestamp, false, target, value.getValueClone(), prevValue.getValueClone()));
			}
			else {
				session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_SET_VALUE, timestamp, true, target, value.getValueClone(), prevValue.getValueClone()));
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			node.setNodeValue(prevValue);
		}
	}

	private class AppendCommand extends AbstractCommand {
		private final int index;
		private final NodeObject node;
		private final NodeValue<?> value;
		private final NodePath target;

		/**
		 * @param node
		 */
		public AppendCommand(final NodeObject node) {
			this.node = node;
			value = node.getNodeValue();
			target = node.getParentNode().getNodePath();
			index = node.getNodePath().getLastPathElement();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doAppendNode(node);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_APPEND_NODE, timestamp, target, index, value.getValueClone()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			NodeEditor.this.node.removeChildNode(index);
		}
	}

	private class InsertBeforeCommand extends AbstractCommand {
		private final int index;
		private final NodeObject node;
		private final NodeValue<?> value;
		private final NodePath target;

		/**
		 * @param index
		 * @param node
		 */
		public InsertBeforeCommand(final int index, final NodeObject node) {
			this.index = index;
			this.node = node;
			value = node.getNodeValue();
			target = node.getParentNode().getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doInsertNodeBefore(index, node);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_INSERT_NODE_BEFORE, timestamp, target, index, value.getValueClone()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			NodeEditor.this.node.removeChildNode(index);
		}
	}

	private class InsertAfterCommand extends AbstractCommand {
		private final int index;
		private final NodeObject node;
		private final NodeValue<?> value;
		private final NodePath target;

		/**
		 * @param index
		 * @param node
		 */
		public InsertAfterCommand(final int index, final NodeObject node) {
			this.index = index;
			this.node = node;
			value = node.getNodeValue();
			target = node.getParentNode().getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doInsertNodeAfter(index, node);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_INSERT_NODE_AFTER, timestamp, target, index, value.getValueClone()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			NodeEditor.this.node.removeChildNode(index + 1);
		}
	}

	private class RemoveCommand extends AbstractCommand {
		private final NodeObject node;
		private final int index;
		private final NodeValue<?> value;
		private final NodePath target;

		/**
		 * @param index
		 * @param node
		 */
		public RemoveCommand(final int index, final NodeObject node) {
			this.index = index;
			this.node = node;
			value = node.getNodeValue();
			target = node.getParentNode().getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doRemoveNode(index);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_REMOVE_NODE, timestamp, target, index, value.getValueClone()));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			NodeEditor.this.node.insertChildNodeAt(index, node);
		}
	}

	private class MoveUpCommand extends AbstractCommand {
		private final int index;
		private final NodePath target;

		/**
		 * @param index
		 */
		public MoveUpCommand(final int index) {
			this.index = index;
			target = node.getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doMoveUpNode(index);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_MOVE_UP_NODE, timestamp, target, index));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			node.moveDownChildNode(index);
		}
	}

	private class MoveDownCommand extends AbstractCommand {
		private final int index;
		private final NodePath target;

		/**
		 * @param index
		 */
		public MoveDownCommand(final int index) {
			this.index = index;
			target = node.getNodePath();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			doMoveDownNode(index);
			session.appendAction(new NodeAction(getNodeClass(), NodeAction.ACTION_MOVE_DOWN_NODE, timestamp, target, index));
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept()
		 */
		@Override
		public void cancel() {
			node.moveUpChildNode(index);
		}
	}

	private class ForwardCommand implements NodeCommand {
		private NodeCommand command;

		/**
		 * @return
		 */
		@SuppressWarnings("unused")
		public NodeCommand getCommand() {
			return command;
		}

		/**
		 * @param command
		 */
		void setCommand(final NodeCommand command) {
			this.command = command;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#accept(com.nextbreakpoint.nextfractal.core.tree.NodeSession, long)
		 */
		@Override
		public void accept(final NodeSession session, final long timestamp) {
			if (command != null) {
				command.accept(session, timestamp);
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#cancel()
		 */
		@Override
		public void cancel() {
			if (command != null) {
				command.cancel();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#consume()
		 */
		@Override
		public void consume() {
			if (command != null) {
				command.consume();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.tree.NodeCommand#isConsumed()
		 */
		@Override
		public boolean isConsumed() {
			if (command != null) {
				return command.isConsumed();
			}
			return true;
		}
	}
}
