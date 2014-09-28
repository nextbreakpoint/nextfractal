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
package com.nextbreakpoint.nextfractal.twister.extensions.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Map;

import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;

/**
 * @author Andrea Medeghini
 */
public class TextRuntime extends ImageExtensionRuntime<TextConfig> {
	private boolean isOverlay;
	private Tile tile;
	private Color color;
	private Font font;
	private float fontSize;
	private int left;
	private int top;
	private double a;

	public TextRuntime() {
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
			if (fontSize > 0) {
				int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + left;
				int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + top;
				g2d.setColor(color);
				g2d.setFont(font);
				AffineTransform newTransform = AffineTransform.getTranslateInstance(tx, ty);
				AffineTransform oldTransform = g2d.getTransform();
				newTransform.rotate(a);
				g2d.setTransform(newTransform);
				g2d.drawString(getConfig().getText(), 0, 0);
				g2d.setTransform(oldTransform);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y) {
		if (!isOverlay && (tile != null)) {
			if (fontSize > 0) {
				int tx = tile.getTileBorder().getX() - tile.getTileOffset().getX() + left + x;
				int ty = tile.getTileBorder().getY() - tile.getTileOffset().getY() + top + y;
				g2d.setColor(color);
				g2d.setFont(font);
				AffineTransform newTransform = AffineTransform.getTranslateInstance(tx, ty);
				AffineTransform oldTransform = g2d.getTransform();
				newTransform.rotate(a);
				g2d.setTransform(newTransform);
				g2d.drawString(getConfig().getText(), 0, 0);
				g2d.setTransform(oldTransform);
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#drawImage(java.awt.Graphics2D, int, int, int, int)
	 */
	@Override
	public void drawImage(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
		if (!isOverlay && (tile != null)) {
			fontSize = (float) ((getConfig().getSize().doubleValue() * h) / 100f);
			if (fontSize > 0) {
				font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
				g2d.setColor(color);
				g2d.setFont(font);
				AffineTransform newTransform = AffineTransform.getTranslateInstance(x, y);
				AffineTransform oldTransform = g2d.getTransform();
				newTransform.rotate(a);
				g2d.setTransform(newTransform);
				g2d.drawString(getConfig().getText(), 0, 0);
				g2d.setTransform(oldTransform);
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
			font = getConfig().getFont().deriveFont((fontSize * 96f) / 72f);
			color = new Color(getConfig().getColor().getARGB(), true);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.image.extension.ImageExtensionRuntime#isDynamic()
	 */
	@Override
	public boolean isDynamic() {
		return false;
	}
}
