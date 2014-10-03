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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import com.nextbreakpoint.nextfractal.core.media.EngineException;
import com.nextbreakpoint.nextfractal.core.media.Movie;
import com.nextbreakpoint.nextfractal.core.swing.media.renderer.MovieRenderer;
import com.nextbreakpoint.nextfractal.core.swing.media.renderer.RenderingCanvas;

/**
 * @author Andrea Medeghini
 */
public final class MovieWindow extends Frame {
	private static final long serialVersionUID = 1L;
	private GraphicsEnvironment environment;
	private GraphicsDevice device;
	private MovieRenderer renderer;
	private Color color = Color.white;
	private boolean debug = false;
	private boolean loop = false;

	/**
	 * @param movie
	 * @param title
	 */
	public MovieWindow(final Movie movie, final String title) {
		try {
			environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			if (GraphicsEnvironment.isHeadless()) {
				throw new EngineException("Graphics environment is headless!");
			}
			device = environment.getDefaultScreenDevice();
			if (device.getType() != GraphicsDevice.TYPE_RASTER_SCREEN) {
				throw new EngineException("Graphics device not found!");
			}
			loadProperties();
			setTitle(title);
			setBackground(color);
			setForeground(color);
			setUndecorated(true);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (!device.isFullScreenSupported()) {
				throw new EngineException("Fullscreen not supported!");
			}
			device.setFullScreenWindow(this);
			if (!device.isDisplayChangeSupported()) {
				throw new EngineException("Displaychange not supported!");
			}
			device.setDisplayMode(new DisplayMode(640, 480, 32, DisplayMode.REFRESH_RATE_UNKNOWN));
			DefaultMovieContext movieContext = new DefaultMovieContext();
			MovieRenderer renderer = new MovieRenderer(movieContext, movie);
			RenderingCanvas canvas = new RenderingCanvas(renderer);
			movieContext.setColor(getBackground());
			movieContext.setDebug(debug);
			movieContext.setLoop(loop);
			movieContext.setCanvas(canvas);
			setLayout(new BorderLayout());
			add(canvas, BorderLayout.CENTER);
			pack();
			canvas.requestFocus();
			addWindowListener(new DefaultWindowAdapter());
			addKeyListener(new DefaultKeyAdapter());
			renderer = canvas.getRenderer();
			movie.setSize(canvas.getSize());
			movie.setCenter(new Point2D.Double(-canvas.getSize().getWidth() / 2, -canvas.getSize().getHeight() / 2));
			renderer.init();
		}
		catch (final EngineException e) {
			e.printStackTrace();
		}
	}

	private void loadProperties() {
		if (System.getProperty("debug", "false").toLowerCase().equals("true")) {
			debug = true;
		}
		if (System.getProperty("loop", "false").toLowerCase().equals("true")) {
			loop = true;
		}
		try {
			color = new Color(Integer.parseInt(System.getProperty("color", "#FFFFFF").substring(1), 16));
		}
		catch (final NumberFormatException e) {
		}
	}

	private class DefaultKeyAdapter extends KeyAdapter {
		public DefaultKeyAdapter() {
		}

		@Override
		public void keyPressed(final KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE: {
					renderer.dispose();
					device.setFullScreenWindow(null);
					break;
				}
				default:
					break;
			}
		}
	}

	private class DefaultWindowAdapter extends WindowAdapter {
		public DefaultWindowAdapter() {
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			renderer.dispose();
			device.setFullScreenWindow(null);
		}
	}
}
