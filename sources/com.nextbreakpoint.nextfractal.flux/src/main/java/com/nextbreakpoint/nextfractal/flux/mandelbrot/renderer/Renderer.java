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

import com.nextbreakpoint.nextfractal.flux.mandelbrot.MutableNumber;

/**
 * @author Andrea Medeghini
 */
public class Renderer {
	public static final int MODE_CALCULATE = 0x01;
	public static final int MODE_REFRESH = 0x02;
	protected final RendererDelegate rendererDelegate;
	protected RendererStrategy rendererStrategy;
	protected final RendererFractal rendererFractal;
	protected final RendererData rendererData;
	protected boolean aborted;
	protected float progress;
	protected int width;
	protected int height;

	/**
	 * @param rendererDelegate
	 * @param rendererFractal
	 * @param width
	 * @param height
	 */
	public Renderer(RendererDelegate rendererDelegate, RendererFractal rendererFractal, int width, int height) {
		this.rendererDelegate = rendererDelegate;
		this.rendererFractal = rendererFractal;
		this.rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		this.rendererData = createRendererData();
		this.width = width;
		this.height = height;
	}

	/**
	 * @return
	 */
	protected RendererData createRendererData() {
		return new RendererData();
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
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		free();
		init();
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
		rendererData.init(width, height);
	}

	/**
	 * @param dynamic
	 */
	public void doRender(final boolean dynamic) {
		progress = 0;
		update();
		rendererStrategy.prepare();
		final MutableNumber px = new MutableNumber(0, 0);
		final MutableNumber pw = new MutableNumber(0, 0);
		final RendererPoint p = rendererData.newPoint();
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
				rendererDelegate.didPixelsChange(rendererData.getPixels());
			}
			Thread.yield();
			if (isInterrupted()) {
				aborted = true;
				break;
			}
		}
		rendererDelegate.didPixelsChange(rendererData.getPixels());
		if (aborted) {
			progress = 1;
		}
	}

	/**
	 * 
	 */
	private void update() {
		// TODO Auto-generated method stub
		
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
	public void start() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void join() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void dispose() {
		free();
	}

	/**
	 * @return
	 */
	public float getProgress() {
		return progress;
	}

	/**
	 * @param modeRefresh
	 */
	public void setMode(int modeRefresh) {
		// TODO Auto-generated method stub
		
	}
}
