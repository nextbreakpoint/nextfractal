package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import com.nextbreakpoint.nextfractal.ExportSession;
import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.SessionListener;
import com.nextbreakpoint.nextfractal.core.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.render.javaFX.JavaFXRenderFactory;

public class MandelbrotEditorPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotEditorPane.class.getName());
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRenderFactory renderFactory;
	private final FractalSession session;
	private MandelbrotImageGenerator generator;
	private FileChooser fileChooser;
	private File currentFile;
	private boolean noHistory;
	private ScheduledExecutorService sessionsExecutor;
	private ExecutorService historyExecutor;

	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		
		threadFactory = new DefaultThreadFactory("MandelbrotEditorPane", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();

		RendererTile generatorTile = createSingleTile(25, 25);
		
		generator = new MandelbrotImageGenerator(threadFactory, renderFactory, generatorTile);
		
		getStyleClass().add("mandelbrot");

		TabPane tabPane = new TabPane();
		Tab sourceTab = new Tab();
		sourceTab.setText("Source");
		tabPane.getTabs().add(sourceTab);
		Tab historyTab = new Tab();
		historyTab.setText("History");
		tabPane.getTabs().add(historyTab);
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
				return new HistoryListCell(generator.getSize(), generatorTile);
			}
		});
		HBox historyButtons = new HBox(10);
		Button clearButton = new Button("Clear");
		historyButtons.getChildren().add(clearButton);
		historyButtons.getStyleClass().add("actions-pane");
		historyPane.setCenter(historyList);
		historyPane.setBottom(historyButtons);
		historyTab.setContent(historyPane);

		BorderPane jobsPane = new BorderPane();
		ListView<ExportSession> jobsList = new ListView<>();
		jobsList.setCellFactory(new Callback<ListView<ExportSession>, ListCell<ExportSession>>() {
			@Override
			public ListCell<ExportSession> call(ListView<ExportSession> gridView) {
				return new ExportListCell(generator.getSize(), generatorTile);
			}
		});
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
		
		session.addSessionListener(new SessionListener() {
			@Override
			public void dataChanged(FractalSession session) {
				sourceText.setText(getMandelbrotSession().getSource());
				addDataToHistory(historyList);
			}
			
			@Override
			public void pointChanged(FractalSession session) {
			}

			@Override
			public void viewChanged(FractalSession session, boolean zoom) {
				if (!zoom) {
					addDataToHistory(historyList);
				}
			}

			@Override
			public void terminate(FractalSession session) {
				shutdown();
			}

			@Override
			public void sessionAdded(FractalSession session, ExportSession exportSession) {
				jobsList.getItems().add(exportSession);
				tabPane.getSelectionModel().select(jobsTab);
			}

			@Override
			public void sessionRemoved(FractalSession session, ExportSession exportSession) {
				jobsList.getItems().remove(exportSession);
			}
		});
		
		renderButton.setOnAction(e -> {
			getMandelbrotSession().setSource(sourceText.getText());
		});
		
		loadButton.setOnAction(e -> {
			createFileChooser(".mg");
			fileChooser.setTitle("Load");
			File file = fileChooser.showOpenDialog(null);
			if (file != null) {
				currentFile = file;
				try {
					MandelbrotDataStore service = new MandelbrotDataStore();
					MandelbrotData data = service.loadFromFile(currentFile);
					logger.info(data.toString());
					getMandelbrotSession().setData(data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});
		
		saveButton.setOnAction(e -> {
			createFileChooser(".mg");
			fileChooser.setTitle("Save");
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				currentFile = file;
				try {
					MandelbrotDataStore service = new MandelbrotDataStore();
					MandelbrotData data = getMandelbrotSession().getData();
					logger.info(data.toString());
					service.saveToFile(currentFile, data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});

		suspendButton.setOnAction(e -> {
			for (ExportSession exportSession : jobsList.getSelectionModel().getSelectedItems()) {
				session.getExportService().suspendSession(exportSession);
			};
		});

		resumeButton.setOnAction(e -> {
			for (ExportSession exportSession : jobsList.getSelectionModel().getSelectedItems()) {
				session.getExportService().resumeSession(exportSession);
			};
		});

		removeButton.setOnAction(e -> {
			for (ExportSession exportSession : jobsList.getSelectionModel().getSelectedItems()) {
				session.getExportService().stopSession(exportSession);
			};
		});

		clearButton.setOnAction(e -> {
			logger.info("Clear history");
			clearHistory(historyList);
			addDataToHistory(historyList);
		});
		
		historyList.setOnMouseClicked(e -> {
			int index = historyList.getSelectionModel().getSelectedIndex();
			MandelbrotData data = historyList.getItems().get(index);
			noHistory = true;
			getMandelbrotSession().setData(data);
			noHistory = false;
		});
		
		historyExecutor = Executors.newSingleThreadExecutor(threadFactory);
		
		sessionsExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
		sessionsExecutor.scheduleWithFixedDelay(new UpdateSessionsRunnable(jobsList), 500, 500, TimeUnit.MILLISECONDS);
		
		addDataToHistory(historyList);
	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	private void addDataToHistory(ListView<MandelbrotData> historyList) {
		if (noHistory) {
			return;
		}
		MandelbrotData data = getMandelbrotSession().getData();
		historyExecutor.submit(new Runnable() {
			@Override
			public void run() {
				data.setPixels(generator.renderImage(data));
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						historyList.getItems().add(0, data);
					}
				});
			}
		});
	}
	
	private void clearHistory(ListView<MandelbrotData> historyList) {
		historyList.getItems().clear();
	}
	
	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	private void createFileChooser(String suffix) {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialFileName("mandel" + suffix);
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private RendererTile createSingleTile(int width, int height) {
		int tileWidth = width;
		int tileHeight = height;
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(tileWidth, tileHeight);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		RendererTile tile = new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
		return tile;
	}
	
	private void shutdown() {
		sessionsExecutor.shutdownNow();
		historyExecutor.shutdownNow();
		try {
			sessionsExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
		try {
			historyExecutor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}
	}
	
	private void updateSessions(ListView<ExportSession> jobsList) {
		Platform.runLater(() -> {
			ObservableList<ExportSession> items = jobsList.getItems();
			for (int i = items.size() - 1; i >= 0; i--) {
				ExportSession session = items.get(i);
				if (session.isStopped()) {
					items.remove(i);
				} else {
					if (jobsList.getSelectionModel().isSelected(i)) {
						triggerUpdate(jobsList, session, i);
						jobsList.getSelectionModel().select(i);
					} else {
						triggerUpdate(jobsList, session, i);
					}
				}
			}
		});
	}

	public static <T> void triggerUpdate(ListView<T> listView, T newValue, int i) {
        EventType<? extends ListView.EditEvent<T>> type = ListView.editCommitEvent();
        Event event = new ListView.EditEvent<>(listView, type, newValue, i);
        listView.fireEvent(event);
    }
	
	private class UpdateSessionsRunnable implements Runnable {
		private final ListView<ExportSession> jobsList;
		
		public UpdateSessionsRunnable(ListView<ExportSession> jobsList) {
			this.jobsList = jobsList;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			updateSessions(jobsList);
		}
	}
}
