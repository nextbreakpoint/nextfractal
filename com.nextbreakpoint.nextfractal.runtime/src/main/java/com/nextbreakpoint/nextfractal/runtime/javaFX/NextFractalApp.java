/*
 * NextFractal 1.3.0
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManagerEntry;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.Plugins;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportRenderer;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportService;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.tools.ToolProvider;

import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;

import static com.nextbreakpoint.nextfractal.core.Plugins.pluginsStream;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryGrammar;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryPlugin;

public class NextFractalApp extends Application {
	private static Logger logger = Logger.getLogger(NextFractalApp.class.getName());

	private static final String DEFAULT_PLUGIN_ID = "Mandelbrot";

	private Session session;

	public static void main(String[] args) {
		launch(args); 
    }

    @Override
    public void start(Stage primaryStage) {
		checkJavaVersion();

		EventBus eventBus = new EventBus();

		Try.of(() -> Objects.requireNonNull(ToolProvider.getSystemJavaCompiler())).ifFailure(e -> showCompilerAlert());

		logger.info(getNoticeMessage());

		logger.info("DPI = " + Screen.getPrimary().getDpi());

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		logger.info("Screen Size = (" + primaryScreenBounds.getWidth() + "," + primaryScreenBounds.getHeight() + ")");

		double baseWidth = primaryScreenBounds.getWidth() * 0.7;
		double imageWidth = Math.min(baseWidth * 0.6, 1400);
		int renderWidth = (int)Math.rint(imageWidth);
		int editorWidth = (int)Math.rint(imageWidth * 0.7);
		int sceneWidth = renderWidth + editorWidth;
		int sceneHeight = renderWidth;

		logger.info("Scene Size = (" + sceneWidth + "," + sceneHeight + ")");
		
        StackPane rootPane = new StackPane();

		printPlugins();

		ExportRenderer exportRenderer = new SimpleExportRenderer(createDefaultThreadFactory("NextFractalRender"), new JavaFXRendererFactory());
		ExportService exportService = new SimpleExportService(createDefaultThreadFactory("NextFractalExport"), exportRenderer);

		eventBus.subscribe("editor-grammar-changed", event -> tryGrammar((String) event).ifPresent(factory -> createSession(eventBus, factory)));

		eventBus.subscribe("session-data-changed", event -> session = (Session) ((Object[])event)[0]);

		eventBus.subscribe("session-changed", event -> eventBus.postEvent("session-data-changed", new Object[] { session, false }));

		eventBus.subscribe("session-terminated", event -> handleSessionTerminate(exportService));

		eventBus.subscribe("session-export", event -> handleSessionExport(exportService));

		eventBus.subscribe("editor-load-file", event -> handleLoadFile(eventBus, (File)event));

		eventBus.subscribe("editor-save-file", event -> handleSaveFile(eventBus, (File)event));

		rootPane.getChildren().add(createMainPane(eventBus, editorWidth, renderWidth, sceneHeight));

        Scene scene = new Scene(rootPane, sceneWidth, sceneHeight);

		loadStyleSheets(scene);

		createMenuBar();

		primaryStage.setOnCloseRequest(e -> eventBus.postEvent("session-terminated", ""));

		DoubleProperty fontSize = new SimpleDoubleProperty(computeOptimalFontSize()); // font size in pt
		rootPane.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", fontSize));

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle(getApplicationName());

		String defaultPluginId = System.getProperty("initialPluginId", DEFAULT_PLUGIN_ID);
		tryPlugin(defaultPluginId).ifPresent(factory -> createSession(eventBus, factory));

		primaryStage.show();
	}

	private void handleLoadFile(EventBus eventBus, File file) {
		try (ZipInputStream is = new ZipInputStream(new FileInputStream(file))) {

			tryLoadingSession(readEntries(is))
				.onSuccess(session -> eventBus.postEvent("current-file-changed", file))
				.ifPresentOrThrow(session -> eventBus.postEvent("session-changed", session));

		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot load data from " + file.getAbsolutePath(), e);
			eventBus.postEvent("session-status-changed", "Cannot read from file " + file.getName() + ": " + e.getMessage());
		}
	}

	private void handleSaveFile(EventBus eventBus, File file) {
		try (ZipOutputStream os = new ZipOutputStream(new FileOutputStream(file))) {

			trySavingSession(os, session.getData()).onSuccess(x -> eventBus.postEvent("current-file-changed", file)).orThrow();

		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot save data to " + file.getAbsolutePath(), e);
			eventBus.postEvent("session-status-changed", "Cannot write into file " + file.getName() + ": " + e.getMessage());
		}
	}

	private Try<Session, Exception> tryLoadingSession(List<FileManagerEntry> entries) {
		return readManifest(entries)
			.flatMap(Plugins::tryPlugin)
			.map(factory -> factory.createFileManager())
			.flatMap(manager -> manager.readEntries(entries));
	}

	private Try<String, Exception> readManifest(List<FileManagerEntry> entries) {
		return entries.stream().filter(entry -> entry.getName().equals("manifest"))
			.findFirst().map(manifest -> Try.success(new String(manifest.getData())))
			.orElseGet(() -> Try.failure(new Exception("Manifest is required")));
	}

	private Try<Object, Exception> trySavingSession(ZipOutputStream os, Object data) {
		return tryPlugin(session.getPluginId())
            .map(factory -> factory.createFileManager())
            .flatMap(manager -> manager.writeEntries(data))
            .flatMap(entries -> putEntries(os, entries));
	}

	private Try<Object, Exception> putEntries(ZipOutputStream os, List<FileManagerEntry> entries) {
		return entries.stream().map(entry -> Try.of(() -> putEntry(os, entry)))
			.filter(result -> result.isFailure()).findFirst().orElse(Try.success(entries));
	}

	private Object putEntry(ZipOutputStream os, FileManagerEntry entry) throws IOException {
		ZipEntry zipEntry = new ZipEntry(entry.getName());
		os.putNextEntry(zipEntry);
		os.write(entry.getData());
		os.closeEntry();
		return entry;
	}

	private List<FileManagerEntry> readEntries(ZipInputStream is) throws IOException {
		LinkedList<FileManagerEntry> entries = new LinkedList<>();
		for (ZipEntry entry = is.getNextEntry(); entry != null; entry = is.getNextEntry()) entries.add(readEntry(is, entry));
		return entries;
	}

	private FileManagerEntry readEntry(ZipInputStream is, ZipEntry entry) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyBytes(is, baos);
		return new FileManagerEntry(entry.getName(), baos.toByteArray());
	}

	private void copyBytes(InputStream is, OutputStream os) throws IOException {
		byte[] data = new byte[4096];
		int length = 0;
		while ((length = is.read(data)) > 0) {
			os.write(data, 0, length);
		}
	}

	private void handleSessionTerminate(ExportService exportService) {
		logger.info("Terminating services...");
		exportService.shutdown();
	}

	private void handleSessionExport(ExportService exportService) {
//		ExportSession session = new ExportSession();
//		exportService.startSession(session);
	}

	private void createSession(EventBus eventBus, FractalFactory factory) {
		tryLoadingSession(factory, eventBus).ifPresent(session -> eventBus.postEvent("session-changed", session));
	}

	private DefaultThreadFactory createDefaultThreadFactory(String name) {
		return new DefaultThreadFactory(name, true, Thread.MIN_PRIORITY);
	}

	private int computeOptimalFontSize() {
		int size = 8;

		Screen screen = Screen.getPrimary();

		if (screen.getDpi() > 100 || screen.getVisualBounds().getWidth() > 1200) {
			size = 12;
		}

		if (screen.getDpi() > 200 || screen.getVisualBounds().getWidth() > 2400) {
			size = 16;
		}

		return size;
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

	private Pane createMainPane(EventBus eventBus, int editorWidth, int renderWidth, int height) {
		int width = renderWidth + editorWidth;
		Pane mainPane = new Pane();
		mainPane.setPrefWidth(width);
		mainPane.setPrefHeight(height);
		mainPane.setMinWidth(width);
		mainPane.setMinHeight(height);
		mainPane.setMaxWidth(width);
		mainPane.setMaxHeight(height);
		Pane editorRootPane = createEditorPane(eventBus, editorWidth, height);
		Pane renderRootPane = createRenderPane(eventBus, renderWidth, height);
		mainPane.getChildren().add(renderRootPane);
		mainPane.getChildren().add(editorRootPane);
		mainPane.getStyleClass().add("application");
		editorRootPane.setLayoutX(renderWidth);
		return mainPane;
	}

	private Pane createRenderPane(EventBus eventBus, int width, int height) {
		MainRenderPane renderRootPane = new MainRenderPane(eventBus, width, height);
		renderRootPane.setPrefWidth(width);
		renderRootPane.setPrefHeight(height);
		renderRootPane.setMinWidth(width);
		renderRootPane.setMinHeight(height);
		renderRootPane.setMaxWidth(width);
		renderRootPane.setMaxHeight(height);
		renderRootPane.getStyleClass().add("render-pane");
		return renderRootPane;
	}

	private MainEditorPane createEditorPane(EventBus eventBus, int width, int height) {
		MainEditorPane editorRootPane = new MainEditorPane(eventBus);
		editorRootPane.setPrefWidth(width);
		editorRootPane.setPrefHeight(height);
		editorRootPane.getStyleClass().add("editor-pane");
		return editorRootPane;
	}

	private ImageView createIconImage(String name) {
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(32);
		image.setFitHeight(32);
		return image;
	}

	private String getApplicationName() {
		return "NextFractal 1.3.0";
	}

	private String getNoticeMessage() {
		return "\n\nNextFratal 1.3.0\n\n" +
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

	private void loadStyleSheets(Scene scene) {
		tryLoadStyleSheet("/theme.css").ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL)));

		pluginsStream().map(FractalFactory::getId).map(name -> "/" + name.toLowerCase() + ".css")
				.map(resourceName -> tryLoadStyleSheet(resourceName)).forEach(maybeURL -> maybeURL.ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL))));
	}

	private Try<String, Exception> tryLoadStyleSheet(String resourceName) {
		return Try.of(() -> getClass().getResource(resourceName).toExternalForm()).onFailure(e -> logger.log(Level.WARNING, "Cannot load style sheet " + resourceName, e));
	}

	private Try<Session, Exception> tryLoadingSession(FractalFactory factory, EventBus eventBus) {
		return Try.of(() -> Objects.requireNonNull(factory.createSession())).onFailure(e -> logger.log(Level.WARNING, "Cannot create session for " + factory.getId(), e));
	}

	private void printPlugins() {
		pluginsStream().forEach(plugin -> logger.fine("Found plugin " + plugin.getId()));
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
}
