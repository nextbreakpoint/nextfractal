package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RecordingPane extends Pane {
    private static final int FRAMES_PER_SECOND = 1;

    private final ScheduledExecutorService executor;
    private final Canvas canvas;
    private ScheduledFuture<?> future;
    private int frame;

    public RecordingPane() {
        executor = Executors.newSingleThreadScheduledExecutor(Objects.requireNonNull(createThreadFactory("Recording")));

        canvas = new Canvas(50, 50);

        getChildren().add(canvas);

        widthProperty().addListener((observable, oldValue, newValue) -> {
          canvas.setLayoutX(newValue.doubleValue() - 50 - 30);
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setLayoutY(30);
        });
    }

    private DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }

    private void updateUI() {
        frame += 1;
        GraphicsContext g2d = canvas.getGraphicsContext2D();
        g2d.clearRect(0 ,0, canvas.getWidth(), canvas.getHeight());
        if (frame % 2 == 1) {
            g2d.setFill(Color.RED);
            g2d.setGlobalAlpha(0.8);
            g2d.fillOval(0 ,0, canvas.getWidth(), canvas.getHeight());
        }
    }

    public void start() {
        stop();
        frame = 0;
        future = executor.scheduleAtFixedRate(() -> Platform.runLater(() -> updateUI()), 0, 1000 / FRAMES_PER_SECOND, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (future != null) {
            future.cancel(true);
            try {
                future.get();
            } catch (Exception e) {
            }
            future = null;
        }
    }
}
