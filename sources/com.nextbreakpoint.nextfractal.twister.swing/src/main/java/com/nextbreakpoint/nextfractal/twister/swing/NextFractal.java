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
package com.nextbreakpoint.nextfractal.twister.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.nextbreakpoint.nextfractal.core.Application;
import com.nextbreakpoint.nextfractal.core.ApplicationContext;
import com.nextbreakpoint.nextfractal.core.launcher.LauncherContextListener;
import com.nextbreakpoint.nextfractal.core.launcher.LauncherThreadFactory;
import com.nextbreakpoint.nextfractal.core.swing.launcher.Launcher;


/**
 * Application implementation.
 * 
 * @author Andrea Medeghini
 */
public class NextFractal implements Application {
	private static final List<JFrame> frames = new ArrayList<JFrame>(1);
	private final Launcher<DefaultTwisterContext> launcher = new Launcher<DefaultTwisterContext>(new DefaultTwisterContext(), new DefaultLauncherThreadFactory());
	private LauncherContextListener listener;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.Application#start(com.nextbreakpoint.nextfractal.core.ApplicationContext)
	 */
	@Override
	public Object start(final ApplicationContext context) throws Exception {
		boolean restart = false;
		Integer exit = Application.EXIT_OK; 
		try {
			launcher.init();
			launcher.start();
			if (listener != null) {
				listener.started();
			}
			context.applicationRunning();
			restart = launcher.dispatch();
			if (listener != null) {
				listener.stopped();
			}
			launcher.dispose();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		if (restart) {
			exit = Application.EXIT_RESTART;
		}
		return exit;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.Application#stop()
	 */
	@Override
	public void stop() {
		launcher.stop();
	}

	private class DefaultTwisterContext implements TwisterContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.SwingContext.swing.TwisterContext#addFrame(javax.swing.JFrame)
		 */
		@Override
		public void addFrame(final JFrame frame) {
			NextFractal.frames.add(frame);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.SwingContext.swing.TwisterContext#removeFrame(javax.swing.JFrame)
		 */
		@Override
		public void removeFrame(final JFrame frame) {
			NextFractal.frames.remove(frame);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.SwingContext.swing.TwisterContext#getFrameCount()
		 */
		@Override
		public int getFrameCount() {
			return NextFractal.frames.size();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.launcher.LauncherContext#exit()
		 */
		@Override
		public void exit() {
			launcher.stop();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.launcher.LauncherContext#restart()
		 */
		@Override
		public void restart() {
			launcher.restart();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.launcher.LauncherContext#setContextListener(com.nextbreakpoint.nextfractal.launcher.LauncherContextListener)
		 */
		@Override
		public void setContextListener(final LauncherContextListener listener) {
			NextFractal.this.listener = listener;
		}
	}

	private class DefaultLauncherThreadFactory implements LauncherThreadFactory<DefaultTwisterContext> {
		/**
		 * @see com.nextbreakpoint.nextfractal.launcher.LauncherThreadFactory#createThread(com.nextbreakpoint.nextfractal.launcher.LauncherContext)
		 */
		@Override
		public Thread createThread(final DefaultTwisterContext context) {
			return new TwisterLauncherThread(context);
		}
	}
}
