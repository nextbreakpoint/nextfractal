package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportPane;
import com.nextbreakpoint.nextfractal.core.javaFX.HistoryDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.HistoryPane;
import com.nextbreakpoint.nextfractal.core.javaFX.JobsPane;
import com.nextbreakpoint.nextfractal.core.javaFX.StatusPane;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindEncoder;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class MainEditorPane extends BorderPane {
    public static final String FILE_EXTENSION = ".nf.zip";
    private static Logger logger = Logger.getLogger(MainEditorPane.class.getName());

    private EventBus localEventBus;
    private FileChooser fileChooser;
    private File currentFile;
    private FileChooser exportFileChooser;
    private File exportCurrentFile;
    private Session session;
    private Pane rootPane;

    public MainEditorPane(EventBus eventBus) {
//        widthProperty().addListener((observable, oldValue, newValue) -> Optional.ofNullable(rootPane).ifPresent(pane -> pane.setPrefWidth(newValue.doubleValue())));
//        heightProperty().addListener((observable, oldValue, newValue) -> Optional.ofNullable(rootPane).ifPresent(pane -> pane.setPrefHeight(newValue.doubleValue())));

        EventHandler<ActionEvent> loadEventHandler = e -> Optional.ofNullable(showLoadFileChooser())
                .map(fileChooser -> fileChooser.showOpenDialog(MainEditorPane.this.getScene().getWindow())).ifPresent(file -> eventBus.postEvent("editor-load-file", file));

        EventHandler<ActionEvent> saveEventHandler = e -> Optional.ofNullable(showSaveFileChooser())
                .map(fileChooser -> fileChooser.showSaveDialog(MainEditorPane.this.getScene().getWindow())).ifPresent(file -> eventBus.postEvent("editor-save-file", file));

        int tileSize = computePercentage(0.02);

        RendererTile tile = createSingleTile(tileSize, tileSize);

        HistoryPane historyPane = new HistoryPane(tile);
        historyPane.getStyleClass().add("sidebar");

        JobsPane jobsPane = new JobsPane(tile);
        jobsPane.getStyleClass().add("sidebar");

        eventBus.subscribe("session-changed", event -> handleSessionChanged(getLocalEventBus(eventBus), (Session) event, loadEventHandler, saveEventHandler, historyPane, jobsPane));

        eventBus.subscribe("history-session-selected", event -> notifyHistoryItemSelected(eventBus, event));

        eventBus.subscribe("session-export", event -> handleExportSession(eventBus, jobsPane, (RendererSize) event, session, file -> exportCurrentFile = file));

        eventBus.subscribe("current-file-changed", event -> setCurrentFile((File)event));
    }

    private void notifyHistoryItemSelected(EventBus eventBus, Object event) {
        if (session == null || !session.getPluginId().equals(((Session)event).getPluginId())) {
            eventBus.postEvent("session-changed", event);
        } else {
//            eventBus.postEvent("editor-source-reloaded", event);
            eventBus.postEvent("editor-data-changed", new Object[] { event, false });
        }
    }

    private void handleSessionChanged(EventBus eventBus, Session session, EventHandler<ActionEvent> loadEventHandler, EventHandler<ActionEvent> saveEventHandler, HistoryPane historyPane, JobsPane jobsPane) {
        this.session = session;

        setCenter(null);

        if (rootPane != null) {
            rootPane.getChildren().remove(historyPane);
            rootPane.getChildren().remove(jobsPane);
        }

        rootPane = createRootPane(session, eventBus, loadEventHandler, saveEventHandler, historyPane, jobsPane);

        setCenter(rootPane);
    }

    private EventBus getLocalEventBus(EventBus eventBus) {
        if (localEventBus != null) {
            localEventBus.detach();
        }
        localEventBus = new EventBus(eventBus);
        return localEventBus;
    }

    private static Pane createRootPane(Session session, EventBus eventBus, EventHandler<ActionEvent> loadEventHandler, EventHandler<ActionEvent> saveEventHandler, HistoryPane historyPane, JobsPane jobsPane) {
        StringObservableValue errorProperty = new StringObservableValue();

        errorProperty.setValue(null);

        Pane editorPane = createEditorPane(session, eventBus).orElse(null);

        MainParamsPane paramsPane = new MainParamsPane(eventBus);

        StatusPane statusPane = new StatusPane();

        ExportPane exportPane = new ExportPane();

        paramsPane.getStyleClass().add("sidebar");
        exportPane.getStyleClass().add("sidebar");

        StackPane sidePane = new StackPane();
        sidePane.getChildren().add(jobsPane);
        sidePane.getChildren().add(historyPane);
        sidePane.getChildren().add(exportPane);
        sidePane.getChildren().add(paramsPane);

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
        renderButton.setOnAction(e -> eventBus.postEvent("editor-action", "reload"));
        loadButton.setOnAction(loadEventHandler);
        saveButton.setOnAction(saveEventHandler);

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
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("session-export", rendererSize);
                }
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

        errorProperty.addListener((source, oldValue, newValue) -> {
            exportPane.setDisable(newValue != null);
            paramsPane.setDisable(newValue != null);
        });

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
                showSidebar(sidebarTransition, a -> {
                });
            } else {
                hideSidebar(sidebarTransition, a -> {
                });
            }
        });

        statusButton.selectedProperty().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                showStatus(statusTransition, a -> {
                });
            } else {
                hideStatus(statusTransition, a -> {
                });
            }
        });

        sidePane.translateXProperty().addListener((source, oldValue, newValue) -> {
            editorPane.prefWidthProperty().setValue(rootPane.getWidth() + newValue.doubleValue());
        });

        statusPane.translateYProperty().addListener((source, oldValue, newValue) -> {
            editorPane.prefHeightProperty().setValue(rootPane.getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
            sidePane.prefHeightProperty().setValue(rootPane.getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
        });

        historyPane.setDelegate(new HistoryDelegate() {
            @Override
            public void sessionChanged(Session session) {
                eventBus.postEvent("history-session-selected", session);
            }
        });

        eventBus.subscribe("session-status-changed", event -> statusPane.setMessage((String) event));

        eventBus.subscribe("session-error-changed", event -> errorProperty.setValue((String) event));

        eventBus.subscribe("history-add-session", event -> historyPane.appendSession((Session) event));

        eventBus.postEvent("editor-source-loaded", session);

        eventBus.postEvent("editor-params-changed", session);

        eventBus.subscribe("export-session-created", event -> jobsButton.setSelected(true));

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
        if (transition.getNode().getTranslateX() != -((Pane) transition.getNode()).getWidth()) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(-((Pane) transition.getNode()).getWidth());
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
        if (transition.getNode().getTranslateY() != ((Pane) transition.getNode()).getHeight()) {
            transition.setFromY(transition.getNode().getTranslateY());
            transition.setToY(((Pane) transition.getNode()).getHeight());
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private static void showPanel(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateX() != -((Pane) transition.getNode()).getWidth()) {
            transition.setFromX(transition.getNode().getTranslateX());
            transition.setToX(-((Pane) transition.getNode()).getWidth());
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
        DefaultThreadFactory threadFactory = new DefaultThreadFactory("Export", true, Thread.MIN_PRIORITY);
        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createImageGenerator(threadFactory, new JavaFXRendererFactory(), tile, true)))
            .onFailure(e -> logger.log(Level.WARNING, "Cannot create image generator with pluginId " + session.getPluginId(), e));
    }

    private static Try<Pane, Exception> createEditorPane(Session session, EventBus eventBus) {
        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createEditorPane(eventBus, session)))
            .onFailure(e -> logger.log(Level.WARNING, "Cannot create editor with pluginId " + session.getPluginId(), e));
    }

    private static int computePercentage(double percentage) {
        return (int) Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
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

    private String createFileName() {
        SimpleDateFormat df = new SimpleDateFormat("YYYYMMdd-HHmmss");
        return df.format(new Date());
    }

    private void ensureFileChooser(String suffix) {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setInitialFileName(createFileName() + suffix);
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
    }

    private FileChooser showSaveFileChooser() {
        ensureFileChooser(FILE_EXTENSION);
        fileChooser.setTitle("Save");
        if (getCurrentFile() != null) {
            fileChooser.setInitialDirectory(getCurrentFile().getParentFile());
            fileChooser.setInitialFileName(getCurrentFile().getName());
        }
        return fileChooser;
    }

    private FileChooser showLoadFileChooser() {
        ensureFileChooser(FILE_EXTENSION);
        fileChooser.setTitle("Load");
        if (getCurrentFile() != null) {
            fileChooser.setInitialDirectory(getCurrentFile().getParentFile());
            fileChooser.setInitialFileName(getCurrentFile().getName());
        }
        return fileChooser;
    }

    private File getCurrentFile() {
        return currentFile;
    }

    private void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    private void handleExportSession(EventBus eventBus, JobsPane jobsPane, RendererSize rendererSize, Session session, Consumer<File> consumer) {
        createEncoder("PNG").ifPresent(encoder -> selectFileAndExport(eventBus, jobsPane, rendererSize, encoder, session, consumer));
    }

    private Optional<? extends Encoder> createEncoder(String format) {
        return tryFindEncoder(format).onFailure(e -> logger.warning("Cannot find encoder for PNG format")).value();
    }

    private void selectFileAndExport(EventBus eventBus, JobsPane jobsPane, RendererSize rendererSize, Encoder encoder, Session session, Consumer<File> consumer) {
        Consumer<File> fileConsumer = file -> createExportSession(eventBus, jobsPane, rendererSize, encoder, file, session);
        Optional.ofNullable(prepareExportFileChooser(encoder.getSuffix()).showSaveDialog(MainEditorPane.this.getScene().getWindow())).ifPresent(fileConsumer.andThen(consumer));
    }

    private void createExportSession(EventBus eventBus, JobsPane jobsPane, RendererSize rendererSize, Encoder encoder, File file, Session session) {
        String uuid = UUID.randomUUID().toString();
        jobsPane.appendSession(uuid, session);
        startExportSession(eventBus, uuid, rendererSize, encoder, file, session);
    }

    private FileChooser prepareExportFileChooser(String suffix) {
        ensureExportFileChooser(suffix);
        exportFileChooser.setTitle("Export");
        if (exportCurrentFile != null) {
            exportFileChooser.setInitialDirectory(exportCurrentFile.getParentFile());
            exportFileChooser.setInitialFileName(exportCurrentFile.getName());
        }
        return exportFileChooser;
    }

    private void startExportSession(EventBus eventBus, String uuid, RendererSize rendererSize, Encoder encoder, File file, Object data) {
        try {
            File tmpFile = File.createTempFile("export-" + uuid, ".dat");
            ExportSession exportSession = new ExportSession(uuid, data, file, tmpFile, rendererSize, 200, encoder);
            logger.info("Export session created: " + exportSession.getSessionId());
            eventBus.postEvent("export-session-created", exportSession);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot export data to file " + file.getAbsolutePath(), e);
            //TODO display error
        }
    }

    private void ensureExportFileChooser(String suffix) {
        if (exportFileChooser == null) {
            exportFileChooser = new FileChooser();
            exportFileChooser.setInitialFileName(createFileName() + suffix);
            exportFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
    }
}