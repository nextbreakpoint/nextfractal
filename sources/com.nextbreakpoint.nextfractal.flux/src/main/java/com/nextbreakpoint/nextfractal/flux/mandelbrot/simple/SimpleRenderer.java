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
package com.nextbreakpoint.nextfractal.flux.mandelbrot.simple;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.MutableNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererStrategy;
import com.nextbreakpoint.nextfractal.flux.render.RenderBuffer;

/**
 * @author Andrea Medeghini
 */
public final class SimpleRenderer implements Renderer {
	private final RendererFractal rendererFractal;
	private final RendererStrategy rendererStrategy;
	private final RendererData rendererData;
	private final RenderBuffer renderBuffer;
	private boolean isAborted;
	private float progress;

	/**
	 * @param threadPriority
	 */
	public SimpleRenderer(RenderBuffer renderBuffer, RendererFractal rendererFractal, RendererStrategy renderingStrategy, RendererData renderedData) {
		this.rendererFractal = rendererFractal;
		this.rendererStrategy = renderingStrategy;
		this.rendererData = renderedData;
		this.renderBuffer = renderBuffer;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotManager.core.fractal.renderer.AbstractFractalRenderer#free()
	 */
	protected void free() {
		rendererData.free();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotManager.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	protected void init() {
		rendererData.init(getRenderBuffer().getWidth(), getRenderBuffer().getHeight(), 1/*TODO*/);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotManager.renderer.AbstractMandelbrotRenderer#doRender(boolean)
	 */
	protected void doRender(final boolean dynamic) {
		progress = 0;
		update();
		rendererStrategy.updateParameters();
		MutableNumber px = new MutableNumber(0, 0);
		MutableNumber pw = new MutableNumber(0, 0);
		final RendererPoint p = new RendererPoint();
		final double beginx = rendererData.region[0].r();
		final double endx = rendererData.region[1].r();
		final double beginy = rendererData.region[0].i();
		final double endy = rendererData.region[1].i();
		final int sizex = getRenderBuffer().getWidth();
		final int sizey = getRenderBuffer().getHeight();
		final double stepx = (endx - beginx) / (sizex - 1);
		final double stepy = (endy - beginy) / (sizey - 1);
		double posx = beginx;
		double posy = beginy;
		for (int x = 0; x < sizex; x++) {
			rendererData.positionX[x] = posx;
			posx += stepx;
		}
		for (int y = 0; y < sizey; y++) {
			rendererData.positionY[y] = posy;
			posy += stepy;
		}
		int offset = 0;
		for (int y = 0; y < sizey; y++) {
			for (int x = 0; x < sizex; x++) {
				px.set(rendererData.point);
				pw.set(rendererData.positionX[x], rendererData.positionY[y]);
				p.pr = rendererData.positionX[x];
				p.pi = rendererData.positionY[y];
				rendererData.newPixels[offset] = rendererStrategy.renderPoint(rendererFractal, p, px, pw);
				offset += 1;
			}
			if (y % 20 == 0) {
				progress = (float)y / (float)sizey;
				copy();
			}
			Thread.yield();
			if (isInterrupted()) {
				isAborted = true;
				break;
			}
		}
		copy();
		if (isAborted) {
			progress = 1;
		}
	}

	private void copy() {
		RenderBuffer buffer = getRenderBuffer();
		buffer.update(rendererData.newPixels);
	}

	private RenderBuffer getRenderBuffer() {
		return renderBuffer;
	}
	
	private void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInterrupted() {
		// TODO Auto-generated method stub
		return isAborted || Thread.currentThread().isInterrupted();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void join() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	public float getProgress() {
		return progress;
	}
}
