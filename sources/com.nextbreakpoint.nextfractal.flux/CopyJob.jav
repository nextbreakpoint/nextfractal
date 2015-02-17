/*
Ø * NextFractal 7.0 
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
package com.nextbreakpoint.nextfractal.spool.job;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.spool.JobInterface;
import com.nextbreakpoint.nextfractal.spool.JobListener;
import com.nextbreakpoint.nextfractal.spool.JobProfile;

/**
 * @author Andrea Medeghini
 */
public class CopyJob implements JobInterface {
	private static final ThreadFactory factory = new DefaultThreadFactory("CopyJob Task", true, Thread.MIN_PRIORITY);
	private final JobListener listener;
	private final String jobId;
	private volatile long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;
	private volatile JobProfile profile;
	private volatile Thread thread;
	private int firstFrameNumber;
	private final Worker worker;

	/**
	 * @param service
	 * @param worker
	 * @param jobId
	 * @param listener
	 */
	public CopyJob(final Worker worker, final String jobId, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
		this.worker = worker;
		this.jobId = jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobId()
	 */
	@Override
	public String getJobId() {
		return jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFrameNumber()
	 */
	@Override
	public synchronized int getFrameNumber() {
		if (profile == null) {
			throw new IllegalStateException();
		}
		return profile.getProfile().getFrameNumber();
	}

	private void setFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		profile.getProfile().setFrameNumber(frameNumber);
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#setFirstFrameNumber(int)
	 */
	@Override
	public synchronized void setFirstFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		if (thread != null) {
			throw new IllegalStateException();
		}
		firstFrameNumber = frameNumber;
		profile.getProfile().setFrameNumber(frameNumber);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFirstFrameNumber()
	 */
	@Override
	public synchronized int getFirstFrameNumber() {
		return firstFrameNumber;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobProfile()
	 */
	@Override
	public synchronized JobProfile getJobProfile() {
		return profile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#setJobProfile(JobProfile)
	 */
	@Override
	public synchronized void setJobProfile(final JobProfile profile) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		if (profile == null) {
			throw new IllegalArgumentException();
		}
		this.profile = profile;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public synchronized String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("id = ");
		builder.append(jobId);
		builder.append(", frameNumber = ");
		builder.append(profile != null ? profile.getProfile().getFrameNumber() : "N/A");
		return builder.toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getLastUpdate()
	 */
	@Override
	public synchronized long getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#reset()
	 */
	@Override
	public void reset() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			started = false;
			aborted = false;
			terminated = false;
			lastUpdate = System.currentTimeMillis();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#start()
	 */
	@Override
	public void start() {
		synchronized (this) {
			if (profile == null) {
				throw new IllegalStateException();
			}
			if (thread == null) {
				lastUpdate = System.currentTimeMillis();
				started = true;
				aborted = false;
				terminated = false;
				worker.addTask(new StartedTask(profile));
				thread = factory.newThread(new RenderTask());
				thread.start();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#stop()
	 */
	@Override
	public void stop() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			if (profile == null) {
				throw new IllegalStateException();
			}
			started = false;
			thread = null;
			worker.addTask(new StoppedTask(profile));
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#abort()
	 */
	@Override
	public synchronized void abort() {
		aborted = true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#dispose()
	 */
	@Override
	public synchronized void dispose() {
		if (profile != null) {
			worker.addTask(new DisposedTask(profile));
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isStarted()
	 */
	@Override
	public synchronized boolean isStarted() {
		return started;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isAborted()
	 */
	@Override
	public synchronized boolean isAborted() {
		return aborted;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isTerminated()
	 */
	@Override
	public synchronized boolean isTerminated() {
		return terminated;
	}

	private class RenderTask implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			ChunkedRandomAccessFile jobRaf = null;
			ChunkedRandomAccessFile profileRaf = null;
			try {
				final int frameCount = (int)Math.floor((profile.getProfile().getStopTime() - profile.getProfile().getStartTime()) * profile.getProfile().getFrameRate());
				if (frameCount == 0) {
					final int tx = profile.getProfile().getTileOffsetX();
					final int ty = profile.getProfile().getTileOffsetY();
					final int tw = profile.getProfile().getTileWidth();
					final int th = profile.getProfile().getTileHeight();
					final int bw = profile.getProfile().getTileBorderWidth();
					final int bh = profile.getProfile().getTileBorderHeight();
					final int iw = profile.getProfile().getFrameWidth();
					final int sw = tw + 2 * bw;
					final int sh = th + 2 * bh;
					final byte[] data = new byte[sw * sh * 4];
					jobRaf = service.getJobRandomAccessFile(profile.getJobId());
					jobRaf.readFully(data);
					profileRaf = service.getProfileRandomAccessFile(profile.getJobId());
					long pos = (ty * iw + tx) * 4;
					for (int j = sw * bh * 4 + bw * 4, k = 0; k < th; k++) {
						profileRaf.seek(pos);
						profileRaf.write(data, j, tw * 4);
						j += sw * 4;
						pos += iw * 4;
						Thread.yield();
					}
					setFrameNumber(0);
					worker.addTask(new StatusChangedTask(profile));
				}
				else if (profile.getProfile().getFrameNumber() < frameCount) {
					final int tx = profile.getProfile().getTileOffsetX();
					final int ty = profile.getProfile().getTileOffsetY();
					final int tw = profile.getProfile().getTileWidth();
					final int th = profile.getProfile().getTileHeight();
					final int bw = profile.getProfile().getTileBorderWidth();
					final int bh = profile.getProfile().getTileBorderHeight();
					final int iw = profile.getProfile().getFrameWidth();
					final int ih = profile.getProfile().getFrameHeight();
					final int sw = tw + 2 * bw;
					final int sh = th + 2 * bh;
					final byte[] data = new byte[sw * sh * 4];
					jobRaf = service.getJobRandomAccessFile(profile.getJobId());
					profileRaf = service.getProfileRandomAccessFile(profile.getJobId());
					int startFrameNumber = 0;
					if (profile.getProfile().getFrameNumber() > 0) {
						startFrameNumber = profile.getProfile().getFrameNumber() + 1;
					}
					long pos = (startFrameNumber * sw * sh) * 4;
					jobRaf.seek(pos);
					for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
						jobRaf.readFully(data);
						pos = (frameNumber * iw * ih + ty * iw + tx) * 4;
						for (int j = sw * bh * 4 + bw * 4, k = 0; k < th; k++) {
							profileRaf.seek(pos);
							profileRaf.write(data, j, tw * 4);
							j += sw * 4;
							pos += iw * 4;
							Thread.yield();
						}
						setFrameNumber(frameNumber);
						worker.addTask(new StatusChangedTask(profile));
						if (aborted) {
							break;
						}
					}
				}
			}
			catch (final Throwable e) {
				aborted = true;
				e.printStackTrace();
			}
			finally {
				if (profileRaf != null) {
					try {
						profileRaf.close();
					}
					catch (final IOException e) {
					}
				}
				if (jobRaf != null) {
					try {
						jobRaf.close();
					}
					catch (final IOException e) {
					}
				}
			}
			synchronized (CopyJob.this) {
				if (!aborted) {
					terminated = true;
				}
				if (terminated) {
					worker.addTask(new TerminatedTask(profile));
				}
			}
		}
	}

	private class StatusChangedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StatusChangedTask(final JobProfile jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.updated(jobId, jobData);
		}
	}

	private class StartedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StartedTask(final JobProfile jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.started(jobId, jobData);
		}
	}

	private class StoppedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StoppedTask(final JobProfile jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.stopped(jobId, jobData);
		}
	}

	private class TerminatedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected TerminatedTask(final JobProfile jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.terminated(jobId, jobData);
		}
	}

	private class DisposedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected DisposedTask(final JobProfile jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.disposed(jobId, jobData);
		}
	}
}
