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

public final class Screen {
	int[] buffer;
	int[] zbuffer;
	int[] idbuffer;
	int width = 0;
	int height = 0;

	public Screen(final int width, final int height, final int[] buffer, final int[] zbuffer, final int[] idbuffer) {
		if (width <= 0) {
			throw new IllegalArgumentException("illegal argument ! [width <= 0]");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("illegal argument ! [height <= 0]");
		}
		if (buffer == null) {
			throw new IllegalArgumentException("illegal argument ! [buffer == null]");
		}
		if (zbuffer == null) {
			throw new IllegalArgumentException("illegal argument ! [zbuffer == null]");
		}
		if (idbuffer == null) {
			throw new IllegalArgumentException("illegal argument ! [idbuffer == null]");
		}
		this.width = width;
		this.height = height;
		this.buffer = buffer;
		this.zbuffer = zbuffer;
		this.idbuffer = idbuffer;
	}

	public int[] getBuffer() {
		return (buffer);
	}

	public int[] getZBuffer() {
		return (zbuffer);
	}

	public int[] getIDBuffer() {
		return (idbuffer);
	}

	public void setBuffer(final int[] buffer) {
		this.buffer = buffer;
	}

	public void setZBuffer(final int[] zbuffer) {
		this.zbuffer = zbuffer;
	}

	public void setIDBuffer(final int[] idbuffer) {
		this.idbuffer = idbuffer;
	}

	public void clear(final int rgb) {
		for (int i = 0; i < width; i++) {
			buffer[i] = rgb;
			zbuffer[i] = 0xFFFFFFF;
			idbuffer[i] = -1;
		}
		int j = width;
		for (int i = 1; i < height; i++) {
			System.arraycopy(buffer, 0, buffer, j, width);
			System.arraycopy(zbuffer, 0, zbuffer, j, width);
			System.arraycopy(idbuffer, 0, idbuffer, j, width);
			j += width;
		}
	}

	public void clear(final Texture texture) {
		if (texture != null) {
			final int h = (texture.height < height) ? texture.height : height;
			final int w = (texture.width < width) ? texture.width : width;
			final int[] background = texture.pixels;
			int j = 0;
			int k = 0;
			for (int i = 0; i < h; i++) {
				System.arraycopy(background, j, buffer, k, w);
				j += texture.width;
				k += width;
			}
		}
		else {
			for (int i = 0; i < width; i++) {
				buffer[i] = 0x00000000;
			}
			int j = width;
			for (int i = 1; i < height; i++) {
				System.arraycopy(buffer, 0, buffer, j, width);
				j += width;
			}
		}
		for (int i = 0; i < width; i++) {
			zbuffer[i] = 0xFFFFFFF;
			idbuffer[i] = -1;
		}
		int j = width;
		for (int i = 1; i < height; i++) {
			System.arraycopy(zbuffer, 0, zbuffer, j, width);
			System.arraycopy(idbuffer, 0, idbuffer, j, width);
			j += width;
		}
	}

	public int getWidth() {
		return (width);
	}

	public int getHeight() {
		return (height);
	}
}
