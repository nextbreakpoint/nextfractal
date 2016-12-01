/*
 * NextFractal 1.3.0
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
package com.nextbreakpoint.nextfractal.contextfree;

import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.contextfree.javaFX.EditorPane;
import com.nextbreakpoint.nextfractal.contextfree.javaFX.ParamsPane;
import com.nextbreakpoint.nextfractal.contextfree.javaFX.RenderPane;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.session.Session;

public class ContextFreeFactory implements FractalFactory {
	public static final String PLUGIN_ID = "ContextFree";

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#getId()
	 */
	public String getId() {
		return PLUGIN_ID;
	}

	public String getGrammar() {
		return "ContextFree";
	}

	/**
	 * @see FractalFactory#createSession()
     */
	@Override
	public Session createSession() {
		return new ContextFreeSession();
	}
	
	/**
	 * @see FractalFactory#createEditorPane(EventBus, Session)
	 */
	@Override
	public Pane createEditorPane(EventBus eventBus, Session session) {
		return new EditorPane(eventBus);
	}

	/**
	 * @see FractalFactory#createRenderPane(EventBus, Session, int, int)
	 */
	@Override
	public Pane createRenderPane(EventBus eventBus, Session session, int width, int height) {
		return new RenderPane(session, eventBus, width, height, 1, 1);
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile, boolean)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile, boolean opaque) {
		return new ContextFreeImageGenerator(threadFactory, renderFactory, tile, opaque);
	}

	@Override
	public FileManager createFileManager() {
		return new ContextFreeFileManager();
	}

	@Override
	public Pane createParamsPane(EventBus eventBus, Session session) {
		return new ParamsPane((ContextFreeSession) session, eventBus);
	}
}
