package com.nextbreakpoint.nextfractal;

import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class ExportService {
	private ThreadFactory threadFactory;
	private RenderFactory renderFactory;
	private int tileSize;
	
	public ExportService(ThreadFactory threadFactory, RenderFactory renderFactory, int tileSize) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.tileSize = tileSize;
		
	}

	public void startSession(ExportSession exportSession) {
		// TODO Auto-generated method stub
		
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
