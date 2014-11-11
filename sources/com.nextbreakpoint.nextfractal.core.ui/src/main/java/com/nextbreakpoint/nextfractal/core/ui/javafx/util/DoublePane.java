package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.DoubleElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class DoublePane extends BorderPane {

	public DoublePane(String title, DoubleElement element) {
		Label label = new Label(title);
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(element.getValue()));
		textfield.setRestrict("-?\\d*\\.?\\d*");
		textfield.setOnAction(e -> {
			Double value = Double.parseDouble(textfield.getText().length() > 0 ? textfield.getText() : "0");
			element.setValue(value);
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("double-pane");
		element.addChangeListener(e -> {
			Double value = (Double)e.getParams()[0] != null ? (Double)e.getParams()[0] : 0;
			if (textfield.getText().length() == 0 || Double.parseDouble(textfield.getText()) != value) {
				textfield.setText(String.valueOf(value));
			}
		});
	}
}
