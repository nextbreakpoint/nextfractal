package com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.render;

import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.flux.render.javaFX.JavaFXRenderFactory;

public class MandelbrotRenderPane extends BorderPane {
	private JavaFXRenderFactory renderFactory;
	private ThreadFactory threadFactory;
	private RendererCoordinator rendererCoordinator;
	private RendererFractal rendererFractal;
	private AnimationTimer timer;
	private int width;
	private int height;
	
	public MandelbrotRenderPane(int width, int height) {
		this.width = width;
		this.height = height;
        Canvas canvas = new Canvas(width, height);
        setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        runTimer(canvas);
		threadFactory = new DefaultThreadFactory("Render", false, Thread.NORM_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 20 && rendererCoordinator != null && rendererCoordinator.isChanged()) {
					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					rendererCoordinator.drawImage(gc);
					last = time;
				}
			}
		};
		timer.start();
	}

	public RendererFractal getRendererFractal() {
		return rendererFractal;
	}

	public void setRendererFractal(RendererFractal rendererFractal) {
		if (rendererCoordinator != null) {
			rendererCoordinator.stop();
			rendererCoordinator.dispose();
			rendererCoordinator = null;
		}
		this.rendererFractal = rendererFractal;
		rendererCoordinator = new RendererCoordinator(threadFactory, renderFactory, rendererFractal, createTile());
		rendererCoordinator.start();
	}

	private Tile createTile() {
		IntegerVector2D imageSize = new IntegerVector2D(width, height);
		IntegerVector2D tileSize = new IntegerVector2D(width, height);
		IntegerVector2D tileBorder = new IntegerVector2D(0, 0);
		IntegerVector2D tileOffset = new IntegerVector2D(0, 0);
		Tile tile = new Tile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
}
