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
package com.nextbreakpoint.nextfractal.contextfree;

import java.util.Map;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.contextfree.renderer.ContextFreeRenderer;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderFactory;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.util.View;

/**
 * @author Andrea Medeghini
 */
public class ContextFreeManager {
	protected static final Logger logger = Logger.getLogger(ContextFreeManager.class.getName());
	protected static final boolean debug = false;
	protected int width = 0;
	protected int height = 0;
	protected int defaultWidth = 0;
	protected int defaultHeight = 0;
	protected double defaultRotation = 0;
	protected int defaultShift = 0;
	protected double rotation = 0;
	protected int shift = 0;
	protected double zoomSpeed = 0;
	protected double shiftSpeed = 0;
	protected double rotationSpeed = 0;
	protected ContextFreeRenderer renderer;

	/**
	 * @param renderer
	 *        the renderer.
	 */
	public ContextFreeManager(final ContextFreeRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	/**
	 * @param hints
	 */
	public void setRenderingHints(final Map<Object, Object> hints) {
		renderer.setRenderingHints(hints);
	}

	/**
	 * @param runtime
	 */
	public void setRuntime(final ContextFreeRuntime runtime) {
		renderer.setRuntime(runtime);
	}

	/**
	 * @param renderer
	 */
	public void setRenderer(final ContextFreeRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * 
	 */
	public void startRenderer() {
		renderer.startRenderer();
	}

	/**
	 * 
	 */
	public void abortRenderer() {
		renderer.abortRenderer();
	}

	/**
	 * @throws InterruptedException
	 */
	public void joinRenderer() throws InterruptedException {
		renderer.joinRenderer();
	}

	/**
	 * @param tile
	 */
	public void setTile(final Tile tile) {
		renderer.setTile(tile);
	}

	/**
	 * @return
	 */
	public Tile getTile() {
		return renderer.getTile();
	}

	/**
	 * @param renderFactory
	 */
	public void setRenderFactory(RenderFactory renderFactory) {
		renderer.setRenderFactory(renderFactory);
	}

	/**
	 * @return the status.
	 */
	public int getRenderingStatus() {
		return renderer.getRenderingStatus();
	}

	/**
	 * @param mode
	 */
	public void setMode(final int mode) {
		renderer.setMode(mode);
	}

	/**
	 * @param gc
	 */
	public void drawImage(final RenderGraphicsContext gc) {
		renderer.drawImage(gc);
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 */
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y) {
		renderer.drawImage(gc, x, y);
	}

	/**
	 * @param gc
	 * @param x
	 * @param y
	 * @param ow
	 * @param oh
	 */
	public void drawImage(final RenderGraphicsContext gc, final int x, final int y, final int w, final int h) {
		renderer.drawImage(gc, x, y, w, h);
	}

	/**
	 * @param view
	 */
	public void setView(final View view) {
		renderer.setView(view);
	}

	/**
	 * @return
	 */
	public boolean isDynamic() {
		return renderer.isDynamic();
	}

	/**
	 * @return
	 */
	public boolean isViewChanged() {
		return renderer.isViewChanged();
	}

	/**
	 * 
	 */
	public void dispose() {
		if (renderer != null) {
			renderer.dispose();
			renderer = null;
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.FractalRenderer#start()
	 */
	public void start() {
		renderer.start();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.FractalRenderer#stop()
	 */
	public void stop() {
		renderer.stop();
	}
}
