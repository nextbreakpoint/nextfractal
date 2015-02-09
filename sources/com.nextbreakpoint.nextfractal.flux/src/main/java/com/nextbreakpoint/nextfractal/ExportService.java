package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import javafx.application.Platform;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.core.util.ProgressListener;
import com.nextbreakpoint.nextfractal.queue.DataTableException;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.RenderServiceException;
import com.nextbreakpoint.nextfractal.queue.SessionException;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJob;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class ExportService {
	private final Worker serviceWorker;
	private ThreadFactory threadFactory;
	private RenderFactory renderFactory;
	private int tileSize;
	
	public ExportService(ThreadFactory threadFactory, RenderFactory renderFactory, int tileSize) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.tileSize = tileSize;
		serviceWorker = new Worker(threadFactory);
		serviceWorker.start();
	}

	public void startSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		serviceWorker.addTask(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						exportSession.setProgress(0.5f);
					}
				});
			}
		});
	}

	public void stopSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}

	public void suspendSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}

	public void resumeSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}



	private List<RenderJob> createJobs(final float percentage) {
		final List<RenderJobDataRow> jobs = new ArrayList<RenderJobDataRow>();
		final int nx = profile.getImageWidth() / LibraryService.TILE_WIDTH;
		final int ny = profile.getImageHeight() / LibraryService.TILE_HEIGHT;
		final int rx = profile.getImageWidth() - nx * LibraryService.TILE_WIDTH;
		final int ry = profile.getImageHeight() - ny * LibraryService.TILE_HEIGHT;
		if ((nx > 0) && (ny > 0)) {
			for (int i = 0; i < nx; i++) {
				for (int j = 0; j < ny; j++) {
					final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
					job.setClipName(profile.getClipName());
					job.setProfileName(profile.getProfileName());
					job.setClipId(profile.getClipId());
					job.setProfileId(profile.getProfileId());
					job.setFrameNumber(profile.getJobFrame());
					job.setImageWidth(profile.getImageWidth());
					job.setImageHeight(profile.getImageHeight());
					job.setTileWidth(LibraryService.TILE_WIDTH);
					job.setTileHeight(LibraryService.TILE_HEIGHT);
					job.setBorderWidth(24);
					job.setBorderHeight(24);
					job.setTileOffsetX(LibraryService.TILE_WIDTH * i);
					job.setTileOffsetY(LibraryService.TILE_HEIGHT * j);
					job.setQuality(profile.getQuality());
					job.setFrameRate(profile.getFrameRate());
					jobs.add(job);
				}
			}
		}
		if (rx > 0) {
			for (int j = 0; j < ny; j++) {
				final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
				job.setClipName(profile.getClipName());
				job.setProfileName(profile.getProfileName());
				job.setClipId(profile.getClipId());
				job.setProfileId(profile.getProfileId());
				job.setFrameNumber(profile.getJobFrame());
				job.setImageWidth(profile.getImageWidth());
				job.setImageHeight(profile.getImageHeight());
				job.setTileWidth(rx);
				job.setTileHeight(LibraryService.TILE_HEIGHT);
				job.setBorderWidth(24);
				job.setBorderHeight(24);
				job.setTileOffsetX(nx * LibraryService.TILE_WIDTH);
				job.setTileOffsetY(LibraryService.TILE_HEIGHT * j);
				job.setQuality(profile.getQuality());
				job.setFrameRate(profile.getFrameRate());
				jobs.add(job);
			}
		}
		if (ry > 0) {
			for (int i = 0; i < nx; i++) {
				final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
				job.setClipName(profile.getClipName());
				job.setProfileName(profile.getProfileName());
				job.setClipId(profile.getClipId());
				job.setProfileId(profile.getProfileId());
				job.setFrameNumber(profile.getJobFrame());
				job.setImageWidth(profile.getImageWidth());
				job.setImageHeight(profile.getImageHeight());
				job.setTileWidth(LibraryService.TILE_WIDTH);
				job.setTileHeight(ry);
				job.setBorderWidth(24);
				job.setBorderHeight(24);
				job.setTileOffsetX(LibraryService.TILE_WIDTH * i);
				job.setTileOffsetY(ny * LibraryService.TILE_HEIGHT);
				job.setQuality(profile.getQuality());
				job.setFrameRate(profile.getFrameRate());
				jobs.add(job);
			}
		}
		if ((rx > 0) && (ry > 0)) {
			final RenderJobDataRow job = new RenderJobDataRow(new RenderJob());
			job.setClipName(profile.getClipName());
			job.setProfileName(profile.getProfileName());
			job.setClipId(profile.getClipId());
			job.setProfileId(profile.getProfileId());
			job.setFrameNumber(profile.getJobFrame());
			job.setImageWidth(profile.getImageWidth());
			job.setImageHeight(profile.getImageHeight());
			job.setTileWidth(rx);
			job.setTileHeight(ry);
			job.setBorderWidth(24);
			job.setBorderHeight(24);
			job.setTileOffsetX(nx * LibraryService.TILE_WIDTH);
			job.setTileOffsetY(ny * LibraryService.TILE_HEIGHT);
			job.setQuality(profile.getQuality());
			job.setFrameRate(profile.getFrameRate());
			jobs.add(job);
		}
		profile.setJobCreated(jobs.size());
		profile.setJobStored(0);
		profileService.saveJobs(session, profile);
		for (int i = 0; i < jobs.size(); i++) {
			RenderJobDataRow job = jobs.get(i);
			listener.stateChanged(message, (int) Math.rint((((float) i) / (jobs.size() - 1)) * 100f));
			clip.setStatus(clip.getStatus() + 1);
			clipService.saveStatus(session, clip);
			profile.setStatus(profile.getStatus() + 1);
			profileService.saveStatus(session, profile);
			jobService.create(session, job);
			startJob(job);
		}
		return jobs;
	}
}
