package com.nextbreakpoint.nextfractal.javaFX;

import java.awt.Desktop;
import java.net.URI;
import java.util.ServiceLoader;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.nextbreakpoint.nextfractal.ExportService;
import com.nextbreakpoint.nextfractal.ExportSession;
import com.nextbreakpoint.nextfractal.FractalFactory;
import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.RenderService;
import com.nextbreakpoint.nextfractal.SessionListener;
import com.nextbreakpoint.nextfractal.SimpleRenderService;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;


public class NextFractalApp extends Application {
	private BorderPane editorRootPane;
	private BorderPane renderRootPane;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		int width = 500;
		int height = 500;
		int editorWidth = 300;
        String pluginId = "Mandelbrot";
        primaryStage.setTitle("NextFractal");
        primaryStage.setResizable(false);
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width + editorWidth);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width + editorWidth);
        mainPane.setMinHeight(height);
        editorRootPane = new BorderPane();
		editorRootPane.setPrefWidth(editorWidth);
        editorRootPane.setPrefHeight(height);
        editorRootPane.setLayoutX(width);
        editorRootPane.getStyleClass().add("editor-pane");
        renderRootPane = new BorderPane();
        renderRootPane.setPrefWidth(width);
        renderRootPane.setPrefHeight(height);
        renderRootPane.setMinWidth(width);
        renderRootPane.setMinHeight(height);
        renderRootPane.setMaxWidth(width);
        renderRootPane.setMaxHeight(height);
        renderRootPane.getStyleClass().add("render-pane");
        mainPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(editorRootPane);
        mainPane.getStyleClass().add("application");
        root.getChildren().add(mainPane);
		DefaultThreadFactory renderThreadFactory = new DefaultThreadFactory("NextFractalApp", true, Thread.MIN_PRIORITY + 1);
		DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("NextFractalApp", true, Thread.MIN_PRIORITY + 0);
		JavaFXRenderFactory renderFactory = new JavaFXRenderFactory();
		RenderService renderService = new SimpleRenderService(renderThreadFactory, renderFactory);
        ExportService exportService = new ExportService(exportThreadFactory, renderService, 200);
        FractalSession session = createFractalSession(pluginId);
        session.setExportService(exportService);
        if (session != null) {
        	Pane renderPane = createRenderPane(session, pluginId, width, height);
        	if (renderPane != null) {
        		renderRootPane.setCenter(renderPane);
        	}
        	Pane editorPane = createEditorPane(session, pluginId);
        	if (editorPane != null) {
        		editorRootPane.setCenter(editorPane);
        	}
        }
        MenuBar menuBar = new MenuBar();
//        final Menu fileMenu = new Menu("File");
//        MenuItem quitItem = new MenuItem("Quit");
//		fileMenu.getItems().add(quitItem);
//		menuBar.getMenus().add(fileMenu);
        final Menu helpMenu = new Menu("Help");
		if (Desktop.isDesktopSupported()) {
			MenuItem siteItem = new MenuItem("Online Manual");
			helpMenu.getItems().add(siteItem);
			siteItem.setOnAction(e -> {
				 try {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(new URI("http://nextbreakpoint.com"));
					}
				} catch (Exception e1) {
				}
			});
		}
		menuBar.getMenus().add(helpMenu);
        menuBar.setUseSystemMenuBar(true);
        Scene scene = new Scene(root);
        root.getChildren().add(menuBar);
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());
		primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("NextFractal");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				session.terminate();
			}
		});
//		quitItem.setOnAction(e -> {
//			primaryStage.close();
//		});
		session.addSessionListener(new SessionListener() {
			@Override
			public void viewChanged(FractalSession session, boolean zoom) {
			}
			
			@Override
			public void pointChanged(FractalSession session) {
			}

			@Override
			public void dataChanged(FractalSession session) {
			}
			
			@Override
			public void terminate(FractalSession session) {
				exportService.shutdown();
			}
			
			@Override
			public void sessionAdded(FractalSession session, ExportSession exportSession) {
			}
			
			@Override
			public void sessionRemoved(FractalSession session, ExportSession exportSession) {
			}
		});
    }

	private Pane createEditorPane(FractalSession session, String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createEditorPane(session);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private Pane createRenderPane(FractalSession session, String pluginId, int width, int height) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createRenderPane(session, width, height);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private FractalSession createFractalSession(String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createSession();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}
