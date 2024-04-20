/*
 * NextFractal 2.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.common.Integer4D;
import com.nextbreakpoint.nextfractal.core.common.Metadata;
import com.nextbreakpoint.nextfractal.core.common.ParamsStrategy;
import com.nextbreakpoint.nextfractal.core.common.ParserStrategy;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.javafx.Bitmap;
import com.nextbreakpoint.nextfractal.core.javafx.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javafx.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.javafx.KeyHandler;
import com.nextbreakpoint.nextfractal.core.javafx.MetadataDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import com.nextbreakpoint.nextfractal.core.javafx.RenderingContext;
import com.nextbreakpoint.nextfractal.core.javafx.RenderingStrategy;
import com.nextbreakpoint.nextfractal.core.javafx.ToolContext;
import com.nextbreakpoint.nextfractal.core.javafx.UIFactory;
import com.nextbreakpoint.nextfractal.core.javafx.render.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.javafx.viewer.Toolbar;
import com.nextbreakpoint.nextfractal.core.render.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.render.RendererPoint;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import com.nextbreakpoint.nextfractal.core.render.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.DSLCompiler;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.DSLParser;
import com.nextbreakpoint.nextfractal.mandelbrot.dsl.DSLParserResult;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotMetadata;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotParamsStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotParserStrategy;
import com.nextbreakpoint.nextfractal.mandelbrot.module.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class MandelbrotUIFactory implements UIFactory {
	public static final String PLUGIN_ID = "Mandelbrot";

	@Override
	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public GridItemRenderer createRenderer(Bitmap bitmap) {
		MandelbrotSession session = (MandelbrotSession)bitmap.getProperty("session");
		Map<String, Integer> hints = new HashMap<>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		hints.put(RendererCoordinator.KEY_MULTITHREAD, RendererCoordinator.VALUE_SINGLE_THREAD);
		RendererTile tile = createRendererTile(bitmap.getWidth(), bitmap.getHeight());
		DefaultThreadFactory threadFactory = new DefaultThreadFactory("Mandelbrot Browser", true, Thread.MIN_PRIORITY);
		RendererCoordinator coordinator = new RendererCoordinator(threadFactory, new JavaFXRendererFactory(), tile, hints);
		Orbit orbit = (Orbit)bitmap.getProperty("orbit");
		Color color = (Color)bitmap.getProperty("color");
		coordinator.setOrbitAndColor(orbit, color);
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
		DSLParser parser = new DSLParser(DSLCompiler.class.getPackage().getName() + ".generated", "Compile" + System.nanoTime());
		DSLParserResult result = parser.parse(source);
		DSLCompiler compiler = new DSLCompiler();
		Orbit orbit = compiler.compileOrbit(result).create();
		Color color = compiler.compileColor(result).create();
		BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), null);
		bitmap.setProperty("orbit", orbit);
		bitmap.setProperty("color", color);
		bitmap.setProperty("session", session);
		return bitmap;
	}

	@Override
	public Try<String, Exception> loadResource(String resourceName) {
		return Try.of(() -> Objects.requireNonNull(getClass().getResource(resourceName)).toExternalForm());
	}

	@Override
	public ParserStrategy createParserStrategy() {
		return new MandelbrotParserStrategy();
	}

	@Override
	public ParamsStrategy createParamsStrategy() {
		return new MandelbrotParamsStrategy();
	}

	@Override
	public RenderingContext createRenderingContext() {
		final RenderingContext renderingContext = new RenderingContext();
		renderingContext.setZoomSpeed(1.025);
		return renderingContext;
	}

	@Override
	public MetadataDelegate createMetadataDelegate(PlatformEventBus eventBus, Supplier<Session> supplier) {
		return new MandelbrotMetadataDelegate(eventBus, supplier);
	}

	@Override
	public RenderingStrategy createRenderingStrategy(RenderingContext renderingContext, MetadataDelegate delegate, int width, int height) {
		final int[] cells = optimalRowsAndCols(Runtime.getRuntime().availableProcessors());

		return new MandelbrotRenderingStrategy(renderingContext, delegate, width, height, getRows(cells), getCols(cells));
	}

	@Override
	public KeyHandler createKeyHandler(RenderingContext renderingContext, MetadataDelegate delegate) {
		return new MandelbrotKeyHandler(renderingContext, delegate);
	}

	@Override
	public Pane createRenderingPanel(RenderingContext renderingContext, int width, int height) {
		return new MandelbrotRenderingPanel(renderingContext, width, height);
	}

	@Override
	public Toolbar createToolbar(PlatformEventBus eventBus, MetadataDelegate delegate, ToolContext<? extends Metadata> toolContext) {
		return new MandelbrotToolbar(delegate, eventBus::postEvent, (MandelbrotToolContext) toolContext);
	}

	@Override
	public ToolContext<? extends Metadata> createToolContext(RenderingContext renderingContext, RenderingStrategy renderingStrategy, MetadataDelegate delegate, int width, int height) {
		return new MandelbrotToolContext(renderingContext, (MandelbrotRenderingStrategy) renderingStrategy, delegate, width, height);
	}

	private RendererTile createRendererTile(int width, int height) {
        RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(width, height);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
        return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
	}

	private static Integer getRows(int[] cells) {
		return Integer.getInteger("mandelbrot.renderer.rows", cells[0]);
	}

	private static Integer getCols(int[] cells) {
		return Integer.getInteger("mandelbrot.renderer.cols", cells[1]);
	}

	private static int[] optimalRowsAndCols(int processors) {
		if (processors > 8) {
			return new int[] { 3, 3 };
		} else if (processors >= 4) {
			return new int[] { 2, 2 };
		} else {
			return new int[] { 1, 1 };
		}
	}

	private static class GridItemRendererAdapter implements GridItemRenderer {
		private final RendererCoordinator coordinator;

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
