package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.mandelbrot.elements.CriteriaElement;

public class CriteriaPane extends BorderPane {

	public CriteriaPane(String title, CriteriaElement element) {
		Label label = new Label(title);
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(element.getValue()));
		textfield.setRestrict("\\d*");
		textfield.setOnAction(e -> {
			Integer value = Integer.parseInt(textfield.getText().length() > 0 ? textfield.getText() : "0");
			element.setValue(value);
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("criteria-pane");
		element.addChangeListener(e -> {
			Integer value = (Integer)e.getParams()[0] != null ? (Integer)e.getParams()[0] : 0;
			if (textfield.getText().length() == 0 || Integer.parseInt(textfield.getText()) != value) {
				textfield.setText(String.valueOf(value));
			}
		});
	}
}
