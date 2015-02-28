package com.nextbreakpoint.nextfractal;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportProfile {
	private float quality;
	private float frameRate;
	private int frameNumber;
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
	private Object data;

	public float getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(float frameRate) {
		this.frameRate = frameRate;
	}

	public int getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public int getTileOffsetX() {
		return tileOffsetX;
	}

	public void setTileOffsetX(int tileOffsetX) {
		this.tileOffsetX = tileOffsetX;
	}

	public int getTileOffsetY() {
		return tileOffsetY;
	}

	public void setTileOffsetY(int tileOffsetY) {
		this.tileOffsetY = tileOffsetY;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

	public int getBorderHeight() {
		return borderHeight;
	}

	public void setBorderHeight(int borderHeight) {
		this.borderHeight = borderHeight;
	}

	public float getQuality() {
		return quality;
	}

	public void setQuality(float quality) {
		this.quality = quality;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public float getStartTime() {
		return startTime;
	}

	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	public float getStopTime() {
		return stopTime;
	}

	public void setStopTime(float stopTime) {
		this.stopTime = stopTime;
	}

	public RendererTile createTile() {
		RendererSize imageSize = new RendererSize(frameWidth, frameHeight);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(borderWidth, borderHeight);
		RendererPoint tileOffset = new RendererPoint(tileOffsetX, tileOffsetY);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}

	@Override
	public String toString() {
		return "[pluginId=" + pluginId + ", frameRate="	+ frameRate + ", frameNumber=" + frameNumber + ", frameWidth=" + frameWidth + ", frameHeight=" + frameHeight + ", tileWidth=" + tileWidth + ", tileHeight=" + tileHeight 
				+ ", tileOffsetX=" + tileOffsetX + ", tileOffsetY=" + tileOffsetY + ", borderWidth=" + borderWidth + ", borderHeight=" + borderHeight + ", quality=" + quality + ", startTime=" + startTime + ", stopTime=" + stopTime + "]";
	}
}
