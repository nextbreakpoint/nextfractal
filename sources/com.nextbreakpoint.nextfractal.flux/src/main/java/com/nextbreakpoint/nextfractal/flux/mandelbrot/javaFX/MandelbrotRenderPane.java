package com.nextbreakpoint.nextfractal.flux.mandelbrot.javaFX;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.FractalSessionListener;
import com.nextbreakpoint.nextfractal.flux.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.flux.core.IntegerVector2D;
import com.nextbreakpoint.nextfractal.flux.core.Tile;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.MandelbrotFractal;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.flux.render.javaFX.JavaFXRenderFactory;

public class MandelbrotRenderPane extends BorderPane {
	private final FractalSession session;
	private JavaFXRenderFactory renderFactory;
	private ThreadFactory threadFactory;
	private RendererCoordinator rendererCoordinator;
	private AnimationTimer timer;
	private int width;
	private int height;
	
	public MandelbrotRenderPane(FractalSession session, int width, int height) {
		this.session = session;
		this.width = width;
		this.height = height;
        Canvas canvas = new Canvas(width, height);
        setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        runTimer(canvas);
		threadFactory = new DefaultThreadFactory("Render", false, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void sourceChanged(FractalSession session) {
				try {
					Compiler compiler = new Compiler(session.getPackageName(), session.getClassName(), session.getSource());
					CompilerReport report = compiler.compile();
					//TODO report errors
					MandelbrotFractal rendererFractal = new MandelbrotFractal(report.getFractal());
					setRendererFractal(rendererFractal); 
				} catch (Exception e) {
					e.printStackTrace();//TODO display errors
				}
			}

			@Override
			public void terminate(FractalSession session) {
				setRendererFractal(null); 
			}
		});
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 1000 && rendererCoordinator != null && rendererCoordinator.isChanged()) {
					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					rendererCoordinator.drawImage(gc);
					last = time;
				}
			}
		};
		timer.start();
	}

	private void setRendererFractal(MandelbrotFractal rendererFractal) {
		if (rendererCoordinator != null) {
			rendererCoordinator.dispose();
			rendererCoordinator = null;
		}
		if (rendererFractal != null) {
			Map<String, Integer> hints = new HashMap<String, Integer>();
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
			rendererCoordinator = new RendererCoordinator(threadFactory, renderFactory, rendererFractal, createTile(), hints);
			rendererCoordinator.startRender();
		}
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
