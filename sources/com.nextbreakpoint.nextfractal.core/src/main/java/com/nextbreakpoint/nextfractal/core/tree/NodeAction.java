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
package com.nextbreakpoint.nextfractal.core.tree;

import java.io.Serializable;

import com.nextbreakpoint.nextfractal.core.config.ConfigElement;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionReference;

/**
 * @author Andrea Medeghini
 */
public final class NodeAction implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int ACTION_SET_VALUE = 0;
	public static final int ACTION_APPEND_NODE = 1;
	public static final int ACTION_INSERT_NODE_BEFORE = 2;
	public static final int ACTION_INSERT_NODE_AFTER = 3;
	public static final int ACTION_REMOVE_NODE = 4;
	public static final int ACTION_MOVE_UP_NODE = 5;
	public static final int ACTION_MOVE_DOWN_NODE = 6;
	public static final int ACTION_CHANGE_NODE = 7;
	public static final String[] actionNames = { "ACTION_SET_VALUE", "ACTION_APPEND_NODE", "ACTION_INSERT_NODE_BEFORE", "ACTION_INSERT_NODE_AFTER", "ACTION_REMOVE_NODE", "ACTION_MOVE_UP_NODE", "ACTION_MOVE_DOWN_NODE", "ACTION_CHANGE_NODE" };
	private final String actionId;
	private final int actionType;
	private final long timestamp;
	private final boolean refreshRequired;
	private final NodePath actionTarget;
	private final Serializable[] actionParams;

	private NodeAction(final String actionId, final int actionType, final long timestamp, final boolean refreshRequired, final NodePath actionTarget, final Serializable[] actionParams) {
		this.actionId = actionId;
		this.actionType = actionType;
		this.timestamp = timestamp;
		this.refreshRequired = refreshRequired;
		this.actionTarget = actionTarget;
		this.actionParams = actionParams;
	}

	/**
	 * @param actionId
	 * @param actionType
	 * @param timestamp
	 * @param refreshRequired
	 * @param actionTarget
	 * @param actionParam0
	 */
	public NodeAction(final String actionId, final int actionType, final long timestamp, final boolean refreshRequired, final NodePath actionTarget, final Serializable actionParam0) {
		this(actionId, actionType, timestamp, refreshRequired, actionTarget, new Serializable[] { actionParam0 });
	}

	/**
	 * @param actionId
	 * @param actionType
	 * @param timestamp
	 * @param refreshRequired
	 * @param actionTarget
	 * @param actionParam0
	 * @param actionParam1
	 */
	public NodeAction(final String actionId, final int actionType, final long timestamp, final boolean refreshRequired, final NodePath actionTarget, final Serializable actionParam0, final Serializable actionParam1) {
		this(actionId, actionType, timestamp, refreshRequired, actionTarget, new Serializable[] { actionParam0, actionParam1 });
	}

	/**
	 * @param actionId
	 * @param actionType
	 * @param timestamp
	 * @param actionTarget
	 * @param actionParam0
	 */
	public NodeAction(final String actionId, final int actionType, final long timestamp, final NodePath actionTarget, final Serializable actionParam0) {
		this(actionId, actionType, timestamp, true, actionTarget, new Serializable[] { actionParam0 });
	}

	/**
	 * @param actionId
	 * @param actionType
	 * @param timestamp
	 * @param actionTarget
	 * @param actionParam0
	 * @param actionParam1
	 */
	public NodeAction(final String actionId, final int actionType, final long timestamp, final NodePath actionTarget, final Serializable actionParam0, final Serializable actionParam1) {
		this(actionId, actionType, timestamp, true, actionTarget, new Serializable[] { actionParam0, actionParam1 });
	}

	/**
	 * @param value
	 */
	public NodeAction(final NodeActionValue value) {
		this(value.getActionId(), value.getActionType(), value.getTimestamp(), value.isRefreshRequired(), value.getActionTarget(), value.getActionParams());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeAction#redo(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	public void redo(final NodeEditor editor) {
		switch (actionType) {
			case ACTION_SET_VALUE: {
				if ((actionParams[0] != null) && (actionParams[0] instanceof ConfigElement)) {
					editor.setNodeValue(editor.createNodeValue(((ConfigElement) actionParams[0]).clone()));
				}
				else if ((actionParams[0] != null) && (actionParams[0] instanceof ExtensionReference)) {
					editor.setNodeValue(editor.createNodeValue(((ExtensionReference) actionParams[0]).clone()));
				}
				else {
					editor.setNodeValue(editor.createNodeValue(actionParams[0]));
				}
				break;
			}
			case ACTION_APPEND_NODE: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.appendChildNode(editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.appendChildNode(editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.appendChildNode(editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			case ACTION_INSERT_NODE_AFTER: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.insertChildNodeAfter((Integer) actionParams[0], editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.insertChildNodeAfter((Integer) actionParams[0], editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.insertChildNodeAfter((Integer) actionParams[0], editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			case ACTION_INSERT_NODE_BEFORE: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.insertChildNodeBefore((Integer) actionParams[0], editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.insertChildNodeBefore((Integer) actionParams[0], editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.insertChildNodeBefore((Integer) actionParams[0], editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			case ACTION_REMOVE_NODE: {
				editor.removeChildNode((Integer) actionParams[0]);
				break;
			}
			case ACTION_MOVE_UP_NODE: {
				editor.moveUpChildNode((Integer) actionParams[0]);
				break;
			}
			case ACTION_MOVE_DOWN_NODE: {
				editor.moveDownChildNode((Integer) actionParams[0]);
				break;
			}
			case ACTION_CHANGE_NODE: {
				if ((actionParams[0] != null) && (actionParams[0] instanceof ConfigElement)) {
					editor.setNodeValue(editor.createNodeValue(((ConfigElement) actionParams[0]).clone()));
				}
				else if ((actionParams[0] != null) && (actionParams[0] instanceof ExtensionReference)) {
					editor.setNodeValue(editor.createNodeValue(((ExtensionReference) actionParams[0]).clone()));
				}
				else {
					editor.setNodeValue(editor.createNodeValue(actionParams[0]));
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeAction#undo(com.nextbreakpoint.nextfractal.core.tree.NodeEditor)
	 */
	public void undo(final NodeEditor editor) {
		switch (actionType) {
			case ACTION_SET_VALUE: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.setNodeValue(editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.setNodeValue(editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.setNodeValue(editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			case ACTION_APPEND_NODE: {
				editor.removeChildNode((Integer) actionParams[0]);
				break;
			}
			case ACTION_INSERT_NODE_BEFORE: {
				editor.removeChildNode((Integer) actionParams[0]);
				break;
			}
			case ACTION_INSERT_NODE_AFTER: {
				editor.removeChildNode((Integer) actionParams[0] + 1);
				break;
			}
			case ACTION_REMOVE_NODE: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.insertChildNodeAt((Integer) actionParams[0], editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.insertChildNodeAt((Integer) actionParams[0], editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.insertChildNodeAt((Integer) actionParams[0], editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			case ACTION_MOVE_UP_NODE: {
				editor.moveDownChildNode((Integer) actionParams[0] - 1);
				break;
			}
			case ACTION_MOVE_DOWN_NODE: {
				editor.moveUpChildNode((Integer) actionParams[0] + 1);
				break;
			}
			case ACTION_CHANGE_NODE: {
				if ((actionParams[1] != null) && (actionParams[1] instanceof ConfigElement)) {
					editor.setNodeValue(editor.createNodeValue(((ConfigElement) actionParams[1]).clone()));
				}
				else if ((actionParams[1] != null) && (actionParams[1] instanceof ExtensionReference)) {
					editor.setNodeValue(editor.createNodeValue(((ExtensionReference) actionParams[1]).clone()));
				}
				else {
					editor.setNodeValue(editor.createNodeValue(actionParams[1]));
				}
				break;
			}
			default: {
				break;
			}
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("actionId = \"");
		builder.append(actionId);
		builder.append("\", actionType = \"");
		builder.append(NodeAction.actionNames[actionType]);
		builder.append("\", timestamp = [ ");
		builder.append(timestamp);
		builder.append(", actionTarget = [ ");
		builder.append(actionTarget.toString());
		builder.append(", actionParams = [ ");
		builder.append(actionParams[0]);
		for (int i = 1; i < actionParams.length; i++) {
			builder.append(", ");
			builder.append(actionParams[i]);
		}
		builder.append(" ] ");
		return builder.toString();
	}

	/**
	 * @return
	 */
	public Serializable[] getActionParams() {
		return actionParams;
	}

	/**
	 * @return
	 */
	public NodePath getActionTarget() {
		return actionTarget;
	}

	/**
	 * @return
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * @return the actionId
	 */
	public String getActionId() {
		return actionId;
	}

	/**
	 * @return
	 */
	public NodeActionValue toActionValue() {
		final NodeActionValue value = new NodeActionValue();
		value.setActionId(actionId);
		value.setActionType(actionType);
		value.setTimestamp(timestamp);
		value.setRefreshRequired(refreshRequired);
		value.setActionTarget(actionTarget);
		value.setActionParams(actionParams);
		return value;
	}

	/**
	 * @return
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return
	 */
	public boolean isRefreshRequired() {
		return refreshRequired;
	}
}
