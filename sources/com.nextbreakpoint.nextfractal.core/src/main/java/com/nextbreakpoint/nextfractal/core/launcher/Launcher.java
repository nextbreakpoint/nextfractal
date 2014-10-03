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
package com.nextbreakpoint.nextfractal.core.launcher;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

/**
 * @author Andrea Medeghini
 */
public class Launcher<T extends LauncherContext> {
	private static final Logger logger = Logger.getLogger(Launcher.class.getName());
	private final LauncherThreadFactory<T> factory;
	private T context;
	private boolean running;
	private boolean restart;

	/**
	 * @param context
	 * @param factory
	 */
	public Launcher(final T context, final LauncherThreadFactory<T> factory) {
		this.context = context;
		this.factory = factory;
	}

	/**
	 * @throws Exception
	 */
	public void init() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	public void start() throws Exception {
		running = true;
		restart = false;
		final String version = ManagementFactory.getRuntimeMXBean().getVmVersion();
		if ((version.length() > 2) && ("1.7".compareTo(version.substring(0, 3)) > 0)) {
			logger.severe("Could not launch the application because it requires Java 1.7 or later!");
			stop();
		}
		else {
			final Thread thread = factory.createThread(context);
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		synchronized (this) {
			running = false;
			notify();
		}
	}

	/**
	 * @return
	 */
	public boolean dispatch() {
		try {
			while (running) {
				synchronized (this) {
					wait();
				}
			}
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return restart;
	}

	/**
	 * 
	 */
	public void dispose() {
		context = null;
	}

	/**
	 * 
	 */
	public void restart() {
		restart = true;
		stop();
	}
}
