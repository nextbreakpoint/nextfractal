package com.nextbreakpoint.nextfractal.flux.mandelbrot.ui;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.flux.FractalSession;
import com.nextbreakpoint.nextfractal.flux.FractalSessionListener;

public class MandelbrotEditorPane extends BorderPane {
	private final FractalSession session;
	
	public MandelbrotEditorPane(FractalSession session) {
		this.session = session;
		TextArea textarea = new TextArea();
		setCenter(textarea);
		session.addSessionListener(new FractalSessionListener() {
			@Override
			public void sourceChanged(FractalSession session) {
				textarea.setText(session.getSource());
			}

			@Override
			public void terminate(FractalSession session) {
			}
		});
	}
}
