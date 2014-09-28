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
public class RenderProfile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String profileName;
	private String clipName;
	private int imageWidth;
	private int imageHeight;
	private int offsetX;
	private int offsetY;
	private int frameRate;
	private int startTime;
	private int stopTime;
	private int quality;

	/**
	 * @return the name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the name to set
	 */
	public void setProfileName(final String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the name
	 */
	public String getClipName() {
		return clipName;
	}

	/**
	 * @param clipName the name to set
	 */
	public void setClipName(final String clipName) {
		this.clipName = clipName;
	}

	/**
	 * @return the frameRate
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * @param frameRate the frameRate to set
	 */
	public void setFrameRate(final int frameRate) {
		this.frameRate = frameRate;
	}

	/**
	 * @return the width
	 */
	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param width the width to set
	 */
	public void setImageWidth(final int width) {
		imageWidth = width;
	}

	/**
	 * @return the height
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param height the height to set
	 */
	public void setImageHeight(final int height) {
		imageHeight = height;
	}

	/**
	 * @return the quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(final int quality) {
		this.quality = quality;
	}

	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(final int startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the stopTime
	 */
	public int getStopTime() {
		return stopTime;
	}

	/**
	 * @param stopTime the stopTime to set
	 */
	public void setStopTime(final int stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return the offsetX
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @param offsetX the offsetX to set
	 */
	public void setOffsetX(final int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @return the offsetY
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @param offsetY the offsetY to set
	 */
	public void setOffsetY(final int offsetY) {
		this.offsetY = offsetY;
	}
}
