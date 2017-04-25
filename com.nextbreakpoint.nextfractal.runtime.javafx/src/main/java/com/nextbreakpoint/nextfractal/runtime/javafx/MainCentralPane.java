/*
 * NextFractal 2.0.1
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
package com.nextbreakpoint.nextfractal.runtime.javafx;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.Clip;
import com.nextbreakpoint.nextfractal.core.javafx.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.javafx.Bitmap;
import com.nextbreakpoint.nextfractal.core.javafx.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javafx.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javafx.BrowseDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.BrowsePane;
import com.nextbreakpoint.nextfractal.core.javafx.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.javafx.PlaybackDelegate;
import com.nextbreakpoint.nextfractal.core.javafx.PlaybackPane;
import com.nextbreakpoint.nextfractal.core.javafx.RecordingPane;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

import static com.nextbreakpoint.nextfractal.core.javafx.UIPlugins.tryFindFactory;

public class MainCentralPane extends BorderPane {
    public MainCentralPane(EventBus eventBus, int width, int height) {
        MainRenderPane renderPane = new MainRenderPane(eventBus, width, height);

        BooleanObservableValue toggleProperty = new BooleanObservableValue();
        toggleProperty.setValue(false);

        BrowsePane browsePane = new BrowsePane(width, height);
        browsePane.setClip(new Rectangle(0, 0, width, height));

        PlaybackPane playbackPane = new PlaybackPane();
        RecordingPane recordingPane = new RecordingPane();

        TranslateTransition browserTransition = createTranslateTransition(browsePane);

        playbackPane.setDelegate(new PlaybackDelegate() {
            @Override
            public void playbackStopped() {
                eventBus.postEvent("playback-clips-stop", "");
            }

            @Override
            public void loadSessionData(Session session, boolean continuous, boolean timeAnimation) {
                eventBus.postEvent("playback-data-load", session, continuous, timeAnimation);
            }

            @Override
            public void updateSessionData(Session session, boolean continuous, boolean timeAnimation) {
                eventBus.postEvent("playback-data-change", session, continuous, timeAnimation);
            }
        });

		browsePane.setDelegate(new BrowseDelegate() {
			@Override
			public void didSelectFile(BrowsePane source, File file) {
				eventBus.postEvent("editor-load-file", file);
                eventBus.postEvent("toggle-browser", "");
			}

			@Override
			public void didClose(BrowsePane source) {
                eventBus.postEvent("toggle-browser", "");
			}

			@Override
			public GridItemRenderer createRenderer(Bitmap bitmap) throws Exception {
				return tryFindFactory(((Session) bitmap.getProperty("session")).getPluginId())
					.flatMap(factory -> Try.of(() -> factory.createRenderer(bitmap))).orThrow();
			}

			@Override
			public BrowseBitmap createBitmap(File file, RendererSize size) throws Exception {
				return FileManager.loadFile(file).flatMap(bundle -> tryFindFactory(bundle.getSession().getPluginId())
					.flatMap(factory -> Try.of(() -> factory.createBitmap(bundle.getSession(), size)))).orThrow();
			}
		});

        Pane stackPane = new Pane();
        stackPane.getChildren().add(renderPane);
        stackPane.getChildren().add(playbackPane);
        stackPane.getChildren().add(recordingPane);
        stackPane.getChildren().add(browsePane);

        playbackPane.setVisible(false);

        recordingPane.setDisable(true);
        recordingPane.setVisible(false);

        setCenter(stackPane);

        browsePane.setTranslateY(-height);

        widthProperty().addListener((observable, oldValue, newValue) -> {
            renderPane.setPrefWidth(newValue.doubleValue());
            browsePane.setPrefWidth(newValue.doubleValue());
            playbackPane.setPrefWidth(newValue.doubleValue());
            recordingPane.setPrefWidth(newValue.doubleValue());
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
            renderPane.setPrefHeight(newValue.doubleValue());
            browsePane.setPrefHeight(newValue.doubleValue());
            playbackPane.setPrefHeight(newValue.doubleValue());
            recordingPane.setPrefHeight(newValue.doubleValue());
        });

        eventBus.subscribe("session-terminated", event -> browsePane.dispose());

        toggleProperty.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                showBrowser(browserTransition, a -> {});
                browsePane.reload();
            } else {
                hideBrowser(browserTransition, a -> {});
            }
        });

        eventBus.subscribe("toggle-browser", event -> {
            toggleProperty.setValue(!toggleProperty.getValue());
        });

        eventBus.subscribe("playback-clips-start", event -> {
            browsePane.setDisable(true);
            renderPane.setDisable(true);
            toggleProperty.setValue(false);
            playbackPane.setVisible(true);
            playbackPane.setClips((List<Clip>)event[0]);
            playbackPane.start();
        });

        eventBus.subscribe("playback-clips-stop", event -> {
            playbackPane.stop();
            browsePane.setDisable(false);
            renderPane.setDisable(false);
            playbackPane.setVisible(false);
        });

        eventBus.subscribe("capture-session-started", event -> {
            recordingPane.setVisible(true);
            recordingPane.start();
        });

        eventBus.subscribe("capture-session-stopped", event -> {
            recordingPane.setVisible(false);
            recordingPane.stop();
        });
    }

    private void handleHideControls(FadeTransition transition, Boolean hide) {
        if (hide) {
            fadeOut(transition, x -> {});
        } else {
            fadeIn(transition, x -> {});
        }
    }

    private FadeTransition createFadeTransition(Node node) {
        FadeTransition transition = new FadeTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(0.5));
        return transition;
    }

    private void fadeOut(FadeTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getOpacity() != 0) {
            transition.setFromValue(transition.getNode().getOpacity());
            transition.setToValue(0);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private void fadeIn(FadeTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getOpacity() != 0.9) {
            transition.setFromValue(transition.getNode().getOpacity());
            transition.setToValue(0.9);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private void showBrowser(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateY() != 0) {
            transition.setFromY(transition.getNode().getTranslateY());
            transition.setToY(0);
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private void hideBrowser(TranslateTransition transition, EventHandler<ActionEvent> handler) {
        transition.stop();
        if (transition.getNode().getTranslateY() != -((Pane)transition.getNode()).getHeight()) {
            transition.setFromY(transition.getNode().getTranslateY());
            transition.setToY(-((Pane)transition.getNode()).getHeight());
            transition.setOnFinished(handler);
            transition.play();
        }
    }

    private TranslateTransition createTranslateTransition(Node node) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(1.0));
        transition.setInterpolator(Interpolator.EASE_BOTH);
//        transition.setInterpolator(new BounceInterpolator());
        return transition;
    }

    private class BounceInterpolator extends Interpolator {
        @Override
        protected double curve(double t) {
            double freq = 2;
            double decay = 3;
            double dur = 0.4;
            if(t < dur){
                return t;
            }else{
                double amp = 1/dur;
                double w = freq*Math.PI*2;
                return 1 + amp*(Math.sin(t*w)/Math.exp(decay*t)/w);
            }
        }
    }
}
