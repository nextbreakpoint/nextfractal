/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.javaFX.Bitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.EditorPane;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.ParamsPane;
import com.nextbreakpoint.nextfractal.mandelbrot.javaFX.RenderPane;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

public class MandelbrotFactory implements FractalFactory {
	public static final String PLUGIN_ID = "Mandelbrot";
	public static final String GRAMMAR = "Mandelbrot";

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#getId()
	 */
	public String getId() {
		return PLUGIN_ID;
	}

	public String getGrammar() {
		return GRAMMAR;
	}

	/**
	 * @see FractalFactory#createSession()
	 */
	@Override
	public Session createSession() {
		return new MandelbrotSession();
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
		return new RenderPane(session, eventBus, width, height, Integer.getInteger("mandelbrot.renderer.rows", 1), Integer.getInteger("mandelbrot.renderer.cols", 1));
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.FractalFactory#createImageGenerator(java.util.concurrent.ThreadFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererFactory, com.nextbreakpoint.nextfractal.core.renderer.RendererTile, boolean)
	 */
	@Override
	public ImageGenerator createImageGenerator(ThreadFactory threadFactory,	RendererFactory renderFactory, RendererTile tile, boolean opaque) {
		return new MandelbrotImageGenerator(threadFactory, renderFactory, tile, opaque);
	}

	@Override
	public FileManager createFileManager() {
		return new MandelbrotFileManager();
	}

	@Override
	public Pane createParamsPane(EventBus eventBus, Session session) {
		return new ParamsPane((MandelbrotSession) session, eventBus);
	}

	@Override
	public GridItemRenderer createRenderer(Bitmap bitmap) throws Exception {
		MandelbrotSession session = (MandelbrotSession)bitmap.getProperty("session");
		Map<String, Integer> hints = new HashMap<>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		hints.put(RendererCoordinator.KEY_MULTITHREAD, RendererCoordinator.VALUE_SINGLE_THREAD);
		RendererTile tile = createSingleTile(bitmap.getWidth(), bitmap.getHeight());
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Browser Renderer", true, Thread.MIN_PRIORITY);
		RendererCoordinator coordinator = new RendererCoordinator(threadFactory, new JavaFXRendererFactory(), tile, hints);
		CompilerBuilder<Orbit> orbitBuilder = (CompilerBuilder<Orbit>)bitmap.getProperty("orbit");
		CompilerBuilder<Color> colorBuilder = (CompilerBuilder<Color>)bitmap.getProperty("color");
		coordinator.setOrbitAndColor(orbitBuilder.build(), colorBuilder.build());
		coordinator.init();
		MandelbrotMetadata data = (MandelbrotMetadata) session.getMetadata();
		RendererView view = new RendererView();
		view.setTraslation(data.getTranslation());
		view.setRotation(data.getRotation());
		view.setScale(data.getScale());
		view.setState(new Integer4D(0, 0, 0, 0));
		view.setPoint(new Number(data.getPoint().getX(), data.getPoint().getY()));
		view.setJulia(data.isJulia());
		coordinator.setView(view);
		coordinator.run();
		return new GridItemRendererAdapter(coordinator);
	}

	@Override
	public BrowseBitmap createBitmap(Session session, RendererSize size) throws Exception {
		String source = session.getScript();
		Compiler compiler = new Compiler();
		CompilerReport report = compiler.compileReport(source);
		if (report.getErrors().size() > 0) {
			throw new RuntimeException("Failed to compile source");
		}
		CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
		if (orbitBuilder.getErrors().size() > 0) {
			throw new RuntimeException("Failed to compile Orbit class");
		}
		CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
		if (colorBuilder.getErrors().size() > 0) {
			throw new RuntimeException("Failed to compile Color class");
		}
		BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), null);
		bitmap.setProperty("orbit", orbitBuilder);
		bitmap.setProperty("color", colorBuilder);
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
