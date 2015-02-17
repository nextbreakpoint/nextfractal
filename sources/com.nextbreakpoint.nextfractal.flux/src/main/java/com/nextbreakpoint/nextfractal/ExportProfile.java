package com.nextbreakpoint.nextfractal;

import java.io.File;

public class ExportProfile {
	private File tmpFile;
	private float quality;
	private float frameRate;
	private int frameNumber;
	private int frameWidth;
	private int frameHeight;
	private int tileWidth;
	private int tileHeight;
	private int tileOffsetX;
	private int tileOffsetY;
	private int tileBorderWidth;
	private int tileBorderHeight;
	private Object data;

	public float getQuality() {
		return quality;
	}

	public void setQuality(float quality) {
		this.quality = quality;
	}

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

	public int getTileBorderWidth() {
		return tileBorderWidth;
	}

	public void setTileBorderWidth(int tileBorderWidth) {
		this.tileBorderWidth = tileBorderWidth;
	}

	public int getTileBorderHeight() {
		return tileBorderHeight;
	}

	public void setTileBorderHeight(int tileBorderHeight) {
		this.tileBorderHeight = tileBorderHeight;
	}

	public File getTmpFile() {
		return tmpFile;
	}

	public void setTmpFile(File tmpFile) {
		this.tmpFile = tmpFile;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public double getStartTime() {
		return 0;
	}

	public double getStopTime() {
		return 0;
	}
}
