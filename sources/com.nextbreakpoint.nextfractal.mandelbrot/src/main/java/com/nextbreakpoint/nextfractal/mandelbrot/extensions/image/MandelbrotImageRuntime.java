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
package com.nextbreakpoint.nextfractal.mandelbrot.extensions.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.core.math.Complex;
import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Rectangle;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotManager;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRuntime;
import com.nextbreakpoint.nextfractal.mandelbrot.fractal.MandelbrotFractalRuntimeElement;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.BestXaosMandelbrotRenderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.FastXaosMandelbrotRenderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RenderedPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.SimpleMandelbrotRenderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderingFormula.RenderingFormulaRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class MandelbrotImageRuntime extends ImageExtensionRuntime<MandelbrotImageConfig> {
	// private static final Logger logger = Logger.getLogger(MandelbrotImageRuntime.class);
	private Map<Object, Object> hints = new HashMap<Object, Object>();
	private MandelbrotRuntime mandelbrotRuntime;
	private RendererStrategy rendererStrategy;
	private int lastStatus;
	private Tile tile;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		mandelbrotRuntime = new MandelbrotRuntime(getConfig().getMandelbrotConfig());
		if (rendererStrategy != null) {
			rendererStrategy.dispose();
			rendererStrategy = null;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#startRenderer()
	 */
	@Override
	public void startRenderer() {
		if (rendererStrategy != null) {
			rendererStrategy.startRenderer();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#abortRenderer()
	 */
	@Override
	public void abortRenderer() {
		if (rendererStrategy != null) {
			rendererStrategy.abortRenderer();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#joinRenderer()
	 */
	@Override
	public void joinRenderer() throws InterruptedException {
		if (rendererStrategy != null) {
			rendererStrategy.joinRenderer();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#getRenderingStatus()
	 */
	@Override
	public int getRenderingStatus() {
		if (rendererStrategy != null) {
			return rendererStrategy.getRenderingStatus();
		}
		return 0;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		if (rendererStrategy != null) {
			return rendererStrategy.isDynamic();
		}
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#prepareImage(boolean)
	 */
	@Override
	public void prepareImage(final boolean isDynamicRequired) {
		if (rendererStrategy != null) {
			rendererStrategy.prepareImage(isDynamicRequired);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawImage(final GraphicsContext gc) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(gc);
		}
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(gc, x, y);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y, final int w, final int h) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(gc, x, y, w, h);
		}
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D)
	 */
	@Override
	public void drawImage(final Graphics2D g2d) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(g2d);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(g2d, x, y);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		if (rendererStrategy != null) {
			rendererStrategy.drawImage(g2d, x, y, w, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#getImageSize()
	 */
	@Override
	public IntegerVector2D getImageSize() {
		return tile.getImageSize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
		if (this.tile != tile) {
			this.tile = tile;
			if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_OVERLAY) {
				rendererStrategy = new OverlayRendererStrategy(tile);
			}
			else {
				rendererStrategy = new DefaultRendererStrategy(tile);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(final Map<Object, Object> hints) {
		this.hints = hints;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime#dispose()
	 */
	@Override
	public void dispose() {
		if (rendererStrategy != null) {
			rendererStrategy.dispose();
			rendererStrategy = null;
		}
		if (mandelbrotRuntime != null) {
			mandelbrotRuntime.dispose();
			mandelbrotRuntime = null;
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean isChanged = (mandelbrotRuntime != null) && ((mandelbrotRuntime.changeCount() > 0) || mandelbrotRuntime.getViewChanged());
		final int status = (rendererStrategy != null) ? rendererStrategy.getRenderingStatus() : -1;
		isChanged |= (status == TwisterRenderer.STATUS_RENDERING) || (lastStatus == TwisterRenderer.STATUS_RENDERING);
		lastStatus = status;
		return super.isChanged() || isChanged;
	}

	private interface RendererStrategy {
		/**
		 * 
		 */
		public void dispose();

		/**
		 * @param isDynamicRequired
		 */
		public void prepareImage(boolean isDynamicRequired);
		
		/**
		 * @param gc
		 */
		public void drawImage(GraphicsContext gc);

		/**
		 * @param gc
		 * @param x
		 * @param y
		 */
		public void drawImage(GraphicsContext gc, int x, int y);

		/**
		 * @param gc
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public void drawImage(GraphicsContext gc, int x, int y, int w, int h);

		/**
		 * @param g2d
		 */
		public void drawImage(final Graphics2D g2d);

		/**
		 * @param g2d
		 * @param x
		 * @param y
		 */
		public void drawImage(Graphics2D g2d, int x, int y);

		/**
		 * @param g2d
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public void drawImage(Graphics2D g2d, int x, int y, int w, int h);

		/**
		 * @return
		 */
		public int getRenderingStatus();

		/**
		 * 
		 */
		public void startRenderer();

		/**
		 * 
		 */
		public void abortRenderer();

		/**
		 * @throws InterruptedException
		 */
		public void joinRenderer() throws InterruptedException;

		/**
		 * @return
		 */
		public boolean isDynamic();
	}

	private class DefaultRendererStrategy implements RendererStrategy {
		private MandelbrotManager manager;
		private int dynamicCount = 0;
		public boolean dirty;

		/**
		 * @param tile
		 */
		public DefaultRendererStrategy(final Tile tile) {
			if (hints.get(TwisterRenderingHints.KEY_QUALITY) == TwisterRenderingHints.QUALITY_REALTIME) {
				if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_PREVIEW) {
					if (hints.get(TwisterRenderingHints.KEY_MEMORY) == TwisterRenderingHints.MEMORY_LOW) {
						manager = new MandelbrotManager(new FastXaosMandelbrotRenderer(Thread.MIN_PRIORITY));
					}
					else {
						manager = new MandelbrotManager(new BestXaosMandelbrotRenderer(Thread.MIN_PRIORITY));
					}
				}
				else {
					if (hints.get(TwisterRenderingHints.KEY_MEMORY) == TwisterRenderingHints.MEMORY_LOW) {
						manager = new MandelbrotManager(new FastXaosMandelbrotRenderer(Thread.MIN_PRIORITY + 2));
					}
					else {
						manager = new MandelbrotManager(new BestXaosMandelbrotRenderer(Thread.MIN_PRIORITY + 2));
					}
				}
			}
			else {
				manager = new MandelbrotManager(new SimpleMandelbrotRenderer(Thread.MIN_PRIORITY + 1));
			}
//			manager = new MandelbrotManager(new SimpleMandelbrotRenderer(Thread.MIN_PRIORITY + 1));
			manager.setRenderingHints(hints);
			manager.setRuntime(mandelbrotRuntime);
			loadConfig();
			manager.setTile(tile);
			manager.start();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#startRenderer()
		 */
		@Override
		public void startRenderer() {
			manager.startRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#abortRenderer()
		 */
		@Override
		public void abortRenderer() {
			manager.abortRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#joinRenderer()
		 */
		@Override
		public void joinRenderer() throws InterruptedException {
			manager.joinRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#getRenderingStatus()
		 */
		@Override
		public int getRenderingStatus() {
			return manager.getRenderingStatus();
		}
		
		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(final GraphicsContext gc) {
			if (tile != null) {
				manager.drawImage(gc);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
		 */
		@Override
		public void drawImage(final GraphicsContext gc, final int x, final int y) {
			if (tile != null) {
				manager.drawImage(gc, x, y);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
		 */
		@Override
		public void drawImage(final GraphicsContext gc, final int x, final int y, final int w, final int h) {
			if (tile != null) {
				manager.drawImage(gc, x, y, w, h);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D)
		 */
		@Override
		public void drawImage(final Graphics2D g2d) {
			if (tile != null) {
				manager.drawImage(g2d);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y) {
			if (tile != null) {
				manager.drawImage(g2d, x, y);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
			if (tile != null) {
				manager.drawImage(g2d, x, y, w, h);
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#prepareImage(boolean)
		 */
		@Override
		public void prepareImage(final boolean isDynamicRequired) {
			if (isDynamicRequired) {
				boolean isChanged = mandelbrotRuntime.isChanged();
				isChanged |= mandelbrotRuntime.isViewChanged();
				if (isChanged || (dynamicCount >= 1)) {
					dynamicCount = 0;
					loadConfig();
					dirty = true;
					manager.asyncStop();
				}
			}
			else {
				loadConfig();
			}
			if (manager.isDynamic()) {
				dynamicCount += 1;
			}
			else {
				dynamicCount = 0;
			}
		}

		private void loadConfig() {
			manager.setView(getConfig().getMandelbrotConfig().getView(), getConfig().getMandelbrotConfig().getConstant(), getConfig().getMandelbrotConfig().getImageMode());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#dispose()
		 */
		@Override
		public void dispose() {
			if (manager != null) {
				manager.stop();
				manager.dispose();
				manager = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#isDynamic()
		 */
		@Override
		public boolean isDynamic() {
			return dynamicCount > 0;
		}
	}

	private class OverlayRendererStrategy implements RendererStrategy {
		private MandelbrotManager manager;
		private boolean suspended;
		private boolean dirty;

		/**
		 * @param tile
		 */
		public OverlayRendererStrategy(final Tile tile) {
			if (hints.get(TwisterRenderingHints.KEY_QUALITY) == TwisterRenderingHints.QUALITY_REALTIME) {
				if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_PREVIEW) {
					if (hints.get(TwisterRenderingHints.KEY_MEMORY) == TwisterRenderingHints.MEMORY_LOW) {
						manager = new MandelbrotManager(new FastXaosMandelbrotRenderer(Thread.MIN_PRIORITY));
					}
					else {
						manager = new MandelbrotManager(new BestXaosMandelbrotRenderer(Thread.MIN_PRIORITY));
					}
				}
				else {
					if (hints.get(TwisterRenderingHints.KEY_MEMORY) == TwisterRenderingHints.MEMORY_LOW) {
						manager = new MandelbrotManager(new FastXaosMandelbrotRenderer(Thread.MIN_PRIORITY + 2));
					}
					else {
						manager = new MandelbrotManager(new BestXaosMandelbrotRenderer(Thread.MIN_PRIORITY + 2));
					}
				}
			}
			else {
				manager = new MandelbrotManager(new SimpleMandelbrotRenderer(Thread.MIN_PRIORITY + 1));
			}
//			manager = new MandelbrotManager(new SimpleMandelbrotRenderer(Thread.MIN_PRIORITY + 1));
			manager.setRenderingHints(hints);
			manager.setRuntime(mandelbrotRuntime);
			loadConfig();
			final Rectangle previewArea = getConfig().getMandelbrotConfig().getPreviewArea();
			final int px = (int) Math.rint(tile.getImageSize().getX() * previewArea.getX());
			final int py = (int) Math.rint(tile.getImageSize().getY() * previewArea.getY());
			final int pw = (int) Math.rint(tile.getImageSize().getX() * previewArea.getW());
			final int ph = (int) Math.rint(tile.getImageSize().getY() * previewArea.getH());
			final int tx = (int) Math.rint(tile.getTileOffset().getX());
			final int ty = (int) Math.rint(tile.getTileOffset().getY());
			final int tw = (int) Math.rint(tile.getTileSize().getX());
			final int th = (int) Math.rint(tile.getTileSize().getY());
			// final int iw = (int) Math.rint(tile.getImageSize().getX());
			// final int ih = (int) Math.rint(tile.getImageSize().getY());
			suspended = true;
			if ((px < tx + tw) && (px + pw > tx)) {
				if ((py < ty + th) && (py + ph > ty)) {
					int ox = 0;
					int oy = 0;
					int ow = pw;
					int oh = ph;
					if (tx > px) {
						ox = tx - px;
					}
					if (ty > py) {
						oy = ty - py;
					}
					if (ox + ow > pw) {
						ow = pw - ox;
					}
					if (oy + oh > ph) {
						oh = ph - oy;
					}
					if ((ow > 0) && (oh > 0) && (ox >= 0) && (oy >= 0)) {
						manager.setTile(new Tile(new IntegerVector2D(pw, ph), new IntegerVector2D(ow, oh), new IntegerVector2D(ox, oy), new IntegerVector2D(0, 0)));
						suspended = false;
					}
				}
			}
			manager.start();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#startRenderer()
		 */
		@Override
		public void startRenderer() {
			if (!suspended) {
				manager.startRenderer();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#abortRenderer()
		 */
		@Override
		public void abortRenderer() {
			if (!suspended) {
				manager.abortRenderer();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#joinRenderer()
		 */
		@Override
		public void joinRenderer() throws InterruptedException {
			if (!suspended) {
				manager.joinRenderer();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#getRenderingStatus()
		 */
		@Override
		public int getRenderingStatus() {
			return manager.getRenderingStatus();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(GraphicsContext gc) {
//			if (tile != null) {
//				gc.setGlobalAlpha(0.8f);
//				if (getConfig().getMandelbrotConfig().getShowPreview() && !suspended) {
//					final Rectangle previewArea = getConfig().getMandelbrotConfig().getPreviewArea();
//					final int px = (int) Math.rint(tile.getImageSize().getX() * previewArea.getX());
//					final int py = (int) Math.rint(tile.getImageSize().getY() * previewArea.getY());
//					final int pw = (int) Math.rint(tile.getImageSize().getX() * previewArea.getW());
//					final int ph = (int) Math.rint(tile.getImageSize().getY() * previewArea.getH());
//					final int tx = (int) Math.rint(tile.getTileOffset().getX());
//					final int ty = (int) Math.rint(tile.getTileOffset().getY());
//					final int tw = (int) Math.rint(tile.getTileSize().getX());
//					final int th = (int) Math.rint(tile.getTileSize().getY());
//					if ((px < tx + tw) && (px + pw > tx)) {
//						if ((py < ty + th) && (py + ph > ty)) {
//							int ox = 0;
//							int oy = 0;
//							int ow = pw;
//							int oh = ph;
//							if (tx > px) {
//								ox = tx - px;
//							}
//							if (ty > py) {
//								oy = ty - py;
//							}
//							if (ox + ow > pw) {
//								ow = pw - ox;
//							}
//							if (oy + oh > ph) {
//								oh = ph - oy;
//							}
//							if ((ow > 0) && (oh > 0) && (ox >= 0) && (oy >= 0)) {
//								gc.setFill(Color.RED);
//								gc.setClip(tx, ty, tw, th);
//								manager.drawImage(gc, px - tx + ox, py - ty + oy, ow, oh);
//								gc.fillRect(px - tx + ox - 1, py - ty + oy - 1, ow, oh);
//								final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
//								if (fractal != null) {
//									final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
//									if (formula != null) {
//										if (formula.getFormulaRuntime() != null) {
//											final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
//											final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
//											final DoubleVector2D constant = getConfig().getMandelbrotConfig().getConstant();
//											final View view = getConfig().getMandelbrotConfig().getView();
//											final double x = view.getPosition().getX();
//											final double y = view.getPosition().getY();
//											final double z = view.getPosition().getZ();
//											final double a = view.getRotation().getZ();
//											final double qx = (constant.getX() - center.getX() - x) / (z * scale.getX());
//											final double qy = (constant.getY() - center.getY() - y) / (z * scale.getY());
//											final double cx = (Math.cos(-a) * qx) + (Math.sin(-a) * qy);
//											final double cy = (Math.cos(-a) * qy) - (Math.sin(-a) * qx);
//											final int dx = (int) Math.rint(cx * tile.getImageSize().getX());
//											final int dy = (int) Math.rint(cy * tile.getImageSize().getX());
//											gc.fillRect(dx + tile.getImageSize().getX() / 2 - tile.getTileOffset().getX() - 2, dy + tile.getImageSize().getY() / 2 - tile.getTileOffset().getY() - 2, 4, 4);
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				if (getConfig().getMandelbrotConfig().getShowOrbit()) {
//					gc.setFill(Color.YELLOW);
//					final View view = getConfig().getMandelbrotConfig().getView();
//					final double x = view.getPosition().getX();
//					final double y = view.getPosition().getY();
//					final double z = view.getPosition().getZ();
//					final double a = view.getRotation().getZ();
//					final int tw = tile.getImageSize().getX();
//					final int th = tile.getImageSize().getY();
//					final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
//					if (fractal != null) {
//						final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
//						if (formula != null) {
//							if (formula.getFormulaRuntime() != null) {
//								formula.getFormulaRuntime().prepareForRendering(fractal.getProcessingFormula().getFormulaRuntime(), fractal.getOrbitTrap().getOrbitTrapRuntime());
//								if (fractal.getOrbitTrap().getOrbitTrapRuntime() != null) {
//									fractal.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractal.getOrbitTrap().getCenter());
//								}
//								final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
//								final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
//								final RenderedPoint rp = new RenderedPoint();
//								rp.xr = formula.getFormulaRuntime().getInitialPoint().r;
//								rp.xi = formula.getFormulaRuntime().getInitialPoint().i;
//								rp.wr = getConfig().getMandelbrotConfig().getConstantElement().getValue().getX();
//								rp.wi = getConfig().getMandelbrotConfig().getConstantElement().getValue().getY();
//								final List<Complex> orbit = formula.getFormulaRuntime().renderOrbit(rp);
//								gc.setClip(0, 0, tw, th);
//								if (orbit.size() > 1) {
//									Complex pa = orbit.get(0);
//									double zx = scale.getX() * z;
//									double zy = scale.getY() * z;
//									double tx = ((pa.r - center.getX() - x) / zx) * tw;
//									double ty = ((pa.i - center.getY() - y) / zy) * tw;
//									double ca = Math.cos(a);
//									double sa = Math.sin(a);
//									int px0 = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
//									int py0 = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
//									for (int i = 1; i < orbit.size(); i++) {
//										pa = orbit.get(i);
//										tx = ((pa.r - center.getX() - x) / zx) * tw;
//										ty = ((pa.i - center.getY() - y) / zy) * tw;
//										int px1 = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
//										int py1 = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
//										g2d.drawLine(px0, py0, px1, py1);
//										px0 = px1;
//										py0 = py1;
//									}
//								}
//								gc.setClip(null);
//							}
//						}
//					}
//				}
//				if (getConfig().getMandelbrotConfig().getShowOrbitTrap()) {
//					gc.setFill(Color.RED);
//					final View view = getConfig().getMandelbrotConfig().getView();
//					final double x = view.getPosition().getX();
//					final double y = view.getPosition().getY();
//					final double z = view.getPosition().getZ();
//					final double a = view.getRotation().getZ();
//					final int tw = tile.getImageSize().getX();
//					final int th = tile.getImageSize().getY();
//					final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
//					if (fractal != null) {
//						final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
//						if (formula != null) {
//							if (formula.getFormulaRuntime() != null) {
//								formula.getFormulaRuntime().prepareForRendering(fractal.getProcessingFormula().getFormulaRuntime(), fractal.getOrbitTrap().getOrbitTrapRuntime());
//								if (fractal.getOrbitTrap().getOrbitTrapRuntime() != null) {
//									fractal.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractal.getOrbitTrap().getCenter());
//									final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
//									final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
//									double zx = scale.getX() * z;
//									double zy = scale.getY() * z;
//									Shape orbit = fractal.getOrbitTrap().getOrbitTrapRuntime().renderOrbitTrap(tw / zx, tw / zy, -a);
//									if (orbit != null) {
//										final DoubleVector2D c = fractal.getOrbitTrap().getCenter();
//										double tx = ((+c.getX() - center.getX() - x) * tw) / zx;
//										double ty = ((-c.getY() - center.getY() - y) * tw) / zy;
//										double ca = Math.cos(a);
//										double sa = Math.sin(a);
//										double px = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
//										double py = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
//										Affine tmpTransform = gc.getTransform();
//										Affine transform = new Affine();
//										transform.append(Affine.translate(px, py));
//										gc.setClip(0, 0, tw, th);
//										gc.setTransform(transform);
//										//TODO
//										gc.fill();
//										gc.setClip(null);
//										gc.setTransform(tmpTransform);
//									}
//								}
//							}
//						}
//					}
//				}
//				if (dirty) {
//					dirty = false;
//					manager.asyncStart();
//					// try {
//					// fractalManager.joinRenderer();
//					// fractalManager.startRenderer();
//					// }
//					// catch (final InterruptedException e) {
//					// Thread.currentThread().interrupt();
//					// }
//				}
//			}
		}

		/**
		 * @param gc
		 * @param x
		 * @param y
		 */
		@Override
		public void drawImage(final GraphicsContext gc, final int x, final int y) {
			this.drawImage(gc);
		}

		/**
		 * @param gc
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		@Override
		public void drawImage(final GraphicsContext gc, final int x, final int y, final int w, final int h) {
			this.drawImage(gc);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D)
		 */
		@Override
		public void drawImage(final Graphics2D g2d) {
			if (tile != null) {
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				if (getConfig().getMandelbrotConfig().getShowPreview() && !suspended) {
					final Rectangle previewArea = getConfig().getMandelbrotConfig().getPreviewArea();
					final int px = (int) Math.rint(tile.getImageSize().getX() * previewArea.getX());
					final int py = (int) Math.rint(tile.getImageSize().getY() * previewArea.getY());
					final int pw = (int) Math.rint(tile.getImageSize().getX() * previewArea.getW());
					final int ph = (int) Math.rint(tile.getImageSize().getY() * previewArea.getH());
					final int tx = (int) Math.rint(tile.getTileOffset().getX());
					final int ty = (int) Math.rint(tile.getTileOffset().getY());
					final int tw = (int) Math.rint(tile.getTileSize().getX());
					final int th = (int) Math.rint(tile.getTileSize().getY());
					if ((px < tx + tw) && (px + pw > tx)) {
						if ((py < ty + th) && (py + ph > ty)) {
							int ox = 0;
							int oy = 0;
							int ow = pw;
							int oh = ph;
							if (tx > px) {
								ox = tx - px;
							}
							if (ty > py) {
								oy = ty - py;
							}
							if (ox + ow > pw) {
								ow = pw - ox;
							}
							if (oy + oh > ph) {
								oh = ph - oy;
							}
							if ((ow > 0) && (oh > 0) && (ox >= 0) && (oy >= 0)) {
								g2d.setColor(java.awt.Color.RED);
								g2d.setClip(tx, ty, tw, th);
								manager.drawImage(g2d, px - tx + ox, py - ty + oy, ow, oh);
								g2d.drawRect(px - tx + ox - 1, py - ty + oy - 1, ow, oh);
								final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
								if (fractal != null) {
									final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
									if (formula != null) {
										if (formula.getFormulaRuntime() != null) {
											final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
											final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
											final DoubleVector2D constant = getConfig().getMandelbrotConfig().getConstant();
											final View view = getConfig().getMandelbrotConfig().getView();
											final double x = view.getPosition().getX();
											final double y = view.getPosition().getY();
											final double z = view.getPosition().getZ();
											final double a = view.getRotation().getZ();
											final double qx = (constant.getX() - center.getX() - x) / (z * scale.getX());
											final double qy = (constant.getY() - center.getY() - y) / (z * scale.getY());
											final double cx = (Math.cos(-a) * qx) + (Math.sin(-a) * qy);
											final double cy = (Math.cos(-a) * qy) - (Math.sin(-a) * qx);
											final int dx = (int) Math.rint(cx * tile.getImageSize().getX());
											final int dy = (int) Math.rint(cy * tile.getImageSize().getX());
											g2d.drawRect(dx + tile.getImageSize().getX() / 2 - tile.getTileOffset().getX() - 2, dy + tile.getImageSize().getY() / 2 - tile.getTileOffset().getY() - 2, 4, 4);
										}
									}
								}
							}
						}
					}
				}
				if (getConfig().getMandelbrotConfig().getShowOrbit()) {
					g2d.setColor(java.awt.Color.YELLOW);
					final View view = getConfig().getMandelbrotConfig().getView();
					final double x = view.getPosition().getX();
					final double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final int tw = tile.getImageSize().getX();
					final int th = tile.getImageSize().getY();
					final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
					if (fractal != null) {
						final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
						if (formula != null) {
							if (formula.getFormulaRuntime() != null) {
								formula.getFormulaRuntime().prepareForRendering(fractal.getProcessingFormula().getFormulaRuntime(), fractal.getOrbitTrap().getOrbitTrapRuntime());
								if (fractal.getOrbitTrap().getOrbitTrapRuntime() != null) {
									fractal.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractal.getOrbitTrap().getCenter());
								}
								final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
								final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
								final RenderedPoint rp = new RenderedPoint();
								rp.xr = formula.getFormulaRuntime().getInitialPoint().r;
								rp.xi = formula.getFormulaRuntime().getInitialPoint().i;
								rp.wr = getConfig().getMandelbrotConfig().getConstantElement().getValue().getX();
								rp.wi = getConfig().getMandelbrotConfig().getConstantElement().getValue().getY();
								final List<Complex> orbit = formula.getFormulaRuntime().renderOrbit(rp);
								g2d.setClip(0, 0, tw, th);
								if (orbit.size() > 1) {
									Complex pa = orbit.get(0);
									double zx = scale.getX() * z;
									double zy = scale.getY() * z;
									double tx = ((pa.r - center.getX() - x) / zx) * tw;
									double ty = ((pa.i - center.getY() - y) / zy) * tw;
									double ca = Math.cos(a);
									double sa = Math.sin(a);
									int px0 = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
									int py0 = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
									for (int i = 1; i < orbit.size(); i++) {
										pa = orbit.get(i);
										tx = ((pa.r - center.getX() - x) / zx) * tw;
										ty = ((pa.i - center.getY() - y) / zy) * tw;
										int px1 = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
										int py1 = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
										g2d.drawLine(px0, py0, px1, py1);
										px0 = px1;
										py0 = py1;
									}
								}
								g2d.setClip(null);
							}
						}
					}
				}
				if (getConfig().getMandelbrotConfig().getShowOrbitTrap()) {
					g2d.setColor(java.awt.Color.RED);
					final View view = getConfig().getMandelbrotConfig().getView();
					final double x = view.getPosition().getX();
					final double y = view.getPosition().getY();
					final double z = view.getPosition().getZ();
					final double a = view.getRotation().getZ();
					final int tw = tile.getImageSize().getX();
					final int th = tile.getImageSize().getY();
					final MandelbrotFractalRuntimeElement fractal = mandelbrotRuntime.getMandelbrotFractal();
					if (fractal != null) {
						final RenderingFormulaRuntimeElement formula = fractal.getRenderingFormula();
						if (formula != null) {
							if (formula.getFormulaRuntime() != null) {
								formula.getFormulaRuntime().prepareForRendering(fractal.getProcessingFormula().getFormulaRuntime(), fractal.getOrbitTrap().getOrbitTrapRuntime());
								if (fractal.getOrbitTrap().getOrbitTrapRuntime() != null) {
									fractal.getOrbitTrap().getOrbitTrapRuntime().prepareForProcessing(fractal.getOrbitTrap().getCenter());
									final DoubleVector2D center = formula.getFormulaRuntime().getCenter();
									final DoubleVector2D scale = formula.getFormulaRuntime().getScale();
									double zx = scale.getX() * z;
									double zy = scale.getY() * z;
									Shape orbit = fractal.getOrbitTrap().getOrbitTrapRuntime().renderOrbitTrap(tw / zx, tw / zy, -a);
									if (orbit != null) {
										final DoubleVector2D c = fractal.getOrbitTrap().getCenter();
										double tx = ((+c.getX() - center.getX() - x) * tw) / zx;
										double ty = ((-c.getY() - center.getY() - y) * tw) / zy;
										double ca = Math.cos(a);
										double sa = Math.sin(a);
										double px = (int) Math.rint((ca * tx) - (sa * ty)) + tw / 2 - tile.getTileOffset().getX();
										double py = (int) Math.rint((ca * ty) + (sa * tx)) + th / 2 - tile.getTileOffset().getY();
										AffineTransform tmpTransform = g2d.getTransform();
										AffineTransform transform = AffineTransform.getTranslateInstance(px, py);
										g2d.setClip(0, 0, tw, th);
										g2d.setTransform(transform);
										g2d.draw(orbit);
										g2d.setClip(null);
										g2d.setTransform(tmpTransform);
									}
								}
							}
						}
					}
				}
				if (dirty) {
					dirty = false;
					manager.asyncStart();
					// try {
					// fractalManager.joinRenderer();
					// fractalManager.startRenderer();
					// }
					// catch (final InterruptedException e) {
					// Thread.currentThread().interrupt();
					// }
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y) {
			this.drawImage(g2d);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
			this.drawImage(g2d);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#prepareImage(boolean)
		 */
		@Override
		public void prepareImage(final boolean isDynamicRequired) {
			if (isDynamicRequired) {
				if (!suspended) {
					boolean isChanged = mandelbrotRuntime.isChanged();
					isChanged |= mandelbrotRuntime.isViewChanged();
					if (isChanged) {
						loadConfig();
						dirty = true;
						manager.asyncStop();
					}
				}
			}
			else {
				loadConfig();
			}
		}

		private void loadConfig() {
			if (getConfig().getMandelbrotConfig().getImageMode() == 0) {
				manager.setMandelbrotMode(1);
			}
			else {
				manager.setMandelbrotMode(0);
			}
			manager.setConstant(getConfig().getMandelbrotConfig().getConstant());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#dispose()
		 */
		@Override
		public void dispose() {
			if (manager != null) {
				manager.stop();
				manager.dispose();
				manager = null;
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageRuntime.RendererStrategy#isDynamic()
		 */
		@Override
		public boolean isDynamic() {
			return false;
		}
	}
}
