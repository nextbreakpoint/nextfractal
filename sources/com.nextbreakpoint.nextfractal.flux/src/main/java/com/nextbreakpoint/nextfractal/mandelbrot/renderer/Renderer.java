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
package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.strategy.JuliaRendererStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.strategy.MandelbrotRendererStrategy;

/**
 * @author Andrea Medeghini
 */
public class Renderer {
	public static final int MODE_CALCULATE = 0x01;
	public static final int MODE_REFRESH = 0x02;
	protected final RendererFractal rendererFractal;
	protected final ThreadFactory threadFactory;
	protected final RendererData rendererData;
	protected final Worker rendererWorker;
	protected volatile RendererDelegate rendererDelegate;
	protected volatile RendererStrategy rendererStrategy;
	protected volatile boolean aborted;
	protected volatile boolean orbitChanged;
	protected volatile boolean colorChanged;
	protected volatile float progress;
	protected int width;
	protected int height;
	protected boolean julia;
	protected Number constant;

	/**
	 * @param rendererDelegate
	 * @param rendererFractal
	 * @param width
	 * @param height
	 */
	public Renderer(ThreadFactory threadFactory, int width, int height) {
		this.threadFactory = threadFactory;
		this.rendererWorker = new Worker(threadFactory);
		this.rendererData = createRendererData();
		this.rendererFractal = new RendererFractal();
		this.width = width;
		this.height = height;
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
	public void startRender(final boolean dynamic) {
		rendererWorker.addTask(new Runnable() {
			@Override
			public void run() {
				doRender(dynamic);
			}
		});
	}
	
	/**
	 * @param julia
	 */
	public void setJulia(boolean julia) {
		this.julia = julia;
	}

	/**
	 * @param constant
	 */
	public void setConstant(double x, double y) {
		this.constant = new Number(x, y);
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
	 * @param orbit
	 */
	public void setOrbit(Orbit orbit) {
		rendererFractal.setOrbit(orbit);
		orbitChanged = true;
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		rendererFractal.setColor(color);
		colorChanged = true;
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
	 */
	protected void doRender(final boolean dynamic) {
		if (rendererFractal == null) {
			progress = 1;
			return;
		}
		final boolean redraw = orbitChanged;
		orbitChanged = false;
		colorChanged = false;
		progress = 0;
		rendererFractal.clearScope();
		rendererFractal.setConstant(constant);
		if (julia) {
			rendererStrategy = new JuliaRendererStrategy(rendererFractal);
		} else {
			rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		}
		PixelStrategy pixelStrategy = null;
		if (redraw) {
			pixelStrategy = new RedrawPixelStrategy();
		} else {
			pixelStrategy = new RefreshPixelStrategy();
		}
		rendererStrategy.prepare();
		rendererData.setSize(width, height, rendererFractal.getStateSize());
		rendererData.setRegion(rendererFractal.getRegion());
		rendererData.initPositions();
		rendererData.swap();
		final MutableNumber px = new MutableNumber(0, 0);
		final MutableNumber pw = new MutableNumber(0, 0);
		final RendererPoint p = rendererData.newPoint();
		int offset = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				px.set(rendererData.point());
				pw.set(rendererData.positionX(x), rendererData.positionY(y));
				pixelStrategy.renderPixel(p, px, pw, offset);
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
	
	private class RedrawPixelStrategy implements PixelStrategy {
		@Override
		public int renderPixel(RendererPoint p, Number x, Number w, int offset) {
			int c = rendererStrategy.renderPoint(p, x, w);
			rendererData.setPoint(offset, p);
			rendererData.setPixel(offset, c);
			return c;
		}
	}

	private class RefreshPixelStrategy implements PixelStrategy {
		@Override
		public int renderPixel(RendererPoint p, Number x, Number w, int offset) {
			rendererData.getPoint(offset, p);
			int c = rendererStrategy.renderColor(p);
			rendererData.setPixel(offset, c);
			return c;
		}
	}
}
