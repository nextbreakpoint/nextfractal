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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtension;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionReference;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRegistry;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ConfigurableExtensionRuntime;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionConfig;
import com.nextbreakpoint.nextfractal.core.runtime.extension.ExtensionException;
import com.nextbreakpoint.nextfractal.core.runtime.tree.NodeObject;
import com.nextbreakpoint.nextfractal.core.ui.javafx.Disposable;

public class ConfigurableExtensionGridPane<T extends ConfigurableExtensionRuntime<? extends V>, V extends ExtensionConfig> extends BorderPane implements Disposable {
	private static final Logger logger = Logger.getLogger(ConfigurableExtensionGridPane.class.getName());
	private EventHandler<ActionEvent> onAction;
	private Pane container = new Pane();
	private int cellsPerRow = 3;
	private double cellSize = 0;

	public ConfigurableExtensionGridPane(NodeObject nodeObject, ConfigurableExtensionRegistry<T, V> registry, Dimension2D size) {
		// TODO Auto-generated constructor stub
		getStyleClass().add("extension-grid-pane");
		setWidth(size.getWidth());
		setHeight(size.getHeight());
		ScrollPane scrollPane = new ScrollPane(container);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		setCenter(scrollPane);
		cellSize = Math.floor(getWidth() / cellsPerRow);
		for (ConfigurableExtension<T, V> extension : registry.getConfigurableExtensionList()) {
			try {
				Pane item = createItem(extension.createConfigurableExtensionReference(extension.createDefaultExtensionConfig()));
				container.getChildren().add(item);
				logger.info("Created extension reference: " + extension.getExtensionId());
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

	private void doLayout() {
		for (int i = 0; i < container.getChildren().size(); i++) {
			Node child = container.getChildren().get(i);
			child.setLayoutX((i % cellsPerRow) * cellSize);
			child.setLayoutY((i / cellsPerRow) * cellSize);
		}
	}
	
	private void setCellSize(Pane node) {
		node.setPrefWidth(cellSize);
		node.setPrefHeight(cellSize);
		node.setMinWidth(cellSize);
		node.setMinHeight(cellSize);
		node.setMaxWidth(cellSize);
		node.setMaxHeight(cellSize);
	}

	private Pane createItem(ConfigurableExtensionReference<V> reference) {
		GridItem item = new GridItem(reference);
		BorderPane wrapper = new BorderPane(item);
		setCellSize(wrapper);
		wrapper.getStyleClass().add("grid-item-wrapper-pane");
		return wrapper;
	}

	private class GridItem extends BorderPane {
		private ConfigurableExtensionReference<V> reference;
		
		public GridItem(ConfigurableExtensionReference<V> reference) {
			this.reference = reference;
			getStyleClass().add("grid-item-pane");
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
