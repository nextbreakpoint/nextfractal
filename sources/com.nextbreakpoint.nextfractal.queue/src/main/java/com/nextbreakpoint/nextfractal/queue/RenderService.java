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
package com.nextbreakpoint.nextfractal.queue;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionReference;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.core.util.Worker;
import com.nextbreakpoint.nextfractal.queue.clip.ClipPreview;
import com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow;
import com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow;
import com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow;
import com.nextbreakpoint.nextfractal.queue.spool.DefaultJobService;
import com.nextbreakpoint.nextfractal.queue.spool.JobData;
import com.nextbreakpoint.nextfractal.queue.spool.JobListener;
import com.nextbreakpoint.nextfractal.queue.spool.JobService;
import com.nextbreakpoint.nextfractal.queue.spool.JobServiceListener;
import com.nextbreakpoint.nextfractal.queue.spool.SpoolJobInterface;
import com.nextbreakpoint.nextfractal.queue.spool.job.CopyProcessSpoolJob;
import com.nextbreakpoint.nextfractal.queue.spool.job.CopyProcessSpoolJobFactory;
import com.nextbreakpoint.nextfractal.queue.spool.job.PostProcessSpoolJob;
import com.nextbreakpoint.nextfractal.queue.spool.job.PostProcessSpoolJobFactory;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;

/**
 * @author Andrea Medeghini
 */
public class RenderService {
	public static final int SERVICE_COPY_PROCESS = 0;
	public static final int SERVICE_POST_PROCESS = 1;
	public static final int SERVICE_PROCESS = 2;
	private final Worker spoolWorker = new Worker(new DefaultThreadFactory("Spool Worker", true, Thread.NORM_PRIORITY));
	private final Worker serviceWorker = new Worker(new DefaultThreadFactory("Service Worker", true, Thread.NORM_PRIORITY));
	private final Worker previewWorker = new Worker(new DefaultThreadFactory("Preview Worker", true, Thread.NORM_PRIORITY));
	private final HashMap<Integer, String> postProcessJobs = new HashMap<Integer, String>();
	private final HashMap<Integer, String> copyProcessJobs = new HashMap<Integer, String>();
	private final HashMap<Integer, String> processJobs = new HashMap<Integer, String>();
	private final HashMap<Integer, ClipPreview> previews = new HashMap<Integer, ClipPreview>();
	private final List<JobServiceListener> listeners = new ArrayList<JobServiceListener>();
	private JobService<? extends SpoolJobInterface> processService;
	private final JobService<PostProcessSpoolJob> postProcessService;
	private final JobService<CopyProcessSpoolJob> copyProcessService;
	private final DefaultJobListener jobListener = new DefaultJobListener();
	private final DefaultJobServiceListener postProcessServicelistener = new DefaultJobServiceListener(SERVICE_COPY_PROCESS);
	private final DefaultJobServiceListener copyProcessServicelistener = new DefaultJobServiceListener(SERVICE_POST_PROCESS);
	private final DefaultJobServiceListener processServicelistener = new DefaultJobServiceListener(SERVICE_PROCESS);
	private final List<PreviewListener> previewListeners = new LinkedList<PreviewListener>();
	private ExtensionReference serviceReference;
	private final LibraryService service;

	/**
	 * @param service
	 * @param jobService
	 * @throws ExtensionException
	 * @throws ExtensionNotFoundException
	 */
	public RenderService(final LibraryService service, final ExtensionReference serviceReference) throws ExtensionException {
		this.service = service;
		this.serviceReference = serviceReference;
		service.addServiceListener(new DefaultLibraryServiceListener());
		postProcessService = new DefaultJobService<PostProcessSpoolJob>(SERVICE_POST_PROCESS, "PostProcessor", new PostProcessSpoolJobFactory(service, spoolWorker), spoolWorker);
		copyProcessService = new DefaultJobService<CopyProcessSpoolJob>(SERVICE_COPY_PROCESS, "CopyProcessor", new CopyProcessSpoolJobFactory(service, spoolWorker), spoolWorker);
		processService = RenderServiceRegistry.getInstance().getSpoolRegistry().getExtension(serviceReference.getExtensionId()).createExtensionRuntime().getJobService(SERVICE_PROCESS, service, spoolWorker);
		postProcessService.addServiceListener(postProcessServicelistener);
		copyProcessService.addServiceListener(copyProcessServicelistener);
		processService.addServiceListener(processServicelistener);
		postProcessService.start();
		copyProcessService.start();
		processService.start();
		previewWorker.start();
		serviceWorker.start();
		spoolWorker.start();
	}

	/**
	 * @param jobService
	 */
	public synchronized void setJobServiceReference(final ExtensionReference serviceReference) {
		this.serviceReference = serviceReference;
		serviceWorker.addTask(new ServiceTask<Object>(new ServiceCallback<Object>() {
				@Override
				public LibraryService execute(final LibraryService service) throws Exception {
				if (processService != null) {
					processService.stop();
					processService.removeServiceListener(processServicelistener);
				}
				processJobs.clear();
				processService = RenderServiceRegistry.getInstance().getSpoolRegistry().getExtension(serviceReference.getExtensionId()).createExtensionRuntime().getJobService(SERVICE_PROCESS, service, spoolWorker);
				processService.addServiceListener(processServicelistener);
				processService.start();
				return null;
				}

			@Override
			public void executed(final Object value) {
				}

			@Override
			public void failed(final Throwable throwable) {
				}
						}));
	}

	/**
	 * @return the serviceReference
	 */
	public synchronized ExtensionReference getJobServiceReference() {
		return serviceReference;
	}

	/**
	 * 
	 */
	public void start() {
		spoolWorker.start();
		serviceWorker.start();
		previewWorker.start();
		postProcessService.start();
		copyProcessService.start();
		if (processService != null) {
			processService.start();
		}
	}

	/**
	 * 
	 */
	public void stop() {
		spoolWorker.stop();
		serviceWorker.stop();
		previewWorker.stop();
		copyProcessService.stop();
		postProcessService.stop();
		if (processService != null) {
			processService.stop();
		}
	}

	/**
	 * @return
	 */
	public LibraryService getLibraryService() {
		return service;
	}

	/**
	 * @param task
	 */
	public <E> void execute(final ServiceCallback<E> callback) {
		serviceWorker.addTask(new ServiceTask<E>(callback));
	}

	/**
	 * @param listener
	 */
	public void addServiceListener(final LibraryServiceListener listener) {
		service.addServiceListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removeServiceListener(final LibraryServiceListener listener) {
		service.removeServiceListener(listener);
	}

	/**
	 * @param listener
	 */
	public void addJobServiceListener(final JobServiceListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	/**
	 * @param listener
	 */
	public void removeJobServiceListener(final JobServiceListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * @param listener
	 */
	public void addPreviewListener(final PreviewListener listener) {
		synchronized (previewListeners) {
			previewListeners.add(listener);
		}
	}

	/**
	 * @param listener
	 */
	public void removePreviewListener(final PreviewListener listener) {
		synchronized (previewListeners) {
			previewListeners.remove(listener);
		}
	}

	/**
	 * 
	 */
	protected void firePreviewUpdated(final int clipId) {
		synchronized (previewListeners) {
			for (PreviewListener listener : previewListeners) {
				listener.updated(clipId);
			}
		}
	}

	/**
	 * @return
	 */
	public ClipPreview getClipPreview(final int clipId) {
		return previews.get(clipId);
	}

	private class ServiceTask<T> implements Runnable {
		private final ServiceCallback<T> callback;

		/**
		 * @param callback
		 * @param hasValue
		 */
		public ServiceTask(final ServiceCallback<T> callback) {
			this.callback = callback;
		}

		@Override
		public final void run() {
			try {
				callback.executed(callback.execute(service));
			}
			catch (final Exception e) {
				e.printStackTrace();
				callback.failed(e);
			}
		}
	}

	public interface ServiceCallback<T> {
		/**
		 * @param service
		 * @return
		 * @throws Exception
		 */
		public T execute(LibraryService service) throws Exception;

		/**
		 * @param throwable
		 */
		public void failed(Throwable throwable);

		/**
		 * @param value
		 */
		public void executed(T value);
	}

	private class DefaultLibraryServiceListener implements LibraryServiceListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#clipCreated(com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow)
		 */
		@Override
		public void clipCreated(final RenderClipDataRow clip) {
			ClipPreview preview = previews.get(clip.getClipId());
			if (preview == null) {
				preview = new DefaultClipPreview(clip.getClipId());
				previews.put(clip.getClipId(), preview);
			}
			preview.update();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#clipDeleted(com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow)
		 */
		@Override
		public void clipDeleted(final RenderClipDataRow clip) {
			ClipPreview preview = previews.remove(clip.getClipId());
			if (preview != null) {
				preview.dispose();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#clipUpdated(com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow)
		 */
		@Override
		public void clipUpdated(final RenderClipDataRow clip) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#clipUpdated(com.nextbreakpoint.nextfractal.queue.clip.RenderClipDataRow)
		 */
		@Override
		public void clipLoaded(final RenderClipDataRow clip) {
			ClipPreview preview = previews.get(clip.getClipId());
			if (preview == null) {
				preview = new DefaultClipPreview(clip.getClipId());
				previews.put(clip.getClipId(), preview);
			}
			preview.update();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobCreated(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobCreated(final RenderJobDataRow job) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobDeleted(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobDeleted(final RenderJobDataRow job) {
			if (job.isPostProcess()) {
				final String jobId = postProcessJobs.remove(job.getJobId());
				if (jobId != null) {
					postProcessService.deleteJob(jobId);
				}
			}
			else if (job.isCopyProcess()) {
				final String jobId = copyProcessJobs.remove(job.getJobId());
				if (jobId != null) {
					copyProcessService.deleteJob(jobId);
				}
			}
			else if (job.isProcess()) {
				final String jobId = processJobs.remove(job.getJobId());
				if (jobId != null) {
					processService.deleteJob(jobId);
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobStarted(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobStarted(final RenderJobDataRow job) {
			if (job.isPostProcess()) {
				if (!postProcessJobs.containsKey(job.getJobId())) {
					final String jobId = postProcessService.createJob(jobListener);
					postProcessService.setJobData(jobId, job, job.getFrameNumber());
					postProcessJobs.put(job.getJobId(), jobId);
					if (jobId != null) {
						postProcessService.runJob(jobId);
					}
				}
				else {
					final String jobId = postProcessJobs.get(job.getJobId());
					if (jobId != null) {
						postProcessService.runJob(jobId);
					}
				}
			}
			else if (job.isCopyProcess()) {
				if (!copyProcessJobs.containsKey(job.getJobId())) {
					final String jobId = copyProcessService.createJob(jobListener);
					copyProcessService.setJobData(jobId, job, job.getFrameNumber());
					copyProcessJobs.put(job.getJobId(), jobId);
					if (jobId != null) {
						copyProcessService.runJob(jobId);
					}
				}
				else {
					final String jobId = copyProcessJobs.get(job.getJobId());
					if (jobId != null) {
						copyProcessService.runJob(jobId);
					}
				}
			}
			else if (job.isProcess()) {
				if (!processJobs.containsKey(job.getJobId())) {
					final String jobId = processService.createJob(jobListener);
					processService.setJobData(jobId, job, job.getFrameNumber());
					processJobs.put(job.getJobId(), jobId);
					if (jobId != null) {
						processService.runJob(jobId);
					}
				}
				else {
					final String jobId = processJobs.get(job.getJobId());
					if (jobId != null) {
						processService.runJob(jobId);
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobAborted(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobAborted(final RenderJobDataRow job) {
			if (job.isPostProcess()) {
				final String jobId = postProcessJobs.get(job.getJobId());
				if (jobId != null) {
					postProcessService.abortJob(jobId);
				}
			}
			else if (job.isCopyProcess()) {
				final String jobId = copyProcessJobs.get(job.getJobId());
				if (jobId != null) {
					copyProcessService.abortJob(jobId);
				}
			}
			else if (job.isProcess()) {
				final String jobId = processJobs.get(job.getJobId());
				if (jobId != null) {
					processService.abortJob(jobId);
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobStopped(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobStopped(final RenderJobDataRow job) {
			if (job.isPostProcess()) {
				final String jobId = postProcessJobs.get(job.getJobId());
				if (jobId != null) {
					postProcessService.stopJob(jobId);
				}
			}
			else if (job.isCopyProcess()) {
				final String jobId = copyProcessJobs.get(job.getJobId());
				if (jobId != null) {
					copyProcessService.stopJob(jobId);
				}
			}
			else if (job.isProcess()) {
				final String jobId = processJobs.get(job.getJobId());
				if (jobId != null) {
					processService.stopJob(jobId);
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobUpdated(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobUpdated(final RenderJobDataRow job) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#jobResumed(com.nextbreakpoint.nextfractal.queue.job.RenderJobDataRow)
		 */
		@Override
		public void jobResumed(final RenderJobDataRow job) {
			if (job.isPostProcess()) {
				if (!postProcessJobs.containsKey(job.getJobId())) {
					final String jobId = postProcessService.createJob(jobListener);
					postProcessService.setJobData(jobId, job, job.getFrameNumber());
					postProcessJobs.put(job.getJobId(), jobId);
				}
			}
			else if (job.isCopyProcess()) {
				if (!copyProcessJobs.containsKey(job.getJobId())) {
					final String jobId = copyProcessService.createJob(jobListener);
					copyProcessService.setJobData(jobId, job, job.getFrameNumber());
					copyProcessJobs.put(job.getJobId(), jobId);
				}
			}
			else if (job.isProcess()) {
				if (!processJobs.containsKey(job.getJobId())) {
					final String jobId = processService.createJob(jobListener);
					processService.setJobData(jobId, job, job.getFrameNumber());
					processJobs.put(job.getJobId(), jobId);
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#profileCreated(com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow)
		 */
		@Override
		public void profileCreated(final RenderProfileDataRow profile) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#profileDeleted(com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow)
		 */
		@Override
		public void profileDeleted(final RenderProfileDataRow profile) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#profileUpdated(com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow)
		 */
		@Override
		public void profileUpdated(final RenderProfileDataRow profile) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.LibraryServiceListener#profileLoaded(com.nextbreakpoint.nextfractal.queue.profile.RenderProfileDataRow)
		 */
		@Override
		public void profileLoaded(final RenderProfileDataRow profile) {
		}
	}

	private class DefaultJobListener implements JobListener {
		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#updated(String, JobData)
		 */
		@Override
		public void updated(final String jobId, final JobData jobData) {
			execute(new ServiceCallback<Object>() {
				@Override
				public Object execute(final LibraryService service) throws Exception {
					try {
						service.processUpdated(jobData);
					}
					catch (Exception e) {
					}
					return null;
				}

				@Override
				public void executed(final Object value) {
				}

				@Override
				public void failed(final Throwable throwable) {
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#started(String, JobData)
		 */
		@Override
		public void started(final String jobId, final JobData jobData) {
			execute(new ServiceCallback<Object>() {
				@Override
				public Object execute(final LibraryService service) throws Exception {
					try {
						service.processStarted(jobData);
					}
					catch (Exception e) {
					}
					return null;
				}

				@Override
				public void executed(final Object value) {
				}

				@Override
				public void failed(final Throwable throwable) {
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#stopped(String, JobData)
		 */
		@Override
		public void stopped(final String jobId, final JobData jobData) {
			execute(new ServiceCallback<Object>() {
				@Override
				public Object execute(final LibraryService service) throws Exception {
					try {
						service.processStopped(jobData);
					}
					catch (Exception e) {
					}
					return null;
				}

				@Override
				public void executed(final Object value) {
				}

				@Override
				public void failed(final Throwable throwable) {
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#terminated(String, JobData)
		 */
		@Override
		public void terminated(final String jobId, final JobData jobData) {
			execute(new ServiceCallback<Object>() {
				@Override
				public Object execute(final LibraryService service) throws Exception {
					try {
						service.processTerminated(jobData);
					}
					catch (Exception e) {
					}
					return null;
				}

				@Override
				public void executed(final Object value) {
				}

				@Override
				public void failed(final Throwable throwable) {
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.spool.JobListener#terminated(String, JobData)
		 */
		@Override
		public void disposed(final String jobId, final JobData jobData) {
			execute(new ServiceCallback<Object>() {
				@Override
				public Object execute(final LibraryService service) throws Exception {
					try {
						service.processDeleted(jobData);
					}
					catch (Exception e) {
					}
					return null;
				}

				@Override
				public void executed(final Object value) {
				}

				@Override
				public void failed(final Throwable throwable) {
				}
			});
		}
	}

	private class DefaultJobServiceListener implements JobServiceListener {
		private final int serviceId;

		/**
		 * @param serviceId
		 */
		public DefaultJobServiceListener(final int serviceId) {
			this.serviceId = serviceId;
		}

		@Override
		public void stateChanged(final int serviceId, final int status, final String message) {
			synchronized (listeners) {
				for (JobServiceListener listener : listeners) {
					listener.stateChanged(DefaultJobServiceListener.this.serviceId, status, message);
				}
			}
		}
	}

	private class DefaultClipPreview implements ClipPreview {
		private final int clipId;
		private Surface surface;

		/**
		 * @param clipId
		 */
		public DefaultClipPreview(final int clipId) {
			this.clipId = clipId;
			surface = new Surface(20, 20);
		}

		private void render(final int clipId) {
			previewWorker.addTask(new Runnable() {
				@Override
				public void run() {
					try {
						RenderClipDataRow dataRow = service.getClip(clipId);
						final TwisterClip clip = service.loadClip(dataRow);
						if (clip.getSequenceCount() > 0) {
							TwisterRuntime runtime = new TwisterRuntime(clip.getSequence(0).getFinalConfig());
							DefaultTwisterRenderer renderer = new DefaultTwisterRenderer(runtime);
							final IntegerVector2D size = new IntegerVector2D(surface.getWidth(), surface.getHeight());
							renderer.setTile(new Tile(size, size, new IntegerVector2D(0, 0), new IntegerVector2D(0, 0)));
							renderer.prepareImage(false);
							renderer.startRenderer();
							renderer.joinRenderer();
							renderer.drawSurface(surface.getGraphics2D());
							renderer.dispose();
							firePreviewUpdated(clipId);
						}
					}
					catch (Exception e) {
					}
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.clip.ClipPreview#getImage()
		 */
		@Override
		public Image getImage() {
			return surface.getImage();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.clip.ClipPreview#dispose()
		 */
		@Override
		public void dispose() {
			if (surface != null) {
				surface.dispose();
				surface = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.queue.clip.ClipPreview#update()
		 */
		@Override
		public void update() {
			render(clipId);
		}
	}
}
