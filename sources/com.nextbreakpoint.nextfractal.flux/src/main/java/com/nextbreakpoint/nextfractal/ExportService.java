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
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			thread = null;
		}
	}

	public synchronized void startSession(ExportSession exportSession) {
		sessions.add(exportSession);
		notify();
	}

	public synchronized void stopSession(ExportSession exportSession) {
		sessions.remove(exportSession);
		notify();
	}
	
	public synchronized void resumeSession(ExportSession exportSession) {
		sessions.add(exportSession);
		notify();
	}

	public synchronized void suspendSession(ExportSession exportSession) {
		sessions.remove(exportSession);
		notify();
	}

	public int getTileSize() {
		return tileSize;
	}

	protected ExportSession findOneSession() {
		synchronized (ExportService.this) {
			for (ExportSession session : sessions) {
				if (!session.isSuspended()) {
					return session;
				}
			}
		}
		return null;
	}

	private void terminateSession(ExportSession exportSession) {
		synchronized (ExportService.this) {
			sessions.remove(exportSession);
		}
	}

	protected void processSession(ExportSession session) {
		for (ExportJob job : session.getJobs()) {
			if (session.isTerminated() || session.isSuspended()) {
				return;
			}
			processJob(job);
		}
		session.updateState();
	}

	protected void processJob(ExportJob job) {
		// TODO Auto-generated method stub
		
	}

	private class ProcessSessions implements Runnable {
		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while (running) {
				ExportSession exportSession = null;
				exportSession = findOneSession();
				if (exportSession != null) {
					if (exportSession.isTerminated()) {
						terminateSession(exportSession);						
					} else {
						processSession(exportSession);
					}
				} else {
					synchronized (ExportService.this) {
						try {
							ExportService.this.wait(1000);
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
	}
}
