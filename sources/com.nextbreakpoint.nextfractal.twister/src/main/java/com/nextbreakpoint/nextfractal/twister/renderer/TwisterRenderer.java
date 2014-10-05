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
package com.nextbreakpoint.nextfractal.twister.renderer;

import java.awt.Graphics2D;
import java.util.Map;

import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;

/**
 * @author Andrea Medeghini
 */
public interface TwisterRenderer {
	/**
	 * 
	 */
	public static final int STATUS_RENDERING = 0x01;
	/**
	 * 
	 */
	public static final int STATUS_TERMINATED = 0x02;
	/**
	 * 
	 */
	public static final int STATUS_ABORTED = 0x03;

	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawImage(Graphics2D g);

	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawImage(final Graphics2D g, int x, int y);

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void drawImage(Graphics2D g, int x, int y, int w, int h);

	/**
	 * @param g
	 * @param x
	 * @param i
	 * @param w
	 * @param h
	 * @param bx
	 * @param by
	 */
	public void drawImage(Graphics2D g, int x, int i, int w, int h, int bx, int by);

	/**
	 * 
	 */
	public void startRenderer();

	/**
	 * 
	 */
	public void stopRenderer();

	/**
	 * 
	 */
	public void abortRenderer();

	/**
	 * 
	 */
	public void joinRenderer();

	/**
	 * @throws InterruptedException
	 */
	public void render() throws InterruptedException;

	/**
	 * @return the tile
	 */
	public Tile getTile();

	/**
	 * @param tile
	 */
	public void setTile(Tile tile);

	/**
	 * @param hints
	 */
	public void setRenderingHints(Map<Object, Object> hints);

	/**
	 * @return
	 */
	public TwisterRuntime getRuntime();

	/**
	 * @param isDynamic
	 */
	public void prepareImage(boolean isDynamic);

	/**
	 * 
	 */
	public void dispose();

	/**
	 * @param g
	 */
	public void drawSurface(Graphics2D g);

	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawSurface(Graphics2D g, int x, int y);

	/**
	 * @param surface
	 */
	public void loadSurface(Surface surface);

	/**
	 * @return
	 */
	public RenderFactory getRenderFactory();
	
	/**
	 * @param renderFactory
	 */
	public void setRenderFactory(RenderFactory renderFactory);
}
