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
package com.nextbreakpoint.nextfractal.core.media.gfx;

public final class EffectFactory {
	private EffectFactory() {
	}

	public static void waterFX(final WaterData FX, final int[] s, final int[] d, final int f, final int w, final int h) {
		if ((w > 0) && (h > 0) && (f >= 0) && (f < 64)) {
			for (int i = 0; i < h; i++) {
				final int j = FX.data[f][i];
				if ((i + j) < 0) {
					final int a = i * w;
					System.arraycopy(s, a, d, 0, w);
				}
				else if ((i + j) < h) {
					final int b = i * w;
					final int a = (j * w) + b;
					System.arraycopy(s, a, d, b, w);
				}
			}
		}
	}

	public static void waterFX(final WaterData FX, final byte[] s, final byte[] d, final int f, final int w, final int h) {
		if ((w > 0) && (h > 0) && (f >= 0) && (f < 64)) {
			for (int i = 0; i < h; i++) {
				final int j = FX.data[f][i];
				if ((i + j) < 0) {
					final int a = i * w;
					System.arraycopy(s, a, d, 0, w);
				}
				else if ((i + j) < h) {
					final int b = i * w;
					final int a = (j * w) + b;
					System.arraycopy(s, a, d, b, w);
				}
			}
		}
	}

	public static void spotFX(final SpotData FX, final int[] f, final int[] s, final int[] d, final int image_w, final int image_h) {
		int a;
		int b;
		int a1;
		int r1;
		int g1;
		int b1;
		// int a2;
		int r2;
		int g2;
		int b2;
		int a3;
		int r3;
		int g3;
		int b3;
		int rgb1 = 0;
		int rgb2 = 0;
		int rgb3 = 0;
		a = 0;
		for (int i = 0; i < image_h; i++) {
			for (int j = 0; j < image_w; j++) {
				b = a + j;
				rgb1 = d[b];
				a1 = 0xFF & (rgb1 >> 24);
				r1 = 0xFF & (rgb1 >> 16);
				g1 = 0xFF & (rgb1 >> 8);
				b1 = 0xFF & (rgb1 >> 0);
				a1 = (a1 > 1) ? (a1 - 1) : 0;
				r1 = (r1 > 1) ? (r1 - 1) : 0;
				g1 = (g1 > 1) ? (g1 - 1) : 0;
				b1 = (b1 > 1) ? (b1 - 1) : 0;
				d[b] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
				rgb2 = s[b];
				// a2 = 0xFF & (rgb2 >> 24);
				r2 = 0xFF & (rgb2 >> 16);
				g2 = 0xFF & (rgb2 >> 8);
				b2 = 0xFF & (rgb2 >> 0);
				rgb3 = f[b];
				a3 = 0xFF & (rgb3 >> 24);
				r3 = 0xFF & (rgb3 >> 16);
				g3 = 0xFF & (rgb3 >> 8);
				b3 = 0xFF & (rgb3 >> 0);
				// a3 = ((a2 * a3) + (a1 * (255 - a3))) >> 8;
				// r3 = ((r2 * r3) + (r1 * (255 - r3))) >> 8;
				// g3 = ((g2 * g3) + (g1 * (255 - g3))) >> 8;
				// b3 = ((b2 * b3) + (b1 * (255 - b3))) >> 8;
				// d[b] = s[b] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
				a3 = 0xFF;
				r3 = ((r2 * r3) + (r1 * (255 - r3))) >> 8;
				g3 = ((g2 * g3) + (g1 * (255 - g3))) >> 8;
				b3 = ((b2 * b3) + (b1 * (255 - b3))) >> 8;
				d[b] = s[b] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
			}
			a += image_w;
		}
	}

	public static void fireFX(final FireData FX, final byte[] d, final int f, final int w, final int h) {
		if ((w > 0) && (h > 0) && (f >= 0) && (f < 256)) {
			// setup flame buffer pitch
			final int pitch = w << 1;
			// flame vertical loop
			for (int y = 1; y < (h - 4); y += 2) {
				// setup current line index
				final int line = y * w;
				// flame horizontal loop
				for (int x = 0; x < w; x++) {
					// setup pixel out index
					final int j = line + x;
					// setup pixel in index
					final int i = j + pitch;
					// read in pixels
					int l = d[i - 1];
					int t = d[i];
					int r = d[i + 1];
					int b = d[i + pitch];
					// adjust signed values
					l &= 0xFF;
					t &= 0xFF;
					r &= 0xFF;
					b &= 0xFF;
					// setup top sum
					final int top = l + t + r;
					// setup bottom sum
					final int bottom = b;
					// combine top and bottom
					int combined = (top + bottom) >> 2;
					// cool down intensity
					if (combined > 0) {
						combined -= 1;
					}
					// interpolate intensity between top and bottom
					final int interpolated = (combined + bottom) >> 1;
					// store pixels
					d[j] = (byte) combined;
					d[j + w] = (byte) interpolated;
				}
			}
			// setup flame generator start index
			final int generator = w * (h - 4);
			// update flame generator bar
			for (int x = 0; x < w; x += 4) {
				// random block color taking intensity into account
				final byte color = (byte) (Math.random() * f);
				for (int p = 0; p < 4; p++) {
					d[generator + x + p] = color;
				}
			}
			for (int y = 1; y < 4; y++) {
				Lowlevel.copy_area(d, d, 0, h - 4, w, 1, 0, h - 4 + y, w, h, w, h);
			}
		}
	}
}
