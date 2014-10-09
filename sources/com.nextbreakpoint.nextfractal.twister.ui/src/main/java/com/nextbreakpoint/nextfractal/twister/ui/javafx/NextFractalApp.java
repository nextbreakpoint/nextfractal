package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.Extension;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
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
import com.nextbreakpoint.nextfractal.twister.ui.javafx.extensionPoints.view.DefaultViewRuntime;
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
	private final NextFractalAppContext appContext = new DefaultNextFractalAppContext();
	private final Pane configPane = new Pane();

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
        root.setStyle("-fx-background-color:#444444");
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
		configPane.setPrefWidth(configPaneWidth);
        configPane.setPrefHeight(height);
        configPane.setOpacity(0.7);
        configPane.setLayoutX(width - configPaneWidth);
        configPane.setStyle("-fx-background-color:#777777");
        Canvas canvas = new Canvas(width - configPaneWidth, height);
        mainPane.getChildren().add(canvas);
        mainPane.getChildren().add(configPane);
        root.getChildren().add(mainPane);
        primaryStage.setScene(new Scene(root));
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
			startRenderers();
			runTimer(canvas);
			Pane configNode = createConfigPanel(appContext, config);
			appContext.showConfigNode(configNode);
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		} catch (ExtensionException e) {
			e.printStackTrace();
		}
	}

	private Pane createConfigPanel(NextFractalAppContext appContext, TwisterConfig config) {
		VBox configNode = new VBox(10);
		configNode.setStyle("-fx-background-color:#aaaaaa;-fx-padding:10px");
		Text textNode = new Text("Configuration");
		Button buttonNode = new Button("Edit");
		ComboBox<String> comboboxNode = new ComboBox<>();
		comboboxNode.getItems().add("A");
		comboboxNode.getItems().add("B");
		comboboxNode.getItems().add("C");
		CheckBox checkboxNode = new CheckBox();
		TextField textFieldNode = new TextField();
		configNode.getChildren().add(textNode);
		configNode.getChildren().add(buttonNode);
		configNode.getChildren().add(comboboxNode);
		configNode.getChildren().add(checkboxNode);
		configNode.getChildren().add(textFieldNode);
		buttonNode.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				appContext.discardConfigNode();
			}
		});
		RenderContext renderContext = new DefaultRenderContext();
		ViewContext viewContext = new DefaultViewContext(appContext);
		TwisterSessionController sessionController = new TwisterSessionController("JavaFX", config);
		sessionController.init();
		sessionController.setRenderContext(renderContext);
		Node configView;
		try {
			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
			final Extension<ViewExtensionRuntime> extension = TwisterUIRegistry.getInstance().getViewExtension(imageElement.getReference().getExtensionId());
			configView = extension.createExtensionRuntime().createView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
		}
		catch (final ExtensionException x) {
			ImageConfigElement imageElement = config.getFrameConfigElement().getLayerConfigElement(0).getLayerConfigElement(0).getImageConfigElement();
			configView = new DefaultViewRuntime().createView(imageElement.getReference().getExtensionConfig(), viewContext, renderContext, sessionController);
		}
		if (configView != null) {
			configNode.getChildren().add(configView);
		}
		return configNode;
	}

	private void runTimer(Canvas canvas) {
		timer = new AnimationTimer() {
			private long last;

			@Override
			public void handle(long now) {
				long time = now / 1000000;
				if ((time - last) > 20 && runtime != null && runtime.isChanged()) {
					RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
					runtime.getFrameElement().getLayer(0).getLayer(0).getImage().getImageRuntime().drawImage(gc);
					last = time;
				}
			}
		};
		timer.start();
	}

	private TwisterConfig createConfig() throws ExtensionNotFoundException, ExtensionException {
		final TwisterConfigBuilder configBuilder = new TwisterConfigBuilder();
		return configBuilder.createDefaultConfig();
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
		runtime = null;
		config = null;
		size = null;
	}

	private class DefaultViewContext implements ViewContext {
		private final NextFractalAppContext context;

		/**
		 * @param context
		 */
		public DefaultViewContext(final NextFractalAppContext context) {
			this.context = context;
		}

		@Override
		public void showConfigView(Node c) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void discardConfigView() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void showEditorView(Node c) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void discardEditorView() {
			// TODO Auto-generated method stub
			
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
	}

	private class DefaultNextFractalAppContext implements NextFractalAppContext {
		@Override
		public void showConfigNode(Pane node) {
			node.setLayoutX(configPane.getWidth());
			node.setPrefWidth(configPane.getWidth());
			node.setPrefHeight(configPane.getHeight());
			configPane.getChildren().add(node);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromX(0);
			tt.setToX(-configPane.getWidth());
			tt.setNode(node);
			tt.play();
		}

		@Override
		public void discardConfigNode() {
			Node node = configPane.getChildren().get(configPane.getChildren().size() - 1);
			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
			tt.setFromX(-configPane.getWidth());
			tt.setToX(0);
			tt.setNode(node);
			tt.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					configPane.getChildren().remove(node);
				}
			});
			tt.play();
		}

		@Override
		public void showEditorNode(Pane node) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void discardEditorNode() {
			// TODO Auto-generated method stub
			
		}
	}
}