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
package com.nextbreakpoint.nextfractal.spool.jobservice;

import java.io.IOException;

import com.nextbreakpoint.nextfractal.core.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.spool.RemoteJobInterface;
import com.nextbreakpoint.nextfractal.spool.JobFactory;
import com.nextbreakpoint.nextfractal.spool.JobService;
import com.nextbreakpoint.nextfractal.spool.store.StoreData;

/**
 * @author Andrea Medeghini
 */
public class RemoteJobService<T extends RemoteJobInterface> extends DefaultJobService<T> implements JobService<T> {
	/**
	 * @param serviceId
	 * @param serviceName
	 * @param jobFactory
	 * @param worker
	 */
	public RemoteJobService(final int serviceId, final String serviceName, final JobFactory<T> jobFactory, final Worker worker) {
		super(serviceId, serviceName, jobFactory, worker);
	}

	public void setJobFrame(final String jobId, final StoreData spoolData, final byte[] data) throws IOException {
		synchronized (spooledJobs) {
			ScheduledJob job = spooledJobs.get(jobId);
			if (job != null) {
				job.getJob().setStoreData(spoolData);
				job.getJob().setJobData(data);
			}
		}
	}

	public byte[] getJobFrame(final String jobId, final int frameNumber) throws IOException {
		synchronized (spooledJobs) {
			ScheduledJob job = spooledJobs.get(jobId);
			if (job != null) {
				final int tw = job.getJob().getJobDataRow().getTileWidth();
				final int th = job.getJob().getJobDataRow().getTileHeight();
				final int bw = job.getJob().getJobDataRow().getBorderWidth();
				final int bh = job.getJob().getJobDataRow().getBorderHeight();
				final int sw = tw + 2 * bw;
				final int sh = th + 2 * bh;
				final byte[] data = new byte[sw * sh * 4];
				ChunkedRandomAccessFile raf = null;
				try {
					if (job.getJob().getFirstFrameNumber() > 0) {
						final long pos = (frameNumber - job.getJob().getFirstFrameNumber() - 1) * sw * sh * 4;
						raf = job.getJob().getRAF();
						raf.seek(pos);
						raf.readFully(data);
					}
					else {
						final long pos = frameNumber * sw * sh * 4;
						raf = job.getJob().getRAF();
						raf.seek(pos);
						raf.readFully(data);
					}
				}
				catch (final IOException e) {
					// System.out.println("firstFrame = " + job.getFirstFrameNumber() + ", frame = " + frameNumber + ", length = " + raf.length() / (sw * sh * 4));
					e.printStackTrace();
				}
				finally {
					if (raf != null) {
						try {
							raf.close();
						}
						catch (final IOException e) {
						}
					}
				}
				return data;
			}
		}
		return null;
	}
}
