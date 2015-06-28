/*
 * NextFractal 1.1.0
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
import java.util.List;
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
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
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
	protected volatile RendererTransform transform;
	protected volatile RendererSurface buffer;
	protected volatile boolean aborted;
	protected volatile boolean interrupted;
	protected volatile boolean orbitChanged;
	protected volatile boolean colorChanged;
	protected volatile boolean regionChanged;
	protected volatile boolean juliaChanged;
	protected volatile boolean pointChanged;
	protected volatile float progress;
	protected volatile double rotation;
	protected boolean julia;
	protected Number point;
	protected boolean multiThread;
	protected boolean singlePass;
	protected boolean continuous;
	protected RendererRegion region;
	protected RendererRegion initialRegion;
	protected RendererSize size;
	protected RendererView view;
	private final RendererLock lock = new DummyRendererLock();
	private final RenderRunnable renderTask = new RenderRunnable();
	private ExecutorService executor;
	private volatile Future<?> future;

	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 */
	public Renderer(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.rendererData = createRendererData();
		this.rendererFractal = new RendererFractal();
		view = new RendererView();
		buffer = new RendererSurface(); 
		buffer.setTile(tile);
		ensureBufferAndSize();
		buffer.setAffine(createTransform(0));
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
	public RendererSize getSize() {
		return size;
	}

	/**
	 * @return
	 */
	public boolean isInterrupted() {
		return interrupted;
	}

	/**
	 * 
	 */
	public void abortTasks() {
		interrupted = true;
//		if (future != null) {
//			future.cancel(true);
//		}
	}

	/**
	 * 
	 */
	public void waitForTasks() {
		try {
			if (future != null) {
				future.get();
				future = null;
			}
		} catch (Exception e) {
			interrupted = true;
//			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void runTask() {
		if (future == null) {
			interrupted = false;
			future = executor.submit(renderTask);
		}
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
	 * @param multiThread
	 */
	public void setMultiThread(boolean multiThread) {
		this.multiThread = multiThread;
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
		lock.lock();
		if ((rotation == 0 && view.getRotation().getZ() != 0) || (rotation != 0 && view.getRotation().getZ() == 0)) {
			rotation = view.getRotation().getZ();
			ensureBufferAndSize();
			orbitChanged = true;
		} else {
			rotation = view.getRotation().getZ();
		}
		transform = new RendererTransform();
		transform.traslate(view.getTraslation().getX(), view.getTraslation().getY());
		transform.rotate(-rotation * Math.PI / 180);
		transform.traslate(-view.getTraslation().getX(), -view.getTraslation().getY());
		buffer.setAffine(createTransform(rotation));
		setRegion(computeRegion());
		setJulia(view.isJulia());
		setPoint(view.getPoint());
		setContinuous(view.getState().getX() >= 1 || view.getState().getY() >= 1 || view.getState().getZ() >= 1 || view.getState().getW() >= 1);
		lock.unlock();
	}

	private void ensureBufferAndSize() {
		size = computeOptimalBufferSize(buffer.getTile(), rotation);
		buffer.setSize(size);
		buffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getHeight()));
		System.out.println(size);
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
	public void getPixels(int[] pixels) {
		int bufferWidth = buffer.getSize().getWidth();
		int bufferHeight = buffer.getSize().getHeight();
		int[] bufferPixels = new int[bufferWidth * bufferHeight];
		IntBuffer tmpBuffer = IntBuffer.wrap(bufferPixels); 
		buffer.getBuffer().getImage().getPixels(tmpBuffer);
		int tileWidth = buffer.getTile().getTileSize().getWidth();
		int tileHeight = buffer.getTile().getTileSize().getHeight();
		int borderWidth = buffer.getTile().getBorderSize().getWidth();
		int borderHeight = buffer.getTile().getBorderSize().getHeight();
		int offsetX = (bufferWidth - tileWidth - borderWidth * 2) / 2;
		int offsetY = (bufferHeight - tileHeight - borderHeight * 2) / 2;
		int offset = offsetY * bufferWidth + offsetX;
		int tileOffset = 0;
		for (int y = 0; y < tileHeight; y++) {
			System.arraycopy(bufferPixels, offset, pixels, tileOffset, tileWidth);
			offset += bufferWidth;
			tileOffset += tileWidth + borderWidth * 2;
		}
	}
	
	/**
	 * @param gc
	 */
	public void drawImage(final RendererGraphicsContext gc) {
		lock.lock();
		if (buffer != null) {
			gc.save();
			RendererSize imageSize = buffer.getTile().getImageSize();
			RendererSize tileSize = buffer.getTile().getTileSize();
			final int offsetX = (getSize().getWidth() - tileSize.getWidth()) / 2;
			final int offsetY = (getSize().getHeight() - tileSize.getHeight()) / 2;
			gc.setAffine(buffer.getAffine());
//			gc.setClip(-offsetX, -offsetY + tileSize.getHeight() - imageSize.getHeight(), getSize().getWidth(), getSize().getHeight());
			gc.drawImage(buffer.getBuffer().getImage(), -offsetX, -offsetY + tileSize.getHeight() - imageSize.getHeight());
			gc.setStroke(renderFactory.createColor(1, 0, 0, 1));
			gc.strokeRect(0, tileSize.getHeight() - imageSize.getHeight(), tileSize.getWidth(), tileSize.getHeight());
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
			RendererSize imageSize = buffer.getTile().getImageSize();
			RendererSize tileSize = buffer.getTile().getTileSize();
			gc.setClip(x, imageSize.getHeight() - tileSize.getHeight() - y, tileSize.getWidth(), tileSize.getHeight());
			gc.setAffine(buffer.getAffine());
			gc.drawImage(buffer.getBuffer().getImage(), x, y + tileSize.getHeight() - imageSize.getHeight());
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
			RendererSize imageSize = buffer.getTile().getImageSize();
			RendererSize tileSize = buffer.getTile().getTileSize();
			gc.setClip(x, imageSize.getHeight() - tileSize.getHeight() - y, w, h);
			gc.setAffine(buffer.getAffine());
			final double sx = w / (double) buffer.getTile().getTileSize().getWidth();
			final double sy = h / (double) buffer.getTile().getTileSize().getHeight();
			final int dw = (int) Math.rint(buffer.getSize().getWidth() * sx);
			final int dh = (int) Math.rint(buffer.getSize().getHeight() * sy);
			gc.drawImage(buffer.getBuffer().getImage(), x, y + tileSize.getHeight() - imageSize.getHeight(), dw, dh);
			gc.restore();
		}
		lock.unlock();
	}

	/**
	 * @param tile
	 * @param rotation
	 * @return
	 */
	protected RendererSize computeOptimalBufferSize(RendererTile tile, double rotation) {
		RendererSize tileSize = tile.getTileSize();
		RendererSize imageSize = tile.getImageSize();
		RendererSize borderSize = tile.getBorderSize();
		int tw = tileSize .getWidth();
		int th = tileSize.getHeight();
		int bw = borderSize.getWidth();
		int bh = borderSize.getHeight();
		if (rotation == 0) {
			return new RendererSize(tw + bw * 2, th + bh * 2);
		} else {
			RendererSize maxSize = computeBufferSize(imageSize);
			int hcells = (int) Math.rint(imageSize.getWidth() / (double)tileSize.getWidth());
			int vcells = (int) Math.rint(imageSize.getHeight() / (double)tileSize.getHeight());
			int width = (int) Math.rint(maxSize.getWidth() / (double)hcells) + bw * 2;
			int height = (int) Math.rint(maxSize.getHeight() / (double)vcells) + bh * 2;
			return new RendererSize(width, height);
		}
	}

	/**
	 * @param tile
	 * @return
	 */
	protected RendererSize computeBufferSize(RendererSize tileSize) {
		int tw = tileSize.getWidth();
		int th = tileSize.getHeight();
		int tileDim = (int) Math.hypot(tw, th);
		return new RendererSize(tileDim, tileDim);
	}

	/**
	 * @param tile
	 * @return
	 */
	protected RendererSize computeBufferSize(RendererSize tileSize, RendererSize borderSize) {
		int bw = borderSize.getWidth();
		int bh = borderSize.getHeight();
		RendererSize size = computeBufferSize(tileSize);
		return new RendererSize(size.getWidth() + bw * 2, size.getHeight() + bh * 2);
	}

	/**
	 * 
	 */
	protected void doRender() {
		try {
//			if (isInterrupted()) {
//				progress = 0;
//				rendererData.swap();
//				rendererData.clearPixels();
//				didChanged(progress, rendererData.getPixels());
//				return;
//			}
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
					transform.transform(pw);
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
	 * 
	 */
	protected RendererRegion computeRegion() {
		final double tx = view.getTraslation().getX();
		final double ty = view.getTraslation().getY();
		final double tz = view.getTraslation().getZ();
//		final double rz = view.getRotation().getZ();
		
//		double a = convertDegToRad(rz);
		
//		logger.info("tx = " + tx + ", ty = " + ty + ", tz = " + tz + ", rz = " + rz);
		
		final RendererSize imageSize = buffer.getTile().getImageSize();
		final RendererSize tileSize = buffer.getTile().getTileSize();
		final RendererPoint tileOffset = buffer.getTile().getTileOffset();
		final RendererSize borderSize = buffer.getTile().getBorderSize();
		
		final RendererRegion region = getInitialRegion();
		
		final Number size = region.getSize();
		final Number center = region.getCenter();

		final RendererSize bufferSize = computeBufferSize(imageSize, borderSize);

		final double dx = tz * size.r() * ((double)bufferSize.getWidth() / (double)imageSize.getWidth()) / 2;
		final double dy = tz * size.i() * ((double)bufferSize.getHeight() / (double)imageSize.getWidth()) / 2;
		
		final double cx = center.r();
		final double cy = center.i();
		final double px = cx - dx + tx;
		final double py = cy - dy + ty;
		final double qx = cx + dx + tx;
		final double qy = cy + dy + ty;

		final double gx = px + (qx - px) * ((bufferSize.getWidth() - imageSize.getWidth()) / 2.0 + tileOffset.getX() + tileSize.getWidth() / 2) / (double)bufferSize.getWidth();
		final double gy = py + (qy - py) * ((bufferSize.getHeight() - imageSize.getHeight()) / 2.0 + tileOffset.getY() + tileSize.getHeight() / 2) / (double)bufferSize.getHeight();
		final double fx = gx;//Math.cos(a) * (gx - cx) + Math.sin(a) * (gy - cx) + cx; 
		final double fy = gy;//Math.cos(a) * (gy - cy) - Math.sin(a) * (gx - cx) + cy;
		final double sx = dx * (getSize().getWidth() / (double)bufferSize.getWidth());
		final double sy = dy * (getSize().getHeight() / (double)bufferSize.getHeight());

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
		final RendererSize imageSize = buffer.getTile().getImageSize();
		final RendererPoint tileOffset = buffer.getTile().getTileOffset();
		RendererSize bufferSize = getSize();
		final int offsetX = (bufferSize.getWidth() - tileSize.getWidth()) / 2;
		final int offsetY = (bufferSize.getHeight() - tileSize.getHeight()) / 2;
		final int centerX = bufferSize.getWidth() / 2 - offsetX;
		final int centerY = bufferSize.getHeight() / 2 - offsetY;
		final int rotCenterX = imageSize.getWidth() / 2 - tileOffset.getX();
		final int rotCenterY = imageSize.getHeight() / 2 - tileSize.getHeight() - tileOffset.getY();
		final RendererAffine affine = renderFactory.createAffine();
		affine.append(renderFactory.createTranslateAffine(0, +centerY));
		affine.append(renderFactory.createScaleAffine(1, -1));
		affine.append(renderFactory.createTranslateAffine(0, -centerY));
		affine.append(renderFactory.createTranslateAffine(tileOffset.getX(), tileOffset.getY()));
		affine.append(renderFactory.createRotateAffine(rotation, rotCenterX, rotCenterY));
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

	public List<Trap> getTraps() {
		return rendererFractal.getOrbit().getTraps();
	}
}
