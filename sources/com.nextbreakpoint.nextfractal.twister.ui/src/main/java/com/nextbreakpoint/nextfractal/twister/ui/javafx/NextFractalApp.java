package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.aquafx_project.AquaFx;
import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.TwisterSessionController;
import com.nextbreakpoint.nextfractal.twister.image.ImageConfigElement;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.javaFX.JavaFXRenderFactory;
import com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.ViewExtensionRuntime;

public class NextFractalApp extends Application {
	private TwisterRenderer renderer;
	private TwisterRuntime runtime;
	private TwisterConfig config;
//	private InputAdapter adapter;
	private AnimationTimer timer;
	private IntegerVector2D size;
	private final List<Runnable>[] commands = new LinkedList[2];
	private final Semaphore semaphore = new Semaphore(1, true);
	private final List<RenderContextListener> contextListeners = new LinkedList<RenderContextListener>();
	private final JavaFXRenderFactory renderFactory = new JavaFXRenderFactory();
	private final Pane configViewPane = new Pane();
	private final Pane editorViewPane = new Pane();
	private final Button close = new Button("<");
	private TwisterSessionController sessionController;

    @Override
    public void start(Stage primaryStage) {
		commands[0] = new LinkedList<Runnable>();
		commands[1] = new LinkedList<Runnable>();
		int width = 800;
		int height = 500;
		int configPaneWidth = 300;
        primaryStage.setTitle("NextFractal");
        primaryStage.setResizable(false);
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        VBox configPane = new VBox(10);
		configPane.setPrefWidth(configPaneWidth);
        configPane.setPrefHeight(height);
        configPane.setOpacity(0.7);
        configPane.setLayoutX(width - configPaneWidth);
        configPane.setId("config-panel");
        StackPane editorPane = new StackPane();
        editorPane.setPrefWidth(width - configPaneWidth);
        editorPane.setPrefHeight(height);
        configViewPane.setPrefWidth(configPaneWidth);
        configViewPane.setPrefHeight(height - 40);
        editorViewPane.setPrefWidth(width - configPaneWidth);
        editorViewPane.setPrefHeight(height);
        close.setVisible(false);
        Canvas canvas = new Canvas(width - configPaneWidth, height);
        configPane.getChildren().add(close);
        configPane.getChildren().add(configViewPane);
        editorPane.getChildren().add(canvas);
        editorPane.getChildren().add(editorViewPane);
        editorPane.setId("editor-panel");
        mainPane.getChildren().add(editorPane);
        mainPane.getChildren().add(configPane);
        root.getChildren().add(mainPane);
        Scene scene = new Scene(root);
        AquaFx.style();
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());
		primaryStage.setScene(scene);
        primaryStage.show();
		execute(width - configPaneWidth, height, canvas);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				dispose();
			}
		});
    }

	private void execute(int width, int height, Canvas canvas) {
		try {
			IntegerVector2D size = new IntegerVector2D(width, height);
			TwisterConfig config = createConfig();
			config.setContext(new DefaultConfigContext());
			init(config, size, 1, 1);
			ViewContext viewContext = new DefaultViewContext();
			RenderContext renderContext = new DefaultRenderContext();
			sessionController = new TwisterSessionController("JavaFX", config);
			sessionController.init();
			sessionController.setRenderContext(renderContext);
			Pane configView = createConfigView(viewContext, renderContext, config);
			if (configView != null) {
				viewContext.showConfigView(configView);
			}
			Pane editorView = createEditorView(viewContext, renderContext, config);
			if (editorView != null) {
				viewContext.showEditorView(editorView);
			}
	        close.setOnAction(e -> { viewContext.discardConfigView(); });
			startRenderers();
			runTimer(canvas);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		} catch (ExtensionException e) {
			e.printStackTrace();
		}
	}

	private void init(final TwisterConfig config, final IntegerVector2D size, final int nx, final int ny) {
		final int dx = size.getX() / nx;
		final int dy = size.getY() / ny;
		final HashMap<Object, Object> hints = new HashMap<Object, Object>();
		hints.put(TwisterRenderingHints.KEY_QUALITY, TwisterRenderingHints.QUALITY_REALTIME);
		if (Boolean.getBoolean("nextfractal.lowMemory")) {
			hints.put(TwisterRenderingHints.KEY_MEMORY, TwisterRenderingHints.MEMORY_LOW);
		}
		if (runtime != null) {
			runtime.dispose();
		}
		runtime = new TwisterRuntime(config);
		renderer = new DefaultTwisterRenderer(runtime);
		renderer.setRenderFactory(renderFactory);
		renderer.setRenderingHints(hints);
		int i = 0;
		int j = 0;
		renderer.setTile(new Tile(new IntegerVector2D(size.getX(), size.getY()), new IntegerVector2D(dx, dy), new IntegerVector2D(dx * i, dy * j), new IntegerVector2D(24, 24)));
//		adapter = new DefaultInputAdapter(new DefaultRenderContext(), config);
		this.size = size;
		this.config = config;
	}

	private void dispose() {
		if (timer != null) {
			timer.stop();
			timer = null;
		}
		if (renderer != null) {
			renderer.dispose();
		}
		renderer = null;
		if (runtime != null) {
			runtime.dispose();
		}
		sessionController = null;
		runtime = null;
		config = null;
		size = null;
	}

	private TwisterConfig createConfig() throws ExtensionNotFoundException, ExtensionException {
		final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
		return configBuilder.createDefaultConfig();
	}

	private Pane createConfigView(ViewContext viewContext, RenderContext renderContext, TwisterConfig config) {
		try {
			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
			final Extension<ViewExtensionRuntime> extension = TwisterUIRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
			return extension.createExtensionRuntime().createConfigView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
		}
		catch (final ExtensionException x) {
		}
		return null;
	}

	private Pane createEditorView(ViewContext viewContext, RenderContext renderContext, TwisterConfig config) {
		try {
			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
			final Extension<ViewExtensionRuntime> extension = TwisterUIRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
			return extension.createExtensionRuntime().createEditorView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
		}
		catch (final ExtensionException x) {
		}
		return null;
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 20 && runtime != null && runtime.isChanged()) {
					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					//runtime.getFrameElement().getLayer(0).getLayer(0).getImage().getImageRuntime().drawImage(gc);
					last = time;
				}
			}
		};
		timer.start();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
	 */
	public void acquire() throws InterruptedException {
		semaphore.acquire();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
	 */
	public void release() {
		semaphore.release();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	public void addRenderContextListener(RenderContextListener listener) {
		synchronized (contextListeners) {
			contextListeners.add(listener);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
	 */
	public void removeRenderContextListener(RenderContextListener listener) {
		synchronized (contextListeners) {
			contextListeners.remove(listener);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
	 */
	public void startRenderers() {
		synchronized (contextListeners) {
			if (renderer != null) {
				renderer.startRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.startRenderer();
			}
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
	 */
	public void stopRenderers() {
		synchronized (contextListeners) {
			if (renderer != null) {
				renderer.abortRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.abortRenderer();
			}
			if (renderer != null) {
				renderer.joinRenderer();
			}
			for (RenderContextListener listener : contextListeners) {
				listener.joinRenderer();
			}
		}
	}

	private class DefaultViewContext implements ViewContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#showConfigView(com.nextbreakpoint.nextfractal.core.ui.javafx.View)
		 */
		@Override
		public void showConfigView(Pane node) {
			close.setDisable(true);
			node.setLayoutY(configViewPane.getHeight());
			node.setPrefWidth(configViewPane.getWidth());
			node.setPrefHeight(configViewPane.getHeight());
			configViewPane.getChildren().add(node);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromY(0);
			tt.setToY(-configViewPane.getHeight());
			tt.setNode(node);
			tt.play();
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (configViewPane.getChildren().size() > 1) {
						close.setVisible(true);
					}
					close.setDisable(false);
				}
			});
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#discardConfigView()
		 */
		@Override
		public void discardConfigView() {
			if (configViewPane.getChildren().size() > 1) {
				close.setDisable(true);
				Node node = configViewPane.getChildren().get(configViewPane.getChildren().size() - 1);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
				tt.setFromY(-configViewPane.getHeight());
				tt.setToY(0);
				tt.setNode(node);
				tt.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						configViewPane.getChildren().remove(node);
						if (node instanceof Disposable) {
							((Disposable)node).dispose();
						}
						if (configViewPane.getChildren().size() <= 1) {
							close.setVisible(false);
						}
						close.setDisable(false);
					}
				});
				tt.play();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#showEditorView(com.nextbreakpoint.nextfractal.core.ui.javafx.View)
		 */
		@Override
		public void showEditorView(Pane node) {
			node.setPrefWidth(editorViewPane.getWidth());
			node.setPrefHeight(editorViewPane.getHeight());
			editorViewPane.getChildren().add(node);
			FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
			ft.setFromValue(0);
			ft.setToValue(1);
			ft.setNode(node);
			ft.play();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#discardEditorView()
		 */
		@Override
		public void discardEditorView() {
			if (editorViewPane.getChildren().size() > 1) {
				Node node = editorViewPane.getChildren().get(editorViewPane.getChildren().size() - 1);
				FadeTransition ft = new FadeTransition(Duration.seconds(0.4));
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setNode(node);
				ft.setOnFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						editorViewPane.getChildren().remove(node);
						if (node instanceof Disposable) {
							((Disposable)node).dispose();
						}
					}
				});
				ft.play();
			}
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#getConfigViewSize()
		 */
		@Override
		public Dimension2D getConfigViewSize() {
			return new Dimension2D(configViewPane.getWidth(), configViewPane.getHeight());
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext#getEditorViewSize()
		 */
		@Override
		public Dimension2D getEditorViewSize() {
			return new Dimension2D(editorViewPane.getWidth(), editorViewPane.getHeight());
		}
	}
	
	private class DefaultRenderContext implements RenderContext {
		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#startRenderers()
		 */
		@Override
		public void startRenderers() {
			NextFractalApp.this.startRenderers();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#stopRenderers()
		 */
		@Override
		public void stopRenderers() {
			NextFractalApp.this.stopRenderers();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#getImageSize()
		 */
		@Override
		public IntegerVector2D getImageSize() {
			return size;
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#refresh()
		 */
		@Override
		public void refresh() {
			//NextFractalApp.this.refresh();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#acquire()
		 */
		@Override
		public void acquire() throws InterruptedException {
			NextFractalApp.this.acquire();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#release()
		 */
		@Override
		public void release() {
			NextFractalApp.this.release();
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#addRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void addRenderContextListener(RenderContextListener listener) {
			NextFractalApp.this.addRenderContextListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#removeRenderContextListener(com.nextbreakpoint.nextfractal.core.util.RenderContextListener)
		 */
		@Override
		public void removeRenderContextListener(RenderContextListener listener) {
			NextFractalApp.this.removeRenderContextListener(listener);
		}

		/**
		 * @see com.nextbreakpoint.nextfractal.core.util.RenderContext#execute(java.lang.Runnable)
		 */
		@Override
		public void execute(Runnable task) {
			try {
				NextFractalApp.this.acquire();
				if (config != null) {
					config.getContext().updateTimestamp();
				}
				task.run();
				NextFractalApp.this.release();
			}
			catch (InterruptedException x) {
				Thread.currentThread().interrupt();
			}
		}
	}
}