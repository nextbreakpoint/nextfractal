package com.nextbreakpoint.nextfractal.core.javaFX;

import com.nextbreakpoint.nextfractal.core.Clip;
import com.nextbreakpoint.nextfractal.core.ClipProcessor;
import com.nextbreakpoint.nextfractal.core.Frame;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class PlaybackPane extends Pane {
    private static final int FRAMES_PER_SECOND = 50;

    private final List<Frame> frames = new LinkedList<>();
    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> future;
    private int frameIndex = -1;
    private Frame lastFrame;
    private PlaybackDelegate delegate;

    public PlaybackPane() {
        executor = Executors.newSingleThreadScheduledExecutor(Objects.requireNonNull(createThreadFactory("Playback")));

        setOnMouseClicked(e -> {
            if (delegate != null) {
                delegate.playbackStopped();
            }
        });

        widthProperty().addListener((observable, oldValue, newValue) -> {
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
        });
    }

    private DefaultThreadFactory createThreadFactory(String name) {
        return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
    }

    private void playNextFrame() {
        try {
            frameIndex += 1;
            if (frameIndex < frames.size()) {
                Frame frame = frames.get(frameIndex);
                if (delegate != null) {
                    if (lastFrame == null || !lastFrame.getPluginId().equals(frame.getPluginId()) || !lastFrame.getScript().equals(frame.getScript())) {
                        tryFindFactory(frame.getPluginId()).map(factory -> factory.createSession(frame.getScript(), frame.getMetadata()))
                            .ifPresent(session -> Platform.runLater(() -> delegate.loadSessionData(session, frame.isKeyFrame(), !frame.isKeyFrame() && frame.isTimeAnimation())));
                    } else if (!lastFrame.getMetadata().equals(frame.getMetadata())) {
                        tryFindFactory(frame.getPluginId()).map(factory -> factory.createSession(frame.getScript(), frame.getMetadata()))
                            .ifPresent(session -> Platform.runLater(() -> delegate.updateSessionData(session, frame.isKeyFrame(), !frame.isKeyFrame() && frame.isTimeAnimation())));
                    } else if (!lastFrame.getMetadata().getTime().equals(frame.getMetadata().getTime())) {
                        tryFindFactory(frame.getPluginId()).map(factory -> factory.createSession(frame.getScript(), frame.getMetadata()))
                            .ifPresent(session -> Platform.runLater(() -> delegate.updateSessionData(session, true, true)));
                    }
                }
                lastFrame = frame;
            } else {
                frameIndex = 0;
                lastFrame = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDelegate(PlaybackDelegate delegate) {
        this.delegate = delegate;
    }

    public void setClips(List<Clip> clips) {
        if (future == null) {
            frames.clear();
            frames.addAll(new ClipProcessor(clips, 1f / FRAMES_PER_SECOND).generateFrames());
        }
    }

    public void start() {
        stop();
        frameIndex = 0;
        lastFrame = null;
        future = executor.scheduleAtFixedRate(() -> playNextFrame(), 0, 1000 / FRAMES_PER_SECOND, TimeUnit.MILLISECONDS);
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
