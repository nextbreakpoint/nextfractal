package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;

public class ConfigurableExtensionGridPane<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends Pane {
	private EventHandler<ActionEvent> onAction;

	public ConfigurableExtensionGridPane(ConfigurableExtensionReferenceElement<? extends V> extensionElement, ConfigurableExtensionRegistry<T, V> registry) {
		// TODO Auto-generated constructor stub
	}

	public EventHandler<ActionEvent> getOnAction() {
		return onAction;
	}

	public void setOnAction(EventHandler<ActionEvent> onAction) {
		this.onAction = onAction;
	}
}
