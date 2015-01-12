package com.nextbreakpoint.nextfractal.flux.mandelbrot.javaFX;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.FractalSessionListener;

public class MandelbrotEditorPane extends BorderPane {
	private final FractalSession session;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		TextArea textarea = new TextArea();
		setCenter(textarea);
		HBox buttonsBox = new HBox(10);
		Button renderButton = new Button("Render");
		buttonsBox.getChildren().add(renderButton);
		setBottom(buttonsBox);
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
	}
}
