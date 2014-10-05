package com.nextbreakpoint.nextfractal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.nextbreakpoint.nextfractal.core.config.DefaultConfigContext;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionNotFoundException;
import com.nextbreakpoint.nextfractal.core.util.IntegerVector2D;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContextListener;
import com.nextbreakpoint.nextfractal.core.util.Tile;
import com.nextbreakpoint.nextfractal.twister.TwisterClip;
import com.nextbreakpoint.nextfractal.twister.TwisterConfig;
import com.nextbreakpoint.nextfractal.twister.TwisterConfigBuilder;
import com.nextbreakpoint.nextfractal.twister.TwisterRuntime;
import com.nextbreakpoint.nextfractal.twister.renderer.DefaultTwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderer;
import com.nextbreakpoint.nextfractal.twister.renderer.TwisterRenderingHints;
import com.nextbreakpoint.nextfractal.twister.renderer.javaFX.JavaFXRenderFactory;
import com.nextbreakpoint.nextfractal.twister.swing.DefaultInputAdapter;
import com.nextbreakpoint.nextfractal.twister.swing.InputAdapter;

public class NextFractalApp extends Application implements NextFractalAppContext {
	private TwisterRenderer renderer;
	private TwisterRuntime runtime;
	private TwisterConfig config;
	private TwisterClip clip;
	private InputAdapter adapter;
	private IntegerVector2D size;
	private int hcells;
	private int vcells;
	private final Semaphore semaphore = new Semaphore(1, true);
	private final List<Runnable>[] commands = new LinkedList[2];
	private final List<RenderContextListener> contextListeners = new LinkedList<RenderContextListener>();
	private final JavaFXRenderFactory renderFactory = new JavaFXRenderFactory();

	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
		commands[0] = new LinkedList<Runnable>();
		commands[1] = new LinkedList<Runnable>();
		int width = 400;
		int height = 400;
        primaryStage.setTitle("NextFractal");
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color:#444444");
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        GridPane configPane = new GridPane();
        configPane.setPrefWidth(300.0);
        configPane.setOpacity(0.7);
        configPane.setStyle("-fx-background-color:#777777");
        Canvas canvas = new Canvas(width, height);
        mainPane.setCenter(canvas);
        root.getChildren().add(mainPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
		try {
			IntegerVector2D size = new IntegerVector2D(width, height);
			TwisterConfig config = createConfig();
			config.setContext(new DefaultConfigContext());
			init(config, size, 1, 1);
			startRenderers();
		} catch (ExtensionNotFoundException e) {
			e.printStackTrace();
		} catch (ExtensionException e) {
			e.printStackTrace();
		}

		RenderGraphicsContext gc = renderFactory.createGraphicsContext(canvas.getGraphicsContext2D());
		
		AnimationTimer timer = new AnimationTimer() {
			private long last;
			
			@Override
			public void handle(long now) {
//				for (int i = 0, y = 0; y < height; y++) {
//					for (int x = 0; x < width; x++) {
////						int p = ((int)Math.rint(Math.random() * 255) & 0xFF);
//						int p = ((((Math.rint(x / 50) % 2) == 0 ? 0 : 1) ^ ((Math.rint(y / 50) % 2) == 0 ? 0 : 1)) * 255);
//						((DataBufferInt)surface.getImage().getRaster().getDataBuffer()).getData()[i + x] = 0xFF000000 | (p << 16) | (p << 8) | p;
//					}
//					i += width;
//				}
				long time = now / 1000000;
				if ((time - last) > 20 && runtime != null && runtime.isChanged()) {
					runtime.getFrameElement().getLayer(0).getLayer(0).getImage().getImageRuntime().drawImage(gc);
//					canvas.getGraphicsContext2D().getPixelWriter().setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), ((DataBufferInt)surface.getImage().getRaster().getDataBuffer()).getData(), 0, width);
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
		adapter = new DefaultInputAdapter(new DefaultRenderContext(), config);
		this.size = size;
		this.config = config;
	}

	private void dispose() {
		if (renderer != null) {
			renderer.dispose();
		}
		renderer = null;
		if (runtime != null) {
			runtime.dispose();
		}
		runtime = null;
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
}