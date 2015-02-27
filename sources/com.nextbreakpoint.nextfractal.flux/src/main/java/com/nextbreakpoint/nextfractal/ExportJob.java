package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;

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

	public void writePixels(IntBuffer pixels) throws IOException {
		File tmpFile = this.getTmpFile();
		RandomAccessFile jobRaf = null;
		try {
			jobRaf = new RandomAccessFile(tmpFile, "rw");
			double stopTime = this.getProfile().getStopTime();
			double startTime = this.getProfile().getStartTime();
			float frameRate = this.getProfile().getFrameRate();
			final int frameCount = (int) Math.floor((stopTime - startTime) * frameRate);
			if (frameCount == 0) {
				final int tx = this.getProfile().getTileOffsetX();
				final int ty = this.getProfile().getTileOffsetY();
				final int tw = this.getProfile().getTileWidth();
				final int th = this.getProfile().getTileHeight();
				final int bw = this.getProfile().getTileBorderWidth();
				final int bh = this.getProfile().getTileBorderHeight();
				final int iw = this.getProfile().getFrameWidth();
				final int sw = tw + 2 * bw;
				final int sh = th + 2 * bh;
				final byte[] data = new byte[sw * sh * 4];
				jobRaf.readFully(data);
				long pos = (ty * iw + tx) * 4;
				for (int j = sw * bh * 4 + bw * 4, k = 0; k < th; k++) {
					jobRaf.seek(pos);
					jobRaf.write(data, j, tw * 4);
					j += sw * 4;
					pos += iw * 4;
					Thread.yield();
				}
				// this.setFrameNumber(0);
			} else if (this.getProfile().getFrameNumber() < frameCount) {
				final int tx = this.getProfile().getTileOffsetX();
				final int ty = this.getProfile().getTileOffsetY();
				final int tw = this.getProfile().getTileWidth();
				final int th = this.getProfile().getTileHeight();
				final int bw = this.getProfile().getTileBorderWidth();
				final int bh = this.getProfile().getTileBorderHeight();
				final int iw = this.getProfile().getFrameWidth();
				final int ih = this.getProfile().getFrameHeight();
				final int sw = tw + 2 * bw;
				final int sh = th + 2 * bh;
				final byte[] data = new byte[sw * sh * 4];
				int startFrameNumber = 0;
				if (this.getProfile().getFrameNumber() > 0) {
					startFrameNumber = this.getProfile().getFrameNumber() + 1;
				}
				long pos = (startFrameNumber * sw * sh) * 4;
				jobRaf.seek(pos);
				for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
					jobRaf.readFully(data);
					pos = (frameNumber * iw * ih + ty * iw + tx) * 4;
					for (int j = sw * bh * 4 + bw * 4, k = 0; k < th; k++) {
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
