package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.StringElement;

public class StringPane extends BorderPane {

	public StringPane(String title, StringElement element) {
		Label label = new Label(title);
		TextField textfield = new TextField();
		textfield.setText(element.getValue());
		textfield.setOnAction(e -> {
			element.setValue(textfield.getText());
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("string-pane");
		element.addChangeListener(e -> {
			String value = (String)e.getParams()[0] != null ? (String)e.getParams()[0] : "";
			if (!textfield.getText().equals(value)) {
				textfield.setText(value);
			}
		});
	}
}
