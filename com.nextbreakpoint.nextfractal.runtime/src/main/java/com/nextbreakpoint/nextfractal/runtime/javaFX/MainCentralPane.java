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

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.javaFX.Bitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BooleanObservableValue;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowsePane;
import com.nextbreakpoint.nextfractal.core.javaFX.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.Session;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class MainCentralPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainCentralPane.class.getName());

    public MainCentralPane(EventBus eventBus, int width, int height) {
        MainRenderPane renderPane = new MainRenderPane(eventBus, width, height);

        BooleanObservableValue toggleProperty = new BooleanObservableValue();
        toggleProperty.setValue(false);

//        TabPane tab = new TabPane(createIconImage("/icon-grid.png"));

        BrowsePane browsePane = new BrowsePane(width, height);
        browsePane.setClip(new Rectangle(0, 0, width, height));

//        FadeTransition tabTransition = createFadeTransition(tab);

//        this.setOnMouseEntered(e -> fadeIn(tabTransition, x -> {}));

//        this.setOnMouseExited(e -> fadeOut(tabTransition, x -> {}));

        TranslateTransition browserTransition = createTranslateTransition(browsePane);

//        tab.setOnAction(e -> eventBus.postEvent("toggle-browser", ""));

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
				return FileManager.loadFile(file).flatMap(session -> tryFindFactory(session.getPluginId())
					.flatMap(factory -> Try.of(() -> factory.createBitmap(session, size)))).orThrow();
			}

			@Override
			public String getFileExtension() {
				return ".nf.zip";
			}
		});

        Pane stackPane = new Pane();
        stackPane.getChildren().add(renderPane);
//        stackPane.getChildren().add(tab);
        stackPane.getChildren().add(browsePane);

        setCenter(stackPane);

        browsePane.setTranslateY(-height);

        widthProperty().addListener((observable, oldValue, newValue) -> {
//            tab.setPrefWidth(newValue.doubleValue() * 0.1);
            renderPane.setPrefWidth(newValue.doubleValue());
            browsePane.setPrefWidth(newValue.doubleValue());
//            tab.setTranslateX((newValue.doubleValue() - newValue.doubleValue() * 0.1) / 2);
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
//            tab.setPrefHeight(newValue.doubleValue() * 0.05);
            renderPane.setPrefHeight(newValue.doubleValue());
            browsePane.setPrefHeight(newValue.doubleValue());
        });

//        eventBus.subscribe("hide-controls", event -> handleHideControls(tabTransition, (Boolean)event));

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
        transition.setDuration(Duration.seconds(0.5));
        return transition;
    }
}
