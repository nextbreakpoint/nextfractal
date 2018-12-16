/*
 * NextFractal 2.1.0
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
package com.nextbreakpoint.nextfractal.runtime.javafx;

import com.nextbreakpoint.nextfractal.core.common.Clip;
import com.nextbreakpoint.nextfractal.core.javafx.EventBus;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.export.ExportState;
import com.nextbreakpoint.nextfractal.core.javafx.ExportDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.ExportPane;
import com.nextbreakpoint.nextfractal.core.javafx.HistoryPane;
import com.nextbreakpoint.nextfractal.core.javafx.JobsDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.JobsPane;
import com.nextbreakpoint.nextfractal.core.javafx.StatusPane;
import com.nextbreakpoint.nextfractal.core.javafx.StringObservableValue;
import com.nextbreakpoint.nextfractal.core.render.RendererPoint;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import com.nextbreakpoint.nextfractal.core.render.RendererTile;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

import java.util.List;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.javafx.Icons.createIconImage;

public class MainSidePane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainSidePane.class.getName());

    private Session session;

    public MainSidePane(EventBus eventBus) {
        EventBus subEventBus = new EventBus(eventBus);

        setCenter(createRootPane(subEventBus));

        eventBus.subscribe("session-data-changed", event -> session = (Session) event[0]);

        eventBus.subscribe("history-session-selected", event -> notifyHistoryItemSelected(eventBus, (Session)event[0]));

        eventBus.subscribe("playback-data-load", event -> session = (Session) event[0]);

        eventBus.subscribe("playback-data-change", event -> session = (Session) event[0]);

        eventBus.subscribe("playback-clips-start", event -> handlePlaybackClipsStart(subEventBus, this));

        eventBus.subscribe("playback-clips-stop", event -> handlePlaybackClipsStop(subEventBus, this));
    }

    private void handlePlaybackClipsStart(EventBus subEventBus, Pane rootPane) {
        subEventBus.disable();
        rootPane.setDisable(true);
        BoxBlur effect = new BoxBlur();
        effect.setIterations(1);
        rootPane.setEffect(effect);
    }

    private void handlePlaybackClipsStop(EventBus subEventBus, Pane rootPane) {
        rootPane.setEffect(null);
        subEventBus.enable();
        rootPane.setDisable(false);
        subEventBus.postEvent("session-data-loaded", session, false, false);
    }

    private void notifyHistoryItemSelected(EventBus eventBus, Session session) {
        eventBus.postEvent("session-data-loaded", session, false, false);
    }

    private Pane createRootPane(EventBus eventBus) {
        int tileSize = computePercentage(0.03);

        RendererTile tile = createSingleTile(tileSize, tileSize);

        StringObservableValue errorProperty = new StringObservableValue();
        errorProperty.setValue(null);

        MainEditorPane editorPane = new MainEditorPane(eventBus);

        MainParamsPane paramsPane = new MainParamsPane(eventBus);

        StatusPane statusPane = new StatusPane();

        ExportPane exportPane = new ExportPane(tile);

        HistoryPane historyPane = new HistoryPane(tile);

        JobsPane jobsPane = new JobsPane(tile);

        StackPane sidePane = new StackPane();
        sidePane.getStyleClass().add("sidebar");
        sidePane.getChildren().add(jobsPane);
        sidePane.getChildren().add(historyPane);
        sidePane.getChildren().add(exportPane);
        sidePane.getChildren().add(paramsPane);

        Pane sourcePane = new Pane();
        HBox sourceButtons = new HBox(0);
        sourceButtons.setAlignment(Pos.CENTER);
        Button browseButton = new Button("", createIconImage("/icon-grid.png"));
        Button renderButton = new Button("", createIconImage("/icon-run.png"));
        Button loadButton = new Button("", createIconImage("/icon-load.png"));
        Button saveButton = new Button("", createIconImage("/icon-save.png"));
        ToggleButton jobsButton = new ToggleButton("", createIconImage("/icon-tool.png"));
        ToggleButton paramsButton = new ToggleButton("", createIconImage("/icon-edit.png"));
        ToggleButton exportButton = new ToggleButton("", createIconImage("/icon-export.png"));
        ToggleButton historyButton = new ToggleButton("", createIconImage("/icon-time.png"));
        ToggleButton statusButton = new ToggleButton("", createIconImage("/icon-warn.png"));
        browseButton.setTooltip(new Tooltip("Show/hide projects browser"));
        renderButton.setTooltip(new Tooltip("Render image"));
        loadButton.setTooltip(new Tooltip("Load project from file"));
        saveButton.setTooltip(new Tooltip("Save project to file"));
        jobsButton.setTooltip(new Tooltip("Show/hide background jobs"));
        paramsButton.setTooltip(new Tooltip("Show/hide project parameters"));
        exportButton.setTooltip(new Tooltip("Show/hide capture and export controls"));
        historyButton.setTooltip(new Tooltip("Show/hide changes history"));
        statusButton.setTooltip(new Tooltip("Show/hide errors console"));
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
        loadButton.setOnAction(e -> eventBus.postEvent("editor-action", "load"));
        saveButton.setOnAction(e -> eventBus.postEvent("editor-action", "save"));

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
                    eventBus.postEvent("session-export", size, format);
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
            public void playbackStart(List<Clip> clips) {
                if (errorProperty.getValue() == null) {
                    eventBus.postEvent("playback-clips-start", clips);
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
            public void captureSessionRestored(Clip clip) {
                eventBus.postEvent("capture-clip-restored", clip);
            }

            @Override
            public void captureSessionMoved(int fromIndex, int toIndex) {
                eventBus.postEvent("capture-clip-moved", fromIndex, toIndex);
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
            editorPane.setPrefHeight(height * 0.78);
            sidePane.setPrefHeight(height * 0.78);
            statusPane.setPrefHeight(height * 0.15);
            sourceButtons.setLayoutY(0);
            editorPane.setLayoutY(height * 0.07);
            sidePane.setLayoutY(height * 0.07);
            statusPane.setLayoutY(height * 0.85);
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

        historyPane.setDelegate(session -> eventBus.postEvent("history-session-selected", session));

        eventBus.subscribe("session-status-changed", event -> statusPane.setMessage((String) event[0]));

        eventBus.subscribe("session-error-changed", event -> errorProperty.setValue((String) event[0]));

        eventBus.subscribe("history-add-session", event -> historyPane.appendSession((Session) event[0]));

        eventBus.subscribe("export-session-created", event -> jobsButton.setSelected(true));

        eventBus.subscribe("export-session-created", event -> jobsPane.appendSession((ExportSession)event[0]));

        eventBus.subscribe("capture-session-started", event -> handleSessionStarted(exportPane, exportButton, (Clip) event[0]));

        eventBus.subscribe("capture-session-stopped", event -> handleSessionStopped(exportPane, exportButton, (Clip) event[0]));

        eventBus.subscribe("capture-clips-loaded", event -> exportPane.loadClips((List<Clip>) event[0]));

        eventBus.subscribe("capture-clips-merged", event -> exportPane.mergeClips((List<Clip>) event[0]));

        eventBus.subscribe("session-data-changed", event -> handleDataChanged(eventBus, errorProperty, (Session) event[0], (Boolean) event[1]));

        eventBus.subscribe("session-terminated", event -> jobsPane.dispose());

        eventBus.subscribe("session-terminated", event -> historyPane.dispose());

        eventBus.subscribe("export-session-state-changed", event -> handleExportSessionStateChanged(jobsPane, (ExportSession) event[0], (ExportState) event[1], (Float) event[2]));

        eventBus.subscribe("capture-session-started", event -> exportPane.setCaptureSelected(true));

        eventBus.subscribe("capture-session-stopped", event -> exportPane.setCaptureSelected(false));

        return rootPane;
    }

    private void handleDataChanged(EventBus eventBus, StringObservableValue errorProperty, Session session, boolean continuous) {
        errorProperty.setValue(null);
        if (!continuous) {
            eventBus.postEvent("editor-params-changed", session);
        }
    }

    private void handleExportSessionStateChanged(JobsPane jobsPane, ExportSession exportSession, ExportState state, Float progress) {
        logger.info("Session " + exportSession.getSessionId() + " state " + state.name());
        if (state == ExportState.FINISHED) {
            jobsPane.removeSession(exportSession);
        } else {
            jobsPane.updateSession(exportSession, state, progress);
        }
    }

    private void handleSessionStarted(ExportPane exportPane, ToggleButton exportButton, Clip clip) {
    }

    private void handleSessionStopped(ExportPane exportPane, ToggleButton exportButton, Clip clip) {
        if (!clip.isEmpty()) exportPane.appendClip(clip);
        exportButton.setSelected(true);
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
}
