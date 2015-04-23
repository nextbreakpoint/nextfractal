/*
 * NextFractal 1.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

public class ExportJob {
	private final ExportSession session;
	private final ExportProfile profile;
	private volatile Throwable error;
	private volatile ExportJobState state = ExportJobState.READY;

	public ExportJob(ExportSession session, ExportProfile profile) {
		this.session = session;
		this.profile = profile;
	}

	public ExportProfile getProfile() {
		return profile;
	}
	
	public RendererTile getTile() {
		return profile.createTile();
	}

	public String getPluginId() {
		return profile.getPluginId();
	}
	
	public File getFile() {
		return session.getFile();
	}

	public File getTmpFile() {
		return session.getTmpFile();
	}

	public boolean isInterrupted() {
		return state == ExportJobState.INTERRUPTED;
	}

	public boolean isCompleted() {
		return state == ExportJobState.COMPLETED;
	}

	public boolean isReady() {
		return state == ExportJobState.READY;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public ExportJobState getState() {
		return state;
	}

	public void setState(ExportJobState state) {
		this.state = state;
	}

	public void writePixels(RendererSize size, IntBuffer pixels) throws IOException {
		RandomAccessFile raf = null;
		try {
			byte[] data = convertToBytes(size, pixels);
			raf = new RandomAccessFile(this.getTmpFile(), "rw");
			double startTime = this.getProfile().getStartTime();
			double stopTime = this.getProfile().getStopTime();
			float frameRate = this.getProfile().getFrameRate();
			int firstFrame = this.getProfile().getFrameNumber();
			final int frameCount = computeFrameCount(startTime, stopTime, frameRate);
			if (frameCount == 0) {
				writeFrame(raf, 0, size, data);
			} else if (firstFrame > 0 && firstFrame < frameCount) {
				for (int frameNumber = firstFrame; frameNumber < frameCount; frameNumber++) {
					writeFrame(raf, frameNumber, size, data);
				}
			}
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (final IOException e) {
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "[sessionId = " + session.getSessionId() + ", profile=" + profile + "]";
	}

	private int computeFrameCount(double startTime, double stopTime, float frameRate) {
		return (int) Math.floor((stopTime - startTime) * frameRate);
	}

	private void writeFrame(RandomAccessFile raf, int frameNumber, RendererSize size, byte[] data) throws IOException {
		final int sw = size.getWidth();
		final int sh = size.getHeight();
		final int tx = this.getProfile().getTileOffsetX();
		final int ty = this.getProfile().getTileOffsetY();
		final int tw = this.getProfile().getTileWidth();
		final int th = this.getProfile().getTileHeight();
		final int iw = this.getProfile().getFrameWidth();
		final int ih = this.getProfile().getFrameHeight();
		final int ly = Math.min(th, ih - ty);
		final int lx = Math.min(tw, iw - tx);
		long pos = (frameNumber * iw * ih + ty * iw + tx) * 4;
		for (int j = ((sw * (sh - th) + (sw - tw)) / 2) * 4, k = 0; k < ly; k++) {
			raf.seek(pos);
			raf.write(data, j, lx * 4);
			j += sw * 4;
			pos += iw * 4;
			Thread.yield();
		}
	}

	private byte[] convertToBytes(RendererSize size, IntBuffer pixels) {
		byte[] data = new byte[size.getWidth() * size.getHeight() * 4];
		for (int j = 0, i = 0; i < data.length; i += 4) {
			int pixel = pixels.get(j++);
			data[i + 3] = (byte)((pixel >> 24) & 0xFF);
			data[i + 0] = (byte)((pixel >> 16) & 0xFF);
			data[i + 1] = (byte)((pixel >> 8) & 0xFF);
			data[i + 2] = (byte)((pixel >> 0) & 0xFF);
		}
		return data;
	}
}
