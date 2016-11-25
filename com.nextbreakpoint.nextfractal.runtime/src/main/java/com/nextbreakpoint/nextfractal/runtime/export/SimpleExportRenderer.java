/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.export.ExportJob;
import com.nextbreakpoint.nextfractal.core.export.ExportJobState;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;

import static com.nextbreakpoint.nextfractal.runtime.Plugins.tryPlugin;

public class SimpleExportRenderer implements ExportRenderer {
	private static final Logger logger = Logger.getLogger(SimpleExportRenderer.class.getName());

	private static final int MAX_THREADS = 5;

	private final ThreadFactory threadFactory;
	private final RendererFactory renderFactory;

	private final ExecutorCompletionService<ExportJob> service;

	public SimpleExportRenderer(ThreadFactory threadFactory, RendererFactory renderFactory) {
		this.threadFactory = Objects.requireNonNull(threadFactory);
		this.renderFactory = Objects.requireNonNull(renderFactory);
		service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(MAX_THREADS, threadFactory));
	}
	
	@Override
	public Future<ExportJob> dispatch(ExportJob job) {
		return service.submit(new ProcessExportJob(job));
	}
	
	private ImageGenerator createImageGenerator(ExportJob job) {
		return tryPlugin(job.getPluginId(), plugin -> plugin.createImageGenerator(threadFactory, renderFactory, job.getTile(), false)).orElse(null);
	}

	private class ProcessExportJob implements Callable<ExportJob> {
		private final ExportJob job;
		
		public ProcessExportJob(ExportJob job) {
			this.job = Objects.requireNonNull(job);
			job.setState(ExportJobState.READY);
		}

		@Override
		public ExportJob call() throws Exception {
			return Try.of(() -> processJob(job)).onFailure(e -> processError(e)).get();
		}

		private void processError(Throwable e) {
			logger.log(Level.WARNING, "Failed to render tile", e);
			job.setError(e);
			job.setState(ExportJobState.INTERRUPTED);
		}

		private ExportJob processJob(ExportJob job) throws IOException {
			logger.fine(job.toString());
			Object data = job.getProfile().getData();
			ImageGenerator generator = createImageGenerator(job);
			IntBuffer pixels = generator.renderImage(data);
			if (generator.isInterrupted()) {
                job.setState(ExportJobState.INTERRUPTED);
            } else {
                job.writePixels(generator.getSize(), pixels);
                job.setState(ExportJobState.COMPLETED);
            }
            return job;
		}
	}
}
