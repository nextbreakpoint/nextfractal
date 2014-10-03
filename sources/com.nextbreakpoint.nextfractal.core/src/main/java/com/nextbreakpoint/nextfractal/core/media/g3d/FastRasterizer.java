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

public class FastRasterizer extends Rasterizer {
	private Texture material_texture;
	private int material_transparency;
	private int material_reflectivity;
	private int material_color;
	private int[] texture_pixels;
	private int texture_width_mask;
	private int texture_height_mask;
	private int texture_wsize;
	private int triangle_id;
	private int[] pixels;
	private int[] zbuffer;
	private int[] idbuffer;
	private int[][] diffuse;
	private int[][] specular;
	private Vertex v;
	private Vertex p1;
	private Vertex p2;
	private Vertex p3;
	private int center_x;
	private int center_y;
	private int x1;
	private int x2;
	private int x3;
	private int x4;
	private int y1;
	private int y2;
	private int y3;
	private int z4;
	private int z1;
	private int z2;
	private int z3;
	private int nx1;
	private int nx2;
	private int nx3;
	private int nx4;
	private int ny1;
	private int ny2;
	private int ny3;
	private int ny4;
	private int tx1;
	private int tx2;
	private int tx3;
	private int tx4;
	private int ty1;
	private int ty2;
	private int ty3;
	private int ty4;
	private int dx;
	private int dy;
	private int dz;
	private int dnx;
	private int dny;
	private int dtx;
	private int dty;
	private int dxL;
	private int dxR;
	private int dzBase;
	private int dnxBase;
	private int dnyBase;
	private int dtxBase;
	private int dtyBase;
	private int xL;
	private int xR;
	private int xMax;
	private int xBase;
	private int zBase;
	private int nxBase;
	private int nyBase;
	private int txBase;
	private int tyBase;
	private int x;
	private int y;
	private int z;
	private int nx;
	private int ny;
	private int tx;
	private int ty;
	private int cnx;
	private int cny;
	private int cnz;
	private int offset;
	private int lutid;
	private int lutz;
	private int j;
	private int t;
	private int c;
	private int s;
	private int w;
	private int h;

	public FastRasterizer() {
	}

	@Override
	protected void render_triangle(final Triangle triangle, final Material material) {
		material_transparency = material.transparency;
		material_reflectivity = material.reflectivity;
		material_texture = material.texture;
		material_color = material.color;
		if (material_texture != null) {
			texture_pixels = material_texture.pixels;
			texture_width_mask = material_texture.width - 1;
			texture_height_mask = material_texture.height - 1;
			texture_wsize = material_texture.wsize;
		}
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
		if ((z1 < 6553) || (z2 < 6553) || (z3 < 6553)) {
			return;
		}
		nx1 = p1.nx;
		nx2 = p2.nx;
		nx3 = p3.nx;
		ny1 = p1.ny;
		ny2 = p2.ny;
		ny3 = p3.ny;
		tx1 = p1.tx;
		tx2 = p2.tx;
		tx3 = p3.tx;
		ty1 = p1.ty;
		ty2 = p2.ty;
		ty3 = p3.ty;
		cnx = triangle.nx;
		cny = triangle.ny;
		cnz = triangle.nz;
		if (triangle.cosine > 0) {
			nx1 = 254 - nx1;
			ny1 = 254 - ny1;
			nx2 = 254 - nx2;
			ny2 = 254 - ny2;
			nx3 = 254 - nx3;
			ny3 = 254 - ny3;
			cnx = 254 - cnx;
			cny = 254 - cny;
			cnz = -cnz;
		}
		diffuse = pipeline.diffuse;
		specular = pipeline.specular;
		if (cnz > 0) {
			lutz = 0;
		}
		else {
			lutz = 1;
		}
		x1 <<= 8;
		x2 <<= 8;
		x3 <<= 8;
		nx1 <<= 8;
		nx2 <<= 8;
		nx3 <<= 8;
		ny1 <<= 8;
		ny2 <<= 8;
		ny3 <<= 8;
		tx1 <<= 8;
		tx2 <<= 8;
		tx3 <<= 8;
		ty1 <<= 8;
		ty2 <<= 8;
		ty3 <<= 8;
		x4 = x1 + (((x3 - x1) * (y2 - y1)) / (y3 - y1));
		z4 = z1 + (((z3 - z1) * (y2 - y1)) / (y3 - y1));
		nx4 = nx1 + (((nx3 - nx1) * (y2 - y1)) / (y3 - y1));
		ny4 = ny1 + (((ny3 - ny1) * (y2 - y1)) / (y3 - y1));
		tx4 = tx1 + (((tx3 - tx1) * (y2 - y1)) / (y3 - y1));
		ty4 = ty1 + (((ty3 - ty1) * (y2 - y1)) / (y3 - y1));
		x1 <<= 8;
		x2 <<= 8;
		x3 <<= 8;
		x4 <<= 8;
		nx1 <<= 8;
		nx2 <<= 8;
		nx3 <<= 8;
		nx4 <<= 8;
		ny1 <<= 8;
		ny2 <<= 8;
		ny3 <<= 8;
		ny4 <<= 8;
		tx1 <<= 8;
		tx2 <<= 8;
		tx3 <<= 8;
		tx4 <<= 8;
		ty1 <<= 8;
		ty2 <<= 8;
		ty3 <<= 8;
		ty4 <<= 8;
		dx = (x4 - x2) >> 16;
		if (dx == 0) {
			return;
		}
		dz = (z4 - z2) / dx;
		dnx = (nx4 - nx2) / dx;
		dny = (ny4 - ny2) / dx;
		dtx = (tx4 - tx2) / dx;
		dty = (ty4 - ty2) / dx;
		dxL = 0;
		dxR = 0;
		dzBase = 0;
		dnxBase = 0;
		dnyBase = 0;
		dtxBase = 0;
		dtyBase = 0;
		if (dx < 0) {
			t = x2;
			x2 = x4;
			x4 = t;
			t = z2;
			z2 = z4;
			z4 = t;
			t = nx2;
			nx2 = nx4;
			nx4 = t;
			t = ny2;
			ny2 = ny4;
			ny4 = t;
			t = tx2;
			tx2 = tx4;
			tx4 = t;
			t = ty2;
			ty2 = ty4;
			ty4 = t;
		}
		if (y2 >= 0) {
			dy = y2 - y1;
			if (dy != 0) {
				dxL = (x2 - x1) / dy;
				dxR = (x4 - x1) / dy;
				dzBase = (z2 - z1) / dy;
				dnxBase = (nx2 - nx1) / dy;
				dnyBase = (ny2 - ny1) / dy;
				dtxBase = (tx2 - tx1) / dy;
				dtyBase = (ty2 - ty1) / dy;
			}
			xMax = x1;
			xBase = x1;
			zBase = z1;
			nxBase = nx1;
			nyBase = ny1;
			txBase = tx1;
			tyBase = ty1;
			if (y1 < 0) {
				xMax -= (y1 * dxR);
				xBase -= (y1 * dxL);
				zBase -= (y1 * dzBase);
				nxBase -= (y1 * dnxBase);
				nyBase -= (y1 * dnyBase);
				txBase -= (y1 * dtxBase);
				tyBase -= (y1 * dtyBase);
				y1 = 0;
			}
			offset = y1 * w;
			y2 = (y2 < h) ? y2 : h;
			for (y = y1; y < y2; y++) {
				xL = xBase >> 16;
				xR = xMax >> 16;
				z = zBase;
				nx = nxBase;
				ny = nyBase;
				tx = txBase;
				ty = tyBase;
				if (xL < 0) {
					z -= (xL * dz);
					nx -= (xL * dnx);
					ny -= (xL * dny);
					tx -= (xL * dtx);
					ty -= (xL * dty);
					xL = 0;
				}
				xR = (xR < w) ? xR : w;
				render_line(xL, xR);
				offset += w;
				xMax += dxR;
				xBase += dxL;
				zBase += dzBase;
				nxBase += dnxBase;
				nyBase += dnyBase;
				txBase += dtxBase;
				tyBase += dtyBase;
			}
		}
		if (y2 < h) {
			dy = y3 - y2;
			if (dy != 0) {
				dxL = (x3 - x2) / dy;
				dxR = (x3 - x4) / dy;
				dzBase = (z3 - z2) / dy;
				dnxBase = (nx3 - nx2) / dy;
				dnyBase = (ny3 - ny2) / dy;
				dtxBase = (tx3 - tx2) / dy;
				dtyBase = (ty3 - ty2) / dy;
			}
			xMax = x4;
			xBase = x2;
			zBase = z2;
			nxBase = nx2;
			nyBase = ny2;
			txBase = tx2;
			tyBase = ty2;
			if (y2 < 0) {
				xMax -= (y2 * dxR);
				xBase -= (y2 * dxL);
				zBase -= (y2 * dzBase);
				nxBase -= (y2 * dnxBase);
				nyBase -= (y2 * dnyBase);
				txBase -= (y2 * dtxBase);
				tyBase -= (y2 * dtyBase);
				y2 = 0;
			}
			offset = y2 * w;
			y3 = (y3 < h) ? y3 : h;
			for (y = y2; y < y3; y++) {
				xL = xBase >> 16;
				xR = xMax >> 16;
				z = zBase;
				nx = nxBase;
				ny = nyBase;
				tx = txBase;
				ty = tyBase;
				if (xL < 0) {
					z -= (xL * dz);
					nx -= (xL * dnx);
					ny -= (xL * dny);
					tx -= (xL * dtx);
					ty -= (xL * dty);
					xL = 0;
				}
				xR = (xR < w) ? xR : w;
				render_line(xL, xR);
				offset += w;
				xMax += dxR;
				xBase += dxL;
				zBase += dzBase;
				nxBase += dnxBase;
				nyBase += dnyBase;
				txBase += dtxBase;
				tyBase += dtyBase;
			}
		}
	}

	private void render_line(final int xL, final int xR) {
		if (pipeline.flat_shading) {
			if (pipeline.texture_mapping && (material_texture != null)) {
				render_lineFSTM(xL, xR);
			}
			else {
				render_lineFS(xL, xR);
			}
		}
		else {
			if (pipeline.texture_mapping && (material_texture != null)) {
				render_lineGSTM(xL, xR);
			}
			else {
				render_lineGS(xL, xR);
			}
		}
	}

	private void render_lineFSTM(final int xL, final int xR) {
		for (x = xL; x < xR; x++) {
			j = x + offset;
			if (z < zbuffer[j]) {
				c = texture_pixels[((tx >> 16) & texture_width_mask) + (((ty >> 16) & texture_height_mask) << texture_wsize)];
				lutid = (cnx & 255) + ((cny & 255) << 8);
				c = Color.mul(c, diffuse[lutz][lutid]);
				c = Color.mix(c, pixels[j], material_transparency);
				s = Color.scale(specular[lutz][lutid], material_reflectivity);
				c = Color.add(c, s);
				zbuffer[j] = z;
				pixels[j] = c;
				idbuffer[j] = triangle_id;
			}
			z += dz;
			tx += dtx;
			ty += dty;
		}
	}

	private void render_lineFS(final int xL, final int xR) {
		for (x = xL; x < xR; x++) {
			j = x + offset;
			if (z < zbuffer[j]) {
				c = material_color;
				lutid = (cnx & 255) + ((cny & 255) << 8);
				c = Color.mul(c, diffuse[lutz][lutid]);
				c = Color.mix(c, pixels[j], material_transparency);
				s = Color.scale(specular[lutz][lutid], material_reflectivity);
				c = Color.add(c, s);
				zbuffer[j] = z;
				pixels[j] = c;
				idbuffer[j] = triangle_id;
			}
			z += dz;
		}
	}

	private void render_lineGSTM(final int xL, final int xR) {
		for (x = xL; x < xR; x++) {
			j = x + offset;
			if (z < zbuffer[j]) {
				c = texture_pixels[((tx >> 16) & texture_width_mask) + (((ty >> 16) & texture_height_mask) << texture_wsize)];
				lutid = ((nx >> 16) & 255) + (((ny >> 16) & 255) << 8);
				c = Color.mul(c, diffuse[lutz][lutid]);
				c = Color.mix(c, pixels[j], material_transparency);
				s = Color.scale(specular[lutz][lutid], material_reflectivity);
				c = Color.add(c, s);
				zbuffer[j] = z;
				pixels[j] = c;
				idbuffer[j] = triangle_id;
			}
			z += dz;
			nx += dnx;
			ny += dny;
			tx += dtx;
			ty += dty;
		}
	}

	private void render_lineGS(final int xL, final int xR) {
		for (x = xL; x < xR; x++) {
			j = x + offset;
			if (z < zbuffer[j]) {
				c = material_color;
				lutid = ((nx >> 16) & 255) + (((ny >> 16) & 255) << 8);
				c = Color.mul(c, diffuse[lutz][lutid]);
				c = Color.mix(c, pixels[j], material_transparency);
				s = Color.scale(specular[lutz][lutid], material_reflectivity);
				c = Color.add(c, s);
				zbuffer[j] = z;
				pixels[j] = c;
				idbuffer[j] = triangle_id;
			}
			z += dz;
			nx += dnx;
			ny += dny;
		}
	}
}
