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
package com.nextbreakpoint.nextfractal.test;

import java.io.IOException;

import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.queue.spool.JobListener;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;

/**
 * @author Andrea Medeghini
 */
public class DummyJob implements DistributedJobInterface {
	private final JobListener listener;
	private String jobId;
	private JobData jobDataRow;
	private int frameNumber;
	private byte[] dataIn;
	private final byte[] dataOut;
	private long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;

	/**
	 * @param dataOut
	 * @param dataIn
	 * @param listener
	 */
	public DummyJob(final byte[] dataIn, final byte[] dataOut, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
		this.dataIn = dataIn;
		this.dataOut = dataOut;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#setJobId(java.lang.String)
	 */
	public synchronized void setJobId(final String id) {
		jobId = id;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobId()
	 */
	@Override
	public synchronized String getJobId() {
		return jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFrameNumber()
	 */
	@Override
	public synchronized int getFrameNumber() {
		return frameNumber;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#setFrameNumber(int)
	 */
	public void setFrameNumber(final int frameNumber) {
		synchronized (this) {
			this.frameNumber = frameNumber;
			lastUpdate = System.currentTimeMillis();
		}
		listener.updated(jobId, jobDataRow);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#writeData(byte[])
	 */
	public synchronized void writeData(final byte[] data) throws IOException {
		dataIn = new byte[data.length];
		System.arraycopy(data, 0, dataIn, 0, data.length);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#readData()
	 */
	public synchronized byte[] readData() throws IOException {
		final byte[] data = new byte[dataOut.length];
		System.arraycopy(dataOut, 0, data, 0, data.length);
		return data;
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
		builder.append(frameNumber);
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
	public synchronized void reset() {
		started = false;
		aborted = false;
		terminated = false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#start()
	 */
	@Override
	public void start() {
		synchronized (this) {
			lastUpdate = System.currentTimeMillis();
			started = true;
			aborted = false;
			terminated = false;
		}
		listener.started(jobId, jobDataRow);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#stop()
	 */
	@Override
	public void stop() {
		synchronized (this) {
			started = false;
			terminated = true;
		}
		listener.stopped(jobId, jobDataRow);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#abort()
	 */
	@Override
	public void abort() {
		synchronized (this) {
			started = false;
			aborted = true;
			terminated = true;
		}
		listener.stopped(jobId, jobDataRow);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#dispose()
	 */
	@Override
	public synchronized void dispose() {
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

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#getClip()
	 */
	@Override
	public TwisterClip getClip() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#getJobDataRow()
	 */
	@Override
	public JobData getJobDataRow() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#setClip(com.nextbreakpoint.nextfractal.twister.TwisterClip)
	 */
	@Override
	public void setClip(final TwisterClip clip) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#setJobDataRow(JobData)
	 */
	@Override
	public void setJobDataRow(final JobData job) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#getJobData()
	 */
	@Override
	public byte[] getJobData() {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#setJobData(byte[])
	 */
	@Override
	public void setJobData(final byte[] jobData) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#getRAF()
	 */
	@Override
	public ChunkedRandomAccessFile getRAF() throws IOException {
		return null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#setFirstFrameNumber(int)
	 */
	@Override
	public void setFirstFrameNumber(final int frameNumber) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.DistributedJobInterface#getFirstFrameNumber()
	 */
	@Override
	public int getFirstFrameNumber() {
		return 0;
	}
}
