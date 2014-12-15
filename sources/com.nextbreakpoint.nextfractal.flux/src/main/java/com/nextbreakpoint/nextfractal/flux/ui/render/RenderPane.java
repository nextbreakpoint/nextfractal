package com.nextbreakpoint.nextfractal.flux.ui.render;

import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.util.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererFractal;
import com.nextbreakpoint.nextfractal.flux.render.javaFX.JavaFXRenderFactory;

public class RenderPane extends BorderPane {
	private JavaFXRenderFactory renderFactory;
	private ThreadFactory threadFactory;
	private RendererFractal rendererFractal;
	private AnimationTimer timer;
	private Renderer renderer;
	private int width;
	private int height;
	
	public RenderPane(int width, int height) {
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
//				if ((time - last) > 20 && runtime != null && runtime.isChanged()) {
//					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
//					//runtime.getFrameElement().getLayer(0).getLayer(0).getImage().getImageRuntime().drawImage(gc);
//					last = time;
//				}
			}
		};
		timer.start();
	}

	public RendererFractal getRendererFractal() {
		return rendererFractal;
	}

	public void setRendererFractal(RendererFractal rendererFractal) {
		if (renderer != null) {
			renderer.stopRender();
			renderer.stop();
			renderer.dispose();
			renderer = null;
		}
		this.rendererFractal = rendererFractal;
		renderer = new Renderer(threadFactory, rendererFractal, width, height);
		renderer.start();
		renderer.startRender(false, 0);
	}
}
