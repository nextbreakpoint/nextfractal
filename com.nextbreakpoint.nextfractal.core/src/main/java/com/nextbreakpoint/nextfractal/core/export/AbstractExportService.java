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
package com.nextbreakpoint.nextfractal.core.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractExportService implements ExportService {
	private final List<ExportSessionHolder> holders = new ArrayList<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final ScheduledExecutorService executor;
	
	public AbstractExportService(ThreadFactory threadFactory) {
		executor = Executors.newSingleThreadScheduledExecutor(Objects.requireNonNull(threadFactory));
		executor.scheduleWithFixedDelay(() -> lockAndUpdateSessions(), 1000, 1000, TimeUnit.MILLISECONDS);
	}

//	public void shutdown() {
//		executor.shutdown(); 
//		try {
//			if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
//				executor.shutdownNow(); 
//			}
//		} catch (InterruptedException x) {
//			executor.shutdownNow();
//			Thread.currentThread().interrupt();
//		}
//	}

	public final void shutdown() {
		executor.shutdownNow();
		try {
			executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}

	public final void startSession(ExportSession session) {
		try {
			lock.lock();
			if (session.getState() != ExportState.SUSPENDED) {
				throw new IllegalStateException("Session is not suspended");
			}
			session.setState(ExportState.STARTED);
			session.setCancelled(false);
			holders.add(new ExportSessionHolder(session));
		} finally {
			lock.unlock();
		}
	}

	public final void stopSession(ExportSession session) {
		try {
			lock.lock();
			session.setCancelled(true);
			cancelJobs(session);
		} finally {
			lock.unlock();
		}
	}

	public final void suspendSession(ExportSession session) {
		try {
			lock.lock();
			session.setCancelled(false);
			cancelJobs(session);
		} finally {
			lock.unlock();
		}
	}

	public final void resumeSession(ExportSession session) {
		try {
			lock.lock();
			if (session.getState() != ExportState.SUSPENDED) {
				throw new IllegalStateException("Session is not suspended");
			}
			session.setState(ExportState.STARTED);
			session.setCancelled(false);
		} finally {
			lock.unlock();
		}
	}

	private void lockAndUpdateSessions() {
		try {
			lock.lock();
			updateSessionsInBackground(holders);
		} finally {
			lock.unlock();
		}
	}

	protected abstract void updateSessionsInBackground(List<ExportSessionHolder> holders);

	protected abstract void cancelJobs(ExportSession session);
}
