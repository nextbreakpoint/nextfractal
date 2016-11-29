package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportPane;
import com.nextbreakpoint.nextfractal.core.javaFX.HistoryPane;
import com.nextbreakpoint.nextfractal.core.javaFX.JobsPane;
import com.nextbreakpoint.nextfractal.core.javaFX.StatusPane;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.runtime.Plugins.tryPlugin;

public class MainEditorPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    private EventBus localEventBus;

    public MainEditorPane(EventBus eventBus) {
//        widthProperty().addListener((observable, oldValue, newValue) -> Optional.ofNullable(rootPane).ifPresent(pane -> pane.setPrefWidth(newValue.doubleValue())));
//        heightProperty().addListener((observable, oldValue, newValue) -> Optional.ofNullable(rootPane).ifPresent(pane -> pane.setPrefHeight(newValue.doubleValue())));

        eventBus.subscribe("session-changed", event -> handleSessionChanged(eventBus, (Session) event));
    }

    private void handleSessionChanged(EventBus eventBus, Session session) {
        setCenter(createRootPane(session, getLocalEventBus(eventBus)));
    }

    private EventBus getLocalEventBus(EventBus eventBus) {
        if (localEventBus != null) {
            localEventBus.detach();
        }
        localEventBus = new EventBus(eventBus);
        return localEventBus;
    }

    private static Pane createRootPane(Session session, EventBus eventBus) {
//        errorProperty = new StringObservableValue();
//        errorProperty.setValue(null);

        int tileSize = computePercentage(0.02);

        RendererTile tile = createSingleTile(tileSize, tileSize);

        Pane historyPane = createGenerator(session, tile).map(generator -> new HistoryPane(generator, tile)).orElse(null);

        Pane jobsPane = createGenerator(session, tile).map(generator -> new JobsPane(generator, tile, session.getExportService())).orElse(null);

        Pane editorPane = createEditorPane(session, eventBus).orElse(null);

        MainParamsPane paramsPane = new MainParamsPane(eventBus);

        StatusPane statusPane = new StatusPane();

        ExportPane exportPane = new ExportPane();

        historyPane.getStyleClass().add("sidebar");
        paramsPane.getStyleClass().add("sidebar");
        exportPane.getStyleClass().add("sidebar");
        jobsPane.getStyleClass().add("sidebar");

        StackPane sidePane = new StackPane();
        sidePane.getChildren().add(jobsPane);
		sidePane.getChildren().add(historyPane);
        sidePane.getChildren().add(exportPane);
        sidePane.getChildren().add(paramsPane);

//        EventHandler<ActionEvent> renderEventHandler = e -> Platform.runLater(() -> updateSource());

//        EventHandler<ActionEvent> loadEventHandler = e -> Optional.ofNullable(showLoadFileChooser())
//                .map(fileChooser -> fileChooser.showOpenDialog(EditorPane.this.getScene().getWindow())).ifPresent(file -> loadDataFromFile(file));
//
//        EventHandler<ActionEvent> saveEventHandler = e -> Optional.ofNullable(showSaveFileChooser())
//                .map(fileChooser -> fileChooser.showSaveDialog(EditorPane.this.getScene().getWindow())).ifPresent(file -> saveDataToFile(file));

        Pane sourcePane = new Pane();
        HBox sourceButtons = new HBox(0);
        sourceButtons.setAlignment(Pos.CENTER);
        Button renderButton = new Button("", createIconImage("/icon-run.png"));
        Button loadButton = new Button("", createIconImage("/icon-load.png"));
        Button saveButton = new Button("", createIconImage("/icon-save.png"));
        ToggleButton jobsButton = new ToggleButton("", createIconImage("/icon-tool.png"));
        ToggleButton paramsButton = new ToggleButton("", createIconImage("/icon-edit.png"));
        ToggleButton exportButton = new ToggleButton("", createIconImage("/icon-export.png"));
        ToggleButton historyButton = new ToggleButton("", createIconImage("/icon-time.png"));
        ToggleButton statusButton = new ToggleButton("", createIconImage("/icon-warn.png"));
        renderButton.setTooltip(new Tooltip("Render fractal"));
        loadButton.setTooltip(new Tooltip("Load fractal from file"));
        saveButton.setTooltip(new Tooltip("Save fractal to file"));
        jobsButton.setTooltip(new Tooltip("Show/hide jobs"));
        paramsButton.setTooltip(new Tooltip("Show/hide parameters"));
        exportButton.setTooltip(new Tooltip("Export fractal as image"));
        historyButton.setTooltip(new Tooltip("Show/hide history"));
        statusButton.setTooltip(new Tooltip("Show/hide console"));
        sourceButtons.getChildren().add(renderButton);
        sourceButtons.getChildren().add(loadButton);
        sourceButtons.getChildren().add(saveButton);
        sourceButtons.getChildren().add(paramsButton);
        sourceButtons.getChildren().add(historyButton);
        sourceButtons.getChildren().add(exportButton);
        sourceButtons.getChildren().add(jobsButton);
        sourceButtons.getChildren().add(statusButton);
        sourceButtons.getStyleClass().add("toolbar");
        sourceButtons.getStyleClass().add("menubar");
        sourcePane.getChildren().add(editorPane);
        sourcePane.getChildren().add(sourceButtons);
        sourcePane.getChildren().add(statusPane);
        sourcePane.getChildren().add(sidePane);
//        renderButton.setOnAction(renderEventHandler);
//        loadButton.setOnAction(loadEventHandler);
//        saveButton.setOnAction(saveEventHandler);

        TranslateTransition sidebarTransition = createTranslateTransition(sidePane);
        TranslateTransition statusTransition = createTranslateTransition(statusPane);

        statusButton.setSelected(true);

        ToggleGroup viewGroup = new ToggleGroup();
        viewGroup.getToggles().add(jobsButton);
        viewGroup.getToggles().add(historyButton);
        viewGroup.getToggles().add(paramsButton);
        viewGroup.getToggles().add(exportButton);

        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(sourcePane);

        exportPane.setExportDelegate(new ExportDelegate() {
            @Override
            public void createSession(RendererSize rendererSize) {
//                if (errorProperty.getValue() == null) {
//                    doExportSession(rendererSize, getMandelbrotSession().getDataAsCopy(), a -> jobsButton.setSelected(true));
//                }
            }
        });

        rootPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            sourceButtons.setPrefWidth(width);
            editorPane.setPrefWidth(width);
            sidePane.setPrefWidth(width * 0.4);
            statusPane.setPrefWidth(width);
            sourceButtons.setLayoutX(0);
            editorPane.setLayoutX(0);
            sidePane.setLayoutX(width);
            statusPane.setLayoutX(0);
        });

        rootPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            sourceButtons.setPrefHeight(height * 0.07);
            editorPane.setPrefHeight(height * 0.7);
            sidePane.setPrefHeight(height * 0.7);
            statusPane.setPrefHeight(height * 0.23);
            sourceButtons.setLayoutY(0);
            editorPane.setLayoutY(height * 0.07);
            sidePane.setLayoutY(height * 0.07);
            statusPane.setLayoutY(height * 0.77);
        });

        sidePane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            historyPane.setPrefWidth(width);
            paramsPane.setPrefWidth(width);
            exportPane.setPrefWidth(width);
            jobsPane.setPrefWidth(width);
            historyPane.setMaxWidth(width);
            paramsPane.setMaxWidth(width);
            exportPane.setMaxWidth(width);
            jobsPane.setMaxWidth(width);
        });

        sidePane.heightProperty().addListener((observable, oldValue, newValue) -> {
            double height = newValue.doubleValue();
            historyPane.setPrefHeight(height);
            paramsPane.setPrefHeight(height);
            exportPane.setPrefHeight(height);
            jobsPane.setPrefHeight(height);
            historyPane.setMaxHeight(height);
            paramsPane.setMaxHeight(height);
            exportPane.setMaxHeight(height);
            jobsPane.setMaxHeight(height);
        });

//        errorProperty.addListener((source, oldValue, newValue) -> {
//            exportPane.setDisable(newValue != null);
//            paramsPane.setDisable(newValue != null);
//        });

        historyButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                sidePane.getChildren().remove(historyPane);
                sidePane.getChildren().add(historyPane);
            }
        });

        paramsButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                sidePane.getChildren().remove(paramsPane);
                sidePane.getChildren().add(paramsPane);
            }
        });

        jobsButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                sidePane.getChildren().remove(jobsPane);
                sidePane.getChildren().add(jobsPane);
            }
        });

        exportButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                sidePane.getChildren().remove(exportPane);
                sidePane.getChildren().add(exportPane);
            }
        });

        viewGroup.selectedToggleProperty().addListener((source, oldValue, newValue) -> {
            if (newValue != null) {
                showSidebar(sidebarTransition, a -> {});
            } else {
                hideSidebar(sidebarTransition, a -> {});
            }
        });

        statusButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                showStatus(statusTransition, a -> {});
            } else {
                hideStatus(statusTransition, a -> {});
            }
        });

        sidePane.translateXProperty().addListener((source, oldValue, newValue) -> {
            editorPane.prefWidthProperty().setValue(rootPane.getWidth() + newValue.doubleValue());
        });

        statusPane.translateYProperty().addListener((source, oldValue, newValue) -> {
            editorPane.prefHeightProperty().setValue(rootPane.getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
            sidePane.prefHeightProperty().setValue(rootPane.getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
        });

//        DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("Export", true, Thread.MIN_PRIORITY);
//        exportExecutor = Executors.newSingleThreadExecutor(exportThreadFactory);

//        addDataToHistory(historyList);

        eventBus.subscribe("session-status-changed", event -> statusPane.setMessage((String)event));

        eventBus.postEvent("editor-source-loaded", session.getSource());

        eventBus.postEvent("editor-params-changed", session);

        return rootPane;
    }

    private static TranslateTransition createTranslateTransition(Node node) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(javafx.util.Duration.seconds(0.5));
        return transition;
    }

    private static void showSidebar(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateX() != -((Pane)transition.getNode()).getWidth()) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(-((Pane)transition.getNode()).getWidth());
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void hideSidebar(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateX() != 0) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(0);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void showStatus(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateY() != 0) {
            transition.setFromY(transition.getNode().getTranslateY());
            transition.setToY(0);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void hideStatus(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateY() != ((Pane)transition.getNode()).getHeight()) {
            transition.setFromY(transition.getNode().getTranslateY());
            transition.setToY(((Pane)transition.getNode()).getHeight());
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void showPanel(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateX() != -((Pane)transition.getNode()).getWidth()) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(-((Pane)transition.getNode()).getWidth());
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void hidePanel(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateX() != 0) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(0);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static Try<ImageGenerator, Exception> createGenerator(Session session, RendererTile tile) {
        DefaultThreadFactory threadFactory = new DefaultThreadFactory("MandelbrotHistoryImageGenerator", true, Thread.MIN_PRIORITY);
        return tryPlugin(session.getPluginId(), plugin -> Objects.requireNonNull(plugin.createImageGenerator(threadFactory, new JavaFXRendererFactory(), tile, true)))
                .onFailure(e -> logger.log(Level.WARNING, "Cannot create image generator with pluginId " + session.getPluginId(), e));
    }

    private static Try<Pane, Exception> createEditorPane(Session session, EventBus eventBus) {
        return tryPlugin(session.getPluginId(), plugin -> Objects.requireNonNull(plugin.createEditorPane(session, eventBus)))
                .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor with pluginId " + session.getPluginId(), e));
    }

    private static int computePercentage(double percentage) {
        return (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
    }

    private static RendererTile createSingleTile(int width, int height) {
        RendererSize imageSize = new RendererSize(width, height);
        RendererSize tileSize = new RendererSize(width, height);
        RendererSize tileBorder = new RendererSize(0, 0);
        RendererPoint tileOffset = new RendererPoint(0, 0);
        return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
    }

    private static ImageView createIconImage(String name, double percentage) {
        int size = computePercentage(percentage);
        InputStream stream = MainEditorPane.class.getResourceAsStream(name);
        ImageView image = new ImageView(new Image(stream));
        image.setSmooth(true);
        image.setFitWidth(size);
        image.setFitHeight(size);
        return image;
    }

    private static ImageView createIconImage(String name) {
        return createIconImage(name, 0.018);
    }
}
