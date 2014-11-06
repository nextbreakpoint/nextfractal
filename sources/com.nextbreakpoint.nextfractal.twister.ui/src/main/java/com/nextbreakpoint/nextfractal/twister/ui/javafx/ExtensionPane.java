package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;

public class ExtensionPane<T extends ExtensionConfig> extends BorderPane {

	public ExtensionPane(ConfigurableExtensionReferenceElement<T> element) {
		Label label = new Label(getExtensionName(element));
		Button button = new Button("?");
		setLeft(label);
		setRight(button);
		setId("extension-pane");
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
}
