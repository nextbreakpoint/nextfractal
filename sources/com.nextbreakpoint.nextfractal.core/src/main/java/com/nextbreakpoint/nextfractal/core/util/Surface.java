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
package com.nextbreakpoint.nextfractal.core.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * @author Andrea Medeghini
 */
public class Surface {
	public static int DEFAULT_TYPE = BufferedImage.TYPE_INT_ARGB;
	private BufferedImage image;
	private Graphics2D g2d;

	/**
	 * @param width
	 * @param height
	 */
	public Surface(final int width, final int height) {
		image = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
	}

	/**
	 * @param image
	 */
	public Surface(final BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return image.getHeight();
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return image.getWidth();
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @return the graphics
	 */
	public Graphics2D getGraphics2D() {
		if (g2d == null) {
			g2d = image.createGraphics();
		}
		return g2d;
	}

	/**
	 * 
	 */
	public void dispose() {
		disposeGraphics();
		if (image != null) {
			image.flush();
			image = null;
		}
	}

	/**
	 * 
	 */
	public void disposeGraphics() {
		if (g2d != null) {
			g2d.dispose();
			g2d = null;
		}
	}
}
