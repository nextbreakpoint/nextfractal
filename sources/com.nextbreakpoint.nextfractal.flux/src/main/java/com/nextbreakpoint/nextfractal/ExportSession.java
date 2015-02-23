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
	private volatile boolean dispatched;
	private volatile boolean terminated;
	private volatile boolean suspended;
	private volatile boolean requestTerminate;
	private volatile boolean requestSuspend;

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
	
	public synchronized void start() {
		logger.info(sessioinId + " -> start");
		requestTerminate = false;
		for (ExportJob job : jobs) {
			job.setTerminated(false);
		}
	}

	public synchronized void stop() {
		logger.info(sessioinId + " -> stop");
		requestTerminate = true;
	}

	public synchronized void resume() {
		logger.info(sessioinId + " -> resume");
		requestSuspend = false;
		for (ExportJob job : jobs) {
			job.setSuspended(false);
		}
	}

	public synchronized void suspend() {
		logger.info(sessioinId + " -> suspend");
		requestSuspend = true;
	}

	public boolean isRequestTerminate() {
		return requestTerminate;
	}

	public boolean isRequestSuspend() {
		return requestSuspend;
	}

	public boolean isDispatched() {
		return dispatched;
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

	public synchronized void updateState() {
		float count = 0;
		for (ExportJob job : jobs) {
			if (job.isTerminated()) {
				count += 1;
			}
		}
		boolean allJobDispatched = true;
		for (ExportJob job : jobs) {
			if (!job.isDispatched()) {
				allJobDispatched = false;
				break;
			}
		}
		boolean allJobTerminated = true;
		for (ExportJob job : jobs) {
			if (!job.isTerminated()) {
				allJobTerminated = false;
				break;
			}
		}
		boolean allJobSuspended = true;
		for (ExportJob job : jobs) {
			if (!job.isSuspended()) {
				allJobSuspended = false;
				break;
			}
		}
		if (allJobDispatched) {
			logger.info(sessioinId + " -> dispatched");
			this.dispatched = true;
		}
		if (allJobTerminated) {
			logger.info(sessioinId + " -> terminated");
			this.terminated = true;
		}
		if (allJobSuspended) {
			logger.info(sessioinId + " -> suspended");
			this.suspended = true;
		}
		setProgress(count / jobs.size());
	}

	public float getProgress() {
		return progress;
	}

	protected void setProgress(float progress) {
		logger.info(sessioinId + " -> progress = " + progress);
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
}
