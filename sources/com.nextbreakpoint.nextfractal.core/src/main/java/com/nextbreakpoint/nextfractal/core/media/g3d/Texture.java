/*
 * NextFractal 6.1 
 * http://nextfractal.sourceforge.net
 *
 * Copyright 2001, 2010 Andrea Medeghini
 * http://andreamedeghini.users.sourceforge.net
 *
 * This file is based on code from idx3dIII
 * Copyright 1999, 2000 Peter Walser
 * http://www.idx3d.ch/idx3d/idx3d.html
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
package com.nextbreakpoint.nextfractal.core.media.g3d;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.net.URL;

public final class Texture {
	int[] pixels;
	int wsize = 0;
	int hsize = 0;
	int width = 0;
	int height = 0;

	public Texture(final int width, final int height) {
		this(width, height, (int[]) null);
	}

	public Texture(final int width, final int height, int[] pixels) {
		if (width <= 0) {
			throw new IllegalArgumentException("illegal argument ! [width <= 0]");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("illegal argument ! [height <= 0]");
		}
		this.width = width;
		this.height = height;
		wsize = (int) (java.lang.Math.log(width) / java.lang.Math.log(2));
		hsize = (int) (java.lang.Math.log(height) / java.lang.Math.log(2));
		if (pixels == null) {
			pixels = new int[width * height];
		}
		this.pixels = pixels;
		if (pixels.length != (width * height)) {
			throw new IllegalArgumentException("illegal buffer size !");
		}
	}

	public Texture(final int width, final int height, final Image image) {
		loadTexture(width, height, image);
	}

	public Texture(final int width, final int height, final URL url, final MediaTracker tracker, final int id) {
		try {
			final Image image = Toolkit.getDefaultToolkit().getImage(url);
			tracker.addImage(image, id);
			tracker.waitForID(id);
			tracker.removeImage(image, id);
			loadTexture(width, height, image);
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public Texture(final int width, final int height, final String file, final MediaTracker tracker, final int id) {
		try {
			final Image image = Toolkit.getDefaultToolkit().getImage(file);
			tracker.addImage(image, id);
			tracker.waitForID(id);
			tracker.removeImage(image, id);
			loadTexture(width, height, image);
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void loadTexture(final int width, final int height, final Image image) {
		if (width <= 0) {
			throw new IllegalArgumentException("illegal argument ! [width <= 0]");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("illegal argument ! [height <= 0]");
		}
		if (image == null) {
			throw new IllegalArgumentException("illegal argument ! [image == null]");
		}
		this.width = width;
		this.height = height;
		wsize = (int) (java.lang.Math.log(width) / java.lang.Math.log(2));
		hsize = (int) (java.lang.Math.log(height) / java.lang.Math.log(2));
		pixels = new int[width * height];
		try {
			final Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			final PixelGrabber grabber = new PixelGrabber(scaled, 0, 0, width, height, pixels, 0, width);
			grabber.grabPixels(0);
		}
		catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public int[] getPixels() {
		return (pixels);
	}

	public int getWidth() {
		return (width);
	}

	public int getHeight() {
		return (height);
	}

	public Texture mix(final Texture texture) {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.mix(pixels[i], texture.pixels[i]);
		}
		return this;
	}

	public Texture add(final Texture texture) {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.add(pixels[i], texture.pixels[i]);
		}
		return this;
	}

	public Texture sub(final Texture texture) {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.sub(pixels[i], texture.pixels[i]);
		}
		return this;
	}

	public Texture inv() {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.inv(pixels[i]);
		}
		return this;
	}

	public Texture mul(final Texture texture) {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.mul(pixels[i], texture.pixels[i]);
		}
		return this;
	}

	public Texture toGray() {
		for (int i = (width * height) - 1; i >= 0; i--) {
			pixels[i] = Color.getGray(pixels[i]);
		}
		return this;
	}
}
