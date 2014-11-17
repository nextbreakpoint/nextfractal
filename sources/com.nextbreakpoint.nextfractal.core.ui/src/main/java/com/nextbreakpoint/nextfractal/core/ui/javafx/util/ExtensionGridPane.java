package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;

public class ExtensionGridPane<T extends ExtensionRuntime> extends Pane {
	private EventHandler<ActionEvent> onAction;

	public ExtensionGridPane(NodeObject nodeObject, ExtensionRegistry<T> registry) {
		// TODO Auto-generated constructor stub
	}

	public EventHandler<ActionEvent> getOnAction() {
		return onAction;
	}

	public void setOnAction(EventHandler<ActionEvent> onAction) {
		this.onAction = onAction;
	}
}
