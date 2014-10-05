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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import java.awt.Graphics2D;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class BorderRuntime extends ImageExtensionRuntime<BorderConfig> {
	private RenderFactory renderFactory;
	private boolean isOverlay;
	private java.awt.Color awtColor;
	private Color color;
	private Tile tile;

	public BorderRuntime() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
		this.tile = tile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#startRenderer()
	 */
	@Override
	public void startRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#abortRenderer()
	 */
	@Override
	public void abortRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#joinRenderer()
	 */
	@Override
	public void joinRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#getRenderingStatus()
	 */
	@Override
	public int getRenderingStatus() {
		return TwisterRenderer.STATUS_TERMINATED;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D)
	 */
	@Override
	public void drawImage(final Graphics2D g2d) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX();
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY();
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			g2d.setColor(awtColor);
			g2d.fillRect(tx, ty, w, sh);
			g2d.fillRect(tx, h - sh + ty, w, sh);
			g2d.fillRect(tx, ty, sw, h);
			g2d.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + x;
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + y;
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			g2d.setColor(awtColor);
			g2d.fillRect(tx, ty, w, sh);
			g2d.fillRect(tx, h - sh + ty, w, sh);
			g2d.fillRect(tx, ty, sw, h);
			g2d.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		if (!isOverlay && (tile != null)) {
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			g2d.setColor(awtColor);
			g2d.fillRect(x, y, w, sh);
			g2d.fillRect(x, h - sh + y, w, sh);
			g2d.fillRect(x, y, sw, h);
			g2d.fillRect(w - sw + x, y, sw, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawImage(GraphicsContext gc) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX();
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY();
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			gc.setFill(color);
			gc.fillRect(tx, ty, w, sh);
			gc.fillRect(tx, h - sh + ty, w, sh);
			gc.fillRect(tx, ty, sw, h);
			gc.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y) {
		if (!isOverlay && (tile != null)) {
			int w = getImageSize().getX();
			int h = getImageSize().getY();
			int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + x;
			int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + y;
			int d = w - tile.getTileBorder().getX() * 2;
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * d) / 100d);
			gc.setFill(color);
			gc.fillRect(tx, ty, w, sh);
			gc.fillRect(tx, h - sh + ty, w, sh);
			gc.fillRect(tx, ty, sw, h);
			gc.fillRect(w - sw + tx, ty, sw, h);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final GraphicsContext gc, final int x, final int y, final int w, final int h) {
		if (!isOverlay && (tile != null)) {
			int sw = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			int sh = (int) Math.rint((getConfig().getSize().doubleValue() * w) / 100d);
			gc.setFill(color);
			gc.fillRect(x, y, w, sh);
			gc.fillRect(x, h - sh + y, w, sh);
			gc.fillRect(x, y, sw, h);
			gc.fillRect(w - sw + x, y, sw, h);
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
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#setRenderingHints(java.util.Map)
	 */
	@Override
	public void setRenderingHints(final Map<Object, Object> hints) {
		if (hints.get(TwisterRenderingHints.KEY_TYPE) == TwisterRenderingHints.TYPE_OVERLAY) {
			isOverlay = true;
		}
		else {
			isOverlay = false;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#prepareImage(boolean)
	 */
	@Override
	public void prepareImage(final boolean isDynamicRequired) {
		awtColor = new java.awt.Color(getConfig().getColor().getARGB(), true);
		color = new Color(getConfig().getColor().getRed(), getConfig().getColor().getGreen(), getConfig().getColor().getBlue(), getConfig().getColor().getAlpha());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return false;
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
}
