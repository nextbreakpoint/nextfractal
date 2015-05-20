/*
 * NextFractal 1.0.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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

import java.awt.Desktop;
import java.net.URI;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.session.SessionListener;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportRenderer;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportService;


public class NextFractalApp extends Application {
	private static Logger logger = Logger.getLogger(NextFractalApp.class.getName());
	private BorderPane editorRootPane;
	private BorderPane renderRootPane;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		int width = 600;
		int height = 600;
		int editorWidth = 424;
        String pluginId = "Mandelbrot";
        primaryStage.setTitle("NextFractal 1.0.4");
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
        editorRootPane.getStyleClass().add("editor");
        renderRootPane = new BorderPane();
        renderRootPane.setPrefWidth(width);
        renderRootPane.setPrefHeight(height);
        renderRootPane.setMinWidth(width);
        renderRootPane.setMinHeight(height);
        renderRootPane.setMaxWidth(width);
        renderRootPane.setMaxHeight(height);
        renderRootPane.getStyleClass().add("render");
        mainPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(editorRootPane);
        mainPane.getStyleClass().add("application");
        root.getChildren().add(mainPane);
		DefaultThreadFactory renderThreadFactory = new DefaultThreadFactory("NextFractal Render", true, Thread.MIN_PRIORITY);
		DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("NextFractal Export", true, Thread.MIN_PRIORITY);
		JavaFXRendererFactory renderFactory = new JavaFXRendererFactory();
		ExportRenderer exportRenderer = new SimpleExportRenderer(renderThreadFactory, renderFactory);
        ExportService exportService = new SimpleExportService(exportThreadFactory, exportRenderer);
        printPlugins();
        Session session = createFractalSession(pluginId);
        if (session != null) {
        	session.setExportService(exportService);
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
//        quitItem.setOnAction(e -> {
//			 primaryStage.close();
//		});
//		fileMenu.getItems().add(quitItem);
//		menuBar.getMenus().add(fileMenu);
        final Menu helpMenu = new Menu("Help");
		if (Desktop.isDesktopSupported()) {
			MenuItem siteItem = new MenuItem("User Guide");
			helpMenu.getItems().add(siteItem);
			siteItem.setOnAction(e -> {
				 try {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(new URI("http://nextfractal.nextbreakpoint.com"));
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
        final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		plugins.forEach(factory -> {
			try {
				scene.getStylesheets().add(getClass().getResource("/" + factory.getId().toLowerCase() + ".css").toExternalForm());
			} catch (Throwable e) {
				//TODO log errors
			}
		});
		
		primaryStage.setScene(scene);
        primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if (session != null) {
					session.terminate();
				}
			}
		});
//		quitItem.setOnAction(e -> {
//			primaryStage.close();
//		});
		if (session != null) {
			session.addSessionListener(new SessionListener() {
				@Override
				public void terminate(Session session) {
					exportService.shutdown();
				}
				
				@Override
				public void sessionAdded(Session session, ExportSession exportSession) {
				}
				
				@Override
				public void sessionRemoved(Session session, ExportSession exportSession) {
				}
			});
		}
    }

//	private void setup() {
//		try {
//			addLibraryPath("lib");
//		} catch (Exception x) {
//			x.printStackTrace();
//		}
////    	System.setProperty("java.library.path", "lib");
////    	try {
////			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
////			fieldSysPath.setAccessible(true);
////			fieldSysPath.set(null, null);
////    	} catch (Exception e) {
////    	}
//	}
	
//	private static void addLibraryPath(String pathToAdd) throws Exception {
//		Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
//		usrPathsField.setAccessible(true);
//		String[] paths = (String[]) usrPathsField.get(null);
//		for (String path : paths)
//			if (path.equals(pathToAdd))
//				return;
//		String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
//		newPaths[newPaths.length - 1] = pathToAdd;
//		usrPathsField.set(null, newPaths);
//	}

	private Pane createEditorPane(Session session, String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createEditorPane(session);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Cannot create editor panel with pluginId = " + pluginId, e);
			}
		}
		return null;
	}

	private Pane createRenderPane(Session session, String pluginId, int width, int height) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createRenderPane(session, width, height);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Cannot create renderer panel with pluginId = " + pluginId, e);
			}
		}
		return null;
	}

	private Session createFractalSession(String pluginId) {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		for (FractalFactory plugin : plugins) {
			try {
				if (pluginId.equals(plugin.getId())) {
					return plugin.createSession();
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "Cannot create session with pluginId = " + pluginId, e);
			}
		}
		return null;
	}

	private void printPlugins() {
		final ServiceLoader<? extends FractalFactory> plugins = ServiceLoader.load(FractalFactory.class);
		plugins.forEach(plugin -> {
			logger.fine("Found plugin " + plugin.getId());
		});
	}
}
