package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ExportService {
	private final List<ExportSession> sessions = new ArrayList<>();
	private final Map<String, List<Future<ExportJob>>> futures = new HashMap<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final ScheduledExecutorService executor;
	private final RenderService dispatchService;
	private final int tileSize;
	
	public ExportService(ThreadFactory threadFactory, RenderService dispatchService, int tileSize) {
		this.dispatchService = dispatchService;
		this.tileSize = tileSize;
		executor = Executors.newSingleThreadScheduledExecutor(threadFactory);
		executor.scheduleWithFixedDelay(new UpdateSessionsRunnable(), 1000, 1000, TimeUnit.MILLISECONDS);
	}

	public void shutdown() {
		executor.shutdownNow();
		try {
			executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public void startSession(ExportSession session) {
		session.setState(SessionState.STARTED);
		session.setCancelled(false);
		lock.lock();
		sessions.add(session);
		lock.unlock();
	}

	public void stopSession(ExportSession session) {
		session.setCancelled(true);
		cancelSession(session);
	}

	public void suspendSession(ExportSession session) {
		session.setCancelled(false);
		cancelSession(session);
	}

	public void resumeSession(ExportSession session) {
		session.setState(SessionState.STARTED);
		session.setCancelled(false);
	}

	private void updateSessions() {
		lock.lock();
		for (Iterator<ExportSession> i = sessions.iterator(); i.hasNext();) {
			ExportSession session = i.next();
			updateSession(session);
			if (session.isStopped()) {
				futures.remove(session.getSessionId());
				i.remove();
			}
		}
		lock.unlock();
	}

	private void updateSession(ExportSession session) {
		if (session.isStarted()) {
			dispatchJobs(session);
			session.setState(SessionState.DISPATCHED);
		} else if (session.isInterrupted()) {
			session.setState(SessionState.STOPPED);
		} else if (session.isCompleted()) {
			session.setState(SessionState.STOPPED);
		} else {
			processSession(session);
		}
		session.updateProgress();
	}

	private void processSession(ExportSession session) {
		List<Future<ExportJob>> list = futures.get(session.getSessionId());
		if (list != null) {
			removeTerminatedJobs(list);
			if (list.size() == 0) {
				if (session.isCancelled()) {
					session.setState(SessionState.INTERRUPTED);
				} else if (isSessionCompleted(session)) {
					session.setState(SessionState.COMPLETED);
				} else {
					session.setState(SessionState.SUSPENDED);
				}
			}
		}
	}

	private void removeTerminatedJobs(List<Future<ExportJob>> list) {
		for (Iterator<Future<ExportJob>> i = list.iterator(); i.hasNext();) {
			Future<ExportJob> future = i.next();
			if (future.isCancelled() || future.isDone()) {
				i.remove();
			}
		}
	}

	private boolean isSessionCompleted(ExportSession session) {
		return session.getCompletedJobsCount() == session.getJobsCount();
	}

	private void cancelSession(ExportSession session) {
		List<Future<ExportJob>> list = futures.get(session.getSessionId());
		if (list != null) {
			for (Future<ExportJob> future : list) {
				future.cancel(true);
			}
		}
	}

	private void dispatchJobs(ExportSession session) {
		for (ExportJob job : session.getJobs()) {
			if (!job.isCompleted()) {
				Future<ExportJob> future = dispatchService.dispatch(job);
				List<Future<ExportJob>> list = futures.get(session.getSessionId());
				if (list == null) {
					list = new ArrayList<>();
					futures.put(session.getSessionId(), list);
				}
				list.add(future);
			}
		}
	}

	private class UpdateSessionsRunnable implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			updateSessions();
		}
	}
}
