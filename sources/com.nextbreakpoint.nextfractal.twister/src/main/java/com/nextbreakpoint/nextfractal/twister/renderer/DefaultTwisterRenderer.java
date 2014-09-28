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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.SurfacePool;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.frame.FrameRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.frameFilter.FrameFilterRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.image.ImageRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.GroupLayerRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.ImageLayerRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layer.LayerRuntimeElement;
import com.nextbreakpoint.nextfractal.twister.layerFilter.LayerFilterRuntimeElement;

/**
 * @author Andrea Medeghini
 */
public class DefaultTwisterRenderer implements TwisterRenderer {
	private static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);
	private Map<Object, Object> hints = new HashMap<Object, Object>();
	private TwisterRuntime runtime;
	private SurfacePool surfacePool;
	private Surface currentSurface;
	private Surface previousSurface;
	private IntegerVector2D bufferSize;
	private Tile tile;
	private Color color;
	private boolean isDynamic;

	/**
	 * @param context
	 * @param runtime
	 */
	public DefaultTwisterRenderer(final TwisterRuntime runtime) {
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
		if ((runtime == null) || (tile == null)) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			final Graphics2D g2d = currentSurface.getGraphics2D();
			g2d.setComposite(AlphaComposite.SrcOver);
			g2d.setColor(color);
			g2d.fillRect(0, 0, currentSurface.getWidth(), currentSurface.getHeight());
			currentSurface = drawFrame(runtime.getFrameElement(), currentSurface);
			g.setClip(tile.getTileOffset().getX(), tile.getTileOffset().getY(), tile.getTileSize().getX() + 1, tile.getTileSize().getY() + 1);
			// g.setColor(Color.RED);
			// g.fillRect(tile.getTileOffset().getX(), tile.getTileOffset().getY(), tile.getTileSize().getX(), tile.getTileSize().getY());
			g.drawImage(currentSurface.getImage(), tile.getTileOffset().getX() - tile.getTileBorder().getX(), tile.getTileOffset().getY() - tile.getTileBorder().getY(), null);
			g.setClip(null);
			final Surface tmpSurface = currentSurface;
			currentSurface = previousSurface;
			previousSurface = tmpSurface;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y) {
		if ((runtime == null) || (tile == null)) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			final Graphics2D g2d = currentSurface.getGraphics2D();
			g2d.setComposite(AlphaComposite.SrcOver);
			g2d.setColor(color);
			g2d.fillRect(0, 0, currentSurface.getWidth(), currentSurface.getHeight());
			currentSurface = drawFrame(runtime.getFrameElement(), currentSurface);
			g.setClip(x + tile.getTileOffset().getX(), y + tile.getTileOffset().getY(), tile.getTileSize().getX() + 1, tile.getTileSize().getY() + 1);
			// g.setColor(Color.RED);
			// g.fillRect(tile.getTileOffset().getX(), tile.getTileOffset().getY(), tile.getTileSize().getX(), tile.getTileSize().getY());
			g.drawImage(currentSurface.getImage(), x + tile.getTileOffset().getX() - tile.getTileBorder().getX(), y + tile.getTileOffset().getY() - tile.getTileBorder().getY(), null);
			g.setClip(null);
			final Surface tmpSurface = currentSurface;
			currentSurface = previousSurface;
			previousSurface = tmpSurface;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y, final int w, final int h) {
		if ((runtime == null) || (tile == null)) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			final Graphics2D g2d = currentSurface.getGraphics2D();
			g2d.setComposite(AlphaComposite.SrcOver);
			g2d.setColor(color);
			g2d.fillRect(0, 0, currentSurface.getWidth(), currentSurface.getHeight());
			currentSurface = drawFrame(runtime.getFrameElement(), currentSurface);
			final double sx = w / (double) tile.getTileSize().getX();
			final double sy = h / (double) tile.getTileSize().getY();
			final int dw = (int) Math.rint(bufferSize.getX() * sx);
			final int dh = (int) Math.rint(bufferSize.getY() * sy);
			g.setClip(x, y, dw, dh);
			// g.setColor(Color.RED);
			// g.fillRect(x, y, dw, dh);
			g.drawImage(currentSurface.getImage(), x - tile.getTileBorder().getX(), y - tile.getTileBorder().getY(), dw, dh, null);
			g.setClip(null);
			final Surface tmpSurface = currentSurface;
			currentSurface = previousSurface;
			previousSurface = tmpSurface;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawImage(java.awt.Graphics2D, int, int, int, int, int, int)
	 */
	public void drawImage(final Graphics2D g, final int x, final int y, final int w, final int h, final int bx, final int by) {
		if ((runtime == null) || (tile == null)) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			final Graphics2D g2d = currentSurface.getGraphics2D();
			g2d.setComposite(AlphaComposite.SrcOver);
			g2d.setColor(color);
			g2d.fillRect(0, 0, currentSurface.getWidth(), currentSurface.getHeight());
			currentSurface = drawFrame(runtime.getFrameElement(), currentSurface);
			final double sx = w / (double) tile.getTileSize().getX();
			final double sy = h / (double) tile.getTileSize().getY();
			final int dw = (int) Math.rint(bufferSize.getX() * sx);
			final int dh = (int) Math.rint(bufferSize.getY() * sy);
			g.setClip(x + bx, y + by, dw - 2 * bx, dh - 2 * by);
			// g.setColor(Color.RED);
			// g.fillRect(x, y, dw, dh);
			g.drawImage(currentSurface.getImage(), x - tile.getTileBorder().getX(), y - tile.getTileBorder().getY(), dw, dh, null);
			g.setClip(null);
			final Surface tmpSurface = currentSurface;
			currentSurface = previousSurface;
			previousSurface = tmpSurface;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#startRenderer()
	 */
	public void startRenderer() {
		if (runtime == null) {
			return;
		}
		startLayers(runtime.getFrameElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#stopRenderer()
	 */
	public void stopRenderer() {
		if (runtime == null) {
			return;
		}
		try {
			abortLayers(runtime.getFrameElement());
			joinLayers(runtime.getFrameElement());
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#stopRenderer()
	 */
	public void abortRenderer() {
		if (runtime == null) {
			return;
		}
		abortLayers(runtime.getFrameElement());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#stopRenderer()
	 */
	public void joinRenderer() {
		if (runtime == null) {
			return;
		}
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
		startFilter(frame);
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
		startFilter(layer);
	}

	private void startLayer(final GroupLayerRuntimeElement layer) {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.startLayer(layer.getLayer(j));
		}
		startFilter(layer);
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
		isDynamic = false;
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			this.prepareLayer(frame.getLayer(i), isDynamicRequired);
		}
		final int filterCount = frame.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			if (frame.getFilter(i).getFilterRuntime() != null) {
				frame.getFilter(i).getFilterRuntime().prepareFilter(isDynamic);
			}
		}
	}

	private void prepareLayer(final ImageLayerRuntimeElement layer, final boolean isDynamicRequired) {
		if ((layer.getImage() != null) && (layer.getImage().getImageRuntime() != null)) {
			layer.getImage().getImageRuntime().setRenderingHints(hints);
			layer.getImage().getImageRuntime().setTile(tile);
			layer.getImage().getImageRuntime().prepareImage(isDynamicRequired);
			isDynamic |= layer.getImage().getImageRuntime().isDynamic();
		}
		final int filterCount = layer.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			if (layer.getFilter(i).getFilterRuntime() != null) {
				layer.getFilter(i).getFilterRuntime().prepareFilter();
			}
		}
	}

	private void prepareLayer(final GroupLayerRuntimeElement layer, final boolean isDynamicRequired) {
		final int sublayerCount = layer.getLayerCount();
		for (int j = 0; j < sublayerCount; j++) {
			this.prepareLayer(layer.getLayer(j), isDynamicRequired);
		}
		final int filterCount = layer.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			if (layer.getFilter(i).getFilterRuntime() != null) {
				layer.getFilter(i).getFilterRuntime().prepareFilter();
			}
		}
	}

	private void startFilter(final LayerRuntimeElement layer) {
		final int filterCount = layer.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			final LayerFilterRuntimeElement filter = layer.getFilter(i);
			if (filter.getFilterRuntime() != null) {
				filter.getFilterRuntime().setTile(tile);
			}
		}
	}

	private void startFilter(final FrameRuntimeElement frame) {
		final int filterCount = frame.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			final FrameFilterRuntimeElement filter = frame.getFilter(i);
			if (filter.getFilterRuntime() != null) {
				filter.getFilterRuntime().setTile(tile);
			}
		}
	}

	private void drawImage(final ImageRuntimeElement image, final Surface tmpSurface0, final float opacity) {
		final Graphics2D g2d = tmpSurface0.getGraphics2D();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		if ((image != null) && (image.getImageRuntime() != null)) {
			image.getImageRuntime().drawImage(g2d);
		}
		else {
			// g2d.setColor(Color.BLACK);
			// g2d.fillRect(0, 0, tmpSurface0.getWidth(), tmpSurface0.getHeight());
		}
	}

	private Surface filterImage(final LayerRuntimeElement layer, Surface tmpSurface0) {
		final int filterCount = layer.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			final LayerFilterRuntimeElement filter = layer.getFilter(i);
			if ((filter.getFilterRuntime() != null) && filter.isEnabled()) {
				final Surface tmpSurface1 = surfacePool.getSurface();
				filter.getFilterRuntime().renderImage(tmpSurface0, tmpSurface1);
				surfacePool.putSurface(tmpSurface0);
				tmpSurface0 = tmpSurface1;
			}
		}
		return tmpSurface0;
	}

	private void drawLayers(final FrameRuntimeElement frame, final Surface tmpSurface0, final float opacity) {
		final int layerCount = frame.getLayerCount();
		for (int i = 0; i < layerCount; i++) {
			final GroupLayerRuntimeElement layer = frame.getLayer(i);
			if (layer.isVisible()) {
				this.drawLayer(layer, tmpSurface0, opacity);
			}
		}
	}

	private void drawLayer(final ImageLayerRuntimeElement layer, final Surface tmpSurface0, final float opacity) {
		if (layer.getFilterCount() > 0) {
			final Surface tmpSurface1 = surfacePool.getSurface();
			final Graphics2D g2d0 = tmpSurface0.getGraphics2D();
			final Graphics2D g2d1 = tmpSurface1.getGraphics2D();
			g2d1.setComposite(AlphaComposite.Src);
			g2d1.setColor(TRANSPARENT_COLOR);
			g2d1.fillRect(0, 0, tmpSurface1.getWidth(), tmpSurface1.getHeight());
			this.drawImage(layer.getImage(), tmpSurface1, 1f);
			final Surface tmpSurface2 = filterImage(layer, tmpSurface1);
			g2d0.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity * layer.getOpacity()));
			g2d0.drawImage(tmpSurface2.getImage(), 0, 0, null);
			surfacePool.putSurface(tmpSurface2);
		}
		else {
			this.drawImage(layer.getImage(), tmpSurface0, opacity * layer.getOpacity());
		}
	}

	private void drawLayer(final GroupLayerRuntimeElement layer, final Surface tmpSurface0, final float opacity) {
		if (layer.getFilterCount() > 0) {
			final Surface tmpSurface1 = surfacePool.getSurface();
			final int sublayerCount = layer.getLayerCount();
			for (int j = 0; j < sublayerCount; j++) {
				final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
				if (sublayer.isVisible()) {
					this.drawLayer(sublayer, tmpSurface1, 1f);
				}
			}
			final Surface tmpSurface2 = filterImage(layer, tmpSurface1);
			final Graphics2D g2d = tmpSurface0.getGraphics2D();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity * layer.getOpacity()));
			g2d.drawImage(tmpSurface2.getImage(), 0, 0, null);
			surfacePool.putSurface(tmpSurface2);
		}
		else {
			final int sublayerCount = layer.getLayerCount();
			for (int j = 0; j < sublayerCount; j++) {
				final ImageLayerRuntimeElement sublayer = layer.getLayer(j);
				if (sublayer.isVisible()) {
					this.drawLayer(sublayer, tmpSurface0, opacity * layer.getOpacity());
				}
			}
		}
	}

	private Surface drawFrame(final FrameRuntimeElement frame, Surface tmpSurface0) {
		drawLayers(frame, tmpSurface0, 1f);
		final int filterCount = frame.getFilterCount();
		for (int i = 0; i < filterCount; i++) {
			final FrameFilterRuntimeElement filter = frame.getFilter(i);
			if ((filter.getFilterRuntime() != null) && filter.isEnabled()) {
				final Surface tmpSurface1 = surfacePool.getSurface();
				filter.getFilterRuntime().renderImage(tmpSurface0, tmpSurface1, previousSurface);
				surfacePool.putSurface(tmpSurface0);
				tmpSurface0 = tmpSurface1;
			}
		}
		return tmpSurface0;
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
		bufferSize = new IntegerVector2D(tile.getTileSize().getX() + tile.getTileBorder().getX() * 2, tile.getTileSize().getY() + tile.getTileBorder().getY() * 2);
		if ((surfacePool == null) || (surfacePool.getWidth() != bufferSize.getX()) || (surfacePool.getHeight() != bufferSize.getY())) {
			if (currentSurface != null) {
				currentSurface.dispose();
				currentSurface = null;
			}
			if (previousSurface != null) {
				previousSurface.dispose();
				previousSurface = null;
			}
			if (surfacePool != null) {
				surfacePool.dispose();
				surfacePool = null;
			}
			surfacePool = new SurfacePool(bufferSize.getX(), bufferSize.getY());
			currentSurface = surfacePool.getSurface();
			previousSurface = surfacePool.getSurface();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#render()
	 */
	public void render() throws InterruptedException {
		if (runtime == null) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			// abortLayers(runtime.getFrameElement());
			// joinLayers(runtime.getFrameElement());
			startLayers(runtime.getFrameElement());
			joinLayers(runtime.getFrameElement());
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#prepareImage()
	 */
	public void prepareImage(final boolean isDynamicRequired) {
		if (runtime == null) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			prepareLayers(runtime.getFrameElement(), isDynamicRequired);
		}
		color = new Color(runtime.getBackground().getARGB(), true);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#dispose()
	 */
	public void dispose() {
		stopRenderer();
		if (runtime != null) {
			runtime.dispose();
			runtime = null;
		}
		tile = null;
		if (hints != null) {
			hints.clear();
			hints = null;
		}
		bufferSize = null;
		currentSurface = null;
		previousSurface = null;
		if (surfacePool != null) {
			surfacePool.dispose();
			surfacePool = null;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#drawSurface(java.awt.Graphics2D)
	 */
	public void drawSurface(final Graphics2D g) {
		drawSurface(g, 0, 0);
	}

	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawSurface(final Graphics2D g, final int x, final int y) {
		if ((runtime == null) || (tile == null)) {
			return;
		}
		if (runtime.getFrameElement() != null) {
			final Graphics2D g2d = currentSurface.getGraphics2D();
			g2d.setComposite(AlphaComposite.SrcOver);
			g2d.setColor(color);
			g2d.fillRect(0, 0, currentSurface.getWidth(), currentSurface.getHeight());
			currentSurface = drawFrame(runtime.getFrameElement(), currentSurface);
			g.drawImage(currentSurface.getImage(), x, y, currentSurface.getWidth(), currentSurface.getHeight(), null);
			final Surface tmpSurface = currentSurface;
			currentSurface = previousSurface;
			previousSurface = tmpSurface;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer#loadSurface(com.nextbreakpoint.nextfractal.core.util.Surface)
	 */
	public void loadSurface(final Surface surface) {
		// final int[] previousData = ((DataBufferInt) previousSurface.getImage().getRaster().getDataBuffer()).getData();
		// final int[] data = ((DataBufferInt) surface.getImage().getRaster().getDataBuffer()).getData();
		// System.arraycopy(data, 0, previousData, 0, data.length);
		previousSurface.getGraphics2D().setComposite(AlphaComposite.SrcOver);
		previousSurface.getGraphics2D().drawImage(surface.getImage(), 0, 0, null);
	}
}
