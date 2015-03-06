package com.nextbreakpoint.nextfractal.runtime.export;

import java.nio.IntBuffer;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.export.ExportJob;
import com.nextbreakpoint.nextfractal.core.export.ExportJobState;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;

public class SimpleExportRenderer implements ExportRenderer {
	private static final Logger logger = Logger.getLogger(SimpleExportRenderer.class.getName());
	private ExecutorCompletionService<ExportJob> service;
	private ThreadFactory threadFactory;
	private RendererFactory renderFactory;
	
	/**
	 * @param threadFactory
	 * @param renderFactory
	 */
	public SimpleExportRenderer(ThreadFactory threadFactory, RendererFactory renderFactory) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(5, threadFactory));
	}
	
	@Override
	public Future<ExportJob> dispatch(ExportJob job) {
		return service.submit(new ProcessJobCallable(job));
	}

	private class ProcessJobCallable implements Callable<ExportJob> {
		private ExportJob job;
		
		public ProcessJobCallable(ExportJob job) {
			this.job = job;
		}

		@Override
		public ExportJob call() throws Exception {
			try {
				logger.fine(job.toString());
				ImageGenerator generator = createImageGenerator(job);
				IntBuffer pixels = generator.renderImage(job.getProfile().getData());
				if (generator.isInterrupted()) {
					job.setState(ExportJobState.INTERRUPTED);
				} else {
					job.writePixels(generator.getSize(), pixels);
					job.setState(ExportJobState.COMPLETED);
				}
			} catch (Throwable e) {
				logger.log(Level.WARNING, "Failed to render tile", e);
				job.setError(e);
				job.setState(ExportJobState.INTERRUPTED);
			}
			return job;
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
	}
}
