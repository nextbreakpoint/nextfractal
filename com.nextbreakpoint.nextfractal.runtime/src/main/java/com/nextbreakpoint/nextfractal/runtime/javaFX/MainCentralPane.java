package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.javaFX.Bitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseBitmap;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowseDelegate;
import com.nextbreakpoint.nextfractal.core.javaFX.BrowsePane;
import com.nextbreakpoint.nextfractal.core.javaFX.GridItemRenderer;
import com.nextbreakpoint.nextfractal.core.javaFX.TabPane;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;

public class MainCentralPane extends BorderPane {
    private static Logger logger = Logger.getLogger(MainCentralPane.class.getName());

    private ExecutorService watcherExecutor;

    public MainCentralPane(EventBus eventBus, int width, int height) {
        MainRenderPane renderPane = new MainRenderPane(eventBus, width, height);

        TabPane tab = new TabPane(createIconImage("/icon-grid.png"));

        BrowsePane browsePane = new BrowsePane(width, height);
        browsePane.setClip(new Rectangle(0, 0, width, height));

        FadeTransition toolsTransition = createFadeTransition(tab);

        this.setOnMouseEntered(e -> fadeIn(toolsTransition, x -> {}));

        this.setOnMouseExited(e -> fadeOut(toolsTransition, x -> {}));

        FadeTransition tabTransition = createFadeTransition(tab);

        TranslateTransition browserTransition = createTranslateTransition(browsePane);

        tab.setOnAction(e -> {
			showBrowser(browserTransition, a -> {});
			browsePane.reload();
        });

		browsePane.setDelegate(new BrowseDelegate() {
			@Override
			public void didSelectFile(BrowsePane source, File file) {
				eventBus.postEvent("editor-load-file", file);
				hideBrowser(browserTransition, a -> {});
			}

			@Override
			public void didClose(BrowsePane source) {
				hideBrowser(browserTransition, a -> {});
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
        stackPane.getChildren().add(tab);
        stackPane.getChildren().add(browsePane);

        setCenter(stackPane);

        browsePane.setTranslateY(-height);

        widthProperty().addListener((observable, oldValue, newValue) -> {
            tab.setPrefWidth(newValue.doubleValue() * 0.1);
            renderPane.setPrefWidth(newValue.doubleValue());
            browsePane.setPrefWidth(newValue.doubleValue());
            tab.setTranslateX((newValue.doubleValue() - newValue.doubleValue() * 0.1) / 2);
        });

        heightProperty().addListener((observable, oldValue, newValue) -> {
            tab.setPrefHeight(newValue.doubleValue() * 0.05);
            renderPane.setPrefHeight(newValue.doubleValue());
            browsePane.setPrefHeight(newValue.doubleValue());
        });

        eventBus.subscribe("hide-controls", event -> handleHideControls(tabTransition, (Boolean)event));
//        watcherExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("Watcher", true, Thread.MIN_PRIORITY));
    }

    private void handleHideControls(FadeTransition tab, Boolean hide) {
        if (hide) {
            fadeOut(tab, x -> {});
        } else {
            fadeIn(tab, x -> {});
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

    private ImageView createIconImage(String name, double percentage) {
        int size = (int)Math.rint(Screen.getPrimary().getVisualBounds().getWidth() * percentage);
        InputStream stream = getClass().getResourceAsStream(name);
        ImageView image = new ImageView(new Image(stream));
        image.setSmooth(true);
        image.setFitWidth(size);
        image.setFitHeight(size);
        return image;
    }

    private ImageView createIconImage(String name) {
        return createIconImage(name, 0.02);
    }

    private void watchFolder(BrowsePane pane, File file) {
        Future<?> future = watcherExecutor.submit(() -> Block.create(a -> watchLoop(pane, file.toPath()))
            .tryExecute().ifFailure(e -> logger.log(Level.WARNING, "Can't create watcher for location {}", file.getAbsolutePath())));
    }

    private void watchLoop(BrowsePane pane, Path dir) throws IOException {
        WatchService watcher = FileSystems.getDefault().newWatchService();

        WatchKey watchKey = null;

        try {
            watchKey = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            try {
                for (;;) {
                    WatchKey key = watcher.take();

                    for (WatchEvent<?> event: key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        WatchEvent<Path> ev = (WatchEvent<Path>)event;

                        Path filename = ev.context();

                        try {
                            Path child = dir.resolve(filename);
                            if (!Files.probeContentType(child).equals("text/plain")) {
                                logger.log(Level.WARNING, "New file {} is not a plain text file", filename);
                                continue;
                            }
                        } catch (IOException x) {
                            logger.log(Level.WARNING, "Can't resolve file {}", filename);
                            continue;
                        }

                        Platform.runLater(() -> pane.reload());
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (InterruptedException x) {
            }
        } catch (IOException x) {
            logger.log(Level.WARNING, "Can't subscribe watcher on directory {}", dir.getFileName());
        } finally {
            if (watchKey != null) {
                watchKey.cancel();
            }

            watcher.close();
        }
    }
}
