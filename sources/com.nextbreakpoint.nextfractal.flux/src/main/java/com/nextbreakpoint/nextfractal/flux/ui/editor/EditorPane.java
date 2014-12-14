package com.nextbreakpoint.nextfractal.flux.ui.editor;

import javafx.geometry.Dimension2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EditorPane extends Pane {
	public EditorPane(Dimension2D size) {
		VBox vbox = new VBox(20);
		TextArea orbitTextarea = new TextArea();
		TextArea colorTextarea = new TextArea();
		orbitTextarea.setPrefWidth(size.getWidth());
		colorTextarea.setPrefWidth(size.getWidth());
		vbox.getChildren().add(new ScrollPane(orbitTextarea));
		vbox.getChildren().add(new ScrollPane(colorTextarea));
		getChildren().add(vbox);
	}
}
