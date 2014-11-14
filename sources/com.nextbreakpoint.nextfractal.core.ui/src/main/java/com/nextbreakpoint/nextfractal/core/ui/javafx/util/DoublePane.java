package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class DoublePane extends BorderPane {

	public DoublePane(NodeObject nodeObject) {
		Label label = new Label(nodeObject.getNodeLabel());
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(nodeObject.getNodeValue().getValue()));
		textfield.setRestrict("-?\\d*\\.?\\d*");
		textfield.setOnAction(e -> {
			Double value = Double.parseDouble(textfield.getText().length() > 0 ? textfield.getText() : "0");
			nodeObject.getNodeEditor().setNodeValue(nodeObject.getNodeEditor().createNodeValue(value));
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("double-pane");
//		element.addChangeListener(e -> {
//			Double value = (Double)e.getParams()[0] != null ? (Double)e.getParams()[0] : 0;
//			if (textfield.getText().length() == 0 || Double.parseDouble(textfield.getText()) != value) {
//				textfield.setText(String.valueOf(value));
//			}
//		});
	}
}
