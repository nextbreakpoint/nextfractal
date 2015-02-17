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
package com.nextbreakpoint.nextfractal.spool.job;

import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.spool.JobInterface;
import com.nextbreakpoint.nextfractal.spool.JobListener;
import com.nextbreakpoint.nextfractal.spool.JobProfile;

/**
 * @author Andrea Medeghini
 */
public class LocalJob implements JobInterface {
	private static final ThreadFactory factory = new DefaultThreadFactory("LocalJob Task", true, Thread.MIN_PRIORITY);
	private final JobListener listener;
	private final String jobId;
	private volatile long lastUpdate;
	private volatile boolean started;
	private volatile boolean aborted;
	private volatile boolean terminated;
	private volatile JobProfile profile;
	private volatile Thread thread;
	private int firstFrameNumber;
	private final Worker worker;

	/**
	 * @param service
	 * @param worker
	 * @param jobId
	 * @param listener
	 */
	public LocalJob(final Worker worker, final String jobId, final JobListener listener) {
		lastUpdate = System.currentTimeMillis();
		this.listener = listener;
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
		if (profile == null) {
			throw new IllegalStateException();
		}
		return profile.getProfile().getFrameNumber();
	}

	private void setFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		profile.getProfile().setFrameNumber(frameNumber);
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface#setFirstFrameNumber(int)
	 */
	@Override
	public synchronized void setFirstFrameNumber(final int frameNumber) {
		if (profile == null) {
			throw new IllegalStateException();
		}
		if (thread != null) {
			throw new IllegalStateException();
		}
		firstFrameNumber = frameNumber;
		profile.getProfile().setFrameNumber(frameNumber);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getFirstFrameNumber()
	 */
	@Override
	public synchronized int getFirstFrameNumber() {
		return firstFrameNumber;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#getJobProfile()
	 */
	@Override
	public synchronized JobProfile getJobProfile() {
		return profile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.queue.spool.JobInterface#setJobProfile(JobProfile)
	 */
	@Override
	public synchronized void setJobProfile(final JobProfile profile) {
		if (thread != null) {
			throw new IllegalStateException();
		}
		if (profile == null) {
			throw new IllegalArgumentException();
		}
		this.profile = profile;
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
		builder.append(profile != null ? profile.getProfile().getFrameNumber() : "N/A");
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
			if (profile == null) {
				throw new IllegalStateException();
			}
			if (thread == null) {
				lastUpdate = System.currentTimeMillis();
				started = true;
				aborted = false;
				terminated = false;
				worker.addTask(new StartedTask(profile));
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
			if (profile == null) {
				throw new IllegalStateException();
			}
			started = false;
			thread = null;
			worker.addTask(new StoppedTask(profile));
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
		if (profile != null) {
			worker.addTask(new DisposedTask(profile));
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
//			Surface surface = null;
//			TwisterRuntime runtime = null;
//			TwisterRenderer renderer = null;
//			TwisterRuntime overlayRuntime = null;
//			TwisterRenderer overlayRenderer = null;
//			ChunkedRandomAccessFile jobRaf = null;
//			InputStream clipStream = null;
//			try {
//				clipStream = service.getClipInputStream(profile.getClipId());
//				final TwisterClipXMLImporter importer = new TwisterClipXMLImporter();
//				final Document doc = XML.loadDocument(clipStream, "twister-clip.xml");
//				final MandelbrotStoreData clip = importer.importFromElement(doc.getDocumentElement());
//				if (clip.getSequenceCount() > 0) {
//					final int frameCount = (profile.getStopTime() - profile.getStartTime()) * profile.getFrameRate();
//					int frameTimeInMillis = 0;
//					final int tx = profile.getTileOffsetX();
//					final int ty = profile.getTileOffsetY();
//					final int tw = profile.getTileWidth();
//					final int th = profile.getTileHeight();
//					final int iw = profile.getImageWidth();
//					final int ih = profile.getImageHeight();
//					final int bw = profile.getBorderWidth();
//					final int bh = profile.getBorderHeight();
//					final int sw = tw + 2 * bw;
//					final int sh = th + 2 * bh;
//					if ((profile.getFrameRate() == 0) || (frameCount == 0)) {
//						TwisterConfig config = clip.getSequence(0).getInitialConfig();
//						if (config == null) {
//							config = clip.getSequence(0).getFinalConfig();
//						}
//						if (config != null) {
//							surface = new Surface(sw, sh);
//							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//							surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//							runtime = new TwisterRuntime(config);
//							renderer = new DefaultTwisterRenderer(runtime);
//							final Map<Object, Object> hints = new HashMap<Object, Object>();
//							hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
//							renderer.setRenderFactory(new Java2DRenderFactory());
//							renderer.setRenderingHints(hints);
//							renderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(bw, bh)));
//							overlayRuntime = new TwisterRuntime(config);
//							overlayRenderer = new OverlayTwisterRenderer(overlayRuntime);
//							final Map<Object, Object> overlayHints = new HashMap<Object, Object>();
//							overlayHints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
//							overlayHints.put(TwisterRenderingHints.KEY_TYPE, TwisterRenderingHints.TYPE_OVERLAY);
//							overlayRenderer.setRenderFactory(new Java2DRenderFactory());
//							overlayRenderer.setRenderingHints(overlayHints);
//							overlayRenderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(bw, bh)));
//							renderer.prepareImage(false);
//							overlayRenderer.prepareImage(false);
//							renderer.render();
//							overlayRenderer.render();
//							renderer.drawSurface(surface.getGraphics2D());
//							overlayRenderer.drawSurface(surface.getGraphics2D());
//							final byte[] row = new byte[sw * 4];
//							jobRaf = service.getJobRandomAccessFile(profile.getJobId());
//							final int[] data = ((DataBufferInt) surface.getImage().getRaster().getDataBuffer()).getData();
//							long pos = 0;
//							for (int j = 0, k = 0; k < sh; k++) {
//								for (int i = 0; i < row.length; i += 4) {
//									row[i + 0] = (byte) ((data[j] & 0x00FF0000) >> 16);
//									row[i + 1] = (byte) ((data[j] & 0x0000FF00) >> 8);
//									row[i + 2] = (byte) ((data[j] & 0x000000FF) >> 0);
//									row[i + 3] = (byte) ((data[j] & 0xFF000000) >> 24);
//									j += 1;
//								}
//								jobRaf.seek(pos);
//								jobRaf.write(row);
//								pos += row.length;
//								Thread.yield();
//							}
//							setFrameNumber(0);
//							worker.addTask(new StatusChangedTask(new JobProfile(profile)));
//						}
//					}
//					else if (profile.getFrameNumber() < frameCount) {
//						surface = new Surface(sw, sh);
//						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//						surface.getGraphics2D().setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//						final TwisterClipController controller = new TwisterClipController(clip);
//						controller.init();
//						final TwisterConfig config = controller.getConfig();
//						runtime = new TwisterRuntime(config);
//						renderer = new DefaultTwisterRenderer(runtime);
//						final Map<Object, Object> hints = new HashMap<Object, Object>();
//						hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
//						renderer.setRenderFactory(new Java2DRenderFactory());
//						renderer.setRenderingHints(hints);
//						renderer.setTile(new Tile(new IntegerVector2D(iw, ih), new IntegerVector2D(tw, th), new IntegerVector2D(tx, ty), new IntegerVector2D(bw, bh)));
//						jobRaf = service.getJobRandomAccessFile(profile.getJobId());
//						final byte[] row = new byte[sw * 4];
//						final int[] data = ((DataBufferInt) surface.getImage().getRaster().getDataBuffer()).getData();
//						int startFrameNumber = 0;
//						if (profile.getFrameNumber() > 0) {
//							long pos = profile.getFrameNumber() * sw * sh * 4;
//							for (int j = 0, k = 0; k < sh; k++) {
//								jobRaf.seek(pos);
//								jobRaf.readFully(row);
//								for (int i = 0; i < row.length; i += 4) {
//									final int argb = Colors.color(row[i + 3], row[i + 0], row[i + 1], row[i + 2]);
//									data[j] = argb;
//									j += 1;
//								}
//								pos += row.length;
//							}
//							startFrameNumber = profile.getFrameNumber() + 1;
//							frameTimeInMillis = profile.getStartTime() * 1000 + (profile.getFrameNumber() * 1000) / profile.getFrameRate();
//							controller.redoAction(frameTimeInMillis, false);
//							renderer.prepareImage(false);
//							renderer.render();
//							renderer.loadSurface(surface);
//						}
//						for (int frameNumber = startFrameNumber; frameNumber < frameCount; frameNumber++) {
//							frameTimeInMillis = profile.getStartTime() * 1000 + (frameNumber * 1000) / profile.getFrameRate();
//							controller.redoAction(frameTimeInMillis, false);
//							renderer.prepareImage(false);
//							renderer.render();
//							renderer.drawSurface(surface.getGraphics2D());
//							long pos = frameNumber * sw * sh * 4;
//							for (int j = 0, k = 0; k < sh; k++) {
//								for (int i = 0; i < row.length; i += 4) {
//									row[i + 0] = (byte) ((data[j] & 0x00FF0000) >> 16);
//									row[i + 1] = (byte) ((data[j] & 0x0000FF00) >> 8);
//									row[i + 2] = (byte) ((data[j] & 0x000000FF) >> 0);
//									row[i + 3] = (byte) ((data[j] & 0xFF000000) >> 24);
//									j += 1;
//								}
//								jobRaf.seek(pos);
//								jobRaf.write(row);
//								pos += row.length;
//								Thread.yield();
//							}
//							setFrameNumber(frameNumber);
//							worker.addTask(new StatusChangedTask(new JobProfile(profile)));
//							if ((((frameNumber + 1) % MAX_FRAMES) == 0) && ((frameCount - frameNumber - 1) > (MAX_FRAMES / 2))) {
//								break;
//							}
//							if (aborted) {
//								break;
//							}
//						}
//					}
//				}
//			}
//			catch (final InterruptedException e) {
//				aborted = true;
//			}
//			catch (final Throwable e) {
//				aborted = true;
//				e.printStackTrace();
//			}
//			finally {
//				if (overlayRenderer != null) {
//					overlayRenderer.dispose();
//					overlayRenderer = null;
//				}
//				if (overlayRuntime != null) {
//					overlayRuntime.dispose();
//					overlayRuntime = null;
//				}
//				if (renderer != null) {
//					renderer.dispose();
//					renderer = null;
//				}
//				if (runtime != null) {
//					runtime.dispose();
//					runtime = null;
//				}
//				if (surface != null) {
//					surface.dispose();
//					surface = null;
//				}
//				if (jobRaf != null) {
//					try {
//						jobRaf.close();
//					}
//					catch (final IOException e) {
//					}
//				}
//				if (clipStream != null) {
//					try {
//						clipStream.close();
//					}
//					catch (final IOException e) {
//					}
//				}
//			}
//			synchronized (LocalJob.this) {
//				if (!aborted) {
//					terminated = true;
//				}
//				if (terminated) {
//					worker.addTask(new TerminatedTask(new JobProfile(profile)));
//				}
//			}
		}
	}

	private class StatusChangedTask implements Runnable {
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StatusChangedTask(final JobProfile jobData) {
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
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StartedTask(final JobProfile jobData) {
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
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected StoppedTask(final JobProfile jobData) {
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
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected TerminatedTask(final JobProfile jobData) {
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
		private final JobProfile jobData;

		/**
		 * @param jobData
		 */
		protected DisposedTask(final JobProfile jobData) {
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
