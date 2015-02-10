package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class ExportSession {
	private static final int TILE_BORDER_SIZE = 24;
	private final List<ExportSessionListener> listeners = new ArrayList<>();
	private final List<ExportJob> jobs = new ArrayList<>();
	private final String sessioinId;
	private final ExportService exportService;
	private final DataEncoder encoder;
	private final RendererSize size;
	private final Object data;
	private final File file;
	private float progress;
	private float quality;
	private float frameRate;
	private int frame;

	public ExportSession(ExportService exportService, File file, Object data, RendererSize size, DataEncoder encoder) {
		this.file = file;
		this.data = data;
		this.size = size;
		this.encoder = encoder;
		this.exportService = exportService;
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

	public DataEncoder getEncoder() {
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
		exportService.startSession(this);
		fireStateChanged();
	}

	public void stop() {
		exportService.stopSession(this);
		fireStateChanged();
	}

	public void suspend() {
		exportService.suspendSession(this);
		fireStateChanged();
	}

	public void resume() {
		exportService.resumeSession(this);
		fireStateChanged();
	}

	public float getProgress() {
		return progress;
	}

	protected List<ExportJob> createJobs() {
		final List<ExportJob> jobs = new ArrayList<ExportJob>();
		final int tileSize = exportService.getTileSize();
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
					job.setQuality(quality);
					job.setFrameNumber(frame);
					job.setFrameRate(frameRate);
					job.setFrameWidth(frameWidth);
					job.setFrameHeight(frameHeight);
					job.setTileWidth(tileSize);
					job.setTileHeight(tileSize);
					job.setTileOffsetX(tileSize * tx);
					job.setTileOffsetY(tileSize * ty);
					job.setTileBorderWidth(TILE_BORDER_SIZE);
					job.setTileBorderHeight(TILE_BORDER_SIZE);
					jobs.add(job);
				}
			}
		}
		if (rx > 0) {
			for (int ty = 0; ty < ny; ty++) {
				final ExportJob job = createJob();
				job.setQuality(quality);
				job.setFrameNumber(frame);
				job.setFrameRate(frameRate);
				job.setFrameWidth(frameWidth);
				job.setFrameHeight(frameHeight);
				job.setTileWidth(rx);
				job.setTileHeight(tileSize);
				job.setTileOffsetX(tileSize * nx);
				job.setTileOffsetY(tileSize * ty);
				job.setTileBorderWidth(TILE_BORDER_SIZE);
				job.setTileBorderHeight(TILE_BORDER_SIZE);
				jobs.add(job);
			}
		}
		if (ry > 0) {
			for (int tx = 0; tx < nx; tx++) {
				final ExportJob job = createJob();
				job.setQuality(quality);
				job.setFrameNumber(frame);
				job.setFrameRate(frameRate);
				job.setFrameWidth(frameWidth);
				job.setFrameHeight(frameHeight);
				job.setTileWidth(tileSize);
				job.setTileHeight(ry);
				job.setTileOffsetX(tileSize * tx);
				job.setTileOffsetY(tileSize * ny);
				job.setTileBorderWidth(TILE_BORDER_SIZE);
				job.setTileBorderHeight(TILE_BORDER_SIZE);
				jobs.add(job);
			}
		}
		if (rx > 0 && ry > 0) {
			final ExportJob job = createJob();
			job.setQuality(quality);
			job.setFrameNumber(frame);
			job.setFrameRate(frameRate);
			job.setFrameWidth(frameWidth);
			job.setFrameHeight(frameHeight);
			job.setTileWidth(rx);
			job.setTileHeight(ry);
			job.setTileOffsetX(tileSize * nx);
			job.setTileOffsetY(tileSize * ny);
			job.setTileBorderWidth(TILE_BORDER_SIZE);
			job.setTileBorderHeight(TILE_BORDER_SIZE);
			jobs.add(job);
		}
		return jobs;
	}

	protected ExportJob createJob() {
		final ExportJob job = new ExportJob(this);
		return job;
	}

	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ExportJob> getJobs() {
		return Collections.unmodifiableList(jobs);
	}

	public void updateState() {
		// TODO Auto-generated method stub
		
	}
}
