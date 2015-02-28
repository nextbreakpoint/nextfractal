package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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
