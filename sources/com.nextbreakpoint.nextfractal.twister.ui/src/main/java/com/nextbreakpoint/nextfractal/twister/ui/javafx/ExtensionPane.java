package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeEvent;
import com.nextbreakpoint.nextfractal.core.config.ValueChangeListener;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;

public class ExtensionPane<T extends ExtensionConfig> extends BorderPane {

	public ExtensionPane(ConfigurableExtensionReferenceElement<T> extensionElement) {
		Label label = new Label(getExtensionName(extensionElement));
		Button button = new Button("?");
		setCenter(label);
		getChildren().add(button);
		BorderPane.setAlignment(button, Pos.CENTER_RIGHT);
		extensionElement.addChangeListener(new ValueChangeListener() {
			@Override
			public void valueChanged(ValueChangeEvent e) {
				label.setText(getExtensionName(extensionElement));
			}
		});
	}

	protected String getExtensionName(ConfigurableExtensionReferenceElement<T> extensionElement) {
		if (extensionElement.getReference() != null) {
			return extensionElement.getReference().getExtensionName();
		}
		return "?";
	}
}
