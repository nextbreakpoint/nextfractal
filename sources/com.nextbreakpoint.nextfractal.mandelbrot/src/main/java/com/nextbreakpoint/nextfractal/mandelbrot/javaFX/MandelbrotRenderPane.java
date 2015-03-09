package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.session.SessionListener;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class MandelbrotRenderPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotRenderPane.class.getName());
	private final Session session;
	private ThreadFactory threadFactory;
	private JavaFXRendererFactory renderFactory;
	private RendererCoordinator[] coordinators;
	private RendererCoordinator juliaCoordinator;
	private MandelbrotImageGenerator generator;
	private AnimationTimer timer;
	private FileChooser fileChooser;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private boolean redrawOrbit;
	private boolean hideOrbit;
	private String astOrbit;
	private String astColor;
	private Tool currentTool;
	private MandelbrotData exportData;
	private ExecutorService exportExecutor;
	private Stack<MandelbrotView> views = new Stack<>();
	private CompilerBuilder<Orbit> orbitBuilder;
	private CompilerBuilder<Color> colorBuilder;
	private List<Number[]> states;

	public MandelbrotRenderPane(Session session, int width, int height, int rows, int columns) {
		this.session = session;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;
		
		threadFactory = new DefaultThreadFactory("MandelbrotRenderPane", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRendererFactory();

		generator = new MandelbrotImageGenerator(threadFactory, renderFactory, createSingleTile(25, 25));

		coordinators = new RendererCoordinator[rows * columns];
		
		Map<String, Integer> hints = new HashMap<String, Integer>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		createCoordinators(rows, columns, hints);
		
		Map<String, Integer> juliaHints = new HashMap<String, Integer>();
		juliaHints.put(RendererCoordinator.KEY_PROGRESS, RendererCoordinator.VALUE_SINGLE_PASS);
		createJuliaCoordinator(juliaHints);
		
		getStyleClass().add("mandelbrot");

		BorderPane controls = new BorderPane();
				
		HBox buttons = new HBox(10);
		Button zoomButton = new Button("Zoom");
		Button moveButton = new Button("Move");
		Button homeButton = new Button("Home");
		Button pickButton = new Button("Pick");
		Button orbitButton = new Button("Orbit");
		Button juliaButton = new Button("Julia");
		Button exportButton = new Button("Export");
		buttons.getChildren().add(homeButton);
		buttons.getChildren().add(zoomButton);
		buttons.getChildren().add(moveButton);
		buttons.getChildren().add(pickButton);
		buttons.getChildren().add(juliaButton);
		buttons.getChildren().add(orbitButton);
		buttons.getChildren().add(exportButton);
		buttons.getStyleClass().add("toolbar");
		
		ExportPane export = new ExportPane();
		export.setMinHeight(250);
		export.setMaxHeight(250);
		export.setPrefHeight(250);
		
		controls.setTop(export);
		controls.setBottom(buttons);
		
        Canvas fractalCanvas = new Canvas(width, height);
        GraphicsContext gcFractalCanvas = fractalCanvas.getGraphicsContext2D();
        gcFractalCanvas.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gcFractalCanvas.fillRect(0, 0, width, height);

        Canvas orbitCanvas = new Canvas(width, height);
        GraphicsContext gcOrbitCanvas = orbitCanvas.getGraphicsContext2D();
        gcOrbitCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcOrbitCanvas.fillRect(0, 0, width, height);

        Canvas juliaCanvas = new Canvas(width, height);
        GraphicsContext gcJuliaCanvas = juliaCanvas.getGraphicsContext2D();
        gcJuliaCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcJuliaCanvas.fillRect(0, 0, width, height);
        juliaCanvas.setOpacity(0.8);
        juliaCanvas.setVisible(false);

		currentTool = new ZoomTool();
		
		controls.setOnMouseClicked(e -> {
			if (currentTool != null) {
				currentTool.clicked(e);
			}
		});
		
		controls.setOnMousePressed(e -> {
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		
		controls.setOnMouseReleased(e -> {
			if (currentTool != null) {
				currentTool.released(e);
			}
		});
		
		controls.setOnMouseDragged(e -> {
			if (currentTool != null) {
				currentTool.dragged(e);
			}
		});
		
		controls.setOnMouseMoved(e -> {
			if (currentTool != null) {
				currentTool.moved(e);
			}
		});
		
		session.addSessionListener(new SessionListener() {
			@Override
			public void dataChanged(Session session) {
				updateFractalData(session);
			}
			
			@Override
			public void pointChanged(Session session, boolean continuous) {
				updateFractalPoint(continuous);
			}
			
			@Override
			public void viewChanged(Session session, boolean continuous) {
				updateFractalView(continuous);
			}

			@Override
			public void terminate(Session session) {
				dispose();
			}

			@Override
			public void sessionAdded(Session session, ExportSession exportSession) {
			}

			@Override
			public void sessionRemoved(Session session, ExportSession exportSession) {
			}
		});
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(fractalCanvas);
		stackPane.getChildren().add(orbitCanvas);
		stackPane.getChildren().add(juliaCanvas);
		stackPane.getChildren().add(controls);
		setCenter(stackPane);
        
		homeButton.setOnAction(e -> {
			resetView();
		});
		
		zoomButton.setOnAction(e -> {
			currentTool = new ZoomTool();
			juliaCanvas.setVisible(false);
		});
		
		moveButton.setOnAction(e -> {
			currentTool = new MoveTool();
			juliaCanvas.setVisible(false);
		});
		
		pickButton.setOnAction(e -> {
			currentTool = new PickTool();
			juliaCanvas.setVisible(true);
		});
		
		exportButton.setOnAction(e -> {
			storeExportData();
			export.show();
		});
		
		orbitButton.setOnAction(e -> {
			toggleShowOrbit(orbitCanvas);
		});
		
		juliaButton.setOnAction(e -> {
			toggleFractalJulia(juliaCanvas);
		});
		
		exportExecutor = Executors.newSingleThreadExecutor(threadFactory);
		
		runTimer(fractalCanvas, orbitCanvas, juliaCanvas);
		
		updateFractalData(session);

		export.hide();
	}

	private void resetView() {
		MandelbrotView view = new MandelbrotView(new double[] { 0, 0, 1, 0 }, new double[] { 0, 0, 0, 0 }, new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), getMandelbrotSession().getView().isJulia());
		getMandelbrotSession().setView(view, false);
	}

	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	private double getZoomSpeed() {
		return 1.1;
	}

	private void createFileChooser(String suffix) {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialFileName("image" + suffix);
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private void createCoordinators(int rows, int columns, Map<String, Integer> hints) {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				coordinators[row * columns + column] = new RendererCoordinator(threadFactory, renderFactory, createTile(row, column), hints);
			}
		}
		juliaCoordinator = new RendererCoordinator(threadFactory, renderFactory, createSingleTile(200, 200), hints);
	}

	private void disposeCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].abort();
			}
			if (juliaCoordinator != null) {
				juliaCoordinator.abort();
			}
		}
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].waitFor();
				coordinators[i].dispose();
				coordinators[i] = null;
			}
			if (juliaCoordinator != null) {
				juliaCoordinator.waitFor();
				juliaCoordinator.dispose();
				juliaCoordinator = null;
			}
		}
	}

	private void createJuliaCoordinator(Map<String, Integer> hints) {
		juliaCoordinator = new RendererCoordinator(threadFactory, renderFactory, createSingleTile(200, 200), hints);
	}

	private void disposeJuliaCoordinator() {
		if (juliaCoordinator != null) {
			juliaCoordinator.abort();
		}
		if (juliaCoordinator != null) {
			juliaCoordinator.waitFor();
			juliaCoordinator.dispose();
			juliaCoordinator = null;
		}
	}

	private void runTimer(Canvas fractalCanvas, Canvas orbitCanvas, Canvas juliaCanvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 25) {
					redrawIfPixelsChanged(fractalCanvas);
					redrawIfJuliaPixelsChanged(juliaCanvas);
					redrawIfOrbitChanged(orbitCanvas);
					if (currentTool != null) {
						currentTool.update(time);
					}
					last = time;
				}
			}
		};
		timer.start();
	}
	
	private void pushView() {
		views.push(getMandelbrotSession().getView());
	}

	private MandelbrotView popView() {
		if (views.size() > 0) {
			return views.pop();
		}
		return null;
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

	private void toggleFractalJulia(Canvas juliaCanvas) {
		if (getMandelbrotSession().getView().isJulia()) {
			currentTool = new ZoomTool();
			juliaCanvas.setVisible(false);
//			orbitCanvas.setVisible(!hideOrbit);
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), false);
			getMandelbrotSession().setView(view, false);
		} else {
			currentTool = new ZoomTool();
			juliaCanvas.setVisible(false);
//			orbitCanvas.setVisible(!hideOrbit);
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), true);
			getMandelbrotSession().setView(view, false);
		}
	}

	private void toggleShowOrbit(Canvas orbitCanvas) {
		hideOrbit = !hideOrbit;
		orbitCanvas.setVisible(!hideOrbit);
//		juliaCanvas.setVisible(!getMandelbrotSession().getView().isJulia() && !hideOrbit);
	}
	
	private void updateFractalData(Session session) {
		try {
			boolean[] changed = recompileFractal();
			boolean orbitChanged = changed[0];
			boolean colorChanged = changed[1];
			if (orbitChanged) {
				logger.info("Orbit algorithm is changed");
			}
			if (colorChanged) {
				logger.info("Color algorithm is changed");
			}
			double[] traslation = getMandelbrotSession().getView().getTraslation();
			double[] rotation = getMandelbrotSession().getView().getRotation();
			double[] scale = getMandelbrotSession().getView().getScale();
			double[] point = getMandelbrotSession().getView().getPoint();
			boolean julia = getMandelbrotSession().getView().isJulia();
			abortCoordinators();
			if (!julia && juliaCoordinator != null) {
				juliaCoordinator.abort();
			}
			joinCoordinators();
			if (!julia && juliaCoordinator != null) {
				juliaCoordinator.waitFor();
			}
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
					view.setTraslation(new Double4D(traslation));
					view.setRotation(new Double4D(rotation));
					view.setScale(new Double4D(scale));
					view.setState(new Integer4D(0, 0, 0, 0));
					view.setJulia(julia);
					view.setPoint(new Number(point));
					coordinator.setView(view);
				}
			}
			if (!julia && juliaCoordinator != null) {
				if (Boolean.getBoolean("disableSmartRender")) {
					juliaCoordinator.setOrbitAndColor(orbitBuilder.build(), colorBuilder.build());
				} else {
					if (orbitChanged) {
						juliaCoordinator.setOrbitAndColor(orbitBuilder.build(), colorBuilder.build());
					} else if (colorChanged) {
						juliaCoordinator.setColor(colorBuilder.build());
					}
				}
				juliaCoordinator.init();
				RendererView view = new RendererView();
				view.setTraslation(new Double4D(traslation));
				view.setRotation(new Double4D(rotation));
				view.setScale(new Double4D(scale));
				view.setState(new Integer4D(0, 0, 0, 0));
				view.setJulia(true);
				view.setPoint(new Number(point));
				juliaCoordinator.setView(view);
			}
			startCoordinators();
			if (!julia && juliaCoordinator != null) {
				juliaCoordinator.run();
			}
			if (!julia) {
				states = renderOrbit(point);
				redrawOrbit = true;
				logger.info("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
			}
		} catch (Exception e) {
			e.printStackTrace();//TODO display errors
		}
	}

	private boolean[] recompileFractal() throws Exception {
		boolean[] changed = new boolean[2];
		Compiler compiler = new Compiler(getClass().getPackage().getName(), getClass().getSimpleName());
		CompilerReport report = compiler.generateJavaSource(getMandelbrotSession().getSource());
		orbitBuilder = compiler.compileOrbit(report);
		//TODO report errors
		String newASTOrbit = report.getAST().getOrbit().toString();
		changed[0] = !newASTOrbit.equals(astOrbit);
		astOrbit = newASTOrbit;
		colorBuilder = compiler.compileColor(report);
		//TODO report errors
		String newASTColor = report.getAST().getColor().toString();
		changed[1] = !newASTColor.equals(astColor);
		astColor = newASTColor;
		return changed;
	}

	private void updateFractalPoint(boolean continuous) {
		boolean julia = getMandelbrotSession().getView().isJulia();
		double[] point = getMandelbrotSession().getView().getPoint();
		if (julia) {
			abortCoordinators();
			joinCoordinators();
			double[] traslation = getMandelbrotSession().getView().getTraslation();
			double[] rotation = getMandelbrotSession().getView().getRotation();
			double[] scale = getMandelbrotSession().getView().getScale();
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					RendererView view = new RendererView();
					view.setTraslation(new Double4D(traslation));
					view.setRotation(new Double4D(rotation));
					view.setScale(new Double4D(scale));
					view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
					view.setJulia(julia);
					view.setPoint(new Number(point));
					coordinator.setView(view);
				}
			}
			startCoordinators();
		} else {
			if (juliaCoordinator != null) {
				juliaCoordinator.abort();
				juliaCoordinator.waitFor();
				RendererView view = new RendererView();
				view.setTraslation(new Double4D(new double[] { 0, 0, 1, 0 }));
				view.setRotation(new Double4D(new double[] { 0, 0, 0, 0 }));
				view.setScale(new Double4D(new double[] { 1, 1, 1, 1 }));
				view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
				view.setJulia(true);
				view.setPoint(new Number(point));
				juliaCoordinator.setView(view);
				juliaCoordinator.run();
			}
			states = renderOrbit(point);
			redrawOrbit = true;
			logger.info("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
		}
	}

	private void updateFractalView(boolean continuous) {
		double[] traslation = getMandelbrotSession().getView().getTraslation();
		double[] rotation = getMandelbrotSession().getView().getRotation();
		double[] scale = getMandelbrotSession().getView().getScale();
		double[] point = getMandelbrotSession().getView().getPoint();
		boolean julia = getMandelbrotSession().getView().isJulia();
		abortCoordinators();
		joinCoordinators();
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				RendererView view = new RendererView();
				view.setTraslation(new Double4D(traslation));
				view.setRotation(new Double4D(rotation));
				view.setScale(new Double4D(scale));
				view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
				view.setJulia(julia);
				view.setPoint(new Number(point));
				coordinator.setView(view);
			}
		}
		startCoordinators();
		if (!continuous && !julia && juliaCoordinator != null) {
			juliaCoordinator.abort();
			juliaCoordinator.waitFor();
			RendererView view = new RendererView();
			view.setTraslation(new Double4D(new double[] { 0, 0, 1, 0 }));
			view.setRotation(new Double4D(new double[] { 0, 0, 0, 0 }));
			view.setScale(new Double4D(new double[] { 1, 1, 1, 1 }));
			view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
			view.setJulia(true);
			view.setPoint(new Number(point));
			juliaCoordinator.setView(view);
			juliaCoordinator.run();
		}
		redrawOrbit = true;
	}

	private List<Number[]> renderOrbit(double[] point) {
		List<Number[]> states = new ArrayList<>(); 
		try {
			Orbit orbit = orbitBuilder.build();
			Scope scope = new Scope();
			orbit.setScope(scope);
			orbit.init();
			orbit.setW(new Number(point));
			orbit.setX(orbit.getInitialPoint());
			orbit.render(states);
		} catch (Throwable e) {
			logger.log(Level.WARNING, "Failed to render orbit", e);
		}
		return states;
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

	private void redrawIfPixelsChanged(Canvas canvas) {
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null && coordinator.isPixelsChanged()) {
				RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
				coordinator.drawImage(gc);
			}
		}
	}

	private void redrawIfJuliaPixelsChanged(Canvas canvas) {
		if (!getMandelbrotSession().getView().isJulia() && juliaCoordinator != null && juliaCoordinator.isPixelsChanged()) {
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			double dw = canvas.getWidth();
			double dh = canvas.getHeight();
			gc.clearRect(0, 0, (int)dw, (int)dh);
			juliaCoordinator.drawImage(gc, 10, 10, 200, 200);
		}
	}

	protected void redrawIfOrbitChanged(Canvas canvas) {
		if (redrawOrbit) {
			redrawOrbit = false;
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				Number size = coordinators[0].getInitialSize();
				Number center = coordinators[0].getInitialCenter();
				double[] t = getMandelbrotSession().getView().getTraslation();
				double[] r = getMandelbrotSession().getView().getRotation();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				gc.setStroke(renderFactory.createColor(1, 0, 0, 1));
				Number[] state = states.get(0);
				double zx = state[0].r();
				double zy = state[0].i();
				double cx = dw / 2;
				double cy = dh / 2;
				double px = (zx - tx - center.r()) / (tz * size.r());
				double py = (zy - ty - center.i()) / (tz * size.r());
				double qx = Math.cos(a) * px + Math.sin(a) * py;
				double qy = Math.cos(a) * py - Math.sin(a) * px;
				int x = (int)Math.rint(qx * dw + cx);
				int y = (int)Math.rint(qy * dh + cy);
				gc.beginPath();
				gc.moveTo(x, y);
				for (int i = 1; i < states.size(); i++) {
					state = states.get(i);
					zx = state[0].r();
					zy = state[0].i();
					px = (zx - tx - center.r()) / (tz * size.r());
					py = (zy - ty - center.i()) / (tz * size.r());
					qx = Math.cos(a) * px + Math.sin(a) * py;
					qy = Math.cos(a) * py - Math.sin(a) * px;
					x = (int)Math.rint(qx * dw + cx);
					y = (int)Math.rint(qy * dh + cy);
					gc.lineTo(x, y);
				}
				gc.stroke();
			}
		}
	}

	private void storeExportData() {
		exportData = getMandelbrotSession().getData();
	}

	private Encoder createEncoder(String format) {
		final ServiceLoader<? extends Encoder> plugins = ServiceLoader.load(Encoder.class);
		for (Encoder plugin : plugins) {
			try {
				if (format.equals(plugin.getId())) {
					return plugin;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public void createExportSession(RendererSize rendererSize) {
		Encoder encoder = createEncoder("PNG");
		if (encoder == null) {
			//TODO error
			return;
		}
		createFileChooser(encoder.getSuffix());
		fileChooser.setTitle("Export");
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			MandelbrotData data = exportData; 
			exportExecutor.submit(new Runnable() {
				@Override
				public void run() {
					data.setPixels(generator.renderImage(data));
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								ExportSession exportSession = createExportSession(rendererSize, file, data, encoder);
								session.addExportSession(exportSession);
								session.getExportService().startSession(exportSession);
							} catch (Exception e) {
								logger.log(Level.WARNING, "Failed to export data", e);
								//TODO display error
							}
						}
					});
				}
			});
		}
	}

	private ExportSession createExportSession(RendererSize rendererSize, File file,	MandelbrotData data, Encoder encoder) throws IOException {
		File tmpFile = File.createTempFile("nextfractal-profile-", ".dat");
		ExportSession exportSession = new ExportSession("Mandelbrot", data, file, tmpFile, rendererSize, 200, encoder);
		logger.info("Export session created: " + exportSession.getSessionId());
		return exportSession;
	}

	private void dispose() {
		disposeCoordinators();
		disposeJuliaCoordinator();
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
				double[] p = getMandelbrotSession().getView().getPoint();
				boolean j = getMandelbrotSession().getView().isJulia();
				double x = t[0];
				double y = t[1];
				double z = t[2];
				double a = r[2] * Math.PI / 180;
				double zs = zoomin ? 1 / getZoomSpeed() : getZoomSpeed();
				Number size = coordinators[0].getInitialSize();
				x -= (zs - 1) * z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
				y -= (zs - 1) * z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
				z *= zs;
				MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s, p, j);
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
				double[] p = getMandelbrotSession().getView().getPoint();
				boolean j = getMandelbrotSession().getView().isJulia();
				double x = t[0];
				double y = t[1];
				double z = t[2];
				double a = r[2] * Math.PI / 180;
				Number size = coordinators[0].getInitialSize();
				x -= z * size.r() * (Math.cos(a) * (x1 - x0) + Math.sin(a) * (y1 - y0));
				y -= z * size.i() * (Math.cos(a) * (y1 - y0) - Math.sin(a) * (x1 - x0));
				x0 = x1;
				y0 = y1;
				MandelbrotView view = new MandelbrotView(new double[] { x, y, z, t[3] }, new double[] { 0, 0, r[2], r[3] }, s, p, j);
				getMandelbrotSession().setView(view, pressed);
				changed = false;
			}
		}
	}
	
	private class PickTool implements Tool {
		private volatile boolean pressed;
		private volatile boolean changed;
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
			pressed = true;
		}

		@Override
		public void update(long time) {
			if (changed) {
				if (!getMandelbrotSession().getView().isJulia()) {
					double[] t = getMandelbrotSession().getView().getTraslation();
					double[] r = getMandelbrotSession().getView().getRotation();
					double x = t[0];
					double y = t[1];
					double z = t[2];
					double a = r[2] * Math.PI / 180;
					Number size = coordinators[0].getInitialSize();
					Number center = coordinators[0].getInitialCenter();
					x += center.r() + z * size.r() * (Math.cos(a) * x1 + Math.sin(a) * y1);
					y += center.i() + z * size.i() * (Math.cos(a) * y1 - Math.sin(a) * x1);
					getMandelbrotSession().setPoint(new double[] { x, y }, pressed);
				}
				changed = false;
			}
		}
	}

	private class ExportPane extends Pane {
		private VBox box = new VBox(10);

		public ExportPane() {
			ComboBox<Integer[]> presets = new ComboBox<>();
			presets.getItems().add(new Integer[] { 1900, 1080 });
			presets.getItems().add(new Integer[] { 1650, 1050 });
			presets.getItems().add(new Integer[] { 1024, 768 });
			presets.getItems().add(new Integer[] { 640, 480 });
			presets.getItems().add(new Integer[] { 0, 0 });
			presets.setMinWidth(400);
			presets.setMaxWidth(400);
			presets.setPrefWidth(400);
			presets.getSelectionModel().select(0);
			Integer[] item0 = presets.getSelectionModel().getSelectedItem();
			AdvancedTextField widthField = new AdvancedTextField();
			widthField.setRestrict(getRestriction());
			widthField.setEditable(false);
			widthField.setMinWidth(400);
			widthField.setMaxWidth(400);
			widthField.setPrefWidth(400);
			widthField.setText(String.valueOf(item0[0]));
			AdvancedTextField heightField = new AdvancedTextField();
			heightField.setRestrict(getRestriction());
			heightField.setEditable(false);
			heightField.setMinWidth(400);
			heightField.setMaxWidth(400);
			heightField.setPrefWidth(400);
			heightField.setText(String.valueOf(item0[1]));
			Button close = new Button("Close");
			Button start = new Button("Start");

			HBox buttons = new HBox(10);
			buttons.getChildren().add(start);
			buttons.getChildren().add(close);
			buttons.setAlignment(Pos.CENTER);
			
			box.setAlignment(Pos.TOP_CENTER);
			box.getChildren().add(new Label("Presets"));
			box.getChildren().add(presets);
			box.getChildren().add(new Label("Width"));
			box.getChildren().add(widthField);
			box.getChildren().add(new Label("Height"));
			box.getChildren().add(heightField);
			box.getChildren().add(buttons);
			box.getStyleClass().add("export");
			
			presets.setConverter(new StringConverter<Integer[]>() {
				@Override
				public String toString(Integer[] item) {
					if (item == null) {
						return null;
					} else {
						if (item[0] == 0 || item[1] == 0) {
							return "Custom";
						} else {
							return item[0] + " \u00D7 " + item[1] + " px";
						}
					}
				}

				@Override
				public Integer[] fromString(String preset) {
					return null;
				}
			});
			presets.setCellFactory(new Callback<ListView<Integer[]>, ListCell<Integer[]>>() {
				@Override
				public ListCell<Integer[]> call(ListView<Integer[]> p) {
					return new ListCell<Integer[]>() {
						private final Label label;
						{
							label = new Label();
						}
						
						@Override
						protected void updateItem(Integer[] item, boolean empty) {
							super.updateItem(item, empty);
							if (item == null || empty) {
								setGraphic(null);
							} else {
								label.setText(presets.getConverter().toString(item));
								setGraphic(label);
							}
						}
					};
				}
			});

			presets.valueProperty().addListener(new ChangeListener<Integer[]>() {
		        @Override 
		        public void changed(ObservableValue<? extends Integer[]> value, Integer[] oldItem, Integer[] newItem) {
					if (newItem[0] == 0 || newItem[1] == 0) {
						widthField.setEditable(true);
						heightField.setEditable(true);
						widthField.setText("1024");
						heightField.setText("768");
					} else {
						widthField.setEditable(false);
						heightField.setEditable(false);
						widthField.setText(String.valueOf(newItem[0]));
						heightField.setText(String.valueOf(newItem[1]));
					}
		        }    
		    });
			
			close.setOnMouseClicked(e -> {
				hide();
			});
			
			start.setOnMouseClicked(e -> {
				hide();
				int width = Integer.parseInt(widthField.getText());
				int height = Integer.parseInt(heightField.getText());
				createExportSession(new RendererSize(width, height));
			});
			
			getChildren().add(box);
			
			widthProperty().addListener(new ChangeListener<java.lang.Number>() {
				@Override
				public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
					box.setPrefWidth(newValue.doubleValue());
				}
			});
			
			heightProperty().addListener(new ChangeListener<java.lang.Number>() {
				@Override
				public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
					box.setPrefHeight(newValue.doubleValue());
					box.setLayoutY(-newValue.doubleValue());
				}
			});
		}

		protected String getRestriction() {
			return "-?\\d*\\.?\\d*";
		}
		
		public void show() {
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromY(box.getTranslateY());
			tt.setToY(box.getHeight());
			tt.setNode(box);
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				}
			});
			tt.play();
		}
		
		public void hide() {
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromY(box.getHeight());
			tt.setToY(0);
			tt.setNode(box);
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				}
			});
			tt.play();
		}
	}
}
