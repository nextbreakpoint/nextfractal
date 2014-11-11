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
package com.nextbreakpoint.nextfractal.core.runtime.tree;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class NodeActionValue {
	private int actionType;
	private String actionId;
	private long timestamp;
	private boolean refreshRequired;
	private NodePath actionTarget;
	private Serializable[] actionParams;

	/**
	 * @return the actionTarget
	 */
	public NodePath getActionTarget() {
		return actionTarget;
	}

	/**
	 * @param actionTarget the actionTarget to set
	 */
	public void setActionTarget(final NodePath actionTarget) {
		this.actionTarget = actionTarget;
	}

	/**
	 * @return the actionType
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(final int actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the nodeId
	 */
	public String getActionId() {
		return actionId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setActionId(final String nodeId) {
		actionId = nodeId;
	}

	/**
	 * @return the params
	 */
	public Serializable[] getActionParams() {
		return actionParams;
	}

	/**
	 * @param params the params to set
	 */
	public void setActionParams(final Serializable[] params) {
		actionParams = params;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the refreshRequired
	 */
	public boolean isRefreshRequired() {
		return refreshRequired;
	}

	/**
	 * @param refreshRequired the refreshRequired to set
	 */
	public void setRefreshRequired(final boolean refreshRequired) {
		this.refreshRequired = refreshRequired;
	}
}
