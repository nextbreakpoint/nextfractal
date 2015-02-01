package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.core.DoubleVector4D;
import com.nextbreakpoint.nextfractal.core.IntegerVector4D;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerBuilder;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Color;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Orbit;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.Renderer;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererView;
import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;

public class MandelbrotEditorPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotEditorPane.class.getName());
	private static final AtomicInteger id = new AtomicInteger(0);
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRenderFactory renderFactory;
	private final FractalSession session;
	private FileChooser fileChooser;
	private File currentFile;
	private Renderer renderer;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		
		threadFactory = new DefaultThreadFactory("Image", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();

		getStyleClass().add("mandelbrot");

		TabPane tabPane = new TabPane();
		Tab sourceTab = new Tab();
		sourceTab.setText("Source");
		tabPane.getTabs().add(sourceTab);
		Tab historyTab = new Tab();
		historyTab.setText("History");
		tabPane.getTabs().add(historyTab);
		Tab libraryTab = new Tab();
		libraryTab.setText("Library");
		tabPane.getTabs().add(libraryTab);
		Tab jobsTab = new Tab();
		jobsTab.setText("Jobs");
		tabPane.getTabs().add(jobsTab);
		setCenter(tabPane);

		BorderPane sourcePane = new BorderPane();
		TextArea sourceText = new TextArea();
		sourceText.getStyleClass().add("source-pane");
		HBox sourceButtons = new HBox(10);
		Button renderButton = new Button("Render");
		Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");
		sourceButtons.getChildren().add(renderButton);
		sourceButtons.getChildren().add(loadButton);
		sourceButtons.getChildren().add(saveButton);
		sourceButtons.getStyleClass().add("actions-pane");
		sourcePane.setCenter(sourceText);
		sourcePane.setBottom(sourceButtons);
		sourceTab.setContent(sourcePane);

		BorderPane historyPane = new BorderPane();
		ListView<MandelbrotData> historyList = new ListView<>();
		historyList.getStyleClass().add("history-list");
		historyList.setCellFactory(new Callback<ListView<MandelbrotData>, ListCell<MandelbrotData>>() {
			@Override
			public ListCell<MandelbrotData> call(ListView<MandelbrotData> gridView) {
				return new HistoryListCell();
			}
		});
		HBox historyButtons = new HBox(10);
		Button clearButton = new Button("Clear");
		historyButtons.getChildren().add(clearButton);
		historyButtons.getStyleClass().add("actions-pane");
		historyPane.setCenter(historyList);
		historyPane.setBottom(historyButtons);
		historyTab.setContent(historyPane);

		BorderPane libraryPane = new BorderPane();
		ListView<MandelbrotData> libraryList = new ListView<>();
		libraryList.getStyleClass().add("library-list");
		libraryList.setCellFactory(new Callback<ListView<MandelbrotData>, ListCell<MandelbrotData>>() {
			@Override
			public ListCell<MandelbrotData> call(ListView<MandelbrotData> gridView) {
				return new LibraryListCell();
			}
		});		
		HBox libraryButtons = new HBox(10);
		Button insertButton = new Button("Insert");
		Button deleteButton = new Button("Delete");
		Button importButton = new Button("Import");
		Button exportButton = new Button("Export");
		libraryButtons.getChildren().add(insertButton);
		libraryButtons.getChildren().add(deleteButton);
		libraryButtons.getChildren().add(importButton);
		libraryButtons.getChildren().add(exportButton);
		libraryButtons.getStyleClass().add("actions-pane");
		libraryPane.setCenter(libraryList);
		libraryPane.setBottom(libraryButtons);
		libraryTab.setContent(libraryPane);

		BorderPane jobsPane = new BorderPane();
		ListView<MandelbrotData> jobsList = new ListView<>();
		jobsList.getStyleClass().add("jobs-list");
		HBox jobsButtons = new HBox(10);
		Button suspendButton = new Button("Suspend");
		Button resumeButton = new Button("Resume");
		Button removeButton = new Button("Remove");
		jobsButtons.getChildren().add(suspendButton);
		jobsButtons.getChildren().add(resumeButton);
		jobsButtons.getChildren().add(removeButton);
		jobsButtons.getStyleClass().add("actions-pane");
		jobsPane.setCenter(jobsList);
		jobsPane.setBottom(jobsButtons);
		jobsTab.setContent(jobsPane);

		sourceText.setText(getMandelbrotSession().getSource());
		
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void dataChanged(FractalSession session) {
				sourceText.setText(getMandelbrotSession().getSource());
				addDataToHistory(historyList);
			}

			@Override
			public void viewChanged(FractalSession session, boolean zoom) {
				if (!zoom) {
					addDataToHistory(historyList);
				}
			}

			@Override
			public void terminate(FractalSession session) {
			}
		});
		
		renderButton.setOnAction(e -> {
			getMandelbrotSession().setSource(sourceText.getText());
		});
		
		loadButton.setOnAction(e -> {
			createFileChooser();
			fileChooser.setTitle("Load");
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				currentFile = file;
				try {
					FileService service = new FileService();
					MandelbrotData data = service.loadFromFile(currentFile);
					logger.info(data.toString());
					getMandelbrotSession().setData(data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});
		
		saveButton.setOnAction(e -> {
			createFileChooser();
			fileChooser.setTitle("Save");
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				currentFile = file;
				try {
					FileService service = new FileService();
					MandelbrotData data = getMandelbrotSession().toData();
					logger.info(data.toString());
					service.saveToFile(currentFile, data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});
		
		clearButton.setOnAction(e -> {
			logger.info("Clear history");
			clearHistory(historyList);
		});
		
		insertButton.setOnAction(e -> {
			logger.info("Insert data");
			addDataToLibrary(libraryList, getMandelbrotSession().toData());
		});
		
		deleteButton.setOnAction(e -> {
			logger.info("Delete data");
			ObservableList<MandelbrotData> selectedItems = libraryList.getSelectionModel().getSelectedItems();
			removeDataFromLibrary(libraryList, selectedItems.toArray(new MandelbrotData[0]));
		});
		
		importButton.setOnAction(e -> {
			logger.info("Import data");
			createFileChooser();
			fileChooser.setTitle("Import");
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				currentFile = file;
				try {
					FileService service = new FileService();
					MandelbrotData data = service.loadFromFile(currentFile);
					logger.info(data.toString());
					addDataToLibrary(libraryList, data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});
		
		exportButton.setOnAction(e -> {
			ObservableList<MandelbrotData> selectedItems = libraryList.getSelectionModel().getSelectedItems();
			if (selectedItems.size() == 1) {
				logger.info("Export data");
				createFileChooser();
				fileChooser.setTitle("Export");
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					currentFile = file;
					try {
						FileService service = new FileService();
						logger.info(selectedItems.get(0).toString());
						service.saveToFile(currentFile, selectedItems.get(0));
					} catch (Exception x) {
						//TODO show error
					}
				}
			}
		});
		
		addDataToHistory(historyList);
		
		renderer = new Renderer(threadFactory, renderFactory, createTile(100, 100));
	}

	private void addDataToLibrary(ListView<MandelbrotData> libraryGrid, MandelbrotData data) {
		IntBuffer pixels = IntBuffer.allocate(renderer.getWidth() * renderer.getHeight());
		renderImage(getMandelbrotSession(), data, pixels);
		data.setPixels(pixels);
		libraryGrid.getItems().add(data);
	}

	private void removeDataFromLibrary(ListView<MandelbrotData> libraryGrid, MandelbrotData[] items) {
		libraryGrid.getItems().removeAll(items);
	}

	private void addDataToHistory(ListView<MandelbrotData> historyList) {
		historyList.getItems().add(getMandelbrotSession().toData());
	}
	
	private void clearHistory(ListView<MandelbrotData> historyList) {
		historyList.getItems().clear();
	}
	
	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	private void createFileChooser() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private RendererTile createTile(int width, int height) {
		int tileWidth = width;
		int tileHeight = height;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
	
	private void renderImage(MandelbrotSession session, MandelbrotData data, IntBuffer pixels) {
		try {
			Compiler compiler = new Compiler(session.getOutDir(), session.getPackageName(), session.getClassName());
			CompilerReport report = compiler.generateJavaSource(data.getSource());
			//TODO report errors
			CompilerBuilder<Orbit> orbitBuilder = compiler.compileOrbit(report);
			CompilerBuilder<Color> colorBuilder = compiler.compileColor(report);
			if (renderer != null) {
				renderer.abortTasks();
				renderer.waitForTasks();
				double[] traslation = data.getTraslation();
				double[] rotation = data.getRotation();
				double[] scale = data.getScale();
				double[] constant = data.getConstant();
				boolean julia = data.isJulia();
				renderer.setOrbit(orbitBuilder.build());
				renderer.setColor(colorBuilder.build());
				renderer.init();
				RendererView view = new RendererView();
				view .setTraslation(new DoubleVector4D(traslation));
				view.setRotation(new DoubleVector4D(rotation));
				view.setScale(new DoubleVector4D(scale));
				view.setState(new IntegerVector4D(0, 0, 0, 0));
				view.setJulia(julia);
				view.setConstant(new Number(constant));
				renderer.setView(view);
				renderer.runTask();
				renderer.waitForTasks();
				renderer.getPixels(pixels);
			}
		} catch (Exception e) {
			e.printStackTrace();//TODO display errors
		}
	}
}
