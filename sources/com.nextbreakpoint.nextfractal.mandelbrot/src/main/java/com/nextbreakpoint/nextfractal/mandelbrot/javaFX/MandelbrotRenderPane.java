package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
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
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class MandelbrotRenderPane extends BorderPane implements ExportPaneDelegate {
	private static final int FRAME_LENGTH_IN_MILLIS = 20;
	private static final Logger logger = Logger.getLogger(MandelbrotRenderPane.class.getName());
	private final Session session;
	private ThreadFactory threadFactory;
	private JavaFXRendererFactory renderFactory;
	private RendererCoordinator[] coordinators;
	private RendererCoordinator juliaCoordinator;
	private MandelbrotImageGenerator generator;
	private AnimationTimer timer;
	private FileChooser fileChooser;
	private StringObservableValue errorProperty;
	private BooleanObservableValue hideOrbitProperty;
	private BooleanObservableValue hideErrorsProperty;
	private BooleanObservableValue juliaProperty;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private volatile boolean redrawOrbit;
	private volatile boolean disableTool;
	private String astOrbit;
	private String astColor;
	private Tool currentTool;
	private MandelbrotData exportData;
	private ExecutorService exportExecutor;
	private Stack<MandelbrotView> views = new Stack<>();
	private CompilerBuilder<Orbit> orbitBuilder;
	private CompilerBuilder<Color> colorBuilder;
	private List<Number[]> states;
	private FadeTransition alertsTransition;
	private FadeTransition toolsTransition;

	public MandelbrotRenderPane(Session session, int width, int height, int rows, int columns) {
		this.session = session;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;

		errorProperty = new StringObservableValue();
		errorProperty.setValue(null);

		juliaProperty = new BooleanObservableValue();
		juliaProperty.setValue(false);

		hideOrbitProperty = new BooleanObservableValue();
		hideOrbitProperty.setValue(true);
		
		hideErrorsProperty = new BooleanObservableValue();
		hideErrorsProperty.setValue(true);
		
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
				
		HBox toolButtons = new HBox(10);
		Button zoominButton = new Button("", createIconImage("/icon-zoomin.png"));
		Button zoomoutButton = new Button("", createIconImage("/icon-zoomout.png"));
		Button moveButton = new Button("", createIconImage("/icon-move.png"));
		Button homeButton = new Button("", createIconImage("/icon-home.png"));
		Button pickButton = new Button("", createIconImage("/icon-pick.png"));
		Button orbitButton = new Button("", createIconImage("/icon-orbit.png"));
		Button juliaButton = new Button("", createIconImage("/icon-julia.png"));
		Button exportButton = new Button("", createIconImage("/icon-export.png"));
		toolButtons.getChildren().add(homeButton);
		toolButtons.getChildren().add(zoominButton);
		toolButtons.getChildren().add(zoomoutButton);
		toolButtons.getChildren().add(moveButton);
		toolButtons.getChildren().add(pickButton);
		toolButtons.getChildren().add(juliaButton);
		toolButtons.getChildren().add(orbitButton);
		toolButtons.getChildren().add(exportButton);
		toolButtons.getStyleClass().add("toolbar");
		toolButtons.setOpacity(0);
		createToolsTransition(toolButtons);

		HBox alertButtons = new HBox(10);
		Button errorsButton = new Button("", createIconImage("/icon-errors.png"));
		alertButtons.getChildren().add(errorsButton);
		alertButtons.getStyleClass().add("alerts");

		ExportPane exportPane = new ExportPane();
		exportPane.setDelegate(this);
		exportPane.setDisable(true);
		
		ErrorPane errorPane = new ErrorPane();
		errorPane.setDisable(true);
		
		StackPane alertsPane = new StackPane();
		alertsPane.setMinHeight(250);
		alertsPane.setMaxHeight(250);
		alertsPane.setPrefHeight(250);
		
		alertsPane.getChildren().add(alertButtons);
		alertsPane.getChildren().add(exportPane);
		alertsPane.getChildren().add(errorPane);
		controls.setTop(alertsPane);
		controls.setBottom(toolButtons);
		alertButtons.setVisible(false);
		createAlertsTransition(alertButtons);
		
        Canvas fractalCanvas = new Canvas(width, height);
        GraphicsContext gcFractalCanvas = fractalCanvas.getGraphicsContext2D();
        gcFractalCanvas.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gcFractalCanvas.fillRect(0, 0, width, height);

        Canvas orbitCanvas = new Canvas(width, height);
        GraphicsContext gcOrbitCanvas = orbitCanvas.getGraphicsContext2D();
        gcOrbitCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcOrbitCanvas.fillRect(0, 0, width, height);
		orbitCanvas.setVisible(false);

        Canvas juliaCanvas = new Canvas(width, height);
        GraphicsContext gcJuliaCanvas = juliaCanvas.getGraphicsContext2D();
        gcJuliaCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcJuliaCanvas.fillRect(0, 0, width, height);
        juliaCanvas.setOpacity(0.8);
        juliaCanvas.setVisible(false);

		currentTool = new ZoomTool(true);
		
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
			if (e.getY() > controls.getHeight() - 100 && e.getY() < controls.getHeight()) {
				fadeIn(toolsTransition, x -> {});
			} else {
				fadeOut(toolsTransition, x -> {});
			}
			if (currentTool != null) {
				currentTool.moved(e);
			}
		});
		
		getMandelbrotSession().addMandelbrotListener(new MandelbrotListener() {
			@Override
			public void dataChanged(MandelbrotSession session) {
				juliaProperty.setValue(session.getData().isJulia());
			}
			
			@Override
			public void sourceChanged(MandelbrotSession session) {
			}
			
			@Override
			public void pointChanged(MandelbrotSession session, boolean continuous) {
				updatePoint(continuous);
			}
			
			@Override
			public void viewChanged(MandelbrotSession session, boolean continuous) {
				updateView(continuous);
			}

			@Override
			public void reportChanged(MandelbrotSession session) {
				updateFractal(session);
			}
		});

		session.addSessionListener(new SessionListener() {
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
		
		zoominButton.setOnAction(e -> {
			currentTool = new ZoomTool(true);
			juliaCanvas.setVisible(false);
		});
		
		zoomoutButton.setOnAction(e -> {
			currentTool = new ZoomTool(false);
			juliaCanvas.setVisible(false);
		});
		
		moveButton.setOnAction(e -> {
			currentTool = new MoveTool();
			juliaCanvas.setVisible(false);
		});
		
		pickButton.setOnAction(e -> {
			if (!getMandelbrotSession().getData().isJulia()) {
				currentTool = new PickTool();
				juliaCanvas.setVisible(true);
			}
		});
		
		exportButton.setOnAction(e -> {
			if (errorProperty.getValue() == null) {
				storeExportData();
				exportPane.show();
			}
		});
		
		orbitButton.setOnAction(e -> {
			if (!getMandelbrotSession().getData().isJulia()) {
				currentTool = new PickTool();
				juliaCanvas.setVisible(true);
				pickButton.requestFocus();
			} else {
				currentTool = new ZoomTool(true);
				juliaCanvas.setVisible(false);
				zoominButton.requestFocus();
			}
			toggleShowOrbit(orbitCanvas);
		});
		
		juliaButton.setOnAction(e -> {
			currentTool = new ZoomTool(true);
			juliaCanvas.setVisible(false);
			juliaProperty.setValue(!juliaProperty.getValue());
			zoominButton.requestFocus();
		});
		
		hideOrbitProperty.addListener((observable, oldValue, newValue) -> {
			orbitCanvas.setVisible(!newValue);
		});
		
		juliaProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				juliaButton.setGraphic(createIconImage("/icon-mandelbrot.png"));
				setFractalJulia(true);
			} else {
				juliaButton.setGraphic(createIconImage("/icon-julia.png"));
				setFractalJulia(false);
			}
			pickButton.setDisable(newValue);
		});
		
		errorPane.disabledProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				if (errorProperty.getValue() == null) {
					fadeOut(alertsTransition, x -> { 
						alertButtons.setVisible(false);
					});
				} else {
					alertButtons.setVisible(true);
					fadeIn(alertsTransition, x -> {
					});
				}
			}
		});
		
		errorProperty.addListener((observable, oldValue, newValue) -> {
			exportPane.hide();
			errorPane.setMessage(newValue);
			if (newValue == null) {
				fadeOut(alertsTransition, x -> { 
					alertButtons.setVisible(false);
				});
			} else if (hideErrorsProperty.getValue()) {
				alertButtons.setVisible(true);
				fadeIn(alertsTransition, x -> {
				});
			}
		});
		
		errorsButton.setOnAction(e -> {
			fadeOut(alertsTransition, x -> { 
				alertButtons.setVisible(false);
				errorPane.show();
			});
		});
		
		exportExecutor = Executors.newSingleThreadExecutor(threadFactory);
		
		runTimer(fractalCanvas, orbitCanvas, juliaCanvas);
		
		exportPane.hide();
	}

	private ImageView createIconImage(String name) {
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(32);
		image.setFitHeight(32);
		return image;
	}

	private void createAlertsTransition(Node node) {
		alertsTransition = new FadeTransition();
		alertsTransition.setNode(node);
		alertsTransition.setDuration(Duration.seconds(0.5));
		alertsTransition.play();
	}
	
	private void createToolsTransition(Node node) {
		toolsTransition = new FadeTransition();
		toolsTransition.setNode(node);
		toolsTransition.setDuration(Duration.seconds(0.5));
		toolsTransition.play();
	}
	
	private void fadeOut(FadeTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		transition.setFromValue(transition.getNode().getOpacity());
		transition.setToValue(0);
		transition.setOnFinished(handler);
		transition.play();
	}

	private void fadeIn(FadeTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		transition.setFromValue(transition.getNode().getOpacity());
		transition.setToValue(1);
		transition.setOnFinished(handler);
		transition.play();
	}

	private void resetView() {
		MandelbrotView view = new MandelbrotView(new double[] { 0, 0, 1, 0 }, new double[] { 0, 0, 0, 0 }, new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), getMandelbrotSession().getView().isJulia());
		getMandelbrotSession().setView(view, false);
	}

	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	private double getZoomSpeed() {
		return 1.05;
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
				if (time - last > FRAME_LENGTH_IN_MILLIS) {
					if (!disableTool) {
						redrawIfPixelsChanged(fractalCanvas);
						redrawIfJuliaPixelsChanged(juliaCanvas);
						redrawIfOrbitChanged(orbitCanvas);
						if (currentTool != null) {
							currentTool.update(time);
						}
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

	private void setFractalJulia(boolean julia) {
		if (disableTool) {
			return;
		}
		if (!julia && getMandelbrotSession().getView().isJulia()) {
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), false);
			getMandelbrotSession().setView(view, false);
		} else if (julia && !getMandelbrotSession().getView().isJulia()) {
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getView().getPoint(), true);
			getMandelbrotSession().setView(view, false);
		}
	}

	private void toggleShowOrbit(Canvas orbitCanvas) {
		if (disableTool) {
			return;
		}
		hideOrbitProperty.setValue(!hideOrbitProperty.getValue());
	}
	
	private void updateFractal(Session session) {
		try {
			boolean[] changed = generateOrbitAndColor();
			updateErrors(null, null);
			boolean orbitChanged = changed[0];
			boolean colorChanged = changed[1];
			if (orbitChanged) {
				logger.info("Orbit algorithm is changed");
			}
			if (colorChanged) {
				logger.info("Color algorithm is changed");
			}
//			if (!orbitChanged && !colorChanged) {
//				logger.info("Orbit or color algorithms are not changed");
//				return;
//			}
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
		} catch (CompileSourceException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), e.getErrors());
		} catch (CompileClassException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), e.getErrors());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), null);
		}
	}

	private boolean[] generateOrbitAndColor() throws CompileSourceException, CompileClassException, ClassNotFoundException, IOException {
		CompilerReport report = getMandelbrotSession().getReport();
		if (report.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompileSourceException("Failed to compile source", report.getErrors());
		}
		Compiler compiler = new Compiler();
		boolean[] changed = new boolean[] { false, false };
		CompilerBuilder<Orbit> newOrbitBuilder = compiler.compileOrbit(report);
		if (newOrbitBuilder.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompileClassException("Failed to compile Orbit subclass", newOrbitBuilder.getErrors());
		}
		CompilerBuilder<Color> newColorBuilder = compiler.compileColor(report);
		if (newColorBuilder.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompileClassException("Failed to compile Color subclass", newColorBuilder.getErrors());
		}
		orbitBuilder = newOrbitBuilder;
		String newASTOrbit = report.getAST().getOrbit().toString();
		changed[0] = !newASTOrbit.equals(astOrbit);
		astOrbit = newASTOrbit;
		colorBuilder = newColorBuilder;
		String newASTColor = report.getAST().getColor().toString();
		changed[1] = !newASTColor.equals(astColor);
		astColor = newASTColor;
		return changed;
	}

	private void updateErrors(String message, List<CompilerError> errors) {
		disableTool = message != null;
		Platform.runLater(() -> {
			errorProperty.setValue(null);
			if (message != null) {
				StringBuilder builder = new StringBuilder();
				builder.append(message);
				if (errors != null) {
					builder.append("\n\n");
					for (CompilerError error : errors) {
						builder.append("Line ");
						builder.append(error.getLine());
						builder.append(": ");
						builder.append(error.getMessage());
						builder.append("\n");
					}
				}
				errorProperty.setValue(builder.toString());
			}
		});
	}

	private void updatePoint(boolean continuous) {
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

	private void updateView(boolean continuous) {
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
			logger.log(Level.WARNING, "Failed to render orbit");
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
			Number size = juliaCoordinator.getInitialSize();
			Number center = juliaCoordinator.getInitialCenter();
			gc.setStroke(renderFactory.createColor(1, 0, 0, 1));
			double[] t = getMandelbrotSession().getView().getTraslation();
			double[] r = getMandelbrotSession().getView().getRotation();
			double tx = t[0];
			double ty = t[1];
			double tz = t[2];
			double a = -r[2] * Math.PI / 180;
			double[] point = getMandelbrotSession().getView().getPoint();
			double zx = point[0];
			double zy = point[1];
			double cx = dw / 2;
			double cy = dh / 2;
			double px = (zx - tx - center.r()) / (tz * size.r());
			double py = (zy - ty - center.i()) / (tz * size.r());
			double qx = Math.cos(a) * px + Math.sin(a) * py;
			double qy = Math.cos(a) * py - Math.sin(a) * px;
			int x = (int)Math.rint(qx * dw + cx);
			int y = (int)Math.rint(qy * dh + cy);
			gc.beginPath();
			gc.moveTo(x - 2, y - 2);
			gc.lineTo(x + 2, y - 2);
			gc.lineTo(x + 2, y + 2);
			gc.lineTo(x - 2, y + 2);
			gc.lineTo(x - 2, y - 2);
			gc.stroke();
		}
	}

	protected void redrawIfOrbitChanged(Canvas canvas) {
		if (redrawOrbit) {
			redrawOrbit = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
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
			if (format.equals(plugin.getId())) {
				return plugin;
			}
		}
		return null;
	}

	public void createExportSession(RendererSize rendererSize) {
		Encoder encoder = createEncoder("PNG");
		if (encoder == null) {
			logger.warning("Cannot find encoder for PNG format");
			//TODO display error
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
								logger.log(Level.WARNING, "Cannot export data to file " + file.getAbsolutePath(), e);
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

		public ZoomTool(boolean zoomin) {
			this.zoomin = zoomin;
		}
		
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
//			zoomin = (e.isPrimaryButtonDown()) ? true : false;
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
}
