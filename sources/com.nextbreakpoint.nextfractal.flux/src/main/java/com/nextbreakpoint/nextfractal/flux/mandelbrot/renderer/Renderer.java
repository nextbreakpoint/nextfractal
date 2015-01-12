/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.flux.core.Worker;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.strategy.JuliaRendererStrategy;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.strategy.MandelbrotRendererStrategy;

/**
 * @author Andrea Medeghini
 */
public class Renderer {
	public static final int MODE_CALCULATE = 0x01;
	public static final int MODE_REFRESH = 0x02;
	protected final ThreadFactory threadFactory;
	protected final MandelbrotFractal rendererFractal;
	protected final RendererData rendererData;
	protected final Worker rendererWorker;
	protected volatile RendererDelegate rendererDelegate;
	protected volatile RendererStrategy rendererStrategy;
	protected volatile boolean aborted;
	protected volatile float progress;
	protected int width;
	protected int height;
	protected int size;

	/**
	 * @param rendererDelegate
	 * @param rendererFractal
	 * @param width
	 * @param height
	 */
	public Renderer(ThreadFactory threadFactory, MandelbrotFractal rendererFractal, int width, int height) {
		this.threadFactory = threadFactory;
		this.rendererFractal = rendererFractal;
		this.rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		this.rendererWorker = new Worker(threadFactory);
		this.rendererData = createRendererData();
		this.width = width;
		this.height = height;
		this.size = rendererFractal.getStateSize();
		init();
		start();
	}
	
	/**
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return
	 */
	public boolean isInterrupted() {
		return aborted || Thread.currentThread().isInterrupted();
	}

	/**
	 * 
	 */
	public void dispose() {
		stop();
		free();
	}

	/**
	 * @return
	 */
	public float getProgress() {
		return progress;
	}

	/**
	 * 
	 */
	public void stopRender() {
		rendererWorker.abortTasks();
		rendererWorker.waitTasks();
	}

	/**
	 * 
	 */
	public void abortRender() {
		rendererWorker.abortTasks();
	}

	/**
	 * 
	 */
	public void joinRender() {
		rendererWorker.waitTasks();
	}

	/**
	 * @param dynamic
	 */
	public void startRender(final boolean dynamic, final int mode) {
		rendererWorker.addTask(new Runnable() {
			@Override
			public void run() {
				doRender(dynamic, mode);
			}
		});
	}
	
	/**
	 * @param julia
	 */
	public void setJulia(boolean julia) {
		if (julia) {
			rendererStrategy = new JuliaRendererStrategy(rendererFractal);
		} else {
			rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		}
	}

	/**
	 * @param constant
	 */
	public void setConstant(double x, double y) {
		rendererFractal.setConstant(new Number(x, y));
	}

	/**
	 * @return
	 */
	public RendererDelegate getRendererDelegate() {
		return rendererDelegate;
	}

	/**
	 * @param rendererDelegate
	 */
	public void setRendererDelegate(RendererDelegate rendererDelegate) {
		this.rendererDelegate = rendererDelegate;
	}

	/**
	 * @return
	 */
	protected RendererData createRendererData() {
		return new RendererData();
	}

	/**
	 * 
	 */
	protected void free() {
		rendererData.free();
	}

	/**
	 * 
	 */
	protected void init() {
		rendererData.init(width, height, size);
	}

	/**
	 * 
	 */
	protected void start() {
		rendererWorker.start();
	}

	/**
	 * 
	 */
	protected void stop() {
		rendererWorker.stop();
	}

	/**
	 * @param dynamic
	 * @param mode
	 */
	protected void doRender(final boolean dynamic, final int mode) {
		progress = 0;
		rendererStrategy.prepare();
		final MutableNumber px = new MutableNumber(0, 0);
		final MutableNumber pw = new MutableNumber(0, 0);
		final RendererPoint p = rendererData.newPoint();
		rendererData.setRegion(rendererFractal.getRegion());
		rendererData.initPositions();
		int offset = 0;
		int c = 0;
		rendererData.swap();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				px.set(rendererData.point());
				pw.set(rendererData.positionX(x), rendererData.positionY(y));
				c = rendererStrategy.renderPoint(p, px, pw);
				rendererData.setPixel(offset, c);
				rendererData.setPoint(offset, p);
				offset += 1;
			}
			if (y % 20 == 0) {
				progress = (float)y / (float)height;
				if (rendererDelegate != null) {
					rendererDelegate.didChanged(progress, rendererData.getPixels());
				}
			}
			Thread.yield();
			if (isInterrupted()) {
				aborted = true;
				break;
			}
		}
		if (aborted) {
			progress = 1;
		}
		if (rendererDelegate != null) {
			rendererDelegate.didChanged(progress, rendererData.getPixels());
		}
		Thread.yield();
	}
}
