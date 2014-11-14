package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.elements.BooleanElement;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;

public class BooleanPane extends BorderPane {

	public BooleanPane(NodeObject nodeObject) {
		Label label = new Label(nodeObject.getNodeLabel());
		CheckBox checkbox = new CheckBox();
		if (nodeObject.getNodeClass().equals(BooleanElement.CLASS_ID)) {
			checkbox.setSelected((Boolean)nodeObject.getNodeValue().getValue());
			checkbox.setOnAction(e -> {
				nodeObject.getNodeEditor().setNodeValue(nodeObject.getNodeEditor().createNodeValue(checkbox.isSelected()));
//				if (!new Boolean(checkbox.isSelected()).equals((Boolean)nodeObject.getNodeValue().getValue())) {
//					checkbox.setSelected((Boolean)nodeObject.getNodeValue().getValue());
//				}
			});
		}
		setLeft(label);
		setRight(checkbox);
		getStyleClass().add("boolean-pane");
//		element.addChangeListener(e -> {
//			boolean value = (Boolean)e.getParams()[0] != null ? (Boolean)e.getParams()[0] : false;
//			if (checkbox.isSelected() != value) {
//				checkbox.setSelected(value);
//			}
//		});
	}
}
