/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Error;
import com.nextbreakpoint.nextfractal.core.javafx.EventBus;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.javafx.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javafx.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.javafx.render.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.utils.Double2D;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.core.utils.Integer4D;
import com.nextbreakpoint.nextfractal.core.utils.Time;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotMetadata;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotOptions;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerClassException;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.javafx.Icons.computeOptimalLargeIconPercentage;
import static com.nextbreakpoint.nextfractal.core.javafx.Icons.createIconImage;

public class RenderPane extends BorderPane {
	private static final int FRAME_LENGTH_IN_MILLIS = 1000 / 25;
	private static final Logger logger = Logger.getLogger(RenderPane.class.getName());
	private final JavaFXRendererFactory renderFactory;
	private final StringObservableValue errorProperty;
	private final StringObservableValue statusProperty;
	private final BooleanObservableValue showTrapsProperty;
	private final BooleanObservableValue showOrbitProperty;
	private final BooleanObservableValue showPreviewProperty;
	private final BooleanObservableValue hideErrorsProperty;
	private final BooleanObservableValue timeProperty;
	private final BooleanObservableValue juliaProperty;
	private final BooleanObservableValue captureProperty;
	private final RendererCoordinator[] coordinators;
	private RendererCoordinator juliaCoordinator;
	private AnimationTimer timer;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private double zoomSpeed = 1.025;
	private volatile boolean redrawTraps;
	private volatile boolean redrawOrbit;
	private volatile boolean redrawPoint;
	private volatile boolean hasError;
	private volatile boolean playback;
	private volatile boolean timeAnimation;
	private volatile MandelbrotSession mandelbrotSession;
	private String astOrbit;
	private String astColor;
	private Tool currentTool;
	private CompilerBuilder<Orbit> orbitBuilder;
	private CompilerBuilder<Color> colorBuilder;
	private List<Number[]> states = new ArrayList<>();

	public RenderPane(Session session, EventBus eventBus, int width, int height, int rows, int columns) {
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;

		mandelbrotSession = (MandelbrotSession)session;

		errorProperty = new StringObservableValue();
		errorProperty.setValue(null);

		statusProperty = new StringObservableValue();
		statusProperty.setValue(null);

		timeProperty = new BooleanObservableValue();
		timeProperty.setValue(false);

		juliaProperty = new BooleanObservableValue();
		juliaProperty.setValue(false);

		captureProperty = new BooleanObservableValue();
		captureProperty.setValue(false);

		showTrapsProperty = new BooleanObservableValue();
		showTrapsProperty.setValue(false);

		showOrbitProperty = new BooleanObservableValue();
		showOrbitProperty.setValue(false);

		showPreviewProperty = new BooleanObservableValue();
		showPreviewProperty.setValue(false);

		hideErrorsProperty = new BooleanObservableValue();
		hideErrorsProperty.setValue(true);
		
		renderFactory = new JavaFXRendererFactory();

		coordinators = new RendererCoordinator[rows * columns];
		
		Map<String, Integer> hints = new HashMap<>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		createCoordinators(rows, columns, hints);
		
		Map<String, Integer> juliaHints = new HashMap<>();
		juliaHints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
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
		ToggleButton zoominButton = new ToggleButton("", createIconImage(getClass(), "/icon-zoomin.png", computeOptimalLargeIconPercentage()));
		ToggleButton zoomoutButton = new ToggleButton("", createIconImage(getClass(), "/icon-zoomout.png", computeOptimalLargeIconPercentage()));
		ToggleButton moveButton = new ToggleButton("", createIconImage(getClass(), "/icon-move.png", computeOptimalLargeIconPercentage()));
		ToggleButton rotateButton = new ToggleButton("", createIconImage(getClass(), "/icon-rotate.png", computeOptimalLargeIconPercentage()));
		ToggleButton pickButton = new ToggleButton("", createIconImage(getClass(), "/icon-pick.png", computeOptimalLargeIconPercentage()));
		ToggleButton juliaButton = new ToggleButton("", createIconImage(getClass(), "/icon-julia.png", computeOptimalLargeIconPercentage()));
		ToggleButton orbitButton = new ToggleButton("", createIconImage(getClass(), "/icon-orbit.png", computeOptimalLargeIconPercentage()));
		ToggleButton captureButton = new ToggleButton("", createIconImage(getClass(), "/icon-capture.png", computeOptimalLargeIconPercentage()));
		ToggleButton timeButton = new ToggleButton("", createIconImage(getClass(), "/icon-cron.png", computeOptimalLargeIconPercentage()));
		ToggleGroup toolsGroup = new ToggleGroup();
		toolsGroup.getToggles().add(zoominButton);
		toolsGroup.getToggles().add(zoomoutButton);
		toolsGroup.getToggles().add(moveButton);
		toolsGroup.getToggles().add(rotateButton);
		toolsGroup.getToggles().add(pickButton);
		Button homeButton = new Button("", createIconImage(getClass(), "/icon-home.png", computeOptimalLargeIconPercentage()));
		zoominButton.setTooltip(new Tooltip("Select zoom in tool"));
		zoomoutButton.setTooltip(new Tooltip("Select zoom out tool"));
		moveButton.setTooltip(new Tooltip("Select move tool"));
		rotateButton.setTooltip(new Tooltip("Select rotate tool"));
		pickButton.setTooltip(new Tooltip("Select pick tool"));
		homeButton.setTooltip(new Tooltip("Reset region to initial value"));
		orbitButton.setTooltip(new Tooltip("Show/hide orbit and traps"));
		juliaButton.setTooltip(new Tooltip("Enable/disable Julia mode"));
		captureButton.setTooltip(new Tooltip("Enable/disable capture mode"));
		timeButton.setTooltip(new Tooltip("Enable/disable time animation"));
		toolButtons.getChildren().add(homeButton);
		toolButtons.getChildren().add(zoominButton);
		toolButtons.getChildren().add(zoomoutButton);
		toolButtons.getChildren().add(moveButton);
		toolButtons.getChildren().add(rotateButton);
		toolButtons.getChildren().add(pickButton);
		toolButtons.getChildren().add(juliaButton);
		toolButtons.getChildren().add(orbitButton);
		toolButtons.getChildren().add(captureButton);
		toolButtons.getChildren().add(timeButton);
		toolButtons.getStyleClass().add("toolbar");

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

		ToolContext context = new ToolContext() {
			public Number getInitialSize() {
				return coordinators[0].getInitialSize();
			}

			public Number getInitialCenter() {
				return coordinators[0].getInitialCenter();
			}

			public double getZoomSpeed() {
				return zoomSpeed;
			}

			public RendererFactory getRendererFactory() {
				return renderFactory;
			}

			@Override
			public double getWidth() {
				return width;
			}

			@Override
			public double getHeight() {
				return height;
			}

			@Override
			public MandelbrotMetadata getMetadata() {
				return (MandelbrotMetadata) mandelbrotSession.getMetadata();
			}

			@Override
			public void setPoint(MandelbrotMetadata metadata, boolean continuous, boolean appendHistory) {
				eventBus.propagateEvent("render-point-changed", new MandelbrotSession(mandelbrotSession.getScript(), metadata), continuous, appendHistory);
			}

			@Override
			public void setView(MandelbrotMetadata metadata, boolean continuous, boolean appendHistory) {
				eventBus.propagateEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), metadata), continuous, appendHistory);
			}

			@Override
			public void setTime(MandelbrotMetadata metadata, boolean continuous, boolean appendHistory) {
				eventBus.propagateEvent("render-time-changed", new MandelbrotSession(mandelbrotSession.getScript(), metadata), continuous, appendHistory);
			}
		};

		currentTool = new ToolZoom(context, true);
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
			eventBus.postEvent("hide-controls", true);
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		
		controls.setOnMouseReleased(e -> {
			fadeIn(toolsTransition, x -> {});
			eventBus.postEvent("hide-controls", false);
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

		this.setOnMouseEntered(e -> {
			fadeIn(toolsTransition, x -> {});
			controls.requestFocus();
		});
		
		this.setOnMouseExited(e -> {
			fadeOut(toolsTransition, x -> {});
		});

		EventHandler<KeyEvent> eventHandler = keyEvent -> {
			switch (keyEvent.getCode()) {
				case DIGIT1: {
					zoomSpeed = 1.005;
					break;
				}
				case DIGIT2: {
					zoomSpeed = 1.01;
					break;
				}
				case DIGIT3: {
					zoomSpeed = 1.025;
					break;
				}
				case DIGIT4: {
					zoomSpeed = 1.05;
					break;
				}
				case DIGIT5: {
					zoomSpeed = 1.10;
					break;
				}
				case T: {
					showTrapsProperty.setValue(!showTrapsProperty.getValue());
					eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, true);
					break;
				}
				case O: {
					showOrbitProperty.setValue(!showOrbitProperty.getValue());
					eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, true);
					break;
				}
				case P: {
					showPreviewProperty.setValue(!showPreviewProperty.getValue());
					eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, true);
					break;
				}
			}
		};

		controls.addEventHandler(KeyEvent.KEY_RELEASED, eventHandler);

		Pane stackPane = new Pane();
		stackPane.getChildren().add(fractalCanvas);
		stackPane.getChildren().add(trapCanvas);
		stackPane.getChildren().add(orbitCanvas);
		stackPane.getChildren().add(pointCanvas);
		stackPane.getChildren().add(juliaCanvas);
		stackPane.getChildren().add(toolCanvas);
		stackPane.getChildren().add(controls);
		stackPane.getChildren().add(errors);
		setCenter(stackPane);

		homeButton.setOnAction(e -> resetView(eventBus));

		toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToggleButton)oldValue).setDisable(false);
			}
			if (newValue != null) {
				((ToggleButton)newValue).setDisable(true);
			}
		});

		zoominButton.setOnAction(e -> {
			boolean appendToHistory = currentTool instanceof ToolPick;
			currentTool = new ToolZoom(context, true);
			showPreviewProperty.setValue(false);
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, appendToHistory);
		});
		
		zoomoutButton.setOnAction(e -> {
			boolean appendToHistory = currentTool instanceof ToolPick;
			currentTool = new ToolZoom(context, false);
			showPreviewProperty.setValue(false);
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, appendToHistory);
		});
		
		moveButton.setOnAction(e -> {
			boolean appendToHistory = currentTool instanceof ToolPick;
			currentTool = new ToolMove(context);
			showPreviewProperty.setValue(false);
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, appendToHistory);
		});
		
		rotateButton.setOnAction(e -> {
			boolean appendToHistory = currentTool instanceof ToolPick;
			currentTool = new ToolRotate(context);
			showPreviewProperty.setValue(false);
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, appendToHistory);
		});
		
		pickButton.setOnAction(e -> {
			boolean appendToHistory = !(currentTool instanceof ToolPick);
			currentTool = new ToolPick(context);
			showPreviewProperty.setValue(true);
			MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
			MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.getTime(), false, createMandelbrotOptions(metadata));
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), newMetadata), false, appendToHistory);
			juliaProperty.setValue(false);
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
//			if (!hasError) {
                showOrbitProperty.setValue(!showOrbitProperty.getValue());
				eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, true);
//            }
		});

		juliaButton.setOnAction(e -> juliaProperty.setValue(juliaButton.isSelected()));

		captureButton.setOnAction(e -> {
			captureProperty.setValue(captureButton.isSelected());
			if (captureProperty.getValue()) {
				eventBus.postEvent("capture-session", "start");
			} else {
				eventBus.postEvent("capture-session", "stop");
			}
		});

		timeButton.setOnAction(e -> {
			timeProperty.setValue(timeButton.isSelected());
			if (timeProperty.getValue()) {
				eventBus.postEvent("time-animation", "start");
			} else {
				eventBus.postEvent("time-animation", "stop");
			}
		});

		captureProperty.addListener((observable, oldValue, newValue) -> {
			captureButton.setSelected(newValue);
		});

		timeProperty.addListener((observable, oldValue, newValue) -> {
			timeAnimation = newValue;
			timeButton.setSelected(newValue);
		});

		eventBus.subscribe("capture-session-started", event -> captureProperty.setValue(true));

		eventBus.subscribe("capture-session-stopped", event -> captureProperty.setValue(false));

		showTrapsProperty.addListener((observable, oldValue, newValue) -> {
			trapCanvas.setVisible(newValue);
		});

		showOrbitProperty.addListener((observable, oldValue, newValue) -> {
			orbitCanvas.setVisible(newValue);
			pointCanvas.setVisible(newValue);
			orbitButton.setSelected(newValue);
			pointCanvas.setVisible(newValue || showPreviewProperty.getValue());
		});

		showPreviewProperty.addListener((observable, oldValue, newValue) -> {
			juliaCanvas.setVisible(newValue);
			pointCanvas.setVisible(newValue || showOrbitProperty.getValue());
		});

		juliaProperty.addListener((observable, oldValue, newValue) -> {
//			if (!hasError) {
				if (newValue) {
//				juliaButton.setGraphic(createIconImage("/icon-mandelbrot.png"));
					setFractalJulia(eventBus, true);
				} else {
//				juliaButton.setGraphic(createIconImage("/icon-julia.png"));
					setFractalJulia(eventBus, false);
				}
//			}
			if (newValue) {
				if (currentTool instanceof ToolPick) {
					currentTool = new ToolZoom(context, true);
//					zoominButton.requestFocus();
					zoominButton.setSelected(true);
				}
				showPreviewProperty.setValue(false);
//				eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), createMetadataWithOptions()), false, false);
			}
			juliaButton.setSelected(newValue);
//			pickButton.setDisable(newValue);
		});
		
		errorProperty.addListener((observable, oldValue, newValue) -> {
			errors.setVisible(newValue != null);
			eventBus.postEvent("render-error-changed", newValue);
		});

		statusProperty.addListener((observable, oldValue, newValue) -> {
			eventBus.postEvent("render-status-changed", newValue);
		});

//		Block<MandelbrotMetadata, Exception> updateJulia = metadata -> {
//			if (metadata.isJulia() && currentTool instanceof ToolPick) {
//				currentTool = new ToolZoom(context, true);
//				juliaCanvas.setVisible(false);
//				pointCanvas.setVisible(false);
//				zoominButton.setSelected(true);
//			}
//			juliaButton.setSelected(metadata.isJulia());
//		};

		heightProperty().addListener((observable, oldValue, newValue) -> {
			toolButtons.setPrefHeight(newValue.doubleValue() * 0.07);
		});

		stackPane.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst()
			.ifPresent(file -> eventBus.postEvent("editor-load-file", file)));

		stackPane.setOnDragOver(x -> Optional.of(x).filter(e -> e.getGestureSource() != stackPane)
			.filter(e -> e.getDragboard().hasFiles()).ifPresent(e -> e.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

		runTimer(fractalCanvas, orbitCanvas, juliaCanvas, pointCanvas, trapCanvas, toolCanvas);

		eventBus.subscribe("session-report-changed", event -> {
//			timeProperty.setValue(false);
			List<Error> lastErrors = updateReport((CompilerReport) event[0]);
			if (lastErrors.size() == 0) {
				notifySessionChanged(eventBus, (MandelbrotSession)event[1], (Boolean)event[2], true, (Boolean)event[3]);
			}
		});

//		eventBus.subscribe("session-data-loaded", event -> loadData(event));

		eventBus.subscribe("session-data-loaded", event -> restoreTool(context, pickButton, zoominButton));

		eventBus.subscribe("session-data-changed", event -> updateData((MandelbrotSession) event[0], (Boolean) event[1], (Boolean) event[2]));

		eventBus.subscribe("session-data-changed", event -> restoreView((MandelbrotSession) event[0]));

		eventBus.subscribe("session-data-changed", event -> updateJulia((MandelbrotSession) event[0]));

		eventBus.subscribe("capture-clips-start", event -> playback = true);

		eventBus.subscribe("capture-clips-stop", event -> playback = false);

		eventBus.subscribe("playback-data-load", event -> timeProperty.setValue(false));

		eventBus.subscribe("playback-data-load", event -> loadData((MandelbrotSession) event[0], (Boolean) event[1], (Boolean) event[2]));

		eventBus.subscribe("playback-data-load", event -> restoreView((MandelbrotSession) event[0]));

		eventBus.subscribe("playback-data-change", event -> updateData((MandelbrotSession) event[0], (Boolean) event[1], (Boolean) event[2]));

		eventBus.subscribe("playback-data-change", event -> restoreView((MandelbrotSession) event[0]));

		eventBus.subscribe("editor-source-changed", event -> {
//			MandelbrotSession newSession = new MandelbrotSession((String) event[0], (MandelbrotMetadata) mandelbrotSession.getMetadata());
//            notifySessionChanged(eventBus, newSession, false, true);
        });

		eventBus.subscribe("editor-data-changed", event -> {
			MandelbrotSession newSession = (MandelbrotSession) event[0];
			Boolean continuous = (Boolean) event[1];
			Boolean appendHistory = (Boolean) event[2];
			notifySessionChanged(eventBus, newSession, continuous, true, appendHistory && !continuous);
        });

		eventBus.subscribe("render-data-changed", event -> {
			MandelbrotSession newSession = (MandelbrotSession) event[0];
			Boolean continuous = (Boolean) event[1];
			Boolean appendHistory = (Boolean) event[2];
			notifySessionChanged(eventBus, newSession, continuous, false, appendHistory && !continuous);
		});

		eventBus.subscribe("render-point-changed", event -> {
			MandelbrotSession newSession = (MandelbrotSession) event[0];
			Boolean continuous = (Boolean) event[1];
			Boolean appendHistory = (Boolean) event[2];
			notifySessionChanged(eventBus, newSession, continuous, false, appendHistory && !continuous);
		});

		eventBus.subscribe("render-mode-changed", event -> {
			MandelbrotSession newSession = (MandelbrotSession) event[0];
			Boolean continuous = (Boolean) event[1];
			Boolean appendHistory = (Boolean) event[2];
			notifySessionChanged(eventBus, newSession, continuous, false, appendHistory && !continuous);
		});

		eventBus.subscribe("render-time-changed", event -> {
			MandelbrotSession newSession = (MandelbrotSession) event[0];
			Boolean continuous = (Boolean) event[1];
			Boolean appendHistory = (Boolean) event[2];
			notifySessionChanged(eventBus, newSession, continuous, true, appendHistory && !continuous);
		});

		eventBus.subscribe("render-status-changed", event -> {
			eventBus.postEvent("session-status-changed", event);
		});

		eventBus.subscribe("render-error-changed", event -> {
			eventBus.postEvent("session-error-changed", event);
		});

		eventBus.subscribe("session-terminated", event -> dispose());

		eventBus.subscribe("time-animation", event -> handleTimeAnimationAction(eventBus, (String)event[0]));

		Platform.runLater(() -> controls.requestFocus());
	}

	private void handleTimeAnimationAction(EventBus eventBus, String action) {
		if ("start".equals(action)) startTimeAnimation(eventBus);
		if ("stop".equals(action)) stopTimeAnimation(eventBus);
	}

	private void startTimeAnimation(EventBus eventBus) {
		eventBus.postEvent("history-add-session", mandelbrotSession);
	}

	private void stopTimeAnimation(EventBus eventBus) {
		eventBus.postEvent("history-add-session", mandelbrotSession);
	}

//	private void updateTime(EventBus eventBus, double seconds) {
//			MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
//			Time newTime = new Time(metadata.getTime().getValue() + seconds, metadata.getTime().getScale());
//			MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), newTime, metadata.isJulia(), metadata.getOptions());
//			eventBus.postEvent("render-time-changed", new MandelbrotSession(mandelbrotSession.getScript(), newMetadata), true, false);
//	}

	private MandelbrotMetadata createMetadataWithOptions() {
		MandelbrotMetadata oldMetatada = (MandelbrotMetadata) mandelbrotSession.getMetadata();
		return new MandelbrotMetadata(oldMetatada, createMandelbrotOptions(oldMetatada));
	}

	private MandelbrotOptions createMandelbrotOptions(MandelbrotMetadata metadata) {
		Double2D previewOrigin = metadata.getOptions().getPreviewOrigin();
		Double2D previewSize = metadata.getOptions().getPreviewSize();
		Boolean showPreview = showPreviewProperty.getValue();
		Boolean showOrbit = showOrbitProperty.getValue();
		Boolean showTrap = showTrapsProperty.getValue();
		return new MandelbrotOptions(showPreview, showTrap, showOrbit, showOrbit || showPreview, previewOrigin, previewSize);
	}

	private void notifySessionChanged(EventBus eventBus, MandelbrotSession newSession, boolean continuous, boolean timeAnimation, boolean historyAppend) {
        eventBus.propagateEvent("session-data-changed", newSession, continuous, timeAnimation, false);
		if (historyAppend) {
			eventBus.postEvent("history-add-session", newSession);
		}
    }

	private CompilerReport generateReport(String text) throws Exception {
		return new Compiler(Compiler.class.getPackage().getName(), "Compile" + System.nanoTime()).compileReport(text);
	}

	private void loadData(MandelbrotSession session, Boolean continuous, boolean timeAnimation) {
		Try.of(() -> generateReport(session.getScript())).filter(report -> ((CompilerReport)report).getErrors().size() == 0).ifPresent(report -> {
			List<Error> errors = updateReport(report);
			if (errors.size() == 0) {
				updateData(session, continuous, timeAnimation);
			}
		});
	}

	private void updateJulia(MandelbrotSession mandelbrotSession) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) this.mandelbrotSession.getMetadata();
		juliaProperty.setValue(metadata.isJulia());
	}

	private void restoreTool(ToolContext context, ToggleButton pickButton, ToggleButton zoominButton) {
		timeProperty.setValue(false);
		if (showPreviewProperty.getValue()) {
			if (juliaProperty.getValue()) {
				currentTool = new ToolZoom(context, true);
				zoominButton.setSelected(true);
			} else {
				currentTool = new ToolPick(context);
				pickButton.setSelected(true);
			}
		} else {
			if (!juliaProperty.getValue() && currentTool instanceof ToolPick) {
				currentTool = new ToolZoom(context, true);
				zoominButton.setSelected(true);
			}
		}
	}

	private void updateData(MandelbrotSession session, Boolean continuous, Boolean timeAnimation) {
		mandelbrotSession = session;
        updateSession(session, continuous, timeAnimation);
	}

	private void restoreView(MandelbrotSession mandelbrotSession) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
		showPreviewProperty.setValue(!metadata.isJulia() && metadata.getOptions().isShowPreview());
		showOrbitProperty.setValue(metadata.getOptions().isShowOrbit());
		showTrapsProperty.setValue(metadata.getOptions().isShowTraps());
	}

	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	private void dispose() {
		disposeCoordinators();
	}

	private FadeTransition createFadeTransition(Node node) {
		FadeTransition transition = new FadeTransition();
		transition.setNode(node);
		transition.setDuration(Duration.seconds(0.5));
		return transition;
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

	private void resetView(EventBus eventBus) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
		double[] translation = {0, 0, 1, 0};
		double[] rotation = {0, 0, 0, 0};
		double[] scale = {1, 1, 1, 1};
		MandelbrotMetadata newMetadata = new MandelbrotMetadata(translation, rotation, scale, metadata.getPoint().toArray(), metadata.getTime(), metadata.isJulia(), metadata.getOptions());
		eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), newMetadata), false, true);
	}

	private void createCoordinators(int rows, int columns, Map<String, Integer> hints) {
		DefaultThreadFactory threadFactory = createThreadFactory("Mandelbrot Coordinator", Thread.MIN_PRIORITY + 2);
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				coordinators[row * columns + column] = new RendererCoordinator(threadFactory, renderFactory, createTile(row, column), hints);
			}
		}
	}

	private RendererCoordinator createJuliaCoordinator(Map<String, Integer> hints) {
		DefaultThreadFactory threadFactory = createThreadFactory("Julia Coordinator", Thread.MIN_PRIORITY + 1);
		return new RendererCoordinator(threadFactory, renderFactory, createSingleTile(200, 200), hints);
	}

	private DefaultThreadFactory createThreadFactory(String name, int priority) {
		return new DefaultThreadFactory(name, true, priority);
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

	private void runTimer(Canvas fractalCanvas, Canvas orbitCanvas, Canvas juliaCanvas, Canvas pointCanvas, Canvas trapCanvas, Canvas toolCanvas) {
		timer = new AnimationTimer() {
			private long lastInMillis;

			@Override
			public void handle(long nanos) {
				long nowInMillis = nanos / 1000000;
				if (nowInMillis - lastInMillis > FRAME_LENGTH_IN_MILLIS) {
					if (!playback && !hasError && coordinators[0] != null && coordinators[0].isInitialized()) {
						processRenderErrors();
						redrawIfPixelsChanged(fractalCanvas);
						redrawIfJuliaPixelsChanged(juliaCanvas);
						redrawIfPointChanged(pointCanvas);
						redrawIfOrbitChanged(orbitCanvas);
						redrawIfTrapChanged(trapCanvas);
						redrawIfToolChanged(toolCanvas);
						if (currentTool != null) {
							currentTool.update(nowInMillis, timeAnimation);
						}
					}
					lastInMillis = nowInMillis;
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

	private void setFractalJulia(EventBus eventBus, boolean julia) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
		if (!julia && metadata.isJulia()) {
			MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.getTime(), false, metadata.getOptions());
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), newMetadata), false, true);
		} else if (julia && !metadata.isJulia()) {
			MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.getTime(), true, metadata.getOptions());
			eventBus.postEvent("render-data-changed", new MandelbrotSession(mandelbrotSession.getScript(), newMetadata), false, true);
		}
	}

	private List<Error> updateReport(CompilerReport report) {
		try {
			updateCompilerErrors(null, null, null);
			boolean[] changed = createOrbitAndColor(report);
			boolean orbitChanged = changed[0];
			boolean colorChanged = changed[1];
			if (orbitChanged) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Orbit algorithm is changed");
				}
			}
			if (colorChanged) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Color algorithm is changed");
				}
			}
//			if (!orbitChanged && !colorChanged) {
//				logger.info("Orbit or color algorithms are not changed");
//				return;
//			}
			MandelbrotMetadata oldMetadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
			Double4D translation = oldMetadata.getTranslation();
			Double4D rotation = oldMetadata.getRotation();
			Double4D scale = oldMetadata.getScale();
			Double2D point = oldMetadata.getPoint();
			Time time = oldMetadata.getTime();
			boolean julia = oldMetadata.isJulia();
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
					view.setTraslation(translation);
					view.setRotation(rotation);
					view.setScale(scale);
					view.setState(new Integer4D(0, 0, 0, 0));
					view.setJulia(julia);
					view.setPoint(new Number(point.getX(), point.getY()));
					coordinator.setView(view);
					coordinator.setTime(time);
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
				view.setTraslation(translation);
				view.setRotation(rotation);
				view.setScale(scale);
				view.setState(new Integer4D(0, 0, 0, 0));
				view.setJulia(true);
				view.setPoint(new Number(point.getX(), point.getY()));
				juliaCoordinator.setView(view);
				juliaCoordinator.setTime(time);
			}
			startCoordinators();
			if (juliaCoordinator != null) {
				juliaCoordinator.run();
			}
			states = renderOrbit(time, point);
			redrawTraps = true;
			redrawOrbit = true;
			redrawPoint = true;
			if (!julia) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Orbit: point = " + point + ", length = " + states.size());
				}
			}
		} catch (CompilerSourceException e) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "Cannot render fractal: " + e.getMessage());
			}
			updateCompilerErrors(e.getMessage(), e.getErrors(), null);
			return e.getErrors();
		} catch (CompilerClassException e) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "Cannot render fractal: " + e.getMessage());
			}
			updateCompilerErrors(e.getMessage(), e.getErrors(), e.getSource());
			return e.getErrors();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			if (logger.isLoggable(Level.FINE)) {
				logger.log(Level.FINE, "Cannot render fractal: " + e.getMessage());
			}
			updateCompilerErrors(e.getMessage(), null, null);
			return Arrays.asList(new Error(Error.ErrorType.RUNTIME, 0, 0, 0, 0, "Cannot render image"));
		}
		return Collections.emptyList();
	}

	private boolean[] createOrbitAndColor(CompilerReport report) throws CompilerSourceException, CompilerClassException, ClassNotFoundException, IOException {
		if (report.getErrors().size() > 0) {
			astOrbit = null;
			astColor = null;
			orbitBuilder = null;
			colorBuilder = null;
			throw new CompilerSourceException("Failed to compile source", report.getErrors());
		}
		Compiler compiler = new Compiler(Compiler.class.getPackage().getName(), "Compile" + System.nanoTime());
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

	private void updateCompilerErrors(String message, List<Error> errors, String source) {
		hasError = message != null;
		Platform.runLater(() -> {
			statusProperty.setValue(null);
			errorProperty.setValue(null);
			if (message != null) {
				StringBuilder builder = new StringBuilder();
				builder.append(message);
				if (errors != null) {
					builder.append("\n\n");
					for (Error error : errors) {
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
//				statusProperty.setValue("Source compiled");
			}
		});
	}

	private void updateRendererErrors(String message, List<Error> errors, String source) {
		hasError = message != null;
		Platform.runLater(() -> {
			statusProperty.setValue(null);
			errorProperty.setValue(null);
			if (message != null) {
				StringBuilder builder = new StringBuilder();
				builder.append(message);
				if (errors != null) {
					builder.append("\n\n");
					for (Error error : errors) {
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
//				statusProperty.setValue("Rendering completed");
			}
		});
	}

//	private void updatePoint(MandelbrotSession session, boolean continuous) {
//		MandelbrotMetadata metadata = (MandelbrotMetadata) session.getMetadata();
//		Double2D point = metadata.getPoint();
//		boolean julia = metadata.isJulia();
//		if (julia) {
//			abortCoordinators();
//			joinCoordinators();
//			Double4D translation = metadata.getTranslation();
//			Double4D rotation = metadata.getRotation();
//			Double4D scale = metadata.getScale();
//			for (int i = 0; i < coordinators.length; i++) {
//				RendererCoordinator coordinator = coordinators[i];
//				if (coordinator != null) {
//					RendererView view = new RendererView();
//					view.setTraslation(translation);
//					view.setRotation(rotation);
//					view.setScale(scale);
//					view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
//					view.setJulia(julia);
//					view.setPoint(new Number(point.getX(), point.getY()));
//					coordinator.setView(view);
//				}
//			}
//			startCoordinators();
//		} else {
//			if (juliaCoordinator != null) {
//				juliaCoordinator.abort();
//				juliaCoordinator.waitFor();
//				RendererView view = new RendererView();
//				view.setTraslation(new Double4D(new double[] { 0, 0, 1, 0 }));
//				view.setRotation(new Double4D(new double[] { 0, 0, 0, 0 }));
//				view.setScale(new Double4D(new double[] { 1, 1, 1, 1 }));
//				view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
//				view.setJulia(true);
//				view.setPoint(new Number(point.getX(), point.getY()));
//				juliaCoordinator.setView(view);
//				juliaCoordinator.run();
//			}
//			states = renderOrbit(point);
//			redrawOrbit = true;
//			redrawPoint = true;
//			if (logger.isLoggable(Level.FINE)) {
//				logger.fine("Orbit: point = " + point + ", length = " + states.size());
//			}
//		}
//	}

	private void updateSession(MandelbrotSession session, boolean continuous, boolean timeAnimation) {
		MandelbrotMetadata metadata = (MandelbrotMetadata) session.getMetadata();
		Double4D translation = metadata.getTranslation();
		Double4D rotation = metadata.getRotation();
		Double4D scale = metadata.getScale();
		Double2D point = metadata.getPoint();
		Time time = metadata.getTime();
		boolean julia = metadata.isJulia();
		abortCoordinators();
		joinCoordinators();
		for (int i = 0; i < coordinators.length; i++) {
			RendererCoordinator coordinator = coordinators[i];
			if (coordinator != null) {
				RendererView view = new RendererView();
				view.setTraslation(translation);
				view.setRotation(rotation);
				view.setScale(scale);
				view.setState(new Integer4D(0, 0, continuous ? 1 : 0, timeAnimation ? 1 : 0));
				view.setJulia(julia);
				view.setPoint(new Number(point.getX(), point.getY()));
				coordinator.setView(view);
				if (timeAnimation) {
					coordinator.setTime(time);
				}
			}
		}
		startCoordinators();
		if ((!continuous || currentTool instanceof ToolPick) && !julia && juliaCoordinator != null) {
			juliaCoordinator.abort();
			juliaCoordinator.waitFor();
			RendererView view = new RendererView();
			view.setTraslation(new Double4D(new double[] { 0, 0, 1, 0 }));
			view.setRotation(new Double4D(new double[] { 0, 0, 0, 0 }));
			view.setScale(new Double4D(new double[] { 1, 1, 1, 1 }));
			view.setState(new Integer4D(0, 0, continuous ? 1 : 0, timeAnimation ? 1 : 0));
			view.setJulia(true);
			view.setPoint(new Number(point.getX(), point.getY()));
			juliaCoordinator.setView(view);
			if (timeAnimation) {
				juliaCoordinator.setTime(time);
			}
			juliaCoordinator.run();
		}
		states = renderOrbit(time, point);
		redrawOrbit = true;
		redrawPoint = true;
		redrawTraps = true;
		if (!julia && !continuous) {
//			states = renderOrbit(point);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Orbit: point = " + point + ", length = " + states.size());
			}
		}
	}

//	private void updateMode(MandelbrotSession session, boolean continuous) {
//		MandelbrotMetadata metadata = (MandelbrotMetadata) session.getMetadata();
//		Double4D translation = metadata.getTranslation();
//		Double4D rotation = metadata.getRotation();
//		Double4D scale = metadata.getScale();
//		Double2D point = metadata.getPoint();
//		boolean julia = metadata.isJulia();
//		abortCoordinators();
//		joinCoordinators();
//		for (int i = 0; i < coordinators.length; i++) {
//			RendererCoordinator coordinator = coordinators[i];
//			if (coordinator != null) {
//				RendererView view = new RendererView();
//				view.setTraslation(translation);
//				view.setRotation(rotation);
//				view.setScale(scale);
//				view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
//				view.setJulia(julia);
//				view.setPoint(new Number(point.getX(), point.getY()));
//				coordinator.setView(view);
//			}
//		}
//		startCoordinators();
//		if (!continuous && !julia && juliaCoordinator != null) {
//			juliaCoordinator.abort();
//			juliaCoordinator.waitFor();
//			RendererView view = new RendererView();
//			view.setTraslation(new Double4D(new double[] { 0, 0, 1, 0 }));
//			view.setRotation(new Double4D(new double[] { 0, 0, 0, 0 }));
//			view.setScale(new Double4D(new double[] { 1, 1, 1, 1 }));
//			view.setState(new Integer4D(0, 0, continuous ? 1 : 0, 0));
//			view.setJulia(true);
//			view.setPoint(new Number(point.getX(), point.getY()));
//			juliaCoordinator.setView(view);
//			juliaCoordinator.run();
//		}
//		redrawOrbit = true;
//		redrawPoint = true;
//		redrawTraps = true;
//		if (!julia && !continuous) {
//			states = renderOrbit(point);
//			logger.fine("Orbit: point = " + point + ", length = " + states.size());
//		}
//	}

	private List<Number[]> renderOrbit(Time time, Double2D point) {
		List<Number[]> states = new ArrayList<>(); 
		try {
			if (orbitBuilder != null) {
				Orbit orbit = orbitBuilder.build();
				Scope scope = new Scope();
				orbit.setScope(scope);
				orbit.init();
				orbit.setW(new Number(point.getX(), point.getY()));
				orbit.setX(orbit.getInitialPoint());
				orbit.render(states);
			}
		} catch (Throwable e) {
			logger.log(Level.WARNING, "Failed to render orbit", e);
		}
		return states;
	}

	private void processRenderErrors() {
		if (coordinators != null && coordinators.length > 0) {
			RendererCoordinator coordinator = coordinators[0];
			if (coordinator != null) {
				List<Error> errors = coordinator.getErrors();
				if (errors.isEmpty()) {
					updateRendererErrors(null, null, null);
				} else {
					updateRendererErrors("Error", errors, null);
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
		MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
		if (!metadata.isJulia() && juliaCoordinator != null && juliaCoordinator.isPixelsChanged()) {
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			double dw = canvas.getWidth();
			double dh = canvas.getHeight();
			gc.clearRect(0, 0, (int)dw, (int)dh);
			juliaCoordinator.drawImage(gc, 0, 0);
//			Number size = juliaCoordinator.getInitialSize();
//			Number center = juliaCoordinator.getInitialCenter();
//			gc.setStroke(renderFactory.createColor(1, 1, 0, 1));
		}
	}

	private void redrawIfPointChanged(Canvas canvas) {
		if (redrawPoint) {
			redrawPoint = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] t = metadata.getTranslation().toArray();
				double[] r = metadata.getRotation().toArray();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				double cx = dw / 2;
				double cy = dh / 2;
				gc.setStrokeLine(((float)dw) * 0.002f, RendererGraphicsContext.CAP_BUTT, RendererGraphicsContext.JOIN_MITER, 1f);
				gc.setStroke(renderFactory.createColor(1, 1, 0, 1));
				double[] point = metadata.getPoint().toArray();
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
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] t = metadata.getTranslation().toArray();
				double[] r = metadata.getRotation().toArray();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				double cx = dw / 2;
				double cy = dh / 2;
				gc.setStrokeLine(((float)dw) * 0.002f, RendererGraphicsContext.CAP_BUTT, RendererGraphicsContext.JOIN_MITER, 1f);
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
		if (redrawTraps) {
			redrawTraps = false;
			Number size = coordinators[0].getInitialSize();
			Number center = coordinators[0].getInitialCenter();
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			if (states.size() > 1) {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] t = metadata.getTranslation().toArray();
				double[] r = metadata.getRotation().toArray();
				double tx = t[0];
				double ty = t[1];
				double tz = t[2];
				double a = -r[2] * Math.PI / 180;
				double dw = canvas.getWidth();
				double dh = canvas.getHeight();
				gc.clearRect(0, 0, (int)dw, (int)dh);
				gc.setStrokeLine(((float)dw) * 0.002f, RendererGraphicsContext.CAP_BUTT, RendererGraphicsContext.JOIN_MITER, 1f);
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
}
