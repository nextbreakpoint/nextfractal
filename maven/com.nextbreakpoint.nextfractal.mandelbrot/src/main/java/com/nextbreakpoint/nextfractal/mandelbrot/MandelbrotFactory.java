/*
 * NextFractal 1.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.MandelbrotRenderPane;

public class MandelbrotFactory implements FractalFactory {
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#getId()
	 */
	public String getId() {
		return "Mandelbrot";
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createSession(com.nextbreakpoint.nextfractal.ExportService)
	 */
	@Override
	public Session createSession() {
		MandelbrotSession session = new MandelbrotSession();
        session.setSource(getInitialSource());
		return session;
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createEditorPane(com.nextbreakpoint.nextfractal.core.session.Session)
	 */
	@Override
	public Pane createEditorPane(Session session) {
		return new MandelbrotEditorPane(session);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createRenderPane(com.nextbreakpoint.nextfractal.core.session.Session, int, int)
	 */
	@Override
	public Pane createRenderPane(Session session, int width, int height) {
		return new MandelbrotRenderPane(session, width, height, Integer.getInteger("mandelbrot.renderer.rows", 1), Integer.getInteger("mandelbrot.renderer.cols", 1));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile) {
		return new MandelbrotImageGenerator(threadFactory, renderFactory, tile);
	}

	protected String getInitialSource() {
		try {
			return getSource("mandelbrot-default.m");
		} catch (IOException e) {
		}
		return "";
	}

	protected String getSource(String name) throws IOException {
		InputStream is = getClass().getResourceAsStream(name);
		if (is != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			return baos.toString();
		}
		return "";
	}
}
