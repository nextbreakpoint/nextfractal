package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;

public class MandelbrotImage extends BorderPane {
	private DefaultThreadFactory threadFactory;
	private JavaFXRenderFactory renderFactory;
	private RendererCoordinator coordinator;
	private AnimationTimer timer;

	public MandelbrotImage(int width, int height) {
		threadFactory = new DefaultThreadFactory("Image", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
		Map<String, Integer> hints = new HashMap<String, Integer>();
		if (!Boolean.getBoolean("disableXaosRender")) {
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		}
		Canvas canvas = new Canvas();
		setCenter(canvas);
		coordinator = new RendererCoordinator(threadFactory, renderFactory, createTile(width, height), hints);
		runTimer(canvas);
	}

	private RendererTile createTile(int width, int height) {
		int tileWidth = width;
		int tileHeight = height;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				redrawCanvasIfCoordinatorsPixelsChanged(canvas);
				if ((time - last) > 25) {
					last = time;
				}
			}
		};
		timer.start();
	}

	private void redrawCanvasIfCoordinatorsPixelsChanged(Canvas canvas) {
		if (coordinator != null && coordinator.isPixelsChanged()) {
			RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			coordinator.drawImage(gc);
		}
	}
}
