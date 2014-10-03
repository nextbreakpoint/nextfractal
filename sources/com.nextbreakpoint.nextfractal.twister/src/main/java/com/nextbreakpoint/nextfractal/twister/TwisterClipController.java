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

import java.util.List;

import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.tree.NodeAction;
import com.nextbreakpoint.nextfractal.core.tree.NodeActionValue;

/**
 * @author Andrea Medeghini
 */
public class TwisterClipController extends AbstractTwisterController {
	private final TwisterClip clip;

	/**
	 * @param clip
	 */
	public TwisterClipController(final TwisterClip clip) {
		this.clip = clip;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.TwisterController#init()
	 */
	@Override
	public void init() {
		commands.clear();
		clipTimestamp = 0;
		clipDuration = 0;
		commandIndex = 0;
		undone = false;
		redone = false;
		// commands.add(new NullCommand(0));
		// redone = true;
		loadCommands(commands);
		if (commands.size() > 0) {
			commands.get(0).redo();
			commandIndex = 0;
			redone = true;
		}
		else {
			if (clip.getSequenceCount() > 0) {
				if (clip.getSequence(0).getInitialConfig() != null) {
					final TwisterConfig configClone = clip.getSequence(0).getInitialConfig().clone();
					configClone.setContext(new DefaultConfigContext());
					updateConfig(configClone);
				}
				else if (clip.getSequence(0).getFinalConfig() != null) {
					final TwisterConfig configClone = clip.getSequence(0).getFinalConfig().clone();
					configClone.setContext(new DefaultConfigContext());
					updateConfig(configClone);
				}
			}
		}
	}

	private void loadCommands(final List<ControllerCommand> commands) {
		long timestamp = 0;
		for (int i = 0; i < clip.getSequenceCount(); i++) {
			final TwisterSequence sequence = clip.getSequence(i);
			if (sequence.getDuration() > 0) {
				if (sequence.getInitialConfig() != null) {
					commands.add(new InitialConfigCommand(sequence.getInitialConfig(), timestamp));
				}
				// else {
				// commands.add(new LoadCommand(sequence.getFinalConfig().clone(), timestamp) {
				// public void redo() {
				// updateConfig(config);
				// }
				// });
				// }
				for (int j = 0; j < sequence.getActionCount(); j++) {
					final NodeActionValue actionValue = sequence.getAction(j).toActionValue();
					actionValue.setTimestamp(actionValue.getTimestamp() + timestamp);
					commands.add(new ActionCommand(new NodeAction(actionValue)));
				}
				clipDuration += sequence.getDuration();
				timestamp += sequence.getDuration();
				if (sequence.getFinalConfig() != null) {
					commands.add(new FinalConfigCommand(sequence.getFinalConfig(), timestamp));
				}
				// else {
				// commands.add(new LoadCommand(sequence.getInitialConfig().clone(), timestamp) {
				// public void undo() {
				// updateConfig(config);
				// }
				// });
				// }
			}
		}
	}

	private class InitialConfigCommand extends LoadCommand {
		/**
		 * @param config
		 * @param timestamp
		 */
		public InitialConfigCommand(final TwisterConfig config, final long timestamp) {
			super(config, timestamp);
		}

		@Override
		public void redo() {
			final TwisterConfig configClone = config.clone();
			configClone.setContext(new DefaultConfigContext());
			updateConfig(configClone);
		}
	}

	private class FinalConfigCommand extends LoadCommand {
		/**
		 * @param config
		 * @param timestamp
		 */
		public FinalConfigCommand(final TwisterConfig config, final long timestamp) {
			super(config, timestamp);
		}

		@Override
		public void undo() {
			final TwisterConfig configClone = config.clone();
			configClone.setContext(new DefaultConfigContext());
			updateConfig(configClone);
		}
	}
}
