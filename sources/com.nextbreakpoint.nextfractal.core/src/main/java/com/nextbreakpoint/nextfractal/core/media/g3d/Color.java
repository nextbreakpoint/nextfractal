/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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

public final class Color {
	public static final int MASK7Bit = 0xFEFEFF;
	public static final int ALPHA = 0xFF000000;
	public static final int RGB = 0xFFFFFF;

	private Color() {
	}

	public static int getAlpha(final int c) {
		return ((c >> 24) & 255);
	}

	public static int getRed(final int c) {
		return ((c >> 16) & 255);
	}

	public static int getGreen(final int c) {
		return ((c >> 8) & 255);
	}

	public static int getBlue(final int c) {
		return ((c >> 0) & 255);
	}

	public static int getCropColor(final int r, final int g, final int b) {
		return Color.ALPHA | (Math.crop(r, 0, 255) << 16) | (Math.crop(g, 0, 255) << 8) | Math.crop(b, 0, 255);
	}

	public static int getCropColor(final int a, final int r, final int g, final int b) {
		return (Math.crop(a, 0, 255) << 24) | (Math.crop(r, 0, 255) << 16) | (Math.crop(g, 0, 255) << 8) | Math.crop(b, 0, 255);
	}

	public static int getColor(final int a, final int r, final int g, final int b) {
		return ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
	}

	public static int getGray(final int color) {
		final int r = ((color >> 16) & 255);
		final int g = ((color >> 8) & 255);
		final int b = (color & 255);
		final int Y = ((r * 3) + (g * 6) + b) / 10;
		return (color & 0xFF000000) | (Y << 16) | (Y << 8) | Y;
	}

	public static int getAverage(final int color) {
		return (color & 0xFF000000) | (((color >> 16) & 255) + ((color >> 8) & 255) + (color & 255)) / 3;
	}

	public static int add(final int color1, final int color2) {
		final int c = (color1 & Color.MASK7Bit) + (color2 & Color.MASK7Bit);
		int overflow = c & 0x1010100;
		overflow = overflow - (overflow >> 8);
		return (color1 & 0xFF000000) | (overflow | c);
	}

	public static int sub(final int color1, final int color2) {
		final int c = (color1 & Color.MASK7Bit) + (~color2 & Color.MASK7Bit);
		int overflow = ~c & 0x1010100;
		overflow = overflow - (overflow >> 8);
		return (color1 & 0xFF000000) | (~overflow & c);
	}

	public static int inv(final int color) {
		return (color & 0xFF000000) | (~color);
	}

	public static int scale(final int color, final int factor) {
		if (factor == 0) {
			return (color & 0xFF000000);
		}
		if (factor == 255) {
			return color;
		}
		if (factor == 127) {
			return (color & 0xFF000000) | ((color & 0xFEFEFE) >> 1);
		}
		final int r = (((color >> 16) & 255) * factor) >> 8;
		final int g = (((color >> 8) & 255) * factor) >> 8;
		final int b = ((color & 255) * factor) >> 8;
		return (color & 0xFF000000) | (r << 16) | (g << 8) | b;
	}

	public static int mul(final int color1, final int color2) {
		if ((color1 & Color.RGB) == 0) {
			return (color1 & 0xFF000000);
		}
		if ((color2 & Color.RGB) == 0) {
			return (color1 & 0xFF000000);
		}
		final int r = (((color1 >> 16) & 255) * ((color2 >> 16) & 255)) >> 8;
		final int g = (((color1 >> 8) & 255) * ((color2 >> 8) & 255)) >> 8;
		final int b = ((color1 & 255) * (color2 & 255)) >> 8;
		return (color1 & 0xFF000000) | (r << 16) | (g << 8) | b;
	}

	public static int mix(final int color1, final int color2) {
		return (color1 & 0xFF000000) | (((color1 & 0xFEFEFEFE) >> 1) + ((color2 & 0xFEFEFEFE) >> 1));
	}

	public static int mix(final int color1, final int color2, final int alpha) {
		if (alpha == 0) {
			return color1;
		}
		if (alpha == 255) {
			return color2;
		}
		if (alpha == 127) {
			return Color.mix(color1, color2);
		}
		final int r = ((alpha * (((color2 >> 16) & 255) - ((color1 >> 16) & 255))) >> 8) + ((color1 >> 16) & 255);
		final int g = ((alpha * (((color2 >> 8) & 255) - ((color1 >> 8) & 255))) >> 8) + ((color1 >> 8) & 255);
		final int b = ((alpha * ((color2 & 255) - (color1 & 255))) >> 8) + (color1 & 255);
		return (color1 & 0xFF000000) | (r << 16) | (g << 8) | b;
	}

	public static final int random() {
		return Color.ALPHA | (int) (java.lang.Math.random() * 16777216);
	}
}
