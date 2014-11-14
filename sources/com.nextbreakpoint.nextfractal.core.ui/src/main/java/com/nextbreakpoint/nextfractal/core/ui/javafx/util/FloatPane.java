package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.AdvancedTextField;

public class FloatPane extends BorderPane {

	public FloatPane(NodeObject nodeObject) {
		Label label = new Label(nodeObject.getNodeLabel());
		AdvancedTextField textfield = new AdvancedTextField();
		textfield.setText(String.valueOf(nodeObject.getNodeValue().getValue()));
		textfield.setRestrict("-?\\d*\\.?\\d*");
		textfield.setOnAction(e -> {
			Float value = Float.parseFloat(textfield.getText().length() > 0 ? textfield.getText() : "0");
			nodeObject.getNodeEditor().setNodeValue(nodeObject.getNodeEditor().createNodeValue(value));
		});
		setLeft(label);
		setRight(textfield);
		getStyleClass().add("float-pane");
//		element.addChangeListener(e -> {
//			Float value = (Float)e.getParams()[0] != null ? (Float)e.getParams()[0] : 0;
//			if (textfield.getText().length() == 0 || Float.parseFloat(textfield.getText()) != value) {
//				textfield.setText(String.valueOf(value));
//			}
//		});
	}
}
