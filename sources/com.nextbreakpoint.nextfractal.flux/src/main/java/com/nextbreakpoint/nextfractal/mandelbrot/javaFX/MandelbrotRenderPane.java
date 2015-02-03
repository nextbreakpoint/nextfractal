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
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
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
		
		getStyleClass().add("mandelbrot");

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
		buttons.getStyleClass().add("tools-pane");
		
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gc.fillRect(0, 0, width, height);
		canvas.getStyleClass().add("render-pane");

        coordinators = new RendererCoordinator[rows * columns];
		threadFactory = new DefaultThreadFactory("Render", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();
		Map<String, Integer> hints = new HashMap<String, Integer>();
		if (!Boolean.getBoolean("disableXaosRender")) {
			hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		}
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
			resetView();
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
				updateFractalData(session);
			}
			
			@Override
			public void viewChanged(FractalSession session, boolean zoom) {
				updateFractalView(zoom);
			}

			@Override
			public void terminate(FractalSession session) {
				disposeCoordinators();
			}
		});
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(canvas);
		stackPane.getChildren().add(buttons);
		setCenter(stackPane);
        
		runTimer(canvas);
		
		updateFractalData(session);
	}

	private void resetView() {
		MandelbrotView view = new MandelbrotView(new double[] { 0, 0, 1, 0 }, new double[] { 0, 0, 0, 0 }, new double[] { 1, 1, 1, 1 });
		getMandelbrotSession().setView(view, false);
	}

	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
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
				coordinators[i].abort();
			}
		}
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].waitFor();
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
					redrawCanvasIfCoordinatorsPixelsChanged(canvas);
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
	
	private void updateFractalData(FractalSession session) {
		try {
			Compiler compiler = new Compiler(session.getOutDir(), session.getPackageName(), session.getClassName());
			CompilerReport report = compiler.generateJavaSource(getMandelbrotSession().getSource());
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
					coordinator.abort();
				}
			}
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					coordinator.waitFor();
				}
			}
			double[] traslation = getMandelbrotSession().getView().getTraslation();
			double[] rotation = getMandelbrotSession().getView().getRotation();
			double[] scale = getMandelbrotSession().getView().getScale();
			double[] constant = getMandelbrotSession().getConstant();
			boolean julia = getMandelbrotSession().isJulia();
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
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
					RendererView view = new RendererView();
					view .setTraslation(new DoubleVector4D(traslation));
					view.setRotation(new DoubleVector4D(rotation));
					view.setScale(new DoubleVector4D(scale));
					view.setState(new IntegerVector4D(0, 0, 0, 0));
					view.setJulia(julia);
					view.setConstant(new Number(constant));
					coordinator.setView(view);
				}
			}
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					coordinator.run();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();//TODO display errors
		}
	}

	private void updateFractalView(boolean zoom) {
		abortCoordinators();
		joinCoordinators();
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				RendererView view = new RendererView();
				double[] traslation = getMandelbrotSession().getView().getTraslation();
				double[] rotation = getMandelbrotSession().getView().getRotation();
				double[] scale = getMandelbrotSession().getView().getScale();
				double[] constant = getMandelbrotSession().getConstant();
				boolean julia = getMandelbrotSession().isJulia();
				view.setTraslation(new DoubleVector4D(traslation));
				view.setRotation(new DoubleVector4D(rotation));
				view.setScale(new DoubleVector4D(scale));
				view.setState(new IntegerVector4D(0, 0, zoom ? 1 : 0, 0));
				view.setJulia(julia);
				view.setConstant(new Number(constant));
				coordinator.setView(view);
			}
		}
		startCoordinators();
	}

	private void abortCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.abort();
			}
		}
	}

	private void joinCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.waitFor();
			}
		}
	}

	private void startCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				coordinator.run();
			}
		}
	}

	private void redrawCanvasIfCoordinatorsPixelsChanged(Canvas canvas) {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null && coordinator.isPixelsChanged()) {
				RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
//				coordinator.drawImage(gc);
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
				double[] t = getMandelbrotSession().getView().getTraslation();
				double[] r = getMandelbrotSession().getView().getRotation();
				double[] s = getMandelbrotSession().getView().getScale();
				double x = t[0];
				double y = t[1];
				double z = t[2];
				double a = r[2] * Math.PI / 180;
				double zs = zoomin ? 1 / getZoomSpeed() : getZoomSpeed();
				Number size = coordinators[0].getInitialSize();
				x -= (zs - 1) * z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
				y -= (zs - 1) * z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
				z *= zs;
				MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s);
				getMandelbrotSession().setView(view, pressed);
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
				double[] t = getMandelbrotSession().getView().getTraslation();
				double[] r = getMandelbrotSession().getView().getRotation();
				double[] s = getMandelbrotSession().getView().getScale();
				double x = t[0];
				double y = t[1];
				double z = t[2];
				double a = r[2] * Math.PI / 180;
				Number size = coordinators[0].getInitialSize();
				x -= z * size.r() * (Math.cos(a) * (x1 - x0) + Math.sin(a) * (y1 - y0));
				y -= z * size.i() * (Math.cos(a) * (y1 - y0) - Math.sin(a) * (x1 - x0));
				x0 = x1;
				y0 = y1;
				MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s);
				getMandelbrotSession().setView(view, pressed);
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
				changed = false;
			}
		}
	}
}
