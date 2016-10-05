/*
 * NextFractal 1.2.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.nextbreakpoint.Try;
import javafx.application.Application;
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

import static com.nextbreakpoint.nextfractal.runtime.Plugins.pluginsStream;
import static com.nextbreakpoint.nextfractal.runtime.Plugins.selectPlugin;

public class NextFractalApp extends Application {
	private static Logger logger = Logger.getLogger(NextFractalApp.class.getName());

	private BorderPane editorRootPane;
	private BorderPane renderRootPane;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		checkJavaVersion();

		Try.of(() -> Objects.requireNonNull(ToolProvider.getSystemJavaCompiler())).ifFailure(e -> showCompilerAlert());

		logger.info(getNoticeMessage());

		logger.info("DPI = " + Screen.getPrimary().getDpi());

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		logger.info("Screen Size = (" + primaryScreenBounds.getWidth() + "," + primaryScreenBounds.getHeight() + ")");

		double baseWidth = Math.min(primaryScreenBounds.getWidth() * 0.75, 1400);
		int editorWidth = (int)Math.rint(baseWidth * 0.5);
		int renderWidth = (int)Math.rint(baseWidth) - editorWidth;
		int sceneWidth = renderWidth + editorWidth;
		int sceneHeight = renderWidth;

		logger.info("Scene Size = (" + sceneWidth + "," + sceneHeight + ")");
		
        String initialPluginId = "Mandelbrot";

        StackPane rootPane = new StackPane();

		rootPane.getChildren().add(createMainPane(editorWidth, renderWidth, sceneHeight));

		ExportRenderer exportRenderer = new SimpleExportRenderer(createDefaultThreadFactory("NextFractalRender"), new JavaFXRendererFactory());

		ExportService exportService = new SimpleExportService(createDefaultThreadFactory("NextFractalExport"), exportRenderer);

		printPlugins();

		Try<Session, Exception> maybeSession = createSession(initialPluginId).onSuccess(this::sessionCreated).execute();

		maybeSession.ifPresent(session -> session.setExportService(exportService));

		maybeSession.flatMap(session -> createRenderPane(session, initialPluginId, renderWidth, sceneHeight)).ifPresent(renderRootPane::setCenter);

		maybeSession.flatMap(session -> createEditorPane(session, initialPluginId)).ifPresent(editorRootPane::setCenter);

        Scene scene = new Scene(rootPane, sceneWidth, sceneHeight);

		createMenuBar();

		loadStyleSheets(scene);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle(getApplicationName());
        primaryStage.show();
		primaryStage.setOnCloseRequest(e -> maybeSession.ifPresent(Session::terminate));

		maybeSession.ifPresent(session -> session.addSessionListener(new DefaultSessionListener(exportService)));
    }

	private DefaultThreadFactory createDefaultThreadFactory(String name) {
		return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
	}

//	private static double getVersion() {
//		String version = System.getProperty("java.version");
//		int pos = version.indexOf('.');
//		pos = version.indexOf('.', pos + 1);
//		return Double.parseDouble(version.substring(0, pos));
//	}

	private void checkJavaVersion() {
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
	}

	private void createMenuBar() {
		//        MenuBar menuBar = new MenuBar();
////        final Menu fileMenu = new Menu("File");
////        MenuItem quitItem = new MenuItem("Quit");
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
//		quitItem.setOnAction(e -> primaryStage.close());
	}

	private Pane createMainPane(int editorWidth, int renderWidth, int height) {
		int width = renderWidth + editorWidth;
		Pane mainPane = new Pane();
		mainPane.setPrefWidth(width);
		mainPane.setPrefHeight(height);
		mainPane.setMinWidth(width);
		mainPane.setMinHeight(height);
		mainPane.setMaxWidth(width);
		mainPane.setMaxHeight(height);
		createEditorPane(editorWidth, renderWidth, height);
		createRenderPane(renderWidth, height);
		mainPane.getChildren().add(renderRootPane);
		mainPane.getChildren().add(editorRootPane);
		mainPane.getStyleClass().add("application");
		return mainPane;
	}

	private void createRenderPane(int renderWidth, int height) {
		renderRootPane = new BorderPane();
		renderRootPane.setPrefWidth(renderWidth);
		renderRootPane.setPrefHeight(height);
		renderRootPane.setMinWidth(renderWidth);
		renderRootPane.setMinHeight(height);
		renderRootPane.setMaxWidth(renderWidth);
		renderRootPane.setMaxHeight(height);
		renderRootPane.getStyleClass().add("render");
	}

	private void createEditorPane(int editorWidth, int renderWidth, int height) {
		editorRootPane = new BorderPane();
		editorRootPane.setPrefWidth(editorWidth);
		editorRootPane.setPrefHeight(height);
		editorRootPane.setLayoutX(renderWidth);
		editorRootPane.getStyleClass().add("editor");
	}

	private String getApplicationName() {
		return "NextFractal 1.2";
	}

	private String getNoticeMessage() {
		return "\n\nNextFratal 1.2\n\n" +
				"https://github.com/nextbreakpoint/nextfractal\n\n" +
				"Copyright 2015-2016 Andrea Medeghini\n\n" +
				"NextFractal is an application for creating fractals and other graphics artifacts.\n\n" +
				"NextFractal is free software: you can redistribute it and/or modify\n" +
				"it under the terms of the GNU General Public License as published by\n" +
				"the Free Software Foundation, either version 3 of the License, or\n" +
				"(at your option) any later version.\n\n" +
				"NextFractal is distributed in the hope that it will be useful,\n" +
				"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
				"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
				"GNU General Public License for more details.\n\n" +
				"You should have received a copy of the GNU General Public License\n" +
				"along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.\n\n";
	}

	private Object showCompilerAlert() {
		ButtonType exitButtonType = new ButtonType("Continue", ButtonData.OK_DONE);
		Dialog<String> dialog = new Dialog<>();
		dialog.getDialogPane().getButtonTypes().add(exitButtonType);
		dialog.setGraphic(createIconImage("/icon-errors.png"));
		dialog.setTitle("Warning");
		dialog.setHeaderText("Cannot find Java compiler in your classpath");
		dialog.setContentText("Java compiler is required to reduce computation time. Please install Java JDK 8 (>= 1.8.0_40) or later and add directory your_jdk_path/bin to your system's path.");
		dialog.showAndWait();
		return null;
	}

	private void sessionCreated(Optional<Object> session) {
		logger.info("Session created");
	}

	private void loadStyleSheets(Scene scene) {
		tryLoadStyleSheet("/theme.css").ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL)));

		pluginsStream().map(FractalFactory::getId).map(name -> "/" + name.toLowerCase() + ".css")
				.map(resourceName -> tryLoadStyleSheet(resourceName)).forEach(maybe -> maybe.ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL))));
	}

	private Try<String, Exception> tryLoadStyleSheet(String resourceName) {
		return Try.of(() -> getClass().getResource(resourceName).toExternalForm()).onFailure(e -> logger.log(Level.WARNING, "Cannot load style sheet " + resourceName, e));
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

	private void printPlugins() {
		pluginsStream().forEach(plugin -> logger.fine("Found plugin " + plugin.getId()));
	}

	private Try<Pane, Exception> createEditorPane(Session session, String pluginId) {
		return selectPlugin(pluginId, plugin -> Objects.requireNonNull(plugin.createEditorPane(session)))
				.onFailure(e -> logger.log(Level.WARNING, "Cannot create editor panel with pluginId " + pluginId, e));
	}

	private Try<Pane, Exception> createRenderPane(Session session, String pluginId, int width, int height) {
		return selectPlugin(pluginId, plugin -> Objects.requireNonNull(plugin.createRenderPane(session, width, height)))
				.onFailure(e -> logger.log(Level.WARNING, "Cannot create renderer panel with pluginId " + pluginId, e));
	}

	private Try<Session, Exception> createSession(String pluginId) {
		return selectPlugin(pluginId, plugin -> Objects.requireNonNull(plugin.createSession()))
				.onFailure(e -> logger.log(Level.WARNING, "Cannot create session with pluginId " + pluginId, e));
	}

	private ImageView createIconImage(String name) {
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(32);
		image.setFitHeight(32);
		return image;
	}

	private static class DefaultSessionListener implements SessionListener {
		private final ExportService exportService;

		public DefaultSessionListener(ExportService exportService) {
			this.exportService = exportService;
		}

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
	}
}
