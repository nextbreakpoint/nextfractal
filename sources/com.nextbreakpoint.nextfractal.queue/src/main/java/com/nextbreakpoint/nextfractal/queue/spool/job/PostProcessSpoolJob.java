/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.queue.spool.job;

import java.awt.RenderingHints;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import org.w3c.dom.Document;

import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.core.xml.XML;
import com.nextbreakpoint.nextfractal.queue.LibraryService;
import com.nextbreakpoint.nextfractal.queue.io.ChunkedRandomAccessFile;
import com.nextbreakpoint.nextfractal.queue.spool.DefaultJobData;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.queue.spool.JobListener;
import com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterClipController;
import com.nextbreakpoint.nextfractal.twister.TwisterClipXMLImporter;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.OverlayTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.java2D.Java2DRenderFactory;

/**
 * @author Andrea Medeghini
 */
public class PostProcessSpoolJob implements SpoolJobInterface {
	private static final ThreadFactory factory = new DefaultThreadFactory("PostProcessSpoolJob Task", true, Thread.MIN_PRIORITY);
	private final JobListener listener;
	private final String jobId;
	private volatile long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;
	private volatile JobData jobDataRow;
	private volatile Thread thread;
	private final LibraryService service;
	private int firstFrameNumber;
	private final Worker worker;

	/**
	 * @param service
	 * @param worker
	 * @param jobId
	 * @param listener
	 */
	public PostProcessSpoolJob(final LibraryService service, final Worker worker, final String jobId, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
		this.service = service;
		this.worker = worker;
		this.jobId = jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobId()
	 */
	@Override
	public String getJobId() {
		return jobId;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFrameNumber()
	 */
	@Override
	public synchronized int getFrameNumber() {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		return jobDataRow.getFrameNumber();
	}

	private void setFrameNumber(final int frameNumber) {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		jobDataRow.setFrameNumber(frameNumber);
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#setFirstFrameNumber(int)
	 */
	@Override
	public synchronized void setFirstFrameNumber(final int frameNumber) {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		if (thread != null) {
			throw new IllegalStateException();
		}
		firstFrameNumber = frameNumber;
		jobDataRow.setFrameNumber(frameNumber);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFirstFrameNumber()
	 */
	@Override
	public synchronized int getFirstFrameNumber() {
		return firstFrameNumber;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobDataRow()
	 */
	@Override
	public synchronized JobData getJobDataRow() {
		return jobDataRow;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#setJobDataRow(JobData)
	 */
	@Override
	public synchronized void setJobDataRow(final JobData jobDataRow) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		if (jobDataRow == null) {
			throw new IllegalArgumentException();
		}
		this.jobDataRow = jobDataRow;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#getClip()
	 */
	@Override
	public synchronized TwisterClip getClip() throws IOException {
		if (jobDataRow == null) {
			throw new IllegalStateException();
		}
		try {
			final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
			final InputStream is = service.getClipInputStream(jobDataRow.getClipId());
			final Document doc = XML.loadDocument(is, "twister-clip.xml");
			return importer.importFromElement(doc.getDocumentElement());
		}
		catch (final Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public synchronized String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("id = ");
		builder.append(jobId);
		builder.append(", frameNumber = ");
		builder.append(jobDataRow != null ? jobDataRow.getFrameNumber() : "N/A");
		return builder.toString();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getLastUpdate()
	 */
	@Override
	public synchronized long getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#reset()
	 */
	@Override
	public void reset() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			started = false;
			aborted = false;
			terminated = false;
			lastUpdate = System.currentTimeMillis();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#start()
	 */
	@Override
	public void start() {
		synchronized (this) {
			if (jobDataRow == null) {
				throw new IllegalStateException();
			}
			if (thread == null) {
				lastUpdate = System.currentTimeMillis();
				started = true;
				aborted = false;
				terminated = false;
				worker.addTask(new StartedTask(new DefaultJobData(jobDataRow)));
				thread = factory.newThread(new RenderTask());
				thread.start();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#stop()
	 */
	@Override
	public void stop() {
		Thread tmpThread = thread;
		if (tmpThread != null) {
			tmpThread.interrupt();
			try {
				tmpThread.join();
			}
			catch (final InterruptedException e) {
			}
		}
		synchronized (this) {
			if (jobDataRow == null) {
				throw new IllegalStateException();
			}
			started = false;
			thread = null;
			worker.addTask(new StoppedTask(new DefaultJobData(jobDataRow)));
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#abort()
	 */
	@Override
	public synchronized void abort() {
		aborted = true;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#dispose()
	 */
	@Override
	public synchronized void dispose() {
		if (jobDataRow != null) {
			worker.addTask(new DisposedTask(new DefaultJobData(jobDataRow)));
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isStarted()
	 */
	@Override
	public synchronized boolean isStarted() {
		return started;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isAborted()
	 */
	@Override
	public synchronized boolean isAborted() {
		return aborted;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#isTerminated()
	 */
	@Override
	public synchronized boolean isTerminated() {
		return terminated;
	}

	private class RenderTask implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Surface surface = null;
			TwisterRuntime overlayRuntime = null;
			TwisterRenderer overlayRenderer = null;
			ChunkedRandomAccessFile profileRaf = null;
			InputStream clipStream = null;
			try {
				clipStream = service.getClipInputStream(jobDataRow.getClipId());
				final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
				final Document doc = XML.loadDocument(clipStream, "twister-clip.xml");
				final TwisterClip clip = importer.importFromElement(doc.getDocumentElement());
				if (clip.getSequenceCount() > 0) {
					final int frameCount = (jobDataRow.getStopTime() - jobDataRow.getStartTime()) * jobDataRow.getFrameRate();
					int frameTimeInMillis = 0;
					final int iw = jobDataRow.getImageWidth();
					final int ih = jobDataRow.getImageHeight();
					final byte[] row = new byte[iw * 4];
					surface = new Surface(iw, ih);
					surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
					surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
					surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					final TwisterClipController controller = new TwisterClipController(clip);
					controller.init();
					final TwisterConfig config = controller.getConfig();
					overlayRuntime = new TwisterRuntime(config);
					overlayRenderer = new OverlayTwisterRenderer(overlayRuntime);
					final Map<Object, Object> overlayHints = new HashMap<Object, Object>();
					overlayHints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
					overlayHints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_OVERLAY);
					overlayRenderer.setRenderFactory(new Java2DRenderFactory());
					overlayRenderer.setRenderingHints(overlayHints);
					final IntegerVector2D size = new IntegerVector2D(iw, ih);
					overlayRenderer.setTile(new Tile(size, size, new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
					final int[] data = ((DataBufferInt) (surface.getImage().getRaster().getDataBuffer())).getData();
					profileRaf = service.getProfileRandomAccessFile(jobDataRow.getProfileId());
					int startFrameNumber = 0;
					if (jobDataRow.getFrameNumber() > 0) {
						startFrameNumber = jobDataRow.getFrameNumber() + 1;
						frameTimeInMillis = jobDataRow.getStartTime() * 1000 + (jobDataRow.getFrameNumber() * 1000) / jobDataRow.getFrameRate();
						controller.redoAction(frameTimeInMillis, false);
						overlayRenderer.prepareImage(false);
						overlayRenderer.render();
					}
					for (int frameNumber = 0; frameNumber < startFrameNumber; frameNumber++) {
						frameTimeInMillis = jobDataRow.getStartTime() * 1000 + (frameNumber * 1000) / jobDataRow.getFrameRate();
						controller.redoAction(frameTimeInMillis, false);
						if (overlayRuntime.getEffectElement().getEffectRuntime() != null) {
							overlayRuntime.getEffectElement().getEffectRuntime().setSize(size);
							overlayRuntime.getEffectElement().getEffectRuntime().prepareEffect();
						}
					}
					for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
						frameTimeInMillis = jobDataRow.getStartTime() * 1000 + (frameNumber * 1000) / jobDataRow.getFrameRate();
						controller.redoAction(frameTimeInMillis, false);
						overlayRenderer.prepareImage(false);
						overlayRenderer.render();
						long pos = frameNumber * iw * ih * 4;
						for (int j = 0, k = 0; k < ih; k++) {
							profileRaf.seek(pos);
							profileRaf.readFully(row);
							for (int i = 0; i < row.length; i += 4) {
								data[j] = (((row[i + 3]) & 0xFF) << 24) | (((row[i + 0]) & 0xFF) << 16) | (((row[i + 1]) & 0xFF) << 8) | ((row[i + 2]) & 0xFF);
								j += 1;
							}
							pos += row.length;
							Thread.yield();
						}
						if (overlayRuntime.getEffectElement().getEffectRuntime() != null) {
							overlayRuntime.getEffectElement().getEffectRuntime().setSize(size);
							overlayRuntime.getEffectElement().getEffectRuntime().prepareEffect();
							overlayRuntime.getEffectElement().getEffectRuntime().renderImage(surface);
						}
						overlayRenderer.drawImage(surface.getGraphics2D());
						pos = frameNumber * iw * ih * 4;
						for (int j = 0, k = 0; k < ih; k++) {
							for (int i = 0; i < row.length; i += 4) {
								row[i + 0] = (byte) ((data[j] & 0x00FF0000) >> 16);
								row[i + 1] = (byte) ((data[j] & 0x0000FF00) >> 8);
								row[i + 2] = (byte) ((data[j] & 0x000000FF) >> 0);
								row[i + 3] = (byte) ((data[j] & 0xFF000000) >> 24);
								j += 1;
							}
							profileRaf.seek(pos);
							profileRaf.write(row);
							pos += row.length;
							Thread.yield();
						}
						setFrameNumber(frameNumber);
						worker.addTask(new StatusChangedTask(new DefaultJobData(jobDataRow)));
						if (aborted) {
							break;
						}
					}
				}
			}
			catch (final InterruptedException e) {
				aborted = true;
			}
			catch (final Throwable e) {
				aborted = true;
				e.printStackTrace();
			}
			finally {
				if (overlayRenderer != null) {
					overlayRenderer.dispose();
					overlayRenderer = null;
				}
				if (overlayRuntime != null) {
					overlayRuntime.dispose();
					overlayRuntime = null;
				}
				if (surface != null) {
					surface.dispose();
					surface = null;
				}
				if (profileRaf != null) {
					try {
						profileRaf.close();
					}
					catch (final IOException e) {
					}
				}
				if (clipStream != null) {
					try {
						clipStream.close();
					}
					catch (final IOException e) {
					}
				}
			}
			synchronized (PostProcessSpoolJob.this) {
				if (!aborted) {
					terminated = true;
				}
				if (terminated) {
					worker.addTask(new TerminatedTask(new DefaultJobData(jobDataRow)));
				}
			}
		}
	}

	private class StatusChangedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StatusChangedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.updated(jobId, jobData);
		}
	}

	private class StartedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StartedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.started(jobId, jobData);
		}
	}

	private class StoppedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected StoppedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.stopped(jobId, jobData);
		}
	}

	private class TerminatedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected TerminatedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.terminated(jobId, jobData);
		}
	}

	private class DisposedTask implements Runnable {
		private final JobData jobData;

		/**
		 * @param jobData
		 */
		protected DisposedTask(final JobData jobData) {
			this.jobData = jobData;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			listener.disposed(jobId, jobData);
		}
	}
}
