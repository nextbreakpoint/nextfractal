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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.encoder.Encoder;
import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.session.SessionListener;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.*;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MandelbrotEditorPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotEditorPane.class.getName());
	private final MandelbrotImageGenerator generator;
	private final ScheduledExecutorService sessionsExecutor;
	private final ExecutorService historyExecutor;
	private final ExecutorService textExecutor;
	private final Session session;
	private final CodeArea codeArea;
	private final ExecutorService exportExecutor;
	private Pattern highlightingPattern;
	private FileChooser fileChooser;
	private volatile boolean noHistory;
	private File currentExportFile;
	private MandelbrotData exportData;

	public MandelbrotEditorPane(Session session) {
		this.session = session;

		RendererTile generatorTile = createSingleTile(50, 50);
		
		DefaultThreadFactory generatorThreadFactory = new DefaultThreadFactory("MandelbrotHistoryImageGenerator", true, Thread.MIN_PRIORITY);
		generator = new MandelbrotImageGenerator(generatorThreadFactory, new JavaFXRendererFactory(), generatorTile, true);
		
		getStyleClass().add("mandelbrot");

		codeArea = new CodeArea();
		codeArea.getStyleClass().add("source");

		EventHandler<ActionEvent> renderEventHandler = e -> Platform.runLater(() -> codeArea.replaceText(getMandelbrotSession().getSource()));

		EventHandler<ActionEvent> loadEventHandler = e -> Optional.ofNullable(showLoadFileChooser())
				.map(fileChooser -> fileChooser.showOpenDialog(MandelbrotEditorPane.this.getScene().getWindow())).ifPresent(file -> loadDataFromFile(file));

		EventHandler<ActionEvent> saveEventHandler = e -> Optional.ofNullable(showSaveFileChooser())
				.map(fileChooser -> fileChooser.showSaveDialog(MandelbrotEditorPane.this.getScene().getWindow())).ifPresent(file -> saveDataToFile(file));

		ListView<MandelbrotData> historyList = new ListView<>();
		historyList.setFixedCellSize(60);
		historyList.getStyleClass().add("history");
		historyList.setCellFactory(listView -> new HistoryListCell(generator.getSize(), generatorTile));

		ListView<ExportSession> jobsList = new ListView<>();
		jobsList.setFixedCellSize(60);
		jobsList.getStyleClass().add("jobs");
		jobsList.setCellFactory(listView -> new ExportListCell(generator.getSize(), generatorTile));

		BorderPane historyPane = new BorderPane();
//		BorderPane historyButtons = new BorderPane();
//		Button clearButton = new Button("", createIconImage("/icon-clear.png"));
//		clearButton.setTooltip(new Tooltip("Remove all elements"));
//		historyButtons.setRight(clearButton);
//		historyButtons.getStyleClass().add("menubar");
		historyPane.setCenter(historyList);
//		historyPane.setBottom(historyButtons);
		historyList.getSelectionModel().getSelectedItems().addListener((Change<? extends MandelbrotData> c) -> historyItemSelected(historyList));
//		clearButton.setOnAction(e -> Block.create((Block<ListView<MandelbrotData>,Exception>)(list -> logger.info("Clear history")))
//				.andThen(list -> historyRemoveAllItems(list)).andThen(list -> addDataToHistory(list)).tryExecute(historyList));

		BorderPane jobsPane = new BorderPane();
		BorderPane jobsButtons = new BorderPane();
		HBox jobsButtonsLeft = new HBox();
		HBox jobsButtonsRight = new HBox();
		Button exportButton = new Button("", createIconImage("/icon-export.png"));
		Button suspendButton = new Button("", createIconImage("/icon-suspend.png"));
		Button resumeButton = new Button("", createIconImage("/icon-resume.png"));
		Button removeButton = new Button("", createIconImage("/icon-remove.png"));
		exportButton.setTooltip(new Tooltip("Export fractal as image"));
		suspendButton.setTooltip(new Tooltip("Suspend selected tasks"));
		resumeButton.setTooltip(new Tooltip("Resume selected tasks"));
		removeButton.setTooltip(new Tooltip("Remove selected tasks"));
		suspendButton.setDisable(true);
		resumeButton.setDisable(true);
		removeButton.setDisable(true);
		jobsButtonsLeft.getChildren().add(exportButton);
		jobsButtonsRight.getChildren().add(suspendButton);
		jobsButtonsRight.getChildren().add(resumeButton);
		jobsButtonsRight.getChildren().add(removeButton);
		jobsButtons.setLeft(jobsButtonsLeft);
		jobsButtons.setRight(jobsButtonsRight);
		jobsButtons.getStyleClass().add("menubar");
		jobsPane.setCenter(jobsList);
		jobsPane.setBottom(jobsButtons);
		List<Button> buttons = Arrays.asList(suspendButton, resumeButton, removeButton);
		jobsList.getSelectionModel().getSelectedItems().addListener((Change<? extends ExportSession> c) -> updateButtons(buttons, c.getList().size() == 0));
		suspendButton.setOnAction(e -> selectedItems(jobsList).filter(exportSession -> !exportSession.isSuspended()).forEach(exportSession -> session.getExportService().suspendSession(exportSession)));
		resumeButton.setOnAction(e -> selectedItems(jobsList).filter(exportSession -> exportSession.isSuspended()).forEach(exportSession -> session.getExportService().resumeSession(exportSession)));
		removeButton.setOnAction(e -> selectedItems(jobsList).forEach(exportSession -> session.getExportService().stopSession(exportSession)));

		MandelbrotParamsPane paramsPane = new MandelbrotParamsPane(getMandelbrotSession());

		ExportPane exportPane = new ExportPane();

		Pane exportJobsPane = new Pane();
		exportJobsPane.getChildren().add(jobsPane);
		exportJobsPane.getChildren().add(exportPane);

		Pane sidePane = new Pane();
		sidePane.getChildren().add(exportJobsPane);
		sidePane.getChildren().add(historyPane);
		sidePane.getChildren().add(paramsPane);

		historyPane.getStyleClass().add("sidebar");
		paramsPane.getStyleClass().add("sidebar");
		jobsPane.getStyleClass().add("sidebar");

		ScrollPane codePane = new ScrollPane();
		codePane.setContent(codeArea);
		codePane.setFitToWidth(true);
		codePane.setFitToHeight(true);
		codePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		codePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		StatusPane statusPane = new StatusPane();

		Pane sourcePane = new Pane();
		HBox sourceButtons = new HBox();
		sourceButtons.setAlignment(Pos.CENTER);
		Button renderButton = new Button("", createIconImage("/icon-run.png"));
		Button loadButton = new Button("", createIconImage("/icon-load.png"));
		Button saveButton = new Button("", createIconImage("/icon-save.png"));
		ToggleButton paramsButton = new ToggleButton("", createIconImage("/icon-edit.png"));
		ToggleButton historyButton = new ToggleButton("", createIconImage("/icon-time.png"));
		ToggleButton jobsButton = new ToggleButton("", createIconImage("/icon-tool.png"));
		ToggleButton statusButton = new ToggleButton("", createIconImage("/icon-warn.png"));
		renderButton.setTooltip(new Tooltip("Render fractal"));
		loadButton.setTooltip(new Tooltip("Load fractal from file"));
		saveButton.setTooltip(new Tooltip("Save fractal to file"));
		paramsButton.setTooltip(new Tooltip("Show/hide parameters"));
		historyButton.setTooltip(new Tooltip("Show/hide history"));
		jobsButton.setTooltip(new Tooltip("Show/hide jobs"));
		statusButton.setTooltip(new Tooltip("Show/hide status"));
		sourceButtons.getChildren().add(renderButton);
		sourceButtons.getChildren().add(loadButton);
		sourceButtons.getChildren().add(saveButton);
		sourceButtons.getChildren().add(paramsButton);
		sourceButtons.getChildren().add(historyButton);
		sourceButtons.getChildren().add(jobsButton);
		sourceButtons.getChildren().add(statusButton);
		sourceButtons.getStyleClass().add("menubar");
		sourcePane.getChildren().add(codePane);
		sourcePane.getChildren().add(sourceButtons);
		sourcePane.getChildren().add(statusPane);
		sourcePane.getChildren().add(sidePane);
		renderButton.setOnAction(renderEventHandler);
		loadButton.setOnAction(loadEventHandler);
		saveButton.setOnAction(saveEventHandler);

		TranslateTransition sidebarTransition = createTranslateTransition(sidePane);
		TranslateTransition statusTransition = createTranslateTransition(statusPane);
		TranslateTransition exportTransition = createTranslateTransition(exportPane);

		paramsButton.setSelected(true);
		statusButton.setSelected(true);

		ToggleGroup viewGroup = new ToggleGroup();
		viewGroup.getToggles().add(historyButton);
		viewGroup.getToggles().add(paramsButton);
		viewGroup.getToggles().add(jobsButton);

		exportButton.setOnAction(e -> {
			//		if (errorProperty.getValue() == null) {
			MandelbrotSession mandelbrotSession = getMandelbrotSession();
			exportData = mandelbrotSession.getDataAsCopy();
			showPanel(exportTransition, a -> {});
		});

		exportPane.setExportDelegate(new ExportDelegate() {
			@Override
			public void createSession(RendererSize rendererSize) {
				doExportSession(rendererSize);
				hidePanel(exportTransition, a -> {});
			}

			@Override
			public void cancel() {
				hidePanel(exportTransition, a -> {});
			}
		});

		historyButton.selectedProperty().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				sidePane.getChildren().remove(historyPane);
				sidePane.getChildren().add(historyPane);
			}
		});

		paramsButton.selectedProperty().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				sidePane.getChildren().remove(paramsPane);
				sidePane.getChildren().add(paramsPane);
			}
		});

		jobsButton.selectedProperty().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				sidePane.getChildren().remove(exportJobsPane);
				sidePane.getChildren().add(exportJobsPane);
			}
		});

		viewGroup.selectedToggleProperty().addListener((source, oldValue, newValue) -> {
			if (newValue != null) {
				showSidebar(sidebarTransition, a -> {});
			} else {
				hideSidebar(sidebarTransition, a -> {});
			}
		});

		statusButton.selectedProperty().addListener((source, oldValue, newValue) -> {
			if (newValue) {
				showStatus(statusTransition, a -> {});
			} else {
				hideStatus(statusTransition, a -> {});
			}
		});

		sidePane.translateXProperty().addListener((source, oldValue, newValue) -> {
			codePane.prefWidthProperty().setValue(getWidth() - sidePane.getWidth() + newValue.doubleValue());
		});

		statusPane.translateYProperty().addListener((source, oldValue, newValue) -> {
			codePane.prefHeightProperty().setValue(getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
			sidePane.prefHeightProperty().setValue(getHeight() - statusPane.getHeight() - sourceButtons.getHeight() + newValue.doubleValue());
		});

		StackPane rootPane = new StackPane();
		rootPane.getChildren().add(sourcePane);
		setCenter(rootPane);

		initHighlightingPattern();

		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

		codeArea.plainTextChanges().successionEnds(Duration.ofMillis(500)).supplyTask(this::computeTaskAsync)
				.awaitLatest().map(org.reactfx.util.Try::get).subscribe(this::applyTaskResult);
        
        codeArea.replaceText(getMandelbrotSession().getSource());
        
        codeArea.setOnDragDropped(e -> e.getDragboard().getFiles().stream().findFirst().ifPresent(file -> loadDataFromFile(file)));
        
        codeArea.setOnDragOver(e -> Optional.of(e).filter(q -> q.getGestureSource() != codeArea
				&& q.getDragboard().hasFiles()).ifPresent(q -> q.acceptTransferModes(TransferMode.COPY_OR_MOVE)));

		getMandelbrotSession().addMandelbrotListener(new MandelbrotListener() {
			@Override
			public void dataChanged(MandelbrotSession session) {
				addDataToHistory(historyList);
				Platform.runLater(() -> codeArea.replaceText(getMandelbrotSession().getSource()));
			}
			
			@Override
			public void pointChanged(MandelbrotSession session, boolean continuous) {
				if (!continuous) {
					addDataToHistory(historyList);
				}
			}

			@Override
			public void viewChanged(MandelbrotSession session, boolean continuous) {
				if (!continuous) {
					addDataToHistory(historyList);
				}
			}

			@Override
			public void statusChanged(MandelbrotSession session) {
				statusPane.setMessage(session.getStatus());
			}

			@Override
			public void sourceChanged(MandelbrotSession session) {
				addDataToHistory(historyList);
			}

			@Override
			public void reportChanged(MandelbrotSession session) {
			}
		});

		session.addSessionListener(new SessionListener() {
			@Override
			public void terminate(Session session) {
				shutdown();
			}

			@Override
			public void sessionAdded(Session session, ExportSession exportSession) {
				jobsList.getItems().add(exportSession);
			}

			@Override
			public void sessionRemoved(Session session, ExportSession exportSession) {
				jobsList.getItems().remove(exportSession);
			}
		});

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue();
			sourceButtons.setPrefWidth(width);
			codePane.setPrefWidth(width * 0.7);
			sidePane.setPrefWidth(width * 0.3);
			statusPane.setPrefWidth(width);
			sourceButtons.setLayoutX(0);
			codePane.setLayoutX(0);
			sidePane.setLayoutX(width * 0.7);
			statusPane.setLayoutX(0);
        });

		heightProperty().addListener((observable, oldValue, newValue) -> {
			double height = newValue.doubleValue();
			sourceButtons.setPrefHeight(height * 0.07);
			codePane.setPrefHeight(height * 0.7);
			sidePane.setPrefHeight(height * 0.7);
			statusPane.setPrefHeight(height * 0.23);
			sourceButtons.setLayoutY(0);
			codePane.setLayoutY(height * 0.07);
			sidePane.setLayoutY(height * 0.07);
			statusPane.setLayoutY(height * 0.77);
        });

		sidePane.widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue();
			paramsPane.setPrefWidth(width);
			historyPane.setPrefWidth(width);
			exportJobsPane.setPrefWidth(width);
		});

		sidePane.heightProperty().addListener((observable, oldValue, newValue) -> {
			double height = newValue.doubleValue();
			paramsPane.setPrefHeight(height);
			historyPane.setPrefHeight(height);
			exportJobsPane.setPrefHeight(height);
		});

		exportJobsPane.widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue();
			jobsPane.setPrefWidth(width);
			exportPane.setPrefWidth(width);
			exportPane.setLayoutX(width);
		});

		exportJobsPane.heightProperty().addListener((observable, oldValue, newValue) -> {
			double height = newValue.doubleValue();
			jobsPane.setPrefHeight(height);
			exportPane.setPrefHeight(height);
		});

		textExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("MandelbrotTextUpdate", true, Thread.MIN_PRIORITY));

		historyExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("MandelbrotHistoryUpdate", true, Thread.MIN_PRIORITY));

		sessionsExecutor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("MandelbrotSessionsUpdate", true, Thread.MIN_PRIORITY));

		sessionsExecutor.scheduleWithFixedDelay(() -> Platform.runLater(() -> updateJobList(jobsList)), 500, 500, TimeUnit.MILLISECONDS);

		DefaultThreadFactory exportThreadFactory = new DefaultThreadFactory("MandelbrotImageExport", true, Thread.MIN_PRIORITY);
		exportExecutor = Executors.newSingleThreadExecutor(exportThreadFactory);

		addDataToHistory(historyList);
	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	private TranslateTransition createTranslateTransition(Node node) {
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(node);
		transition.setDuration(javafx.util.Duration.seconds(0.5));
		return transition;
	}

	private void showSidebar(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != 0) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void hideSidebar(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != ((Pane)transition.getNode()).getWidth()) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(((Pane)transition.getNode()).getWidth());
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void showStatus(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateY() != 0) {
			transition.setFromY(transition.getNode().getTranslateY());
			transition.setToY(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void hideStatus(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateY() != ((Pane)transition.getNode()).getHeight()) {
			transition.setFromY(transition.getNode().getTranslateY());
			transition.setToY(((Pane)transition.getNode()).getHeight());
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void showPanel(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != -((Pane)transition.getNode()).getWidth()) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(-((Pane)transition.getNode()).getWidth());
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void hidePanel(TranslateTransition transition, EventHandler<ActionEvent> handler) {
		transition.stop();
		if (transition.getNode().getTranslateX() != 0) {
			transition.setFromX(transition.getNode().getTranslateX());
			transition.setToX(0);
			transition.setOnFinished(handler);
			transition.play();
		}
	}

	private void shutdown() {
		List<ExecutorService> executors = Arrays.asList(sessionsExecutor, historyExecutor, textExecutor, exportExecutor);
		executors.forEach(executor -> executor.shutdownNow());
		executors.forEach(executor -> Block.create(ExecutorService.class).andThen(e -> await(e)).tryExecute(executor));
	}

	private void await(ExecutorService executor) throws InterruptedException {
		executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
	}

	private void updateButtons(List<Button> buttons, boolean disabled) {
		buttons.stream().forEach(button -> button.setDisable(disabled));
	}

	private void historyItemSelected(ListView<MandelbrotData> historyList) {
		int index = historyList.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			MandelbrotData data = historyList.getItems().get(index);
			noHistory = true;
			getMandelbrotSession().setData(data);
			noHistory = false;
		}
	}

	private Stream<ExportSession> selectedItems(ListView<ExportSession> jobsList) {
		return jobsList.getSelectionModel().getSelectedItems().stream();
	}

	private FileChooser showSaveFileChooser() {
		ensureFileChooser(".m");
		fileChooser.setTitle("Save");
		if (getMandelbrotSession().getCurrentFile() != null) {
            fileChooser.setInitialDirectory(getMandelbrotSession().getCurrentFile().getParentFile());
            fileChooser.setInitialFileName(getMandelbrotSession().getCurrentFile().getName());
        }
		return fileChooser;
	}

	private FileChooser showLoadFileChooser() {
		ensureFileChooser(".m");
		fileChooser.setTitle("Load");
		if (getMandelbrotSession().getCurrentFile() != null) {
            fileChooser.setInitialDirectory(getMandelbrotSession().getCurrentFile().getParentFile());
            fileChooser.setInitialFileName(getMandelbrotSession().getCurrentFile().getName());
        }
        return fileChooser;
	}

	private void saveDataToFile(File file) {
		try {
            getMandelbrotSession().setCurrentFile(file);
            MandelbrotDataStore service = new MandelbrotDataStore();
            MandelbrotData data = getMandelbrotSession().getDataAsCopy();
            logger.info(data.toString());
            service.saveToFile(getMandelbrotSession().getCurrentFile(), data);
        } catch (Exception x) {
            logger.warning("Cannot save file " + file.getAbsolutePath());
            //TODO display error
        }
	}

	private void loadDataFromFile(File file) {
		try {
            MandelbrotDataStore service = new MandelbrotDataStore();
			MandelbrotData data = service.loadFromFile(file);
            getMandelbrotSession().setCurrentFile(file);
            logger.info(data.toString());
            getMandelbrotSession().setData(data);
        } catch (Exception x) {
            logger.warning("Cannot read file " + file.getAbsolutePath());
            //TODO display error
        }
	}

	private ImageView createIconImage(String name) {
		InputStream stream = getClass().getResourceAsStream(name);
		ImageView image = new ImageView(new Image(stream));
		image.setSmooth(true);
		image.setFitWidth(32);
		image.setFitHeight(32);
		return image;
	}

	private void updateReportAndSource(String text, CompilerReport report) {
		Block.create(CompilerReport.class).andThen(r -> getMandelbrotSession().setReport(r)).tryExecute(report)
				.filter(r -> ((CompilerReport)r).getErrors().size() == 0).ifPresent(r -> getMandelbrotSession().setSource(text));
	}
	
	private CompilerReport generateReport(String text) throws Exception {
		return new Compiler().compileReport(text);
	}

	private void displayErrors() {
		List<CompilerError> errors = getMandelbrotSession().getReport().getErrors();
		if (errors.size() > 0) {
			Collections.sort(errors, (o1, o2) -> o2.getIndex() < o1.getIndex() ? -1 : 1);
			for (CompilerError error : errors) {
				logger.info(error.toString());
				if (error.getType() == CompilerError.ErrorType.M_COMPILER) {
					int lineEnd = (int)error.getIndex() + 1;
					int lineBegin = (int)error.getIndex();
					StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
					builder.add(Collections.singleton("error"), lineEnd - lineBegin);
					try {
						if (lineBegin < codeArea.getLength()) {
							codeArea.setStyleSpans(lineBegin, builder.create());
						} else {
							logger.info("begin " + lineBegin + ", length " + (lineEnd - lineBegin));
						}
					} catch (Exception e) {
						logger.info("begin " + lineBegin + ", length " + (lineEnd - lineBegin));
						logger.log(Level.WARNING, "Something is wrong", e);
					}
				}
			}
		}
	}

	private class TaskResult {
		private String source;
		private CompilerReport report;
		private StyleSpans<Collection<String>> highlighting;

		public TaskResult(String source, CompilerReport report, StyleSpans<Collection<String>> highlighting) {
			this.source = source;
			this.report = report;
			this.highlighting = highlighting;
		}
	}
	
	private Task<Optional<TaskResult>> computeTaskAsync() {
        String text = codeArea.getText();
        Task<Optional<TaskResult>> task = new Task<Optional<TaskResult>>() {
            @Override
            protected Optional<TaskResult> call() throws Exception {
				return Try.of(() -> new TaskResult(text, generateReport(text), computeHighlighting(text))).onFailure(e -> logger.log(Level.WARNING, "Cannot parse source", e)).value();
            }
        };
        textExecutor.execute(task);
        return task;
    }

	private void initHighlightingPattern() {
		String[] KEYWORDS = new String[] {
	        "fractal", "orbit", "color", "begin", "loop", "end", "rule", "trap", "palette", "if", "else", "stop", "init"
		};
		
		String[] FUNCTIONS = new String[] {
		        "re", "im", "mod", "pha", "log", "exp", "sqrt", "mod2", "abs", "ceil", "floor", "pow", "hypot", "atan2", "min", "max", "cos", "sin", "tan", "asin", "acos", "atan"
		};
		
		String[] PATHOP = new String[] {
		        "MOVETO", "MOVETOREL", "LINETO", "LINETOREL", "ARCTO", "ARCTOREL", "QUADTO", "QUADTOREL", "CURVETO", "CURVETOREL", "CLOSE"
		};
		
		String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
		String FUNCTION_PATTERN = "\\b(" + String.join("|", FUNCTIONS) + ")\\b";
		String PATHOP_PATTERN = "\\b(" + String.join("|", PATHOP) + ")\\b";
		String PAREN_PATTERN = "\\(|\\)";
		String BRACE_PATTERN = "\\{|\\}";
		String OPERATOR_PATTERN = "\\*|\\+|-|/|\\^|<|>|\\||&|=|#|;|\\[|\\]";
		
		highlightingPattern = Pattern.compile(
		        "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
		        + "|(?<FUNCTION>" + FUNCTION_PATTERN + ")"
		        + "|(?<PAREN>" + PAREN_PATTERN + ")"
		        + "|(?<BRACE>" + BRACE_PATTERN + ")"
		        + "|(?<OPERATOR>" + OPERATOR_PATTERN + ")"
		        + "|(?<PATHOP>" + PATHOP_PATTERN + ")"
		);
	}
	
    private void applyTaskResult(Optional<TaskResult> result) {
		result.ifPresent(value -> Block.create(TaskResult.class)
				.andThen(r -> compileOrbitAndColor(r.report))
				.andThen(r -> updateReportAndSource(r.source, r.report))
				.andThen(r -> codeArea.setStyleSpans(0, r.highlighting))
				.andThen(r -> displayErrors()).tryExecute(value));
    }

	private void compileOrbitAndColor(CompilerReport report) {
		Block.create(CompilerReport.class).andThen(this::compileOrbit).andThen(this::compileColor).tryExecute(report).ifFailure(e -> processCompilerErrors(report, e));
	}

	private void processCompilerErrors(CompilerReport report, Exception e) {
		if (e instanceof CompilerSourceException) {
			report.getErrors().addAll(((CompilerSourceException)e).getErrors());
		} else {
			logger.log(Level.WARNING, "Cannot compile fractal", e);
		}
	}

	private void compileOrbit(CompilerReport report) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, CompilerSourceException {
		Optional.of(new Compiler().compileOrbit(report)).filter(builder -> builder.getErrors().size() == 0).ifPresent(builder -> Try.of(() -> builder.build()).execute());
	}

	private void compileColor(CompilerReport report) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, CompilerSourceException {
		Optional.of(new Compiler().compileColor(report)).filter(builder -> builder.getErrors().size() == 0).ifPresent(builder -> Try.of(() -> builder.build()).execute());
	}

	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = highlightingPattern.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher
					.group("KEYWORD") != null ? "keyword" : matcher
					.group("FUNCTION") != null ? "function" : matcher
					.group("PAREN") != null ? "paren" : matcher
					.group("BRACE") != null ? "brace" : matcher
					.group("OPERATOR") != null ? "operator" : matcher
					.group("PATHOP") != null ? "pathop" : null;
			assert styleClass != null;
			spansBuilder.add(Collections.singleton("code"), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.singleton("code"), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

	private void addDataToHistory(ListView<MandelbrotData> historyList) {
		if (noHistory) {
			return;
		}
		historyExecutor.submit(Block.create(MandelbrotData.class)
				.andThen(data -> data.setPixels(generator.renderImage(data)))
				.andThen(data -> Platform.runLater(() -> historyAddItem(historyList, data)))
				.toCallable(getMandelbrotSession().getDataAsCopy()));
	}

	private void historyAddItem(ListView<MandelbrotData> historyList, MandelbrotData data) {
		historyList.getItems().add(0, data);
	}

	private void historyRemoveAllItems(ListView<MandelbrotData> historyList) {
		historyList.getItems().clear();
	}
	
	private MandelbrotSession getMandelbrotSession() {
		return (MandelbrotSession) session;
	}

	private void ensureFileChooser(String suffix) {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialFileName("mandel" + suffix);
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}

	private RendererTile createSingleTile(int width, int height) {
		RendererSize imageSize = new RendererSize(width, height);
		RendererSize tileSize = new RendererSize(width, height);
		RendererSize tileBorder = new RendererSize(0, 0);
		RendererPoint tileOffset = new RendererPoint(0, 0);
		return new RendererTile(imageSize, tileSize, tileOffset, tileBorder);
	}
	
	private void updateJobList(ListView<ExportSession> jobsList) {
		ObservableList<ExportSession> sessions = jobsList.getItems();
		for (int i = sessions.size() - 1; i >= 0; i--) {
			ExportSession session = sessions.get(i);
			if (session.isStopped()) {
				sessions.remove(i);
			} else {
				if (jobsList.getSelectionModel().isSelected(i)) {
					triggerUpdate(jobsList, session, i);
					jobsList.getSelectionModel().select(i);
				} else {
					triggerUpdate(jobsList, session, i);
				}
			}
		}
	}

	private <T> void triggerUpdate(ListView<T> listView, T newValue, int index) {
		listView.fireEvent(new ListView.EditEvent<>(listView, ListView.editCommitEvent(), newValue, index));
    }

	private static ServiceLoader<Encoder> loadPlugins() {
		return ServiceLoader.load(Encoder.class);
	}

	private static Stream<? extends Encoder> pluginsStream() {
		return StreamSupport.stream(loadPlugins().spliterator(), false);
	}

	private static <T> Try<T, Exception> selectPlugin(String pluginId, Function<Encoder, T> action) {
		return pluginsStream().filter(plugin -> pluginId.equals(plugin.getId())).findFirst()
				.map(plugin -> Try.of(() -> action.apply(plugin))).orElse(Try.failure(new Exception("Plugin not found")));
	}

	private Optional<Encoder> createEncoder(String format) {
		return selectPlugin(format, plugin -> plugin).onFailure(e -> logger.warning("Cannot find encoder for PNG format")).value();
	}

	private void doExportSession(RendererSize rendererSize) {
		createEncoder("PNG").ifPresent(encoder -> Block.create(Encoder.class)
				.andThen(e -> prepareExportFileChooser(e.getSuffix())).andThen(e -> selectFileAndExport(rendererSize, encoder)).tryExecute(encoder));
	}

	private void selectFileAndExport(RendererSize rendererSize, Encoder encoder) {
		Optional.ofNullable(fileChooser.showSaveDialog(MandelbrotEditorPane.this.getScene().getWindow())).ifPresent(file -> createExportSession(rendererSize, encoder, file));
	}

	private void createExportSession(RendererSize rendererSize, Encoder encoder, File file) {
		currentExportFile = file;
		exportExecutor.submit(Block.create(MandelbrotData.class).andThen(data -> data.setPixels(generator.renderImage(data)))
				.andThen(data -> Platform.runLater(() -> createExportSession(rendererSize, encoder, file, data))).toCallable(exportData));
	}

	private void prepareExportFileChooser(String suffix) {
		ensureFileChooser(suffix);
		fileChooser.setTitle("Export");
		if (currentExportFile != null) {
			fileChooser.setInitialDirectory(currentExportFile.getParentFile());
			fileChooser.setInitialFileName(currentExportFile.getName());
		}
	}

	private void createExportSession(RendererSize rendererSize, Encoder encoder, File file, MandelbrotData data) {
		try {
			File tmpFile = File.createTempFile("nextfractal-profile-", ".dat");
			ExportSession exportSession = new ExportSession("Mandelbrot", data, file, tmpFile, rendererSize, 200, encoder);
			logger.info("Export session created: " + exportSession.getSessionId());
			session.addExportSession(exportSession);
			session.getExportService().startSession(exportSession);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot export data to file " + file.getAbsolutePath(), e);
			//TODO display error
		}
	}
}
