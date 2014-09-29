/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.Colors;
import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector4D;
import com.nextbreakpoint.nextfractal.core.util.RenderWorker;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.incolouringFormula.IncolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.outcolouringFormula.OutcolouringFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public abstract class AbstractMandelbrotRenderer implements MandelbrotRenderer {
	protected static final Logger logger = Logger.getLogger(AbstractMandelbrotRenderer.class.getName());
	private Graphics2D newBuffer;
	private Graphics2D oldBuffer;
	private BufferedImage newImage;
	private BufferedImage oldImage;
	private IntegerVector2D bufferSize;
	private Tile newTile;
	private Tile oldTile;
	private int imageDim;
	private int tileDim;
	protected int newShiftValue;
	protected int oldShiftValue;
	protected double rotationValue;
	protected DoubleVector2D newConstant;
	protected DoubleVector2D oldConstant;
	protected int renderMode = MandelbrotRenderer.MODE_CALCULATE;
	protected int newImageMode = 0;
	protected int oldImageMode = 0;
	private boolean dynamic = false;
	private boolean dynamicZoom = false;
	private AffineTransform transform = new AffineTransform();
	protected RenderedArea area = new RenderedArea();
	protected RenderingStrategy renderingStrategy;
	protected MandelbrotRuntime runtime;
	private final Complex center = new Complex();
	private final Complex scale = new Complex();
	protected View newView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	protected View oldView = new View(new IntegerVector4D(0, 0, 0, 0), new DoubleVector4D(0, 0, 1, 0), new DoubleVector4D(0, 0, 0, 0));
	protected int percent = 100;
	protected MandelbrotFractalRuntimeElement fractalRuntime;
	protected int status = TwisterRenderer.STATUS_TERMINATED;
	private final MandelbrotWorker renderWorker;
	protected final ThreadFactory factory;
	private boolean viewChanged;
	private boolean invalidated;
	private final Object lock = new Object();

	/**
	 * @param threadPriority
	 */
	public AbstractMandelbrotRenderer(final int threadPriority) {
		factory = new DefaultThreadFactory("MandelbrotRendererWorker", true, threadPriority);
		renderWorker = new MandelbrotWorker(factory);
	}

	/**
	 * 
	 */
	public void start() {
		renderWorker.start();
	}

	/**
	 * 
	 */
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#asyncStop()
	 */
	public final void asyncStop() {
		clearTasks();
		abortTasks();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#asyncStart()
	 */
	public final void asyncStart() {
		startTasks();
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		area = null;
		transform = null;
		renderingStrategy = null;
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#dispose()
	 */
	public final void dispose() {
		stop();
		free();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#getRuntime()
	 */
	public MandelbrotRuntime getRuntime() {
		return runtime;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setRuntime(com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalRuntimeElement)
	 */
	public void setRuntime(final MandelbrotRuntime runtime) {
		synchronized (this) {
			this.runtime = runtime;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View, com.nextbreakpoint.nextfractal.core.util.DoubleVector2D, int)
	 */
	public void setView(final View view, final DoubleVector2D constant, final int imageMode) {
		synchronized (this) {
			newView = view;
			newConstant = constant;
			newImageMode = imageMode;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setView(com.nextbreakpoint.nextfractal.twister.util.View)
	 */
	public void setView(final View view) {
		synchronized (this) {
			newView = view;
		}
	}

	private void updateView(final View view) {
		rotationValue = view.getRotation().getZ();
		newShiftValue = (int) Math.rint(view.getRotation().getW());
		if (newShiftValue < 0) {
			newShiftValue = 0;
		}
		dynamic = (view.getStatus().getZ() == 1) || (view.getStatus().getW() == 1);
		dynamicZoom = dynamic;
		if (view.getStatus().getZ() == 2) {
			setMode(MandelbrotRenderer.MODE_CALCULATE);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#isDynamic()
	 */
	public boolean isDynamic() {
		final boolean value = dynamic;
		dynamic = false;
		return value;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#isViewChanged()
	 */
	public boolean isViewChanged() {
		final boolean value = viewChanged;
		viewChanged = false;
		return value;
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
		oldImage = new BufferedImage(bufferSize.getX(), bufferSize.getY(), Surface.DEFAULT_TYPE);
		newBuffer = newImage.createGraphics();
		oldBuffer = oldImage.createGraphics();
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setRenderingHints(java.util.Map)
	 */
	public void setRenderingHints(final Map<Object, Object> hints) {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setMode(int)
	 */
	public void setMode(final int renderMode) {
		this.renderMode |= renderMode;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#getMode()
	 */
	public int getMode() {
		return renderMode;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#startRenderer()
	 */
	public void startRenderer() {
		renderWorker.executeTask();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#abortRenderer()
	 */
	public void abortRenderer() {
		renderWorker.abortTasks();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#joinRenderer()
	 */
	public void joinRenderer() throws InterruptedException {
		renderWorker.waitTasks();
	}

	private void render() {
		synchronized (this) {
			fractalRuntime = runtime.getMandelbrotFractal();
			if (oldView != newView) {
				viewChanged = true;
				updateView(newView);
			}
			if (newShiftValue != oldShiftValue) {
				setMode(MandelbrotRenderer.MODE_REFRESH);
			}
			if (newImageMode != oldImageMode) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if ((newConstant != oldConstant) && (newImageMode != 0)) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if (fractalRuntime.isRenderingFormulaChanged()) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if (fractalRuntime.isTransformingFormulaChanged()) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if (fractalRuntime.isProcessingFormulaChanged()) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if (fractalRuntime.isOrbitTrapChanged()) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
			}
			if (fractalRuntime.isIncolouringFormulaChanged()) {
				setMode(MandelbrotRenderer.MODE_REFRESH);
			}
			if (fractalRuntime.isOutcolouringFormulaChanged()) {
				setMode(MandelbrotRenderer.MODE_REFRESH);
			}
			// if (!isDynamic) {
			// setMode(FractalRenderer.MODE_REFRESH);
			// }
			oldView = newView;
			oldConstant = newConstant;
			oldImageMode = newImageMode;
			oldShiftValue = newShiftValue;
			if (newTile != oldTile) {
				setMode(MandelbrotRenderer.MODE_CALCULATE);
				oldTile = newTile;
				free();
				init();
			}
		}
		if (oldImageMode == 0) {
			renderingStrategy = getMandelbrotRenderingStrategy();
			if ((fractalRuntime.getRenderingFormula() != null) && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null) && !fractalRuntime.getRenderingFormula().getFormulaRuntime().isMandelbrotModeAllowed()) {
				status = TwisterRenderer.STATUS_TERMINATED;
				newBuffer.clearRect(0, 0, newImage.getWidth(), newImage.getHeight());
				dynamicZoom = false;
				return;
			}
		}
		else {
			renderingStrategy = getJuliaRenderingStrategy();
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

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#getRenderingStatus()
	 */
	public int getRenderingStatus() {
		return status;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	public void setTile(final Tile tile) {
		synchronized (this) {
			invalidated = false;
			newTile = tile;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#getTile()
	 */
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setMandelbrotMode(boolean)
	 */
	public void setMandelbrotMode(final Integer mode) {
		synchronized (this) {
			newImageMode = mode;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#setConstant(com.nextbreakpoint.nextfractal.core.util.DoubleVector2D)
	 */
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
	protected void updateShift() {
	}

	/**
	 * 
	 */
	protected void updateRegion() {
		if ((fractalRuntime.getRenderingFormula() != null) && (fractalRuntime.getRenderingFormula().getFormulaRuntime() != null)) {
			final DoubleVector2D s = fractalRuntime.getRenderingFormula().getFormulaRuntime().getScale();
			final double x = oldView.getPosition().getX();
			final double y = oldView.getPosition().getY();
			final double z = oldView.getPosition().getZ();
			scale.r = s.getX() * z;
			scale.i = s.getY() * z;
			center.r = fractalRuntime.getRenderingFormula().getFormulaRuntime().getCenter().getX() + x;
			center.i = fractalRuntime.getRenderingFormula().getFormulaRuntime().getCenter().getY() + y;
			final double imageOffsetX = (imageDim - oldTile.getImageSize().getX()) / 2;
			final double imageOffsetY = (imageDim - oldTile.getImageSize().getY()) / 2;
			double sx = (scale.r * 0.5d * imageDim) / oldTile.getImageSize().getX();
			double sy = (scale.i * 0.5d * imageDim) / oldTile.getImageSize().getX();
			Complex p0 = new Complex(center.r - sx, center.i - sy);
			Complex p1 = new Complex(center.r + sx, center.i + sy);
			final Complex t0 = new Complex();
			final Complex t1 = new Complex();
			final double dr = p1.r - p0.r;
			final double di = p1.i - p0.i;
			t0.r = p0.r + dr * (imageOffsetX + oldTile.getTileOffset().getX() + oldTile.getTileSize().getX() / 2d) / imageDim;
			t0.i = p0.i + di * (imageOffsetY + oldTile.getTileOffset().getY() + oldTile.getTileSize().getY() / 2d) / imageDim;
			t1.r = p0.r + dr * 0.5;
			t1.i = p0.i + di * 0.5;
			final AffineTransform t = new AffineTransform();
			t.rotate(-rotationValue, t1.r / dr, t1.i / di);
			Point2D.Double p = new Point2D.Double(t0.r / dr, t0.i / di);
			p = (Point2D.Double) t.transform(p, p);
			p.setLocation(p.getX() * dr, p.getY() * di);
			sx = dr * 0.5 * bufferSize.getX() / imageDim;
			sy = di * 0.5 * bufferSize.getY() / imageDim;
			p0 = new Complex(p.getX() - sx, p.getY() - sy);
			p1 = new Complex(p.getX() + sx, p.getY() + sy);
			area.points[0] = p0;
			area.points[1] = p1;
		}
		else {
			final Complex p0 = new Complex(-0.5, +0.5);
			final Complex p1 = new Complex(-0.5, +0.5);
			area.points[0] = p0;
			area.points[1] = p1;
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
	 * @param p
	 * @return the color.
	 */
	protected int renderPoint(final RenderedPoint cp) {
		fractalRuntime.getRenderingFormula().getFormulaRuntime().renderPoint(cp);
		return renderColor(cp);
	}

	/**
	 * @param p
	 * @return the color.
	 */
	protected int renderColor(final RenderedPoint cp) {
		int newRGB = 0;
		int tmpRGB = 0;
		if (cp.time > 0) {
			if (fractalRuntime.getOutcolouringFormulaCount() == 1) {
				final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(0);
				if ((outcolouringFormula.getFormulaRuntime() != null) && outcolouringFormula.isEnabled()) {
					if (newShiftValue != 0) {
						newRGB = outcolouringFormula.getFormulaRuntime().renderColor(cp, newShiftValue);
					}
					else {
						newRGB = outcolouringFormula.getFormulaRuntime().renderColor(cp);
					}
				}
			}
			else {
				for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
					final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
					if ((outcolouringFormula.getFormulaRuntime() != null) && outcolouringFormula.isEnabled()) {
						if (newShiftValue != 0) {
							tmpRGB = outcolouringFormula.getFormulaRuntime().renderColor(cp, newShiftValue);
						}
						else {
							tmpRGB = outcolouringFormula.getFormulaRuntime().renderColor(cp);
						}
						newRGB = (newRGB != 0) ? Colors.mixColors(newRGB, tmpRGB, outcolouringFormula.getOpacity()) : tmpRGB;
					}
				}
			}
			return newRGB;
		}
		else {
			if (fractalRuntime.getIncolouringFormulaCount() == 1) {
				final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(0);
				if ((incolouringFormula.getFormulaRuntime() != null) && incolouringFormula.isEnabled()) {
					if (newShiftValue != 0) {
						newRGB = incolouringFormula.getFormulaRuntime().renderColor(cp, newShiftValue);
					}
					else {
						newRGB = incolouringFormula.getFormulaRuntime().renderColor(cp);
					}
				}
			}
			else {
				for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
					final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
					if ((incolouringFormula.getFormulaRuntime() != null) && incolouringFormula.isEnabled()) {
						if (newShiftValue != 0) {
							tmpRGB = incolouringFormula.getFormulaRuntime().renderColor(cp, newShiftValue);
						}
						else {
							tmpRGB = incolouringFormula.getFormulaRuntime().renderColor(cp);
						}
						newRGB = (newRGB != 0) ? Colors.mixColors(newRGB, tmpRGB, incolouringFormula.getOpacity()) : tmpRGB;
					}
				}
			}
			return newRGB;
		}
	}

	/**
	 * @param dynamic
	 */
	protected abstract void doRender(boolean dynamic);

	/**
	 * @return true if solidguess is supported.
	 */
	public boolean isSolidGuessSupported() {
		for (int i = 0; i < fractalRuntime.getOutcolouringFormulaCount(); i++) {
			final OutcolouringFormulaRuntimeElement outcolouringFormula = fractalRuntime.getOutcolouringFormula(i);
			if ((outcolouringFormula.getFormulaRuntime() != null) && !outcolouringFormula.getFormulaRuntime().isSolidGuessAllowed() && outcolouringFormula.isEnabled()) {
				return false;
			}
		}
		for (int i = 0; i < fractalRuntime.getIncolouringFormulaCount(); i++) {
			final IncolouringFormulaRuntimeElement incolouringFormula = fractalRuntime.getIncolouringFormula(i);
			if ((incolouringFormula.getFormulaRuntime() != null) && !incolouringFormula.getFormulaRuntime().isSolidGuessAllowed() && incolouringFormula.isEnabled()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return true if symetry is supported.
	 */
	public boolean isVerticalSymetrySupported() {
		return renderingStrategy.isVerticalSymetrySupported();
	}

	/**
	 * @return true if symetry is supported.
	 */
	public boolean isHorizontalSymetrySupported() {
		return renderingStrategy.isHorizontalSymetrySupported();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#drawImage(java.awt.Graphics2D)
	 */
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#drawImage(java.awt.Graphics2D, int, int)
	 */
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
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
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
		swapImages();
		return newBuffer;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.MandelbrotRenderer#isInterrupted()
	 */
	public boolean isInterrupted() {
		return Thread.currentThread().isInterrupted();
	}

	/**
	 * @return the strategy.
	 */
	protected abstract RenderingStrategy getMandelbrotRenderingStrategy();

	/**
	 * @return the strategy.
	 */
	protected abstract RenderingStrategy getJuliaRenderingStrategy();

	/**
	 * @author Andrea Medeghini
	 */
	protected interface RenderingStrategy {
		/**
		 * @param p
		 * @param pw
		 * @param px
		 * @return the time
		 */
		public int renderPoint(RenderedPoint p, Complex px, Complex pw);

		/**
		 * @return true if vertical symetry is supported.
		 */
		public boolean isVerticalSymetrySupported();

		/**
		 * @return true if horizontal symetry is supported.
		 */
		public boolean isHorizontalSymetrySupported();

		/**
		 * 
		 */
		public void updateParameters();
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
