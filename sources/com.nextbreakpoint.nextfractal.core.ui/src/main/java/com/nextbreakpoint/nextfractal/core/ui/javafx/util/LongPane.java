package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.LongElement;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class LongPane extends BorderPane {

	public LongPane(String title, LongElement element) {
		Label label = new Label(title);
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(element.getValue()));
		textfield.setRestrict("-?\\d*");
		textfield.setOnAction(e -> {
			Long value = Long.parseLong(textfield.getText().length() > 0 ? textfield.getText() : "0");
			element.setValue(value);
		});
		setLeft(label);
		setRight(textfield);
		setId("long-pane");
		element.addChangeListener(e -> {
			Long value = (Long)e.getParams()[0] != null ? (Long)e.getParams()[0] : 0;
			if (textfield.getText().length() == 0 || Long.parseLong(textfield.getText()) != value) {
				textfield.setText(String.valueOf(value));
			}
		});
	}
}
