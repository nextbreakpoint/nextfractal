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

/**
 * @author Andrea Medeghini
 */
public class RenderJob implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clipName;
	private String profileName;
	private int imageWidth;
	private int imageHeight;
	private int frameRate;
	private int startTime;
	private int stopTime;
	private int quality;
	private int frameNumber;
	private int tileWidth;
	private int tileHeight;
	private int borderWidth;
	private int borderHeight;
	private int tileOffsetX;
	private int tileOffsetY;

	/**
	 * @param tileBorderWidth the tileBorderWidth to set
	 */
	public void setBorderWidth(final int tileBorderWidth) {
		borderWidth = tileBorderWidth;
	}

	/**
	 * @param tileBorderHeight the tileBorderHeight to set
	 */
	public void setBorderHeight(final int tileBorderHeight) {
		borderHeight = tileBorderHeight;
	}

	/**
	 * @param tileWidth the tileWidth to set
	 */
	public void setTileWidth(final int tileWidth) {
		this.tileWidth = tileWidth;
	}

	/**
	 * @param tileHeight the tileHeight to set
	 */
	public void setTileHeight(final int tileHeight) {
		this.tileHeight = tileHeight;
	}

	/**
	 * @param tileOffsetX the tileOffsetX to set
	 */
	public void setTileOffsetX(final int tileOffsetX) {
		this.tileOffsetX = tileOffsetX;
	}

	/**
	 * @param tileOffsetY the tileOffsetY to set
	 */
	public void setTileOffsetY(final int tileOffsetY) {
		this.tileOffsetY = tileOffsetY;
	}

	/**
	 * @return the tileBorderWidth
	 */
	public int getBorderWidth() {
		return borderWidth;
	}

	/**
	 * @return the tileBorderHeight
	 */
	public int getBorderHeight() {
		return borderHeight;
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
	 * @return the frame
	 */
	public int getFrameNumber() {
		return frameNumber;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrameNumber(final int frame) {
		frameNumber = frame;
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
	 * @param imageWidth the width to set
	 */
	public void setImageWidth(final int imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the height
	 */
	public int getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the height to set
	 */
	public void setImageHeight(final int imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the name
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param name the name to set
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
}
