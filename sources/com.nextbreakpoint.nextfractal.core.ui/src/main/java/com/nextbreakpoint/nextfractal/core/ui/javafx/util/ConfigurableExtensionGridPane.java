package com.nextbreakpoint.nextfractal.core.ui.javafx.util;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.common.ConfigurableExtensionReferenceElement;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.extension.ExtensionException;

public class ConfigurableExtensionGridPane<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends BorderPane {
	private static final Logger logger = Logger.getLogger(ConfigurableExtensionGridPane.class.getName());
	private EventHandler<ActionEvent> onAction;
	private Pane container = new Pane();

	public ConfigurableExtensionGridPane(ConfigurableExtensionReferenceElement<? extends V> extensionElement, ConfigurableExtensionRegistry<T, V> registry, Dimension2D size) {
		// TODO Auto-generated constructor stub
		setWidth(size.getWidth());
		setHeight(size.getHeight());
		setCenter(new ScrollPane(container));
		for (ConfigurableExtension<T, V> extension : registry.getConfigurableExtensionList()) {
			try {
				GridItem item = createItem(extension.createConfigurableExtensionReference(extension.createDefaultExtensionConfig()));
				container.getChildren().add(item);
				logger.info("Created extension reference: " + item.getReference().getExtensionId());
			} catch (ExtensionException e) {
				logger.warning("Cannot create extension reference: " + e.getMessage());
			}
		}
		doLayout();
	}

	public EventHandler<ActionEvent> getOnAction() {
		return onAction;
	}

	public void setOnAction(EventHandler<ActionEvent> onAction) {
		this.onAction = onAction;
	}

	private int getCellCount(double width, double size) {
		return (int)Math.floor(width / (size + 10));
	}
	
	private void doLayout() {
		int cells = getCellCount(getWidth(), 200);
		for (int i = 0; i < container.getChildren().size(); i++) {
			Node child = container.getChildren().get(i);
			child.setLayoutX((i % cells) * (200 + 10));
			child.setLayoutY((i / cells) * (200 + 10));
		}
	}
	
	private void setCellSize(Pane node) {
		node.setPrefWidth(getMinHeight());
		node.setPrefHeight(getMinHeight());
		node.setMinWidth(getMinHeight());
		node.setMinHeight(getMinHeight());
		node.setMaxWidth(getMinHeight());
		node.setMaxHeight(getMinHeight());
	}

	private GridItem createItem(ConfigurableExtensionReference<V> reference) {
		GridItem item = new GridItem(reference);
		setCellSize(item);
		return item;
	}

	private class GridItem extends BorderPane {
		private ConfigurableExtensionReference<V> reference;
		
		public GridItem(ConfigurableExtensionReference<V> reference) {
			this.reference = reference;
			setId("grid-item");
			Label label = new Label(getName());
			label.setAlignment(Pos.CENTER);
			setCenter(label);
		}

		public String getName() {
			String name = reference.getExtensionName();
			if (name != null) {
				Pattern pattern = Pattern.compile("([A-Z])[a-z ]*", 0);
				Matcher matcher = pattern.matcher(name);
				StringBuilder builder = new StringBuilder();
				while (matcher.find()) {
					builder.append(matcher.group(1));
				}
				return builder.toString();
			}
			return "?";
		}

		public ConfigurableExtensionReference<V> getReference() {
			return reference;
		}
	}
}
