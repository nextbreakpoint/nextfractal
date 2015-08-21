/*
 * NextFractal 1.1.5
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

import java.io.InputStream;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

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
	
//	private static double getVersion() {
//		String version = System.getProperty("java.version");
//		int pos = version.indexOf('.');
//		pos = version.indexOf('.', pos + 1);
//		return Double.parseDouble(version.substring(0, pos));
//	}
	
    @Override
    public void start(Stage primaryStage) {
//		if (getVersion() < 1.8) {
//			ButtonType exitButtonType = new ButtonType("Exit", ButtonData.FINISH);
//			Dialog<String> dialog = new Dialog<>();
//			dialog.getDialogPane().getButtonTypes().add(exitButtonType);
//			dialog.setGraphic(createIconImage("/icon-errors.png"));
//			dialog.setTitle("Error");
//			dialog.setHeaderText("NextFractal requires Java JDK 8 or later");
//			dialog.setContentText("Please install Java JDK 8 or later and add the command your_jdk_path/bin/java to your system's path variable.");
//			dialog.showAndWait();
//			System.exit(1);
//			return;
//		}

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			ButtonType exitButtonType = new ButtonType("Continue", ButtonData.OK_DONE);
			Dialog<String> dialog = new Dialog<>();
			dialog.getDialogPane().getButtonTypes().add(exitButtonType);
			dialog.setGraphic(createIconImage("/icon-errors.png"));
			dialog.setTitle("Warning");
			dialog.setHeaderText("Cannot find Java compiler in your classpath");
			dialog.setContentText("Java compiler is required to reduce computation time. Please install Java JDK 8 (>= 1.8.0_40) or later and add directory your_jdk_path/bin to your system's path.");
			dialog.showAndWait();
		}
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		logger.info("DPI = " + Screen.getPrimary().getDpi());
		logger.info("Screen Size = (" + primaryScreenBounds.getWidth() + "," + primaryScreenBounds.getHeight() + ")");
		double baseWidth = Math.min(primaryScreenBounds.getWidth() * 0.75, 1400);
		int editorWidth = (int)Math.rint(baseWidth * 0.4);
		int renderWidth = (int)Math.rint(baseWidth) - editorWidth;
		int width = renderWidth + editorWidth;
		int height = renderWidth;
		logger.info("Window Size = (" + width + "," + height + ")");
		
        String pluginId = "Mandelbrot";
        StackPane root = new StackPane();
        Pane mainPane = new Pane();
        mainPane.setPrefWidth(width);
        mainPane.setPrefHeight(height);
        mainPane.setMinWidth(width);
        mainPane.setMinHeight(height);
        mainPane.setMaxWidth(width);
        mainPane.setMaxHeight(height);
        editorRootPane = new BorderPane();
		editorRootPane.setPrefWidth(editorWidth);
        editorRootPane.setPrefHeight(height);
        editorRootPane.setLayoutX(renderWidth);
        editorRootPane.getStyleClass().add("editor");
        renderRootPane = new BorderPane();
        renderRootPane.setPrefWidth(renderWidth);
        renderRootPane.setPrefHeight(height);
        renderRootPane.setMinWidth(renderWidth);
        renderRootPane.setMinHeight(height);
        renderRootPane.setMaxWidth(renderWidth);
        renderRootPane.setMaxHeight(height);
        renderRootPane.getStyleClass().add("render");
        mainPane.getChildren().add(renderRootPane);
        mainPane.getChildren().add(editorRootPane);
        mainPane.getStyleClass().add("application");
        root.getChildren().add(mainPane);
		DefaultThreadFactory renderThreadFactory = new DefaultThreadFactory("NextFractalRender", true, Thread.MIN_PRIORITY);
		DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("NextFractalExport", true, Thread.MIN_PRIORITY);
		JavaFXRendererFactory renderFactory = new JavaFXRendererFactory();
		ExportRenderer exportRenderer = new SimpleExportRenderer(renderThreadFactory, renderFactory);
        ExportService exportService = new SimpleExportService(exportThreadFactory, exportRenderer);
        printPlugins();
        
        Session session = createFractalSession(pluginId);
        if (session != null) {
        	session.setExportService(exportService);
        	Pane renderPane = createRenderPane(session, pluginId, renderWidth, height);
        	if (renderPane != null) {
        		renderRootPane.setCenter(renderPane);
        	}
        	Pane editorPane = createEditorPane(session, pluginId);
        	if (editorPane != null) {
        		editorRootPane.setCenter(editorPane);
        	}
        }

        Scene scene = new Scene(root, width, height);
        
//        MenuBar menuBar = new MenuBar();
////        final Menu fileMenu = new Menu("File");
////        MenuItem quitItem = new MenuItem("Quit");
////        quitItem.setOnAction(e -> {
////			 primaryStage.close();
////		});
////		fileMenu.getItems().add(quitItem);
////		menuBar.getMenus().add(fileMenu);
//        final Menu helpMenu = new Menu("Help");
//		if (Desktop.isDesktopSupported()) {
//			MenuItem siteItem = new MenuItem("User Guide");
//			helpMenu.getItems().add(siteItem);
//			siteItem.setOnAction(e -> {
//				 try {
//					if (Desktop.isDesktopSupported()) {
//						Desktop.getDesktop().browse(new URI("http://nextfractal.nextbreakpoint.com"));
//					}
//				} catch (Exception e1) {
//				}
//			});
//		}
//		menuBar.getMenus().add(helpMenu);
//        menuBar.setUseSystemMenuBar(true);
//        root.getChildren().add(menuBar);

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
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("NextFractal 1.1.5");
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

				@Override
				public void doExportAsImage(Session session) {
				}

				@Override
				public void showBrowser(Session session) {
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

	private ImageView createIconImage(String name) {
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(32);
		image.setFitHeight(32);
		return image;
	}
}
