/*
 * NextFractal 2.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.common.Bundle;
import com.nextbreakpoint.nextfractal.core.common.Clip;
import com.nextbreakpoint.nextfractal.core.common.CoreFactory;
import com.nextbreakpoint.nextfractal.core.common.Plugins;
import com.nextbreakpoint.nextfractal.core.common.FileManager;
import com.nextbreakpoint.nextfractal.core.common.Session;
import com.nextbreakpoint.nextfractal.core.encode.Encoder;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipAdded;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipMoved;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipRemoved;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipRestored;
import com.nextbreakpoint.nextfractal.core.event.CaptureSessionStarted;
import com.nextbreakpoint.nextfractal.core.event.CaptureSessionStopped;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipsLoaded;
import com.nextbreakpoint.nextfractal.core.event.CaptureClipsMerged;
import com.nextbreakpoint.nextfractal.core.event.CaptureSessionActionFired;
import com.nextbreakpoint.nextfractal.core.event.CurrentFileChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorActionFired;
import com.nextbreakpoint.nextfractal.core.event.EditorDataChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorParamsActionFired;
import com.nextbreakpoint.nextfractal.core.event.EditorDeleteFilesRequested;
import com.nextbreakpoint.nextfractal.core.event.EditorGrammarSelected;
import com.nextbreakpoint.nextfractal.core.event.EditorLoadFileRequested;
import com.nextbreakpoint.nextfractal.core.event.EditorParamsChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorReportChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorSaveFileRequested;
import com.nextbreakpoint.nextfractal.core.event.EditorSourceChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorStoreFileRequested;
import com.nextbreakpoint.nextfractal.core.event.ExportSessionCreated;
import com.nextbreakpoint.nextfractal.core.event.ExportSessionResumed;
import com.nextbreakpoint.nextfractal.core.event.ExportSessionStateChanged;
import com.nextbreakpoint.nextfractal.core.event.ExportSessionStopped;
import com.nextbreakpoint.nextfractal.core.event.ExportSessionSuspended;
import com.nextbreakpoint.nextfractal.core.event.HideControlsFired;
import com.nextbreakpoint.nextfractal.core.event.HistorySessionAdded;
import com.nextbreakpoint.nextfractal.core.event.HistorySessionSelected;
import com.nextbreakpoint.nextfractal.core.event.PlaybackDataChanged;
import com.nextbreakpoint.nextfractal.core.event.PlaybackDataLoaded;
import com.nextbreakpoint.nextfractal.core.event.PlaybackStarted;
import com.nextbreakpoint.nextfractal.core.event.PlaybackStopped;
import com.nextbreakpoint.nextfractal.core.event.RenderDataChanged;
import com.nextbreakpoint.nextfractal.core.event.RenderErrorChanged;
import com.nextbreakpoint.nextfractal.core.event.RenderPointChanged;
import com.nextbreakpoint.nextfractal.core.event.RenderStatusChanged;
import com.nextbreakpoint.nextfractal.core.event.RenderTimeChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionBundleLoaded;
import com.nextbreakpoint.nextfractal.core.event.SessionDataChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionDataLoaded;
import com.nextbreakpoint.nextfractal.core.event.SessionErrorChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionExportRequested;
import com.nextbreakpoint.nextfractal.core.event.SessionReportChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionStatusChanged;
import com.nextbreakpoint.nextfractal.core.event.SessionTerminated;
import com.nextbreakpoint.nextfractal.core.event.TimeAnimationActionFired;
import com.nextbreakpoint.nextfractal.core.event.ToggleBrowserRequested;
import com.nextbreakpoint.nextfractal.core.event.WorkspaceChanged;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import com.nextbreakpoint.nextfractal.core.javafx.UIPlugins;
import com.nextbreakpoint.nextfractal.core.render.RendererSize;
import com.nextbreakpoint.nextfractal.core.common.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.runtime.export.ExportServiceDelegate;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportRenderer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.common.Plugins.tryFindEncoder;
import static com.nextbreakpoint.nextfractal.core.common.Plugins.tryFindFactory;
import static com.nextbreakpoint.nextfractal.core.common.Plugins.tryFindFactoryByGrammar;

public class NextFractalApp extends Application {
    private static Logger logger = Logger.getLogger(NextFractalApp.class.getName());

    // TODO move to constants class
    private static final String DEFAULT_PLUGIN_ID = "Mandelbrot";
    private static final String FILE_EXTENSION = ".nf.zip";

    // TODO move to coordinator class
    private List<Clip> clips = new ArrayList<>();
    private Session session;
    private Clip clip;

    // TODO move to coordinator class
    private boolean capture;
    private boolean edited;

    // TODO are they required?
    private FileChooser exportFileChooser;
    private FileChooser bundleFileChooser;
    private File exportCurrentFile;
    private File bundleCurrentFile;

    // TODO move to coordinator class
    private File workspace;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        PlatformEventBus eventBus = new PlatformEventBus("Main");

        // TODO move to utility class
        Try.of(() -> Objects.requireNonNull(ToolProvider.getSystemJavaCompiler())).ifFailure(e -> showCompilerAlert());

        logger.info(getNoticeMessage());

        // TODO move to utility class
        printPlugins();

        final Screen primaryScreen = Screen.getPrimary();

        final Rectangle2D visualBounds = primaryScreen.getVisualBounds();
        logger.info("Screen size = (" + visualBounds.getWidth() + ", " + visualBounds.getHeight() + "), dpi = " + primaryScreen.getDpi());

        int optimalImageSize = computeOptimalImageSize(primaryScreen);
        logger.info("Optimal image size = " + optimalImageSize + "px");

        int renderWidth = (int) Math.rint(optimalImageSize);
        int editorWidth = (int) Math.rint(optimalImageSize * 0.7);

        final StackPane rootPane = new StackPane();

        final int optimalFontSize = computeOptimalFontSize(primaryScreen);
        logger.info("Optimal font size = " + optimalFontSize + "pt");

        final DoubleProperty fontSize = new SimpleDoubleProperty(optimalFontSize);
        rootPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));

        final ExportServiceDelegate delegate = (session, state, progress) -> eventBus.postEvent(ExportSessionStateChanged.class.getSimpleName(), ExportSessionStateChanged.builder().session(session).state(state).progress(progress).build());
        final ExportRenderer exportRenderer = new SimpleExportRenderer(createThreadFactory("Export Renderer"));
        final ExportService exportService = new SimpleExportService(delegate, createThreadFactory("Export Service"), exportRenderer);

//		eventBus.register("editor-grammar-changed", new EditorGrammarChangedValidator());

        eventBus.subscribe("editor-grammar-changed", event -> tryFindFactoryByGrammar((String) event[0]).ifPresent(factory -> createSession(eventBus, factory)));

        eventBus.subscribe("session-data-changed", event -> session = (Session) event[0]);

        eventBus.subscribe("session-data-changed", event -> handleSessionChanged((Session) event[0]));

        eventBus.subscribe("session-terminated", event -> handleSessionTerminate(exportService));

        eventBus.subscribe("export-session-created", event -> handleExportSessionCreated(exportService, (ExportSession) event[0]));

        eventBus.subscribe("export-session-stopped", event -> handleExportSessionStopped(exportService, (ExportSession) event[0]));

        eventBus.subscribe("export-session-resumed", event -> handleExportSessionResumed(exportService, (ExportSession) event[0]));

        eventBus.subscribe("export-session-suspended", event -> handleExportSessionSuspended(exportService, (ExportSession) event[0]));

        eventBus.subscribe("editor-load-file", event -> handleLoadFile(eventBus, (File) event[0]));

        eventBus.subscribe("editor-save-file", event -> handleSaveFile(eventBus, (File) event[0]));

        eventBus.subscribe("editor-store-file", event -> handleStoreFile(eventBus, (File) event[0]));

        eventBus.subscribe("editor-delete-files", event -> handleDeleteFiles((List<File>) event[0]));

        eventBus.subscribe("session-error-changed", event -> handleErrorChanged((String) event[0]));

        eventBus.subscribe("capture-session", event -> handleCaptureSession(eventBus, (String) event[0]));

        eventBus.subscribe("capture-clip-restored", event -> handleClipRestored((Clip) event[0]));

        eventBus.subscribe("capture-clip-removed", event -> handleClipRemoved((Clip) event[0]));

        eventBus.subscribe("capture-clip-added", event -> handleClipAdded((Clip) event[0]));

        eventBus.subscribe("capture-clip-moved", event -> handleClipMoved((int) event[0], (int) event[1]));

        eventBus.subscribe("session-bundle-loaded", event -> handleBundleLoaded(eventBus, (Bundle) event[0], (boolean) event[1], (boolean) event[2]));

        eventBus.subscribe("session-export", event -> handleExportSession(eventBus, primaryStage, (String) event[1], session, clips, file -> exportCurrentFile = file, (RendererSize) event[0]));

        eventBus.subscribe("current-file-changed", event -> bundleCurrentFile = (File) event[0]);

        eventBus.subscribe("editor-action", event -> handleEditorAction(eventBus, primaryStage, (String) event[0]));

        eventBus.subscribe("workspace-changed", event -> workspace = (File) event[0]);

        final Pane mainPane = createMainPane(eventBus, editorWidth, renderWidth, renderWidth);

        rootPane.getChildren().add(mainPane);

        final Scene scene = new Scene(rootPane, renderWidth + editorWidth, renderWidth);
        logger.info("Scene size = (" + scene.getWidth() + ", " + scene.getHeight() + ")");

        loadStyleSheets(scene);

        primaryStage.setOnCloseRequest(e -> {
            if (!isTerminating(eventBus, exportService)) {
                e.consume();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.setTitle(getApplicationName());
        primaryStage.show();

        // TODO refactor
        Platform.runLater(() -> {
            final String defaultPluginId = System.getProperty("initialPluginId", DEFAULT_PLUGIN_ID);
            tryFindFactory(defaultPluginId).ifPresent(factory -> createSession(eventBus, factory));
        });

        //TODO remove after replacing subscribers
        eventBus.subscribe(SessionTerminated.class.getSimpleName(), event -> eventBus.postEvent("session-terminated", ""));
        eventBus.subscribe(HideControlsFired.class.getSimpleName(), event -> eventBus.postEvent("hide-controls", ((HideControlsFired) event[0]).hide()));
        eventBus.subscribe(TimeAnimationActionFired.class.getSimpleName(), event -> eventBus.postEvent("time-animation", ((TimeAnimationActionFired) event[0]).action()));
        eventBus.subscribe(CurrentFileChanged.class.getSimpleName(), event -> eventBus.postEvent("current-file-changed", ((CurrentFileChanged) event[0]).file()));
        eventBus.subscribe(PlaybackStarted.class.getSimpleName(), event -> eventBus.postEvent("playback-clips-start", ((PlaybackStarted) event[0]).clips()));
        eventBus.subscribe(PlaybackStopped.class.getSimpleName(), event -> eventBus.postEvent("playback-clips-stop", ""));
        eventBus.subscribe(PlaybackDataLoaded.class.getSimpleName(), event -> eventBus.postEvent("playback-data-load", ((PlaybackDataLoaded) event[0]).session(), ((PlaybackDataLoaded) event[0]).continuous(), ((PlaybackDataLoaded) event[0]).timeAnimation()));
        eventBus.subscribe(PlaybackDataChanged.class.getSimpleName(), event -> eventBus.postEvent("playback-data-change", ((PlaybackDataChanged) event[0]).session(), ((PlaybackDataChanged) event[0]).continuous(), ((PlaybackDataChanged) event[0]).timeAnimation()));
        eventBus.subscribe(ToggleBrowserRequested.class.getSimpleName(), event -> eventBus.postEvent("toggle-browser", ""));
        eventBus.subscribe(WorkspaceChanged.class.getSimpleName(), event -> eventBus.postEvent("workspace-changed", ((WorkspaceChanged) event[0]).file()));
        eventBus.subscribe(EditorActionFired.class.getSimpleName(), event -> eventBus.postEvent("editor-action", ((EditorActionFired) event[0]).action()));
        eventBus.subscribe(EditorDataChanged.class.getSimpleName(), event -> eventBus.postEvent("editor-data-changed", ((EditorDataChanged) event[0]).session(), ((EditorDataChanged) event[0]).continuous(), ((EditorDataChanged) event[0]).timeAnimation()));
        eventBus.subscribe(EditorSourceChanged.class.getSimpleName(), event -> eventBus.postEvent("editor-source-changed", ((EditorSourceChanged) event[0]).source()));
        eventBus.subscribe(EditorReportChanged.class.getSimpleName(), event -> eventBus.postEvent("editor-report-changed", ((EditorReportChanged) event[0]).report(), ((EditorReportChanged) event[0]).session(), ((EditorReportChanged) event[0]).continuous(), ((EditorReportChanged) event[0]).timeAnimation()));
        eventBus.subscribe(EditorParamsChanged.class.getSimpleName(), event -> eventBus.postEvent("editor-params-changed", ((EditorParamsChanged) event[0]).session()));
        eventBus.subscribe(EditorGrammarSelected.class.getSimpleName(), event -> eventBus.postEvent("editor-grammar-changed", ((EditorGrammarSelected) event[0]).grammar()));
        eventBus.subscribe(EditorParamsActionFired.class.getSimpleName(), event -> eventBus.postEvent("editor-params-action", ((EditorParamsActionFired) event[0]).action()));
        eventBus.subscribe(SessionExportRequested.class.getSimpleName(), event -> eventBus.postEvent("session-export", ((SessionExportRequested) event[0]).size(), ((SessionExportRequested) event[0]).format()));
        eventBus.subscribe(SessionDataLoaded.class.getSimpleName(), event -> eventBus.postEvent("session-data-loaded", ((SessionDataLoaded) event[0]).session(), ((SessionDataLoaded) event[0]).continuous(), ((SessionDataLoaded) event[0]).timeAnimation()));
        eventBus.subscribe(SessionDataChanged.class.getSimpleName(), event -> eventBus.postEvent("session-data-changed", ((SessionDataChanged) event[0]).session(), ((SessionDataChanged) event[0]).continuous(), ((SessionDataChanged) event[0]).timeAnimation()));
        eventBus.subscribe(SessionBundleLoaded.class.getSimpleName(), event -> eventBus.postEvent("session-bundle-loaded", ((SessionBundleLoaded) event[0]).bundle(), ((SessionBundleLoaded) event[0]).continuous(), ((SessionBundleLoaded) event[0]).timeAnimation()));
        eventBus.subscribe(SessionErrorChanged.class.getSimpleName(), event -> eventBus.postEvent("session-error-changed", ((SessionErrorChanged) event[0]).error()));
        eventBus.subscribe(SessionStatusChanged.class.getSimpleName(), event -> eventBus.postEvent("session-status-changed", ((SessionStatusChanged) event[0]).status()));
        eventBus.subscribe(SessionReportChanged.class.getSimpleName(), event -> eventBus.postEvent("session-report-changed", ((SessionReportChanged) event[0]).report(), ((SessionReportChanged) event[0]).session(), ((SessionReportChanged) event[0]).continuous(), ((SessionReportChanged) event[0]).timeAnimation()));
        eventBus.subscribe(CaptureSessionActionFired.class.getSimpleName(), event -> eventBus.postEvent("capture-session", ((CaptureSessionActionFired) event[0]).action()));
        eventBus.subscribe(CaptureSessionStarted.class.getSimpleName(), event -> eventBus.postEvent("capture-session-started", ((CaptureSessionStarted) event[0]).clip()));
        eventBus.subscribe(CaptureSessionStopped.class.getSimpleName(), event -> eventBus.postEvent("capture-session-stopped", ((CaptureSessionStopped) event[0]).clip()));
        eventBus.subscribe(CaptureClipMoved.class.getSimpleName(), event -> eventBus.postEvent("capture-clip-moved", ((CaptureClipMoved) event[0]).fromIndex(), ((CaptureClipMoved) event[0]).toIndex()));
        eventBus.subscribe(CaptureClipAdded.class.getSimpleName(), event -> eventBus.postEvent("capture-clip-added", ((CaptureClipAdded) event[0]).clip()));
        eventBus.subscribe(CaptureClipRemoved.class.getSimpleName(), event -> eventBus.postEvent("capture-clip-removed", ((CaptureClipRemoved) event[0]).clip()));
        eventBus.subscribe(CaptureClipRestored.class.getSimpleName(), event -> eventBus.postEvent("capture-clip-restored", ((CaptureClipRestored) event[0]).clip()));
        eventBus.subscribe(CaptureClipsLoaded.class.getSimpleName(), event -> eventBus.postEvent("capture-clips-loaded", ((CaptureClipsLoaded) event[0]).clips()));
        eventBus.subscribe(CaptureClipsMerged.class.getSimpleName(), event -> eventBus.postEvent("capture-clips-merged", ((CaptureClipsMerged) event[0]).clips()));
        eventBus.subscribe(ExportSessionCreated.class.getSimpleName(), event -> eventBus.postEvent("export-session-created", ((ExportSessionCreated) event[0]).session()));
        eventBus.subscribe(ExportSessionStopped.class.getSimpleName(), event -> eventBus.postEvent("export-session-stopped", ((ExportSessionStopped) event[0]).session()));
        eventBus.subscribe(ExportSessionSuspended.class.getSimpleName(), event -> eventBus.postEvent("export-session-suspended", ((ExportSessionSuspended) event[0]).session()));
        eventBus.subscribe(ExportSessionResumed.class.getSimpleName(), event -> eventBus.postEvent("export-session-resumed", ((ExportSessionResumed) event[0]).session()));
        eventBus.subscribe(HistorySessionAdded.class.getSimpleName(), event -> eventBus.postEvent("history-add-session", ((HistorySessionAdded) event[0]).session()));
        eventBus.subscribe(HistorySessionSelected.class.getSimpleName(), event -> eventBus.postEvent("history-session-selected", ((HistorySessionSelected) event[0]).session()));
        eventBus.subscribe(ExportSessionStateChanged.class.getSimpleName(), event -> eventBus.postEvent("export-session-state-changed", new Object[]{ ((ExportSessionStateChanged) event[0]).session(), ((ExportSessionStateChanged) event[0]).state(), ((ExportSessionStateChanged) event[0]).progress() }));
        eventBus.subscribe(EditorLoadFileRequested.class.getSimpleName(), event -> eventBus.postEvent("editor-load-file", ((EditorLoadFileRequested) event[0]).file()));
        eventBus.subscribe(EditorSaveFileRequested.class.getSimpleName(), event -> eventBus.postEvent("editor-save-file", ((EditorSaveFileRequested) event[0]).file()));
        eventBus.subscribe(EditorStoreFileRequested.class.getSimpleName(), event -> eventBus.postEvent("editor-store-file", ((EditorStoreFileRequested) event[0]).file()));
        eventBus.subscribe(EditorDeleteFilesRequested.class.getSimpleName(), event -> eventBus.postEvent("editor-delete-files", ((EditorDeleteFilesRequested) event[0]).files()));
        eventBus.subscribe(RenderDataChanged.class.getSimpleName(), event -> eventBus.postEvent("render-data-changed", ((RenderDataChanged) event[0]).session(), ((RenderDataChanged) event[0]).continuous(), ((RenderDataChanged) event[0]).timeAnimation()));
        eventBus.subscribe(RenderTimeChanged.class.getSimpleName(), event -> eventBus.postEvent("render-time-changed", ((RenderTimeChanged) event[0]).session(), ((RenderTimeChanged) event[0]).continuous(), ((RenderTimeChanged) event[0]).timeAnimation()));
        eventBus.subscribe(RenderPointChanged.class.getSimpleName(), event -> eventBus.postEvent("render-point-changed", ((RenderPointChanged) event[0]).session(), ((RenderPointChanged) event[0]).continuous(), ((RenderPointChanged) event[0]).timeAnimation()));
        eventBus.subscribe(RenderErrorChanged.class.getSimpleName(), event -> eventBus.postEvent("render-error-changed", ((RenderErrorChanged) event[0]).error()));
        eventBus.subscribe(RenderStatusChanged.class.getSimpleName(), event -> eventBus.postEvent("render-status-changed", ((RenderStatusChanged) event[0]).status()));
    }

    // TODO move to handler class
    private void handleDeleteFiles(List<File> files) {
        final Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setContentText("Do you want to delete the selected %s?".formatted(files.size() > 1 ? files.size() + " projects" : "project"));
        dialog.setTitle("Action required");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
        final Optional<ButtonType> response = dialog.showAndWait();
        if (response.isPresent() && response.get().equals(ButtonType.YES)) {
            files.forEach(file -> {
                try {
                    logger.info("Deleting file " + file);
                    Files.deleteIfExists(file.toPath());
                } catch (IOException e) {
                    logger.warning("Can't delete file " + file);
                }
            });
        }
    }

    // TODO move to handler class
    private boolean isTerminating(PlatformEventBus eventBus, ExportService exportService) {
        if (terminateNow(exportService.getSessionCount())) {
            eventBus.postEvent(SessionTerminated.class.getSimpleName(), SessionTerminated.builder().build());
            return true;
        } else {
            return false;
        }
    }

    // TODO move to handler class
    private boolean terminateNow(int sessionCount) {
        if (sessionCount > 0) {
            final Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setContentText("There are jobs still running. Do you want to terminate them and exit?");
            dialog.setTitle("Action required");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            final Optional<ButtonType> response = dialog.showAndWait();
            return response.isEmpty() || !response.get().equals(ButtonType.NO);
        } else if (edited && clips.size() > 0) {
            final Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setContentText("There are new or modified clips. Do you want to discard them and exit?");
            dialog.setTitle("Action required");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            final Optional<ButtonType> response = dialog.showAndWait();
            return response.isEmpty() || !response.get().equals(ButtonType.NO);
        }
        return true;
    }

    // TODO move to handler class
    private void handleEditorAction(PlatformEventBus eventBus, Window window, String action) {
        if (action.equals("load")) Optional.ofNullable(showLoadBundleFileChooser())
                .map(fileChooser -> fileChooser.showOpenDialog(window))
                .ifPresent(file -> eventBus.postEvent(EditorLoadFileRequested.class.getSimpleName(), EditorLoadFileRequested.builder().file(file).build()));
        if (action.equals("save")) Optional.ofNullable(showSaveBundleFileChooser())
                .map(fileChooser -> fileChooser.showSaveDialog(window))
                .ifPresent(file -> eventBus.postEvent(EditorSaveFileRequested.class.getSimpleName(), EditorSaveFileRequested.builder().file(file).build()));
        if (action.equals("store")) Optional.ofNullable(createNewFile())
                .ifPresent(file -> eventBus.postEvent(EditorStoreFileRequested.class.getSimpleName(), EditorStoreFileRequested.builder().file(file).build()));
    }

    // TODO move to handler class
    private void handleBundleLoaded(PlatformEventBus eventBus, Bundle bundle, boolean continuous, boolean appendHistory) {
        if (edited && clips.size() > 0 && bundle.getClips().size() > 0) {
            final Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setContentText("There are new or modified clips. Do you want to discard them?");
            dialog.setTitle("Action required");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
            final Optional<ButtonType> response = dialog.showAndWait();
            if (response.isPresent() && response.get().equals(ButtonType.NO)) {
                eventBus.postEvent(CaptureClipsMerged.class.getSimpleName(), CaptureClipsMerged.builder().clips(bundle.getClips()).build());
            } else {
                eventBus.postEvent(CaptureClipsLoaded.class.getSimpleName(), CaptureClipsLoaded.builder().clips(bundle.getClips()).build());
            }
        } else {
            if (edited && clips.size() > 0) {
                eventBus.postEvent(CaptureClipsMerged.class.getSimpleName(), CaptureClipsMerged.builder().clips(bundle.getClips()).build());
            } else {
                eventBus.postEvent(CaptureClipsLoaded.class.getSimpleName(), CaptureClipsLoaded.builder().clips(bundle.getClips()).build());
            }
        }
        eventBus.postEvent(SessionDataLoaded.class.getSimpleName(), SessionDataLoaded.builder().session(bundle.getSession()).continuous(continuous).timeAnimation(appendHistory).build());
    }

    // TODO move to coordinator class
    private void handleClipAdded(Clip clip) {
        clips.add(clip);
        edited = true;
    }

    // TODO move to coordinator class
    private void handleClipRemoved(Clip clip) {
        clips.remove(clip);
        edited = true;
    }

    // TODO move to coordinator class
    private void handleClipRestored(Clip clip) {
        clips.add(clip);
        edited = false;
    }

    // TODO move to coordinator class
    private void handleClipMoved(int fromIndex, int toIndex) {
        Clip clip = clips.get(fromIndex);
        clips.remove(fromIndex);
        clips.add(toIndex, clip);
        edited = true;
    }

    private void handleCaptureSession(PlatformEventBus eventBus, String action) {
        if (action.equals("start")) startCapture(eventBus);
        if (action.equals("stop")) stopCapture(eventBus);
    }

    // TODO move to coordinator class
    private void startCapture(PlatformEventBus eventBus) {
        capture = true;
        clip = new Clip();
        if (session != null) {
            clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
        }
        eventBus.postEvent(CaptureSessionStarted.class.getSimpleName(), CaptureSessionStarted.builder().clip(clip).build());
    }

    // TODO move to coordinator class
    private void stopCapture(PlatformEventBus eventBus) {
        capture = false;
        if (session != null) {
            clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
        }
        eventBus.postEvent(CaptureSessionStopped.class.getSimpleName(), CaptureSessionStopped.builder().clip(clip).build());
    }

    // TODO move to coordinator class
    private void handleSessionChanged(Session session) {
        if (capture) {
            clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
        }
    }

    // TODO move to coordinator class
    private void handleErrorChanged(String error) {
    }

    // TODO move to coordinator class
    private void handleLoadFile(PlatformEventBus eventBus, File file) {
        FileManager.loadFile(file)
                .onSuccess(session -> eventBus.postEvent(CurrentFileChanged.class.getSimpleName(), CurrentFileChanged.builder().file(file).build()))
                .onFailure(e -> showLoadError(eventBus, file, e))
                .ifPresent(bundle -> eventBus.postEvent(SessionBundleLoaded.class.getSimpleName(), SessionBundleLoaded.builder().bundle(bundle).continuous(false).timeAnimation(true).build()));
    }

    // TODO move to coordinator class
    private void handleSaveFile(PlatformEventBus eventBus, File file) {
        FileManager.saveFile(file, new Bundle(session, clips))
                .onSuccess(session -> eventBus.postEvent(CurrentFileChanged.class.getSimpleName(), CurrentFileChanged.builder().file(file).build()))
                .onFailure(e -> showSaveError(eventBus, file, e))
                .ifSuccess(v -> edited = false);
    }

    // TODO move to coordinator class
    private void handleStoreFile(PlatformEventBus eventBus, File file) {
        FileManager.saveFile(file, new Bundle(session, clips))
                .onFailure(e -> showSaveError(eventBus, file, e))
                .ifSuccess(v -> edited = false);
    }

    private void showLoadError(PlatformEventBus eventBus, File file, Exception e) {
        logger.log(Level.WARNING, "Cannot load file " + file.getAbsolutePath(), e);
        eventBus.postEvent(SessionStatusChanged.class.getSimpleName(), SessionStatusChanged.builder().status("Cannot load file " + file.getName()).build());
    }

    private void showSaveError(PlatformEventBus eventBus, File file, Exception e) {
        logger.log(Level.WARNING, "Cannot save file " + file.getAbsolutePath(), e);
        eventBus.postEvent(SessionStatusChanged.class.getSimpleName(), SessionStatusChanged.builder().status("Cannot save file " + file.getName()).build());
    }

    private void handleSessionTerminate(ExportService exportService) {
        logger.info("Terminating services...");
        exportService.shutdown();
    }

    private void handleExportSessionCreated(ExportService exportService, ExportSession exportSession) {
        exportService.startSession(exportSession);
    }

    private void handleExportSessionStopped(ExportService exportService, ExportSession exportSession) {
        exportService.stopSession(exportSession);
    }

    private void handleExportSessionResumed(ExportService exportService, ExportSession exportSession) {
        exportService.resumeSession(exportSession);
    }

    private void handleExportSessionSuspended(ExportService exportService, ExportSession exportSession) {
        exportService.suspendSession(exportSession);
    }

    private Pane createMainPane(PlatformEventBus eventBus, int editorWidth, int renderWidth, int height) {
        final int width = renderWidth + editorWidth;
        final Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        mainPane.setMaxWidth(width);
        mainPane.setMaxHeight(height);
        final Pane centralRootPane = createCentralPane(eventBus, renderWidth, height);
        final Pane sideRootPane = createSidePane(eventBus, editorWidth, height);
        mainPane.getChildren().add(centralRootPane);
        mainPane.getChildren().add(sideRootPane);
        mainPane.getStyleClass().add("application");
        sideRootPane.setLayoutX(renderWidth);
        return mainPane;
    }

    private Pane createCentralPane(PlatformEventBus eventBus, int width, int height) {
        final MainCentralPane renderRootPane = new MainCentralPane(eventBus, width, height);
        renderRootPane.setPrefWidth(width);
        renderRootPane.setPrefHeight(height);
        renderRootPane.setMinWidth(width);
        renderRootPane.setMinHeight(height);
        renderRootPane.setMaxWidth(width);
        renderRootPane.setMaxHeight(height);
        renderRootPane.getStyleClass().add("central-pane");
        return renderRootPane;
    }

    private MainSidePane createSidePane(PlatformEventBus eventBus, int width, int height) {
        final MainSidePane sideRootPane = new MainSidePane(eventBus);
        sideRootPane.setPrefWidth(width);
        sideRootPane.setPrefHeight(height);
        sideRootPane.getStyleClass().add("side-pane");
        return sideRootPane;
    }

    private ImageView createIconImage(String name) {
        final InputStream stream = getClass().getResourceAsStream(name);
        final ImageView image = new ImageView(new Image(stream));
        image.setSmooth(true);
        image.setFitWidth(32);
        image.setFitHeight(32);
        return image;
    }

    private String getApplicationName() {
        return "NextFractal 2.1.5";
    }

    private String getNoticeMessage() {
        return "\n\nNextFractal 2.1.5\n\n" +
                "https://github.com/nextbreakpoint/nextfractal\n\n" +
                "Copyright 2015-2024 Andrea Medeghini\n\n" +
                "NextFractal is an application for creating fractals and other graphics artifacts.\n\n" +
                "NextFractal is free software: you can redistribute it and/or modify\n" +
                "it under the terms of the GNU General Public License as published by\n" +
                "the Free Software Foundation, either version 3 of the License, or\n" +
                "(at your option) any later version.\n\n" +
                "NextFractal is distributed in the hope that it will be useful,\n" +
                "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "GNU General Public License for more details.\n\n" +
                "You should have received a copy of the GNU General Public License\n" +
                "along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.\n\n";
    }

    private void showCompilerAlert() {
        final ButtonType exitButtonType = new ButtonType("Continue", ButtonData.OK_DONE);
        final Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(exitButtonType);
        dialog.setGraphic(createIconImage("/icon-errors.png"));
        dialog.setTitle("Warning");
        dialog.setHeaderText("Cannot find Java compiler in your classpath");
        dialog.setContentText("Java compiler is required to reduce computation time");
        dialog.showAndWait();
    }

    private void loadStyleSheets(Scene scene) {
        tryLoadResource("/theme.css").ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL)));

        UIPlugins.factories().map(factory -> factory.loadResource("/" + factory.getId().toLowerCase() + ".css")
                        .onFailure(e -> logger.log(Level.WARNING, "Cannot load style sheet " + factory.getId().toLowerCase() + ".css", e)))
                .forEach(maybeURL -> maybeURL.ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL))));
    }

    private int computeOptimalImageSize(Screen screen) {
        return (int) Math.rint(Math.max(screen.getVisualBounds().getWidth() * 0.5, 800));
    }

    private int computeOptimalFontSize(Screen screen) {
        int size = 8;

        if (screen.getDpi() > 100 || screen.getVisualBounds().getWidth() > 1200) {
            size = 12;
        }

        if (screen.getDpi() > 200 || screen.getVisualBounds().getWidth() > 2400) {
            size = 16;
        }

        return size;
    }

    // TODO move to utility class
    private DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }

    private Try<String, Exception> tryLoadResource(String resourceName) {
        return Try.of(() -> getClass().getResource(resourceName).toExternalForm());
    }

    // TODO move to coordinator class
    private void createSession(PlatformEventBus eventBus, CoreFactory factory) {
        tryCreateSession(factory).ifPresent(session -> eventBus.postEvent(SessionDataLoaded.class.getSimpleName(), SessionDataLoaded.builder().session(session).continuous(false).timeAnimation(true).build()));
    }

    private Try<Session, Exception> tryCreateSession(CoreFactory factory) {
        return Try.of(() -> Objects.requireNonNull(factory.createSession())).onFailure(e -> logger.log(Level.WARNING, "Cannot create session for " + factory.getId(), e));
    }

    private void printPlugins() {
        Plugins.factories().forEach(plugin -> logger.fine("Found plugin " + plugin.getId()));
    }

    // TODO move to handler class
    private void handleExportSession(PlatformEventBus eventBus, Window window, String format, Session session, List<Clip> clips, Consumer<File> consumer, RendererSize size) {
        createEncoder(format).ifPresent(encoder -> selectFileAndExport(eventBus, window, encoder, session, clips, consumer, size));
    }

    private Optional<? extends Encoder> createEncoder(String format) {
        return tryFindEncoder(format).onFailure(e -> logger.warning("Cannot find encoder for format " + format)).value();
    }

    private void selectFileAndExport(PlatformEventBus eventBus, Window window, Encoder encoder, Session session, List<Clip> clips, Consumer<File> consumer, RendererSize size) {
        final Consumer<File> fileConsumer = file -> createExportSession(eventBus, size, encoder, file, session, clips);
        Optional.ofNullable(prepareExportFileChooser(encoder.getSuffix()).showSaveDialog(window)).ifPresent(fileConsumer.andThen(consumer));
    }

    private void createExportSession(PlatformEventBus eventBus, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
        startExportSession(eventBus, UUID.randomUUID().toString(), size, encoder, file, session, clips);
    }

    private String createFileName() {
        return new SimpleDateFormat("YYYYMMdd-HHmmss").format(new Date());
    }

    private void startExportSession(PlatformEventBus eventBus, String uuid, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
        try {
            final File tmpFile = File.createTempFile("export-" + uuid, ".dat");
            final ExportSession exportSession = new ExportSession(uuid, session, encoder.isVideoSupported() ? clips : new LinkedList<>(), file, tmpFile, size, 400, encoder);
            logger.info("Export session created: " + exportSession.getSessionId());
            eventBus.postEvent(ExportSessionCreated.class.getSimpleName(), ExportSessionCreated.builder().session(exportSession).build());
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

    private FileChooser prepareExportFileChooser(String suffix) {
        ensureExportFileChooser(suffix);
        exportFileChooser.setTitle("Export");
        exportFileChooser.setInitialFileName(createFileName() + suffix);
        if (exportCurrentFile != null) {
            exportFileChooser.setInitialDirectory(exportCurrentFile.getParentFile());
        }
        return exportFileChooser;
    }

    private void ensureBundleFileChooser(String suffix) {
        if (bundleFileChooser == null) {
            bundleFileChooser = new FileChooser();
            bundleFileChooser.setInitialFileName(createFileName() + suffix);
            bundleFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
    }

    private FileChooser showSaveBundleFileChooser() {
        ensureBundleFileChooser(FILE_EXTENSION);
        bundleFileChooser.setTitle("Save");
        if (bundleCurrentFile != null) {
            bundleFileChooser.setInitialDirectory(bundleCurrentFile.getParentFile());
            if (bundleCurrentFile.getName().endsWith(FILE_EXTENSION)) {
                bundleFileChooser.setInitialFileName(bundleCurrentFile.getName());
            } else {
                bundleFileChooser.setInitialFileName(createFileName() + FILE_EXTENSION);
            }
        }
        return bundleFileChooser;
    }

    private FileChooser showLoadBundleFileChooser() {
        ensureBundleFileChooser(FILE_EXTENSION);
        bundleFileChooser.setTitle("Load");
        if (bundleCurrentFile != null) {
            bundleFileChooser.setInitialDirectory(bundleCurrentFile.getParentFile());
            bundleFileChooser.setInitialFileName(bundleCurrentFile.getName());
        }
        return bundleFileChooser;
    }

    private File createNewFile() {
        if (!workspace.exists() || !workspace.canWrite()) {
            throw new IllegalStateException("Can't create file");
        }
        File file = new File(workspace, System.currentTimeMillis() + ".nf.zip");
        while (file.exists()) {
            file = new File(workspace, System.currentTimeMillis() + ".nf.zip");
        }
        return file;
    }

//	if (Desktop.isDesktopSupported()) {
//		Desktop.getDesktop().browse(new URI("http://nextfractal.nextbreakpoint.com"));
//	}
}
