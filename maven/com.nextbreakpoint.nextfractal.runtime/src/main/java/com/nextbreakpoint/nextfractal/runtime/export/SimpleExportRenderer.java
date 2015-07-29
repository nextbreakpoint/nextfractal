/*
 * NextFractal 1.1.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
				Object data = job.getProfile().getData();
				IntBuffer pixels = generator.renderImage(data);
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
	}
}
