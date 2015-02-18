package com.nextbreakpoint.nextfractal;

import java.io.File;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.mandelbrot.ImageGenerator;
import com.nextbreakpoint.nextfractal.render.RenderFactory;

public class DispatchService {
	private ThreadFactory threadFactory;
	private RenderFactory renderFactory;
	private Worker worker;
	private File outDir;
	
	/**
	 * @param threadFactory
	 * @param renderFactory
	 */
	public DispatchService(File outDir, ThreadFactory threadFactory, RenderFactory renderFactory) {
		this.threadFactory = threadFactory; 
		this.renderFactory = renderFactory;
		this.outDir = outDir;
		worker = new Worker(threadFactory);
		worker.start();
	}
	
	/**
	 * 
	 */
	public void dispose() {
		worker.stop();
	}

	public void dispatch(ExportJob job) {
		worker.addTask(new Runnable() {
			@Override
			public void run() {
				ImageGenerator generator = new ImageGenerator(threadFactory, renderFactory, job.getTile());
				generator.renderImage(outDir, job.getProfile().getData());
				//TODO job
				
			}
		});
	}
}
