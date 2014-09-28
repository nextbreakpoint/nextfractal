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
package com.nextbreakpoint.nextfractal.twister;

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.tree.NodeSessionListener;

/**
 * @author Andrea Medeghini
 */
public class TwisterSessionController extends AbstractTwisterController implements NodeSession {
	// private static final Logger logger = Logger.getLogger(TwisterSessionController.class.getName());
	private List<NodeSessionListener> listeners = new ArrayList<NodeSessionListener>(); 
	private final String sessionName;
	private long refTimestamp;
	private long timestamp;
	private boolean acceptActions = true;
	private boolean isAcceptImmediatly;

	/**
	 * @param sessionName
	 * @param config
	 */
	public TwisterSessionController(final String sessionName, final TwisterConfig config) {
		this.sessionName = sessionName;
		updateConfig(config);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#init()
	 */
	public void init() {
		commands.clear();
		clipTimestamp = 0;
		clipDuration = 0;
		commandIndex = 0;
		undone = false;
		redone = false;
		// commands.add(new NullCommand(0));
		// redone = true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession#appendAction(com.nextbreakpoint.nextfractal.core.tree.NodeAction)
	 */
	public void appendAction(NodeAction action) {
		// if (logger.isDebugEnabled()) {
		// logger.debug("Session " + getSessionName()+ " - " + action);
		// }
		if (acceptActions) {
			removeDeadCommands();
			final NodeActionValue value = action.toActionValue();
			value.setTimestamp(value.getTimestamp() - refTimestamp);
			action = new NodeAction(value);
			commands.add(new ActionCommand(action));
			clipTimestamp = action.getTimestamp();
			clipDuration = action.getTimestamp();
			undone = false;
			redone = true;
			commandIndex = lastCommandIndex();
		}
	}

	private void removeLastCommand() {
		if (commands.size() > 0) {
			// if (TwisterSessionController.logger.isDebugEnabled()) {
			// TwisterSessionController.logger.debug("Remove command " + commands.get(lastCommandIndex()));
			// }
			commands.remove(lastCommandIndex());
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.DefaultNodeSession#getActions()
	 */
	public List<NodeAction> getActions() {
		removeDeadCommands();
		final List<NodeAction> actions = new ArrayList<NodeAction>(commands.size());
		for (final ControllerCommand command : commands) {
			actions.add(((ActionCommand) command).getAction());
		}
		return actions;
	}

	private void removeDeadCommands() {
		if (commands.size() > 1) {
			while (commandIndex != lastCommandIndex()) {
				removeLastCommand();
			}
			if (!redone) {
				removeLastCommand();
			}
			redone = true;
			undone = false;
			commandIndex = lastCommandIndex();
		}
	}

	/**
	 * 
	 */
	@Override
	protected void doAccept() {
		acceptActions = false;
		super.doAccept();
		acceptActions = true;
	}

	/**
	 * @return the refTimestamp
	 */
	public long getRefTimestamp() {
		return refTimestamp;
	}

	/**
	 * @param refTimestamp the refTimestamp to set
	 */
	public void setRefTimestamp(final long refTimestamp) {
		this.refTimestamp = refTimestamp;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getSessionName()
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#getTimestamp()
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.tree.NodeSession#setTimestamp(long)
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
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
