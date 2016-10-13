/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.javaFX.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.*;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.session.SessionListener;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.mandelbrot.*;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.*;
import com.nextbreakpoint.nextfractal.mandelbrot.core.*;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererError;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RenderPane extends BorderPane implements ToolContext {
	private static final int FRAME_LENGTH_IN_MILLIS = 20;
	private static final Logger logger = Logger.getLogger(RenderPane.class.getName());
	private final Session session;
	private final ThreadFactory renderThreadFactory;
	private final ThreadFactory juliaRenderThreadFactory;
	private final JavaFXRendererFactory renderFactory;
	private final Stack<MandelbrotView> views = new Stack<>();
	private final StringObservableValue fileProperty;
	private final StringObservableValue errorProperty;
	private final StringObservableValue statusProperty;
	private final BooleanObservableValue hideOrbitProperty;
	private final BooleanObservableValue hideErrorsProperty;
	private final BooleanObservableValue juliaProperty;
	private final RendererCoordinator[] coordinators;
	private final ExecutorService watcherExecutor;
	private RendererCoordinator juliaCoordinator;
	private AnimationTimer timer;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private volatile boolean redrawTrap;
	private volatile boolean redrawOrbit;
	private volatile boolean redrawPoint;
	private volatile boolean disableTool;
	private String astOrbit;
	private String astColor;
	private Tool currentTool;
	private CompilerBuilder<Orbit> orbitBuilder;
	private CompilerBuilder<Color> colorBuilder;
	private List<Number[]> states;

	public RenderPane(Session session, int width, int height, int rows, int columns) {
		this.session = session;
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;

		fileProperty = new StringObservableValue();
		fileProperty.setValue(null);

		errorProperty = new StringObservableValue();
		errorProperty.setValue(null);

		statusProperty = new StringObservableValue();
		statusProperty.setValue(null);

		juliaProperty = new BooleanObservableValue();
		juliaProperty.setValue(false);

		hideOrbitProperty = new BooleanObservableValue();
		hideOrbitProperty.setValue(true);
		
		hideErrorsProperty = new BooleanObservableValue();
		hideErrorsProperty.setValue(true);
		
		renderThreadFactory = new DefaultThreadFactory("MandelbrotRendererCoordinator", true, Thread.MIN_PRIORITY + 2);
		juliaRenderThreadFactory = new DefaultThreadFactory("MandelbrotJuliaRendererCoordinator", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		coordinators = new RendererCoordinator[rows * columns];
		
		Map<String, Integer> hints = new HashMap<>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		createCoordinators(rows, columns, hints);
		
		Map<String, Integer> juliaHints = new HashMap<>();
		juliaHints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
//		juliaHints.put(RendererCoordinator.KEY_PROGRESS, RendererCoordinator.VALUE_SINGLE_PASS);
		juliaCoordinator = createJuliaCoordinator(juliaHints);
		
		getStyleClass().add("mandelbrot");

		BorderPane controls = new BorderPane();
		controls.setMinWidth(width);
		controls.setMaxWidth(width);
		controls.setPrefWidth(width);
		controls.setMinHeight(height);
		controls.setMaxHeight(height);
		controls.setPrefHeight(height);

		BorderPane errors = new BorderPane();
		errors.setMinWidth(width);
		errors.setMaxWidth(width);
		errors.setPrefWidth(width);
		errors.setMinHeight(height);
		errors.setMaxHeight(height);
		errors.setPrefHeight(height);
		errors.getStyleClass().add("errors");
		errors.setVisible(false);

		HBox toolButtons = new HBox(0);
		Button browseButton = new Button("", createIconImage("/icon-grid.png"));
		ToggleButton zoominButton = new ToggleButton("", createIconImage("/icon-zoomin.png"));
		ToggleButton zoomoutButton = new ToggleButton("", createIconImage("/icon-zoomout.png"));
		ToggleButton moveButton = new ToggleButton("", createIconImage("/icon-move.png"));
		ToggleButton rotateButton = new ToggleButton("", createIconImage("/icon-rotate.png"));
		ToggleButton pickButton = new ToggleButton("", createIconImage("/icon-pick.png"));
		ToggleButton juliaButton = new ToggleButton("", createIconImage("/icon-julia.png"));
		ToggleButton orbitButton = new ToggleButton("", createIconImage("/icon-orbit.png"));
		ToggleGroup toolsGroup = new ToggleGroup();
		toolsGroup.getToggles().add(zoominButton);
		toolsGroup.getToggles().add(zoomoutButton);
		toolsGroup.getToggles().add(moveButton);
		toolsGroup.getToggles().add(rotateButton);
		toolsGroup.getToggles().add(pickButton);
		Button homeButton = new Button("", createIconImage("/icon-home.png"));
		browseButton.setTooltip(new Tooltip("Show fractals browser"));
		zoominButton.setTooltip(new Tooltip("Select zoom in tool"));
		zoomoutButton.setTooltip(new Tooltip("Select zoom out tool"));
		moveButton.setTooltip(new Tooltip("Select move tool"));
		rotateButton.setTooltip(new Tooltip("Select rotate tool"));
		pickButton.setTooltip(new Tooltip("Select pick tool"));
		homeButton.setTooltip(new Tooltip("Reset region to initial value"));
		orbitButton.setTooltip(new Tooltip("Toggle orbit and traps"));
		juliaButton.setTooltip(new Tooltip("Toggle Julia mode"));
		toolButtons.getChildren().add(browseButton);
		toolButtons.getChildren().add(homeButton);
		toolButtons.getChildren().add(zoominButton);
		toolButtons.getChildren().add(zoomoutButton);
		toolButtons.getChildren().add(moveButton);
		toolButtons.getChildren().add(rotateButton);
		toolButtons.getChildren().add(pickButton);
		toolButtons.getChildren().add(juliaButton);
		toolButtons.getChildren().add(orbitButton);
		toolButtons.getStyleClass().add("toolbar");

		BrowsePane browsePane = new BrowsePane(width, height);
		browsePane.setTranslateX(-width);

		TranslateTransition browserTransition = createTranslateTransition(browsePane);

		browseButton.setOnAction(e -> {
			showBrowser(browserTransition, a -> {});
			browsePane.reload();
		});

		browsePane.setDelegate(new BrowseDelegate() {
			@Override
			public void didSelectFile(BrowsePane source, File file) {
				updateFile(browsePane, file);
				hideBrowser(browserTransition, a -> {});
			}

			@Override
			public void didClose(BrowsePane source) {
				hideBrowser(browserTransition, a -> {});
			}
		});

		controls.setBottom(toolButtons);
		toolButtons.setOpacity(0.9);

        Canvas fractalCanvas = new Canvas(width, height);
        GraphicsContext gcFractalCanvas = fractalCanvas.getGraphicsContext2D();
        gcFractalCanvas.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gcFractalCanvas.fillRect(0, 0, width, height);

        Canvas orbitCanvas = new Canvas(width, height);
        GraphicsContext gcOrbitCanvas = orbitCanvas.getGraphicsContext2D();
        gcOrbitCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcOrbitCanvas.fillRect(0, 0, width, height);
		orbitCanvas.setVisible(false);
		
		Canvas trapCanvas = new Canvas(width, height);
		GraphicsContext gcTrapCanvas = trapCanvas.getGraphicsContext2D();
		gcTrapCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
		gcTrapCanvas.fillRect(0, 0, width, height);
		trapCanvas.setVisible(false);

		Canvas pointCanvas = new Canvas(width, height);
		GraphicsContext gcPointCanvas = pointCanvas.getGraphicsContext2D();
		gcPointCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
		gcPointCanvas.fillRect(0, 0, width, height);
		pointCanvas.setVisible(false);

		Canvas toolCanvas = new Canvas(width, height);
		GraphicsContext gcToolCanvas = toolCanvas.getGraphicsContext2D();
		gcToolCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
		gcToolCanvas.fillRect(0, 0, width, height);

        Canvas juliaCanvas = new Canvas(width, height);
        GraphicsContext gcJuliaCanvas = juliaCanvas.getGraphicsContext2D();
        gcJuliaCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
        gcJuliaCanvas.fillRect(0, 0, width, height);
        juliaCanvas.setOpacity(0.8);
        juliaCanvas.setVisible(false);

		currentTool = new ToolZoom(this, true);
		zoominButton.setSelected(true);
		zoominButton.setDisable(true);

		FadeTransition toolsTransition = createFadeTransition(controls);

		controls.setOnMouseClicked(e -> {
			if (currentTool != null) {
				currentTool.clicked(e);
			}
		});
		
		controls.setOnMousePressed(e -> {
			fadeOut(toolsTransition, x -> {});
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		
		controls.setOnMouseReleased(e -> {
			fadeIn(toolsTransition, x -> {});
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
		
		this.setOnMouseEntered(e -> fadeIn(toolsTransition, x -> {}));
		
		this.setOnMouseExited(e -> fadeOut(toolsTransition, x -> {}));
		
		getMandelbrotSession().addMandelbrotListener(new MandelbrotListener() {
			@Override
			public void dataChanged(MandelbrotSession session) {
				juliaProperty.setValue(session.getDataAsCopy().isJulia());
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
				juliaProperty.setValue(session.getDataAsCopy().isJulia());
				updateView(continuous);
			}

			@Override
			public void statusChanged(MandelbrotSession session) {
			}

			@Override
			public void errorChanged(MandelbrotSession session) {
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

			@Override
			public void selectGrammar(Session session, String grammar) {
			}
		});
		
		Pane stackPane = new Pane();
		stackPane.getChildren().add(fractalCanvas);
		stackPane.getChildren().add(trapCanvas);
		stackPane.getChildren().add(orbitCanvas);
		stackPane.getChildren().add(pointCanvas);
		stackPane.getChildren().add(juliaCanvas);
		stackPane.getChildren().add(toolCanvas);
		stackPane.getChildren().add(controls);
		stackPane.getChildren().add(errors);
		stackPane.getChildren().add(browsePane);
		setCenter(stackPane);

		homeButton.setOnAction(e -> resetView());

		toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToggleButton)oldValue).setDisable(false);
			}
			if (newValue != null) {
				((ToggleButton)newValue).setDisable(true);
			}
		});
		
		zoominButton.setOnAction(e -> {
			currentTool = new ToolZoom(this, true);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		zoomoutButton.setOnAction(e -> {
			currentTool = new ToolZoom(this, false);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		moveButton.setOnAction(e -> {
			currentTool = new ToolMove(this);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		rotateButton.setOnAction(e -> {
			currentTool = new ToolRotate(this);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		pickButton.setOnAction(e -> {
			if (!getMandelbrotSession().getDataAsCopy().isJulia()) {
				currentTool = new ToolPick(this);
				juliaCanvas.setVisible(true);
				pointCanvas.setVisible(true);
			}
		});
		
		orbitButton.setOnAction(e -> {
//			if (!getMandelbrotSession().getDataAsCopy().isJulia()) {
//				currentTool = new ToolPick(this);
//				juliaCanvas.setVisible(true);
//				pointCanvas.setVisible(true);
//				pickButton.requestFocus();
//			} else {
//				currentTool = new ToolZoom(this, true);
//				juliaCanvas.setVisible(false);
//				pointCanvas.setVisible(false);
//				zoominButton.requestFocus();
//			}
			toggleShowOrbit();
		});
		
		juliaButton.setOnAction(e -> juliaProperty.setValue(!juliaProperty.getValue()));

		hideOrbitProperty.addListener((observable, oldValue, newValue) -> {
			orbitCanvas.setVisible(!newValue);
			trapCanvas.setVisible(!newValue);
		});
		
		juliaProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
//				juliaButton.setGraphic(createIconImage("/icon-mandelbrot.png"));
				setFractalJulia(true);
			} else {
//				juliaButton.setGraphic(createIconImage("/icon-julia.png"));
				setFractalJulia(false);
			}
			if (pickButton.isSelected()) {
				currentTool = new ToolZoom(this, true);
				juliaCanvas.setVisible(false);
				pointCanvas.setVisible(false);
				zoominButton.requestFocus();
				zoominButton.setSelected(true);
			}
			juliaButton.setSelected(newValue);
			pickButton.setDisable(newValue);
		});
		
		errorProperty.addListener((observable, oldValue, newValue) -> updateErrors(errors, newValue));

		statusProperty.addListener((observable, oldValue, newValue) -> getMandelbrotSession().setStatus(newValue));

		Block<MandelbrotData, Exception> updateJulia = data -> {
			if (data.isJulia() && currentTool instanceof ToolPick) {
				currentTool = new ToolZoom(this, true);
				juliaCanvas.setVisible(false);
				pointCanvas.setVisible(false);
				zoominButton.setSelected(true);
			}
			juliaButton.setSelected(data.isJulia());
		};

		fileProperty.addListener((observable, oldValue, newValue) -> loadFractalFromFile(updateJulia, newValue));

		heightProperty().addListener((observable, oldValue, newValue) -> {
			toolButtons.setPrefHeight(newValue.doubleValue() * 0.07);
		});

		stackPane.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst().ifPresent(file -> updateFile(browsePane, file)));

		stackPane.setOnDragOver(x -> Optional.of(x).filter(e -> e.getGestureSource() != stackPane)
				.filter(e -> e.getDragboard().hasFiles()).ifPresent(e -> e.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

		watcherExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("MandelbrotWatcher", true, Thread.MIN_PRIORITY));

		runTimer(fractalCanvas, orbitCanvas, juliaCanvas, pointCanvas, trapCanvas, toolCanvas);
	}

	private void updateFile(BrowsePane pane, File file) {
		fileProperty.setValue(null);
		fileProperty.setValue(file.getAbsolutePath());
	}

	@Override
	public MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	@Override
	public Number getInitialSize() {
		return coordinators[0].getInitialSize();
	}

	@Override
	public Number getInitialCenter() {
		return coordinators[0].getInitialCenter();
	}

	@Override
	public double getZoomSpeed() {
		return 1.05;
	}

	@Override
	public RendererFactory getRendererFactory() {
		return renderFactory;
	}

	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	private void updateErrors(Pane panel, String error) {
		panel.setVisible(error != null);
		getMandelbrotSession().setError(error);
	}

	private void dispose() {
		disposeCoordinators();
		disposeJuliaCoordinator();
	}

	private ImageView createIconImage(String name, double percentage) {
		int size = (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(size);
		image.setFitHeight(size);
		return image;
	}

	private ImageView createIconImage(String name) {
		return createIconImage(name, 0.02);
	}

	private FadeTransition createFadeTransition(Node node) {
		FadeTransition transition = new FadeTransition();
		transition.setNode(node);
		transition.setDuration(Duration.seconds(0.5));
		return transition;
	}

	private TranslateTransition createTranslateTransition(Node node) {
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(node);
		transition.setDuration(Duration.seconds(0.5));
		return transition;
	}

	private void showBrowser(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != 0) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void hideBrowser(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != -((Pane)transition.getNode()).getWidth()) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(-((Pane)transition.getNode()).getWidth());
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void fadeOut(FadeTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getOpacity() != 0) {
			transition.setFromValue(transition.getNode().getOpacity());
			transition.setToValue(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void fadeIn(FadeTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getOpacity() != 0.9) {
			transition.setFromValue(transition.getNode().getOpacity());
			transition.setToValue(0.9);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void loadFractalFromFile(Block<MandelbrotData, Exception> updateJulia, String filename) {
		if (filename == null) {
			return;
		}
		File file = new File(filename);
		try {
			MandelbrotDataStore service = new MandelbrotDataStore();
			MandelbrotData data = service.loadFromFile(file);
			getMandelbrotSession().setCurrentFile(file);
			updateJulia.execute(data);
			getMandelbrotSession().setData(data);
			logger.info(data.toString());
		} catch (Exception x) {
			logger.warning("Cannot read file " + file.getAbsolutePath());
			//TODO display error
		}
	}

	private void resetView() {
		MandelbrotView viewAsCopy = getMandelbrotSession().getViewAsCopy();
		double[] translation = {0, 0, 1, 0};
		double[] rotation = {0, 0, 0, 0};
		double[] scale = {1, 1, 1, 1};
		MandelbrotView view = new MandelbrotView(translation, rotation, scale, viewAsCopy.getPoint(), viewAsCopy.isJulia());
		getMandelbrotSession().setView(view, false);
	}

	private void createCoordinators(int rows, int columns, Map<String, Integer> hints) {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				coordinators[row * columns + column] = new RendererCoordinator(renderThreadFactory, renderFactory, createTile(row, column), hints);
			}
		}
	}

	private RendererCoordinator createJuliaCoordinator(Map<String, Integer> hints) {
		return new RendererCoordinator(juliaRenderThreadFactory, renderFactory, createSingleTile(200, 200), hints);
	}

	private void disposeCoordinators() {
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].abort();
			}
		}
		if (juliaCoordinator != null) {
			juliaCoordinator.abort();
		}
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null) {
				coordinators[i].waitFor();
				coordinators[i].dispose();
				coordinators[i] = null;
			}
		}
		if (juliaCoordinator != null) {
			juliaCoordinator.waitFor();
			juliaCoordinator.dispose();
			juliaCoordinator = null;
		}
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

	private void runTimer(Canvas fractalCanvas, Canvas orbitCanvas, Canvas juliaCanvas, Canvas pointCanvas, Canvas trapCanvas, Canvas toolCanvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if (time - last > FRAME_LENGTH_IN_MILLIS) {
					if (!disableTool) {
						processRenderErrors();
//						redrawIfPixelsChanged(fractalCanvas);
//						redrawIfJuliaPixelsChanged(juliaCanvas);
						redrawIfPointChanged(pointCanvas);
						redrawIfOrbitChanged(orbitCanvas);
						redrawIfTrapChanged(trapCanvas);
						redrawIfToolChanged(toolCanvas);
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
		views.push(getMandelbrotSession().getViewAsCopy());
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
		RendererSize tileBorder = new RendererSize(0, 0);
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
		MandelbrotView currentView = getMandelbrotSession().getViewAsCopy();
		if (!julia && currentView.isJulia()) {
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, currentView.getPoint(), false);
			getMandelbrotSession().setView(view, false);
		} else if (julia && !currentView.isJulia()) {
			MandelbrotView oldView = popView();
			pushView();
			MandelbrotView view = new MandelbrotView(oldView != null ? oldView.getTraslation() : new double[] { 0, 0, 1, 0 }, oldView != null ? oldView.getRotation() : new double[] { 0, 0, 0, 0 }, oldView != null ? oldView.getScale() : new double[] { 1, 1, 1, 1 }, currentView.getPoint(), true);
			getMandelbrotSession().setView(view, false);
		}
	}

	private void toggleShowOrbit() {
		if (disableTool) {
			return;
		}
		hideOrbitProperty.setValue(!hideOrbitProperty.getValue());
	}
	
	private void updateFractal(Session session) {
		try {
			boolean[] changed = createOrbitAndColor();
			updateCompilerErrors(null, null, null);
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
			MandelbrotView oldView = getMandelbrotSession().getViewAsCopy();
			double[] translation = oldView.getTraslation();
			double[] rotation = oldView.getRotation();
			double[] scale = oldView.getScale();
			double[] point = oldView.getPoint();
			boolean julia = oldView.isJulia();
			abortCoordinators();
			if (juliaCoordinator != null) {
				juliaCoordinator.abort();
			}
			joinCoordinators();
			if (juliaCoordinator != null) {
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
					view.setTraslation(new Double4D(translation));
					view.setRotation(new Double4D(rotation));
					view.setScale(new Double4D(scale));
					view.setState(new Integer4D(0, 0, 0, 0));
					view.setJulia(julia);
					view.setPoint(new Number(point));
					coordinator.setView(view);
				}
			}
			if (juliaCoordinator != null) {
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
				view.setTraslation(new Double4D(translation));
				view.setRotation(new Double4D(rotation));
				view.setScale(new Double4D(scale));
				view.setState(new Integer4D(0, 0, 0, 0));
				view.setJulia(true);
				view.setPoint(new Number(point));
				juliaCoordinator.setView(view);
			}
			startCoordinators();
			if (juliaCoordinator != null) {
				juliaCoordinator.run();
			}
			redrawTrap = true;
			redrawOrbit = true;
			redrawPoint = true;
			if (!julia) {
				states = renderOrbit(point);
				logger.info("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
			}
		} catch (CompilerSourceException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateCompilerErrors(e.getMessage(), e.getErrors(), null);
		} catch (CompilerClassException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateCompilerErrors(e.getMessage(), e.getErrors(), e.getSource());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateCompilerErrors(e.getMessage(), null, null);
		}
	}

	private boolean[] createOrbitAndColor() throws CompilerSourceException, CompilerClassException, ClassNotFoundException, IOException {
		CompilerReport report = getMandelbrotSession().getReport();
		if (report.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompilerSourceException("Failed to compile source", report.getErrors());
		}
		Compiler compiler = new Compiler();
		boolean[] changed = new boolean[] { false, false };
		CompilerBuilder<Orbit> newOrbitBuilder = compiler.compileOrbit(report);
		if (newOrbitBuilder.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompilerClassException("Failed to compile Orbit subclass", report.getOrbitSource(), newOrbitBuilder.getErrors());
		}
		CompilerBuilder<Color> newColorBuilder = compiler.compileColor(report);
		if (newColorBuilder.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompilerClassException("Failed to compile Color subclass", report.getColorSource(), newColorBuilder.getErrors());
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

	private void updateCompilerErrors(String message, List<CompilerError> errors, String source) {
		disableTool = message != null;
		Platform.runLater(() -> {
			statusProperty.setValue(null);
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
				if (source != null) {
					builder.append("\n\n");
					builder.append(source);
					builder.append("\n");
				}
				errorProperty.setValue(builder.toString());
				statusProperty.setValue(builder.toString());
			} else {
				statusProperty.setValue("Source compiled");
			}
		});
	}

	private void updateRendererErrors(String message, List<RendererError> errors, String source) {
		disableTool = message != null;
		Platform.runLater(() -> {
			statusProperty.setValue(null);
			errorProperty.setValue(null);
			if (message != null) {
				StringBuilder builder = new StringBuilder();
				builder.append(message);
				if (errors != null) {
					builder.append("\n\n");
					for (RendererError error : errors) {
						builder.append("Line ");
						builder.append(error.getLine());
						builder.append(": ");
						builder.append(error.getMessage());
						builder.append("\n");
					}
				}
				if (source != null) {
					builder.append("\n\n");
					builder.append(source);
					builder.append("\n");
				}
				errorProperty.setValue(builder.toString());
				statusProperty.setValue(builder.toString());
			} else {
				statusProperty.setValue("Rendering completed");
			}
		});
	}

	private void updatePoint(boolean continuous) {
		MandelbrotView oldView = getMandelbrotSession().getViewAsCopy();
		boolean julia = oldView.isJulia();
		double[] point = oldView.getPoint();
		if (julia) {
			abortCoordinators();
			joinCoordinators();
			double[] translation = oldView.getTraslation();
			double[] rotation = oldView.getRotation();
			double[] scale = oldView.getScale();
			for (int i = 0; i < coordinators.length; i++) {
				RendererCoordinator coordinator = coordinators[i];
				if (coordinator != null) {
					RendererView view = new RendererView();
					view.setTraslation(new Double4D(translation));
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
			redrawPoint = true;
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
			}
		}
	}

	private void updateView(boolean continuous) {
		MandelbrotView oldView = getMandelbrotSession().getViewAsCopy();
		double[] translation = oldView.getTraslation();
		double[] rotation = oldView.getRotation();
		double[] scale = oldView.getScale();
		double[] point = oldView.getPoint();
		boolean julia = oldView.isJulia();
		abortCoordinators();
		joinCoordinators();
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				RendererView view = new RendererView();
				view.setTraslation(new Double4D(translation));
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
		redrawPoint = true;
		redrawTrap = true;
		if (!julia && !continuous) {
			states = renderOrbit(point);
			logger.info("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
		}
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

	private void processRenderErrors() {
		if (coordinators != null && coordinators.length > 0) {
			RendererCoordinator coordinator = coordinators[0];
			if (coordinator != null) {
				RendererError error = coordinator.getError();
				if (error != null) {
					updateRendererErrors("Error", Collections.singletonList(error), null);
				} else {
					updateRendererErrors(null, null, null);
				}
			}
		}
	}

	private void abortCoordinators() {
		visitCoordinators(coordinator -> true, coordinator -> coordinator.abort());
	}

	private void joinCoordinators() {
		visitCoordinators(coordinator -> true, coordinator -> coordinator.waitFor());
	}

	private void startCoordinators() {
		visitCoordinators(coordinator -> true, coordinator -> coordinator.run());
	}

	private void redrawIfPixelsChanged(Canvas canvas) {
		RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
		visitCoordinators(coordinator -> coordinator.isPixelsChanged(), coordinator -> coordinator.drawImage(gc, 0, 0));
	}

	private void visitCoordinators(Predicate<RendererCoordinator> predicate, Consumer<RendererCoordinator> consumer) {
		for (int i = 0; i < coordinators.length; i++) {
			if (coordinators[i] != null && predicate.test(coordinators[i])) {
				consumer.accept(coordinators[i]);
			}
		}
	}

	private void redrawIfJuliaPixelsChanged(Canvas canvas) {
		MandelbrotView view = getMandelbrotSession().getViewAsCopy();
		if (!view.isJulia() && juliaCoordinator != null && juliaCoordinator.isPixelsChanged()) {
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			double dw = canvas.getWidth();
			double dh = canvas.getHeight();
			gc.clearRect(0, 0, (int)dw, (int)dh);
			juliaCoordinator.drawImage(gc, 0, 0);
			Number size = juliaCoordinator.getInitialSize();
			Number center = juliaCoordinator.getInitialCenter();
			gc.setStroke(renderFactory.createColor(1, 1, 0, 1));
		}
	}

	private void redrawIfPointChanged(Canvas canvas) {
		if (redrawPoint) {
			redrawPoint = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				MandelbrotView view = getMandelbrotSession().getViewAsCopy();
				double[] t = view.getTraslation();
				double[] r = view.getRotation();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				double cx = dw / 2;
				double cy = dh / 2;
				gc.setStroke(renderFactory.createColor(1, 1, 0, 1));
				double[] point = view.getPoint();
				double zx = point[0];
				double zy = point[1];
				double px = (zx - tx - center.r()) / (tz * size.r());
				double py = (zy - ty - center.i()) / (tz * size.r());
				double qx = Math.cos(a) * px + Math.sin(a) * py;
				double qy = Math.cos(a) * py - Math.sin(a) * px;
				int x = (int)Math.rint(qx * dw + cx);
				int y = (int)Math.rint(cy - qy * dh);
				gc.beginPath();
				gc.moveTo(x - 2, y - 2);
				gc.lineTo(x + 2, y - 2);
				gc.lineTo(x + 2, y + 2);
				gc.lineTo(x - 2, y + 2);
				gc.lineTo(x - 2, y - 2);
				gc.stroke();
			}
		}
	}

	private void redrawIfOrbitChanged(Canvas canvas) {
		if (redrawOrbit) {
			redrawOrbit = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				MandelbrotView view = getMandelbrotSession().getViewAsCopy();
				double[] t = view.getTraslation();
				double[] r = view.getRotation();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				double cx = dw / 2;
				double cy = dh / 2;
				gc.setStroke(renderFactory.createColor(1, 0, 0, 1));
				Number[] state = states.get(0);
				double zx = state[0].r();
				double zy = state[0].i();
				double px = (zx - tx - center.r()) / (tz * size.r());
				double py = (zy - ty - center.i()) / (tz * size.r());
				double qx = Math.cos(a) * px + Math.sin(a) * py;
				double qy = Math.cos(a) * py - Math.sin(a) * px;
				int x = (int)Math.rint(qx * dw + cx);
				int y = (int)Math.rint(cy - qy * dh);
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
					y = (int)Math.rint(cy - qy * dh);
					gc.lineTo(x, y);
				}
				gc.stroke();
			}
		}
	}

	private void redrawIfTrapChanged(Canvas canvas) {
		if (redrawTrap) {
			redrawTrap = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				MandelbrotView view = getMandelbrotSession().getViewAsCopy();
				double[] t = view.getTraslation();
				double[] r = view.getRotation();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				gc.setStroke(renderFactory.createColor(1, 1, 0, 1));
				List<Trap> traps = coordinators[0].getTraps();
				for (Trap trap : traps) {
					List<Number> points = trap.toPoints();
					if (points.size() > 0) {
						double zx = points.get(0).r();
						double zy = points.get(0).i();
						double cx = dw / 2;
						double cy = dh / 2;
						double px = (zx - tx - center.r()) / (tz * size.r());
						double py = (zy - ty - center.i()) / (tz * size.r());
						double qx = Math.cos(a) * px + Math.sin(a) * py;
						double qy = Math.cos(a) * py - Math.sin(a) * px;
						int x = (int)Math.rint(qx * dw + cx);
						int y = (int)Math.rint(cy - qy * dh);
						gc.beginPath();
						gc.moveTo(x, y);
						for (int i = 1; i < points.size(); i++) {
							zx = points.get(i).r();
							zy = points.get(i).i();
							px = (zx - tx - center.r()) / (tz * size.r());
							py = (zy - ty - center.i()) / (tz * size.r());
							qx = Math.cos(a) * px + Math.sin(a) * py;
							qy = Math.cos(a) * py - Math.sin(a) * px;
							x = (int)Math.rint(qx * dw + cx);
							y = (int)Math.rint(cy - qy * dh);
							gc.lineTo(x, y);
						}
						gc.stroke();
					}
				}
			}
		}
	}

	private void redrawIfToolChanged(Canvas canvas) {
		Optional.ofNullable(currentTool).filter(tool -> tool.isChanged()).ifPresent(tool -> tool.draw(renderFactory.createGraphicsContext(canvas.getGraphicsContext2D())));
	}

	private void watchFolder(BrowsePane pane, File file) {
		Future<?> future = watcherExecutor.submit(() -> Block.create(a -> watchLoop(pane, file.toPath()))
				.tryExecute().ifFailure(e -> logger.log(Level.WARNING, "Can't create watcher for location {}", file.getAbsolutePath())));
	}

	private void watchLoop(BrowsePane pane, Path dir) throws IOException {
		WatchService watcher = FileSystems.getDefault().newWatchService();

		WatchKey watchKey = null;

		try {
			watchKey = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			try {
				for (;;) {
					WatchKey key = watcher.take();

					for (WatchEvent<?> event: key.pollEvents()) {
						WatchEvent.Kind<?> kind = event.kind();

						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}

						WatchEvent<Path> ev = (WatchEvent<Path>)event;

						Path filename = ev.context();

						try {
							Path child = dir.resolve(filename);
							if (!Files.probeContentType(child).equals("text/plain")) {
								logger.log(Level.WARNING, "New file {} is not a plain text file", filename);
								continue;
							}
						} catch (IOException x) {
							logger.log(Level.WARNING, "Can't resolve file {}", filename);
							continue;
						}

						Platform.runLater(() -> pane.reload());
					}

					boolean valid = key.reset();
					if (!valid) {
						break;
					}
				}
			} catch (InterruptedException x) {
			}
		} catch (IOException x) {
			logger.log(Level.WARNING, "Can't register watcher on directory {}", dir.getFileName());
		} finally {
			if (watchKey != null) {
				watchKey.cancel();
			}

			watcher.close();
		}
	}
}
