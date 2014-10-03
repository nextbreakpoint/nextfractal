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
package com.nextbreakpoint.nextfractal.core.media.gfx;

public final class Lowlevel {
	static public boolean debug = false;

	private Lowlevel() {
	}

	public static void mix(final int[] s, final int[] t, final int[] d, final int f, final int w, final int h) {
		int rs = 0;
		int gs = 0;
		int bs = 0;
		int rd = 0;
		int gd = 0;
		int bd = 0;
		int rgb = 0;
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > s.length) || (s.length != t.length) || (t.length != d.length) || (f < 0) || (f > 255)) {
				throw new Error("error in Lowlevel.mix(int[] s,int[] t,int[] d,int f,int w,int h)!!!");
			}
		}
		for (int i = 0; i < (w * h); i++) {
			rgb = s[i];
			rs = 0xFF & (rgb >> 16);
			gs = 0xFF & (rgb >> 8);
			bs = 0xFF & rgb;
			rgb = t[i];
			rd = 0xFF & (rgb >> 16);
			gd = 0xFF & (rgb >> 8);
			bd = 0xFF & rgb;
			rd = rs + (((rd - rs) * f) >> 8);
			gd = gs + (((gd - gs) * f) >> 8);
			bd = bs + (((bd - bs) * f) >> 8);
			d[i] = (0xFF << 24) | (rd << 16) | (gd << 8) | bd;
		}
	}

	public static void mix(final int[] s, final int[] d, final int f, final int w, final int h) {
		int rs = 0;
		int gs = 0;
		int bs = 0;
		int rgb = 0;
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > s.length) || (s.length != d.length) || (f < 0) || (f > 255)) {
				throw new Error("error in Lowlevel.mix(int[] s,int[] d,int f,int w,int h)!!!");
			}
		}
		for (int i = 0; i < (w * h); i++) {
			rgb = s[i];
			rs = 0xFF & (rgb >> 16);
			gs = 0xFF & (rgb >> 8);
			bs = 0xFF & rgb;
			rs = ((rs * f) >> 8);
			gs = ((gs * f) >> 8);
			bs = ((bs * f) >> 8);
			d[i] = (0xFF << 24) | (rs << 16) | (gs << 8) | bs;
		}
	}

	public static void fill(final int[] d, final int rgb, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > d.length)) {
				throw new Error("error in Lowlevel.color(int[] d,int rgb,int w,int h)!!!");
			}
		}
		for (int i = 0; i < w; i++) {
			d[i] = rgb;
		}
		for (int i = 1; i < h; i++) {
			System.arraycopy(d, 0, d, i * w, w);
		}
	}

	public static void fill(final byte[] d, final byte c, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > d.length)) {
				throw new Error("error in Lowlevel.color(byte[] d,int rgb,int w,int h)!!!");
			}
		}
		for (int i = 0; i < w; i++) {
			d[i] = c;
		}
		for (int i = 1; i < h; i++) {
			System.arraycopy(d, 0, d, i * w, w);
		}
	}

	public static void copy(final byte[] s, final int[] d, final byte[] r, final byte[] g, final byte[] b, final byte[] a, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > s.length) || (s.length != d.length) || (r.length < 256) || (g.length < 256) || (b.length < 256)) {
				throw new Error("error in Lowlevel.copy(byte[] s,int[] d,byte[] r,byte[] g,byte[] b,byte[] a,int w,int h)!!!");
			}
		}
		for (int i = 0; i < (w * h); i++) {
			final int j = 0xFF & s[i];
			final int ca = 0xFF & a[j];
			final int cr = 0xFF & r[j];
			final int cg = 0xFF & g[j];
			final int cb = 0xFF & b[j];
			d[i] = (ca << 24) | (cr << 16) | (cg << 8) | cb;
		}
	}

	public static void copy(final int[] s, final int[] d, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > s.length) || (s.length != d.length)) {
				throw new Error("error in Lowlevel.copy(int[] s,int[] d,int w,int h)!!!");
			}
		}
		System.arraycopy(s, 0, d, 0, w * h);
		// for (int i = 0; i < w * h; i++)
		// {
		// d[i] = s[i];
		// }
	}

	public static void copy(final byte[] s, final byte[] d, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > s.length) || (s.length != d.length)) {
				throw new Error("error in Lowlevel.copy(byte[] s,byte[] d,int w,int h)!!!");
			}
		}
		System.arraycopy(s, 0, d, 0, w * h);
		// for (int i = 0; i < w * h; i++)
		// {
		// d[i] = s[i];
		// }
	}

	public static void copy_area(final int[] s, final int[] d, final int sx, final int sy, final int bw, final int bh, final int dx, final int dy, final int sw, final int sh, final int dw, final int dh) {
		if (Lowlevel.debug) {
			if ((sw < 0) || (sh < 0) || ((sw * sh) > s.length)) {
				throw new Error("error in Lowlevel.copy_area(int[] s,int[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((dw < 0) || (dh < 0) || ((dw * dh) > d.length)) {
				throw new Error("error in Lowlevel.copy_area(int[] s,int[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((bw < 0) || (sx < 0) || (sx > (sw - bw)) || (dx < 0) || (dx > (dw - bw))) {
				throw new Error("error in Lowlevel.copy_area(int[] s,int[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((bh < 0) || (sy < 0) || (sy > (sh - bh)) || (dy < 0) || (dy > (dh - bh))) {
				throw new Error("error in Lowlevel.copy_area(int[] s,int[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
		}
		int a = sy * sw;
		int b = dy * dw;
		for (int i = 0; i < bh; i++) {
			System.arraycopy(s, a + sx, d, b + dx, bw);
			// for (int j = 0; j < bw; j++)
			// {
			// d[j + dx + b] = s[j + sx + a];
			// }
			a += sw;
			b += dw;
		}
	}

	public static void copy_area(final byte[] s, final byte[] d, final int sx, final int sy, final int bw, final int bh, final int dx, final int dy, final int sw, final int sh, final int dw, final int dh) {
		if (Lowlevel.debug) {
			if ((sw < 0) || (sh < 0) || ((sw * sh) > s.length)) {
				throw new Error("error in Lowlevel.copy_area(byte[] s,byte[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((dw < 0) || (dh < 0) || ((dw * dh) > d.length)) {
				throw new Error("error in Lowlevel.copy_area(byte[] s,byte[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((bw < 0) || (sx < 0) || (sx > (sw - bw)) || (dx < 0) || (dx > (dw - bw))) {
				throw new Error("error in Lowlevel.copy_area(byte[] s,byte[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
			if ((bh < 0) || (sy < 0) || (sy > (sh - bh)) || (dy < 0) || (dy > (dh - bh))) {
				throw new Error("error in Lowlevel.copy_area(byte[] s,byte[] d,int sx,int sy,int bw,int bh,int dx,int dy,int sw,int sh,int dw,int dh)!!!");
			}
		}
		int a = sy * sw;
		int b = dy * dw;
		for (int i = 0; i < bh; i++) {
			System.arraycopy(s, a + sx, d, b + dx, bw);
			// for (int j = 0; j < bw; j++)
			// {
			// d[j + dx + b] = s[j + sx + a];
			// }
			a += sw;
			b += dw;
		}
	}

	public static void flip_vertical(final int[] s, final int[] d, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > d.length)) {
				throw new Error("error in Lowlevel.flip_vertical(int[] s,int[] d,int w,int h)!!!");
			}
		}
		int a = 0;
		int b = (h - 1) * w;
		for (int i = 0; i < h; i++) {
			System.arraycopy(s, a, d, b, w);
			// for (int j = 0; j < w; j++)
			// {
			// d[b + j] = s[a + j];
			// }
			a += w;
			b -= w;
		}
	}

	public static void flip_vertical(final byte[] s, final byte[] d, final int w, final int h) {
		if (Lowlevel.debug) {
			if ((w < 0) || (h < 0) || ((w * h) > d.length)) {
				throw new Error("error in Lowlevel.flip_vertical(byte[] s,byte[] d,int w,int h)!!!");
			}
		}
		int a = 0;
		int b = (h - 1) * w;
		for (int i = 0; i < h; i++) {
			System.arraycopy(s, a, d, b, w);
			// for (int j = 0; j < w; j++)
			// {
			// d[b + j] = s[a + j];
			// }
			a += w;
			b -= w;
		}
	}

	public static int make_color(final int r, final int g, final int b, final int a) {
		return ((a << 24) | ((0xFF & r) << 16) | ((0xFF & g) << 8) | (0xFF & b));
	}
}
