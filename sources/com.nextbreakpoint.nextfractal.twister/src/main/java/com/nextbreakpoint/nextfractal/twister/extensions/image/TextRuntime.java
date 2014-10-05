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
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderAffine;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderColor;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFont;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class TextRuntime extends ImageExtensionRuntime<TextConfig> {
	private RenderFactory renderFactory;
	private boolean isOverlay;
	private RenderColor color;
	private RenderFont font;
	private Tile tile;
	private float fontSize;
	private int left;
	private int top;
	private double a;

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
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc) {
		if (!isOverlay && (tile != null)) {
			if (fontSize > 0) {
				int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + left;
				int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + top;
//				font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
				gc.setFill(color);
//TODO				gc.setFont(font);
				gc.saveTransform();
				RenderAffine affine = renderFactory.createTranslateAffine(tx, ty);
				affine.append(renderFactory.createRotateAffine(a, 0, 0));
				gc.setAffine(affine);
				gc.fillText(getConfig().getText(), 0, 0);
				gc.restoreTransform();
			}
		}
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
		if (!isOverlay && (tile != null)) {
			if (fontSize > 0) {
//				font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
				gc.setFill(color);
//TODO				gc.setFont(font);
				gc.saveTransform();
				RenderAffine affine = renderFactory.createTranslateAffine(x, y);
				affine.append(renderFactory.createRotateAffine(a, 0, 0));
				gc.setAffine(affine);
				gc.fillText(getConfig().getText(), 0, 0);
				gc.restoreTransform();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(javafx.scene.canvas.GraphicsContext, int, int, int, int)
	 */
	@Override
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
		if (!isOverlay && (tile != null)) {
			fontSize = (float) ((getConfig().getSize().doubleValue() * h) / 100f);
			if (fontSize > 0) {
//				font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
				gc.setFill(color);
//TODO				gc.setFont(font);
				gc.saveTransform();
				RenderAffine affine = renderFactory.createTranslateAffine(x, y);
				affine.append(renderFactory.createRotateAffine(a, 0, 0));
				gc.setAffine(affine);
				gc.fillText(getConfig().getText(), 0, 0);
				gc.restoreTransform();
			}
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
		int w = getImageSize().getX();
		int h = getImageSize().getY();
		left = (int) Math.rint((getConfig().getLeft().doubleValue() * w) / 100d);
		top = (int) Math.rint((getConfig().getTop().doubleValue() * h) / 100d);
		fontSize = (float) ((getConfig().getSize().doubleValue() * h) / 100f);
		a = (getConfig().getRotation() * Math.PI) / 180d;
		if (fontSize > 0) {
//TODO			font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
			color = renderFactory.createColor(getConfig().getColor().getRed(), getConfig().getColor().getGreen(), getConfig().getColor().getBlue(), getConfig().getColor().getAlpha());
		}
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
