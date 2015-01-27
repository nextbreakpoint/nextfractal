package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.service.FileService;


public class MandelbrotEditorPane extends BorderPane {
	private final FractalSession session;
	private FileChooser fileChooser;
	private File currentFile;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		TextArea textarea = new TextArea();
		setCenter(textarea);
		HBox buttons = new HBox(10);
		Button renderButton = new Button("Render");
		Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");
		buttons.getChildren().add(renderButton);
		buttons.getChildren().add(loadButton);
		buttons.getChildren().add(saveButton);
		setBottom(buttons);
		textarea.getStyleClass().add("source-pane");
		buttons.getStyleClass().add("actions-pane");
		getStyleClass().add("mandelbrot");
		textarea.setText(((MandelbrotData)session.getData()).getSource());
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void dataChanged(FractalSession session) {
				textarea.setText(((MandelbrotData)session.getData()).getSource());
			}

			@Override
			public void terminate(FractalSession session) {
			}
		});
		renderButton.setOnAction(e -> {
			((MandelbrotData)session.getData()).setSource(textarea.getText());
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
					session.setData(data);
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
					MandelbrotData data = (MandelbrotData) session.getData();
					service.saveToFile(currentFile, data);
				} catch (Exception x) {
					//TODO show error
				}
			}
		});
	}

	private void createFileChooser() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
	}
}
