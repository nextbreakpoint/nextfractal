package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.IntegerVector4D;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
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
	private Tool currentTool;
	private RendererView view;
	
	public MandelbrotRenderPane(FractalSession session, int width, int height) {
		this.session = session;
		this.width = width;
		this.height = height;
		currentTool = new ZoomTool();
		view = new RendererView();
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
					Compiler compiler = new Compiler(session.getOutDir(), session.getPackageName(), session.getClassName(), session.getSource());
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
					rendererCoordinator.stopRender();
					if (Boolean.getBoolean("disableSmartRender")) {
						rendererCoordinator.setOrbitAndColor((Orbit)reportOrbit.getObject(), (Color)reportColor.getObject());
					} else {
						if (orbitChanged) {
							rendererCoordinator.setOrbitAndColor((Orbit)reportOrbit.getObject(), (Color)reportColor.getObject());
						} else if (colorChanged) {
							rendererCoordinator.setColor((Color)reportColor.getObject());
						}
					}
					rendererCoordinator.init();
					rendererCoordinator.setView(view);
					rendererCoordinator.startRender();
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
		canvas.setOnMouseClicked(e -> {
			if (currentTool != null) {
				currentTool.clicked(e);
			}
		});
		canvas.setOnMousePressed(e -> {
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		canvas.setOnMouseReleased(e -> {
			if (currentTool != null) {
				currentTool.released(e);
			}
		});
		canvas.setOnMouseDragged(e -> {
			if (currentTool != null) {
				currentTool.dragged(e);
			}
		});
		canvas.setOnMouseMoved(e -> {
			if (currentTool != null) {
				currentTool.moved(e);
			}
		});
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 20 && rendererCoordinator != null) {
					if (currentTool != null) {
						currentTool.update(time);
					}
				}
				if ((time - last) > 50 && rendererCoordinator != null && rendererCoordinator.isPixelsChanged()) {
					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					rendererCoordinator.drawImage(gc);
					last = time;
				}
			}
		};
		timer.start();
	}

	private RendererTile createTile() {
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(width, height);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererSize tileOffset = new RendererSize(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
	
	private interface Tool {
		public void clicked(MouseEvent e);

		public void moved(MouseEvent e);

		public void dragged(MouseEvent e);

		public void released(MouseEvent e);

		public void pressed(MouseEvent e);

		public void update(long time);
	}
	
	private class ZoomTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean active;
		private boolean zoomin;
		private double x0;
		private double y0;
		private double x1;
		private double y1;
		private double z;
		private DoubleVector4D t;
		private IntegerVector4D s;
		
		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getSceneX() - rendererCoordinator.getWidth() / 2) / rendererCoordinator.getWidth();
			y1 = (e.getSceneY() - rendererCoordinator.getHeight() / 2) / rendererCoordinator.getHeight();
			z = zoomin ? z * 0.01 : z / 0.01;
		}

		@Override
		public void released(MouseEvent e) {
			pressed = false;
		}

		@Override
		public void pressed(MouseEvent e) {
			pressed = true;
			active = true;
			x0 = (e.getSceneX() - rendererCoordinator.getWidth() / 2) / rendererCoordinator.getWidth();
			y0 = (e.getSceneY() - rendererCoordinator.getHeight() / 2) / rendererCoordinator.getHeight();
			z = 1;
			s = view.getState();
			t = view.getTraslation();
			zoomin = (e.isPrimaryButtonDown()) ? true : false;
		}

		@Override
		public void update(long time) {
			if (pressed) {
				rendererCoordinator.stopRender();
				view.setTraslation(new DoubleVector4D(t.getX() + x1 - x0, t.getY() + y0 - y1, t.getZ() * z, t.getW()));
				view.setState(new IntegerVector4D(s.getX(), s.getY(), 1, s.getW()));
				rendererCoordinator.setView(view);
				rendererCoordinator.startRender();
			} else if (active) {
				rendererCoordinator.stopRender();
				view.setTraslation(new DoubleVector4D(t.getX() + x1 - x0, t.getY() + y0 - y1, t.getZ() * z, t.getW()));
				view.setState(new IntegerVector4D(s.getX(), s.getY(), 0, s.getW()));
				rendererCoordinator.setView(view);
				rendererCoordinator.startRender();
				active = false;
			}
		}
	}
	
	private class MoveTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean active;
		private double x0;
		private double y0;
		private double x1;
		private double y1;
		private DoubleVector4D t;
		private IntegerVector4D s;

		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getSceneX() - rendererCoordinator.getWidth() / 2) / rendererCoordinator.getWidth();
			y1 = (e.getSceneY() - rendererCoordinator.getHeight() / 2) / rendererCoordinator.getHeight();
		}

		@Override
		public void released(MouseEvent e) {
			pressed = false;
		}

		@Override
		public void pressed(MouseEvent e) {
			pressed = true;
			active = true;
			x0 = (e.getSceneX() - rendererCoordinator.getWidth() / 2) / rendererCoordinator.getWidth();
			y0 = (e.getSceneY() - rendererCoordinator.getHeight() / 2) / rendererCoordinator.getHeight();
			s = view.getState();
			t = view.getTraslation();
		}

		@Override
		public void update(long time) {
			if (pressed) {
				rendererCoordinator.stopRender();
				view.setTraslation(new DoubleVector4D(t.getX() + x1 - x0, t.getY() + y0 - y1, t.getZ(), t.getW()));
				view.setState(new IntegerVector4D(s.getX(), s.getY(), 1, s.getW()));
				rendererCoordinator.setView(view);
				rendererCoordinator.startRender();
			} else if (active) {
				rendererCoordinator.stopRender();
				view.setTraslation(new DoubleVector4D(t.getX() + x1 - x0, t.getY() + y0 - y1, t.getZ(), t.getW()));
				view.setState(new IntegerVector4D(s.getX(), s.getY(), 0, s.getW()));
				rendererCoordinator.setView(view);
				rendererCoordinator.startRender();
				active = false;
			}
		}
	}
	
	private class PickTool implements Tool {
		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
		}

		@Override
		public void released(MouseEvent e) {
		}

		@Override
		public void pressed(MouseEvent e) {
		}

		@Override
		public void update(long time) {
		}
	}
}
