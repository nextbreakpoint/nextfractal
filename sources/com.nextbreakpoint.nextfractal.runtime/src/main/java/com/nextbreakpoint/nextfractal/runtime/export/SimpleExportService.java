package com.nextbreakpoint.nextfractal.runtime.export;

import java.io.IOException;
import java.io.RandomAccessFile;
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

import com.nextbreakpoint.nextfractal.core.encoder.EncoderException;
import com.nextbreakpoint.nextfractal.core.export.ExportJob;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.session.SessionState;
import com.nextbreakpoint.nextfractal.runtime.encoder.RAFEncoderContext;

public class SimpleExportService implements ExportService {
	private final List<ExportSession> sessions = new ArrayList<>();
	private final Map<String, List<Future<ExportJob>>> futures = new HashMap<>();
	private final ReentrantLock lock = new ReentrantLock();
	private final ScheduledExecutorService executor;
	private final ExportRenderer exportRenderer;
	private final int tileSize;
	
	public SimpleExportService(ThreadFactory threadFactory, ExportRenderer exportRenderer, int tileSize) {
		this.exportRenderer = exportRenderer;
		this.tileSize = tileSize;
		executor = Executors.newSingleThreadScheduledExecutor(threadFactory);
		executor.scheduleWithFixedDelay(new UpdateSessionsRunnable(), 1000, 1000, TimeUnit.MILLISECONDS);
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
		} else if (session.isFailed()) {
			if (session.isCancelled()) {
				session.setState(SessionState.STOPPED);
			}
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
					try {
						encodeData(session);
						session.setState(SessionState.COMPLETED);
					} catch (Exception e) {
						session.setState(SessionState.FAILED);
					}
				} else {
					session.setState(SessionState.SUSPENDED);
				}
			}
		}
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
				Future<ExportJob> future = exportRenderer.dispatch(job);
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
