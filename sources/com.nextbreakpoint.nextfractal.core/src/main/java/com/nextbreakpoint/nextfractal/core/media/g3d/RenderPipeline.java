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

import java.util.Enumeration;

public final class RenderPipeline {
	private static final Matrix mu = Matrix.unit;
	public boolean debug = false;
	public boolean shading = true;
	public boolean flat_shading = true;
	public boolean texture_mapping = true;
	public boolean auto_animate = true;
	private Triangle[] triangles;
	private final java.util.Vector<Triangle> opaqueQueue = new java.util.Vector<Triangle>();
	private final java.util.Vector<Triangle> transparentQueue = new java.util.Vector<Triangle>();
	private Camera camera;
	private Scene scene;
	private Screen screen;
	private Texture texture;
	private Lightmap lightmap;
	private Rasterizer rasterizer;
	int[] pixels;
	int[] zbuffer;
	int[] idbuffer;
	int[][] diffuse;
	int[][] specular;
	int center_x;
	int center_y;
	int w;
	int h;

	public RenderPipeline() {
		setRasterizer(new DefaultRasterizer());
	}

	public RenderPipeline(final Rasterizer rasterizer) {
		setRasterizer(rasterizer);
	}

	public void setRasterizer(final Rasterizer rasterizer) {
		if (rasterizer == null) {
			throw new IllegalArgumentException("illegal argument ! [rasterizer == null]");
		}
		this.rasterizer = rasterizer;
		rasterizer.pipeline = this;
	}

	public void animate() {
		final boolean a = auto_animate;
		auto_animate = true;
		this.animate(scene);
		auto_animate = a;
	}

	private void animate(final Assembly assembly) {
		if (assembly != null) {
			if ((assembly.handler != null) && auto_animate) {
				assembly.handler.animate();
			}
			Solid solid = null;
			final Enumeration<Solid> solids = assembly.getSolids();
			while (solids.hasMoreElements()) {
				solid = solids.nextElement();
				if (solid instanceof Assembly) {
					this.animate((Assembly) solid);
				}
				else if (solid instanceof Part) {
					this.animate((Part) solid);
				}
			}
		}
	}

	private void animate(final Part part) {
		if ((part.handler != null) && auto_animate) {
			part.handler.animate();
		}
	}

	public void init(final Camera camera, final Scene scene) {
		if (camera == null) {
			throw new IllegalArgumentException("illegal argument ! [camera == null]");
		}
		if (scene == null) {
			throw new IllegalArgumentException("illegal argument ! [scene == null]");
		}
		this.scene = scene;
		this.camera = camera;
	}

	public void render() {
		screen = camera.getScreen();
		texture = scene.getEnvironment().texture;
		if (texture != null) {
			screen.clear(texture);
		}
		else {
			screen.clear(scene.getEnvironment().background);
		}
		pixels = screen.getBuffer();
		zbuffer = screen.getZBuffer();
		idbuffer = screen.getIDBuffer();
		w = screen.getWidth();
		h = screen.getHeight();
		center_x = w >> 1;
		center_y = h >> 1;
		scene.rebuildLightmap();
		lightmap = scene.getLightmap();
		specular = lightmap.specular;
		diffuse = lightmap.diffuse;
		scene.rebuild();
		scene.project(RenderPipeline.mu, camera.mp, camera.vx, camera.vy, camera.vz);
		emptyQueues();
		this.enqueueTriangles(scene);
		triangles = getOpaqueQueue();
		if (triangles != null) {
			for (int i = triangles.length - 1; i >= 0; i--) {
				rasterizer.render_triangle(triangles[i], triangles[i].parent.material);
			}
		}
		triangles = getTransparentQueue();
		if (triangles != null) {
			for (final Triangle element : triangles) {
				rasterizer.render_triangle(element, element.parent.material);
			}
		}
	}

	private void enqueueTriangles(final Assembly assembly) {
		if ((assembly.handler != null) && auto_animate) {
			assembly.handler.animate();
		}
		if (!assembly.hidden) {
			Solid solid = null;
			final Enumeration<Solid> solids = assembly.getSolids();
			while (solids.hasMoreElements()) {
				solid = solids.nextElement();
				if (solid instanceof Assembly) {
					this.enqueueTriangles((Assembly) solid);
				}
				else if (solid instanceof Part) {
					this.enqueueTriangles((Part) solid);
				}
			}
		}
		else {
			this.animate(assembly);
		}
	}

	private void enqueueTriangles(final Part part) {
		if ((part.handler != null) && auto_animate) {
			part.handler.animate();
		}
		if (!part.hidden) {
			for (final Triangle element : part.triangles) {
				enqueueTriangle(element, part.material);
			}
		}
	}

	private void enqueueTriangle(final Triangle triangle, final Material material) {
		if ((material == null) || (triangle.visible == false)) {
			return;
		}
		if ((material.transparency == 255) && (material.reflectivity == 0)) {
			return;
		}
		if (material.transparency > 0) {
			transparentQueue.addElement(triangle);
		}
		else {
			opaqueQueue.addElement(triangle);
		}
	}

	private void emptyQueues() {
		opaqueQueue.removeAllElements();
		transparentQueue.removeAllElements();
	}

	private Triangle[] getOpaqueQueue() {
		if (opaqueQueue.size() == 0) {
			return (null);
		}
		final Triangle[] triangles = new Triangle[opaqueQueue.size()];
		final Enumeration<Triangle> e = opaqueQueue.elements();
		int i = 0;
		while (e.hasMoreElements()) {
			triangles[i++] = e.nextElement();
		}
		return (sortTriangles(triangles, 0, triangles.length - 1));
	}

	private Triangle[] getTransparentQueue() {
		if (transparentQueue.size() == 0) {
			return (null);
		}
		final Triangle[] triangles = new Triangle[transparentQueue.size()];
		final Enumeration<Triangle> e = transparentQueue.elements();
		int i = 0;
		while (e.hasMoreElements()) {
			triangles[i++] = e.nextElement();
		}
		return (sortTriangles(triangles, 0, triangles.length - 1));
	}

	private Triangle[] sortTriangles(final Triangle[] triangles, final int l, final int r) {
		final float m = (triangles[l].distance + triangles[r].distance) / 2f;
		Triangle t = null;
		int i = l;
		int j = r;
		do {
			while (triangles[i].distance > m) {
				i++;
			}
			while (triangles[j].distance < m) {
				j--;
			}
			if (i <= j) {
				t = triangles[i];
				triangles[i] = triangles[j];
				triangles[j] = t;
				i++;
				j--;
			}
		}
		while (j >= i);
		if (l < j) {
			sortTriangles(triangles, l, j);
		}
		if (r > i) {
			sortTriangles(triangles, i, r);
		}
		return (triangles);
	}
}
