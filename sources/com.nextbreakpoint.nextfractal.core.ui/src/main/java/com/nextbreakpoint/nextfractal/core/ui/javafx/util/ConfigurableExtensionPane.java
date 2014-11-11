package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.elements.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;

public class ConfigurableExtensionPane<T extends ExtensionConfig> extends BorderPane {
	private EventHandler<ActionEvent> onAction;
	
	public ConfigurableExtensionPane(ConfigurableExtensionReferenceElement<T> element) {
		Label label = new Label(getExtensionName(element));
		Button button = new Button("?");
		button.setOnAction(e -> {
			if (onAction != null) {
				onAction.handle(new ActionEvent(ConfigurableExtensionPane.this, null));
			}
		});
		setLeft(label);
		setRight(button);
		getStyleClass().add("extension-pane");
		element.addChangeListener(e -> {
			label.setText(getExtensionName(element));
		});
	}

	protected String getExtensionName(ConfigurableExtensionReferenceElement<T> element) {
		if (element.getReference() != null) {
			return element.getReference().getExtensionName();
		}
		return "?";
	}

	public EventHandler<ActionEvent> getOnAction() {
		return onAction;
	}

	public void setOnAction(EventHandler<ActionEvent> onAction) {
		this.onAction = onAction;
	}
}
