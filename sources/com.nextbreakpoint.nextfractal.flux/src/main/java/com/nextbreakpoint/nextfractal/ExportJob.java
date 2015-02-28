package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;

public class ExportJob {
	private final ExportSession session;
	private final ExportProfile profile;
	private volatile Throwable error;
	private volatile JobState state = JobState.READY;

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

	public void writePixels(RendererSize size, IntBuffer pixels) throws IOException {
		File tmpFile = this.getTmpFile();
		RandomAccessFile jobRaf = null;
		try {
			byte[] data = convertToBytes(size, pixels);
			jobRaf = new RandomAccessFile(tmpFile, "rw");
			double stopTime = this.getProfile().getStopTime();
			double startTime = this.getProfile().getStartTime();
			float frameRate = this.getProfile().getFrameRate();
			final int frameCount = (int) Math.floor((stopTime - startTime) * frameRate);
			if (frameCount == 0) {
				final int sw = size.getWidth();
				final int sh = size.getHeight();
				final int tx = this.getProfile().getTileOffsetX();
				final int ty = this.getProfile().getTileOffsetY();
				final int tw = this.getProfile().getTileWidth();
				final int th = this.getProfile().getTileHeight();
				final int iw = this.getProfile().getFrameWidth();
				long pos = (ty * iw + tx) * 4;
				for (int j = sw * (sh - th) * 4 / 2 + (sw - tw) * 4 / 2, k = 0; k < th; k++) {
					jobRaf.seek(pos);
					jobRaf.write(data, j, tw * 4);
					j += sw * 4;
					pos += iw * 4;
					Thread.yield();
				}
				// this.setFrameNumber(0);
			} else if (this.getProfile().getFrameNumber() < frameCount) {
				final int sw = size.getWidth();
				final int sh = size.getHeight();
				final int tx = this.getProfile().getTileOffsetX();
				final int ty = this.getProfile().getTileOffsetY();
				final int tw = this.getProfile().getTileWidth();
				final int th = this.getProfile().getTileHeight();
				final int iw = this.getProfile().getFrameWidth();
				final int ih = this.getProfile().getFrameHeight();
				int startFrameNumber = 0;
				if (this.getProfile().getFrameNumber() > 0) {
					startFrameNumber = this.getProfile().getFrameNumber() + 1;
				}
				for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
					jobRaf.readFully(data);
					long pos = (frameNumber * iw * ih + ty * iw + tx) * 4;
					for (int j = sw * (sh - th) * 4 / 2 + (sw - tw) * 4 / 2, k = 0; k < th; k++) {
						jobRaf.seek(pos);
						jobRaf.write(data, j, tw * 4);
						j += sw * 4;
						pos += iw * 4;
						Thread.yield();
					}
					// this.setFrameNumber(frameNumber);
				}
			}
		} finally {
			if (jobRaf != null) {
				try {
					jobRaf.close();
				} catch (final IOException e) {
				}
			}
		}
	}

	private byte[] convertToBytes(RendererSize size, IntBuffer pixels) {
		byte[] data = new byte[size.getWidth() * size.getHeight() * 4];
		for (int j = 0, i = 0; i < data.length; i += 4) {
			int pixel = pixels.get(j++);
			data[i + 0] = (byte)((pixel >> 0) & 0xFF);
			data[i + 1] = (byte)((pixel >> 8) & 0xFF);
			data[i + 2] = (byte)((pixel >> 16) & 0xFF);
			data[i + 3] = (byte)((pixel >> 24) & 0xFF);
		}
		return data;
	}
	
	@Override
	public String toString() {
		return "[sessionId = " + session.getSessionId() + ", profile=" + profile + "]";
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public boolean isInterrupted() {
		return state == JobState.INTERRUPTED;
	}

	public boolean isCompleted() {
		return state == JobState.COMPLETED;
	}

	public boolean isReady() {
		return state == JobState.READY;
	}

	public JobState getState() {
		return state;
	}

	public void setState(JobState state) {
		this.state = state;
	}
}
