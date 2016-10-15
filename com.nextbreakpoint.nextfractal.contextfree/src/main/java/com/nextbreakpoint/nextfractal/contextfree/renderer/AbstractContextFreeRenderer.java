/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.renderer;


/**
 * @author Andrea Medeghini
 */
public abstract class AbstractContextFreeRenderer implements ContextFreeRenderer {
//	private RenderBuffer newBuffer;
//	private RenderBuffer oldBuffer;
//	private IntegerVector2D bufferSize;
//	private Tile newTile;
//	private Tile oldTile;
//	protected int imageDim;
//	private int tileDim;
//	protected double rotationValue;
//	protected int renderMode = ContextFreeRenderer.MODE_CALCULATE;
//	protected int newImageMode = 0;
//	protected int oldImageMode = 0;
//	private boolean dynamic = false;
//	private boolean dynamicZoom = false;
//	private RenderAffine affine;
//	protected ContextFreeRuntime runtime;
//	protected View newView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
//	protected View oldView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
//	protected int percent = 100;
//	protected CFDGRuntimeElement cfdgRuntime;
//	protected int status = TwisterRenderer.STATUS_TERMINATED;
//	private final ContextFreeWorker renderWorker;
//	private RenderFactory renderFactory;
//	protected final ThreadFactory factory;
//	private boolean viewChanged;
//	private boolean invalidated;
//	private final Object lock = new Object();
//
//	/**
//	 * 
//	 */
//	public AbstractContextFreeRenderer(final int threadPriority) {
//		factory = new DefaultThreadFactory("ContextFreeRendererWorker", true, threadPriority);
//		renderWorker = new ContextFreeWorker(factory);
//	}
//
//	/**
//	 * @see java.lang.Object#finalize()
//	 */
//	@Override
//	public void finalize() throws Throwable {
//		dispose();
//		super.finalize();
//	}
//
//	@Override
//	public void dispose() {
//		stop();
//		free();
//	}
//
//	/**
//	 * 
//	 */
//	protected void free() {
//		if (newBuffer != null) {
//			newBuffer.dispose();
//		}
//		newBuffer = null;
//		if (oldBuffer != null) {
//			oldBuffer.dispose();
//		}
//		oldBuffer = null;
//	}
//
//	/**
//	 * 
//	 */
//	protected void init() {
//		imageDim = (int) Math.sqrt(((oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2)));
//		tileDim = (int) Math.sqrt(((oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2)));
//		bufferSize = new IntegerVector2D(tileDim, tileDim);
//		newBuffer = renderFactory.createBuffer(bufferSize.getX(), bufferSize.getY());
//		oldBuffer = newBuffer;
//	}
//
//	/**
//	 * 
//	 */
//	protected final void swapImages() {
//		synchronized (lock) {
//			final RenderBuffer tmpBuffer = oldBuffer;
//			oldBuffer = newBuffer;
//			newBuffer = tmpBuffer;
//		}
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#setRenderingHints(java.util.Map)
//	 */
//	@Override
//	public void setRenderingHints(final Map<Object, Object> hints) {
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#getRenderingStatus()
//	 */
//	@Override
//	public int getRenderingStatus() {
//		return status;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#setMode(int)
//	 */
//	@Override
//	public void setMode(final int renderMode) {
//		this.renderMode |= renderMode;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#getMode()
//	 */
//	@Override
//	public int getMode() {
//		return renderMode;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
//	 */
//	@Override
//	public void setTile(final Tile tile) {
//		synchronized (this) {
//			invalidated = false;
//			newTile = tile;
//		}
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#getTile()
//	 */
//	@Override
//	public Tile getTile() {
//		return oldTile;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#getRenderFactory()
//	 */
//	@Override
//	public RenderFactory getRenderFactory() {
//		return renderFactory;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setRenderFactory(com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory)
//	 */
//	@Override
//	public void setRenderFactory(RenderFactory renderFactory) {
//		this.renderFactory = renderFactory;
//	}
//
//	/**
//	 * @return
//	 */
//	public int getBufferWidth() {
//		return bufferSize.getX();
//	}
//
//	/**
//	 * @return
//	 */
//	public int getBufferHeight() {
//		return bufferSize.getY();
//	}
//
//	/**
//	 * @return
//	 */
//	protected boolean isTileSupported() {
//		return true;
//	}
//
//	@Override
//	public boolean isDynamic() {
//		final boolean value = dynamic;
//		dynamic = false;
//		return value;
//	}
//
//	@Override
//	public boolean isViewChanged() {
//		final boolean value = viewChanged;
//		viewChanged = false;
//		return value;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View)
//	 */
//	@Override
//	public void setView(final View view) {
//		synchronized (this) {
//			newView = view;
//		}
//	}
//
//	private void updateView(final View view) {
//		rotationValue = view.getRotation().getZ();
//		dynamic = view.getStatus().getZ() == 1;
//		dynamicZoom = dynamic;
//		if (view.getStatus().getZ() == 2) {
//			setMode(ContextFreeRenderer.MODE_CALCULATE);
//		}
//	}
//
//	/**
//	 * 
//	 */
//	protected void updateTransform() {
//		final int offsetX = (getBufferWidth() - oldTile.getTileSize().getX() - oldTile.getTileBorder().getX() * 2) / 2;
//		final int offsetY = (getBufferHeight() - oldTile.getTileSize().getY() - oldTile.getTileBorder().getY() * 2) / 2;
//		final int centerX = getBufferWidth() / 2;
//		final int centerY = getBufferHeight() / 2;
//		affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
//		affine.append(renderFactory.createRotateAffine(rotationValue, centerX, centerY));
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#startRenderer()
//	 */
//	@Override
//	public void startRenderer() {
//		renderWorker.executeTask();
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#abortRenderer()
//	 */
//	@Override
//	public void abortRenderer() {
//		renderWorker.abortTasks();
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#joinRenderer()
//	 */
//	@Override
//	public void joinRenderer() throws InterruptedException {
//		renderWorker.waitTasks();
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public void start() {
//		renderWorker.start();
//	}
//
//	/**
//	 * 
//	 */
//	@Override
//	public void stop() {
//		renderWorker.stop();
//	}
//
//	public void startTasks() {
//		renderWorker.executeTask();
//	}
//
//	public void clearTasks() {
//		renderWorker.clearTasks();
//	}
//
//	public void abortTasks() {
//		renderWorker.abortTasks();
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#asyncStop()
//	 */
//	@Override
//	public final void asyncStop() {
//		clearTasks();
//		abortTasks();
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#asyncStart()
//	 */
//	@Override
//	public final void asyncStart() {
//		startTasks();
//	}
//
//	@Override
//	public ContextFreeRuntime getRuntime() {
//		return runtime;
//	}
//
//	@Override
//	public void setRuntime(ContextFreeRuntime runtime) {
//		this.runtime = runtime;
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext)
//	 */
//	@Override
//	public void drawImage(final RenderGraphicsContext gc) {
//		synchronized (lock) {
//			if (oldTile != null) {
//				gc.saveTransform();
//				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
//				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
//				gc.setAffine(affine);
//				gc.drawImage(newBuffer.getImage(), 0, 0);
//				//gc.setClip(null);
//				// g.dispose();
//				gc.restoreTransform();
//			}
//		}
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext, int, int)
//	 */
//	@Override
//	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
//		synchronized (lock) {
//			if (oldTile != null) {
//				gc.saveTransform();
//				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
//				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
//				gc.setAffine(affine);
//				gc.drawImage(newBuffer.getImage(), x, y);
//				//gc.setClip(null);
//				// g.dispose();
//				gc.restoreTransform();
//			}
//		}
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext, int, int, int, int)
//	 */
//	@Override
//	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
//		synchronized (lock) {
//			if (oldTile != null) {
//				gc.saveTransform();
//				//TODO gc.setClip(x, y, w, h);
//				gc.setAffine(affine);
//				final double sx = w / (double) getTile().getTileSize().getX();
//				final double sy = h / (double) getTile().getTileSize().getY();
//				final int dw = (int) Math.rint(bufferSize.getX() * sx);
//				final int dh = (int) Math.rint(bufferSize.getY() * sy);
//				gc.drawImage(newBuffer.getImage(), x, y, dw, dh);
//				//TODO gc.setClip(null);
//				// g.dispose();
//				gc.restoreTransform();
//			}
//		}
//	}
//	
//	/**
//	 * @return
//	 */
//	protected RenderBuffer getRenderBuffer() {
//		return newBuffer;
//	}
//	
//	/**
//	 * @param scale 
//	 * @return
//	 */
//	protected void copyOldBuffer() {
////		oldBuffer.setComposite(AlphaComposite.Src);
////		oldBuffer.drawImage(newImage, 0, 0, null);
//	}
//
//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.ContextFreeFractalRenderer#isInterrupted()
//	 */
//	@Override
//	public boolean isInterrupted() {
//		return Thread.currentThread().isInterrupted();
//	}
//
//	private void render() {
//		synchronized (this) {
//			cfdgRuntime = runtime.getCFDG();
//			if (oldView != newView) {
//				viewChanged = true;
//				updateView(newView);
//			}
//			oldView = newView;
//			if (newTile != oldTile) {
//				setMode(ContextFreeRenderer.MODE_CALCULATE);
//				oldTile = newTile;
//				free();
//				init();
//			}
//		}
//		percent = 0;
//		status = TwisterRenderer.STATUS_RENDERING;
//		doRender(dynamicZoom);
//		if (percent == 100) {
//			status = TwisterRenderer.STATUS_TERMINATED;
//		}
//		else {
//			status = TwisterRenderer.STATUS_ABORTED;
//		}
//		dynamicZoom = false;
//	}
//
//	protected abstract void doRender(boolean dynamicZoom);
//
//	private class ContextFreeWorker extends RenderWorker {
//		/**
//		 * @param factory
//		 */
//		public ContextFreeWorker(ThreadFactory factory) {
//			super(factory);
//		}
//
//		/**
//		 * @see com.nextbreakpoint.nextfractal.core.util.RenderWorker#execute()
//		 */
//		@Override
//		protected void execute() {
//			try {
//				if (!invalidated) {
//					render();
//				}
//			}
//			catch (final Throwable e) {
//				invalidated = true;
//				e.printStackTrace();
//			}
//		}
//	}
}
