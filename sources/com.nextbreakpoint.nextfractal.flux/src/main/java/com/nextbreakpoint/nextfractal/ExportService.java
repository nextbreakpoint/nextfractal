package com.nextbreakpoint.nextfractal;

import java.util.concurrent.ThreadFactory;

import javafx.application.Platform;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class ExportService {
	private final Worker serviceWorker;
	private ThreadFactory threadFactory;
	private RenderFactory renderFactory;
	private int tileSize;
	
	public ExportService(ThreadFactory threadFactory, RenderFactory renderFactory, int tileSize) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.tileSize = tileSize;
		serviceWorker = new Worker(threadFactory);
		serviceWorker.start();
	}

	public void startSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		serviceWorker.addTask(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						exportSession.setProgress(0.5f);
					}
				});
			}
		});
	}

	public void stopSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}

	public void suspendSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}

	public void resumeSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
	}
}
