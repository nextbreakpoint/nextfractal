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
package com.nextbreakpoint.nextfractal.flux.render.simple;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.flux.render.RenderBuffer;
import com.nextbreakpoint.nextfractal.flux.render.RenderStrategy;
import com.nextbreakpoint.nextfractal.flux.render.RenderedPoint;
import com.nextbreakpoint.nextfractal.flux.render.Renderer;

/**
 * @author Andrea Medeghini
 */
public final class SimpleRenderer implements Renderer {
	private RendererData renderedData;
	private boolean isAborted = false;
	
	private RenderStrategy renderingStrategy;
	private int percent;
	private Complex[] points;

	/**
	 * @param threadPriority
	 */
	public SimpleRenderer(final int threadPriority) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotManager.core.fractal.renderer.AbstractFractalRenderer#free()
	 */
	protected void free() {
		if (renderedData != null) {
			renderedData.free();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotManager.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	protected void init() {
		renderedData = new RendererData();
		renderedData.reallocate(getBufferWidth(), getBufferHeight());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotManager.renderer.AbstractMandelbrotRenderer#doRender(boolean)
	 */
	protected void doRender(final boolean dynamic) {
		update();
		renderingStrategy.updateParameters();
		final Complex px = new Complex(0, 0);
		final Complex pw = new Complex(0, 0);
		final RenderedPoint p = new RenderedPoint();
		final double beginx = points[0].r;
		final double endx = points[1].r;
		final double beginy = points[0].i;
		final double endy = points[1].i;
		final int sizex = getBufferWidth();
		final int sizey = getBufferHeight();
		final double stepx = (endx - beginx) / (sizex - 1);
		final double stepy = (endy - beginy) / (sizey - 1);
		double posx = beginx;
		double posy = beginy;
		for (int x = 0; x < sizex; x++) {
			renderedData.positionX[x] = posx;
			posx += stepx;
		}
		for (int y = 0; y < sizey; y++) {
			renderedData.positionY[y] = posy;
			posy += stepy;
		}
		int offset = 0;
		for (int y = 0; y < sizey; y++) {
			for (int x = 0; x < sizex; x++) {
				px.r = renderedData.x0;
				px.i = renderedData.y0;
				pw.r = renderedData.positionX[x];
				pw.i = renderedData.positionY[y];
				p.pr = renderedData.positionX[x];
				p.pi = renderedData.positionY[y];
				renderedData.newRGB[offset] = renderingStrategy.renderPoint(p, px, pw);
				offset += 1;
			}
			if (y % 20 == 0) {
				percent = (int) ((y * 100f) / sizey);
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
			percent = 100;
		}
	}

	private void copy() {
		final RenderBuffer buffer = getRenderBuffer();
		buffer.update(renderedData.newRGB);
	}

	private RenderBuffer getRenderBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int getBufferWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getBufferHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInterrupted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
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
}
