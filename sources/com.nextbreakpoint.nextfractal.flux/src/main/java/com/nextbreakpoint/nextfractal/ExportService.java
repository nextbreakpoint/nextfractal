package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class ExportService {
	private final List<ExportSession> sessions = new ArrayList<>();
	private final RenderService dispatchService;
	private final ThreadFactory threadFactory;
	private final int tileSize;
	private volatile Thread cleanupThread;
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
		if (cleanupThread == null) {
			cleanupThread = createThread(new CleanupSessions());
			cleanupThread.start();
		}
		if (dispatchThread == null) {
			dispatchThread = createThread(new DispatchSessions());
			dispatchThread.start();
		}
	}

	public void stop() {
		running = false;
		if (cleanupThread != null) {
			cleanupThread.interrupt();
		}
		if (dispatchThread != null) {
			dispatchThread.interrupt();
		}
		if (cleanupThread != null) {
			try {
				cleanupThread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			cleanupThread = null;
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

	public void runSession(ExportSession exportSession) {
		synchronized (sessions) {
			sessions.add(exportSession);
			sessions.notifyAll();
		}
	}

	private void cleanupSessions() {
		synchronized (sessions) {
			for (Iterator<ExportSession> i = sessions.iterator(); i.hasNext();) {
				ExportSession session = i.next();
				if (session.isTerminated()) {
					i.remove();
				}
			}
		}
	}

	private synchronized void dispatchSessions() {
		synchronized (sessions) {
			for (Iterator<ExportSession> i = sessions.iterator(); i.hasNext();) {
				ExportSession session = i.next();
				if (session.isStarted()) {
					dispatchSession(session);
				}
			}
		}
	}

	private void dispatchSession(ExportSession session) {
		session.updateState();
		for (ExportJob job : session.getJobs()) {
			if (isJobReady(job)) {
				dispatchJob(job);
			}
		}
	}

	private boolean isJobReady(ExportJob job) {
		return job.getState() == JobState.READY;
	}
	
	private void dispatchJob(ExportJob job) {
		dispatchService.dispatch(job);
	}
	
	private class CleanupSessions implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (running) {
					cleanupSessions();
					synchronized (sessions) {
						sessions.wait(500);
					}
				}
			} catch (InterruptedException e) {
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
					dispatchSessions();
					synchronized (sessions) {
						sessions.wait(500);
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
