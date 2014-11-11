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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrea Medeghini
 */
public class DefaultNodeSession implements NodeSession {
	private List<NodeSessionListener> listeners = new ArrayList<NodeSessionListener>(); 
	private List<NodeAction> actions = new ArrayList<NodeAction>();
	private final String sessionName;
	private long timestamp;
	private boolean isAcceptImmediatly;

	/**
	 * @param sessionName
	 */
	public DefaultNodeSession(final String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		actions.clear();
		actions = null;
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction)
	 */
	@Override
	public void appendAction(final NodeAction action) {
		actions.add(action);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession#getSessionName()
	 */
	@Override
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession#getActions()
	 */
	@Override
	public List<NodeAction> getActions() {
		return Collections.unmodifiableList(actions);
	}

	/**
	 * @return the timestamp
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	@Override
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	protected int getActionCount() {
		return actions.size();
	}

	/**
	 * @param index
	 * @return
	 */
	protected NodeAction getAction(final int index) {
		return actions.get(index);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession#isAcceptImmediatly()
	 */
	@Override
	public boolean isAcceptImmediatly() {
		return isAcceptImmediatly;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.runtime.tree.NodeSession#setAcceptImmediatly(boolean)
	 */
	@Override
	public void setAcceptImmediatly(final boolean isAcceptImmediatly) {
		this.isAcceptImmediatly = isAcceptImmediatly;
	}
	
	@Override
	public void fireSessionChanged() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionChanged();
		}
	}

	@Override
	public void fireSessionAccepted() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionAccepted();
		}
	}

	@Override
	public void fireSessionCancelled() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionCancelled();
		}
	}
	
	@Override
	public void addSessionListener(NodeSessionListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeSessionListener(NodeSessionListener listener) {
		listeners.remove(listener);
	}
}
