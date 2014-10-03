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
package com.nextbreakpoint.nextfractal.core.swing.media.animator;

import com.nextbreakpoint.nextfractal.core.media.MovieContext;

/**
 * @author Andrea Medeghini
 */
public final class MovieAnimator {
	private static final boolean debug = false;
	private volatile boolean running = false;
	private final MovieAnimatorContext animatorContext;
	private final MovieContext movieContext;
	private Thread thread;

	/**
	 * @param movieContext
	 * @param animatorContext
	 */
	public MovieAnimator(final MovieContext movieContext, final MovieAnimatorContext animatorContext) {
		this.movieContext = movieContext;
		this.animatorContext = animatorContext;
	}

	/**
	 * 
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(new AnimatorRunnable());
			thread.setDaemon(true);
			running = true;
			thread.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		if (thread != null) {
			running = false;
			thread.interrupt();
			try {
				thread.join();
			}
			catch (final InterruptedException e) {
			}
			thread = null;
		}
	}

	private class AnimatorRunnable implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				int skip = 1;
				int frame = 0;
				long start_time = 0;
				long total_time = 0;
				long frame_time = 0;
				long sleep_time = 0;
				long mean_time = 0;
				final long min_time = (1000 << 16) / movieContext.getFrameRate();
				long max_time = min_time;
				long low_time = max_time / 3;
				long high_time = max_time - (max_time / (skip + 2));
				// long last_time[] = new long[4];
				// int index = 0;
				while (running) {
					start_time = System.currentTimeMillis();
					animatorContext.draw();
					frame_time = (System.currentTimeMillis() - start_time) << 16;
					frame += skip;
					total_time += frame_time;
					mean_time = total_time / frame;
					if ((frame_time >= high_time) || ((mean_time >= high_time) && (skip < 20))) {
						skip += 1;
						max_time += min_time;
						low_time = max_time / 3;
						high_time = max_time - (max_time / (skip + 3));
						if (movieContext.debug() && MovieAnimator.debug) {
							movieContext.println("skip = " + skip);
						}
					}
					else if ((frame_time < low_time) && (mean_time < low_time) && (skip > 1)) {
						skip -= 1;
						max_time -= min_time;
						low_time = max_time / 3;
						high_time = max_time - (max_time / (skip + 3));
						if (movieContext.debug() && MovieAnimator.debug) {
							movieContext.println("skip = " + skip);
						}
					}
					frame_time = (System.currentTimeMillis() - start_time) << 16;
					// last_time[(index + 1) & 0x03] = (System.currentTimeMillis() - start_time) << 16;
					// sleep_time = max_time - ((last_time[0] + last_time[1] + last_time[2] + last_time[3] + 1) >> 2);
					if (movieContext.debug() && MovieAnimator.debug) {
						movieContext.println("mean time = " + (mean_time >> 16));
					}
					sleep_time = max_time - frame_time;
					if (sleep_time > 0) {
						Thread.sleep(sleep_time >> 16);
					}
					else {
						Thread.yield();
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
