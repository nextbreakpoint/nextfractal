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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.javaFX.EditorPane;
import com.nextbreakpoint.nextfractal.contextfree.javaFX.ParamsPane;
import com.nextbreakpoint.nextfractal.contextfree.javaFX.RenderPane;
import com.nextbreakpoint.nextfractal.contextfree.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.javaFX.Bitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.Session;

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

	@Override
	public GridItemRenderer createRenderer(Bitmap bitmap) throws Exception {
		Map<String, Integer> hints = new HashMap<String, Integer>();
		RendererTile tile = createSingleTile(bitmap.getWidth(), bitmap.getHeight());
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Browser", true, Thread.MIN_PRIORITY);
		RendererCoordinator coordinator = new RendererCoordinator(threadFactory, new JavaFXRendererFactory(), tile, hints);
		CFDG cfdg = (CFDG)bitmap.getProperty("cfdg");
		coordinator.setCFDG(cfdg);
		coordinator.init();
		coordinator.run();
		return new GridItemRendererAdapter(coordinator);
	}

	@Override
	public BrowseBitmap createBitmap(Session session, RendererSize size) throws Exception {
//		ContextFreeDataStore service = new ContextFreeDataStore();
//		ContextFreeData data = service.loadFromFile(file);
//		if (Thread.currentThread().isInterrupted()) {
//			return null;
//		}
//		Compiler compiler = new Compiler();
//		CompilerReport report = compiler.compileReport(data.getSource());
//		if (report.getErrors().size() > 0) {
//			throw new RuntimeException("Failed to compile source");
//		}
//		if (Thread.currentThread().isInterrupted()) {
//			return null;
//		}
		BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), null);
//		bitmap.setProperty("cfdg", report.getCFDG());
//		bitmap.setProperty("data", data);
		bitmap.setProperty("session", session);
		return bitmap;
	}

	private RendererTile createSingleTile(int width, int height) {
		int tileWidth = width;
		int tileHeight = height;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}

	private class GridItemRendererAdapter implements GridItemRenderer {
		private RendererCoordinator coordinator;

		public GridItemRendererAdapter(RendererCoordinator coordinator) {
			this.coordinator = coordinator;
		}

		@Override
		public void abort() {
			coordinator.abort();
		}

		@Override
		public void waitFor() {
			coordinator.waitFor();
		}

		@Override
		public void dispose() {
			coordinator.dispose();
		}

		@Override
		public boolean isPixelsChanged() {
			return coordinator.isPixelsChanged();
		}

		@Override
		public void drawImage(RendererGraphicsContext gc, int x, int y) {
			coordinator.drawImage(gc, x, y);
		}
	}
}
