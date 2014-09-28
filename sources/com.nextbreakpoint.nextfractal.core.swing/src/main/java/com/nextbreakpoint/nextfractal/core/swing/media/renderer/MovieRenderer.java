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
package com.nextbreakpoint.nextfractal.core.swing.media.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.nextbreakpoint.nextfractal.core.media.Controller;
import com.nextbreakpoint.nextfractal.core.media.EngineEvent;
import com.nextbreakpoint.nextfractal.core.media.Movie;
import com.nextbreakpoint.nextfractal.core.media.MovieContext;
import com.nextbreakpoint.nextfractal.core.media.Pipeline;

/**
 * @author Andrea Medeghini
 */
public final class MovieRenderer {
	private final MovieContext movieContext;
	private final Movie movie;
	private Controller controller;
	private Pipeline pipeline;

	/**
	 * @param movieContext
	 * @param movie
	 */
	public MovieRenderer(final MovieContext movieContext, final Movie movie) {
		this.movieContext = movieContext;
		this.movie = movie;
	}

	/**
	 * 
	 */
	public synchronized void init() {
		pipeline = new Pipeline(movieContext);
		controller = new RendererController();
		movie.load();
		movie.build(controller, null, null, null);
		movie.init();
	}

	/**
	 * 
	 */
	public synchronized void dispose() {
		movie.kill();
		movie.flush();
		pipeline.kill();
	}

	/**
	 * @param g2d
	 */
	public synchronized void draw(Graphics2D g2d) {
		setRenderingHints(g2d);
		pipeline.render(g2d, movie.getWidth(), movie.getHeight(), movie);
	}

	/**
	 * @return
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * @param size
	 */
	public synchronized void setSize(final Dimension size) {
		movie.setSize(size);
		movie.build(controller, null, null, null);
	}

	/**
	 * @param event
	 */
	public synchronized void enqueueEvent(EngineEvent event) {
		pipeline.enqueueEvent(event);
	}

	public synchronized void nextFrame() {
		movie.nextFrame();
	}

	public synchronized void prevFrame() {
		movie.prevFrame();
	}

	public synchronized void setFrame(int frame) {
		movie.setFrame(frame);
	}

	private void setRenderingHints(final Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}

	private class RendererController implements Controller {
		public void play() {
			synchronized (MovieRenderer.this) {
				movieContext.setStopped(false);
			}
		}

		public void stop() {
			synchronized (MovieRenderer.this) {
				movieContext.setStopped(true);
			}
		}

		public void gotoAndPlay(final int frame) {
			synchronized (MovieRenderer.this) {
				movie.setFrame(frame % movie.getFrames());
				movieContext.setStopped(false);
			}
		}

		public void gotoAndStop(final int frame) {
			synchronized (MovieRenderer.this) {
				movie.setFrame(frame % movie.getFrames());
				movieContext.setStopped(true);
			}
		}
	}
}
