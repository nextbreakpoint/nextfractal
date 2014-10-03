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
package com.nextbreakpoint.nextfractal.contextfree.renderer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRuntime;
import com.nextbreakpoint.nextfractal.contextfree.cfdg.CFDGRuntimeElement;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;
import com.nextbreakpoint.nextfractal.core.util.RenderWorker;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractContextFreeRenderer implements ContextFreeRenderer {
	private Graphics2D newBuffer;
	private Graphics2D oldBuffer;
	private BufferedImage newImage;
	private BufferedImage oldImage;
	private IntegerVector2D bufferSize;
	private Tile newTile;
	private Tile oldTile;
	protected int imageDim;
	private int tileDim;
	protected double rotationValue;
	protected int renderMode = ContextFreeRenderer.MODE_CALCULATE;
	protected int newImageMode = 0;
	protected int oldImageMode = 0;
	private boolean dynamic = false;
	private boolean dynamicZoom = false;
	private final AffineTransform transform = new AffineTransform();
	protected ContextFreeRuntime runtime;
	protected View newView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	protected View oldView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	protected int percent = 100;
	protected CFDGRuntimeElement cfdgRuntime;
	protected int status = TwisterRenderer.STATUS_TERMINATED;
	private final ContextFreeWorker renderWorker;
	protected final ThreadFactory factory;
	private boolean viewChanged;
	private boolean invalidated;
	private final Object lock = new Object();

	/**
	 * 
	 */
	public AbstractContextFreeRenderer(final int threadPriority) {
		factory = new DefaultThreadFactory("ContextFreeRendererWorker", true, threadPriority);
		renderWorker = new ContextFreeWorker(factory);
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	@Override
	public void dispose() {
		stop();
		free();
	}

	/**
	 * 
	 */
	protected void free() {
		if (newBuffer != null) {
			newBuffer.dispose();
		}
		if (newImage != null) {
			newImage.flush();
		}
		newImage = null;
		newBuffer = null;
		if (oldBuffer != null) {
			oldBuffer.dispose();
		}
		if (oldImage != null) {
			oldImage.flush();
		}
		oldImage = null;
		oldBuffer = null;
	}

	/**
	 * 
	 */
	protected void init() {
		imageDim = (int) Math.sqrt(((oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getImageSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getImageSize().getY() + oldTile.getTileBorder().getY() * 2)));
		tileDim = (int) Math.sqrt(((oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2) * (oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() * 2)) + ((oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2) * (oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() * 2)));
		bufferSize = new IntegerVector2D(tileDim, tileDim);
		newImage = new BufferedImage(bufferSize.getX(), bufferSize.getY(), Surface.DEFAULT_TYPE);
		oldImage = newImage;//new BufferedImage(bufferSize.getX(), bufferSize.getY(), Surface.DEFAULT_TYPE);
		newBuffer = newImage.createGraphics();
		oldBuffer = newBuffer;//oldImage.createGraphics();
	}

	/**
	 * 
	 */
	protected final void swapImages() {
		synchronized (lock) {
			final BufferedImage tmpImage = oldImage;
			oldImage = newImage;
			newImage = tmpImage;
			final Graphics2D tmpBuffer = oldBuffer;
			oldBuffer = newBuffer;
			newBuffer = tmpBuffer;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(final Map<Object, Object> hints) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#getRenderingStatus()
	 */
	@Override
	public int getRenderingStatus() {
		return status;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#setMode(int)
	 */
	@Override
	public void setMode(final int renderMode) {
		this.renderMode |= renderMode;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#getMode()
	 */
	@Override
	public int getMode() {
		return renderMode;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
		synchronized (this) {
			invalidated = false;
			newTile = tile;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#getTile()
	 */
	@Override
	public Tile getTile() {
		return oldTile;
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
	 * @return
	 */
	protected boolean isTileSupported() {
		return true;
	}

	@Override
	public boolean isDynamic() {
		final boolean value = dynamic;
		dynamic = false;
		return value;
	}

	@Override
	public boolean isViewChanged() {
		final boolean value = viewChanged;
		viewChanged = false;
		return value;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View)
	 */
	@Override
	public void setView(final View view) {
		synchronized (this) {
			newView = view;
		}
	}

	private void updateView(final View view) {
		rotationValue = view.getRotation().getZ();
		dynamic = view.getStatus().getZ() == 1;
		dynamicZoom = dynamic;
		if (view.getStatus().getZ() == 2) {
			setMode(ContextFreeRenderer.MODE_CALCULATE);
		}
	}

	/**
	 * 
	 */
	protected void updateTransform() {
		final int offsetX = (getBufferWidth() - oldTile.getTileSize().getX() - oldTile.getTileBorder().getX() * 2) / 2;
		final int offsetY = (getBufferHeight() - oldTile.getTileSize().getY() - oldTile.getTileBorder().getY() * 2) / 2;
		transform.setToTranslation(-offsetX, -offsetY);
		final int centerX = getBufferWidth() / 2;
		final int centerY = getBufferHeight() / 2;
		transform.rotate(rotationValue, centerX, centerY);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#startRenderer()
	 */
	@Override
	public void startRenderer() {
		renderWorker.executeTask();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#abortRenderer()
	 */
	@Override
	public void abortRenderer() {
		renderWorker.abortTasks();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#joinRenderer()
	 */
	@Override
	public void joinRenderer() throws InterruptedException {
		renderWorker.waitTasks();
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
	public void stop() {
		renderWorker.stop();
	}

	public void startTasks() {
		renderWorker.executeTask();
	}

	public void clearTasks() {
		renderWorker.clearTasks();
	}

	public void abortTasks() {
		renderWorker.abortTasks();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#asyncStop()
	 */
	@Override
	public final void asyncStop() {
		clearTasks();
		abortTasks();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#asyncStart()
	 */
	@Override
	public final void asyncStart() {
		startTasks();
	}

	@Override
	public ContextFreeRuntime getRuntime() {
		return runtime;
	}

	@Override
	public void setRuntime(ContextFreeRuntime runtime) {
		this.runtime = runtime;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawImage(final GraphicsContext gc) {
		synchronized (lock) {
			final Affine t = gc.getTransform();
			if (oldTile != null) {
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
//				gc.setTransform(transform);
//				gc.drawImage(newImage, 0, 0, null);
//				gc.setTransform(t);
//				gc.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y) {
		synchronized (lock) {
			final Affine t = gc.getTransform();
			if (oldTile != null) {
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
//				gc.setTransform(transform);
//				gc.drawImage(newImage, x, y, null);
//				gc.setTransform(t);
//				gc.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y, final int w, final int h) {
		synchronized (lock) {
			final Affine t = gc.getTransform();
			if (oldTile != null) {
//				gc.setClip(x, y, w, h);
//				gc.setTransform(transform);
//				final double sx = w / (double) getTile().getTileSize().getX();
//				final double sy = h / (double) getTile().getTileSize().getY();
//				final int dw = (int) Math.rint(bufferSize.getX() * sx);
//				final int dh = (int) Math.rint(bufferSize.getY() * sy);
//				gc.drawImage(newImage, x, y, dw, dh, null);
//				gc.setTransform(t);
//				gc.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#drawImage(java.awt.Graphics2D)
	 */
	@Override
	public void drawImage(final Graphics2D g) {
		synchronized (lock) {
			final AffineTransform t = g.getTransform();
			if (oldTile != null) {
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				g.setTransform(transform);
				g.drawImage(newImage, 0, 0, null);
				g.setTransform(t);
				g.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#drawImage(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g, final int x, final int y) {
		synchronized (lock) {
			final AffineTransform t = g.getTransform();
			if (oldTile != null) {
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				g.setTransform(transform);
				g.drawImage(newImage, x, y, null);
				g.setTransform(t);
				g.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g, final int x, final int y, final int w, final int h) {
		synchronized (lock) {
			final AffineTransform t = g.getTransform();
			if (oldTile != null) {
				g.setClip(x, y, w, h);
				g.setTransform(transform);
				final double sx = w / (double) getTile().getTileSize().getX();
				final double sy = h / (double) getTile().getTileSize().getY();
				final int dw = (int) Math.rint(bufferSize.getX() * sx);
				final int dh = (int) Math.rint(bufferSize.getY() * sy);
				g.drawImage(newImage, x, y, dw, dh, null);
				g.setTransform(t);
				g.setClip(null);
				// g.dispose();
			}
		}
	}

	/**
	 * @return
	 */
	protected Graphics2D getGraphics() {
		return oldBuffer;
	}
	
	/**
	 * @param scale 
	 * @return
	 */
	protected void copyOldBuffer() {
//		oldBuffer.setComposite(AlphaComposite.Src);
//		oldBuffer.drawImage(newImage, 0, 0, null);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotFractalRenderer#isInterrupted()
	 */
	@Override
	public boolean isInterrupted() {
		return Thread.currentThread().isInterrupted();
	}

	private void render() {
		synchronized (this) {
			cfdgRuntime = runtime.getCFDG();
			if (oldView != newView) {
				viewChanged = true;
				updateView(newView);
			}
			oldView = newView;
			if (newTile != oldTile) {
				setMode(ContextFreeRenderer.MODE_CALCULATE);
				oldTile = newTile;
				free();
				init();
			}
		}
		percent = 0;
		status = TwisterRenderer.STATUS_RENDERING;
		doRender(dynamicZoom);
		if (percent == 100) {
			status = TwisterRenderer.STATUS_TERMINATED;
		}
		else {
			status = TwisterRenderer.STATUS_ABORTED;
		}
		dynamicZoom = false;
	}

	protected abstract void doRender(boolean dynamicZoom);

	private class ContextFreeWorker extends RenderWorker {
		/**
		 * @param factory
		 */
		public ContextFreeWorker(ThreadFactory factory) {
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
