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
package com.nextbreakpoint.nextfractal.twister;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultNodeSession;
import com.nextbreakpoint.nextfractal.core.runtime.tree.DefaultRootNode;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodePath;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractTwisterController implements TwisterController {
	private static final Logger logger = Logger.getLogger(AbstractTwisterController.class.getName());
	private final List<ControllerListener> listeners = new ArrayList<ControllerListener>();
	protected List<ControllerCommand> commands = new ArrayList<ControllerCommand>();
	protected List<ControllerAction> actions = new ArrayList<ControllerAction>();
	private final NodeObject rootNode = new DefaultRootNode();
	private TwisterConfig config;
	protected int commandIndex = -1;
	protected long clipDuration = 0;
	protected long clipTimestamp = 0;
	protected boolean redone = true;
	protected boolean undone = false;
	protected boolean needRefresh;
	private RenderContext context;

	/**
	 *
	 */
	public AbstractTwisterController() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#setRenderContext(com.nextbreakpoint.nextfractal.core.util.RenderContext)
	 */
	@Override
	public void setRenderContext(final RenderContext context) {
		this.context = context;
	}

	/**
	 * @param config
	 */
	protected void updateConfig(final TwisterConfig config) {
		if (this.config == null) {
			this.config = config;
			final TwisterConfigNodeBuilder builder = new TwisterConfigNodeBuilder(config);
			rootNode.removeAllChildNodes();
			builder.createNodes(rootNode);
			rootNode.setContext(config.getContext());
			rootNode.setSession(new DefaultNodeSession("controller"));
		}
		else {
			this.config.setFrameConfigElement(config.getFrameConfigElement().clone());
			this.config.setEffectConfigElement(config.getEffectConfigElement().clone());
			this.config.setBackground(config.getBackground());
		}
		fireConfigChanged();
	}

	private boolean isEmpty() {
		return commands.size() == 0;
	}

	private ControllerCommand getCommand() {
		if ((commandIndex > -1) && (commandIndex < commands.size())) {
			return commands.get(commandIndex);
		}
		return null;
	}

	private ControllerCommand getNextCommand() {
		if ((commandIndex >= -1) && (commandIndex < commands.size() - 1)) {
			return commands.get(commandIndex + 1);
		}
		return null;
	}

	private ControllerCommand getPrevCommand() {
		if ((commandIndex > 0) && (commandIndex <= commands.size() - 1)) {
			return commands.get(commandIndex - 1);
		}
		return null;
	}

	private boolean isFirstIndex() {
		return commandIndex == 0;
	}

	private boolean isLastIndex() {
		return commandIndex == commands.size() - 1;
	}

	private boolean nextCommand() {
		final int index = commandIndex + 1;
		if (index < commands.size()) {
			commandIndex = index;
			redone = false;
			undone = false;
			return true;
		}
		return false;
	}

	private boolean prevCommand() {
		final int index = commandIndex - 1;
		if (index > -1) {
			commandIndex = index;
			redone = false;
			undone = false;
			return true;
		}
		return false;
	}

	private void redoCommand() {
		if ((commandIndex > -1) && (commandIndex < commands.size())) {
			commands.get(commandIndex).redo();
		}
		redone = true;
		undone = false;
	}

	private void undoCommand() {
		if ((commandIndex > -1) && (commandIndex < commands.size())) {
			commands.get(commandIndex).undo();
		}
		undone = true;
		redone = false;
	}

	private void redoAction(final NodeAction action) {
		actions.add(new ControllerAction(false, action));
		if (action.isRefreshRequired()) {
			needRefresh = true;
		}
	}

	private void undoAction(final NodeAction action) {
		actions.add(new ControllerAction(true, action));
		if (action.isRefreshRequired()) {
			needRefresh = true;
		}
	}

	private void execute() {
		if (needRefresh && (context != null)) {
			context.stopRenderers();
		}
		for (final ControllerAction action : actions) {
			action.execute();
		}
		actions.clear();
		doAccept();
		if (needRefresh && (context != null)) {
			context.startRenderers();
		}
		needRefresh = false;
	}

	private void executeRedoAction(final NodeAction action) {
		final NodePath path = action.getActionTarget();
		final Integer[] pe = path.getPathElements();
		NodeObject node = rootNode;
		try {
			for (final Integer element : pe) {
				node = node.getChildNode(element);
			}
			if (AbstractTwisterController.logger.isLoggable(Level.FINER)) {
				AbstractTwisterController.logger.finer(action.toString());
			}
			action.redo(node.getNodeEditor());
		}
		catch (Exception e) {
			logger.log(Level.WARNING, path.toString(), e);
		}
	}

	private void executeUndoAction(final NodeAction action) {
		final NodePath path = action.getActionTarget();
		final Integer[] pe = path.getPathElements();
		NodeObject node = rootNode;
		try {
			for (final Integer element : pe) {
				node = node.getChildNode(element);
			}
			if (AbstractTwisterController.logger.isLoggable(Level.FINER)) {
				AbstractTwisterController.logger.finer(action.toString());
			}
			action.undo(node.getNodeEditor());
		}
		catch (Exception e) {
			logger.log(Level.WARNING, path.toString(), e);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#addControllerListener(com.nextbreakpoint.nextfractal.twister.ControllerListener)
	 */
	@Override
	public void addControllerListener(final ControllerListener listener) {
		listeners.add(listener);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#removeControllerListener(com.nextbreakpoint.nextfractal.twister.ControllerListener)
	 */
	@Override
	public void removeControllerListener(final ControllerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * 
	 */
	protected void fireConfigChanged() {
		for (final ControllerListener listener : listeners) {
			listener.configChanged();
		}
	}

	/**
	 * @param action
	 */
	protected void fireActionUndone(final NodeAction action) {
		for (final ControllerListener listener : listeners) {
			listener.actionUndone(action);
		}
	}

	/**
	 * @param action
	 */
	protected void fireActionRedone(final NodeAction action) {
		for (final ControllerListener listener : listeners) {
			listener.actionRedone(action);
		}
	}

	/**
	 * @return
	 */
	protected int lastCommandIndex() {
		return commands.size() - 1;
	}

	/**
	 * 
	 */
	protected void doAccept() {
		rootNode.accept();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#redoAction(boolean)
	 */
	@Override
	public boolean redoAction(final boolean sameTimestamp) {
		if (redo(sameTimestamp)) {
			execute();
			return true;
		}
		return false;
	}

	private boolean redo(final boolean sameTimestamp) {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isLastIndex() && redone)) {
			return false;
		}
		if (!redone) {
			redoCommand();
			if (sameTimestamp) {
				ControllerCommand action = getCommand();
				if (action != null) {
					final long timestamp = action.getTimestamp();
					while ((action = getNextCommand()) != null) {
						if (action.getTimestamp() != timestamp) {
							break;
						}
						nextCommand();
						redoCommand();
					}
				}
			}
		}
		else {
			if (nextCommand()) {
				redoCommand();
				if (sameTimestamp) {
					ControllerCommand action = getCommand();
					if (action != null) {
						final long timestamp = action.getTimestamp();
						while ((action = getNextCommand()) != null) {
							if (action.getTimestamp() != timestamp) {
								break;
							}
							nextCommand();
							redoCommand();
						}
					}
				}
			}
		}
		if (isLastIndex() && redone) {
			clipTimestamp = clipDuration;
		}
		else {
			clipTimestamp = getCommand().getTimestamp();
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#undoAction(boolean)
	 */
	@Override
	public boolean undoAction(final boolean sameTimestamp) {
		if (undo(sameTimestamp)) {
			execute();
			return true;
		}
		return false;
	}

	private boolean undo(final boolean sameTimestamp) {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isFirstIndex() && undone)) {
			return false;
		}
		if (!undone) {
			undoCommand();
			if (sameTimestamp) {
				ControllerCommand action = getCommand();
				if (action != null) {
					final long timestamp = action.getTimestamp();
					while ((action = getPrevCommand()) != null) {
						if (action.getTimestamp() != timestamp) {
							break;
						}
						prevCommand();
						undoCommand();
					}
				}
			}
		}
		else {
			if (prevCommand()) {
				undoCommand();
				if (sameTimestamp) {
					ControllerCommand action = getCommand();
					if (action != null) {
						final long timestamp = action.getTimestamp();
						while ((action = getPrevCommand()) != null) {
							if (action.getTimestamp() != timestamp) {
								break;
							}
							prevCommand();
							undoCommand();
						}
					}
				}
			}
		}
		if (isFirstIndex() && undone) {
			clipTimestamp = 0;
		}
		else {
			clipTimestamp = getCommand().getTimestamp();
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#redoAction(long, boolean)
	 */
	@Override
	public boolean redoAction(final long timestamp, final boolean relative) {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isLastIndex() && redone)) {
			return false;
		}
		if (relative) {
			if (!redone) {
				ControllerCommand action = getCommand();
				if (action != null) {
					if (timestamp >= action.getTimestamp() - clipTimestamp) {
						redoCommand();
					}
					action = getNextCommand();
					if (action != null) {
						if (timestamp >= action.getTimestamp() - clipTimestamp) {
							nextCommand();
							redoCommand();
							while ((action = getNextCommand()) != null) {
								if (timestamp < action.getTimestamp() - clipTimestamp) {
									break;
								}
								nextCommand();
								redoCommand();
							}
						}
					}
				}
			}
			else {
				ControllerCommand action = getNextCommand();
				if (action != null) {
					if (timestamp >= action.getTimestamp() - clipTimestamp) {
						nextCommand();
						redoCommand();
						while ((action = getNextCommand()) != null) {
							if (timestamp < action.getTimestamp() - clipTimestamp) {
								break;
							}
							nextCommand();
							redoCommand();
						}
					}
				}
			}
		}
		else {
			if (!redone) {
				ControllerCommand action = getCommand();
				if (action != null) {
					if (timestamp >= action.getTimestamp()) {
						redoCommand();
					}
					action = getNextCommand();
					if (action != null) {
						if (timestamp >= action.getTimestamp()) {
							nextCommand();
							redoCommand();
							while ((action = getNextCommand()) != null) {
								if (timestamp < action.getTimestamp()) {
									break;
								}
								nextCommand();
								redoCommand();
							}
						}
					}
				}
			}
			else {
				ControllerCommand action = getNextCommand();
				if (action != null) {
					if (timestamp >= action.getTimestamp()) {
						nextCommand();
						redoCommand();
						while ((action = getNextCommand()) != null) {
							if (timestamp < action.getTimestamp()) {
								break;
							}
							nextCommand();
							redoCommand();
						}
					}
				}
			}
		}
		if (isLastIndex() && redone) {
			clipTimestamp = clipDuration;
		}
		else {
			if (relative) {
				clipTimestamp += timestamp;
			}
			else {
				clipTimestamp = timestamp;
			}
		}
		execute();
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#undoAction(long, boolean)
	 */
	@Override
	public boolean undoAction(final long timestamp, final boolean relative) {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isFirstIndex() && undone)) {
			return false;
		}
		if (relative) {
			if (!undone) {
				ControllerCommand action = getCommand();
				if (action != null) {
					if (timestamp >= clipTimestamp - action.getTimestamp()) {
						undoCommand();
					}
					action = getPrevCommand();
					if (action != null) {
						if (timestamp >= clipTimestamp - action.getTimestamp()) {
							prevCommand();
							undoCommand();
							while ((action = getPrevCommand()) != null) {
								if (timestamp < clipTimestamp - action.getTimestamp()) {
									break;
								}
								prevCommand();
								undoCommand();
							}
						}
					}
				}
			}
			else {
				ControllerCommand action = getPrevCommand();
				if (action != null) {
					if (timestamp >= clipTimestamp - action.getTimestamp()) {
						prevCommand();
						undoCommand();
						while ((action = getPrevCommand()) != null) {
							if (timestamp < clipTimestamp - action.getTimestamp()) {
								break;
							}
							prevCommand();
							undoCommand();
						}
					}
				}
			}
		}
		else {
			if (!undone) {
				ControllerCommand action = getCommand();
				if (action != null) {
					if (timestamp <= action.getTimestamp()) {
						undoCommand();
					}
					action = getPrevCommand();
					if (action != null) {
						if (timestamp <= action.getTimestamp()) {
							prevCommand();
							undoCommand();
							while ((action = getPrevCommand()) != null) {
								if (timestamp > action.getTimestamp()) {
									break;
								}
								prevCommand();
								undoCommand();
							}
						}
					}
				}
			}
			else {
				ControllerCommand action = getPrevCommand();
				if (action != null) {
					if (timestamp <= action.getTimestamp()) {
						prevCommand();
						undoCommand();
						while ((action = getPrevCommand()) != null) {
							if (timestamp > action.getTimestamp()) {
								break;
							}
							prevCommand();
							undoCommand();
						}
					}
				}
			}
		}
		if (isFirstIndex() && undone) {
			clipTimestamp = 0;
		}
		else {
			if (relative) {
				clipTimestamp -= timestamp;
			}
			else {
				clipTimestamp = timestamp;
			}
		}
		execute();
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#redoActionAndSleep()
	 */
	@Override
	public boolean redoActionAndSleep() {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isLastIndex() && redone)) {
			return false;
		}
		long sleep = 0;
		if (!redone) {
			ControllerCommand action = getCommand();
			if (action != null) {
				long timestamp = action.getTimestamp();
				if (timestamp > clipTimestamp) {
					try {
						Thread.sleep(timestamp - clipTimestamp);
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				redoCommand();
				clipTimestamp = timestamp;
				while ((action = getNextCommand()) != null) {
					if (action.getTimestamp() != clipTimestamp) {
						break;
					}
					timestamp = action.getTimestamp();
					if (timestamp > clipTimestamp) {
						sleep += timestamp - clipTimestamp;
					}
					nextCommand();
					redoCommand();
					clipTimestamp = getCommand().getTimestamp();
				}
			}
		}
		else {
			ControllerCommand action = getNextCommand();
			if (action != null) {
				long timestamp = action.getTimestamp();
				if (timestamp > clipTimestamp) {
					try {
						Thread.sleep(timestamp - clipTimestamp);
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				nextCommand();
				redoCommand();
				clipTimestamp = timestamp;
				while ((action = getNextCommand()) != null) {
					if (action.getTimestamp() != clipTimestamp) {
						break;
					}
					timestamp = action.getTimestamp();
					if (timestamp > clipTimestamp) {
						sleep += timestamp - clipTimestamp;
					}
					nextCommand();
					redoCommand();
					clipTimestamp = getCommand().getTimestamp();
				}
			}
		}
		execute();
		if (sleep > 0) {
			try {
				Thread.sleep(sleep);
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (isLastIndex() && redone) {
			try {
				Thread.sleep(clipDuration - clipTimestamp);
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			clipTimestamp = clipDuration;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#undoActionAndSleep()
	 */
	@Override
	public boolean undoActionAndSleep() {
		// if (logger.isDebugEnabled()) {
		// logger.debug(getStatus());
		// }
		if (isEmpty() || (isFirstIndex() && undone)) {
			return false;
		}
		long sleep = 0;
		if (!undone) {
			ControllerCommand action = getCommand();
			if (action != null) {
				long timestamp = action.getTimestamp();
				if (timestamp < clipTimestamp) {
					try {
						Thread.sleep(clipTimestamp - timestamp);
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				undoCommand();
				clipTimestamp = timestamp;
				while ((action = getPrevCommand()) != null) {
					if (action.getTimestamp() != clipTimestamp) {
						break;
					}
					timestamp = action.getTimestamp();
					if (timestamp > clipTimestamp) {
						sleep += clipTimestamp - timestamp;
					}
					prevCommand();
					undoCommand();
					clipTimestamp = getCommand().getTimestamp();
				}
			}
		}
		else {
			ControllerCommand action = getPrevCommand();
			if (action != null) {
				long timestamp = action.getTimestamp();
				if (timestamp < clipTimestamp) {
					try {
						Thread.sleep(clipTimestamp - timestamp);
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				prevCommand();
				undoCommand();
				clipTimestamp = timestamp;
				while ((action = getPrevCommand()) != null) {
					if (action.getTimestamp() != clipTimestamp) {
						break;
					}
					timestamp = action.getTimestamp();
					if (timestamp < clipTimestamp) {
						sleep += clipTimestamp - timestamp;
					}
					prevCommand();
					undoCommand();
					clipTimestamp = getCommand().getTimestamp();
				}
			}
		}
		execute();
		if (sleep > 0) {
			try {
				Thread.sleep(sleep);
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (isFirstIndex() && undone) {
			try {
				Thread.sleep(clipTimestamp);
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			clipTimestamp = 0;
		}
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#undoAll()
	 */
	@Override
	public boolean undoAll() {
		while (undo(false)) {
		}
		execute();
		clipTimestamp = 0;
		return true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#redoAll()
	 */
	@Override
	public boolean redoAll() {
		while (redo(false)) {
		}
		execute();
		clipTimestamp = clipDuration;
		return true;
	}

	/**
	 * @return the config
	 */
	public TwisterConfig getConfig() {
		return config;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#getDuration()
	 */
	@Override
	public long getDuration() {
		return clipDuration;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#getTime()
	 */
	@Override
	public long getTime() {
		return clipTimestamp;
	}

	protected interface ControllerCommand {
		public void undo();

		public void redo();

		public long getTimestamp();
	}

	protected class ActionCommand implements ControllerCommand {
		private final NodeAction action;

		public ActionCommand(final NodeAction action) {
			this.action = action;
		}

		@Override
		public void undo() {
			undoAction(action);
			fireActionUndone(action);
		}

		@Override
		public void redo() {
			redoAction(action);
			fireActionRedone(action);
		}

		@Override
		public long getTimestamp() {
			return action.getTimestamp();
		}

		public NodeAction getAction() {
			return action;
		}
	}

	protected class LoadCommand implements ControllerCommand {
		protected TwisterConfig config;
		protected long timestamp;

		public LoadCommand(final TwisterConfig config, final long timestamp) {
			this.config = config;
			this.timestamp = timestamp;
		}

		@Override
		public void undo() {
		}

		@Override
		public void redo() {
		}

		@Override
		public long getTimestamp() {
			return timestamp;
		}
	}

	protected class NullCommand implements ControllerCommand {
		protected long timestamp;

		public NullCommand(final long timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public void undo() {
		}

		@Override
		public void redo() {
		}

		@Override
		public long getTimestamp() {
			return timestamp;
		}
	}

	private class ControllerAction {
		private final boolean isUndo;
		private final NodeAction action;

		/**
		 * @param isUndo
		 * @param action
		 */
		protected ControllerAction(final boolean isUndo, final NodeAction action) {
			this.isUndo = isUndo;
			this.action = action;
		}

		/**
		 * 
		 */
		public void execute() {
			if (isUndo) {
				executeUndoAction(action);
			}
			else {
				executeRedoAction(action);
			}
		}
	}
}
