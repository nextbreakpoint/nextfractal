package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.ShortElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class ShortPane extends BorderPane {

	public ShortPane(String title, ShortElement element) {
		Label label = new Label(title);
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(element.getValue()));
		textfield.setRestrict("-?\\d*");
		textfield.setOnAction(e -> {
			Short value = Short.parseShort(textfield.getText().length() > 0 ? textfield.getText() : "0");
			element.setValue(value);
		});
		setLeft(label);
		setRight(textfield);
		setId("short-pane");
		element.addChangeListener(e -> {
			Short value = (Short)e.getParams()[0] != null ? (Short)e.getParams()[0] : 0;
			if (textfield.getText().length() == 0 || Short.parseShort(textfield.getText()) != value) {
				textfield.setText(String.valueOf(value));
			}
		});
	}
}
