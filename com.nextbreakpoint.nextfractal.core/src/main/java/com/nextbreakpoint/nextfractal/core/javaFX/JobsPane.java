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
package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class JobsPane extends BorderPane {
    private static Logger logger = Logger.getLogger(JobsPane.class.getName());
    private static final int PADDING = 8;

    private final ExecutorService executor;
    private final ListView<Bitmap> listView;
    private JobsDelegate delegate;
    private RendererTile tile;

    public JobsPane(RendererTile tile) {
        this.tile = tile;

        listView = new ListView<>();
        listView.setFixedCellSize(tile.getTileSize().getHeight() + PADDING);
        listView.getStyleClass().add("jobs");
        listView.setCellFactory(view -> new JobsListCell(tile));

        HBox buttons = new HBox(0);
        buttons.setAlignment(Pos.CENTER);
        Button suspendButton = new Button("", createIconImage("/icon-suspend.png", 0.015));
        Button resumeButton = new Button("", createIconImage("/icon-resume.png", 0.015));
        Button removeButton = new Button("", createIconImage("/icon-remove.png", 0.015));
        suspendButton.setTooltip(new Tooltip("Suspend selected jobs"));
        resumeButton.setTooltip(new Tooltip("Resume selected jobs"));
        removeButton.setTooltip(new Tooltip("Remove selected jobs"));
        suspendButton.setDisable(true);
        resumeButton.setDisable(true);
        removeButton.setDisable(true);
        buttons.getChildren().add(suspendButton);
        buttons.getChildren().add(resumeButton);
        buttons.getChildren().add(removeButton);
        buttons.getStyleClass().add("toolbar");

        setCenter(listView);
        setBottom(buttons);

        List<Button> buttonsList = Arrays.asList(suspendButton, resumeButton, removeButton);
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Bitmap> c) -> updateButtons(buttonsList, c.getList().size() == 0));

        suspendButton.setOnAction(e -> selectedItems(listView).filter(bitmap -> !((ExportSession) bitmap.getProperty("exportSession")).isSuspended())
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionSuspended((ExportSession) bitmap.getProperty("exportSession")))));

        resumeButton.setOnAction(e -> selectedItems(listView).filter(bitmap -> ((ExportSession) bitmap.getProperty("exportSession")).isSuspended())
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionResumed((ExportSession) bitmap.getProperty("exportSession")))));

        removeButton.setOnAction(e -> selectedItems(listView)
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionStopped((ExportSession) bitmap.getProperty("exportSession")))));

        executor = Executors.newSingleThreadExecutor(createThreadFactory("Jobs List"));
    }

    private DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }

    private void updateButtons(List<Button> buttons, boolean disabled) {
        buttons.stream().forEach(button -> button.setDisable(disabled));
    }

    private Stream<Bitmap> selectedItems(ListView<Bitmap> jobsList) {
        return jobsList.getSelectionModel().getSelectedItems().stream();
    }

    private int computePercentage(double percentage) {
        return (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
    }

    private ImageView createIconImage(String name, double percentage) {
        int size = computePercentage(percentage);
        InputStream stream = getClass().getResourceAsStream(name);
        ImageView image = new ImageView(new Image(stream));
        image.setSmooth(true);
        image.setFitWidth(size);
        image.setFitHeight(size);
        return image;
    }

    private void updateJobList(ListView<Bitmap> jobsList) {
        ObservableList<Bitmap> bitmaps = jobsList.getItems();
        for (int i = bitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = bitmaps.get(i);
            ExportSession session = (ExportSession) bitmap.getProperty("exportSession");
            if (session.isStopped()) {
                bitmaps.remove(i);
            } else {
                bitmap.setProgress(session.getProgress());
                if (jobsList.getSelectionModel().isSelected(i)) {
                    triggerUpdate(jobsList, bitmap, i);
                    jobsList.getSelectionModel().select(i);
                } else {
                    triggerUpdate(jobsList, bitmap, i);
                }
            }
        }
    }

    private <T> void triggerUpdate(ListView<T> listView, T newValue, int index) {
        listView.fireEvent(new ListView.EditEvent<>(listView, ListView.editCommitEvent(), newValue, index));
    }

    private void itemSelected(ListView<Bitmap> listView) {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
        }
    }

    private void submitItem(ExportSession session, ImageGenerator generator) {
        executor.submit(() -> Try.of(() -> generator.renderImage(session.getSession().getScript(), session.getSession().getMetadata()))
            .ifPresent(pixels -> Platform.runLater(() -> addItem(listView, session, pixels, generator.getSize()))));
    }

    private void addItem(ListView<Bitmap> listView, ExportSession session, IntBuffer pixels, RendererSize size) {
        BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), pixels);
        bitmap.setProperty("exportSession", session);
        bitmap.setProperty("session", session.getSession());
        listView.getItems().add(0, bitmap);
    }


    public void appendSession(ExportSession session) {
        tryFindFactory(session.getPluginId()).map(factory -> factory.createImageGenerator(createThreadFactory("Jobs Renderer"),
            new JavaFXRendererFactory(), tile, true)).ifPresent(generator -> submitItem(session, generator));
    }

    public void setDelegate(JobsDelegate delegate) {
        this.delegate = delegate;
    }

    public void updateSessions() {
        updateJobList(listView);
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    public void dispose() {
        List<ExecutorService> executors = Arrays.asList(executor);
        executors.forEach(executor -> executor.shutdownNow());
        executors.forEach(executor -> await(executor));
    }

    private void await(ExecutorService executor) {
        Try.of(() -> executor.awaitTermination(5000, TimeUnit.MILLISECONDS)).onFailure(e -> logger.warning("Await termination timeout")).execute();
    }
}
