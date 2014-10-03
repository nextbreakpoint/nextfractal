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
package com.nextbreakpoint.nextfractal.twister.image.extension;

import java.awt.Graphics2D;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;

/**
 * @author Andrea Medeghini
 */
public abstract class ImageExtensionRuntime<T extends ImageExtensionConfig> extends ConfigurableExtensionRuntime<T> {
	/**
	 * 
	 */
	public abstract void startRenderer();

	/**
	 * 
	 */
	public abstract void abortRenderer();

	/**
	 * @throws InterruptedException
	 */
	public abstract void joinRenderer() throws InterruptedException;

	/**
	 * @param tile
	 */
	public abstract void setTile(Tile tile);

	/**
	 * @return the rendering status.
	 */
	public abstract int getRenderingStatus();

	/**
	 * @return
	 */
	public abstract boolean isDynamic();

	/**
	 * @param isDynamicRequired
	 */
	public abstract void prepareImage(boolean isDynamicRequired);

	/**
	 * @param gc
	 */
	public abstract void drawImage(GraphicsContext gc);

	/**
	 * @param gc
	 * @param x
	 * @param y
	 */
	public abstract void drawImage(GraphicsContext gc, int x, int y);

	/**
	 * @param gc
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public abstract void drawImage(GraphicsContext gc, int x, int y, int w, int h);

	/**
	 * @param g2d
	 */
	public abstract void drawImage(Graphics2D g2d);

	/**
	 * @param g2d
	 * @param x
	 * @param y
	 */
	public abstract void drawImage(Graphics2D g2d, int x, int y);

	/**
	 * @param g2d
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public abstract void drawImage(Graphics2D g2d, int x, int y, int w, int h);

	/**
	 * @return
	 */
	public abstract IntegerVector2D getImageSize();

	/**
	 * @param hints
	 */
	public abstract void setRenderingHints(Map<Object, Object> hints);

	/**
	 * @see com.nextbreakpoint.nextfractal.core.extension.ExtensionRuntime#dispose()
	 */
	@Override
	public void dispose() {
	}
}
