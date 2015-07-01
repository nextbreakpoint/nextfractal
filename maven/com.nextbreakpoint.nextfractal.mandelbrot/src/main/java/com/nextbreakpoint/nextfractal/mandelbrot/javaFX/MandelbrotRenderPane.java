/*
 * NextFractal 1.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.javaFX.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.RendererFactory;
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
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerClassException;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Scope;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Trap;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererCoordinator;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;

public class MandelbrotRenderPane extends BorderPane implements ExportDelegate, BrowseDelegate, MandelbrotToolContext {
	private static final int FRAME_LENGTH_IN_MILLIS = 20;
	private static final Logger logger = Logger.getLogger(MandelbrotRenderPane.class.getName());
	private final Session session;
	private ThreadFactory renderThreadFactory;
	private ThreadFactory juliaRenderThreadFactory;
	private JavaFXRendererFactory renderFactory;
	private RendererCoordinator[] coordinators;
	private RendererCoordinator juliaCoordinator;
	private MandelbrotImageGenerator generator;
	private AnimationTimer timer;
	private FileChooser fileChooser;
	private File currentExportFile;
	private StringObservableValue fileProperty;
	private StringObservableValue errorProperty;
	private BooleanObservableValue hideOrbitProperty;
	private BooleanObservableValue hideErrorsProperty;
	private BooleanObservableValue juliaProperty;
	private boolean pressed; 
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
	private MandelbrotTool currentTool;
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

		fileProperty = new StringObservableValue();
		fileProperty.setValue(null);

		errorProperty = new StringObservableValue();
		errorProperty.setValue(null);

		juliaProperty = new BooleanObservableValue();
		juliaProperty.setValue(false);

		hideOrbitProperty = new BooleanObservableValue();
		hideOrbitProperty.setValue(true);
		
		hideErrorsProperty = new BooleanObservableValue();
		hideErrorsProperty.setValue(true);
		
		renderThreadFactory = new DefaultThreadFactory("MandelbrotRendererCoordinator", true, Thread.MIN_PRIORITY + 2);
		juliaRenderThreadFactory = new DefaultThreadFactory("MandelbrotJuliaRendererCoordinator", true, Thread.MIN_PRIORITY);
		
		renderFactory = new JavaFXRendererFactory();

		DefaultThreadFactory generatorThreadFactory = new DefaultThreadFactory("MandelbrotExportImageGenerator", true, Thread.MIN_PRIORITY);
		generator = new MandelbrotImageGenerator(generatorThreadFactory, renderFactory, createSingleTile(50, 50));

		coordinators = new RendererCoordinator[rows * columns];
		
		Map<String, Integer> hints = new HashMap<String, Integer>();
		hints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
		createCoordinators(rows, columns, hints);
		
		Map<String, Integer> juliaHints = new HashMap<String, Integer>();
		juliaHints.put(RendererCoordinator.KEY_TYPE, RendererCoordinator.VALUE_REALTIME);
//		juliaHints.put(RendererCoordinator.KEY_PROGRESS, RendererCoordinator.VALUE_SINGLE_PASS);
		createJuliaCoordinator(juliaHints);
		
		getStyleClass().add("mandelbrot");

		BorderPane controls = new BorderPane();
		controls.setMinHeight(height);
		controls.setMaxHeight(height);
		controls.setPrefHeight(height);
		
		HBox toolButtons = new HBox(10);
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
//		Button exportButton = new Button("", createIconImage("/icon-export.png"));
//		Button browseButton = new Button("", createIconImage("/icon-load.png"));
		Button homeButton = new Button("", createIconImage("/icon-home.png"));
		zoominButton.setTooltip(new Tooltip("Select zoom in tool"));
		zoomoutButton.setTooltip(new Tooltip("Select zoom out tool"));
		moveButton.setTooltip(new Tooltip("Select move tool"));
		rotateButton.setTooltip(new Tooltip("Select rotate tool"));
		pickButton.setTooltip(new Tooltip("Select pick tool"));
		homeButton.setTooltip(new Tooltip("Reset region to initial value"));
		orbitButton.setTooltip(new Tooltip("Toggle orbit and traps"));
		juliaButton.setTooltip(new Tooltip("Toggle Julia mode"));
//		exportButton.setTooltip(new Tooltip("Export fractal as image"));
//		browseButton.setTooltip(new Tooltip("Browse fractals"));
//		toolButtons.getChildren().add(browseButton);
		toolButtons.getChildren().add(homeButton);
		toolButtons.getChildren().add(zoominButton);
		toolButtons.getChildren().add(zoomoutButton);
		toolButtons.getChildren().add(moveButton);
		toolButtons.getChildren().add(rotateButton);
		toolButtons.getChildren().add(pickButton);
		toolButtons.getChildren().add(juliaButton);
		toolButtons.getChildren().add(orbitButton);
//		toolButtons.getChildren().add(exportButton);
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
		exportPane.setMinHeight(height / 2);
		exportPane.setMaxHeight(height / 2);
		exportPane.setPrefHeight(height / 2);
		
		BrowsePane browsePane = new BrowsePane(width);
		browsePane.setDelegate(this);
		browsePane.setDisable(true);
		browsePane.setMinHeight(height);
		browsePane.setMaxHeight(height);
		browsePane.setPrefHeight(height);
		
		ErrorPane errorPane = new ErrorPane();
		errorPane.setDisable(true);
		errorPane.setMinHeight(height / 2);
		errorPane.setMaxHeight(height / 2);
		errorPane.setPrefHeight(height / 2);
		
		StackPane alertsPane = new StackPane();
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

		currentTool = new MandelbrotZoom(this, true);
		zoominButton.setSelected(true);
		zoominButton.setDisable(true);
		
		controls.setOnMouseClicked(e -> {
			if (!pressed && e.getY() > controls.getHeight() - toolButtons.getHeight() && e.getY() < controls.getHeight()) {
				fadeIn(toolsTransition, x -> {});
			} else {
				fadeOut(toolsTransition, x -> {});
			}
			if (currentTool != null) {
				currentTool.clicked(e);
			}
		});
		
		controls.setOnMousePressed(e -> {
			pressed = true;
			fadeOut(toolsTransition, x -> {});
			if (currentTool != null) {
				currentTool.pressed(e);
			}
		});
		
		controls.setOnMouseReleased(e -> {
			pressed = false;
			if (e.getY() > controls.getHeight() - toolButtons.getHeight() && e.getY() < controls.getHeight()) {
				fadeIn(toolsTransition, x -> {});
			} else {
				fadeOut(toolsTransition, x -> {});
			}
			if (currentTool != null) {
				currentTool.released(e);
			}
		});
		
		controls.setOnMouseDragged(e -> {
			fadeOut(toolsTransition, x -> {});
			if (currentTool != null) {
				currentTool.dragged(e);
			}
		});
		
		controls.setOnMouseMoved(e -> {
			if (!pressed && e.getY() > controls.getHeight() - toolButtons.getHeight() && e.getY() < controls.getHeight()) {
				fadeIn(toolsTransition, x -> {});
			} else {
				fadeOut(toolsTransition, x -> {});
			}
			if (currentTool != null) {
				currentTool.moved(e);
			}
		});
		
		controls.setOnMouseEntered(e -> {
			if (e.getY() > controls.getHeight() - toolButtons.getHeight() && e.getY() < controls.getHeight()) {
				fadeIn(toolsTransition, x -> {});
			}
		});
		
		controls.setOnMouseExited(e -> {
			fadeOut(toolsTransition, x -> {});
		});
		
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

			@Override
			public void doExportAsImage(Session session) {
				exportAsImage(exportPane);
			}

			@Override
			public void showBrowser(Session session) {
				browsePane.show();
			}
		});
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(fractalCanvas);
		stackPane.getChildren().add(trapCanvas);
		stackPane.getChildren().add(orbitCanvas);
		stackPane.getChildren().add(pointCanvas);
		stackPane.getChildren().add(juliaCanvas);
		stackPane.getChildren().add(toolCanvas);
		stackPane.getChildren().add(controls);
		stackPane.getChildren().add(browsePane);
		setCenter(stackPane);
        
		homeButton.setOnAction(e -> {
			resetView();
		});
		
		toolsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != null) {
				((ToggleButton)oldValue).setDisable(false);
			}
			if (newValue != null) {
				((ToggleButton)newValue).setDisable(true);
			}
		});
		
		zoominButton.setOnAction(e -> {
			currentTool = new MandelbrotZoom(this, true);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		zoomoutButton.setOnAction(e -> {
			currentTool = new MandelbrotZoom(this, false);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		moveButton.setOnAction(e -> {
			currentTool = new MandelbrotMove(this);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		rotateButton.setOnAction(e -> {
			currentTool = new MandelbrotRotate(this);
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
		});
		
		pickButton.setOnAction(e -> {
			if (!getMandelbrotSession().getDataAsCopy().isJulia()) {
				currentTool = new MandelbrotPick(this);
				juliaCanvas.setVisible(true);
				pointCanvas.setVisible(true);
			}
		});
		
//		exportButton.setOnAction(e -> {
//			exportAsImage(exportPane);
//		});

//		browseButton.setOnAction(e -> {
//			browsePane.show();
//		});
		
		orbitButton.setOnAction(e -> {
//			if (!getMandelbrotSession().getDataAsCopy().isJulia()) {
//				currentTool = new MandelbrotPick(this);
//				juliaCanvas.setVisible(true);
//				pointCanvas.setVisible(true);
//				pickButton.requestFocus();
//			} else {
//				currentTool = new MandelbrotZoom(this, true);
//				juliaCanvas.setVisible(false);
//				pointCanvas.setVisible(false);
//				zoominButton.requestFocus();
//			}
			toggleShowOrbit();
		});
		
		juliaButton.setOnAction(e -> {
			juliaCanvas.setVisible(false);
			pointCanvas.setVisible(false);
			juliaProperty.setValue(!juliaProperty.getValue());
			if (pickButton.isSelected()) {
				currentTool = new MandelbrotZoom(this, true);
				zoominButton.requestFocus();
				zoominButton.setSelected(true);
				pickButton.setDisable(juliaProperty.getValue());
			}
		});
		
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

		fileProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
				return;
			}
			try {
				File file = new File(newValue);
				MandelbrotDataStore service = new MandelbrotDataStore();
				MandelbrotData data = service.loadFromFile(file);
				getMandelbrotSession().setCurrentFile(file);
				if (data.isJulia() && currentTool instanceof MandelbrotPick) {
					currentTool = new MandelbrotZoom(this, true);
					juliaCanvas.setVisible(false);
					pointCanvas.setVisible(false);
					zoominButton.setSelected(true);
				}
				juliaButton.setSelected(data.isJulia());
				getMandelbrotSession().setData(data);
				logger.info(data.toString());
			} catch (Exception x) {
				logger.warning("Cannot read file " + newValue);
				//TODO display error
			}
		});
		
		errorsButton.setOnAction(e -> {
			fadeOut(alertsTransition, x -> { 
				alertButtons.setVisible(false);
				errorPane.show();
			});
		});
		
		stackPane.setOnDragDropped(e -> {
        	List<File> files = e.getDragboard().getFiles();
        	if (files.size() > 0) {
        		File file = files.get(0);
        		fileProperty.setValue(file.getAbsolutePath());
        	}
        });
        
		stackPane.setOnDragOver(e -> {
        	if (e.getGestureSource() != stackPane && e.getDragboard().hasFiles()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        });
		
		DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("MandelbrotImageExport", true, Thread.MIN_PRIORITY);
		exportExecutor = Executors.newSingleThreadExecutor(exportThreadFactory);
		
		runTimer(fractalCanvas, orbitCanvas, juliaCanvas, pointCanvas, trapCanvas, toolCanvas);
		
		exportPane.hide();
		
		browsePane.hide();
	}

	@Override
	public void exportSession(RendererSize rendererSize) {
		doExportSession(rendererSize);
	}

	@Override
	public void didSelectFile(BrowsePane browser, File file) {
		browser.hide();
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
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	private void exportAsImage(ExportPane exportPane) {
		if (errorProperty.getValue() == null) {
			exportData = getMandelbrotSession().getDataAsCopy();
			exportPane.show();
		}
	}

	private void watchLoop(Path dir) throws IOException {
		WatchService watcher = FileSystems.getDefault().newWatchService();

		try {
		    WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException x) {
		    System.err.println(x);
		}
		
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
			                System.err.format("New file '%s' is not a plain text file.%n", filename);
			                continue;
			            }
			        } catch (IOException x) {
			            System.err.println(x);
			            continue;
			        }
	
			        //Details left to reader....
			    }
	
			    boolean valid = key.reset();
			    if (!valid) {
			        break;
			    }
			}
		} catch (InterruptedException x) {
		}
	}
	
	private void shutdown() {
		exportExecutor.shutdownNow();
		try {
			exportExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}
	
	private void dispose() {
		disposeCoordinators();
		disposeJuliaCoordinator();
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
		if (transition.getNode().getOpacity() != 0) {
			transition.setFromValue(transition.getNode().getOpacity());
			transition.setToValue(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void fadeIn(FadeTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getOpacity() != 0.95) {
			transition.setFromValue(transition.getNode().getOpacity());
			transition.setToValue(0.95);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void resetView() {
		MandelbrotView view = new MandelbrotView(new double[] { 0, 0, 1, 0 }, new double[] { 0, 0, 0, 0 }, new double[] { 1, 1, 1, 1 }, getMandelbrotSession().getViewAsCopy().getPoint(), getMandelbrotSession().getViewAsCopy().isJulia());
		getMandelbrotSession().setView(view, false);
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
				coordinators[row * columns + column] = new RendererCoordinator(renderThreadFactory, renderFactory, createTile(row, column), hints);
			}
		}
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
		juliaCoordinator = new RendererCoordinator(juliaRenderThreadFactory, renderFactory, createSingleTile(200, 200), hints);
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
						redrawIfPixelsChanged(fractalCanvas);
						redrawIfJuliaPixelsChanged(juliaCanvas);
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
			boolean[] changed = generateOrbitAndColor();
			updateErrors(null, null, null);
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
			double[] traslation = oldView.getTraslation();
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
					view.setTraslation(new Double4D(traslation));
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
				view.setTraslation(new Double4D(traslation));
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
			if (!julia) {
				states = renderOrbit(point);
				redrawOrbit = true;
				redrawPoint = true;
				logger.info("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
			}
		} catch (CompilerSourceException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), e.getErrors(), null);
		} catch (CompilerClassException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), e.getErrors(), e.getSource());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			logger.log(Level.INFO, "Cannot render fractal: " + e.getMessage());
			updateErrors(e.getMessage(), null, null);
		}
	}

	private boolean[] generateOrbitAndColor() throws CompilerSourceException, CompilerClassException, ClassNotFoundException, IOException {
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

	private void updateErrors(String message, List<CompilerError> errors, String source) {
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
				if (source != null) {
					builder.append("\n\n");
					builder.append(source);
					builder.append("\n");
				}
				errorProperty.setValue(builder.toString());
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
			double[] traslation = oldView.getTraslation();
			double[] rotation = oldView.getRotation();
			double[] scale = oldView.getScale();
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
			redrawPoint = true;
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Orbit: point = " + Arrays.toString(point) + ", length = " + states.size());
			}
		}
	}

	private void updateView(boolean continuous) {
		MandelbrotView oldView = getMandelbrotSession().getViewAsCopy();
		double[] traslation = oldView.getTraslation();
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
		redrawPoint = true;
		redrawTrap = true;
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
				coordinator.drawImage(gc, 0, 0);
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
		if (currentTool != null && currentTool.isChanged()) {
			RendererGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
			currentTool.draw(gc);
		}
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

	private void doExportSession(RendererSize rendererSize) {
		Encoder encoder = createEncoder("PNG");
		if (encoder == null) {
			logger.warning("Cannot find encoder for PNG format");
			//TODO display error
			return;
		}
		createFileChooser(encoder.getSuffix());
		fileChooser.setTitle("Export");
		if (currentExportFile != null) {
			fileChooser.setInitialDirectory(currentExportFile.getParentFile());
			fileChooser.setInitialFileName(currentExportFile.getName());
		}
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			currentExportFile = file;
			MandelbrotData data = exportData; 
			exportExecutor.submit(new Runnable() {
				@Override
				public void run() {
					data.setPixels(generator.renderImage(data));
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								File tmpFile = File.createTempFile("nextfractal-profile-", ".dat");
								ExportSession exportSession = new ExportSession("Mandelbrot", data, file, tmpFile, rendererSize, 200, encoder);
								logger.info("Export session created: " + exportSession.getSessionId());
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

	@Override
	public RendererFactory getRendererFactory() {
		return renderFactory;
	}
}
