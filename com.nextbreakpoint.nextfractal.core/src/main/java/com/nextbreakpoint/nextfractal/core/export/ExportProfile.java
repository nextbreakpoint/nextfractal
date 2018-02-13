/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.core.export;

import com.nextbreakpoint.nextfractal.core.Metadata;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

public class ExportProfile {
	private float quality;
	private float frameRate;
	private int frameWidth;
	private int frameHeight;
	private int tileWidth;
	private int tileHeight;
	private int tileOffsetX;
	private int tileOffsetY;
	private int borderWidth;
	private int borderHeight;
	private float startTime;
	private float stopTime;
	private String pluginId;
	private String script;
	private Metadata metadata;

	ExportProfile(float quality, float frameRate, int frameWidth, int frameHeight, int tileWidth, int tileHeight, int tileOffsetX, int tileOffsetY, int borderWidth, int borderHeight, float startTime, float stopTime, String pluginId, String script, Metadata metadata) {
		this.quality = quality;
		this.frameRate = frameRate;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tileOffsetX = tileOffsetX;
		this.tileOffsetY = tileOffsetY;
		this.borderWidth = borderWidth;
		this.borderHeight = borderHeight;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.pluginId = pluginId;
		this.script = script;
		this.metadata = metadata;
	}

	public float getQuality() {
		return quality;
	}

	public float getFrameRate() {
		return frameRate;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileOffsetX() {
		return tileOffsetX;
	}

	public int getTileOffsetY() {
		return tileOffsetY;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public int getBorderHeight() {
		return borderHeight;
	}

	public float getStartTime() {
		return startTime;
	}

	public float getStopTime() {
		return stopTime;
	}

	public String getPluginId() {
		return pluginId;
	}

	public String getScript() {
		return script;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public RendererTile createTile() {
		RendererSize imageSize = new RendererSize(frameWidth, frameHeight);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(borderWidth, borderHeight);
		RendererPoint tileOffset = new RendererPoint(tileOffsetX, tileOffsetY);
		return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
	}

	@Override
	public String toString() {
		return "[pluginId=" + pluginId + ", frameRate=" + frameRate + ", frameWidth=" + frameWidth + ", frameHeight=" + frameHeight + ", tileWidth=" + tileWidth + ", tileHeight=" + tileHeight
				+ ", tileOffsetX=" + tileOffsetX + ", tileOffsetY=" + tileOffsetY + ", borderWidth=" + borderWidth + ", borderHeight=" + borderHeight + ", quality=" + quality + ", startTime=" + startTime + ", stopTime=" + stopTime + "]";
	}
}
