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
package com.nextbreakpoint.nextfractal.contextfree.extensions.image;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeManager;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeRuntime;
import com.nextbreakpoint.nextfractal.contextfree.renderer.DefaultContextFreeRenderer;
import com.nextbreakpoint.nextfractal.contextfree.renderer.SimpleContextFreeRenderer;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeImageRuntime extends ImageExtensionRuntime<ContextFreeImageConfig> {
	// private static final Logger logger = Logger.getLogger(ContextFreeImageRuntime.class);
	private Map<Object, Object> hints = new HashMap<Object, Object>();
	private ContextFreeRuntime contextFreeRuntime;
	private RendererStrategy rendererStrategy;
	private RenderFactory renderFactory;
	private int lastStatus;
	private Tile tile;

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime#configReloaded()
	 */
	@Override
	public void configReloaded() {
		contextFreeRuntime = new ContextFreeRuntime(getConfig().getContextFreeConfig());
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
//			if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_OVERLAY || hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_PREVIEW) {
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
		if (contextFreeRuntime != null) {
			contextFreeRuntime.dispose();
			contextFreeRuntime = null;
		}
		super.dispose();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.config.RuntimeElement#isChanged()
	 */
	@Override
	public boolean isChanged() {
		boolean isChanged = (contextFreeRuntime != null) && (contextFreeRuntime.changeCount() > 0);
		final int status = (rendererStrategy != null) ? rendererStrategy.getRenderingStatus() : -1;
		isChanged |= (status == TwisterRenderer.STATUS_RENDERING) || (lastStatus == TwisterRenderer.STATUS_RENDERING);
		lastStatus = status;
		return super.isChanged() || isChanged;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#getRenderFactory()
	 */
	@Override
	public RenderFactory getRenderFactory() {
		return renderFactory;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#setRenderFactory(com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory)
	 */
	@Override
	public void setRenderFactory(RenderFactory renderFactory) {
		this.renderFactory = renderFactory;
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
		private ContextFreeManager manager;
		private int dynamicCount = 0;
		private boolean dirty = true;

		/**
		 * @param tile
		 */
		public DefaultRendererStrategy(final Tile tile) {
			if (hints.get(TwisterRenderingHints.KEY_QUALITY) == TwisterRenderingHints.QUALITY_REALTIME) {
				if (hints.get(TwisterRenderingHints.KEY_MEMORY) == TwisterRenderingHints.MEMORY_LOW) {
					manager = new ContextFreeManager(new DefaultContextFreeRenderer(Thread.MIN_PRIORITY + 2));
				}
				else {
					manager = new ContextFreeManager(new DefaultContextFreeRenderer(Thread.MIN_PRIORITY + 2));
				}
			}
			else {
				manager = new ContextFreeManager(new SimpleContextFreeRenderer(Thread.MIN_PRIORITY + 1));
			}
			manager.setRenderFactory(renderFactory);
			manager.setRenderingHints(hints);
			manager.setRuntime(contextFreeRuntime);
			loadConfig();
			manager.setTile(tile);
			manager.start();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#startRenderer()
		 */
		@Override
		public void startRenderer() {
			manager.startRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#abortRenderer()
		 */
		@Override
		public void abortRenderer() {
			manager.abortRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#joinRenderer()
		 */
		@Override
		public void joinRenderer() throws InterruptedException {
			manager.joinRenderer();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#getRenderingStatus()
		 */
		@Override
		public int getRenderingStatus() {
			return manager.getRenderingStatus();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(GraphicsContext gc) {
			if (tile != null) {
				manager.drawImage(gc);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		@Override
		public void drawImage(GraphicsContext gc, int x, int y) {
			if (tile != null) {
				manager.drawImage(gc, x, y);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(GraphicsContext gc, int x, int y, int w, int h) {
			if (tile != null) {
				manager.drawImage(gc, x, y, w, h);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D)
		 */
		@Override
		public void drawImage(final Graphics2D g2d) {
			if (tile != null) {
				manager.drawImage(g2d);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y) {
			if (tile != null) {
				manager.drawImage(g2d, x, y);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
			if (tile != null) {
				manager.drawImage(g2d, x, y, w, h);
				if (dirty) {
					dirty = false;
					try {
						manager.joinRenderer();
						manager.startRenderer();
					}
					catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#prepareImage(boolean)
		 */
		@Override
		public void prepareImage(final boolean isDynamicRequired) {
			if (isDynamicRequired) {
				boolean isChanged = contextFreeRuntime.isChanged();
				isChanged |= contextFreeRuntime.isViewChanged();
				if (isChanged) {
					dynamicCount = 0;
					dirty = true;
				}
				if (dynamicCount >= 1) {
					dirty = true;
				}
				if (dirty) {
					manager.abortRenderer();
					loadConfig();
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
			manager.setView(getConfig().getContextFreeConfig().getView());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#dispose()
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
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#isDynamic()
		 */
		@Override
		public boolean isDynamic() {
			return dynamicCount > 0;
		}
	}

	private class OverlayRendererStrategy implements RendererStrategy {
		/**
		 * @param tile
		 */
		public OverlayRendererStrategy(final Tile tile) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#startRenderer()
		 */
		@Override
		public void startRenderer() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#abortRenderer()
		 */
		@Override
		public void abortRenderer() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#joinRenderer()
		 */
		@Override
		public void joinRenderer() throws InterruptedException {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#getRenderingStatus()
		 */
		@Override
		public int getRenderingStatus() {
			return -1;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(GraphicsContext gc) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
		 */
		@Override
		public void drawImage(GraphicsContext gc, int x, int y) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.contextfree.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(javafx.scene.canvas.GraphicsContext)
		 */
		@Override
		public void drawImage(GraphicsContext gc, int x, int y, int w, int h) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D)
		 */
		@Override
		public void drawImage(final Graphics2D g2d) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#drawImage(java.awt.Graphics2D, int, int, int, int)
		 */
		@Override
		public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#prepareImage(boolean)
		 */
		@Override
		public void prepareImage(final boolean isDynamicRequired) {
			contextFreeRuntime.isChanged();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#dispose()
		 */
		@Override
		public void dispose() {
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.ContextFreeImageRuntime.extensions.image.ContextFreeImageRuntime.RendererStrategy#isDynamic()
		 */
		@Override
		public boolean isDynamic() {
			return false;
		}
	}
}
