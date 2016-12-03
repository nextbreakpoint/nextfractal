package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.ImageGenerator;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
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
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class JobsPane extends BorderPane {
    private static final int PADDING = 8;

    private final ThreadFactory threadFactory = new DefaultThreadFactory("Jobs Renderer", true, Thread.MIN_PRIORITY);

    private final ScheduledExecutorService executor;
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
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends Bitmap> c) -> updateButtons(buttonsList, c.getList().size() == 0));

        suspendButton.setOnAction(e -> selectedItems(listView).filter(bitmap -> bitmap.getProperty("paused") == Boolean.FALSE)
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionSuspended((Session) bitmap.getProperty("session")))));

        resumeButton.setOnAction(e -> selectedItems(listView).filter(bitmap -> bitmap.getProperty("paused") == Boolean.TRUE)
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionResumed((Session) bitmap.getProperty("session")))));

        removeButton.setOnAction(e -> selectedItems(listView)
            .forEach(bitmap -> Optional.ofNullable(delegate).ifPresent(delegate -> delegate.sessionStopped((Session) bitmap.getProperty("session")))));

        executor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("Jobs List", true, Thread.MIN_PRIORITY));
        executor.scheduleWithFixedDelay(() -> Platform.runLater(() -> updateJobList(listView)), 500, 500, TimeUnit.MILLISECONDS);
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
            Session session = (Session) bitmap.getProperty("session");
            Boolean paused = (Boolean) bitmap.getProperty("paused");
            String uuid = (String) bitmap.getProperty("uuid");
            if (paused) {
                bitmaps.remove(i);
            } else {
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

    private void submitItem(String uuid, Session session, ImageGenerator generator) {
        executor.submit(() -> Try.of(() -> generator.renderImage(session.getData()))
            .ifPresent(pixels -> Platform.runLater(() -> addItem(listView, uuid, session, pixels, generator.getSize()))));
    }

    private void addItem(ListView<Bitmap> listView, String uuid, Session session, IntBuffer pixels, RendererSize size) {
        BrowseBitmap bitmap = new BrowseBitmap(size.getWidth(), size.getHeight(), pixels);
        bitmap.setProperty("session", session);
        bitmap.setProperty("paused", false);
        bitmap.setProperty("uuid", uuid);
        listView.getItems().add(0, bitmap);
    }

    public void appendSession(String uuid, Session session) {
        tryFindFactory(session.getPluginId()).map(factory -> factory.createImageGenerator(threadFactory,
            new JavaFXRendererFactory(), tile, true)).ifPresent(generator -> submitItem(uuid, session, generator));
    }

    public void setDelegate(JobsDelegate delegate) {
        this.delegate = delegate;
    }
}
