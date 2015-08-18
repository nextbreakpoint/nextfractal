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
package com.nextbreakpoint.nextfractal.core;

import java.util.concurrent.ThreadFactory;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;

public interface FractalFactory {
	/**
	 * @return
	 */
	public String getId();

	/**
	 * @return
	 */
	public Session createSession();
	
	/**
	 * @param session
	 * @return
	 */
	public Pane createEditorPane(Session session);

	/**
	 * @param session
	 * @param width
	 * @param height
	 * @return
	 */
	public Pane createRenderPane(Session session, int width, int height);

	/**
	 * @param threadFactory
	 * @param renderFactory
	 * @param tile
	 * @param opaque
	 * @return
	 */
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory, RendererFactory renderFactory, RendererTile tile, boolean opaque);
}
