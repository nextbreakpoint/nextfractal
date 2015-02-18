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
package com.nextbreakpoint.nextfractal.spool.job;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.spool.JobListener;
import com.nextbreakpoint.nextfractal.spool.JobProfile;
import com.nextbreakpoint.nextfractal.spool.SpoolJobInterface;

/**
 * @author Andrea Medeghini
 */
public class SpoolJob implements SpoolJobInterface {
	private final JobListener listener;
	private final String jobId;
	private String remoteJobId;
	private volatile long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;
	private volatile JobProfile profile;
	private volatile byte[] jobData;
	private int firstFrameNumber;
	private final Worker worker;
	private File tmpPath;

	/**
	 * @param service
	 * @param worker
	 * @param jobId
	 * @param listener
	 */
	public SpoolJob(final Worker worker, final String jobId, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
		this.worker = worker;
		this.jobId = jobId;
		this.tmpPath = null;//TODO tmpPath
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

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#setFrameNumber(int)
	 */
	@Override
	public synchronized void setFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		profile.getProfile().setFrameNumber(frameNumber);
		lastUpdate = System.currentTimeMillis();
		worker.addTask(new StatusChangedTask(profile));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#setFirstFrameNumber(int)
	 */
	@Override
	public synchronized void setFirstFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		if (started) {
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

	@Override
	public synchronized byte[] getJobData() {
		return jobData;
	}

	@Override
	public synchronized void setJobData(final byte[] jobData) {
		this.jobData = jobData;
	}

	@Override
	public synchronized RandomAccessFile getRAF() throws IOException {
		if (profile == null) {
			throw new IllegalStateException();
		}
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobProfile()
	 */
	@Override
	public synchronized JobProfile getJobProfile() {
		return profile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.spool.JobInterface#setJobProfile(com.nextbreakpoint.nextfractal.spool.JobProfile)
	 */
	@Override
	public synchronized void setJobProfile(final JobProfile profile) {
		if (started) {
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
			if (!started) {
				lastUpdate = System.currentTimeMillis();
				started = true;
				aborted = false;
				terminated = false;
				worker.addTask(new StartedTask(profile));
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#stop()
	 */
	@Override
	public void stop() {
		synchronized (this) {
			if (profile == null) {
				throw new IllegalStateException();
			}
			started = false;
			if (started) {
				worker.addTask(new StoppedTask(profile));
			}
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
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isCompleted()
	 */
	@Override
	public synchronized boolean isTerminated() {
		return terminated;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.SpoolJobInterface.spool.DistributedSpoolJobInterface#getRemoteJobId()
	 */
	@Override
	public synchronized String getRemoteJobId() {
		return remoteJobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.SpoolJobInterface.spool.DistributedSpoolJobInterface#setRemoteJobId(java.lang.String)
	 */
	@Override
	public synchronized void setRemoteJobId(final String remoteJobId) {
		this.remoteJobId = remoteJobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.SpoolJobInterface.spool.DistributedSpoolJobInterface#getTotalFrames()
	 */
	@Override
	public synchronized int getTotalFrames() {
		if (profile == null) {
			throw new IllegalStateException();
		}
		return (int)Math.floor(profile.getProfile().getFrameRate() > 0 ? ((profile.getProfile().getStopTime() - profile.getProfile().getStartTime()) * profile.getProfile().getFrameRate()) : 1);
	}

	/**
	 * 
	 */
	@Override
	public synchronized void terminate() {
		if (profile == null) {
			throw new IllegalStateException();
		}
		terminated = true;
		aborted = false;
		worker.addTask(new TerminatedTask(profile));
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
