package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;

public class StringPane extends BorderPane {

	public StringPane(NodeObject nodeObject) {
		Label label = new Label(nodeObject.getNodeLabel());
		TextField textfield = new TextField();
		textfield.setText(String.valueOf(nodeObject.getNodeValue().getValue()));
		textfield.setOnAction(e -> {
			nodeObject.getNodeEditor().setNodeValue(nodeObject.getNodeEditor().createNodeValue(textfield.getText()));
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("string-pane");
//		element.addChangeListener(e -> {
//			String value = (String)e.getParams()[0] != null ? (String)e.getParams()[0] : "";
//			if (!textfield.getText().equals(value)) {
//				textfield.setText(value);
//			}
//		});
	}
}
