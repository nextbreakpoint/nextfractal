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

public final class Lightmap {
	int ambient;
	java.util.Vector<Light> lights = new java.util.Vector<Light>();
	float[] sphere = new float[65536];
	int[][] diffuse = new int[2][65536];
	int[][] specular = new int[2][65536];
	private boolean dirty = false;

	public Lightmap(final int ambient) {
		this.ambient = ambient;
		buildSphereMap();
	}

	public void removeLight(final Light light) {
		lights.removeElementAt(lights.indexOf(light));
		dirty = false;
	}

	public void addLight(final Light light) {
		lights.addElement(light);
		dirty = false;
	}

	public void setAmbient(final int ambient) {
		this.ambient = ambient;
		dirty = false;
	}

	public void rebuildLightmap() {
		if (!dirty) {
			dirty = true;
			rebuild();
		}
	}

	public void buildLightmap() {
		dirty = true;
		rebuild();
	}

	private void buildSphereMap() {
		float fnx;
		float fny;
		float fnz;
		for (int ny = -128; ny < 128; ny++) {
			fny = ny / 128f;
			for (int nx = -128; nx < 128; nx++) {
				fnx = nx / 128f;
				fnz = (float) (1f - java.lang.Math.sqrt((fnx * fnx) + (fny * fny)));
				sphere[nx + 128 + ((ny + 128) << 8)] = (fnz > 0f) ? fnz : 0f;
			}
		}
	}

	private void rebuild() {
		Light light;
		int phongfact;
		float spread;
		float sheen;
		float fnx;
		float fny;
		int lutid;
		int diffuse;
		int specular;
		int cos;
		int dr;
		int dg;
		int db;
		int sr;
		int sg;
		int sb;
		for (int ny = -128; ny < 128; ny++) {
			fny = ny / 128f;
			for (int nx = -128; nx < 128; nx++) {
				fnx = nx / 128f;
				lutid = nx + 128 + ((ny + 128) << 8);
				dr = Color.getRed(ambient);
				dg = Color.getGreen(ambient);
				db = Color.getBlue(ambient);
				sr = sg = sb = 0;
				for (int i = 0; i < lights.size(); i++) {
					light = lights.elementAt(i);
					diffuse = light.diffuse;
					specular = light.specular;
					cos = (int) (255f * Vector.cos(light.v, new Vector(fnx, fny, +sphere[lutid])));
					cos = (cos > 0) ? cos : 0;
					dr += ((Color.getRed(diffuse) * cos) >> 8);
					dg += ((Color.getGreen(diffuse) * cos) >> 8);
					db += ((Color.getBlue(diffuse) * cos) >> 8);
					sheen = light.highlightSheen / 255f;
					spread = light.highlightSpread / 4096f;
					spread = (spread < 0.01f) ? 0.01f : spread;
					phongfact = (int) (255f * sheen * (float) java.lang.Math.pow(cos / 255f, 1f / spread));
					sr += ((Color.getRed(specular) * phongfact) >> 8);
					sg += ((Color.getGreen(specular) * phongfact) >> 8);
					sb += ((Color.getBlue(specular) * phongfact) >> 8);
				}
				this.diffuse[0][lutid] = Color.getCropColor(dr, dg, db);
				this.specular[0][lutid] = Color.getCropColor(sr, sg, sb);
			}
		}
		for (int ny = -128; ny < 128; ny++) {
			fny = ny / 128f;
			for (int nx = -128; nx < 128; nx++) {
				fnx = nx / 128f;
				lutid = nx + 128 + ((ny + 128) << 8);
				dr = Color.getRed(ambient);
				dg = Color.getGreen(ambient);
				db = Color.getBlue(ambient);
				sr = sg = sb = 0;
				for (int i = 0; i < lights.size(); i++) {
					light = lights.elementAt(i);
					diffuse = light.diffuse;
					specular = light.specular;
					cos = (int) (255f * Vector.cos(light.v, new Vector(fnx, fny, -sphere[lutid])));
					cos = (cos > 0) ? cos : 0;
					dr += ((Color.getRed(diffuse) * cos) >> 8);
					dg += ((Color.getGreen(diffuse) * cos) >> 8);
					db += ((Color.getBlue(diffuse) * cos) >> 8);
					sheen = light.highlightSheen / 255f;
					spread = light.highlightSpread / 4096f;
					spread = (spread < 0.01f) ? 0.01f : spread;
					phongfact = (int) (255f * sheen * (float) java.lang.Math.pow(cos / 255f, 1f / spread));
					sr += ((Color.getRed(specular) * phongfact) >> 8);
					sg += ((Color.getGreen(specular) * phongfact) >> 8);
					sb += ((Color.getBlue(specular) * phongfact) >> 8);
				}
				this.diffuse[1][lutid] = Color.getCropColor(dr, dg, db);
				this.specular[1][lutid] = Color.getCropColor(sr, sg, sb);
			}
		}
	}
}
