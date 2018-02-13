/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.core.CoreFactory;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.ImageComposer;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.Metadata;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;

import java.util.concurrent.ThreadFactory;

public class MandelbrotFactory implements CoreFactory {
	public static final String PLUGIN_ID = "Mandelbrot";
	public static final String GRAMMAR = "Mandelbrot";

	/**
	 * @see CoreFactory#getId()
	 */
	public String getId() {
		return PLUGIN_ID;
	}

	public String getGrammar() {
		return GRAMMAR;
	}

	/**
	 * @see CoreFactory#createSession()
	 */
	@Override
	public Session createSession() {
		return new MandelbrotSession();
	}

	@Override
	public Session createSession(String script, Metadata metadata) {
		return new MandelbrotSession(script, (MandelbrotMetadata)metadata);
	}

	/**
	 * @see CoreFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile, boolean)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile, boolean opaque) {
		return new MandelbrotImageGenerator(threadFactory, renderFactory, tile, opaque);
	}

	@Override
	public ImageComposer createImageComposer(ThreadFactory threadFactory, RendererTile tile, boolean opaque) {
		return new MandelbrotImageComposer(threadFactory, tile, opaque);
	}

	@Override
	public FileManager createFileManager() {
		return new MandelbrotFileManager();
	}
}
