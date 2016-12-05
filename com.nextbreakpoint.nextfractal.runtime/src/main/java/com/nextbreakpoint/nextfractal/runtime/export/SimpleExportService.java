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

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.core.export.*;
import com.nextbreakpoint.nextfractal.core.export.ExportState;
import com.nextbreakpoint.nextfractal.runtime.encoder.RAFEncoderContext;
import javafx.application.Platform;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class SimpleExportService extends AbstractExportService {
	private final Map<String, List<Future<ExportJob>>> futures = new HashMap<>();

	private EventBus eventBus;
	private final ExportRenderer exportRenderer;

	public SimpleExportService(EventBus eventBus, ThreadFactory threadFactory, ExportRenderer exportRenderer) {
		super(threadFactory);
		this.eventBus = Objects.requireNonNull(eventBus);
		this.exportRenderer = Objects.requireNonNull(exportRenderer);
	}

	@Override
	protected void updateSessionsInBackground(List<ExportSessionHolder> holders) {
		List<ExportSessionHolder> completedHolders = holders.stream()
			.peek(holder -> updateSession(holder))
			.filter(holder -> holder.getSession().isStopped())
			.peek(holder -> removeTasks(holder.getSession()))
			.collect(Collectors.toList());
		holders.removeAll(completedHolders);
	}

	@Override
	protected void cancelJobs(ExportSession session) {
		getTasks(session).ifPresent(tasks -> tasks.forEach(task -> task.cancel(true)));
	}

	private void updateSession(ExportSessionHolder holder) {
		ExportSession session = holder.getSession();
		if (session.isStarted()) {
			dispatchJobs(session);
			holder.setState(ExportState.DISPATCHED);
		} else if (session.isInterrupted()) {
			holder.setState(ExportState.STOPPED);
		} else if (session.isCompleted()) {
			holder.setState(ExportState.STOPPED);
		} else if (session.isFailed()) {
			if (session.isCancelled()) {
				holder.setState(ExportState.STOPPED);
			}
		} else {
			getTasks(session).map(tasks -> removeTerminatedTasks(tasks))
				.filter(tasks -> tasks.isEmpty()).ifPresent(tasks -> updateTerminatedSession(holder, session));
		}
		session.updateProgress();
		Platform.runLater(() -> eventBus.postEvent("export-session-updated", session));
	}

	private void updateTerminatedSession(ExportSessionHolder holder, ExportSession session) {
		if (session.isCancelled()) {
            holder.setState(ExportState.INTERRUPTED);
        } else if (isSessionCompleted(session)) {
			updateCompletedSession(holder, session);
		} else {
            holder.setState(ExportState.SUSPENDED);
        }
	}

	private void updateCompletedSession(ExportSessionHolder holder, ExportSession session) {
		Try.of(() -> encodeData(session))
			.onSuccess(s -> holder.setState(ExportState.COMPLETED))
			.onFailure(e -> holder.setState(ExportState.FAILED))
			.execute();
	}

	private List<Future<ExportJob>> removeTerminatedTasks(List<Future<ExportJob>> tasks) {
		tasks.removeAll(getCompletedTasks(tasks));
		return tasks;
	}

	private List<Future<ExportJob>> getCompletedTasks(List<Future<ExportJob>> tasks) {
		return tasks.stream().filter(task -> task.isDone()).collect(Collectors.toList());
	}

	private boolean isSessionCompleted(ExportSession session) {
		return session.getCompletedJobsCount() == session.getJobsCount();
	}

	private void dispatchJobs(ExportSession session) {
		session.getJobs().stream().filter(job -> !job.isCompleted()).forEach(job -> dispatchJob(session, job));
	}

	private void dispatchJob(ExportSession session, ExportJob job) {
		List<Future<ExportJob>> tasks = getTasks(session).orElse(new ArrayList<>());
		updateTasks(session, tasks);
		tasks.add(exportRenderer.dispatch(job));
	}

	private void updateTasks(ExportSession session, List<Future<ExportJob>> tasks) {
		futures.put(session.getSessionId(), tasks);
	}

	private void removeTasks(ExportSession session) {
		futures.remove(session.getSessionId());
	}

	private Optional<List<Future<ExportJob>>> getTasks(ExportSession session) {
		return Optional.ofNullable(futures.get(session.getSessionId()));
	}

	private ExportSession encodeData(ExportSession session) throws IOException, EncoderException {
		try (RandomAccessFile raf = new RandomAccessFile(session.getTmpFile(), "r")) {
			final int frameRate = (int)Math.rint(1 / session.getFrameRate());
			final int imageWidth = session.getSize().getWidth();
			final int imageHeight = session.getSize().getHeight();
			final double stopTime = session.getStopTime();
			final double startTime = session.getStartTime();
			final int frameCount = (int) Math.floor((stopTime - startTime) * frameRate);
			RAFEncoderContext context = new RAFEncoderContext(raf, imageWidth, imageHeight, frameRate, frameCount);
			session.getEncoder().encode(context, session.getFile());
		} finally {
			session.getTmpFile().delete();
		}
		return session;
	}
}
