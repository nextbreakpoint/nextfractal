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
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.Clip;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.FileManager;
import com.nextbreakpoint.nextfractal.core.FractalFactory;
import com.nextbreakpoint.nextfractal.core.Session;
import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportRenderer;
import com.nextbreakpoint.nextfractal.core.export.ExportService;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportRenderer;
import com.nextbreakpoint.nextfractal.runtime.export.SimpleExportService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.tools.ToolProvider;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.nextbreakpoint.nextfractal.core.Plugins.factories;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindEncoder;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactory;
import static com.nextbreakpoint.nextfractal.core.Plugins.tryFindFactoryByGrammar;

public class NextFractalApp extends Application {
	private static Logger logger = Logger.getLogger(NextFractalApp.class.getName());

	private static final String DEFAULT_PLUGIN_ID = "Mandelbrot";
	private static final String FILE_EXTENSION = ".nf.zip";

	private List<Clip> clips = new ArrayList<>();
	private Session session;
	private boolean capture;
	private boolean edited;
	private Clip clip;

	private FileChooser exportFileChooser;
	private File exportCurrentFile;
	private FileChooser bundleFileChooser;
	private File bundleCurrentFile;

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

		ExportRenderer exportRenderer = new SimpleExportRenderer(createThreadFactory("Export Renderer"), new JavaFXRendererFactory());
		ExportService exportService = new SimpleExportService(eventBus, createThreadFactory("Export Service"), exportRenderer);

		eventBus.subscribe("editor-grammar-changed", event -> tryFindFactoryByGrammar((String) event[0]).ifPresent(factory -> createSession(eventBus, factory)));

		eventBus.subscribe("session-data-changed", event -> session = (Session) event[0]);

		eventBus.subscribe("session-data-changed", event -> handleSessionChanged((Session) event[0]));

		eventBus.subscribe("session-terminated", event -> handleSessionTerminate(exportService));

		eventBus.subscribe("export-session-created", event -> handleExportSessionCreated(exportService, (ExportSession) event[0]));

		eventBus.subscribe("export-session-stopped", event -> handleExportSessionStopped(exportService, (ExportSession) event[0]));

		eventBus.subscribe("export-session-resumed", event -> handleExportSessionResumed(exportService, (ExportSession) event[0]));

		eventBus.subscribe("export-session-suspended", event -> handleExportSessionSuspended(exportService, (ExportSession) event[0]));

		eventBus.subscribe("editor-load-file", event -> handleLoadFile(eventBus, (File)event[0]));

		eventBus.subscribe("editor-save-file", event -> handleSaveFile(eventBus, (File)event[0]));

		eventBus.subscribe("session-error-changed", event -> handleErrorChanged((String)event[0]));

		eventBus.subscribe("capture-session", event -> handleCaptureSession(eventBus, (String)event[0]));

		eventBus.subscribe("capture-clip-restored", event -> handleClipRestored((Clip)event[0]));

		eventBus.subscribe("capture-clip-removed", event -> handleClipRemoved((Clip)event[0]));

		eventBus.subscribe("capture-clip-added", event -> handleClipAdded((Clip)event[0]));

		eventBus.subscribe("capture-clip-moved", event -> handleClipMoved((int) event[0], (int) event[1]));

		eventBus.subscribe("session-bundle-loaded", event -> handleBundleLoaded(eventBus, (Bundle) event[0], (boolean) event[1], (boolean) event[2]));

		eventBus.subscribe("session-export", event -> handleExportSession(eventBus, primaryStage, (String) event[1], session, clips, file -> exportCurrentFile = file, (RendererSize) event[0]));

		eventBus.subscribe("current-file-changed", event -> bundleCurrentFile = (File)event[0]);

		eventBus.subscribe("editor-action", event -> handleEditorAction(eventBus, primaryStage, (String)event[0]));

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

		primaryStage.show();

		BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		Objects.requireNonNull(image.createGraphics());

		Platform.runLater(() -> {
			String defaultPluginId = System.getProperty("initialPluginId", DEFAULT_PLUGIN_ID);
			tryFindFactory(defaultPluginId).ifPresent(factory -> createSession(eventBus, factory));
		});
	}

	private void handleEditorAction(EventBus eventBus, Window window, String action) {
		if (action.equals("load")) Optional.ofNullable(showLoadBundleFileChooser()).map(fileChooser -> fileChooser.showOpenDialog(window)).ifPresent(file -> eventBus.postEvent("editor-load-file", file));
		if (action.equals("save")) Optional.ofNullable(showSaveBundleFileChooser()).map(fileChooser -> fileChooser.showSaveDialog(window)).ifPresent(file -> eventBus.postEvent("editor-save-file", file));
	}

	private void handleBundleLoaded(EventBus eventBus, Bundle bundle, boolean continuous, boolean appendHistory) {
		if (edited && clips.size() > 0 && bundle.getClips().size() > 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Action required");
			alert.setHeaderText("Current session contains " + clips.size() + " clip" + (clips.size() != 1 ? "s" : "") + ". Loaded bundle " + (bundle.getClips().size() == 0 ? "doesn't contain clips" : "contains " + bundle.getClips().size() + " clip" + (bundle.getClips().size() != 1 ? "s" : "") + ".\n\n What do you want to do with existing session's clips ?"));

			ButtonType buttonTypeOne = new ButtonType("Keep");
			ButtonType buttonTypeTwo = new ButtonType("Merge");
			ButtonType buttonTypeThree = new ButtonType("Replace");

			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

			Optional<ButtonType> result = alert.showAndWait();

			result.filter(v -> v == buttonTypeTwo).ifPresent(v -> {
				eventBus.postEvent("capture-clips-merged", bundle.getClips());
			});

			result.filter(v -> v == buttonTypeThree).ifPresent(v -> {
				eventBus.postEvent("capture-clips-loaded", bundle.getClips());
			});
		} else {
			if (edited && clips.size() > 0) {
				eventBus.postEvent("capture-clips-merged", bundle.getClips());
			} else {
				eventBus.postEvent("capture-clips-loaded", bundle.getClips());
			}
		}

		eventBus.postEvent("session-data-loaded", bundle.getSession(), continuous, appendHistory);
	}

	private void handleClipAdded(Clip clip) {
		clips.add(clip);
		edited = true;
	}

	private void handleClipRemoved(Clip clip) {
		clips.remove(clip);
		edited = true;
	}

	private void handleClipRestored(Clip clip) {
		clips.add(clip);
		edited = false;
	}

	private void handleClipMoved(int fromIndex, int toIndex) {
		Clip clip = clips.get(fromIndex);
		clips.remove(fromIndex);
		clips.add(toIndex, clip);
		edited = true;
	}

	private void handleCaptureSession(EventBus eventBus, String action) {
		if (action.equals("start")) startCapture(eventBus);
		if (action.equals("stop")) stopCapture(eventBus);
	}

	private void startCapture(EventBus eventBus) {
		capture = true;
		clip = new Clip();
		if (session != null) {
			clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
		}
		eventBus.postEvent("capture-session-started", clip);
	}

	private void stopCapture(EventBus eventBus) {
		capture = false;
		if (session != null) {
			clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
		}
		eventBus.postEvent("capture-session-stopped", clip);
	}

	private void handleSessionChanged(Session session) {
		if (capture) {
			clip.append(new Date(), session.getPluginId(), session.getScript(), session.getMetadata());
		}
	}

	private void handleErrorChanged(String error) {
	}

	private void handleLoadFile(EventBus eventBus, File file) {
		FileManager.loadFile(file)
			.onSuccess(session -> eventBus.postEvent("current-file-changed", file))
			.onFailure(e -> showLoadError(eventBus, file, e))
			.ifPresent(bundle -> eventBus.postEvent("session-bundle-loaded", bundle, false, true));
	}

	private void handleSaveFile(EventBus eventBus, File file) {
		FileManager.saveFile(file, new Bundle(session, clips))
			.onSuccess(session -> eventBus.postEvent("current-file-changed", file))
			.ifFailure(e -> showSaveError(eventBus, file, e));
	}

	private void showLoadError(EventBus eventBus, File file, Exception e) {
		logger.log(Level.WARNING, "Cannot load file " + file.getAbsolutePath(), e);
		eventBus.postEvent("session-status-changed", "Cannot load file " + file.getName());
	}

	private void showSaveError(EventBus eventBus, File file, Exception e) {
		logger.log(Level.WARNING, "Cannot save file " + file.getAbsolutePath(), e);
		eventBus.postEvent("session-status-changed", "Cannot save file " + file.getName());
	}

	private void handleSessionTerminate(ExportService exportService) {
		logger.info("Terminating services...");
		exportService.shutdown();
	}

	private void handleExportSessionCreated(ExportService exportService, ExportSession exportSession) {
		exportService.startSession(exportSession);
	}

	private void handleExportSessionStopped(ExportService exportService, ExportSession exportSession) {
		exportService.stopSession(exportSession);
	}

	private void handleExportSessionResumed(ExportService exportService, ExportSession exportSession) {
		exportService.resumeSession(exportSession);
	}

	private void handleExportSessionSuspended(ExportService exportService, ExportSession exportSession) {
		exportService.suspendSession(exportSession);
	}

	private void createSession(EventBus eventBus, FractalFactory factory) {
		tryCreateSession(factory).ifPresent(session -> eventBus.postEvent("session-data-loaded", session, false, true));
	}

	private DefaultThreadFactory createThreadFactory(String name) {
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
		Pane editorRootPane = createSidePane(eventBus, editorWidth, height);
		Pane renderRootPane = createRenderPane(eventBus, renderWidth, height);
		mainPane.getChildren().add(renderRootPane);
		mainPane.getChildren().add(editorRootPane);
		mainPane.getStyleClass().add("application");
		editorRootPane.setLayoutX(renderWidth);
		return mainPane;
	}

	private Pane createRenderPane(EventBus eventBus, int width, int height) {
		MainCentralPane renderRootPane = new MainCentralPane(eventBus, width, height);
		renderRootPane.setPrefWidth(width);
		renderRootPane.setPrefHeight(height);
		renderRootPane.setMinWidth(width);
		renderRootPane.setMinHeight(height);
		renderRootPane.setMaxWidth(width);
		renderRootPane.setMaxHeight(height);
		renderRootPane.getStyleClass().add("central-pane");
		return renderRootPane;
	}

	private MainSidePane createSidePane(EventBus eventBus, int width, int height) {
		MainSidePane sideRootPane = new MainSidePane(eventBus);
		sideRootPane.setPrefWidth(width);
		sideRootPane.setPrefHeight(height);
		sideRootPane.getStyleClass().add("side-pane");
		return sideRootPane;
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
		return "NextFractal 2.0.0 - Created by Andrea Medeghini";
	}

	private String getNoticeMessage() {
		return "\n\nNextFractal 2.0.0\n\n" +
				"https://github.com/nextbreakpoint/nextfractal\n\n" +
				"Copyright 2015-2017 Andrea Medeghini\n\n" +
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

		factories().map(FractalFactory::getId).map(name -> "/" + name.toLowerCase() + ".css")
			.map(resourceName -> tryLoadStyleSheet(resourceName)).forEach(maybeURL -> maybeURL.ifPresent(resourceURL -> scene.getStylesheets().add((resourceURL))));
	}

	private Try<String, Exception> tryLoadStyleSheet(String resourceName) {
		return Try.of(() -> getClass().getResource(resourceName).toExternalForm()).onFailure(e -> logger.log(Level.WARNING, "Cannot load style sheet " + resourceName, e));
	}

	private Try<Session, Exception> tryCreateSession(FractalFactory factory) {
		return Try.of(() -> Objects.requireNonNull(factory.createSession())).onFailure(e -> logger.log(Level.WARNING, "Cannot create session for " + factory.getId(), e));
	}

	private void printPlugins() {
		factories().forEach(plugin -> logger.fine("Found plugin " + plugin.getId()));
	}

	private void handleExportSession(EventBus eventBus, Window window, String format, Session session, List<Clip> clips, Consumer<File> consumer, RendererSize size) {
		createEncoder(format).ifPresent(encoder -> selectFileAndExport(eventBus, window, encoder, session, clips, consumer, size));
	}

	private Optional<? extends Encoder> createEncoder(String format) {
		return tryFindEncoder(format).onFailure(e -> logger.warning("Cannot find encoder for format " + format)).value();
	}

	private void selectFileAndExport(EventBus eventBus, Window window, Encoder encoder, Session session, List<Clip> clips, Consumer<File> consumer, RendererSize size) {
		Consumer<File> fileConsumer = file -> createExportSession(eventBus, size, encoder, file, session, clips);
		Optional.ofNullable(prepareExportFileChooser(encoder.getSuffix()).showSaveDialog(window)).ifPresent(fileConsumer.andThen(consumer));
	}

	private void createExportSession(EventBus eventBus, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
		startExportSession(eventBus, UUID.randomUUID().toString(), size, encoder, file, session, clips);
	}

	private String createFileName() {
		SimpleDateFormat df = new SimpleDateFormat("YYYYMMdd-HHmmss");
		return df.format(new Date());
	}

	private FileChooser prepareExportFileChooser(String suffix) {
		ensureExportFileChooser(suffix);
		exportFileChooser.setTitle("Export");
		exportFileChooser.setInitialFileName(createFileName() + suffix);
		if (exportCurrentFile != null) {
			exportFileChooser.setInitialDirectory(exportCurrentFile.getParentFile());
		}
		return exportFileChooser;
	}

	private void startExportSession(EventBus eventBus, String uuid, RendererSize size, Encoder encoder, File file, Session session, List<Clip> clips) {
		try {
			File tmpFile = File.createTempFile("export-" + uuid, ".dat");
			ExportSession exportSession = new ExportSession(uuid, session, encoder.isVideoSupported() ? clips : new LinkedList<>(), file, tmpFile, size, 400, encoder);
			logger.info("Export session created: " + exportSession.getSessionId());
			eventBus.postEvent("export-session-created", exportSession);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot export data to file " + file.getAbsolutePath(), e);
			//TODO display error
		}
	}

	private void ensureExportFileChooser(String suffix) {
		if (exportFileChooser == null) {
			exportFileChooser = new FileChooser();
			exportFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private void ensureBundleFileChooser(String suffix) {
		if (bundleFileChooser == null) {
			bundleFileChooser = new FileChooser();
			bundleFileChooser.setInitialFileName(createFileName() + suffix);
			bundleFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private FileChooser showSaveBundleFileChooser() {
		ensureBundleFileChooser(FILE_EXTENSION);
		bundleFileChooser.setTitle("Save");
		if (getBundleCurrentFile() != null) {
			bundleFileChooser.setInitialDirectory(getBundleCurrentFile().getParentFile());
			bundleFileChooser.setInitialFileName(getBundleCurrentFile().getName());
		}
		return bundleFileChooser;
	}

	private FileChooser showLoadBundleFileChooser() {
		ensureBundleFileChooser(FILE_EXTENSION);
		bundleFileChooser.setTitle("Load");
		if (getBundleCurrentFile() != null) {
			bundleFileChooser.setInitialDirectory(getBundleCurrentFile().getParentFile());
			bundleFileChooser.setInitialFileName(getBundleCurrentFile().getName());
		}
		return bundleFileChooser;
	}

	private File getBundleCurrentFile() {
		return bundleCurrentFile;
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
