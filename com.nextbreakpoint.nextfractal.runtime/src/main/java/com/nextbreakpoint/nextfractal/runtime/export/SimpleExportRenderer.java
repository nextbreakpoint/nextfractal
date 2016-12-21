/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.export.ExportJobHandle;
import com.nextbreakpoint.nextfractal.core.export.ExportJobState;
import com.nextbreakpoint.nextfractal.core.export.ExportProfile;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;

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

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class SimpleExportRenderer implements ExportRenderer {
	private static final Logger logger = Logger.getLogger(SimpleExportRenderer.class.getName());

	private static final int MAX_THREADS = Math.max(Runtime.getRuntime().availableProcessors() * 2, 2);

	private final ThreadFactory threadFactory;
	private final RendererFactory renderFactory;

	private final ExecutorCompletionService<ExportJobHandle> service;

	public SimpleExportRenderer(ThreadFactory threadFactory, RendererFactory renderFactory) {
		this.threadFactory = Objects.requireNonNull(threadFactory);
		this.renderFactory = Objects.requireNonNull(renderFactory);
		service = new ExecutorCompletionService<>(Executors.newFixedThreadPool(MAX_THREADS, threadFactory));
	}
	
	@Override
	public Future<ExportJobHandle> dispatch(ExportJobHandle job) {
		return service.submit(new ProcessExportJob(job));
	}
	
	private ImageGenerator createImageGenerator(ExportJobHandle job) {
		return tryFindFactory(job.getProfile().getPluginId()).map(plugin -> plugin.createImageGenerator(threadFactory, renderFactory, job.getJob().getTile(), false)).orElse(null);
	}

	private class ProcessExportJob implements Callable<ExportJobHandle> {
		private final ExportJobHandle job;
		
		public ProcessExportJob(ExportJobHandle job) {
			this.job = Objects.requireNonNull(job);
			job.setState(ExportJobState.READY);
		}

		@Override
		public ExportJobHandle call() throws Exception {
			return Try.of(() -> processJob(job)).onFailure(e -> processError(e)).orElse(job);
		}

		private void processError(Throwable e) {
			logger.log(Level.WARNING, "Failed to render tile", e);
			job.setError(e);
			job.setState(ExportJobState.FAILED);
		}

		private ExportJobHandle processJob(ExportJobHandle job) throws IOException {
			logger.fine(job.toString());
			ExportProfile profile = job.getProfile();
			ImageGenerator generator = createImageGenerator(job);
			IntBuffer pixels = generator.renderImage(profile.getScript(), profile.getMetadata());
			if (generator.isInterrupted()) {
                job.setState(ExportJobState.INTERRUPTED);
            } else {
                job.getJob().writePixels(generator.getSize(), pixels);
                job.setState(ExportJobState.COMPLETED);
            }
            return job;
		}
	}
}
