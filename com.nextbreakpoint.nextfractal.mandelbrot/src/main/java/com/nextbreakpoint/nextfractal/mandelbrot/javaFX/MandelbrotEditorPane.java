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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.utils.Block;
import javafx.application.Platform;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import com.nextbreakpoint.nextfractal.core.export.ExportSession;
import com.nextbreakpoint.nextfractal.core.renderer.RendererPoint;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import com.nextbreakpoint.nextfractal.core.renderer.RendererTile;
import com.nextbreakpoint.nextfractal.core.renderer.javaFX.JavaFXRendererFactory;
import com.nextbreakpoint.nextfractal.core.session.Session;
import com.nextbreakpoint.nextfractal.core.session.SessionListener;
import com.nextbreakpoint.nextfractal.core.utils.DefaultThreadFactory;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotImageGenerator;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.Compiler;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerError;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerReport;
import com.nextbreakpoint.nextfractal.mandelbrot.compiler.CompilerSourceException;

public class MandelbrotEditorPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotEditorPane.class.getName());
	private final MandelbrotImageGenerator generator;
	private final ScheduledExecutorService sessionsExecutor;
	private final ExecutorService historyExecutor;
	private final ExecutorService textExecutor;
	private final Session session;
	private final CodeArea codeArea;
	private Pattern highlightingPattern;
	private FileChooser fileChooser;
	private volatile boolean noHistory;

	public MandelbrotEditorPane(Session session) {
		this.session = session;

		RendererTile generatorTile = createSingleTile(50, 50);
		
		DefaultThreadFactory generatorThreadFactory = new DefaultThreadFactory("MandelbrotHistoryImageGenerator", true, Thread.MIN_PRIORITY);
		generator = new MandelbrotImageGenerator(generatorThreadFactory, new JavaFXRendererFactory(), generatorTile, true);
		
		getStyleClass().add("mandelbrot");

		codeArea = new CodeArea();
		codeArea.getStyleClass().add("source");

		TabPane tabPane = new TabPane();
		Tab sourceTab = new Tab();
		sourceTab.setClosable(false);
		sourceTab.setText("Source");
		sourceTab.setTooltip(new Tooltip("Source code of current fractal"));
		tabPane.getTabs().add(sourceTab);
		Tab paramsTab = new Tab();
		paramsTab.setClosable(false);
		paramsTab.setText("Parameters");
		paramsTab.setTooltip(new Tooltip("Parameters of current fractal"));
		tabPane.getTabs().add(paramsTab);
		Tab historyTab = new Tab();
		historyTab.setClosable(false);
		historyTab.setText("History");
		historyTab.setTooltip(new Tooltip("History of generated fractals"));
		tabPane.getTabs().add(historyTab);
		Tab jobsTab = new Tab();
		jobsTab.setClosable(false);
		jobsTab.setText("Export");
		jobsTab.setTooltip(new Tooltip("List of active export tasks"));
		tabPane.getTabs().add(jobsTab);
		setCenter(tabPane);

		BorderPane sourcePane = new BorderPane();
		BorderPane sourceButtons = new BorderPane();
		HBox sourceButtonsLeft = new HBox();
		HBox sourceButtonsRight = new HBox();
		Button browseButton = new Button("", createIconImage("/icon-folder.png"));
		Button renderButton = new Button("", createIconImage("/icon-run.png"));
		Button loadButton = new Button("", createIconImage("/icon-load.png"));
		Button saveButton = new Button("", createIconImage("/icon-save.png"));
		browseButton.setTooltip(new Tooltip("Browse fractals"));
		renderButton.setTooltip(new Tooltip("Render fractal"));
		loadButton.setTooltip(new Tooltip("Load fractal from file"));
		saveButton.setTooltip(new Tooltip("Save fractal to file"));
		sourceButtonsLeft.getChildren().add(browseButton);
		sourceButtonsLeft.getChildren().add(renderButton);
		sourceButtonsRight.getChildren().add(loadButton);
		sourceButtonsRight.getChildren().add(saveButton);
		sourceButtons.setLeft(sourceButtonsLeft);
		sourceButtons.setRight(sourceButtonsRight);
		sourceButtons.getStyleClass().add("menubar");
		sourcePane.setCenter(codeArea);
		sourcePane.setTop(sourceButtons);
		sourceTab.setContent(sourcePane);
		
		BorderPane historyPane = new BorderPane();
		ListView<MandelbrotData> historyList = new ListView<>();
		historyList.setFixedCellSize(60);
		historyList.getStyleClass().add("history");
		historyList.setCellFactory(listView -> new HistoryListCell(generator.getSize(), generatorTile));
		BorderPane historyButtons = new BorderPane();
		Button clearButton = new Button("", createIconImage("/icon-clear.png"));
		clearButton.setTooltip(new Tooltip("Remove all elements"));
		historyButtons.setRight(clearButton);
		historyButtons.getStyleClass().add("menubar");
		historyPane.setCenter(historyList);
		historyPane.setTop(historyButtons);
		historyTab.setContent(historyPane);

		BorderPane jobsPane = new BorderPane();
		ListView<ExportSession> jobsList = new ListView<>();
		jobsList.setCellFactory(listView -> new ExportListCell(generator.getSize(), generatorTile));
		jobsList.setFixedCellSize(60);
		jobsList.getStyleClass().add("jobs");
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
		jobsPane.setTop(jobsButtons);
		jobsTab.setContent(jobsPane);

		BorderPane paramsPane = new BorderPane();
		BorderPane paramsButtons = new BorderPane();
		MandelbrotParamsPane paramsPanel = new MandelbrotParamsPane(getMandelbrotSession()); 
		HBox paramsButtonsLeft = new HBox();
		HBox paramsButtonsRight = new HBox();
		Button paramsBrowseButton = new Button("", createIconImage("/icon-folder.png"));
		Button paramsRenderButton = new Button("", createIconImage("/icon-run.png"));
		Button paramsLoadButton = new Button("", createIconImage("/icon-load.png"));
		Button paramsSaveButton = new Button("", createIconImage("/icon-save.png"));
		paramsBrowseButton.setTooltip(new Tooltip("Browse fractals"));
		paramsRenderButton.setTooltip(new Tooltip("Render fractal"));
		paramsLoadButton.setTooltip(new Tooltip("Load fractal from file"));
		paramsSaveButton.setTooltip(new Tooltip("Save fractal to file"));
		paramsButtonsLeft.getChildren().add(paramsBrowseButton);
		paramsButtonsLeft.getChildren().add(paramsRenderButton);
		paramsButtonsRight.getChildren().add(paramsLoadButton);
		paramsButtonsRight.getChildren().add(paramsSaveButton);
		paramsButtons.setLeft(paramsButtonsLeft);
		paramsButtons.setRight(paramsButtonsRight);
		paramsButtons.getStyleClass().add("menubar");
		paramsPane.setCenter(paramsPanel);
		paramsPane.setTop(paramsButtons);
		paramsTab.setContent(paramsPane);
		
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
				tabPane.getSelectionModel().select(jobsTab);
			}

			@Override
			public void sessionRemoved(Session session, ExportSession exportSession) {
				jobsList.getItems().remove(exportSession);
			}

			@Override
			public void doExportAsImage(Session session) {
			}

			@Override
			public void showBrowser(Session session) {
			}
		});
		
		List<Button> buttons = Arrays.asList(suspendButton, resumeButton, removeButton);

		jobsList.getSelectionModel().getSelectedItems().addListener((Change<? extends ExportSession> c) -> updateButtons(buttons, c.getList().size() == 0));
		
		EventHandler<ActionEvent> renderEventHandler = e -> Platform.runLater(() -> codeArea.replaceText(getMandelbrotSession().getSource()));
		
		renderButton.setOnAction(renderEventHandler);
		paramsRenderButton.setOnAction(renderEventHandler);
		
		EventHandler<ActionEvent> loadEventHandler = e -> Optional.ofNullable(showLoadFileChooser())
				.map(fileChooser -> fileChooser.showOpenDialog(MandelbrotEditorPane.this.getScene().getWindow())).ifPresent(file -> loadDataFromFile(file));

		loadButton.setOnAction(loadEventHandler);
		paramsLoadButton.setOnAction(loadEventHandler);
		
		EventHandler<ActionEvent> saveEventHandler = e -> Optional.ofNullable(showSaveFileChooser())
				.map(fileChooser -> fileChooser.showSaveDialog(MandelbrotEditorPane.this.getScene().getWindow())).ifPresent(file -> saveDataToFile(file));
		
		saveButton.setOnAction(saveEventHandler);
		paramsSaveButton.setOnAction(saveEventHandler);

		exportButton.setOnAction(e -> getMandelbrotSession().doExportAsImage());

		browseButton.setOnAction(e -> getMandelbrotSession().showBrowser());
		
		paramsBrowseButton.setOnAction(e -> getMandelbrotSession().showBrowser());
		
		suspendButton.setOnAction(e -> selectedItems(jobsList)
				.filter(exportSession -> !exportSession.isSuspended()).forEach(exportSession -> session.getExportService().suspendSession(exportSession)));

		resumeButton.setOnAction(e -> selectedItems(jobsList)
				.filter(exportSession -> exportSession.isSuspended()).forEach(exportSession -> session.getExportService().resumeSession(exportSession)));

		removeButton.setOnAction(e -> selectedItems(jobsList).forEach(exportSession -> session.getExportService().stopSession(exportSession)));

		clearButton.setOnAction(e -> Block.create((Block<ListView<MandelbrotData>,Exception>)(list -> logger.info("Clear history")))
				.andThen(list -> historyRemoveAllItems(list)).andThen(list -> addDataToHistory(list)).tryExecute(historyList));

		historyList.getSelectionModel().getSelectedItems().addListener((Change<? extends MandelbrotData> c) -> historyItemSelected(historyList));

		textExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("MandelbrotTextUpdate", true, Thread.MIN_PRIORITY));

		historyExecutor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("MandelbrotHistoryUpdate", true, Thread.MIN_PRIORITY));

		sessionsExecutor = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("MandelbrotSessionsUpdate", true, Thread.MIN_PRIORITY));

		sessionsExecutor.scheduleWithFixedDelay(() -> Platform.runLater(() -> updateJobList(jobsList)), 500, 500, TimeUnit.MILLISECONDS);
		
		addDataToHistory(historyList);
	}

	@Override
	protected void finalize() throws Throwable {
		shutdown();
		super.finalize();
	}

	private void shutdown() {
		List<ExecutorService> executors = Arrays.asList(sessionsExecutor, historyExecutor, textExecutor);
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
		MandelbrotData data = historyList.getItems().get(index);
		noHistory = true;
		getMandelbrotSession().setData(data);
		noHistory = false;
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
            getMandelbrotSession().setCurrentFile(file);
            MandelbrotDataStore service = new MandelbrotDataStore();
            MandelbrotData data = service.loadFromFile(getMandelbrotSession().getCurrentFile());
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
					StyleSpansBuilder<Collection<String>> driver = new StyleSpansBuilder<>();
					driver.add(Collections.singleton("error"), lineEnd - lineBegin);
					try {
						if (lineBegin < codeArea.getLength()) {
							codeArea.setStyleSpans(lineBegin, driver.create());
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
}
