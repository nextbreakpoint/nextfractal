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
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#appendAction(com.nextbreakpoint.nextfractal.core.tree.NodeAction)
	 */
	public void appendAction(final NodeAction action) {
		actions.add(action);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getSessionName()
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getActions()
	 */
	public List<NodeAction> getActions() {
		return Collections.unmodifiableList(actions);
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
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
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#isAcceptImmediatly()
	 */
	public boolean isAcceptImmediatly() {
		return isAcceptImmediatly;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setAcceptImmediatly(boolean)
	 */
	public void setAcceptImmediatly(final boolean isAcceptImmediatly) {
		this.isAcceptImmediatly = isAcceptImmediatly;
	}
	
	public void fireSessionChanged() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionChanged();
		}
	}

	public void fireSessionAccepted() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionAccepted();
		}
	}

	public void fireSessionCancelled() {
		for (NodeSessionListener listener : listeners) {
			listener.fireSessionCancelled();
		}
	}
	
	public void addSessionListener(NodeSessionListener listener) {
		listeners.add(listener);
	}
	
	public void removeSessionListener(NodeSessionListener listener) {
		listeners.remove(listener);
	}
}
