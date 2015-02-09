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

import java.nio.IntBuffer;
import java.util.concurrent.ThreadFactory;

import javafx.application.Platform;

import com.nextbreakpoint.nextfractal.core.Worker;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.strategy.JuliaRendererStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.strategy.MandelbrotRendererStrategy;
import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderFactory;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

/**
 * @author Andrea Medeghini
 */
public class Renderer {
	protected final RendererFractal rendererFractal;
	protected final ThreadFactory threadFactory;
	protected final RenderFactory renderFactory;
	protected final RendererData rendererData;
	protected final Worker rendererWorker;
	protected volatile RendererDelegate rendererDelegate;
	protected volatile RendererStrategy rendererStrategy;
	protected volatile boolean aborted;
	protected volatile boolean orbitChanged;
	protected volatile boolean colorChanged;
	protected volatile boolean regionChanged;
	protected volatile float progress;
	protected boolean julia;
	protected boolean continuous;
	protected Number constant;
	protected RendererRegion region;
	protected RendererRegion initialRegion;
	protected final RendererSize size;
	protected final RendererTile tile;
	protected RendererBuffer frontBuffer;
	protected RendererBuffer backBuffer;
	protected RendererView view;

	/**
	 * @param renderFactory 
	 * @param rendererDelegate
	 * @param rendererFractal
	 * @param width
	 * @param height
	 */
	public Renderer(ThreadFactory threadFactory, RenderFactory renderFactory, RendererTile tile) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.rendererWorker = new Worker(threadFactory);
		this.rendererData = createRendererData();
		this.rendererFractal = new RendererFractal();
		this.tile = tile;
		RendererSize tileSize = tile.getTileSize();
		RendererSize tileBorder = tile.getTileBorder();
		int tsw = tileSize.getWidth();
		int tbw = tileBorder.getWidth();
		int tsh = tileSize.getHeight();
		int tbh = tileBorder.getHeight();
		int tileDim = (int) Math.hypot(tsw + tbw * 2, tsh + tbh * 2);
		size = new RendererSize(tileDim, tileDim);
		view = new RendererView();
		frontBuffer = new RendererBuffer(); 
		backBuffer = new RendererBuffer(); 
		frontBuffer.setTile(tile);
		backBuffer.setTile(tile);
		frontBuffer.setSize(size);
		backBuffer.setSize(size);
		frontBuffer.setAffine(createTransform(0));
		backBuffer.setAffine(createTransform(0));
		frontBuffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getWidth()));
		backBuffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getHeight()));
		start();
	}

	public RendererTile getTile() {
		return tile;
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return size.getWidth();
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return size.getHeight();
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
	public void abortTasks() {
		rendererWorker.abortTasks();
	}

	/**
	 * 
	 */
	public void waitForTasks() {
		rendererWorker.waitForTasks();
	}

	/**
	 * 
	 */
	public void runTask() {
		rendererWorker.addTask(new Runnable() {
			@Override
			public void run() {
				doRender();
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
	public void setConstant(Number constant) {
		this.constant = constant;
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
	 * @param continuous
	 */
	public void setContinuous(boolean continuous) {
		this.continuous = continuous;
	}

	/**
	 * 
	 */
	public void init() {
		rendererFractal.initialize();
		initialRegion = new RendererRegion(rendererFractal.getOrbit().getInitialRegion());
	}

	/**
	 * 
	 */
	protected void free() {
		rendererData.free();
		if (frontBuffer != null) {
			frontBuffer.dispose();
			frontBuffer = null;
		}
		if (backBuffer != null) {
			backBuffer.dispose();
			backBuffer = null;
		}
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
	 * @return
	 */
	public RendererRegion getInitialRegion() {
		return initialRegion;
	}

	/**
	 * @return
	 */
	public RendererRegion getRegion() {
		return region;
	}

	/**
	 * @param region
	 */
	public void setRegion(RendererRegion region) {
		this.region = region;
		regionChanged = true; 
	}

	/**
	 * @param dynamic
	 */
	protected void doRender() {
		if (rendererFractal == null) {
			progress = 1;
			return;
		}
		final boolean redraw = orbitChanged || regionChanged || regionChanged;
		orbitChanged = false;
		colorChanged = false;
		regionChanged = false;
		aborted = false;
		progress = 0;
		rendererFractal.clearScope();
		rendererFractal.setConstant(constant);
		if (julia) {
			rendererStrategy = new JuliaRendererStrategy(rendererFractal);
		} else {
			rendererStrategy = new MandelbrotRendererStrategy(rendererFractal);
		}
		int width = getWidth();
		int height = getHeight();
		rendererStrategy.prepare();
		rendererData.setSize(width, height, rendererFractal.getStateSize());
		rendererData.setRegion(region);
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
		didChanged(0, rendererData.getPixels());
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
				didChanged(progress, rendererData.getPixels());
				ty += dy;
			}
			Thread.yield();
		}
		if (!aborted) {
			progress = 1f;
		}
		didChanged(progress, rendererData.getPixels());
		Thread.yield();
	}

	/**
	 * @param view
	 */
	public void setView(RendererView view) {
		this.view = view;
		RendererRegion region = computeRegion();
		setRegion(region);
		setJulia(view.isJulia());
		setConstant(view.getConstant());
		setContinuous(view.getState().getX() >= 1 || view.getState().getY() >= 1 || view.getState().getZ() >= 1 || view.getState().getW() >= 1);
		backBuffer.setAffine(createTransform(view.getRotation().getZ()));
	}
	
	/**
	 * 
	 */
	protected RendererRegion computeRegion() {
		final double tx = view.getTraslation().getX();
		final double ty = view.getTraslation().getY();
		final double tz = view.getTraslation().getZ();
		final double rz = view.getRotation().getZ();
		
		double a = rz * Math.PI / 180;
		
//		logger.info("tx = " + tx + ", ty = " + ty + ", tz = " + tz + ", rz = " + rz);
		
		final RendererSize tileBorder = backBuffer.getTile().getTileBorder();
		final RendererPoint tileOffset = backBuffer.getTile().getTileOffset();
		final RendererSize tileSize = backBuffer.getTile().getTileSize();
		final RendererSize imageSize = backBuffer.getTile().getImageSize();
		
		final RendererRegion region = getInitialRegion();
		
		final Number size = region.getSize();
		final Number center = region.getCenter();

		final double imageDim = (int) Math.hypot(imageSize.getWidth() + tileBorder.getWidth() * 2, imageSize.getHeight() + tileBorder.getHeight() * 2);

		int tsw = tileSize.getWidth();
		int tbw = tileBorder.getWidth();
		int tsh = tileSize.getHeight();
		int tbh = tileBorder.getHeight();
		int tileDim = (int) Math.hypot(tsw + tbw * 2, tsh + tbh * 2);

		final double dx = tz * size.r() * (imageDim / imageSize.getWidth()) / 2;
		final double dy = tz * size.i() * (imageDim / imageSize.getHeight()) / 2;
		
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
		final double sx = dx * (tileDim / imageDim);
		final double sy = dy * (tileDim / imageDim);

		final RendererRegion newRegion = new RendererRegion();
//		newRegion.setPoints(new Number(px, py), new Number(qx, qy));
		newRegion.setPoints(new Number(fx - sx, fy - sy), new Number(fx + sx, fy + sy));
//		logger.info(newRegion.toString());
		return newRegion;
	}

	/**
	 * @param rotation
	 * @return
	 */
	protected RenderAffine createTransform(double rotation) {
		final RendererSize tileSize = backBuffer.getTile().getTileSize();
		final RendererSize tileBorder = backBuffer.getTile().getTileBorder();
		final int offsetX = (getWidth() - tileSize.getWidth() - tileBorder.getWidth() * 2) / 2;
		final int offsetY = (getHeight() - tileSize.getHeight() - tileBorder.getHeight() * 2) / 2;
		final int centerX = getWidth() / 2;
		final int centerY = getHeight() / 2;
		final RenderAffine affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
		affine.append(renderFactory.createRotateAffine(rotation, centerX, centerY));
		return affine;
	}

	protected void didChanged(float progress, int[] pixels) {
		if (backBuffer != null) {
			backBuffer.getBuffer().update(pixels);
		}
		swap();
		if (rendererDelegate != null) {
			rendererDelegate.didChanged(progress);
		}
	}

	/**
	 * 
	 */
	protected final void swap() {
		synchronized (this) {
			final RendererBuffer tmpBuffer = backBuffer;
			backBuffer = frontBuffer;
			frontBuffer = tmpBuffer;
		}
	}

	/**
	 * @param gc
	 */
	public void drawImage(final RenderGraphicsContext gc) {
		synchronized (this) {
			if (frontBuffer != null) {
				gc.save();
				RendererPoint tileOffset = frontBuffer.getTile().getTileOffset();
				RendererSize tileSize = frontBuffer.getTile().getTileSize();
				gc.setClip(tileOffset.getX(), tileOffset.getY(), tileSize.getWidth(), tileSize.getHeight());
				gc.setAffine(frontBuffer.getAffine());
				gc.drawImage(frontBuffer.getBuffer().getImage(), tileOffset.getX(), tileOffset.getY());
				gc.restore();
			}
		}
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 */
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
		synchronized (this) {
			if (frontBuffer != null) {
				gc.save();
				RendererSize tileSize = frontBuffer.getTile().getTileSize();
				gc.setClip(x, y, tileSize.getWidth(), tileSize.getHeight());
				gc.setAffine(frontBuffer.getAffine());
				gc.drawImage(frontBuffer.getBuffer().getImage(), x, y);
				gc.restore();
			}
		}
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
		synchronized (this) {
			if (frontBuffer != null) {
				gc.save();
				gc.setClip(x, y, w, h);
				gc.setAffine(frontBuffer.getAffine());
				final double sx = w / (double) frontBuffer.getTile().getTileSize().getWidth();
				final double sy = h / (double) frontBuffer.getTile().getTileSize().getHeight();
				final int dw = (int) Math.rint(frontBuffer.getSize().getWidth() * sx);
				final int dh = (int) Math.rint(frontBuffer.getSize().getHeight() * sy);
				gc.drawImage(frontBuffer.getBuffer().getImage(), x, y, dw, dh);
				gc.restore();
			}
		}
	}
	
	/**
	 * @param pixels
	 */
	public void getPixels(IntBuffer pixels) {
		frontBuffer.getBuffer().getImage().getPixels(pixels);
	}
}
