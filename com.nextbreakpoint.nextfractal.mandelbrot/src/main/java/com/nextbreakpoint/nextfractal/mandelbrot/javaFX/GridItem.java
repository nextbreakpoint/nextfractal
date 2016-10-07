/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;

import java.io.File;
import java.util.concurrent.Future;

public class GridItem {
	private volatile long lastChanged;
	private volatile File file;
	private volatile MandelbrotData data;
	private volatile CompilerBuilder<Color> colorBuilder;
	private volatile CompilerBuilder<Orbit> orbitBuilder;
	private volatile RendererCoordinator coordinator;
	private volatile Future<GridItem> future;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		lastChanged = System.currentTimeMillis();
		this.file = file;
	}

	public MandelbrotData getData() {
		return data;
	}

	public void setData(MandelbrotData data) {
		lastChanged = System.currentTimeMillis();
		this.data = data;
	}

	public CompilerBuilder<Color> getColorBuilder() {
		return colorBuilder;
	}

	public void setColorBuilder(CompilerBuilder<Color> colorBuilder) {
		lastChanged = System.currentTimeMillis();
		this.colorBuilder = colorBuilder;
	}

	public CompilerBuilder<Orbit> getOrbitBuilder() {
		return orbitBuilder;
	}

	public void setOrbitBuilder(CompilerBuilder<Orbit> orbitBuilder) {
		lastChanged = System.currentTimeMillis();
		this.orbitBuilder = orbitBuilder;
	}

	public RendererCoordinator getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(RendererCoordinator coordinator) {
		lastChanged = System.currentTimeMillis();
		this.coordinator = coordinator;
	}

	public Future<GridItem> getFuture() {
		return future;
	}

	public void setFuture(Future<GridItem> future) {
		lastChanged = System.currentTimeMillis();
		this.future = future;
	}

	public long getLastChanged() {
		return lastChanged;
	}
}
