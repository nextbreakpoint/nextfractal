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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.xaos.XaosRenderer;
import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderFactory;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

/**
 * @author Andrea Medeghini
 */
public class RendererCoordinator implements RendererDelegate {
	public static final String KEY_TYPE = "TYPE";
	public static final Integer VALUE_REALTIME = 1;
	protected static final Logger logger = Logger.getLogger(RendererCoordinator.class.getName());
	private final HashMap<String, Integer> hints = new HashMap<>();
	private final ThreadFactory threadFactory;
	private final RenderFactory renderFactory;
	private volatile boolean pixelsChanged;
	private volatile boolean continuous;
	private volatile float progress;
	private final RendererSize size;
	private final RendererTile tile;
	private RendererBuffer frontBuffer;
	private RendererBuffer backBuffer;
	private RendererView view;
	private Renderer renderer;
	
	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 * @param hints
	 */
	public RendererCoordinator(ThreadFactory threadFactory, RenderFactory renderFactory, RendererTile tile, Map<String, Integer> hints) {
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.tile = tile;
		this.hints.putAll(hints);
		frontBuffer = new RendererBuffer(); 
		backBuffer = new RendererBuffer(); 
		RendererSize tileSize = tile.getTileSize();
		RendererSize tileBorder = tile.getTileBorder();
		int tsw = tileSize.getWidth();
		int tbw = tileBorder.getWidth();
		int tsh = tileSize.getHeight();
		int tbh = tileBorder.getHeight();
		int tileDim = (int) Math.hypot(tsw + tbw * 2, tsh + tbh * 2);
		size = new RendererSize(tileDim, tileDim);
		view = new RendererView();
		frontBuffer.setTile(tile);
		backBuffer.setTile(tile);
		frontBuffer.setSize(size);
		backBuffer.setSize(size);
		frontBuffer.setAffine(createTransform(0));
		backBuffer.setAffine(createTransform(0));
		frontBuffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getWidth()));
		backBuffer.setBuffer(renderFactory.createBuffer(size.getWidth(), size.getHeight()));
		renderer = createRenderer();
		renderer.setRendererDelegate(this);
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * 
	 */
	public final void dispose() {
		free();
	}

	/**
	 * 
	 */
	public void stopRender() {
		renderer.stopRender();
	}
	
	/**
	 * 
	 */
	public void abortRender() {
		renderer.abortRender();
	}
	
	/**
	 * 
	 */
	public void joinRender() {
		renderer.joinRender();
	}
	
	/**
	 * 
	 */
	public void startRender() {
		pixelsChanged = false;
		progress = 0;
		renderer.setContinuous(continuous);
		renderer.startRender();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererDelegate#didChanged(float, int[])
	 */
	@Override
	public void didChanged(float progress, int[] pixels) {
		if (backBuffer != null) {
			backBuffer.getBuffer().update(pixels);
		}
		swap();
		this.progress = progress;
		this.pixelsChanged = true;
	}

	/**
	 * @return
	 */
	public boolean isPixelsChanged() {
		boolean result = pixelsChanged;
		pixelsChanged = false;
		return result;
	}

	/**
	 * @return
	 */
	public float getProgress() {
		return progress;
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
	 * @param constant
	 */
	public void setConstant(Number constant) {
		renderer.setConstant(constant);
	}

	/**
	 * @param julia
	 */
	public void setJulia(final boolean julia) {
		renderer.setJulia(julia);
	}

	/**
	 * @param orbit
	 * @param color
	 */
	public void setOrbitAndColor(Orbit orbit, Color color) {
		renderer.setOrbit(orbit);
		renderer.setColor(color);
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		renderer.setColor(color);
	}

	/**
	 * 
	 */
	public void init() {
		renderer.init();
	}

	/**
	 * @return
	 */
	public boolean isTileSupported() {
		return true;
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
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(frontBuffer.getAffine());
				gc.drawImage(frontBuffer.getBuffer().getImage(), 0, 0);
				//gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
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
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(frontBuffer.getAffine());
				gc.drawImage(frontBuffer.getBuffer().getImage(), x, y);
				//gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
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
				gc.saveTransform();
				//TODO gc.setClip(x, y, w, h);
				gc.setAffine(frontBuffer.getAffine());
				final double sx = w / (double) tile.getTileSize().getWidth();
				final double sy = h / (double) tile.getTileSize().getHeight();
				final int dw = (int) Math.rint(size.getWidth() * sx);
				final int dh = (int) Math.rint(size.getHeight() * sy);
				gc.drawImage(frontBuffer.getBuffer().getImage(), x, y, dw, dh);
				//TODO gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
			}
		}
	}

	/**
	 * 
	 */
	protected void free() {
		if (renderer != null) {
			renderer.dispose();
			renderer = null;
		}
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
	 * @return
	 */
	protected Renderer createRenderer() {
		Integer type = hints.get(KEY_TYPE);
		if (type != null && type.equals(VALUE_REALTIME)) {
			return new XaosRenderer(threadFactory, getWidth(), getHeight());
		} else {
			return new Renderer(threadFactory, getWidth(), getWidth());
		}
	}

	/**
	 * @param view
	 */
	public void setView(RendererView view) {
		this.view = view;
		stopRender();
		Number[] region = computeRegion();
		renderer.setRegion(region);
		renderer.setJulia(view.isJulia());
		renderer.setConstant(view.getConstant());
		continuous = (view.getState().getZ() == 1) || (view.getState().getW() == 1);
		backBuffer.setAffine(createTransform(view.getRotation().getZ()));
		startRender();
	}
	
	/**
	 * 
	 */
	protected Number[] computeRegion() {
		final double tx = view.getTraslation().getX();
		final double ty = view.getTraslation().getY();
		final double tz = view.getTraslation().getZ();
		final double rz = view.getRotation().getZ();
		final RendererSize tileBorder = tile.getTileBorder();
		final RendererSize tileOffset = tile.getTileOffset();
		final RendererSize tileSize = tile.getTileSize();
		final RendererSize imageSize = tile.getImageSize();
		final Number[] region = renderer.getInitialRegion();
		final Number center = new Number((region[0].r() + region[1].r()) / 2 + tx, (region[0].i() + region[1].i()) / 2 + ty);
		final Number size = new Number((region[1].r() - region[0].r()) * tz, (region[1].i() - region[0].i()) * tz);
		final int imageDim = (int) Math.hypot(imageSize.getWidth() + tileBorder.getWidth() * 2, imageSize.getHeight() + tileBorder.getHeight() * 2);
		final double imageOffsetX = (imageDim - imageSize.getWidth()) / 2;
		final double imageOffsetY = (imageDim - imageSize.getHeight()) / 2;
		final double w = (size.r() * 0.5 * imageDim) / imageSize.getWidth();
		final double h = (size.i() * 0.5 * imageDim) / imageSize.getWidth();
		final Number p0 = new Number(center.r() - w, center.i() - h);
		final Number p1 = new Number(center.r() + w, center.i() + h);
		final double dr = p1.r() - p0.r();
		final double di = p1.i() - p0.i();
		final double qr = p0.r() + dr * (imageOffsetX + tileOffset.getWidth() + tileSize.getWidth() / 2d) / imageDim;
		final double qi = p0.i() + di * (imageOffsetY + tileOffset.getHeight() + tileSize.getHeight() / 2d) / imageDim;
		final double cr = p0.r() + dr * 0.5;
		final double ci = p0.i() + di * 0.5;
		final double cx = (Math.cos(rz) * (qr - cr) - Math.sin(rz) * (qi - ci)) + cr; 
		final double cy = (Math.sin(rz) * (qr - cr) + Math.cos(rz) * (qi - ci)) + ci; 
		final double dx = (dr * 0.5 * getWidth()) / imageDim;
		final double dy = (di * 0.5 * getHeight()) / imageDim;
		final Number[] newRegion = new Number[2];
		newRegion[0] = new Number(cx - dx, cy - dy);
		newRegion[1] = new Number(cx + dx, cy + dy);
		logger.info(newRegion[0] + " " + newRegion[1]);
		return newRegion;
	}

	/**
	 * @param rotation
	 * @return
	 */
	protected RenderAffine createTransform(double rotation) {
		final RendererSize tileSize = tile.getTileSize();
		final RendererSize tileBorder = tile.getTileBorder();
		final int offsetX = (getWidth() - tileSize.getWidth() - tileBorder.getWidth() * 2) / 2;
		final int offsetY = (getHeight() - tileSize.getHeight() - tileBorder.getHeight() * 2) / 2;
		final int centerX = getWidth() / 2;
		final int centerY = getHeight() / 2;
		final RenderAffine affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
		affine.append(renderFactory.createRotateAffine(rotation, centerX, centerY));
		return affine;
	}
}
