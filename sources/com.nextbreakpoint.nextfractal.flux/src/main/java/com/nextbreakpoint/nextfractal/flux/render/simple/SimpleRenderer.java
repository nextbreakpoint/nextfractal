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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer;
import com.nextbreakpoint.nextfractal.flux.render.RenderBuffer;
import com.nextbreakpoint.nextfractal.flux.render.RenderedPoint;

/**
 * @author Andrea Medeghini
 */
public final class SimpleRenderer extends MandelbrotRenderer {
	private final RenderingStrategy mandelbrotRenderingStrategy = new MandelbrotRenderingStrategy();
	private final RenderingStrategy juliaRenderingStrategy = new JuliaRenderingStrategy();
	private RendererData renderedData;
	private boolean isAborted = false;

	/**
	 * @param threadPriority
	 */
	public SimpleRenderer(final int threadPriority) {
		super(threadPriority);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#free()
	 */
	@Override
	protected void free() {
		super.free();
		if (renderedData != null) {
			renderedData.free();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#init()
	 */
	@Override
	protected void init() {
		super.init();
		renderedData = new RendererData();
		renderedData.reallocate(getBufferWidth(), getBufferHeight());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer#doRender(boolean)
	 */
	@Override
	protected void doRender(final boolean dynamic) {
		updateShift();
		updateRegion();
		updateTransform();
		renderingStrategy.updateParameters();
//		if (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) {
//			fractalRuntime.getRenderingFormula().getFormulaRuntime().prepareForRendering(fractalRuntime.getProcessingFormula().getFormulaRuntime(), fractalRuntime.getOrbitTrap().getOrbitTrapRuntime());
//			if (fractalRuntime.getOrbitTrap().getOrbitTrapRuntime() != null) {
//				fractalRuntime.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractalRuntime.getOrbitTrap().getCenter());
//			}
//		}
//		for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
//			if (fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime() != null) {
//				if (fractalRuntime.getOutcolouringFormula(i).isAutoIterations() && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
//					fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getRenderingFormula().getFormulaRuntime().getIterations());
//				}
//				else {
//					fractalRuntime.getOutcolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getOutcolouringFormula(i).getIterations());
//				}
//			}
//		}
//		for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
//			if (fractalRuntime.getIncolouringFormula(i).getFormulaRuntime() != null) {
//				if (fractalRuntime.getIncolouringFormula(i).isAutoIterations() && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
//					fractalRuntime.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getRenderingFormula().getFormulaRuntime().getIterations());
//				}
//				else {
//					fractalRuntime.getIncolouringFormula(i).getFormulaRuntime().prepareForRendering(fractalRuntime.getRenderingFormula().getFormulaRuntime(), fractalRuntime.getIncolouringFormula(i).getIterations());
//				}
//			}
//		}
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

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#getMandelbrotRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getMandelbrotRenderingStrategy() {
		return mandelbrotRenderingStrategy;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.core.fractal.renderer.AbstractFractalRenderer#createJuliaRenderingStrategy()
	 */
	@Override
	protected RenderingStrategy getJuliaRenderingStrategy() {
		return juliaRenderingStrategy;
	}

	/**
	 * @author Andrea Medeghini
	 */
	public static class RendererData {
		/**
		 * 
		 */
		public BufferedImage newBuffer;
		/**
		 * 
		 */
		public int[] newRGB;
		/**
		 * 
		 */
		public double[] positionX;
		/**
		 * 
		 */
		public double[] positionY;
		/**
		 * 
		 */
		public double x0 = 0;
		/**
		 * 
		 */
		public double y0 = 0;

		/**
		 * @see java.lang.Object#finalize()
		 */
		@Override
		public void finalize() throws Throwable {
			free();
			super.finalize();
		}

		/**
		 * 
		 */
		public void free() {
			positionX = null;
			positionY = null;
			if (newBuffer != null) {
				newBuffer.flush();
			}
			newBuffer = null;
			newRGB = null;
		}

		/**
		 * @param width
		 * @param height
		 */
		public void reallocate(final int width, final int height) {
			free();
			positionX = new double[width];
			positionY = new double[height];
			for (int i = 0; i < width; i++) {
				positionX[i] = 0;
			}
			for (int i = 0; i < height; i++) {
				positionY[i] = 0;
			}
			newBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
			newRGB = ((DataBufferInt) newBuffer.getRaster().getDataBuffer()).getData();
		}
	}

	private class MandelbrotRenderingStrategy implements RenderingStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
		public boolean isVerticalSymetrySupported() {
//			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
//				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
//				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed() && outcolouringFormula.isEnabled()) {
//					return false;
//				}
//			}
//			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
//				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
//				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isVerticalSymetryAllowed() && incolouringFormula.isEnabled()) {
//					return false;
//				}
//			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		@Override
		public boolean isHorizontalSymetrySupported() {
//			for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
//				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
//				if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
//					return false;
//				}
//			}
//			for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
//				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
//				if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isHorizontalSymetryAllowed()) {
//					return false;
//				}
//			}
			return true;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.flux.render.mandelbrot.renderer.RenderedPoint)
		 */
		@Override
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
//			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
//				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(pw);
//				p.xr = px.r;
//				p.xi = px.i;
//				p.wr = pw.r;
//				p.wi = pw.i;
//				p.dr = 0;
//				p.di = 0;
//				p.tr = 0;
//				p.ti = 0;
//				return SimpleRenderer.this.renderPoint(p);
//			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
		public void updateParameters() {
//			if (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) {
//				renderedData.x0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().r;
//				renderedData.y0 = fractalRuntime.getRenderingFormula().getFormulaRuntime().getInitialPoint().i;
//			}
//			else {
//				renderedData.x0 = 0;
//				renderedData.y0 = 0;
//			}
		}
	}

	private class JuliaRenderingStrategy implements RenderingStrategy {
		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isHorizontalSymetrySupported()
		 */
		@Override
		public boolean isHorizontalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#isVerticalSymetrySupported()
		 */
		@Override
		public boolean isVerticalSymetrySupported() {
			return false;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#renderPoint(com.nextbreakpoint.nextfractal.flux.render.mandelbrot.renderer.RenderedPoint)
		 */
		@Override
		public int renderPoint(final RenderedPoint p, final Complex px, final Complex pw) {
//			if ((fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && (fractalRuntime.getTransformingFormula().getFormulaRuntime() != null)) {
//				fractalRuntime.getTransformingFormula().getFormulaRuntime().renderPoint(px);
//				p.xr = pw.r;
//				p.xi = pw.i;
//				p.wr = px.r;
//				p.wi = px.i;
//				p.dr = 0;
//				p.di = 0;
//				p.tr = 0;
//				p.ti = 0;
//				return SimpleRenderer.this.renderPoint(p);
//			}
			return 0;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.flux.render.MandelbrotRenderer.renderer.AbstractMandelbrotRenderer.RenderingStrategy#updateParameters()
		 */
		@Override
		public void updateParameters() {
			renderedData.x0 = oldConstant.getX();
			renderedData.y0 = oldConstant.getY();
		}
	}
}
