package com.nextbreakpoint.nextfractal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class ExportService {
	private final List<ExportSession> sessions = new ArrayList<>();
	private ThreadFactory threadFactory;
	private RenderFactory renderFactory;
	private volatile Thread thread;
	private volatile boolean running;
	private int tileSize;
	
	public ExportService(ThreadFactory threadFactory, RenderFactory renderFactory, int tileSize) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
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
		if (thread == null) {
			thread = createThread(new ProcessSessions());
			running = true;
			thread.start();
		}
	}

	public void stop() {
		running = false;
		if (thread != null) {
			thread.interrupt();
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			thread = null;
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public synchronized void runSession(ExportSession exportSession) {
		sessions.add(exportSession);
		notify();
	}

	protected synchronized ExportSession findOneSession() {
		for (ExportSession session : sessions) {
			if (!session.isSuspended()) {
				return session;
			}
		}
		return null;
	}

	protected synchronized void terminateSession(ExportSession exportSession) {
		sessions.remove(exportSession);
	}

	protected synchronized void waitForSession() {
		try {
			wait(1000);
		} catch (InterruptedException e) {
		}
	}

	protected void processSession(ExportSession session) {
		for (ExportJob job : session.getJobs()) {
			if (isSessionValid(session)) {
				break;
			}
			processJob(job);
		}
		session.updateState();
	}

	private boolean isSessionValid(ExportSession session) {
		return session.isTerminated() || session.isSuspended();
	}

	protected void processJob(ExportJob job) {
		// TODO Auto-generated method stub
		// 1. wait until a thread is available to process the job
		// 2. pass the job to the thread
	}

	private class ProcessSessions implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while (running) {
				ExportSession exportSession = null;
				while (running && (exportSession = findOneSession()) == null) {
					waitForSession();
				}
				if (running && exportSession != null) {
					processSession(exportSession);
					if (exportSession.isTerminated()) {
						terminateSession(exportSession);
					}
				}
			}
		}
	}
}
