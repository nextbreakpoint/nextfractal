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

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public class JobData implements Serializable {
	public static final int PROCESS_JOB = 0;
	public static final int COPY_PROCESS_JOB = 1;
	public static final int POST_PROCESS_JOB = 2;
	private static final long serialVersionUID = 1L;
	private final int jobId;
	private final int profileId;
	private final int clipId;
	private final int imageWidth;
	private final int imageHeight;
	private final int frameRate;
	private final int startTime;
	private final int stopTime;
	private final int quality;
	private final int frameNumber;
	private final int tileWidth;
	private final int tileHeight;
	private final int borderWidth;
	private final int borderHeight;
	private final int tileOffsetX;
	private final int tileOffsetY;
	private final int jobType;

	/**
	 * @param jobData
	 */
	public JobData(final JobData jobData) {
		jobId = jobData.getJobId();
		profileId = jobData.getProfileId();
		clipId = jobData.getClipId();
		imageWidth = jobData.getImageWidth();
		imageHeight = jobData.getImageHeight();
		frameRate = jobData.getFrameRate();
		startTime = jobData.getStartTime();
		stopTime = jobData.getStopTime();
		quality = jobData.getQuality();
		frameNumber = jobData.getFrameNumber();
		tileWidth = jobData.getTileWidth();
		tileHeight = jobData.getTileHeight();
		borderWidth = jobData.getBorderWidth();
		borderHeight = jobData.getBorderHeight();
		tileOffsetX = jobData.getTileOffsetX();
		tileOffsetY = jobData.getTileOffsetY();
		jobType = jobData.getJobType();
	}

	/**
	 * @return the jobId
	 */
	public int getJobId() {
		return jobId;
	}

	/**
	 * @return the profileId
	 */
	public int getProfileId() {
		return profileId;
	}

	/**
	 * @return the clipId
	 */
	public int getClipId() {
		return clipId;
	}

	/**
	 * @return the imageWidth
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @return the frameRate
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @return the stopTime
	 */
	public int getStopTime() {
		return stopTime;
	}

	/**
	 * @return the quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * @return the frameNumber
	 */
	public int getFrameNumber() {
		return frameNumber;
	}

	/**
	 * @return the tileWidth
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * @return the tileHeight
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * @return the borderWidth
	 */
	public int getBorderWidth() {
		return borderWidth;
	}

	/**
	 * @return the borderHeight
	 */
	public int getBorderHeight() {
		return borderHeight;
	}

	/**
	 * @return the tileOffsetX
	 */
	public int getTileOffsetX() {
		return tileOffsetX;
	}

	/**
	 * @return the tileOffsetY
	 */
	public int getTileOffsetY() {
		return tileOffsetY;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#setFrameNumber(int)
	 */
	public void setFrameNumber(final int frame) {
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[jobId = ");
		builder.append(jobId);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getJobType()
	 */
	public int getJobType() {
		return jobType;
	}
}
