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
package com.nextbreakpoint.nextfractal.core.util;

import java.util.concurrent.ThreadFactory;

/**
 * @author andrea
 */
public abstract class Refresher {
	public static final int DEFAULT_PAUSE_TIME = 50;
	private final Object lock = new Object();
	private final ThreadFactory factory;
	private volatile Thread thread;
	private volatile boolean running;
	private volatile boolean refresh;
	private long idleTime = 0;
	private long startTime = 0;
	private long totalTime = 0;
	private long sleepTime = 0;
	private long pauseTime = DEFAULT_PAUSE_TIME;

	/**
	 * @param factory
	 */
	public Refresher(ThreadFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return
	 */
	public long getPauseTime() {
		return pauseTime;
	}

	/**
	 * @param pauseTime
	 */
	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}

	/**
	 * 
	 */
	public void refresh() {
		synchronized (lock) {
			refresh = true;
			lock.notify();
		}
	}

	/**
	 * 
	 */
	public void start() {
		if (thread == null) {
			thread = factory.newThread(new RefresherTask());
			running = true;
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		running = false;
		if (thread != null) {
			thread.interrupt();
			try {
				thread.join();
			}
			catch (final InterruptedException e) {
			}
			thread = null;
		}
	}

	/**
	 * 
	 */
	protected abstract void prepare();

	/**
	 * 
	 */
	protected abstract void process();

	/**
	 * @throws InterruptedException
	 */
	protected abstract void acquire() throws InterruptedException;

	/**
	 * 
	 */
	protected abstract void release();

	/**
	 * @return
	 */
	protected abstract boolean needsRefresh();

	private class RefresherTask implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					startTime = System.currentTimeMillis();
					acquire();
					refresh |= needsRefresh();
					prepare();
					process();
					release();
					synchronized (lock) {
						if (refresh) {
							idleTime = System.currentTimeMillis();
						}
						if (System.currentTimeMillis() - idleTime > 1000) {
							if (!refresh) {
								lock.wait();
							}
							idleTime = System.currentTimeMillis();
						}
						refresh = false;
					}
					totalTime = System.currentTimeMillis() - startTime;
					sleepTime = pauseTime - totalTime;
					if (!running) {
						break;
					}
					if (sleepTime > 10) {
						Thread.sleep(sleepTime);
					}
					else {
						Thread.sleep(10);
					}
				}
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}
