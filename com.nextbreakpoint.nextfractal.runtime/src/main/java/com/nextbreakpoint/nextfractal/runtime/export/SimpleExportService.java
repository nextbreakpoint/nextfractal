/*
 * NextFractal 1.2.1
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
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.core.export.AbstractExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportJob;
import com.nextbreakpoint.nextfractal.core.export.ExportJobState;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.export.ExportSessionHolder;
import com.nextbreakpoint.nextfractal.core.session.SessionState;
import com.nextbreakpoint.nextfractal.runtime.encoder.RAFEncoderContext;

public class SimpleExportService extends AbstractExportService {
	private final Map<String, List<Future<ExportJob>>> futures = new HashMap<>();
	private final ExportRenderer exportRenderer;

	public SimpleExportService(ThreadFactory threadFactory, ExportRenderer exportRenderer) {
		super(threadFactory);
		this.exportRenderer = exportRenderer;
	}

	@Override
	protected void updateSessionsInBackground(List<ExportSessionHolder> holders) {
		for (Iterator<ExportSessionHolder> i = holders.iterator(); i.hasNext();) {
			ExportSessionHolder holder = i.next();
			updateSession(holder);
			ExportSession session = holder.getSession();
			if (session.isStopped()) {
				removeTasks(session);
				i.remove();
			}
		}
	}

	@Override
	protected void cancelJobs(ExportSession session) {
		List<Future<ExportJob>> tasks = getTasks(session);
		if (tasks != null) {
			for (Future<ExportJob> future : tasks) {
				future.cancel(true);
			}
		}
	}

	private void updateSession(ExportSessionHolder holder) {
		ExportSession session = holder.getSession();
		if (session.isStarted()) {
			dispatchJobs(session);
			holder.setState(SessionState.DISPATCHED);
		} else if (session.isInterrupted()) {
			holder.setState(SessionState.STOPPED);
		} else if (session.isCompleted()) {
			holder.setState(SessionState.STOPPED);
		} else if (session.isFailed()) {
			if (session.isCancelled()) {
				holder.setState(SessionState.STOPPED);
			}
		} else {
			List<Future<ExportJob>> tasks = getTasks(session);
			if (tasks != null) {
				removeTerminatedTasks(tasks);
				if (tasks.size() == 0) {
					if (session.isCancelled()) {
						holder.setState(SessionState.INTERRUPTED);
					} else if (isSessionCompleted(session)) {
						try {
							encodeData(session);
							holder.setState(SessionState.COMPLETED);
						} catch (Exception e) {
							holder.setState(SessionState.FAILED);
						}
					} else {
						holder.setState(SessionState.SUSPENDED);
					}
				}
			}
		}
		session.updateProgress();
	}
	
	private void removeTerminatedTasks(List<Future<ExportJob>> tasks) {
		for (Iterator<Future<ExportJob>> i = tasks.iterator(); i.hasNext();) {
			Future<ExportJob> future = i.next();
			if (future.isDone()) {
				i.remove();
			}
		}
	}

	private boolean isSessionCompleted(ExportSession session) {
		return session.getCompletedJobsCount() == session.getJobsCount();
	}

	private void dispatchJobs(ExportSession session) {
		for (ExportJob job : session.getJobs()) {
			if (!job.isCompleted()) {
				job.setState(ExportJobState.READY);
				List<Future<ExportJob>> tasks = getOrCreateTasks(session);
				Future<ExportJob> future = exportRenderer.dispatch(job);
				tasks.add(future);
			}
		}
	}

	private void removeTasks(ExportSession session) {
		futures.remove(session.getSessionId());
	}

	private List<Future<ExportJob>> getTasks(ExportSession session) {
		List<Future<ExportJob>> task = futures.get(session.getSessionId());
		return task;
	}

	private List<Future<ExportJob>> getOrCreateTasks(ExportSession session) {
		List<Future<ExportJob>> tasks = getTasks(session);
		if (tasks == null) {
			tasks = new ArrayList<>();
			futures.put(session.getSessionId(), tasks);
		}
		return tasks;
	}

	private void encodeData(ExportSession session) throws IOException, EncoderException {
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(session.getTmpFile(), "r");
			final int frameRate = (int)Math.rint(1 / session.getFrameRate());
			final int imageWidth = session.getSize().getWidth();
			final int imageHeight = session.getSize().getHeight();
			final double stopTime = session.getStopTime();
			final double startTime = session.getStartTime();
			final int frameCount = (int) Math.floor((stopTime - startTime) * frameRate);
			session.getEncoder().encode(new RAFEncoderContext(raf, imageWidth, imageHeight, frameRate, frameCount), session.getFile());
		} finally {
			session.getTmpFile().delete();
			if (raf != null) {
				try {
					raf.close();
				} catch (final IOException e) {
				}
			}			
		}
	}
}
