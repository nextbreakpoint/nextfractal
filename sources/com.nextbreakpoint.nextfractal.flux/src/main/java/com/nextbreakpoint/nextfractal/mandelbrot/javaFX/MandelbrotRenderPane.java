package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.IntegerVector4D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;

public class MandelbrotRenderPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotRenderPane.class.getName());
	private final FractalSession session;
	private ThreadFactory threadFactory;
	private JavaFXRenderFactory renderFactory;
	private RendererCoordinator[] coordinators;
	private RendererView[] views;
	private AnimationTimer timer;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private String astOrbit;
	private String astColor;
	private Tool currentTool;
	
	public MandelbrotRenderPane(FractalSession session, int width, int height, int rows, int columns) {
		this.session = session;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		HBox buttons = new HBox(10);
		Button zoomButton = new Button("Zoom");
		Button moveButton = new Button("Move");
		Button homeButton = new Button("Home");
		Button pickButton = new Button("Pick");
		Button orbitButton = new Button("Orbit");
		Button exportButton = new Button("Export");
		buttons.getChildren().add(homeButton);
		buttons.getChildren().add(zoomButton);
		buttons.getChildren().add(moveButton);
		buttons.getChildren().add(pickButton);
		buttons.getChildren().add(orbitButton);
		buttons.getChildren().add(exportButton);
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gc.fillRect(0, 0, width, height);
		views = new RendererView[rows * columns];
        coordinators = new RendererCoordinator[rows * columns];
		threadFactory = new DefaultThreadFactory("Render", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
		Map<String, Integer> hints = new HashMap<String, Integer>();
		if (!Boolean.getBoolean("disableXaosRender")) {
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		}
		createViews(rows, columns);
		createCoordinators(rows, columns, hints);
		currentTool = new ZoomTool();
		buttons.setOnMouseClicked(e -> {
			if (currentTool != null) {
				currentTool.clicked(e);
			}
		});
		buttons.setOnMousePressed(e -> {
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		buttons.setOnMouseReleased(e -> {
			if (currentTool != null) {
				currentTool.released(e);
			}
		});
		buttons.setOnMouseDragged(e -> {
			if (currentTool != null) {
				currentTool.dragged(e);
			}
		});
		buttons.setOnMouseMoved(e -> {
			if (currentTool != null) {
				currentTool.moved(e);
			}
		});
		homeButton.setOnAction(e -> {
			resetRegion();
		});
		zoomButton.setOnAction(e -> {
			currentTool = new ZoomTool();
		});
		moveButton.setOnAction(e -> {
			currentTool = new MoveTool();
		});
		pickButton.setOnAction(e -> {
			currentTool = new PickTool();
		});
		orbitButton.setOnAction(e -> {
			currentTool = new OrbitTool();
		});
		exportButton.setOnAction(e -> {
			//TODO export
		});
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void dataChanged(FractalSession session) {
				updateFractal(session);
			}

			@Override
			public void terminate(FractalSession session) {
				disposeCoordinators();
			}
		});
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(canvas);
		stackPane.getChildren().add(buttons);
		canvas.getStyleClass().add("render-pane");
		buttons.getStyleClass().add("tools-pane");
		getStyleClass().add("mandelbrot");
		setCenter(stackPane);
        runTimer(canvas);
		updateFractal(session);
	}

	private void resetRegion() {
		abortCoordinatos();
		joinCoordinators();
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				RendererView view = views[i];
				view.setTraslation(new DoubleVector4D(0, 0, 1, 0));
				view.setRotation(new DoubleVector4D(0, 0, 0, 0));
				view.setState(new IntegerVector4D(0, 0, 0, 0));
				coordinator.setView(view);
			}
		}
		startCoordinators();
	}

	private void createViews(int rows, int columns) {
		for (int i = 0; i < views.length; i++) {
			views[i] = new RendererView();
		}
	}

	private double getZoomSpeed() {
		return 1.1;
	}

	private void createCoordinators(int rows, int columns, Map<String, Integer> hints) {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				coordinators[row * columns + column] = new RendererCoordinator(threadFactory, renderFactory, createTile(row, column), hints);
			}
		}
	}

	private void disposeCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].abortRender();
			}
		}
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].joinRender();
				coordinators[i].dispose();
				coordinators[i] = null;
			}
		}
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 25) {
					for (int i = 0; i < coordinators.length; i++) {
						RendererCoordinator coordinator = coordinators[i];
						if (coordinator != null && coordinator.isPixelsChanged()) {
							RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
//							coordinator.drawImage(gc);
						}
					}
					if (currentTool != null) {
						currentTool.update(time);
					}
					last = time;
				}
			}
		};
		timer.start();
	}

	private RendererTile createTile(int row, int column) {
		int tileWidth = width / columns;
		int tileHeight = height / rows;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);//TODO border?
		RendererPoint tileOffset = new RendererPoint(column * width / columns, row * height / rows);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
	
	private void updateFractal(FractalSession session) {
		try {
			Compiler compiler = new Compiler(session.getOutDir(), session.getPackageName(), session.getClassName());
			CompilerReport report = compiler.generateJavaSource(((MandelbrotData)session.getData()).getSource());
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			//TODO report errors
			String newASTOrbit = report.getAST().getOrbit().toString();
			boolean orbitChanged = !newASTOrbit.equals(astOrbit);
			astOrbit = newASTOrbit;
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			//TODO report errors
			String newASTColor = report.getAST().getColor().toString();
			boolean colorChanged = !newASTColor.equals(astColor);
			astColor = newASTColor;
			if (orbitChanged) {
				logger.info("Orbit algorithm is changed");
			}
			if (colorChanged) {
				logger.info("Color algorithm is changed");
			}
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					coordinator.abortRender();
				}
			}
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					coordinator.joinRender();
					if (Boolean.getBoolean("disableSmartRender")) {
						coordinator.setOrbitAndColor(orbitBuilder.build(), colorBuilder.build());
					} else {
						if (orbitChanged) {
							coordinator.setOrbitAndColor(orbitBuilder.build(), colorBuilder.build());
						} else if (colorChanged) {
							coordinator.setColor(colorBuilder.build());
						}
					}
					coordinator.init();
					coordinator.setView(views[i]);
				}
			}
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					coordinator.startRender();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();//TODO display errors
		}
	}

	private void abortCoordinatos() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.abortRender();
			}
		}
	}

	private void joinCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.joinRender();
			}
		}
	}

	private void startCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.startRender();
			}
		}
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
		private volatile boolean changed;
		private boolean zoomin;
		private double x1;
		private double y1;
		
		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			changed = true;
		}

		@Override
		public void released(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			pressed = false;
			changed = true;
		}

		@Override
		public void pressed(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			zoomin = (e.isPrimaryButtonDown()) ? true : false;
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (pressed || changed) {
				abortCoordinatos();
				joinCoordinators();
				for (int i = 0; i < coordinators.length; i++) {
					RendererCoordinator coordinator = coordinators[i];
					if (coordinator != null) {
						RendererView view = views[i];
						DoubleVector4D t = view.getTraslation();
						DoubleVector4D r = view.getRotation();
						IntegerVector4D s = view.getState();
						double x = t.getX();
						double y = t.getY();
						double z = t.getZ();
						double a = r.getZ() * Math.PI / 180;
						double zs = zoomin ? 1 / getZoomSpeed() : getZoomSpeed();
						Number size = coordinator.getInitialSize();
						x -= (zs - 1) * z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
						y -= (zs - 1) * z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
						z *= zs;
						view.setTraslation(new DoubleVector4D(x, y, z, t.getW()));
						view.setRotation(new DoubleVector4D(0, 0, r.getZ(), r.getW()));
						view.setState(new IntegerVector4D(s.getX(), s.getY(), pressed ? 1 : 0, s.getW()));
						coordinator.setView(view);
					}
				}
				startCoordinators();
				changed = false;
			}
		}
	}
	
	private class MoveTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean changed;
		private double x0;
		private double y0;
		private double x1;
		private double y1;

		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			changed = true;
		}

		@Override
		public void released(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			pressed = false;
			changed = true;
		}

		@Override
		public void pressed(MouseEvent e) {
			x1 = x0 = (e.getX() - width / 2) / width;
			y1 = y0 = (e.getY() - height / 2) / height;
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (changed) {
				abortCoordinatos();
				joinCoordinators();
				for (int i = 0; i < coordinators.length; i++) {
					RendererCoordinator coordinator = coordinators[i];
					if (coordinator != null) {
						RendererView view = views[i];
						DoubleVector4D t = view.getTraslation();
						IntegerVector4D s = view.getState();
						double x = t.getX();
						double y = t.getY();
						double z = t.getZ();
						double w = t.getW();
						Number size = coordinator.getInitialSize();
						x -= (x1 - x0) * z * size.r();
						y -= (y1 - y0) * z * size.i();
						x0 = x1;
						y0 = y1;
						view.setTraslation(new DoubleVector4D(x, y, z, w));
						view.setState(new IntegerVector4D(0, 0, pressed ? 1 : 0, s.getW()));
						coordinator.setView(view);
					}
				}
				startCoordinators();
				changed = false;
			}
		}
	}
	
	private class PickTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean changed;
		private double x0;
		private double y0;
		private double x1;
		private double y1;

		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			changed = true;
		}

		@Override
		public void released(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			pressed = false;
			changed = true;
		}

		@Override
		public void pressed(MouseEvent e) {
			x1 = x0 = (e.getX() - width / 2) / width;
			y1 = y0 = (e.getY() - height / 2) / height;
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (changed) {
				abortCoordinatos();
				joinCoordinators();
				for (int i = 0; i < coordinators.length; i++) {
					RendererCoordinator coordinator = coordinators[i];
					if (coordinator != null) {
						RendererView view = views[i];
						DoubleVector4D t = view.getTraslation();
						IntegerVector4D s = view.getState();
						double x = t.getX();
						double y = t.getY();
						double z = t.getZ();
						double w = t.getW();
					}
				}
				startCoordinators();
				changed = false;
			}
		}
	}

	private class OrbitTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean changed;
		private double x0;
		private double y0;
		private double x1;
		private double y1;

		@Override
		public void clicked(MouseEvent e) {
		}

		@Override
		public void moved(MouseEvent e) {
		}

		@Override
		public void dragged(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			changed = true;
		}

		@Override
		public void released(MouseEvent e) {
			x1 = (e.getX() - width / 2) / width;
			y1 = (e.getY() - height / 2) / height;
			pressed = false;
			changed = true;
		}

		@Override
		public void pressed(MouseEvent e) {
			x1 = x0 = (e.getX() - width / 2) / width;
			y1 = y0 = (e.getY() - height / 2) / height;
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (changed) {
				abortCoordinatos();
				joinCoordinators();
				for (int i = 0; i < coordinators.length; i++) {
					RendererCoordinator coordinator = coordinators[i];
					if (coordinator != null) {
						RendererView view = views[i];
						DoubleVector4D t = view.getTraslation();
						IntegerVector4D s = view.getState();
						double x = t.getX();
						double y = t.getY();
						double z = t.getZ();
						double w = t.getW();
					}
				}
				startCoordinators();
				changed = false;
			}
		}
	}
}
