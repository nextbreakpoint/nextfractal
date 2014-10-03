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
import java.util.Hashtable;

public final class Scene extends Assembly {
	private Environment environment;
	private final Lightmap lightmap;
	private final Hashtable<String, Camera> cameras;
	private final Hashtable<String, Light> lights;

	public Scene(final String name, final Environment environment) {
		super(name);
		if (environment == null) {
			throw new IllegalArgumentException("illegal argument ! [environment == null]");
		}
		lightmap = new Lightmap(environment.ambient);
		this.environment = environment;
		cameras = new Hashtable<String, Camera>();
		lights = new Hashtable<String, Light>();
		parent = null;
	}

	public void removeCamera(final Camera camera) {
		cameras.remove(camera.getName());
	}

	public void addCamera(final Camera camera) {
		if (camera.getName() != null) {
			cameras.put(camera.getName(), camera);
		}
	}

	public Camera getCamera(final String name) {
		return (cameras.get(name));
	}

	public Enumeration<Camera> getCameras() {
		return (cameras.elements());
	}

	public void removeLight(final Light light) {
		lights.remove(light.getName());
		lightmap.removeLight(light);
	}

	public void addLight(final Light light) {
		if (light.getName() != null) {
			lights.put(light.getName(), light);
			lightmap.addLight(light);
		}
	}

	public Light getLight(final String name) {
		return (lights.get(name));
	}

	public Enumeration<Light> getLights() {
		return (lights.elements());
	}

	public Lightmap getLightmap() {
		return (lightmap);
	}

	public void rebuildLightmap() {
		lightmap.rebuildLightmap();
	}

	public void build() {
		rebuild();
	}

	public void setEnvironment(final Environment environment) {
		if (environment == null) {
			throw new IllegalArgumentException("illegal argument ! [environment == null]");
		}
		lightmap.setAmbient(environment.ambient);
		this.environment = environment;
	}

	public Environment getEnvironment() {
		return (environment);
	}

	@Override
	public String toString() {
		final StringBuffer s = new StringBuffer("scene: " + name + "\r\n");
		Solid solid = null;
		final Enumeration<Solid> solids = getSolids();
		while (solids.hasMoreElements()) {
			solid = solids.nextElement();
			s.append(solid.toString());
		}
		return (s.toString());
	}
}
