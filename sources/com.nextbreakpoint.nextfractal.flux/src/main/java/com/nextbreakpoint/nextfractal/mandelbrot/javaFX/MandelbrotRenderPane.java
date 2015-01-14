package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.Tile;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;

public class MandelbrotRenderPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotRenderPane.class.getName());
	private final FractalSession session;
	private JavaFXRenderFactory renderFactory;
	private ThreadFactory threadFactory;
	private RendererCoordinator rendererCoordinator;
	private AnimationTimer timer;
	private int width;
	private int height;
	private String astOrbit;
	private String astColor;
	
	public MandelbrotRenderPane(FractalSession session, int width, int height) {
		this.session = session;
		this.width = width;
		this.height = height;
        Canvas canvas = new Canvas(width, height);
        setCenter(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(0, 0, width, height);
        runTimer(canvas);
		threadFactory = new DefaultThreadFactory("Render", false, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
		Map<String, Integer> hints = new HashMap<String, Integer>();
		if (!Boolean.getBoolean("disableXaosRender")) {
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		}
		rendererCoordinator = new RendererCoordinator(threadFactory, renderFactory, createTile(), hints);
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void sourceChanged(FractalSession session) {
				try {
					Compiler compiler = new Compiler(session.getPackageName(), session.getClassName(), session.getSource());
					CompilerReport reportOrbit = compiler.compileOrbit();
					//TODO report errors
					boolean orbitChanged = !reportOrbit.getAst().equals(astOrbit);
					astOrbit = reportOrbit.getAst();
					CompilerReport reportColor = compiler.compileColor();
					//TODO report errors
					boolean colorChanged = !reportColor.getAst().equals(astColor);
					astColor = reportColor.getAst();
					if (orbitChanged) {
						logger.info("Orbit algorithm is changed");
					}
					if (colorChanged) {
						logger.info("Color algorithm is changed");
					}
					if (Boolean.getBoolean("disableSmartRender")) {
						rendererCoordinator.setOrbitAndColor((Orbit)reportOrbit.getObject(), (Color)reportColor.getObject());
					} else {
						if (orbitChanged) {
							System.out.println("orbit changed");
							rendererCoordinator.setOrbitAndColor((Orbit)reportOrbit.getObject(), (Color)reportColor.getObject());
						} else if (colorChanged) {
							System.out.println("color changed");
							rendererCoordinator.setColor((Color)reportColor.getObject());
						}
					}
//					rendererCoordinator.setOrbitAndColor(new MandelbrotOrbit(), new MandelbrotColor());
				} catch (Exception e) {
					e.printStackTrace();//TODO display errors
				}
			}

			@Override
			public void terminate(FractalSession session) {
				rendererCoordinator.dispose();
				rendererCoordinator = null;
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

	private Tile createTile() {
		IntegerVector2D imageSize = new IntegerVector2D(width, height);
		IntegerVector2D tileSize = new IntegerVector2D(width, height);
		IntegerVector2D tileBorder = new IntegerVector2D(0, 0);
		IntegerVector2D tileOffset = new IntegerVector2D(0, 0);
		Tile tile = new Tile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
}
