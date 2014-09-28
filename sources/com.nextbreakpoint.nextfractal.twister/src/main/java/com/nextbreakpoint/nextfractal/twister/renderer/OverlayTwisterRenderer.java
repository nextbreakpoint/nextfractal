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
package com.nextbreakpoint.nextfractal.twister.renderer;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.frame.FrameRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public class OverlayTwisterRenderer implements TwisterRenderer {
	private Map<Object, Object> hints = new HashMap<Object, Object>();
	private TwisterRuntime runtime;
	private Tile tile;

	/**
	 * @param runtime
	 */
	public OverlayTwisterRenderer(final TwisterRuntime runtime) {
		this.runtime = runtime;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D)
	 */
	public void drawImage(final Graphics2D g) {
		if (runtime.getFrameElement() != null) {
			g.setClip(tile.getTileOffset().getX(), tile.getTileOffset().getY(), tile.getTileSize().getX() + 1, tile.getTileSize().getY() + 1);
			final FrameRuntimeElement frame = runtime.getFrameElement();
			final int layerCount = frame.getLayerCount();
			for (int i = 0; i < layerCount; i++) {
				final GroupLayerRuntimeElement layer = frame.getLayer(i);
				if (layer.isVisible()) {
					final int sublayerCount = layer.getLayerCount();
					for (int j = 0; j < sublayerCount; j++) {
						final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
						if (sublayer.isVisible()) {
							final ImageRuntimeElement image = sublayer.getImage();
							if ((image != null) && (image.getImageRuntime() != null)) {
								image.getImageRuntime().setRenderingHints(hints);
								image.getImageRuntime().setTile(tile);
								if (tile != null) {
									image.getImageRuntime().drawImage(g);
								}
							}
						}
					}
				}
			}
			g.setClip(null);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y) {
		if (runtime.getFrameElement() != null) {
			g.setClip(x + tile.getTileOffset().getX(), y + tile.getTileOffset().getY(), tile.getTileSize().getX() + 1, tile.getTileSize().getY() + 1);
			final FrameRuntimeElement frame = runtime.getFrameElement();
			final int layerCount = frame.getLayerCount();
			for (int i = 0; i < layerCount; i++) {
				final GroupLayerRuntimeElement layer = frame.getLayer(i);
				if (layer.isVisible()) {
					final int sublayerCount = layer.getLayerCount();
					for (int j = 0; j < sublayerCount; j++) {
						final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
						if (sublayer.isVisible()) {
							final ImageRuntimeElement image = sublayer.getImage();
							if ((image != null) && (image.getImageRuntime() != null)) {
								image.getImageRuntime().setRenderingHints(hints);
								image.getImageRuntime().setTile(tile);
								if (tile != null) {
									image.getImageRuntime().drawImage(g, x, y);
								}
							}
						}
					}
				}
			}
			g.setClip(null);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y, final int w, final int h) {
		if (runtime.getFrameElement() != null) {
			g.setClip(x, y, w, h);
			final FrameRuntimeElement frame = runtime.getFrameElement();
			final int layerCount = frame.getLayerCount();
			for (int i = 0; i < layerCount; i++) {
				final GroupLayerRuntimeElement layer = frame.getLayer(i);
				if (layer.isVisible()) {
					final int sublayerCount = layer.getLayerCount();
					for (int j = 0; j < sublayerCount; j++) {
						final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
						if (sublayer.isVisible()) {
							final ImageRuntimeElement image = sublayer.getImage();
							if ((image != null) && (image.getImageRuntime() != null)) {
								image.getImageRuntime().setRenderingHints(hints);
								image.getImageRuntime().setTile(tile);
								if (tile != null) {
									image.getImageRuntime().drawImage(g, x, y, w, h);
								}
							}
						}
					}
				}
			}
			g.setClip(null);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int, int, int, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y, final int w, final int h, final int bx, final int by) {
		if (runtime.getFrameElement() != null) {
			g.setClip(x + bx, y + by, w - 2 * bx, h - 2 * by);
			final FrameRuntimeElement frame = runtime.getFrameElement();
			final int layerCount = frame.getLayerCount();
			for (int i = 0; i < layerCount; i++) {
				final GroupLayerRuntimeElement layer = frame.getLayer(i);
				if (layer.isVisible()) {
					final int sublayerCount = layer.getLayerCount();
					for (int j = 0; j < sublayerCount; j++) {
						final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
						if (sublayer.isVisible()) {
							final ImageRuntimeElement image = sublayer.getImage();
							if ((image != null) && (image.getImageRuntime() != null)) {
								image.getImageRuntime().setRenderingHints(hints);
								image.getImageRuntime().setTile(tile);
								if (tile != null) {
									image.getImageRuntime().drawImage(g, x, y, w, h);
								}
							}
						}
					}
				}
			}
			g.setClip(null);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#startRenderer()
	 */
	public void startRenderer() {
		startLayers(runtime.getFrameElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#stopRenderer()
	 */
	public void stopRenderer() {
		try {
			abortLayers(runtime.getFrameElement());
			joinLayers(runtime.getFrameElement());
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#abortRenderer()
	 */
	public void abortRenderer() {
		abortLayers(runtime.getFrameElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#joinRenderer()
	 */
	public void joinRenderer() {
		try {
			joinLayers(runtime.getFrameElement());
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void startLayers(final FrameRuntimeElement frame) {
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			this.startLayer(frame.getLayer(i));
		}
	}

	private void startLayer(final ImageLayerRuntimeElement layer) {
		if ((layer.getImage() != null) && (layer.getImage().getImageRuntime() != null)) {
			layer.getImage().getImageRuntime().setRenderingHints(hints);
			layer.getImage().getImageRuntime().setTile(tile);
			try {
				layer.getImage().getImageRuntime().startRenderer();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void startLayer(final GroupLayerRuntimeElement layer) {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.startLayer(layer.getLayer(j));
		}
	}

	private void abortLayers(final FrameRuntimeElement frame) {
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			this.abortLayer(frame.getLayer(i));
		}
	}

	private void abortLayer(final ImageLayerRuntimeElement layer) {
		if ((layer.getImage() != null) && (layer.getImage().getImageRuntime() != null)) {
			layer.getImage().getImageRuntime().abortRenderer();
		}
	}

	private void abortLayer(final GroupLayerRuntimeElement layer) {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.abortLayer(layer.getLayer(j));
		}
	}

	private void joinLayers(final FrameRuntimeElement frame) throws InterruptedException {
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			this.joinLayer(frame.getLayer(i));
		}
	}

	private void joinLayer(final ImageLayerRuntimeElement layer) throws InterruptedException {
		if ((layer.getImage() != null) && (layer.getImage().getImageRuntime() != null)) {
			layer.getImage().getImageRuntime().joinRenderer();
		}
	}

	private void joinLayer(final GroupLayerRuntimeElement layer) throws InterruptedException {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.joinLayer(layer.getLayer(j));
		}
	}

	private void prepareLayers(final FrameRuntimeElement frame, final boolean isDynamicRequired) {
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			this.prepareLayer(frame.getLayer(i), isDynamicRequired);
		}
	}

	private void prepareLayer(final ImageLayerRuntimeElement layer, final boolean isDynamicRequired) {
		if ((layer.getImage() != null) && (layer.getImage().getImageRuntime() != null)) {
			layer.getImage().getImageRuntime().setRenderingHints(hints);
			layer.getImage().getImageRuntime().setTile(tile);
			layer.getImage().getImageRuntime().prepareImage(isDynamicRequired);
		}
	}

	private void prepareLayer(final GroupLayerRuntimeElement layer, final boolean isDynamicRequired) {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.prepareLayer(layer.getLayer(j), isDynamicRequired);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#getRuntime()
	 */
	public TwisterRuntime getRuntime() {
		return runtime;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#setRenderingHints(java.util.Map)
	 */
	public void setRenderingHints(final Map<Object, Object> hints) {
		this.hints = hints;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#getTile()
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	public void setTile(final Tile tile) {
		this.tile = tile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#render()
	 */
	public void render() throws InterruptedException {
		if (runtime.getFrameElement() != null) {
			// abortLayers(runtime.getFrameElement());
			// joinLayers(runtime.getFrameElement());
			startLayers(runtime.getFrameElement());
			joinLayers(runtime.getFrameElement());
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#prepareImage(boolean)
	 */
	public void prepareImage(final boolean isDynamicRequired) {
		if (runtime.getFrameElement() != null) {
			prepareLayers(runtime.getFrameElement(), isDynamicRequired);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#dispose()
	 */
	public void dispose() {
		stopRenderer();
		runtime.dispose();
		runtime = null;
		tile = null;
		hints.clear();
		hints = null;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawSurface(java.awt.Graphics2D)
	 */
	public void drawSurface(final Graphics2D g) {
		drawSurface(g, 0, 0);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawSurface(java.awt.Graphics2D, int, int)
	 */
	public void drawSurface(Graphics2D g, int x, int y) {
		drawImage(g, x, y, getTile().getTileSize().getX() + getTile().getTileBorder().getX() * 2, getTile().getTileSize().getY() + getTile().getTileBorder().getY() * 2);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#loadSurface(com.nextbreakpoint.nextfractal.core.util.Surface)
	 */
	public void loadSurface(final Surface surface) {
	}
}
