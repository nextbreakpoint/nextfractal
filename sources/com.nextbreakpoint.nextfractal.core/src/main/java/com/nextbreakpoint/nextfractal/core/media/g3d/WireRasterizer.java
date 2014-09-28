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

public class WireRasterizer extends Rasterizer {
	private int material_color;
	private int triangle_id;
	private int[] pixels;
	private int[] zbuffer;
	private int[] idbuffer;
	private int[][] diffuse;
	private Vertex v;
	private Vertex p1;
	private Vertex p2;
	private Vertex p3;
	private int center_x;
	private int center_y;
	private int x1;
	private int x2;
	private int x3;
	// private int x4;
	private int y1;
	private int y2;
	private int y3;
	// private int z4;
	private int z1;
	private int z2;
	private int z3;
	private int cnx;
	private int cny;
	private int cnz;
	private int lutid;
	private int lutz;
	private int x;
	private int y;
	private int z;
	private int j;
	private int i;
	private int dx;
	private int dy;
	private int dz;
	private int sx;
	private int sy;
	private int mx;
	private int my;
	private int bx;
	private int by;
	private int c;
	private int e;
	private int w;
	private int h;

	public WireRasterizer() {
	}

	@Override
	protected void render_triangle(final Triangle triangle, final Material material) {
		material_color = material.color;
		triangle_id = triangle.id;
		pixels = pipeline.pixels;
		zbuffer = pipeline.zbuffer;
		idbuffer = pipeline.idbuffer;
		center_x = pipeline.center_x;
		center_y = pipeline.center_y;
		w = pipeline.w;
		h = pipeline.h;
		p1 = triangle.p1;
		p2 = triangle.p2;
		p3 = triangle.p3;
		if (p1.py > p2.py) {
			v = p1;
			p1 = p2;
			p2 = v;
		}
		if (p2.py > p3.py) {
			v = p2;
			p2 = p3;
			p3 = v;
		}
		if (p1.py > p2.py) {
			v = p1;
			p1 = p2;
			p2 = v;
		}
		x1 = p1.px + center_x;
		x2 = p2.px + center_x;
		x3 = p3.px + center_x;
		y1 = p1.py + center_y;
		y2 = p2.py + center_y;
		y3 = p3.py + center_y;
		if (y3 < 0) {
			return;
		}
		if (y1 >= h) {
			return;
		}
		if (y1 == y3) {
			return;
		}
		z1 = p1.pz;
		z2 = p2.pz;
		z3 = p3.pz;
		cnx = triangle.nx;
		cny = triangle.ny;
		cnz = triangle.nz;
		if (triangle.cosine > 0) {
			cnx = 254 - cnx;
			cny = 254 - cny;
			cnz = -cnz;
		}
		diffuse = pipeline.diffuse;
		if (cnz > 0) {
			lutz = 0;
		}
		else {
			lutz = 1;
		}
		if ((z1 < 6553) || (z2 < 6553) || (z3 < 6553)) {
			return;
		}
		draw_line(x1, y1, z1, x2, y2, z2);
		draw_line(x2, y2, z2, x3, y3, z3);
		draw_line(x3, y3, z3, x1, y1, z1);
	}

	private void draw_line(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
		dx = x2 - x1;
		dy = y2 - y1;
		x = x1;
		y = y1;
		sx = (dx < 0) ? (-1) : (+1);
		sy = (dy < 0) ? (-1) : (+1);
		mx = dx * sx;
		my = dy * sy;
		bx = (mx << 1);
		by = (my << 1);
		z = z1;
		if (mx > my) {
			e = by - mx;
			if (mx > 0) {
				dz = (z2 - z1) / mx;
			}
			for (i = 0; i < mx; i++) {
				if (((y >= 0) && (y < h)) && ((x >= 0) && (x < w))) {
					j = (y * w) + x;
					if (z < zbuffer[j]) {
						c = material_color;
						lutid = (cnx & 255) + ((cny & 255) << 8);
						c = Color.mul(c, diffuse[lutz][lutid]);
						zbuffer[j] = z;
						pixels[j] = c;
						idbuffer[j] = triangle_id;
					}
				}
				if ((e > 0) && (my != 0)) {
					y += sy;
					e -= bx;
				}
				x += sx;
				e += by;
				z += dz;
			}
		}
		else {
			e = bx - my;
			if (my > 0) {
				dz = (z2 - z1) / my;
			}
			for (i = 0; i < my; i++) {
				if (((y >= 0) && (y < h)) && ((x >= 0) && (x < w))) {
					j = (y * w) + x;
					if (z < zbuffer[j]) {
						c = material_color;
						lutid = (cnx & 255) + ((cny & 255) << 8);
						c = Color.mul(c, diffuse[lutz][lutid]);
						zbuffer[j] = z;
						pixels[j] = c;
						idbuffer[j] = triangle_id;
					}
				}
				if ((e > 0) && (mx != 0)) {
					x += sx;
					e -= by;
				}
				y += sy;
				e += bx;
				z += dz;
			}
		}
	}
}
