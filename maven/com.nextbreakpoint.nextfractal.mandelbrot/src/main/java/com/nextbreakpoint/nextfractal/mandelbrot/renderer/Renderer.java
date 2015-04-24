/*
 * NextFractal 1.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import java.nio.IntBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.nextbreakpoint.nextfractal.core.renderer.RendererAffine;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSurface;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.utils.Condition;
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
//	private static final Logger logger = Logger.getLogger(Renderer.class.getName());
	protected final RendererFractal rendererFractal;
	protected final ThreadFactory threadFactory;
	protected final RendererFactory renderFactory;
	protected final RendererData rendererData;
	protected volatile RendererDelegate rendererDelegate;
	protected volatile RendererStrategy rendererStrategy;
	protected volatile RendererSurface buffer;
	protected volatile boolean aborted;
	protected volatile boolean orbitChanged;
	protected volatile boolean colorChanged;
	protected volatile boolean regionChanged;
	protected volatile boolean juliaChanged;
	protected volatile boolean pointChanged;
	protected volatile float progress;
	protected boolean julia;
	protected Number point;
	protected boolean singlePass;
	protected boolean continuous;
	protected RendererRegion region;
	protected RendererRegion initialRegion;
	protected final RendererSize size;
	protected final RendererTile tile;
	protected RendererView view;
	protected Condition condition;
	private final RendererLock lock = new DummyRendererLock();
	private final RenderRunnable renderTask = new RenderRunnable();
	private ExecutorService executor;
	private volatile Future<?> future;

	/**
	 * @param renderFactory 
	 * @param rendererDelegate
	 * @param rendererFractal
	 * @param width
	 * @param height
	 */
	public Renderer(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.rendererData = createRendererData();
		this.rendererFractal = new RendererFractal();
		this.tile = tile;
		RendererSize tileSize = tile.getTileSize();
		RendererSize borderSize = tile.getBorderSize();
		int tileDim = computeDim(tileSize, borderSize);
		size = new RendererSize(tileDim, tileDim);
		view = new RendererView();
		buffer = new RendererSurface(); 
		buffer.setTile(tile);
		buffer.setSize(size);
		buffer.setAffine(createTransform(0));
		buffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getHeight()));
		executor = Executors.newSingleThreadExecutor(threadFactory);
	}

	/**
	 * 
	 */
	public void dispose() {
		shutdown();
		free();
	}

	/**
	 * @return
	 */
	public RendererTile getTile() {
		return tile;
	}

	/**
	 * @return
	 */
	public RendererSize getSize() {
		return size;
	}

	/**
	 * @return
	 */
	public boolean isInterrupted() {
		return aborted || Thread.currentThread().isInterrupted() || (condition != null && condition.evaluate());
	}

	/**
	 * 
	 */
	public void abortTasks() {
		if (future != null) {
			future.cancel(true);
		}
	}

	/**
	 * 
	 */
	public void waitForTasks() {
		try {
			if (future != null) {
				if (!future.isCancelled()) {
					future.get();
				}
			}
		} catch (InterruptedException | ExecutionException e) {
		}
	}

	/**
	 * 
	 */
	public void runTask() {
		abortTasks();
		waitForTasks();
		future = executor.submit(renderTask);
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
	public float getProgress() {
		return progress;
	}

	/**
	 * 
	 */
	public void init() {
		rendererFractal.initialize();
		initialRegion = new RendererRegion(rendererFractal.getOrbit().getInitialRegion());
	}

	/**
	 * @return
	 */
	public boolean isSinglePass() {
		return singlePass;
	}

	/**
	 * @param singlePass
	 */
	public void setSinglePass(boolean singlePass) {
		this.singlePass = singlePass;
	}

	/**
	 * @param condition
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @param continuous
	 */
	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
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
	 * @param julia
	 */
	public void setJulia(boolean julia) {
		if (this.julia != julia) {
			this.julia = julia;
			juliaChanged = true;
		}
	}

	/**
	 * @param point
	 */
	public void setPoint(Number point) {
		if (this.point == null || !this.point.equals(point)) {
			this.point = point;
			pointChanged = true;
		}
	}

	/**
	 * @param region
	 */
	public void setRegion(RendererRegion region) {
		if (this.region == null || !this.region.equals(region)) {
			this.region = region;
			regionChanged = true; 
		}
	}

	/**
	 * @param view
	 */
	public void setView(RendererView view) {
		this.view = view;
		setRegion(computeRegion());
		setJulia(view.isJulia());
		setPoint(view.getPoint());
		setContinuous(view.getState().getX() >= 1 || view.getState().getY() >= 1 || view.getState().getZ() >= 1 || view.getState().getW() >= 1);
		lock.lock();
		buffer.setAffine(createTransform(view.getRotation().getZ()));
		lock.unlock();
	}

	/**
	 * @return
	 */
	public RendererRegion getInitialRegion() {
		return initialRegion;
	}

	/**
	 * @param pixels
	 */
	public void getPixels(IntBuffer pixels) {
		buffer.getBuffer().getImage().getPixels(pixels);
	}
	
	/**
	 * @param gc
	 */
	public void drawImage(final RendererGraphicsContext gc) {
		lock.lock();
		if (buffer != null) {
			gc.save();
			RendererPoint tileOffset = buffer.getTile().getTileOffset();
			RendererSize tileSize = buffer.getTile().getTileSize();
			gc.setClip(tileOffset.getX(), tileOffset.getY(), tileSize.getWidth(), tileSize.getHeight());
			gc.setAffine(buffer.getAffine());
			gc.drawImage(buffer.getBuffer().getImage(), tileOffset.getX(), tileOffset.getY());
			gc.restore();
		}
		lock.unlock();
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 */
	public void drawImage(final RendererGraphicsContext gc, final int x, final int y) {
		lock.lock();
		if (buffer != null) {
			gc.save();
			RendererSize tileSize = buffer.getTile().getTileSize();
			gc.setClip(x, y, tileSize.getWidth(), tileSize.getHeight());
			gc.setAffine(buffer.getAffine());
			gc.drawImage(buffer.getBuffer().getImage(), x, y);
			gc.restore();
		}
		lock.unlock();
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void drawImage(final RendererGraphicsContext gc, final int x, final int y, final int w, final int h) {
		lock.lock();
		if (buffer != null) {
			gc.save();
			gc.setClip(x, y, w, h);
			gc.setAffine(buffer.getAffine());
			final double sx = w / (double) buffer.getTile().getTileSize().getWidth();
			final double sy = h / (double) buffer.getTile().getTileSize().getHeight();
			final int dw = (int) Math.rint(buffer.getSize().getWidth() * sx);
			final int dh = (int) Math.rint(buffer.getSize().getHeight() * sy);
			gc.drawImage(buffer.getBuffer().getImage(), x, y, dw, dh);
			gc.restore();
		}
		lock.unlock();
	}

	/**
	 * 
	 */
	protected void doRender() {
		try {
			if (rendererFractal == null) {
				progress = 1;
				rendererData.swap();
				rendererData.clearPixels();
				didChanged(progress, rendererData.getPixels());
				return;
			}
			if (rendererFractal.getOrbit() == null) {
				progress = 1;
				rendererData.swap();
				rendererData.clearPixels();
				didChanged(progress, rendererData.getPixels());
				return;
			}
			if (rendererFractal.getColor() == null) {
				progress = 1;
				rendererData.swap();
				rendererData.clearPixels();
				didChanged(progress, rendererData.getPixels());
				return;
			}
			final boolean redraw = orbitChanged || regionChanged || juliaChanged || (julia && pointChanged);
			pointChanged = false;
			orbitChanged = false;
			colorChanged = false;
			regionChanged = false;
			aborted = false;
			progress = 0;
			rendererFractal.clearScope();
			rendererFractal.setPoint(point);
			if (julia) {
				rendererStrategy = new JuliaRendererStrategy(rendererFractal);
			} else {
				rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
			}
			int width = getSize().getWidth();
			int height = getSize().getHeight();
			rendererStrategy.prepare();
			rendererData.setSize(width, height, rendererFractal.getStateSize());
			rendererData.setRegion(region);
			rendererData.setPoint(rendererFractal.getPoint());
			rendererData.initPositions();
			rendererData.swap();
			rendererData.clearPixels();
			final MutableNumber px = new MutableNumber(0, 0);
			final MutableNumber pw = new MutableNumber(0, 0);
			final RendererState p = rendererData.newPoint();
			int offset = 0;
			int c = 0;
			float dy = height / 5.0f;
			float ty = dy;
			if (!singlePass) {
				didChanged(0, rendererData.getPixels());
			}
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					px.set(rendererData.point());
					pw.set(rendererData.positionX(x), rendererData.positionY(y));
					if (redraw) {
						c = rendererStrategy.renderPoint(p, px, pw);
					} else {
						rendererData.getPoint(offset, p);
						c = rendererStrategy.renderColor(p);
					}
					rendererData.setPoint(offset, p);
					rendererData.setPixel(offset, c);
					offset += 1;
				}
				if (isInterrupted()) {
					aborted = true;
					break;
				}
				if (y >= ty) {
					progress = y / (float)(height - 1);
					if (!singlePass) {
						didChanged(progress, rendererData.getPixels());
					}
					ty += dy;
				}
				Thread.yield();
			}
			if (!aborted) {
				progress = 1f;
				didChanged(progress, rendererData.getPixels());
			}
			Thread.yield();
		} catch (Throwable e) {
		}
	}

	/**
	 * @param tileSize
	 * @param borderSize
	 * @return
	 */
	protected int computeDim(RendererSize tileSize, RendererSize borderSize) {
		int tw = tileSize.getWidth();
		int bw = borderSize.getWidth();
		int th = tileSize.getHeight();
		int bh = borderSize.getHeight();
		int tileDim = (int) Math.hypot(tw + bw * 2, th + bh * 2);
		return tileDim;
	}

	/**
	 * 
	 */
	protected RendererRegion computeRegion() {
		final double tx = view.getTraslation().getX();
		final double ty = view.getTraslation().getY();
		final double tz = view.getTraslation().getZ();
		final double rz = view.getRotation().getZ();
		
		double a = convertDegToRad(rz);
		
//		logger.info("tx = " + tx + ", ty = " + ty + ", tz = " + tz + ", rz = " + rz);
		
		final RendererSize imageSize = buffer.getTile().getImageSize();
		final RendererSize tileSize = buffer.getTile().getTileSize();
		final RendererPoint tileOffset = buffer.getTile().getTileOffset();
		final RendererSize borderSize = buffer.getTile().getBorderSize();
		
		final RendererRegion region = getInitialRegion();
		
		final Number size = region.getSize();
		final Number center = region.getCenter();

		final double imageDim = computeDim(imageSize, borderSize);

		final double dx = tz * size.r() * (imageDim / imageSize.getWidth()) / 2;
		final double dy = tz * size.i() * (imageDim / imageSize.getWidth()) / 2;
		
		final double cx = center.r();
		final double cy = center.i();
		final double px = cx - dx + tx;
		final double py = cy - dy + ty;
		final double qx = cx + dx + tx;
		final double qy = cy + dy + ty;

		final double gx = px + (qx - px) * ((imageDim - imageSize.getWidth()) / 2 + tileOffset.getX() + tileSize.getWidth() / 2) / imageDim;
		final double gy = py + (qy - py) * ((imageDim - imageSize.getHeight()) / 2 + tileOffset.getY() + tileSize.getHeight() / 2) / imageDim;
		final double fx = Math.cos(a) * (gx - cx) + Math.sin(a) * (gy - cx) + cx; 
		final double fy = Math.cos(a) * (gy - cy) - Math.sin(a) * (gx - cx) + cy;
		final double sx = dx * (getSize().getWidth() / imageDim);
		final double sy = dy * (getSize().getHeight() / imageDim);

		final RendererRegion newRegion = new RendererRegion(new Number(fx - sx, fy - sy), new Number(fx + sx, fy + sy));
//		logger.info(newRegion.toString());
		return newRegion;
	}

	/**
	 * @param rotation
	 * @return
	 */
	protected RendererAffine createTransform(double rotation) {
		final RendererSize tileSize = buffer.getTile().getTileSize();
		final RendererSize tileBorder = buffer.getTile().getBorderSize();
		int width = getSize().getWidth();
		int height = getSize().getHeight();
		final int offsetX = (width - tileSize.getWidth() - tileBorder.getWidth() * 2) / 2;
		final int offsetY = (height - tileSize.getHeight() - tileBorder.getHeight() * 2) / 2;
		final int centerX = width / 2;
		final int centerY = height / 2;
		final RendererAffine affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
		affine.append(renderFactory.createRotateAffine(rotation, centerX, centerY));
		return affine;
	}

	/**
	 * @param progress
	 * @param pixels
	 */
	protected void didChanged(float progress, int[] pixels) {
		lock.lock();
		if (buffer != null) {
			buffer.getBuffer().update(pixels);
		}
		lock.unlock();
		if (rendererDelegate != null) {
			rendererDelegate.updateImageInBackground(progress);
		}
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
		if (buffer != null) {
			buffer.dispose();
			buffer = null;
		}
	}

	/**
	 * 
	 */
	protected void shutdown() {
		executor.shutdownNow();
		try {
			executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * @param a
	 * @return
	 */
	protected double convertDegToRad(final double a) {
		return a * Math.PI / 180;
	}

	private class RenderRunnable implements Runnable {
		@Override
		public void run() {
			doRender();
		}
	}
}