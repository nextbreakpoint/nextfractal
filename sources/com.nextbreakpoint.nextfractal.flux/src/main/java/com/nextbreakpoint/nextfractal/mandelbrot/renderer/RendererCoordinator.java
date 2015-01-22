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
	private final HashMap<String, Integer> hints = new HashMap<>();
	private final ThreadFactory threadFactory;
	private final RenderFactory renderFactory;
	private volatile boolean pixelsChanged;
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
		RendererSize tileSize = tile.getTileSize();
//		RendererSize imageSize = tile.getImageSize();
		RendererSize tileBorder = tile.getTileBorder();
		int tsw = tileSize.getWidth();
		int tbw = tileBorder.getWidth();
		int tsh = tileSize.getHeight();
		int tbh = tileBorder.getHeight();
//		imageDim = (int) Math.hypot(imageSize.getWidth() + tileBorder.getWidth() * 2, imageSize.getHeight() + tileBorder.getHeight() * 2);
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
		RendererRegion region = computeRegion();
		renderer.setRegion(region);
		renderer.setJulia(view.isJulia());
		renderer.setConstant(view.getConstant());
		renderer.setContinuous(view.getState().getX() >= 1 || view.getState().getY() >= 1 || view.getState().getZ() >= 1 || view.getState().getW() >= 1);
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
		
		final RendererRegion region = renderer.getInitialRegion();
		
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

	public Number getInitialCenter() {
		return renderer.getInitialRegion().getCenter();
	}

	public Number getInitialSize() {
		return renderer.getInitialRegion().getSize();
	}

	public RendererTile getTile() {
		return tile;
	}
}
