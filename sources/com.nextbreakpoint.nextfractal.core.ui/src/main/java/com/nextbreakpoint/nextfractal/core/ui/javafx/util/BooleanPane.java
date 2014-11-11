package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;

public class BooleanPane extends BorderPane {

	public BooleanPane(String title, BooleanElement element) {
		Label label = new Label(title);
		CheckBox checkbox = new CheckBox();
		checkbox.setSelected(element.getValue());
		checkbox.setOnAction(e -> {
			element.setValue(checkbox.isSelected());
		});
		setLeft(label);
		setRight(checkbox);
		getStyleClass().add("boolean-pane");
		element.addChangeListener(e -> {
			boolean value = (Boolean)e.getParams()[0] != null ? (Boolean)e.getParams()[0] : false;
			if (checkbox.isSelected() != value) {
				checkbox.setSelected(value);
			}
		});
	}
}
