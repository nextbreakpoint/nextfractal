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
package com.nextbreakpoint.nextfractal.queue.profile;

import java.io.Serializable;

/**
 * @author Andrea Medeghini
 */
public final class RenderProfileDataRow implements Serializable {
	private static final long serialVersionUID = 1L;
	private int profileId;
	private int clipId;
	private int status;
	private int jobCreated;
	private int jobStored;
	private int totalFrames;
	private int jobFrame;
	private final RenderProfile profile;

	/**
	 * @param profile
	 */
	public RenderProfileDataRow(final RenderProfile profile) {
		this.profile = profile;
	}

	/**
	 * @return the id
	 */
	public int getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId the id to set
	 */
	public void setProfileId(final int profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the clipId
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
	 * @return the jobStored
	 */
	public int getJobStored() {
		return jobStored;
	}

	/**
	 * @param jobStored the jobStored to set
	 */
	public void setJobStored(final int jobStored) {
		this.jobStored = jobStored;
	}

	/**
	 * @return the jobCreated
	 */
	public int getJobCreated() {
		return jobCreated;
	}

	/**
	 * @param jobCreated the jobCreated to set
	 */
	public void setJobCreated(final int jobCreated) {
		this.jobCreated = jobCreated;
	}

	/**
	 * @return the jobFrame
	 */
	public int getJobFrame() {
		return jobFrame;
	}

	/**
	 * @param jobFrame the jobFrame to set
	 */
	public void setJobFrame(final int jobFrame) {
		this.jobFrame = jobFrame;
	}

	/**
	 * @return the lastFrame
	 */
	public int getTotalFrames() {
		return totalFrames;
	}

	/**
	 * @param lastFrame the lastFrame to set
	 */
	public void setTotalFrames(final int lastFrame) {
		totalFrames = lastFrame;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RenderProfile [profileId = ");
		builder.append(profileId);
		builder.append(", clipId = ");
		builder.append(clipId);
		builder.append(", name = ");
		builder.append(getProfile().getProfileName());
		builder.append(", imageWidth = ");
		builder.append(getProfile().getImageWidth());
		builder.append(", imageHeight = ");
		builder.append(getProfile().getImageHeight());
		builder.append(", frameRate = ");
		builder.append(getProfile().getFrameRate());
		builder.append(", startTime = ");
		builder.append(getProfile().getStartTime());
		builder.append(", stopTime = ");
		builder.append(getProfile().getStopTime());
		builder.append(", quality = ");
		builder.append(getProfile().getQuality());
		builder.append(", status = ");
		builder.append(getStatus());
		builder.append(", totalFrames = ");
		builder.append(getTotalFrames());
		builder.append(", jobFrame = ");
		builder.append(getJobFrame());
		builder.append(", jobCreated = ");
		builder.append(getJobCreated());
		builder.append(", jobStored = ");
		builder.append(getJobStored());
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
		return getProfileId() == ((RenderProfileDataRow) obj).getProfileId();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getProfileId();
	}

	/**
	 * @return the profile
	 */
	public RenderProfile getProfile() {
		return profile;
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getClipName()
	 */
	public String getClipName() {
		return profile.getClipName();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getFrameRate()
	 */
	public int getFrameRate() {
		return profile.getFrameRate();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getImageHeight()
	 */
	public int getImageHeight() {
		return profile.getImageHeight();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getImageWidth()
	 */
	public int getImageWidth() {
		return profile.getImageWidth();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getOffsetX()
	 */
	public int getOffsetX() {
		return profile.getOffsetX();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getOffsetY()
	 */
	public int getOffsetY() {
		return profile.getOffsetY();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getProfileName()
	 */
	public String getProfileName() {
		return profile.getProfileName();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getQuality()
	 */
	public int getQuality() {
		return profile.getQuality();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getStartTime()
	 */
	public int getStartTime() {
		return profile.getStartTime();
	}

	/**
	 * @return
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#getStopTime()
	 */
	public int getStopTime() {
		return profile.getStopTime();
	}

	/**
	 * @param clipName
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setClipName(java.lang.String)
	 */
	public void setClipName(final String clipName) {
		profile.setClipName(clipName);
	}

	/**
	 * @param frameRate
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setFrameRate(int)
	 */
	public void setFrameRate(final int frameRate) {
		profile.setFrameRate(frameRate);
	}

	/**
	 * @param height
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setImageHeight(int)
	 */
	public void setImageHeight(final int height) {
		profile.setImageHeight(height);
	}

	/**
	 * @param width
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setImageWidth(int)
	 */
	public void setImageWidth(final int width) {
		profile.setImageWidth(width);
	}

	/**
	 * @param offsetX
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setOffsetX(int)
	 */
	public void setOffsetX(final int offsetX) {
		profile.setOffsetX(offsetX);
	}

	/**
	 * @param offsetY
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setOffsetY(int)
	 */
	public void setOffsetY(final int offsetY) {
		profile.setOffsetY(offsetY);
	}

	/**
	 * @param profileName
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setProfileName(java.lang.String)
	 */
	public void setProfileName(final String profileName) {
		profile.setProfileName(profileName);
	}

	/**
	 * @param quality
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setQuality(int)
	 */
	public void setQuality(final int quality) {
		profile.setQuality(quality);
	}

	/**
	 * @param startTime
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setStartTime(int)
	 */
	public void setStartTime(final int startTime) {
		profile.setStartTime(startTime);
	}

	/**
	 * @param stopTime
	 * @see com.nextbreakpoint.nextfractal.queue.profile.RenderProfile#setStopTime(int)
	 */
	public void setStopTime(final int stopTime) {
		profile.setStopTime(stopTime);
	}
}
