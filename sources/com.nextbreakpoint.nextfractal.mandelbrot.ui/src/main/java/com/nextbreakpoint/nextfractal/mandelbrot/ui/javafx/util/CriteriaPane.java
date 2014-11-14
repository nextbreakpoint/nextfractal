package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class CriteriaPane extends BorderPane {

	public CriteriaPane(NodeObject nodeObject) {
		Label label = new Label(nodeObject.getNodeLabel());
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(nodeObject.getNodeValue().getValue()));
		textfield.setRestrict("\\d*");
		textfield.setOnAction(e -> {
			Integer value = Integer.parseInt(textfield.getText().length() > 0 ? textfield.getText() : "0");
			nodeObject.getNodeEditor().setNodeValue(nodeObject.getNodeEditor().createNodeValue(value));
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("criteria-pane");
//		element.addChangeListener(e -> {
//			Integer value = (Integer)e.getParams()[0] != null ? (Integer)e.getParams()[0] : 0;
//			if (textfield.getText().length() == 0 || Integer.parseInt(textfield.getText()) != value) {
//				textfield.setText(String.valueOf(value));
//			}
//		});
	}
}
