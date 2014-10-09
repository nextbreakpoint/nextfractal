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

import java.util.Map;

import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderColor;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class BorderRuntime extends ImageExtensionRuntime<BorderConfig> {
	private RenderFactory renderFactory;
	private boolean isOverlay;
	private RenderColor color;
	private Tile tile;

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#setTile(com.nextbreakpoint.nextfractal.core.util.Tile)
	 */
	@Override
	public void setTile(final Tile tile) {
		this.tile = tile;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#startRenderer()
	 */
	@Override
	public void startRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#abortRenderer()
	 */
	@Override
	public void abortRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#joinRenderer()
	 */
	@Override
	public void joinRenderer() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#getRenderingStatus()
	 */
	@Override
	public int getRenderingStatus() {
		return TwisterRenderer.STATUS_TERMINATED;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc) {
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
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
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
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
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
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#getImageSize()
	 */
	@Override
	public IntegerVector2D getImageSize() {
		return tile.getImageSize();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#setRenderingHints(java.util.Map)
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
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#prepareImage(boolean)
	 */
	@Override
	public void prepareImage(final boolean isDynamicRequired) {
		color = renderFactory.createColor(getConfig().getColor().getRed(), getConfig().getColor().getGreen(), getConfig().getColor().getBlue(), getConfig().getColor().getAlpha());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return false;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#getRenderFactory()
	 */
	@Override
	public RenderFactory getRenderFactory() {
		return renderFactory;
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.extensionPoints.image.ImageExtensionRuntime#setRenderFactory(com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory)
	 */
	@Override
	public void setRenderFactory(RenderFactory renderFactory) {
		this.renderFactory = renderFactory;
	}
}
