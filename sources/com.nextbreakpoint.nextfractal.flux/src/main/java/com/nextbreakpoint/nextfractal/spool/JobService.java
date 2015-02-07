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
package com.nextbreakpoint.nextfractal.spool;

/**
 * @author Andrea Medeghini
 */
public interface JobService<T extends JobInterface> {
	/**
	 * @return
	 */
	public String getName();

	/**
	 * 
	 */
	public void start();

	/**
	 * 
	 */
	public void stop();

	/**
	 * @return
	 */
	public int getJobCount();

	/**
	 * @param jobId
	 */
	public void runJob(String jobId);

	/**
	 * @param jobId
	 * @param jobData
	 * @param frameNumber
	 */
	public void setJobData(String jobId, JobData jobData, int frameNumber);

	/**
	 * @param listener
	 * @return
	 */
	public String createJob(JobListener listener);

	/**
	 * @param jobId
	 */
	public void deleteJob(String jobId);

	/**
	 * @param jobId
	 */
	public void stopJob(String jobId);

	/**
	 * @param jobId
	 */
	public void abortJob(String jobId);

	/**
	 * @param listener
	 */
	public void addServiceListener(JobServiceListener listener);

	/**
	 * @param listener
	 */
	public void removeServiceListener(JobServiceListener listener);
}
