/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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
package com.nextbreakpoint.nextfractal.queue.job;

import java.io.Serializable;

import com.nextbreakpoint.nextfractal.queue.spool.JobData;

/**
 * @author Andrea Medeghini
 */
public final class RenderJobDataRow implements Serializable, JobData {
	private static final long serialVersionUID = 1L;
	private int jobId;
	private int profileId;
	private int clipId;
	private int status;
	private int type;
	private final RenderJob job;

	/**
	 * @param job
	 */
	public RenderJobDataRow(final RenderJob job) {
		this.job = job;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getJobId()
	 */
	public int getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the id to set
	 */
	public void setJobId(final int jobId) {
		this.jobId = jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getProfileId()
	 */
	public int getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(final int profileId) {
		this.profileId = profileId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getClipId()
	 */
	public int getClipId() {
		return clipId;
	}

	/**
	 * @param clipId the clipId to set
	 */
	public void setClipId(final int clipId) {
		this.clipId = clipId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(final int status) {
		this.status = status;
	}

	/**
	 * @param type the type to set
	 */
	public void setJobType(final int type) {
		this.type = type;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RenderJob [jobId = ");
		builder.append(jobId);
		builder.append(", profileId = ");
		builder.append(getProfileId());
		builder.append(", clipId = ");
		builder.append(getClipId());
		builder.append(", name = ");
		builder.append(getJob().getProfileName());
		builder.append(", frameNumber = ");
		builder.append(getJob().getFrameNumber());
		builder.append(", tileWidth = ");
		builder.append(getJob().getTileWidth());
		builder.append(", tileHeight = ");
		builder.append(getJob().getTileHeight());
		builder.append(", tileOffsetX = ");
		builder.append(getJob().getTileOffsetX());
		builder.append(", tileOffsetY = ");
		builder.append(getJob().getTileOffsetY());
		builder.append(", borderWidth = ");
		builder.append(getJob().getBorderWidth());
		builder.append(", borderHeight = ");
		builder.append(getJob().getBorderHeight());
		builder.append(", status = ");
		builder.append(getStatus());
		builder.append(", type = ");
		builder.append(getJobType());
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		return getJobId() == ((RenderJobDataRow) obj).getJobId();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getJobId();
	}

	/**
	 * @return the job
	 */
	public RenderJob getJob() {
		return job;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getBorderHeight()
	 */
	public int getBorderHeight() {
		return job.getBorderHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getBorderWidth()
	 */
	public int getBorderWidth() {
		return job.getBorderWidth();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#getClipName()
	 */
	public String getClipName() {
		return job.getClipName();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getFrameNumber()
	 */
	public int getFrameNumber() {
		return job.getFrameNumber();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getFrameRate()
	 */
	public int getFrameRate() {
		return job.getFrameRate();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getImageHeight()
	 */
	public int getImageHeight() {
		return job.getImageHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getImageWidth()
	 */
	public int getImageWidth() {
		return job.getImageWidth();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#getProfileName()
	 */
	public String getProfileName() {
		return job.getProfileName();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getQuality()
	 */
	public int getQuality() {
		return job.getQuality();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getStartTime()
	 */
	public int getStartTime() {
		return job.getStartTime();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getStopTime()
	 */
	public int getStopTime() {
		return job.getStopTime();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getTileHeight()
	 */
	public int getTileHeight() {
		return job.getTileHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getTileOffsetX()
	 */
	public int getTileOffsetX() {
		return job.getTileOffsetX();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getTileOffsetY()
	 */
	public int getTileOffsetY() {
		return job.getTileOffsetY();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getTileWidth()
	 */
	public int getTileWidth() {
		return job.getTileWidth();
	}

	/**
	 * @param tileBorderHeight
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setBorderHeight(int)
	 */
	public void setBorderHeight(final int tileBorderHeight) {
		job.setBorderHeight(tileBorderHeight);
	}

	/**
	 * @param tileBorderWidth
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setBorderWidth(int)
	 */
	public void setBorderWidth(final int tileBorderWidth) {
		job.setBorderWidth(tileBorderWidth);
	}

	/**
	 * @param clipName
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setClipName(java.lang.String)
	 */
	public void setClipName(final String clipName) {
		job.setClipName(clipName);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#setFrameNumber(int)
	 */
	public void setFrameNumber(final int frame) {
		job.setFrameNumber(frame);
	}

	/**
	 * @param frameRate
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setFrameRate(int)
	 */
	public void setFrameRate(final int frameRate) {
		job.setFrameRate(frameRate);
	}

	/**
	 * @param imageHeight
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setImageHeight(int)
	 */
	public void setImageHeight(final int imageHeight) {
		job.setImageHeight(imageHeight);
	}

	/**
	 * @param imageWidth
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setImageWidth(int)
	 */
	public void setImageWidth(final int imageWidth) {
		job.setImageWidth(imageWidth);
	}

	/**
	 * @param profileName
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setProfileName(java.lang.String)
	 */
	public void setProfileName(final String profileName) {
		job.setProfileName(profileName);
	}

	/**
	 * @param quality
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setQuality(int)
	 */
	public void setQuality(final int quality) {
		job.setQuality(quality);
	}

	/**
	 * @param startTime
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setStartTime(int)
	 */
	public void setStartTime(final int startTime) {
		job.setStartTime(startTime);
	}

	/**
	 * @param stopTime
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setStopTime(int)
	 */
	public void setStopTime(final int stopTime) {
		job.setStopTime(stopTime);
	}

	/**
	 * @param tileHeight
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setTileHeight(int)
	 */
	public void setTileHeight(final int tileHeight) {
		job.setTileHeight(tileHeight);
	}

	/**
	 * @param tileOffsetX
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setTileOffsetX(int)
	 */
	public void setTileOffsetX(final int tileOffsetX) {
		job.setTileOffsetX(tileOffsetX);
	}

	/**
	 * @param tileOffsetY
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setTileOffsetY(int)
	 */
	public void setTileOffsetY(final int tileOffsetY) {
		job.setTileOffsetY(tileOffsetY);
	}

	/**
	 * @param tileWidth
	 * @see com.nextbreakpoint.nextfractal.queue.job.RenderJob#setTileWidth(int)
	 */
	public void setTileWidth(final int tileWidth) {
		job.setTileWidth(tileWidth);
	}

	/**
	 * @return
	 */
	public boolean isPostProcess() {
		return type == JobData.POST_PROCESS_JOB;
	}

	/**
	 * @return
	 */
	public boolean isCopyProcess() {
		return type == JobData.COPY_PROCESS_JOB;
	}

	/**
	 * @return
	 */
	public boolean isProcess() {
		return type == JobData.PROCESS_JOB;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobData#getJobType()
	 */
	public int getJobType() {
		return type;
	}
}
