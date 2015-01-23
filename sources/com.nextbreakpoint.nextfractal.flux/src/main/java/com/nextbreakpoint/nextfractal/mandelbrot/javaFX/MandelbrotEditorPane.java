package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import com.nextbreakpoint.nextfractal.FractalSession;
import com.nextbreakpoint.nextfractal.FractalSessionListener;

public class MandelbrotEditorPane extends BorderPane {
	private final FractalSession session;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		TextArea textarea = new TextArea();
		setCenter(textarea);
		HBox buttons = new HBox(10);
		Button renderButton = new Button("Render");
		Button exportButton = new Button("Export");
		Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");
		buttons.getChildren().add(renderButton);
		buttons.getChildren().add(exportButton);
		buttons.getChildren().add(loadButton);
		buttons.getChildren().add(saveButton);
		setBottom(buttons);
		textarea.getStyleClass().add("source-pane");
		buttons.getStyleClass().add("actions-pane");
		getStyleClass().add("mandelbrot");
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void sourceChanged(FractalSession session) {
				textarea.setText(session.getSource());
			}

			@Override
			public void terminate(FractalSession session) {
			}
		});
		renderButton.setOnAction(e -> {
			session.setSource(textarea.getText());
		});
		exportButton.setOnAction(e -> {
			//TODO export
		});
		loadButton.setOnAction(e -> {
			//TODO load
		});
		saveButton.setOnAction(e -> {
			//TODO save
		});
	}
}
