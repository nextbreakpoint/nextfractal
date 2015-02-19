package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class DispatchService {
	private final ThreadFactory threadFactory;
	private final RenderFactory renderFactory;
	private final List<Thread> threads = new ArrayList<>();
	private final File outDir;
	
	/**
	 * @param outDir
	 * @param threadFactory
	 * @param renderFactory
	 */
	public DispatchService(File outDir, ThreadFactory threadFactory, RenderFactory renderFactory) {
		this.threadFactory = threadFactory; 
		this.renderFactory = renderFactory;
		this.outDir = outDir;
	}
	
	public void dispatch(ExportJob job) {
		synchronized (threads) {
			if (threads.size() < 10) {
				Thread thread = createThread(new ProcessJob(job));
				threads.add(thread);
				job.setDispatched(true);
				thread.start();
			}
		}
	}

	/**
	 * @param runnable
	 * @return
	 */
	protected Thread createThread(Runnable runnable) {
		return threadFactory.newThread(runnable);
	}

	private void processJob(ExportJob job) {
		try {
			System.out.println(job);
			ImageGenerator generator = createImageGenerator(job);
			IntBuffer pixels = generator.renderImage(outDir, job.getProfile().getData());
			job.setResult(new ExportResult(pixels, null));
		} catch (Exception e) {
			job.setResult(new ExportResult(null, e.getMessage()));
		} finally {
			job.setTerminated(true);
		}
		//TODO interrupt generator when job is terminated or suspended
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
					threads.remove(this);
				}
			}
		}
	}
}
