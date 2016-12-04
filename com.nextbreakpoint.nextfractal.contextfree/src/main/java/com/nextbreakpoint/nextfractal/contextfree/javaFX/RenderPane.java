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
package com.nextbreakpoint.nextfractal.contextfree.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeMetadata;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeSession;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerClassException;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.contextfree.compiler.CompilerSourceException;
import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDG;
import com.nextbreakpoint.nextfractal.contextfree.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.contextfree.renderer.RendererError;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.javaFX.Bitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowsePane;
import com.nextbreakpoint.nextfractal.core.javaFX.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.*;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class RenderPane extends BorderPane {
	private static final int FRAME_LENGTH_IN_MILLIS = 20;
	private static final Logger logger = Logger.getLogger(RenderPane.class.getName());
	private final ThreadFactory renderThreadFactory;
	private final JavaFXRendererFactory renderFactory;
	private final StringObservableValue errorProperty;
	private final StringObservableValue statusProperty;
	private final BooleanObservableValue hideErrorsProperty;
	private RendererCoordinator coordinator;
	private AnimationTimer timer;
	private int width;
	private int height;
	private int rows;
	private int columns;
	private CFDG cfdg;
	private String cfdgSource = "";
	private volatile boolean disableTool;
	private ContextFreeSession contextFreeSession;

	public RenderPane(Session session, EventBus eventBus, int width, int height, int rows, int columns) {
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.columns = columns;

		contextFreeSession = (ContextFreeSession) session;

		errorProperty = new StringObservableValue();
		errorProperty.setValue(null);

		statusProperty = new StringObservableValue();
		statusProperty.setValue(null);

		hideErrorsProperty = new BooleanObservableValue();
		hideErrorsProperty.setValue(true);
		
		renderThreadFactory = new DefaultThreadFactory("ContextFree Coordinator", true, Thread.MIN_PRIORITY + 2);

		renderFactory = new JavaFXRendererFactory();

		Map<String, Integer> hints = new HashMap<>();
		coordinator = createCoordinator(hints, width, height);

		getStyleClass().add("contextfree");

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
//		ToggleButton zoominButton = new ToggleButton("", createIconImage("/icon-zoomin.png"));
//		ToggleButton zoomoutButton = new ToggleButton("", createIconImage("/icon-zoomout.png"));
//		ToggleButton moveButton = new ToggleButton("", createIconImage("/icon-move.png"));
//		ToggleButton rotateButton = new ToggleButton("", createIconImage("/icon-rotate.png"));
		ToggleGroup toolsGroup = new ToggleGroup();
//		toolsGroup.getToggles().add(zoominButton);
//		toolsGroup.getToggles().add(zoomoutButton);
//		toolsGroup.getToggles().add(moveButton);
//		toolsGroup.getToggles().add(rotateButton);
//		Button homeButton = new Button("", createIconImage("/icon-home.png"));
		browseButton.setTooltip(new Tooltip("Show fractals browser"));
//		zoominButton.setTooltip(new Tooltip("Select zoom in tool"));
//		zoomoutButton.setTooltip(new Tooltip("Select zoom out tool"));
//		moveButton.setTooltip(new Tooltip("Select move tool"));
//		rotateButton.setTooltip(new Tooltip("Select rotate tool"));
		toolButtons.getChildren().add(browseButton);
//		toolButtons.getChildren().add(homeButton);
//		toolButtons.getChildren().add(zoominButton);
//		toolButtons.getChildren().add(zoomoutButton);
//		toolButtons.getChildren().add(moveButton);
//		toolButtons.getChildren().add(rotateButton);
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
				eventBus.postEvent("editor-load-file", file);
				hideBrowser(browserTransition, a -> {});
			}

			@Override
			public void didClose(BrowsePane source) {
				hideBrowser(browserTransition, a -> {});
			}

			@Override
			public GridItemRenderer createRenderer(Bitmap bitmap) throws Exception {
				return tryFindFactory(((Session) bitmap.getProperty("session")).getPluginId())
					.flatMap(factory -> Try.of(() -> factory.createRenderer(bitmap))).orThrow();
			}

			@Override
			public BrowseBitmap createBitmap(File file, RendererSize size) throws Exception {
				return FileManager.loadFile(file).flatMap(session -> tryFindFactory(session.getPluginId())
					.flatMap(factory -> Try.of(() -> factory.createBitmap(session, size)))).orThrow();
			}

			@Override
			public String getFileExtension() {
				return ".nf.zip";
			}
		});

		controls.setBottom(toolButtons);
		toolButtons.setOpacity(0.9);

        Canvas fractalCanvas = new Canvas(width, height);
        GraphicsContext gcFractalCanvas = fractalCanvas.getGraphicsContext2D();
        gcFractalCanvas.setFill(javafx.scene.paint.Color.WHITESMOKE);
        gcFractalCanvas.fillRect(0, 0, width, height);

		Canvas toolCanvas = new Canvas(width, height);
		GraphicsContext gcToolCanvas = toolCanvas.getGraphicsContext2D();
		gcToolCanvas.setFill(javafx.scene.paint.Color.TRANSPARENT);
		gcToolCanvas.fillRect(0, 0, width, height);

//		currentTool = new ToolZoom(this, true);
//		zoominButton.setSelected(true);
//		zoominButton.setDisable(true);

		FadeTransition toolsTransition = createFadeTransition(controls);

		this.setOnMouseEntered(e -> fadeIn(toolsTransition, x -> {}));
		
		this.setOnMouseExited(e -> fadeOut(toolsTransition, x -> {}));
		
		Pane stackPane = new Pane();
		stackPane.getChildren().add(fractalCanvas);
		stackPane.getChildren().add(toolCanvas);
		stackPane.getChildren().add(controls);
		stackPane.getChildren().add(errors);
		stackPane.getChildren().add(browsePane);
		setCenter(stackPane);

		toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToggleButton)oldValue).setDisable(false);
			}
			if (newValue != null) {
				((ToggleButton)newValue).setDisable(true);
			}
		});
		
		errorProperty.addListener((observable, oldValue, newValue) -> {
			errors.setVisible(newValue != null);
			eventBus.postEvent("render-error-changed", newValue);
		});

		statusProperty.addListener((observable, oldValue, newValue) -> {
			eventBus.postEvent("render-status-changed", newValue);
		});

		Block<ContextFreeMetadata, Exception> updateUI = data -> {};

		heightProperty().addListener((observable, oldValue, newValue) -> {
			toolButtons.setPrefHeight(newValue.doubleValue() * 0.07);
		});

		stackPane.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst()
			.ifPresent(file -> eventBus.postEvent("editor-load-file", file)));

		stackPane.setOnDragOver(x -> Optional.of(x).filter(e -> e.getGestureSource() != stackPane)
			.filter(e -> e.getDragboard().hasFiles()).ifPresent(e -> e.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

		runTimer(fractalCanvas, toolCanvas);

		eventBus.subscribe("session-terminated", event -> dispose());

		eventBus.subscribe("session-report-changed", event -> updateReport((CompilerReport) event));

		eventBus.subscribe("session-data-changed", event -> updateData((Object[]) event));

		eventBus.subscribe("editor-source-changed", event -> {
			ContextFreeSession newSession = new ContextFreeSession((ContextFreeMetadata) contextFreeSession.getMetadata(), (String) event);
            notifySessionChanged(eventBus, newSession, false, true);
        });

		eventBus.subscribe("editor-data-changed", event -> {
			ContextFreeSession newSession = (ContextFreeSession) ((Object[]) event)[0];
			Boolean continuous = (Boolean) ((Object[]) event)[1];
			Boolean appendHistory = (Boolean) ((Object[]) event)[2];
			notifySessionChanged(eventBus, newSession, continuous, appendHistory && !continuous);
		});

		eventBus.subscribe("render-data-changed", event -> {
			ContextFreeSession newSession = (ContextFreeSession) ((Object[]) event)[0];
			Boolean continuous = (Boolean) ((Object[]) event)[1];
			Boolean appendHistory = (Boolean) ((Object[]) event)[2];
			notifySessionChanged(eventBus, newSession, continuous, appendHistory && !continuous);
		});

		eventBus.subscribe("render-status-changed", event -> {
			eventBus.postEvent("session-status-changed", event);
		});

		eventBus.subscribe("render-error-changed", event -> {
			eventBus.postEvent("session-error-changed", event);
		});
	}

	private void updateData(Object[] event) {
		contextFreeSession = (ContextFreeSession) event[0];
	}

	private void notifySessionChanged(EventBus eventBus, ContextFreeSession newSession, boolean continuous, boolean historyAppend) {
		eventBus.postEvent("session-data-changed", new Object[] { newSession, continuous, false });
		if (historyAppend) {
			eventBus.postEvent("history-add-session", newSession);
		}
	}

	private RendererCoordinator createCoordinator(Map<String, Integer> hints, int width, int height) {
		return new RendererCoordinator(renderThreadFactory, renderFactory, createSingleTile(width, height), hints);
	}

	@Override
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}

	private void dispose() {
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

	private void runTimer(Canvas fractalCanvas, Canvas toolCanvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if (time - last > FRAME_LENGTH_IN_MILLIS) {
					if (!disableTool && coordinator != null && coordinator.isInitialized()) {
						processRenderErrors();
						redrawIfPixelsChanged(fractalCanvas);
//						if (currentTool != null) {
//							currentTool.update(time);
//						}
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
//				statusProperty.setValue("Source compiled");
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
//				statusProperty.setValue("Rendering completed");
			}
		});
	}

	private void updateReport(CompilerReport report) {
		try {
			boolean[] changed = createCFDG(report);
			updateCompilerErrors(null, null, null);
			boolean cfdgChanged = changed[0];
			if (cfdgChanged) {
				RenderPane.logger.info("CFDG is changed");
			}
			if (coordinator != null) {
				coordinator.abort();
				coordinator.waitFor();
				if (cfdgChanged) {
					coordinator.setCFDG(cfdg);
				}
				coordinator.init();
				coordinator.run();
			}
		} catch (CompilerSourceException e) {
			logger.log(Level.INFO, "Cannot render image: " + e.getMessage());
			updateCompilerErrors(e.getMessage(), e.getErrors(), null);
		} catch (CompilerClassException e) {
			logger.log(Level.INFO, "Cannot render image: " + e.getMessage());
			updateCompilerErrors(e.getMessage(), e.getErrors(), e.getSource());
		}
	}

	private boolean[] createCFDG(CompilerReport report) throws CompilerSourceException, CompilerClassException {
		if (report.getErrors().size() > 0) {
			cfdgSource = null;
			throw new CompilerSourceException("Failed to compile source", report.getErrors());
		}
		boolean[] changed = new boolean[] { false, false };
		String newCFDG = report.getSource();
		changed[0] = !newCFDG.equals(cfdgSource);
		cfdgSource = newCFDG;
		cfdg = report.getCFDG();
		return changed;
	}

	private void processRenderErrors() {
		if (coordinator != null) {
			List<RendererError> errors = coordinator.getErrors();
			if (errors.isEmpty()) {
				updateRendererErrors(null, null, null);
			} else {
				updateRendererErrors("Error", errors, null);
			}
		}
	}

	private void redrawIfPixelsChanged(Canvas canvas) {
		if (coordinator.isPixelsChanged()) {
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			coordinator.drawImage(gc, 0, 0);
		}
	}
}
