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
package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import java.awt.Graphics2D;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;

import com.nextbreakpoint.nextfractal.core.util.DoubleVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotRuntime;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public interface MandelbrotRenderer {
	/**
	 * 
	 */
	public static final int MODE_CALCULATE = 0x01;
	/**
	 * 
	 */
	public static final int MODE_REFRESH = 0x02;

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
	 * @param g the destination.
	 */
	public void drawImage(Graphics2D g);

	/**
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawImage(Graphics2D g, int x, int y);

	/**
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void drawImage(Graphics2D g, int x, int y, int w, int h);

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
	 * @return true if is interrupted.
	 */
	public boolean isInterrupted();

	/**
	 * @return the status.
	 */
	public int getRenderingStatus();

	/**
	 * @param mode
	 */
	public void setMode(int mode);

	/**
	 * @return
	 */
	public int getMode();

	/**
	 * @return the runtime.
	 */
	public MandelbrotRuntime getRuntime();

	/**
	 * @param runtime the fractal.
	 */
	public void setRuntime(MandelbrotRuntime runtime);

	/**
	 * @param hints
	 */
	public void setRenderingHints(Map<Object, Object> hints);

	/**
	 * @param view
	 */
	public void setView(View view);

	/**
	 * @param view
	 * @param constant
	 * @param imageMode
	 */
	public void setView(final View view, final DoubleVector2D constant, final int imageMode);

	/**
	 * @param tile
	 */
	public void setTile(Tile tile);

	/**
	 * @return
	 */
	public Tile getTile();

	/**
	 * @param mode
	 */
	public void setMandelbrotMode(Integer mode);

	/**
	 * @param constant
	 */
	public void setConstant(DoubleVector2D constant);

	/**
	 * @return
	 */
	public boolean isDynamic();

	/**
	 * @return
	 */
	public boolean isViewChanged();

	/**
	 * 
	 */
	public void dispose();

	/**
	 * 
	 */
	public void start();

	/**
	 * 
	 */
	public void stop();

	/**
	 * 
	 */
	public void asyncStart();

	/**
	 * 
	 */
	public void asyncStop();
}
