package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.BooleanElement;

public class BooleanPane extends BorderPane {

	public BooleanPane(String title, BooleanElement element) {
		Label label = new Label(title);
		CheckBox checkbox = new CheckBox();
		setLeft(label);
		setRight(checkbox);
		setId("boolean-pane");
	}
}
