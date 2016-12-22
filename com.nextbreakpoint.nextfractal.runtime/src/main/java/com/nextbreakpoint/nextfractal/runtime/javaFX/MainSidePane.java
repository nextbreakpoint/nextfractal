/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.nextfractal.core.Clip;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.export.ExportState;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.ExportPane;
import com.nextbreakpoint.nextfractal.core.javaFX.HistoryDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.HistoryPane;
import com.nextbreakpoint.nextfractal.core.javaFX.JobsDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.JobsPane;
import com.nextbreakpoint.nextfractal.core.javaFX.StatusPane;
import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindEncoder;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;
import static com.nextbreakpoint.nextfractal.core.javaFX.Icons.createIconImage;

public class MainSidePane extends BorderPane {
    public static final String FILE_EXTENSION = ".nf.zip";
    private static Logger logger = Logger.getLogger(MainSidePane.class.getName());

    private List<Clip> clips = new ArrayList<>();
    private FileChooser fileChooser;
    private File currentFile;
    private FileChooser exportFileChooser;
    private File exportCurrentFile;
    private Session session;

    public MainSidePane(EventBus eventBus) {
        EventHandler<ActionEvent> loadEventHandler = e -> Optional.ofNullable(showLoadFileChooser())
                .map(fileChooser -> fileChooser.showOpenDialog(MainSidePane.this.getScene().getWindow())).ifPresent(file -> eventBus.postEvent("editor-load-file", file));

        EventHandler<ActionEvent> saveEventHandler = e -> Optional.ofNullable(showSaveFileChooser())
                .map(fileChooser -> fileChooser.showSaveDialog(MainSidePane.this.getScene().getWindow())).ifPresent(file -> eventBus.postEvent("editor-save-file", file));

        setCenter(createRootPane(eventBus, loadEventHandler, saveEventHandler));

        eventBus.subscribe("session-data-changed", event -> session = (Session) ((Object[])event)[0]);

        eventBus.subscribe("current-file-changed", event -> setCurrentFile((File)event));

        eventBus.subscribe("history-session-selected", event -> notifyHistoryItemSelected(eventBus, event));

        eventBus.subscribe("session-export", event -> handleExportSession(eventBus, (RendererSize) ((Object[])event)[0], (String) ((Object[])event)[1], session, clips, file -> exportCurrentFile = file));

        eventBus.subscribe("capture-clip-added", event -> handleClipAdded((Clip)event));

        eventBus.subscribe("capture-clip-removed", event -> handleClipRemoved((Clip)event));

        eventBus.subscribe("capture-clip-moved-up", event -> handleClipMovedUp((Clip)event));

        eventBus.subscribe("capture-clip-moved-down", event -> handleClipMovedDown((Clip)event));
    }

    private void handleClipAdded(Clip clip) {
        clips.add(clip);
    }

    private void handleClipRemoved(Clip clip) {
        clips.remove(clip);
    }

    private void handleClipMovedUp(Clip clip) {
        int index = clips.indexOf(clip);
        if (index > 0) {
            clips.remove(index);
            clips.add(index - 1, clip);
        }
    }

    private void handleClipMovedDown(Clip clip) {
        int index = clips.indexOf(clip);
        if (index < clips.size() - 1) {
            clips.remove(index);
            clips.add(index, clip);
        }
    }

    private void notifyHistoryItemSelected(EventBus eventBus, Object event) {
        eventBus.postEvent("session-data-loaded", new Object[] { event, false, false });
    }

    private Pane createRootPane(EventBus eventBus, EventHandler<ActionEvent> loadEventHandler, EventHandler<ActionEvent> saveEventHandler) {
        int tileSize = computePercentage(0.02);

        RendererTile tile = createSingleTile(tileSize, tileSize);

        StringObservableValue errorProperty = new StringObservableValue();
        errorProperty.setValue(null);

        MainEditorPane editorPane = new MainEditorPane(eventBus);

        MainParamsPane paramsPane = new MainParamsPane(eventBus);

        StatusPane statusPane = new StatusPane();

        ExportPane exportPane = new ExportPane(tile);

        paramsPane.getStyleClass().add("sidebar");
        exportPane.getStyleClass().add("sidebar");

        HistoryPane historyPane = new HistoryPane(tile);
        historyPane.getStyleClass().add("sidebar");

        JobsPane jobsPane = new JobsPane(tile);
        jobsPane.getStyleClass().add("sidebar");

        StackPane sidePane = new StackPane();
        sidePane.getChildren().add(jobsPane);
        sidePane.getChildren().add(historyPane);
        sidePane.getChildren().add(exportPane);
        sidePane.getChildren().add(paramsPane);

        Pane sourcePane = new Pane();
        HBox sourceButtons = new HBox(0);
        sourceButtons.setAlignment(Pos.CENTER);
        Button browseButton = new Button("", createIconImage(getClass(),"/icon-grid.png"));
        Button renderButton = new Button("", createIconImage(getClass(),"/icon-run.png"));
        Button loadButton = new Button("", createIconImage(getClass(), "/icon-load.png"));
        Button saveButton = new Button("", createIconImage(getClass(), "/icon-save.png"));
        ToggleButton jobsButton = new ToggleButton("", createIconImage(getClass(), "/icon-tool.png"));
        ToggleButton paramsButton = new ToggleButton("", createIconImage(getClass(), "/icon-edit.png"));
        ToggleButton exportButton = new ToggleButton("", createIconImage(getClass(), "/icon-export.png"));
        ToggleButton historyButton = new ToggleButton("", createIconImage(getClass(), "/icon-time.png"));
        ToggleButton statusButton = new ToggleButton("", createIconImage(getClass(), "/icon-warn.png"));
        browseButton.setTooltip(new Tooltip("Show/hide fractals browser"));
        renderButton.setTooltip(new Tooltip("Render fractal"));
        loadButton.setTooltip(new Tooltip("Load fractal from file"));
        saveButton.setTooltip(new Tooltip("Save fractal to file"));
        jobsButton.setTooltip(new Tooltip("Show/hide jobs"));
        paramsButton.setTooltip(new Tooltip("Show/hide parameters"));
        exportButton.setTooltip(new Tooltip("Export fractal as image"));
        historyButton.setTooltip(new Tooltip("Show/hide history"));
        statusButton.setTooltip(new Tooltip("Show/hide console"));
        sourceButtons.getChildren().add(browseButton);
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
        browseButton.setOnAction(e -> eventBus.postEvent("toggle-browser", ""));
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
            public void createSession(RendererSize size, String format) {
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("session-export", new Object[] { size, format });
                }
            }

            @Override
            public void startCaptureSession() {
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("capture-session", "start");
                }
            }

            @Override
            public void stopCaptureSession() {
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("capture-session", "stop");
                }
            }

            @Override
            public void showVideoPreview(List<Clip> clips) {
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("preview-video", clips);
                }
            }

            @Override
            public void captureSessionAdded(Clip clip) {
                eventBus.postEvent("capture-clip-added", clip);
            }

            @Override
            public void captureSessionRemoved(Clip clip) {
                eventBus.postEvent("capture-clip-removed", clip);
            }

            @Override
            public void captureSessionMovedUp(Clip clip) {
                eventBus.postEvent("capture-clip-moved-up", clip);
            }

            @Override
            public void captureSessionMovedDown(Clip clip) {
                eventBus.postEvent("capture-clip-moved-down", clip);
            }
        });

        jobsPane.setDelegate(new JobsDelegate() {
            @Override
            public void sessionSuspended(ExportSession session) {
                eventBus.postEvent("export-session-suspended", session);
            }

            @Override
            public void sessionResumed(ExportSession session) {
                eventBus.postEvent("export-session-resumed", session);
            }

            @Override
            public void sessionStopped(ExportSession session) {
                eventBus.postEvent("export-session-stopped", session);
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
            saveButton.setDisable(newValue != null);
            exportPane.setDisable(newValue != null);
            paramsPane.setParamsDisable(newValue != null);
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

        eventBus.subscribe("export-session-created", event -> jobsButton.setSelected(true));

        eventBus.subscribe("export-session-created", event -> jobsPane.appendSession((ExportSession)event));

        eventBus.subscribe("capture-session-stopped", event -> handleAppendClip(exportPane, (Clip) event));

        eventBus.subscribe("session-data-changed", event -> {
            errorProperty.setValue(null);
            boolean continuous = (Boolean) ((Object[])event)[1];
            if (!continuous) {
                eventBus.postEvent("editor-params-changed", (Session) ((Object[])event)[0]);
            }
        });

        eventBus.subscribe("export-exportEntries-updated", event -> {
            jobsPane.updateSessions();
        });

        eventBus.subscribe("session-terminated", event -> jobsPane.dispose());

        eventBus.subscribe("session-terminated", event -> historyPane.dispose());

        eventBus.subscribe("export-session-state-changed", event -> handleExportSessionStateChanged(jobsPane, (ExportSession)((Object[])event)[0], (ExportState) ((Object[])event)[1], (Float)((Object[])event)[2]));

        eventBus.subscribe("export-sessions-updated", event -> handleExportSessionsUpdated(jobsPane));

        return rootPane;
    }

    private void handleExportSessionsUpdated(JobsPane jobsPane) {
        jobsPane.updateSessions();
    }

    private void handleExportSessionStateChanged(JobsPane jobsPane, ExportSession exportSession, ExportState state, Float progress) {
        logger.info("Session state changed " + exportSession.getSessionId() + " -> " + state.name());
        if (state == ExportState.FINISHED) {
            jobsPane.removeSession(exportSession);
        } else {
            jobsPane.updateSession(exportSession, state, progress);
        }
    }

    private void handleAppendClip(ExportPane exportPane, Clip clip) {
        if (!clip.isEmpty()) exportPane.appendClip(clip);
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

//    private static Try<ImageGenerator, Exception> createGenerator(Session session, RendererTile tile) {
//        DefaultThreadFactory threadFactory = new DefaultThreadFactory("Export", true, Thread.MIN_PRIORITY);
//        return tryFindFactory(session.getPluginId()).map(plugin -> Objects.requireNonNull(plugin.createImageGenerator(threadFactory, new JavaFXRendererFactory(), tile, true)))
//            .onFailure(e -> logger.log(Level.WARNING, "Cannot create image generator with pluginId " + session.getPluginId(), e));
//    }

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

    private void handleExportSession(EventBus eventBus, RendererSize size, String format, Session session, List<Clip> clips, Consumer<File> consumer) {
        createEncoder(format).ifPresent(encoder -> selectFileAndExport(eventBus, size, encoder, session, clips, consumer));
    }

    private Optional<? extends Encoder> createEncoder(String format) {
        return tryFindEncoder(format).onFailure(e -> logger.warning("Cannot find encoder for format " + format)).value();
    }

    private void selectFileAndExport(EventBus eventBus, RendererSize size, Encoder encoder, Session session, List<Clip> clips, Consumer<File> consumer) {
        Consumer<File> fileConsumer = file -> createExportSession(eventBus, size, encoder, file, session, clips);
        Optional.ofNullable(prepareExportFileChooser(encoder.getSuffix()).showSaveDialog(MainSidePane.this.getScene().getWindow())).ifPresent(fileConsumer.andThen(consumer));
    }

    private void createExportSession(EventBus eventBus, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
        startExportSession(eventBus, UUID.randomUUID().toString(), size, encoder, file, session, clips);
    }

    private FileChooser prepareExportFileChooser(String suffix) {
        ensureExportFileChooser(suffix);
        exportFileChooser.setTitle("Export");
        exportFileChooser.setInitialFileName(createFileName() + suffix);
        if (exportCurrentFile != null) {
            exportFileChooser.setInitialDirectory(exportCurrentFile.getParentFile());
        }
        return exportFileChooser;
    }

    private void startExportSession(EventBus eventBus, String uuid, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
        try {
            File tmpFile = File.createTempFile("export-" + uuid, ".dat");
            ExportSession exportSession = new ExportSession(uuid, session, clips, file, tmpFile, size, 400, encoder);
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
            exportFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
    }
}
