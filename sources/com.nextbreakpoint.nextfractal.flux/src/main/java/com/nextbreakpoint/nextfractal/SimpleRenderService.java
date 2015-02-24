package com.nextbreakpoint.nextfractal;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class SimpleRenderService implements RenderService {
	private static final Logger logger = Logger.getLogger(SimpleRenderService.class.getName());
	private final ThreadFactory threadFactory;
	private final RenderFactory renderFactory;
	private final List<Thread> threads = new ArrayList<>();
	
	/**
	 * @param threadFactory
	 * @param renderFactory
	 */
	public SimpleRenderService(ThreadFactory threadFactory, RenderFactory renderFactory) {
		this.threadFactory = threadFactory; 
		this.renderFactory = renderFactory;
	}
	
	@Override
	public void dispatch(ExportJob job) {
		synchronized (threads) {
			if (threads.size() < 5) {
				Thread thread = createThread(new ProcessJob(job));
				threads.add(thread);
				job.setDispatched(true);
				thread.start();
			}
		}
	}

	private Thread createThread(Runnable runnable) {
		return threadFactory.newThread(runnable);
	}

	private void processJob(final ExportJob job) {
		try {
			logger.fine(job.toString());
			ImageGenerator generator = createImageGenerator(job);
			generator.setStopCondition(() -> {
				return job.isRequestSuspend() || job.isRequestTerminate();
			});
			if (job.isRequestSuspend()) {
				job.setDispatched(false);
				job.setSuspended(true);
				return;
			}
			if (job.isRequestTerminate()) {
				job.setDispatched(false);
				job.setTerminated(true);
				return;
			}
			IntBuffer pixels = generator.renderImage(job.getProfile().getData());
			job.setResult(new ExportResult(pixels, null));
			job.setDispatched(false);
			job.setTerminated(true);
		} catch (Throwable e) {
			job.setResult(new ExportResult(null, e.getMessage()));
			job.setDispatched(false);
			job.setTerminated(true);
		}
	}
	
	private ImageGenerator createImageGenerator(ExportJob job) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (job.getPluginId().equals(plugin.getId())) {
					return plugin.createImageGenerator(threadFactory, renderFactory, job.getTile());
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private class ProcessJob implements Runnable {
		private ExportJob job;
		
		public ProcessJob(ExportJob job) {
			this.job = job;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				processJob(job);
			} finally {
				synchronized (threads) {
					threads.remove(Thread.currentThread());
				}
			}
		}
	}
}
