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
package com.nextbreakpoint.nextfractal.core.swing.media;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.geom.Point2D;

import com.nextbreakpoint.nextfractal.core.media.Movie;
import com.nextbreakpoint.nextfractal.core.swing.media.renderer.MovieRenderer;
import com.nextbreakpoint.nextfractal.core.swing.media.renderer.RenderingCanvas;

/**
 * @author Andrea Medeghini
 */
public abstract class MovieApplet extends Applet {
	private static final long serialVersionUID = 1L;
	private RenderingCanvas canvas;
	private Movie movie;
	private Color color = Color.white;
	private boolean debug = false;
	private boolean loop = false;

	@Override
	public final void init() {
		if (canvas == null) {
			final String par_color = getParameter("color");
			final String par_debug = getParameter("debug");
			final String par_loop = getParameter("loop");
			if (par_debug != null) {
				if (par_debug.toLowerCase().equals("true")) {
					debug = true;
				}
			}
			if (par_loop != null) {
				if (par_loop.toLowerCase().equals("true")) {
					loop = true;
				}
			}
			if (par_color != null) {
				try {
					color = new Color(Integer.parseInt(par_color.substring(1), 16));
				}
				catch (final NumberFormatException e) {
				}
			}
			movie = createMovie();
			setBackground(color);
			setForeground(color);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			DefaultMovieContext movieContext = new DefaultMovieContext();
			MovieRenderer renderer = new MovieRenderer(movieContext, movie);
			RenderingCanvas canvas = new RenderingCanvas(renderer);
			movieContext.setColor(getBackground());
			movieContext.setDebug(debug);
			movieContext.setLoop(loop);
			movieContext.setCanvas(canvas);
			setLayout(new BorderLayout());
			add(canvas, BorderLayout.CENTER);
			canvas.requestFocus();
		}
	}

	@Override
	public final void start() {
		if (canvas == null) {
			return;
		}
		movie.setSize(canvas.getSize());
		movie.setCenter(new Point2D.Double(-canvas.getSize().getWidth() / 2, -canvas.getSize().getHeight() / 2));
		canvas.getRenderer().init();
	}

	@Override
	public final void stop() {
		if (canvas == null) {
			return;
		}
		canvas.getRenderer().dispose();
	}

	@Override
	public final void destroy() {
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.media.MovieContext#createMovie()
	 */
	public abstract Movie createMovie();
}
