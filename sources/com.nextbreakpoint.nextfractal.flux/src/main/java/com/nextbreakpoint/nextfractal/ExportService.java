package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

public class ExportService {
	private static final Logger logger = Logger.getLogger(ExportService.class.getName());
	private final List<ExportSession> sessions = new ArrayList<>();
	private final Map<String, List<Future<ExportJob>>> futures = new HashMap<>();
	private final RenderService dispatchService;
	private final ThreadFactory threadFactory;
	private final int tileSize;
	private volatile Thread dispatchThread;
	private volatile boolean running;
	
	public ExportService(ThreadFactory threadFactory, RenderService dispatchService, int tileSize) {
		this.threadFactory = threadFactory;
		this.dispatchService = dispatchService;
		this.tileSize = tileSize;
	}

	/**
	 * @param runnable
	 * @return
	 */
	protected Thread createThread(Runnable runnable) {
		return threadFactory.newThread(runnable);
	}

	public void start() {
		running = true;
		if (dispatchThread == null) {
			dispatchThread = createThread(new DispatchSessions());
			dispatchThread.start();
		}
	}

	public void stop() {
		running = false;
		if (dispatchThread != null) {
			dispatchThread.interrupt();
		}
		if (dispatchThread != null) {
			try {
				dispatchThread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			dispatchThread = null;
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public void startSession(ExportSession session) {
		synchronized (sessions) {
			logger.info(session.getSessionId() + " -> added");
			session.setState(SessionState.STARTED);
			session.setCancelled(false);
			sessions.add(session);
			sessions.notifyAll();
		}
	}

	public void stopSession(ExportSession session) {
		synchronized (sessions) {
			session.setCancelled(true);
			cancelSession(session);
			sessions.notifyAll();
		}
	}

	public void suspendSession(ExportSession session) {
		synchronized (sessions) {
			session.setCancelled(false);
			cancelSession(session);
			sessions.notifyAll();
		}
	}

	public void resumeSession(ExportSession session) {
		synchronized (sessions) {
			session.setState(SessionState.STARTED);
			session.setCancelled(false);
			sessions.notifyAll();
		}
	}

	private void updateSessions() {
		synchronized (sessions) {
			for (Iterator<ExportSession> i = sessions.iterator(); i.hasNext();) {
				ExportSession session = i.next();
				updateSession(session);
				if (session.isStopped()) {
					logger.info(session.getSessionId() + " -> removed");
					futures.remove(session.getSessionId());
					i.remove();
				}
			}
		}
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

	private class DispatchSessions implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					updateSessions();
					synchronized (sessions) {
						sessions.wait(1000);
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
