package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class JobsPane extends BorderPane {
    private static final int PADDING = 8;

    private final ScheduledExecutorService sessionsExecutor;

    public JobsPane(ImageGenerator generator, RendererTile tile, ExportService exportSerivce) {
        ListView<ExportSession> listView = new ListView<>();
        listView.setFixedCellSize(tile.getTileSize().getHeight() + PADDING);
        listView.getStyleClass().add("jobs");
        listView.setCellFactory(view -> new ExportListCell(generator.getSize(), tile));

        HBox buttons = new HBox(0);
        buttons.setAlignment(Pos.CENTER);
        Button suspendButton = new Button("", createIconImage("/icon-suspend.png", 0.015));
        Button resumeButton = new Button("", createIconImage("/icon-resume.png", 0.015));
        Button removeButton = new Button("", createIconImage("/icon-remove.png", 0.015));
        suspendButton.setTooltip(new Tooltip("Suspend selected tasks"));
        resumeButton.setTooltip(new Tooltip("Resume selected tasks"));
        removeButton.setTooltip(new Tooltip("Remove selected tasks"));
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
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ExportSession> c) -> updateButtons(buttonsList, c.getList().size() == 0));

        suspendButton.setOnAction(e -> selectedItems(listView).filter(exportSession -> !exportSession.isSuspended()).forEach(exportSession -> exportSerivce.suspendSession(exportSession)));
        resumeButton.setOnAction(e -> selectedItems(listView).filter(exportSession -> exportSession.isSuspended()).forEach(exportSession -> exportSerivce.resumeSession(exportSession)));
        removeButton.setOnAction(e -> selectedItems(listView).forEach(exportSession -> exportSerivce.stopSession(exportSession)));

        sessionsExecutor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("Jobs", true, Thread.MIN_PRIORITY));
        sessionsExecutor.scheduleWithFixedDelay(() -> Platform.runLater(() -> updateJobList(listView)), 500, 500, TimeUnit.MILLISECONDS);
    }

    private void updateButtons(List<Button> buttons, boolean disabled) {
        buttons.stream().forEach(button -> button.setDisable(disabled));
    }

    private Stream<ExportSession> selectedItems(ListView<ExportSession> jobsList) {
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

    private void updateJobList(ListView<ExportSession> jobsList) {
        ObservableList<ExportSession> sessions = jobsList.getItems();
        for (int i = sessions.size() - 1; i >= 0; i--) {
            ExportSession session = sessions.get(i);
            if (session.isStopped()) {
                sessions.remove(i);
            } else {
                if (jobsList.getSelectionModel().isSelected(i)) {
                    triggerUpdate(jobsList, session, i);
                    jobsList.getSelectionModel().select(i);
                } else {
                    triggerUpdate(jobsList, session, i);
                }
            }
        }
    }

    private <T> void triggerUpdate(ListView<T> listView, T newValue, int index) {
        listView.fireEvent(new ListView.EditEvent<>(listView, ListView.editCommitEvent(), newValue, index));
    }
}
