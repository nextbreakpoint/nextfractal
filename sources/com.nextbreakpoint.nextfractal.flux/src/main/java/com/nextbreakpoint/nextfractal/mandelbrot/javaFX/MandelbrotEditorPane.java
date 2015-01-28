package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;
import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;

public class MandelbrotEditorPane extends BorderPane {
	private static final Logger logger = Logger.getLogger(MandelbrotEditorPane.class.getName());
	private final FractalSession session;
	private FileChooser fileChooser;
	private File currentFile;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		
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
		Pane historyList = new Pane();
		historyList.getStyleClass().add("history-pane");
		HBox historyButtons = new HBox(10);
		Button clearButton = new Button("Clear");
		historyButtons.getChildren().add(clearButton);
		historyButtons.getStyleClass().add("actions-pane");
		historyPane.setCenter(historyList);
		historyPane.setBottom(historyButtons);
		historyTab.setContent(historyPane);

		BorderPane libraryPane = new BorderPane();
		Pane libraryList = new Pane();
		libraryList.getStyleClass().add("library-pane");
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
		Pane jobsList = new Pane();
		jobsList.getStyleClass().add("jobs-pane");
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
			}
			
			@Override
			public void viewChanged(FractalSession session, boolean zoom) {
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
			fileChooser.setTitle("Load");
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
}
