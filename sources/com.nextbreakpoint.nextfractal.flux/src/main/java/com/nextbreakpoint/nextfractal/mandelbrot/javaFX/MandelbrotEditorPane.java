package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;
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
import com.nextbreakpoint.nextfractal.core.Worker;
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
//	private static final AtomicInteger id = new AtomicInteger(0);
	private final DefaultThreadFactory threadFactory;
	private final JavaFXRenderFactory renderFactory;
	private final FractalSession session;
	private final Worker historyWorker;
	private MandelbrotImageGenerator generator;
	private FileChooser fileChooser;
	private File currentFile;
	private boolean noHistory;
	private final UpdateSessions updateSessions;
	private volatile Thread watchSessionsThread;
	private volatile boolean watchRunning;

	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		
		threadFactory = new DefaultThreadFactory("MandelbrotEditorPane", true, Thread.MIN_PRIORITY);
		renderFactory = new JavaFXRenderFactory();

		historyWorker = new Worker(threadFactory);
		historyWorker.start();
		
		generator = new MandelbrotImageGenerator(threadFactory, renderFactory, createSingleTile(25, 25));
		
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
				return new HistoryListCell(generator.getWidth(), generator.getHeight());
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
				return new ExportListCell(generator.getWidth(), generator.getHeight());
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
			public void viewChanged(FractalSession session, boolean zoom) {
				if (!zoom) {
					addDataToHistory(historyList);
				}
			}

			@Override
			public void terminate(FractalSession session) {
				stopWatchingSessions();
			}

			@Override
			public void sessionAdded(FractalSession session, ExportSession exportSession) {
				jobsList.getItems().add(exportSession);
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
			createFileChooser();
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
			createFileChooser();
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
				exportSession.suspend();
			};
		});

		resumeButton.setOnAction(e -> {
			for (ExportSession exportSession : jobsList.getSelectionModel().getSelectedItems()) {
				exportSession.resume();
			};
		});

		removeButton.setOnAction(e -> {
			for (ExportSession exportSession : jobsList.getSelectionModel().getSelectedItems()) {
				exportSession.stop();
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
		
		addDataToHistory(historyList);
		
		updateSessions = new UpdateSessions(jobsList);

		startWatchingSessions();
	}

	@Override
	protected void finalize() throws Throwable {
		stopWatchingSessions();
		super.finalize();
	}

	private void addDataToHistory(ListView<MandelbrotData> historyList) {
		if (noHistory) {
			return;
		}
		MandelbrotData data = getMandelbrotSession().getData();
		historyWorker.addTask(new Runnable() {
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

	private void createFileChooser() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
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
	
	private void startWatchingSessions() {
		watchRunning = true;
		if (watchSessionsThread == null) {
			watchSessionsThread = createThread(updateSessions);
			watchSessionsThread.start();
		}
	}

	private void stopWatchingSessions() {
		watchRunning = false;
		if (watchSessionsThread != null) {
			watchSessionsThread.interrupt();
		}
		if (watchSessionsThread != null) {
			try {
				watchSessionsThread.join();
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			watchSessionsThread = null;
		}
	}
	
	private void updateSessions(ListView<ExportSession> jobsList) {
		Platform.runLater(() -> {
			ObservableList<ExportSession> items = jobsList.getItems();
			for (int i = items.size() - 1; i >= 0; i--) {
				ExportSession session = items.get(i);
				if (session.isTerminated()) {
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

	private Thread createThread(Runnable runnable) {
		return threadFactory.newThread(runnable);
	}

	public static <T> void triggerUpdate(ListView<T> listView, T newValue, int i) {
        EventType<? extends ListView.EditEvent<T>> type = ListView.editCommitEvent();
        Event event = new ListView.EditEvent<>(listView, type, newValue, i);
        listView.fireEvent(event);
    }
	
	private class UpdateSessions implements Runnable {
		private final ListView<ExportSession> jobsList;
		
		public UpdateSessions(ListView<ExportSession> jobsList) {
			this.jobsList = jobsList;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				while (watchRunning) {
					updateSessions(jobsList);
					synchronized (this) {
						wait(500);
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}
}
