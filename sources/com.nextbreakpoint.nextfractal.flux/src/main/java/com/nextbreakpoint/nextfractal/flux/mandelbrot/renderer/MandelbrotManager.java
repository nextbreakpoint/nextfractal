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
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderWorker;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.flux.render.RenderAffine;
import com.nextbreakpoint.nextfractal.flux.render.RenderBuffer;
import com.nextbreakpoint.nextfractal.flux.render.RenderFactory;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;

/**
 * @author Andrea Medeghini
 */
public abstract class MandelbrotManager implements Manager, RendererDelegate {
	protected static final Logger logger = Logger.getLogger(MandelbrotManager.class.getName());
	private RenderBuffer newBuffer;
	private RenderBuffer oldBuffer;
	private Tile newTile;
	private Tile oldTile;
	private int newImageMode;
	private int oldImageMode;
	private int newShiftValue;
	private int oldShiftValue;
	private DoubleVector2D newConstant;
	private DoubleVector2D oldConstant;
//	private View newView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
//	private View oldView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	private double rotationValue;
	private IntegerVector2D bufferSize;
	private int imageDim;
	private int tileDim;
//	private boolean dynamic = false;
	private boolean dynamicZoom = false;
	private final Complex center = new Complex();
	private final Complex scale = new Complex();
	private RenderAffine affine;
	private boolean invalidated;
	private Renderer renderer;
	private RendererFractal rendererFractal;
	private RendererStrategy rendererStrategy;
	private final RendererData rendererData;
	private final RenderFactory renderFactory;
	private final MandelbrotWorker renderWorker;
	private final ThreadFactory factory;
	private final Tile tile;
	private final Object lock = new Object();

	/**
	 * @param threadPriority
	 */
	public MandelbrotManager(final int threadPriority, RenderFactory renderFactory, RendererFractal rendererFractal, Tile tile) {
		factory = new DefaultThreadFactory("MandelbrotRendererWorker", true, threadPriority);
		renderWorker = new MandelbrotWorker(factory);
		rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		this.rendererFractal = rendererFractal;
		this.renderFactory = renderFactory;
		this.rendererData = createRendererData();
		this.renderer = createRenderer(rendererData);
		this.tile = tile;
	}

	protected Renderer createRenderer(RendererData rendererData) {
		return new Renderer(this, rendererStrategy, rendererData, tile.getImageSize().getX(), tile.getImageSize().getY());
	}

	protected RendererData createRendererData() {
		return new RendererData();
	}

	/**
	 * 
	 */
	@Override
	public void start() {
		renderWorker.start();
	}

	/**
	 * 
	 */
	@Override
	public void abort() {
		renderWorker.stop();
	}

	/**
	 * 
	 */
	@Override
	public void join() {
		renderWorker.join();
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		affine = null;
		rendererStrategy = null;
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.Renderer.renderer.MandelbrotManager#dispose()
	 */
	@Override
	public final void dispose() {
		abort();
		join();
		free();
	}

	public void setJulia(boolean julia) {
		if (julia) {
			rendererStrategy = new JuliaRendererStrategy(rendererFractal);
		} else {
			rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		}
	}
	
//	/**
//	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View, com.nextbreakpoint.nextfractal.core.util.DoubleVector2D, int)
//	 */
//	@Override
//	public void setView(final View view, final DoubleVector2D constant, final int imageMode) {
//		synchronized (this) {
//			newView = view;
//			newConstant = constant;
//			newImageMode = imageMode;
//		}
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View)
//	 */
//	@Override
//	public void setView(final View view) {
//		synchronized (this) {
//			newView = view;
//		}
//	}

//	private void updateView(final View view) {
//		rotationValue = view.getRotation().getZ();
//		newShiftValue = (int) Math.rint(view.getRotation().getW());
//		if (newShiftValue < 0) {
//			newShiftValue = 0;
//		}
//		dynamic = (view.getStatus().getZ() == 1) || (view.getStatus().getW() == 1);
//		dynamicZoom = dynamic;
//		if (view.getStatus().getZ() == 2) {
//			setMode(Renderer.MODE_CALCULATE);
//		}
//	}

	/**
	 * 
	 */
	protected void free() {
		if (newBuffer != null) {
			newBuffer.dispose();
		}
		newBuffer = null;
		if (oldBuffer != null) {
			oldBuffer.dispose();
		}
		oldBuffer = null;
	}

	/**
	 * 
	 */
	protected void init() {
		imageDim = (int) Math.sqrt(((oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2)));
		tileDim = (int) Math.sqrt(((oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2)));
		bufferSize = new IntegerVector2D(tileDim, tileDim);
		newBuffer = renderFactory.createBuffer(bufferSize.getX(), bufferSize.getY());
		oldBuffer = renderFactory.createBuffer(bufferSize.getX(), bufferSize.getY());
		affine = renderFactory.createAffine();
	}

	/**
	 * 
	 */
	protected final void swapImages() {
		synchronized (lock) {
			final RenderBuffer tmpBuffer = oldBuffer;
			oldBuffer = newBuffer;
			newBuffer = tmpBuffer;
		}
	}

	private void render() {
		synchronized (this) {
//			if (oldView != newView) {
//				viewChanged = true;
//				updateView(newView);
//			}
			if (newShiftValue != oldShiftValue) {
				renderer.setMode(Renderer.MODE_REFRESH);
			}
			if (newImageMode != oldImageMode) {
				renderer.setMode(Renderer.MODE_CALCULATE);
			}
			if ((newConstant != oldConstant) && (newImageMode != 0)) {
				renderer.setMode(Renderer.MODE_CALCULATE);
			}
//			if (fractalRuntime.isRenderingFormulaChanged()) {
//				renderer.setMode(Renderer.MODE_CALCULATE);
//			}
//			if (fractalRuntime.isTransformingFormulaChanged()) {
//				renderer.setMode(Renderer.MODE_CALCULATE);
//			}
//			if (fractalRuntime.isProcessingFormulaChanged()) {
//				renderer.setMode(Renderer.MODE_CALCULATE);
//			}
//			if (fractalRuntime.isOrbitTrapChanged()) {
//				renderer.setMode(Renderer.MODE_CALCULATE);
//			}
//			if (fractalRuntime.isIncolouringFormulaChanged()) {
//				renderer.setMode(Renderer.MODE_REFRESH);
//			}
//			if (fractalRuntime.isOutcolouringFormulaChanged()) {
//				renderer.setMode(Renderer.MODE_REFRESH);
//			}
			// if (!isDynamic) {
			// setMode(FractalRenderer.MODE_REFRESH);
			// }
//			oldView = newView;
			oldConstant = newConstant;
			oldImageMode = newImageMode;
			oldShiftValue = newShiftValue;
			if (newTile != oldTile) {
				renderer.setMode(Renderer.MODE_CALCULATE);
				oldTile = newTile;
				free();
				init();
			}
		}
//		doRender(dynamicZoom);
		dynamicZoom = false;
	}

	/**
	 * @return
	 */
	public int getBufferWidth() {
		return bufferSize.getX();
	}

	/**
	 * @return
	 */
	public int getBufferHeight() {
		return bufferSize.getY();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.Renderer.renderer.MandelbrotManager#setConstant(com.nextbreakpoint.nextfractal.core.util.DoubleVector2D)
	 */
	@Override
	public void setConstant(final DoubleVector2D constant) {
		synchronized (this) {
			newConstant = constant;
		}
	}

	/**
	 * @return
	 */
	protected boolean isTileSupported() {
		return true;
	}

	/**
	 * 
	 */
	protected void updateRegion() {
//		if ((fractalRuntime.getRenderingFormula() != null) && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
//			final DoubleVector2D s = fractalRuntime.getRenderingFormula().getFormulaRuntime().getScale();
//			final double x = oldView.getPosition().getX();
//			final double y = oldView.getPosition().getY();
//			final double z = oldView.getPosition().getZ();
//			scale.r = s.getX() * z;
//			scale.i = s.getY() * z;
//			center.r = fractalRuntime.getRenderingFormula().getFormulaRuntime().getCenter().getX() + x;
//			center.i = fractalRuntime.getRenderingFormula().getFormulaRuntime().getCenter().getY() + y;
//			final double imageOffsetX = (imageDim - oldTile.getImageSize().getX()) / 2;
//			final double imageOffsetY = (imageDim - oldTile.getImageSize().getY()) / 2;
//			double sx = (scale.r * 0.5d * imageDim) / oldTile.getImageSize().getX();
//			double sy = (scale.i * 0.5d * imageDim) / oldTile.getImageSize().getX();
//			Complex p0 = new Complex(center.r - sx, center.i - sy);
//			Complex p1 = new Complex(center.r + sx, center.i + sy);
//			final Complex t0 = new Complex();
//			final Complex t1 = new Complex();
//			final double dr = p1.r - p0.r;
//			final double di = p1.i - p0.i;
//			t0.r = p0.r + dr * (imageOffsetX + oldTile.getTileOffset().getX() + oldTile.getTileSize().getX() / 2d) / imageDim;
//			t0.i = p0.i + di * (imageOffsetY + oldTile.getTileOffset().getY() + oldTile.getTileSize().getY() / 2d) / imageDim;
//			t1.r = p0.r + dr * 0.5;
//			t1.i = p0.i + di * 0.5;
//			final AffineTransform t = new AffineTransform();
//			t.rotate(-rotationValue, t1.r / dr, t1.i / di);
//			Point2D.Double p = new Point2D.Double(t0.r / dr, t0.i / di);
//			p = (Point2D.Double) t.transform(p, p);
//			p.setLocation(p.getX() * dr, p.getY() * di);
//			sx = dr * 0.5 * bufferSize.getX() / imageDim;
//			sy = di * 0.5 * bufferSize.getY() / imageDim;
//			p0 = new Complex(p.getX() - sx, p.getY() - sy);
//			p1 = new Complex(p.getX() + sx, p.getY() + sy);
//			area.points[0] = p0;
//			area.points[1] = p1;
//		}
//		else {
//			final Complex p0 = new Complex(-0.5, +0.5);
//			final Complex p1 = new Complex(-0.5, +0.5);
//			area.points[0] = p0;
//			area.points[1] = p1;
//		}
	}

	/**
	 * 
	 */
	protected void updateTransform() {
		final int offsetX = (getBufferWidth() - oldTile.getTileSize().getX() - oldTile.getTileBorder().getX() * 2) / 2;
		final int offsetY = (getBufferHeight() - oldTile.getTileSize().getY() - oldTile.getTileBorder().getY() * 2) / 2;
		final int centerX = getBufferWidth() / 2;
		final int centerY = getBufferHeight() / 2;
//TODO cleanup		transform.setToTranslation(-offsetX, -offsetY);
//		affine.setToTransform(Affine.translate(-offsetX, -offsetY));
//		transform.rotate(rotationValue, centerX, centerY);
//		affine.append(Affine.rotate(rotationValue, centerX, centerY));
		affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
		affine.append(renderFactory.createRotateAffine(rotationValue, centerX, centerY));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.Renderer.renderer.MandelbrotManager#drawImage(com.nextbreakpoint.nextfractal.flux.render.twister.renderer.RenderGraphicsContext)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc) {
		synchronized (lock) {
			if (oldTile != null) {
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(affine);
				gc.drawImage(newBuffer.getImage(), 0, 0);
				//gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.Renderer.renderer.MandelbrotManager#drawImage(com.nextbreakpoint.nextfractal.flux.render.twister.renderer.RenderGraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
		synchronized (lock) {
			if (oldTile != null) {
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(affine);
				gc.drawImage(newBuffer.getImage(), x, y);
				//gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.Renderer.renderer.MandelbrotManager#drawImage(com.nextbreakpoint.nextfractal.flux.render.twister.renderer.RenderGraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
		synchronized (lock) {
			if (oldTile != null) {
				gc.saveTransform();
				//TODO gc.setClip(x, y, w, h);
				gc.setAffine(affine);
				final double sx = w / (double) tile.getTileSize().getX();
				final double sy = h / (double) tile.getTileSize().getY();
				final int dw = (int) Math.rint(bufferSize.getX() * sx);
				final int dh = (int) Math.rint(bufferSize.getY() * sy);
				gc.drawImage(newBuffer.getImage(), x, y, dw, dh);
				//TODO gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
			}
		}
	}

	private class MandelbrotWorker extends RenderWorker {
		/**
		 * @param factory
		 */
		public MandelbrotWorker(ThreadFactory factory) {
			super(factory);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderWorker#execute()
		 */
		@Override
		protected void execute() {
			try {
				if (!invalidated) {
					render();
				}
			}
			catch (final Throwable e) {
				invalidated = true;
				e.printStackTrace();
			}
		}
	}
}
