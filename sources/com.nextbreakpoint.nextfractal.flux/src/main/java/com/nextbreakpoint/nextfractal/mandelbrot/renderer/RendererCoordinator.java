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

import com.nextbreakpoint.nextfractal.core.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.Tile;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.xaos.XaosRenderer;
import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderBuffer;
import com.nextbreakpoint.nextfractal.render.RenderFactory;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

/**
 * @author Andrea Medeghini
 */
public class RendererCoordinator implements RendererDelegate {
	protected static final Logger logger = Logger.getLogger(RendererCoordinator.class.getName());
	public static final String KEY_TYPE = "TYPE";
	public static final Integer VALUE_REALTIME = 1;
	private final HashMap<String, Integer> hints = new HashMap<>();
	private RenderBuffer buffer;
	private RenderBuffer backBuffer;
//	private View newView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
//	private View oldView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	private IntegerVector2D size;
	private DoubleVector4D rotation;
	private DoubleVector4D center;
	private DoubleVector4D scale;
	private final ThreadFactory threadFactory;
	private final RenderFactory renderFactory;
	private Renderer renderer;
	private RenderAffine affine;
	private volatile boolean changed;
	private volatile float progress;
	private final Tile tile;

	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 * @param hints
	 */
	public RendererCoordinator(ThreadFactory threadFactory, RenderFactory renderFactory, Tile tile, Map<String, Integer> hints) {
//		factory = new DefaultThreadFactory("XaosRendererCoordinator", true, threadPriority);
		this.threadFactory = threadFactory;
		this.renderFactory = renderFactory;
		this.tile = tile;
		this.rotation = new DoubleVector4D(0, 0, 0, 0);
		this.center = new DoubleVector4D(0, 0, 0, 0);
		this.scale = new DoubleVector4D(1, 1, 1, 1);
		this.hints.putAll(hints);
		init();
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
	protected void abortRender() {
		renderer.abortRender();
	}
	
	/**
	 * 
	 */
	protected void joinRender() {
		renderer.joinRender();
	}
	
	/**
	 * 
	 */
	protected void startRender() {
		changed = false;
		progress = 0;
//		if (oldView != newView) {
//			viewChanged = true;
//			updateView(newView);
//		}
		// if (!isDynamic) {
		// setMode(FractalRenderer.MODE_REFRESH);
		// }
		renderer.startRender(false);//TODO
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererDelegate#didChanged(float, int[])
	 */
	@Override
	public void didChanged(float progress, int[] pixels) {
		if (backBuffer != null) {
			backBuffer.update(pixels);
		}
		this.progress = progress;
		this.changed = true;
	}

	/**
	 * @return
	 */
	public boolean isChanged() {
		boolean result = changed;
		changed = false;
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
		return size.getX();
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return size.getY();
	}

	/**
	 * @param constant
	 */
	public void setConstant(final DoubleVector2D constant) {
		renderer.setConstant(constant.getX(), constant.getY());
	}

	/**
	 * @param julia
	 */
	public void setJulia(final boolean julia) {
		renderer.setJulia(julia);
	}

	/**
	 * @param rendererFractal
	 */
	public void setRendererFractal(RendererFractal rendererFractal) {
		stopRender();
		renderer.setFractal(rendererFractal);
		if (rendererFractal != null) {
			startRender();
		}
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
	public final void swap() {
		synchronized (this) {
			final RenderBuffer tmpBuffer = backBuffer;
			backBuffer = buffer;
			buffer = tmpBuffer;
		}
	}

	/**
	 * @param gc
	 */
	public void drawImage(final RenderGraphicsContext gc) {
		synchronized (this) {
			if (buffer != null && affine != null) {
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(affine);
				gc.drawImage(buffer.getImage(), 0, 0);
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
			if (buffer != null && affine != null) {
				gc.saveTransform();
				// g.setClip(oldTile.getTileBorder().getX(), oldTile.getTileBorder().getY(), oldTile.getTileSize().getX(), oldTile.getTileSize().getY());
				// g.setClip(0, 0, oldTile.getTileSize().getX() + oldTile.getTileBorder().getX() + 2, oldTile.getTileSize().getY() + oldTile.getTileBorder().getY() + 2);
				gc.setAffine(affine);
				gc.drawImage(buffer.getImage(), x, y);
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
			if (buffer != null && affine != null) {
				gc.saveTransform();
				//TODO gc.setClip(x, y, w, h);
				gc.setAffine(affine);
				final double sx = w / (double) tile.getTileSize().getX();
				final double sy = h / (double) tile.getTileSize().getY();
				final int dw = (int) Math.rint(size.getX() * sx);
				final int dh = (int) Math.rint(size.getY() * sy);
				gc.drawImage(buffer.getImage(), x, y, dw, dh);
				//TODO gc.setClip(null);
				// g.dispose();
				gc.restoreTransform();
			}
		}
	}

//	/**
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View, com.nextbreakpoint.nextfractal.core.util.DoubleVector2D, int)
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
//	 * @see com.nextbreakpoint.nextfractal.mandelbrot.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View)
//	 */
//	@Override
//	public void setView(final View view) {
//		synchronized (this) {
//			newView = view;
//		}
//	}

//	private void updateView(final View view) {
//		rotation = view.getRotation().getZ();
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
		if (renderer != null) {
			renderer.dispose();
			renderer = null;
		}
		if (buffer != null) {
			buffer.dispose();
			buffer = null;
		}
		if (backBuffer != null) {
			backBuffer.dispose();
			backBuffer = null;
		}
	}

	/**
	 * 
	 */
	protected void init() {
//		int imageDim = (int) Math.sqrt(((tile.getImageSize().getX() + tile.getTileBorder().getX() * 2) * (tile.getImageSize().getX() + tile.getTileBorder().getX() * 2)) + ((tile.getImageSize().getY() + tile.getTileBorder().getY() * 2) * (tile.getImageSize().getY() + tile.getTileBorder().getY() * 2)));
		int tileDim = (int) Math.sqrt(((tile.getTileSize().getX() + tile.getTileBorder().getX() * 2) * (tile.getTileSize().getX() + tile.getTileBorder().getX() * 2)) + ((tile.getTileSize().getY() + tile.getTileBorder().getY() * 2) * (tile.getTileSize().getY() + tile.getTileBorder().getY() * 2)));
		size = new IntegerVector2D(tileDim, tileDim);
		buffer = renderFactory.createBuffer(size.getX(), size.getY());
		backBuffer = renderFactory.createBuffer(size.getX(), size.getY());
		affine = renderFactory.createAffine();
		renderer = createRenderer();
		renderer.setRendererDelegate(this);
		updateTransform();
	}

	/**
	 * @return
	 */
	protected Renderer createRenderer() {
		Integer type = hints.get(KEY_TYPE);
		if (type != null && type.equals(VALUE_REALTIME)) {
			return new XaosRenderer(threadFactory, size.getX(), size.getY());
		} else {
			return new Renderer(threadFactory, size.getX(), size.getY());
		}
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
//			t.rotate(-rotation, t1.r / dr, t1.i / di);
//			Point2D.Double p = new Point2D.Double(t0.r / dr, t0.i / di);
//			p = (Point2D.Double) t.transform(p, p);
//			p.setLocation(p.getX() * dr, p.getY() * di);
//			sx = dr * 0.5 * size.getX() / imageDim;
//			sy = di * 0.5 * size.getY() / imageDim;
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
		final int offsetX = (getWidth() - tile.getTileSize().getX() - tile.getTileBorder().getX() * 2) / 2;
		final int offsetY = (getHeight() - tile.getTileSize().getY() - tile.getTileBorder().getY() * 2) / 2;
		final int centerX = getWidth() / 2;
		final int centerY = getHeight() / 2;
		affine = renderFactory.createTranslateAffine(-offsetX, -offsetY);
		affine.append(renderFactory.createRotateAffine(rotation.getZ(), centerX, centerY));
	}
}
