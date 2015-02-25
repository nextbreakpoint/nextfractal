package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.encoder.Encoder;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class ExportSession {
	private static final Logger logger = Logger.getLogger(ExportSession.class.getName());
	private static final int TILE_BORDER_SIZE = 24;
	private final List<ExportJob> jobs = new ArrayList<>();
	private final String sessioinId;
	private final Encoder encoder;
	private final RendererSize size;
	private final String pluginId;
	private final Object data;
	private final File tmpFile;
	private final File file;
	private final int tileSize;
	private final float quality;
	private final float frameRate;
	private volatile float progress;
	private volatile int frameNumber;
	private volatile boolean cancelled;
	private volatile SessionState state = SessionState.SUSPENDED;

	public ExportSession(String pluginId, Object data, File file, File tmpFile, RendererSize size, int tileSize, Encoder encoder) {
		this.pluginId = pluginId;
		this.tmpFile = tmpFile;
		this.file = file;
		this.data = data;
		this.size = size;
		this.encoder = encoder;
		this.tileSize = tileSize;
		this.quality = 1;
		this.frameRate = 1.0f/24.0f;
		sessioinId = UUID.randomUUID().toString();
		jobs.addAll(createJobs());
	}
	
	public String getSessionId() {
		return sessioinId;
	}

	public String getPluginId() {
		return pluginId;
	}

	public Object getData() {
		return data;
	}

	public RendererSize getSize() {
		return size;
	}

	public Encoder getEncoder() {
		return encoder;
	}

	public File getFile() {
		return file;
	}

	public File getTmpFile() {
		return tmpFile;
	}

	public void dispose() {
		jobs.clear();
	}

	public SessionState getState() {
		return state;
	}

	public void setState(SessionState state) {
		this.state = state;
		logger.info(getSessionId() + " -> state = " + state);
	}

	public List<ExportJob> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void updateProgress() {
		setProgress((float)getCompletedJobsCount() / (float)jobs.size());
	}

	public int getCompletedJobsCount() {
		int count = 0;
		for (ExportJob job : jobs) {
			if (job.isCompleted()) {
				count += 1;
			}
		}
		return count;
	}

	public float getProgress() {
		return progress;
	}

	protected void setProgress(float progress) {
		this.progress = progress;
	}

	protected List<ExportJob> createJobs() {
		final List<ExportJob> jobs = new ArrayList<ExportJob>();
		final int frameWidth = size.getWidth();
		final int frameHeight = size.getHeight();
		final int nx = frameWidth / tileSize;
		final int ny = frameHeight / tileSize;
		final int rx = frameWidth - tileSize * nx;
		final int ry = frameHeight - tileSize * ny;
		if ((nx > 0) && (ny > 0)) {
			for (int tx = 0; tx < nx; tx++) {
				for (int ty = 0; ty < ny; ty++) {
					final ExportProfile profile = new ExportProfile();
					profile.setPluginId(pluginId);
					profile.setQuality(quality);
					profile.setFrameNumber(frameNumber);
					profile.setFrameRate(frameRate);
					profile.setFrameWidth(frameWidth);
					profile.setFrameHeight(frameHeight);
					profile.setTileWidth(tileSize);
					profile.setTileHeight(tileSize);
					profile.setTileOffsetX(tileSize * tx);
					profile.setTileOffsetY(tileSize * ty);
					profile.setTileBorderWidth(TILE_BORDER_SIZE);
					profile.setTileBorderHeight(TILE_BORDER_SIZE);
					profile.setData(data);
					final ExportJob job = createJob(profile);
					jobs.add(job);
				}
			}
		}
		if (rx > 0) {
			for (int ty = 0; ty < ny; ty++) {
				final ExportProfile profile = new ExportProfile();
				profile.setPluginId(pluginId);
				profile.setQuality(quality);
				profile.setFrameNumber(frameNumber);
				profile.setFrameRate(frameRate);
				profile.setFrameWidth(frameWidth);
				profile.setFrameHeight(frameHeight);
				profile.setTileWidth(rx);
				profile.setTileHeight(tileSize);
				profile.setTileOffsetX(tileSize * nx);
				profile.setTileOffsetY(tileSize * ty);
				profile.setTileBorderWidth(TILE_BORDER_SIZE);
				profile.setTileBorderHeight(TILE_BORDER_SIZE);
				profile.setData(data);
				final ExportJob job = createJob(profile);
				jobs.add(job);
			}
		}
		if (ry > 0) {
			for (int tx = 0; tx < nx; tx++) {
				final ExportProfile profile = new ExportProfile();
				profile.setPluginId(pluginId);
				profile.setQuality(quality);
				profile.setFrameNumber(frameNumber);
				profile.setFrameRate(frameRate);
				profile.setFrameWidth(frameWidth);
				profile.setFrameHeight(frameHeight);
				profile.setTileWidth(tileSize);
				profile.setTileHeight(ry);
				profile.setTileOffsetX(tileSize * tx);
				profile.setTileOffsetY(tileSize * ny);
				profile.setTileBorderWidth(TILE_BORDER_SIZE);
				profile.setTileBorderHeight(TILE_BORDER_SIZE);
				profile.setData(data);
				final ExportJob job = createJob(profile);
				jobs.add(job);
			}
		}
		if (rx > 0 && ry > 0) {
			final ExportProfile profile = new ExportProfile();
			profile.setPluginId(pluginId);
			profile.setQuality(quality);
			profile.setFrameNumber(frameNumber);
			profile.setFrameRate(frameRate);
			profile.setFrameWidth(frameWidth);
			profile.setFrameHeight(frameHeight);
			profile.setTileWidth(rx);
			profile.setTileHeight(ry);
			profile.setTileOffsetX(tileSize * nx);
			profile.setTileOffsetY(tileSize * ny);
			profile.setTileBorderWidth(TILE_BORDER_SIZE);
			profile.setTileBorderHeight(TILE_BORDER_SIZE);
			profile.setData(data);
			final ExportJob job = createJob(profile);
			jobs.add(job);
		}
		return jobs;
	}

	protected ExportJob createJob(ExportProfile profile) {
		return new ExportJob(this, profile);
	}

	public boolean isStopped() {
		return state == SessionState.STOPPED;
	}

	public boolean isDispatched() {
		return state == SessionState.DISPATCHED;
	}

	public boolean isStarted() {
		return state == SessionState.STARTED;
	}

	public boolean isSuspended() {
		return state == SessionState.SUSPENDED;
	}

	public boolean isInterrupted() {
		return state == SessionState.INTERRUPTED;
	}

	public boolean isCompleted() {
		return state == SessionState.COMPLETED;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getJobsCount() {
		return jobs.size();
	}
}