package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.nextbreakpoint.nextfractal.encoder.Encoder;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class ExportSession {
	private static final int TILE_BORDER_SIZE = 24;
	private final List<ExportSessionListener> listeners = new ArrayList<>();
	private final List<ExportJob> jobs = new ArrayList<>();
	private final String sessioinId;
	private final Encoder encoder;
	private final RendererSize size;
	private final String pluginId;
	private final Object data;
	private final File tmpFile;
	private final File file;
	private float progress;
	private float quality;
	private float frameRate;
	private int frameNumber;
	private int tileSize;
	private volatile boolean terminated;
	private volatile boolean suspended;

	public ExportSession(String pluginId, Object data, File file, File tmpFile, RendererSize size, int tileSize, Encoder encoder) {
		this.pluginId = pluginId;
		this.tmpFile = tmpFile;
		this.file = file;
		this.data = data;
		this.size = size;
		this.encoder = encoder;
		this.tileSize = tileSize;
		sessioinId = UUID.randomUUID().toString();
		jobs.addAll(createJobs());
	}
	
	public String getSessionId() {
		return sessioinId;
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

	public void addSessionListener(ExportSessionListener listener) {
		listeners.add(listener);
	}

	public void removeSessionListener(ExportSessionListener listener) {
		listeners.remove(listener);
	}
	
	protected void setProgress(float progress) {
		this.progress = progress;
		fireStateChanged();
	}

	protected void fireStateChanged() {
		for (ExportSessionListener listener : listeners) {
			listener.stateChanged(this, progress);
		}
	}

	public void start() {
		// TODO Auto-generated method stub
		fireStateChanged();
	}

	public void stop() {
		// TODO Auto-generated method stub
		fireStateChanged();
	}

	public void suspend() {
		// TODO Auto-generated method stub
		fireStateChanged();
	}

	public void resume() {
		// TODO Auto-generated method stub
		fireStateChanged();
	}

	public float getProgress() {
		return progress;
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
					final ExportJob job = createJob();
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
					job.setProfile(profile);
					jobs.add(job);
				}
			}
		}
		if (rx > 0) {
			for (int ty = 0; ty < ny; ty++) {
				final ExportJob job = createJob();
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
				job.setProfile(profile);
				jobs.add(job);
			}
		}
		if (ry > 0) {
			for (int tx = 0; tx < nx; tx++) {
				final ExportJob job = createJob();
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
				job.setProfile(profile);
				jobs.add(job);
			}
		}
		if (rx > 0 && ry > 0) {
			final ExportJob job = createJob();
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
			job.setProfile(profile);
			jobs.add(job);
		}
		return jobs;
	}

	protected ExportJob createJob() {
		final ExportJob job = new ExportJob();
		return job;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public List<ExportJob> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void updateState() {
		// TODO Auto-generated method stub
	}

	public File getTmpFile() {
		return tmpFile;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void dispose() {
		listeners.clear();
		jobs.clear();
	}
}
