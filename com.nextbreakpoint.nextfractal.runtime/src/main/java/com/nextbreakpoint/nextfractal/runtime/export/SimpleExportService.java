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
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderHandle;
import com.nextbreakpoint.nextfractal.core.export.AbstractExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportHandle;
import com.nextbreakpoint.nextfractal.core.export.ExportJobHandle;
import com.nextbreakpoint.nextfractal.core.export.ExportJobState;
import com.nextbreakpoint.nextfractal.core.export.ExportProfileBuilder;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportState;
import com.nextbreakpoint.nextfractal.runtime.encoder.RAFEncoderContext;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SimpleExportService extends AbstractExportService {
	private static final Logger logger = Logger.getLogger(SimpleExportService.class.getName());

	private final Map<String, List<Future<ExportJobHandle>>> futures = new HashMap<>();
	private final Map<String, EncoderHandle> handles = new HashMap<>();

	private EventBus eventBus;
	private final ExportRenderer exportRenderer;

	public SimpleExportService(EventBus eventBus, ThreadFactory threadFactory, ExportRenderer exportRenderer) {
		super(threadFactory);
		this.eventBus = Objects.requireNonNull(eventBus);
		this.exportRenderer = Objects.requireNonNull(exportRenderer);
	}

	@Override
	protected Collection<ExportHandle> updateInBackground(Collection<ExportHandle> exportHandles) {
		return exportHandles.stream().peek(this::updateSession).filter(ExportHandle::isFinished).collect(Collectors.toList());
	}

	@Override
	protected void notifyUpdate(Collection<ExportHandle> exportHandles) {
		exportHandles.forEach(exportHandle -> eventBus.postEvent("export-session-state-changed",
			new Object[] { exportHandle.getSession(), exportHandle.getState(), exportHandle.getProgress() }));
	}

	@Override
	protected void resumeTasks(ExportHandle exportHandle) {
		dispatchJobs(exportHandle);
	}

	@Override
	protected void cancelTasks(ExportHandle exportHandle) {
		tasks(exportHandle.getSessionId()).ifPresent(tasks -> tasks.forEach(task -> task.cancel(true)));
	}

	private void updateSession(ExportHandle exportHandle) {
		if (exportHandle.isReady()) {
			openSession(exportHandle);
			resetJobs(exportHandle);
			dispatchJobs(exportHandle);
			exportHandle.setState(ExportState.DISPATCHED);
		} else if (exportHandle.isInterrupted() && isTimeout(exportHandle)) {
			exportHandle.setState(ExportState.FINISHED);
		} else if (exportHandle.isCompleted() && isTimeout(exportHandle)) {
			exportHandle.setState(ExportState.FINISHED);
		} else if (exportHandle.isFailed() && exportHandle.isCancelled() && isTimeout(exportHandle)) {
			exportHandle.setState(ExportState.FINISHED);
		} else if (exportHandle.isDispatched() || exportHandle.isSuspended()) {
			updateDispatchedSession(exportHandle);
		}
		exportHandle.updateProgress();
		if (exportHandle.isFailed() || exportHandle.isFinished()) {
			removeTasks(exportHandle);
			closeSession(exportHandle);
		}
	}

	private boolean isTimeout(ExportHandle exportHandle) {
		return System.currentTimeMillis() - exportHandle.getTimestamp() > 1500;
	}

	private void openSession(ExportHandle exportHandle) {
		try {
			if (!handles.containsKey(exportHandle.getSessionId())) {
				EncoderHandle handle = openEncoder(exportHandle);
				handles.put(exportHandle.getSessionId(), handle);
			}
		} catch (Exception e) {
			exportHandle.setState(ExportState.FAILED);
		}
	}

	private EncoderHandle openEncoder(ExportHandle exportHandle) throws IOException, EncoderException {
		final RandomAccessFile raf = new RandomAccessFile(exportHandle.getTmpFile(), "r");
		final int frameRate = (int) Math.rint(1 / exportHandle.getFrameRate());
		final int imageWidth = exportHandle.getSize().getWidth();
		final int imageHeight = exportHandle.getSize().getHeight();
		final RAFEncoderContext context = new RAFEncoderContext(raf, imageWidth, imageHeight, frameRate);
		return exportHandle.getEncoder().open(context, exportHandle.getFile());
	}

	private void closeSession(ExportHandle exportHandle) {
		try {
			EncoderHandle handle = handles.remove(exportHandle.getSessionId());
			if (handle != null) {
				closeEncoder(exportHandle, handle);
			}
		} catch (Exception e) {
			exportHandle.setState(ExportState.FAILED);
		}
	}

	private void closeEncoder(ExportHandle exportHandle, EncoderHandle encoderHandle) throws IOException, EncoderException {
		try {
			exportHandle.getEncoder().close(encoderHandle);
		} finally {
			exportHandle.getTmpFile().delete();
		}
	}

	private void updateDispatchedSession(ExportHandle exportHandle) {
		tasks(exportHandle.getSessionId()).map(this::removeTerminatedTasks)
			.filter(List::isEmpty).ifPresent(tasks -> updateSessionState(exportHandle));
	}

	private void updateSessionState(ExportHandle exportHandle) {
		if (exportHandle.isCancelled()) {
			exportHandle.setState(ExportState.INTERRUPTED);
		} else if (exportHandle.isSessionCompleted()) {
			logger.info("Frame " + (exportHandle.getFrameNumber() + 1) + " of " + exportHandle.getFrameCount());
			int index = exportHandle.getFrameNumber();
			tryEncodeFrame(exportHandle, index, 1)
				.onSuccess(s -> exportHandle.setState(ExportState.COMPLETED))
				.onFailure(e -> exportHandle.setState(ExportState.FAILED))
				.execute();
        } else if (exportHandle.isFrameCompleted()) {
			int index = exportHandle.getFrameNumber();
			int count = 0;
			do {
				logger.info("Frame " + (exportHandle.getFrameNumber() + 1) + " of " + exportHandle.getFrameCount());
				exportHandle.nextFrame();
				count += 1;
			} while (count < 100 && !isLastFrame(exportHandle) && !isKeyFrame(exportHandle) && !isTimeAnimation(exportHandle));
			tryEncodeFrame(exportHandle, index, count)
					.onSuccess(s -> exportHandle.setState(ExportState.READY))
					.onFailure(e -> exportHandle.setState(ExportState.FAILED))
					.execute();
		} else {
			exportHandle.setState(ExportState.SUSPENDED);
        }
	}

	private boolean isLastFrame(ExportHandle exportHandle) {
		return exportHandle.getFrameNumber() == exportHandle.getFrameCount() - 1;
	}

	private boolean isKeyFrame(ExportHandle exportHandle) {
		return exportHandle.getSession().getFrames().get(exportHandle.getFrameNumber()).isKeyFrame();
	}

	private boolean isTimeAnimation(ExportHandle exportHandle) {
		return exportHandle.getSession().getFrames().get(exportHandle.getFrameNumber()).isTimeAnimation();
	}

	private void resetJobs(ExportHandle exportHandle) {
		exportHandle.getJobs().stream().forEach(job -> job.setState(ExportJobState.READY));
	}

	private Try<ExportHandle, Exception> tryEncodeFrame(ExportHandle exportHandle, int index, int count) {
		return Try.of(() -> encodeData(exportHandle, index, count));
	}

	private List<Future<ExportJobHandle>> removeTerminatedTasks(List<Future<ExportJobHandle>> tasks) {
		tasks.removeAll(tasks.stream().filter(Future::isDone).collect(Collectors.toList()));
		return tasks;
	}

	private void dispatchJobs(ExportHandle exportHandle) {
		exportHandle.getJobs().stream().filter(job -> !job.isCompleted()).forEach(job -> dispatchTasks(exportHandle, job));
	}

	private void dispatchTasks(ExportHandle exportHandle, ExportJobHandle exportJob) {
		List<Future<ExportJobHandle>> tasks = tasks(exportHandle.getSessionId()).orElse(new ArrayList<>());
		ExportProfileBuilder builder = ExportProfileBuilder.fromProfile(exportJob.getJob().getProfile());
		builder.withPluginId(exportHandle.getCurrentPluginId());
		builder.withMetadata(exportHandle.getCurrentMetadata());
		builder.withScript(exportHandle.getCurrentScript());
		exportJob.setProfile(builder.build());
		tasks.add(exportRenderer.dispatch(exportJob));
		futures.put(exportHandle.getSessionId(), tasks);
	}

	private void removeTasks(ExportHandle exportHandle) {
		futures.remove(exportHandle.getSessionId());
	}

	private Optional<List<Future<ExportJobHandle>>> tasks(String sessionId) {
		return Optional.ofNullable(futures.get(sessionId));
	}

	private ExportHandle encodeData(ExportHandle exportHandle, int index, int count) throws IOException, EncoderException {
		exportHandle.getEncoder().encode(handles.get(exportHandle.getSessionId()), index, count);
		return exportHandle;
	}
}
