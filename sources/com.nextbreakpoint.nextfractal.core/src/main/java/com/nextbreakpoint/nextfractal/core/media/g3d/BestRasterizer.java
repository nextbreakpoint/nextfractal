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

public class BestRasterizer extends Rasterizer {
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
	private float IZ;
	private float JZ;
	private float OZ;
	private float HA;
	private float VA;
	private float OA;
	private float HB;
	private float VB;
	private float OB;
	private float HC;
	private float VC;
	private float OC;
	private int center_x;
	private int center_y;
	private int x1;
	private int x2;
	private int x3;
	private int x4;
	private int y1;
	private int y2;
	private int y3;
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
	private int dnx;
	private int dny;
	private int dtx;
	private int dty;
	private int dxL;
	private int dxR;
	private int dnxBase;
	private int dnyBase;
	private int dtxBase;
	private int dtyBase;
	private int xL;
	private int xR;
	private int xD;
	private int xMax;
	private int xBase;
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
	private float ux;
	private float uy;
	private float ul;
	private float um;
	private float c1;
	private float c2;
	private float c3;
	private float z1;
	private float z2;
	private float z3;
	private float Kz;
	private float Az;
	private float Bz;
	private float Cz;
	private int stepz1;
	private int stepz2;
	private int offset;
	private final int length = 8;
	private int divisions;
	private int lutid;
	private int lutz;
	private int j;
	private int l;
	private int k;
	private int t;
	private int c;
	private int s;
	private int w;
	private int h;

	public BestRasterizer() {
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
		IZ = triangle.IZ;
		JZ = triangle.JZ;
		OZ = triangle.OZ;
		HA = triangle.HA;
		VA = triangle.VA;
		OA = triangle.OA;
		HB = triangle.HB;
		VB = triangle.VB;
		OB = triangle.OB;
		HC = triangle.HC;
		VC = triangle.VC;
		OC = triangle.OC;
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
		if ((p1.pz < 6553) || (p2.pz < 6553) || (p3.pz < 6553)) {
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
		dnx = (nx4 - nx2) / dx;
		dny = (ny4 - ny2) / dx;
		dtx = (tx4 - tx2) / dx;
		dty = (ty4 - ty2) / dx;
		dxL = 0;
		dxR = 0;
		dnxBase = 0;
		dnyBase = 0;
		dtxBase = 0;
		dtyBase = 0;
		if (dx < 0) {
			t = x2;
			x2 = x4;
			x4 = t;
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
				dnxBase = (nx2 - nx1) / dy;
				dnyBase = (ny2 - ny1) / dy;
				dtxBase = (tx2 - tx1) / dy;
				dtyBase = (ty2 - ty1) / dy;
			}
			xMax = x1;
			xBase = x1;
			nxBase = nx1;
			nyBase = ny1;
			txBase = tx1;
			tyBase = ty1;
			if (y1 < 0) {
				xMax -= (y1 * dxR);
				xBase -= (y1 * dxL);
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
				nx = nxBase;
				ny = nyBase;
				tx = txBase;
				ty = tyBase;
				if (xL < 0) {
					nx -= (xL * dnx);
					ny -= (xL * dny);
					tx -= (xL * dtx);
					ty -= (xL * dty);
					xL = 0;
				}
				xR = (xR < w) ? xR : w;
				divisions = (xR - xL) >> length;
				xD = xL + (1 << length);
				for (k = 0; k < divisions; k++) {
					render_line(xL, xD);
					xL += (1 << length);
					xD += (1 << length);
				}
				render_line(xL, xR);
				offset += w;
				xMax += dxR;
				xBase += dxL;
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
				dnxBase = (nx3 - nx2) / dy;
				dnyBase = (ny3 - ny2) / dy;
				dtxBase = (tx3 - tx2) / dy;
				dtyBase = (ty3 - ty2) / dy;
			}
			xMax = x4;
			xBase = x2;
			nxBase = nx2;
			nyBase = ny2;
			txBase = tx2;
			tyBase = ty2;
			if (y2 < 0) {
				xMax -= (y2 * dxR);
				xBase -= (y2 * dxL);
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
				nx = nxBase;
				ny = nyBase;
				tx = txBase;
				ty = tyBase;
				if (xL < 0) {
					nx -= (xL * dnx);
					ny -= (xL * dny);
					tx -= (xL * dtx);
					ty -= (xL * dty);
					xL = 0;
				}
				xR = (xR < w) ? xR : w;
				divisions = (xR - xL) >> length;
				xD = xL + (1 << length);
				for (k = 0; k < divisions; k++) {
					render_line(xL, xD);
					xL += (1 << length);
					xD += (1 << length);
				}
				render_line(xL, xR);
				offset += w;
				xMax += dxR;
				xBase += dxL;
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
		l = xR - xL;
		um = l >> 1;
		ul = l;
		ux = xL - center_x;
		uy = y - center_y;
		c1 = (ux * HA) + (uy * VA) + OA;
		c2 = (ux * HB) + (uy * VB) + OB;
		c3 = (ux * HC) + (uy * VC) + OC;
		z1 = (((c1 * IZ) + (c2 * JZ)) / c3) + OZ;
		z2 = (((((ul * HA) + c1) * IZ) + (((ul * HB) + c2) * JZ)) / ((ul * HC) + c3)) + OZ;
		z3 = (((((um * HA) + c1) * IZ) + (((um * HB) + c2) * JZ)) / ((um * HC) + c3)) + OZ;
		Kz = 2f * ((z1 + z2) - (2f * z3));
		Az = z1;
		Bz = (z2 - z1 - Kz) / ul;
		Cz = Kz / (ul * ul);
		stepz1 = (int) ((Bz + Cz) * 262144f);
		stepz2 = (int) ((Cz + Cz) * 262144f);
		z = (int) (Az * 262144f);
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
			z += stepz1;
			stepz1 += stepz2;
			tx += dtx;
			ty += dty;
		}
	}

	private void render_lineFS(final int xL, final int xR) {
		l = xR - xL;
		um = l >> 1;
		ul = l;
		ux = xL - center_x;
		uy = y - center_y;
		c1 = (ux * HA) + (uy * VA) + OA;
		c2 = (ux * HB) + (uy * VB) + OB;
		c3 = (ux * HC) + (uy * VC) + OC;
		z1 = (((c1 * IZ) + (c2 * JZ)) / c3) + OZ;
		z2 = (((((ul * HA) + c1) * IZ) + (((ul * HB) + c2) * JZ)) / ((ul * HC) + c3)) + OZ;
		z3 = (((((um * HA) + c1) * IZ) + (((um * HB) + c2) * JZ)) / ((um * HC) + c3)) + OZ;
		Kz = 2f * ((z1 + z2) - (2f * z3));
		Az = z1;
		Bz = (z2 - z1 - Kz) / ul;
		Cz = Kz / (ul * ul);
		stepz1 = (int) ((Bz + Cz) * 262144f);
		stepz2 = (int) ((Cz + Cz) * 262144f);
		z = (int) (Az * 262144f);
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
			z += stepz1;
			stepz1 += stepz2;
		}
	}

	private void render_lineGSTM(final int xL, final int xR) {
		l = xR - xL;
		um = l >> 1;
		ul = l;
		ux = xL - center_x;
		uy = y - center_y;
		c1 = (ux * HA) + (uy * VA) + OA;
		c2 = (ux * HB) + (uy * VB) + OB;
		c3 = (ux * HC) + (uy * VC) + OC;
		z1 = (((c1 * IZ) + (c2 * JZ)) / c3) + OZ;
		z2 = (((((ul * HA) + c1) * IZ) + (((ul * HB) + c2) * JZ)) / ((ul * HC) + c3)) + OZ;
		z3 = (((((um * HA) + c1) * IZ) + (((um * HB) + c2) * JZ)) / ((um * HC) + c3)) + OZ;
		Kz = 2f * ((z1 + z2) - (2f * z3));
		Az = z1;
		Bz = (z2 - z1 - Kz) / ul;
		Cz = Kz / (ul * ul);
		stepz1 = (int) ((Bz + Cz) * 262144f);
		stepz2 = (int) ((Cz + Cz) * 262144f);
		z = (int) (Az * 262144f);
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
			z += stepz1;
			stepz1 += stepz2;
			nx += dnx;
			ny += dny;
			tx += dtx;
			ty += dty;
		}
	}

	private void render_lineGS(final int xL, final int xR) {
		l = xR - xL;
		um = l >> 1;
		ul = l;
		ux = xL - center_x;
		uy = y - center_y;
		c1 = (ux * HA) + (uy * VA) + OA;
		c2 = (ux * HB) + (uy * VB) + OB;
		c3 = (ux * HC) + (uy * VC) + OC;
		z1 = (((c1 * IZ) + (c2 * JZ)) / c3) + OZ;
		z2 = (((((ul * HA) + c1) * IZ) + (((ul * HB) + c2) * JZ)) / ((ul * HC) + c3)) + OZ;
		z3 = (((((um * HA) + c1) * IZ) + (((um * HB) + c2) * JZ)) / ((um * HC) + c3)) + OZ;
		Kz = 2f * ((z1 + z2) - (2f * z3));
		Az = z1;
		Bz = (z2 - z1 - Kz) / ul;
		Cz = Kz / (ul * ul);
		stepz1 = (int) ((Bz + Cz) * 262144f);
		stepz2 = (int) ((Cz + Cz) * 262144f);
		z = (int) (Az * 262144f);
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
			z += stepz1;
			stepz1 += stepz2;
			nx += dnx;
			ny += dny;
		}
	}
}
