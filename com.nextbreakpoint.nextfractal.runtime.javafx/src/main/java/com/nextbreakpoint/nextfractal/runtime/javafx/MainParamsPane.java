/*
 * NextFractal 2.0.3
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2018 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.runtime.javafx;

import com.nextbreakpoint.nextfractal.core.javafx.EventBus;
import com.nextbreakpoint.nextfractal.core.EventListener;
import com.nextbreakpoint.nextfractal.core.Session;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.nextbreakpoint.nextfractal.core.Plugins.listGrammars;
import static com.nextbreakpoint.nextfractal.core.javafx.UIPlugins.tryFindFactory;

public class MainParamsPane extends Pane {
	private static final int SPACING = 5;

	private Map<String, EventBus> buses = new HashMap<>();
	private Map<String, Pane> panels = new HashMap<>();
	private VBox paramsBox;
	private Session session;

	public MainParamsPane(EventBus eventBus) {
		VBox box = new VBox(SPACING * 2);
		box.getStyleClass().add("params");

		VBox grammarPane = new VBox(SPACING);

		Label grammarLabel = new Label("Project grammar");
		grammarPane.getChildren().add(grammarLabel);

		ComboBox<String> grammarCombobox = new ComboBox<>();
		grammarCombobox.getItems().addAll(listGrammars());
		grammarCombobox.getStyleClass().add("text-small");
		grammarCombobox.setTooltip(new Tooltip("Select project grammar"));
		grammarPane.getChildren().add(grammarCombobox);

		if (grammarCombobox.getItems().size() > 1) {
			box.getChildren().add(grammarPane);
		}

		paramsBox = new VBox(4);

		BorderPane paramsPane = new BorderPane();
		paramsBox.getChildren().add(paramsPane);

		HBox buttons = new HBox(4);
		Button applyButton = new Button("Apply");
		Button cancelButton = new Button("Cancel");
		applyButton.setTooltip(new Tooltip("Apply parameters"));
		cancelButton.setTooltip(new Tooltip("Revert parameters"));
		buttons.getChildren().add(applyButton);
		buttons.getChildren().add(cancelButton);
		buttons.getStyleClass().add("buttons");
		buttons.getStyleClass().add("text-small");

		paramsBox.getChildren().add(buttons);

		box.getChildren().add(paramsBox);

		ScrollPane scrollPane = new ScrollPane(box);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		getChildren().add(scrollPane);

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			box.setPrefWidth(width);
			grammarCombobox.setPrefWidth(width);
			applyButton.setPrefWidth(newValue.doubleValue());
			cancelButton.setPrefWidth(newValue.doubleValue());
			scrollPane.setPrefWidth(newValue.doubleValue());
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
			scrollPane.setPrefHeight(newValue.doubleValue());
		});

		cancelButton.setOnAction(e -> eventBus.postEvent("editor-params-action", "cancel"));
		
		applyButton.setOnAction((e) -> eventBus.postEvent("editor-params-action", "apply"));

		EventListener updateUI = event -> {
			Session session = (Session) event[0];
			if (this.session == null || !this.session.getPluginId().equals(session.getPluginId())) {
				if (this.session != null) {
					Optional.ofNullable(buses.get(this.session.getPluginId())).ifPresent(bus -> bus.disable());
				}
				Pane rootPane = panels.get(session.getPluginId());
				if (rootPane == null) {
					EventBus innerBus = new EventBus(eventBus);
					rootPane = createParamsPane(innerBus, session);
					panels.put(session.getPluginId(), rootPane);
					buses.put(session.getPluginId(), innerBus);
				}
				paramsPane.setCenter(rootPane);
				Optional.ofNullable(buses.get(session.getPluginId())).ifPresent(bus -> bus.enable());
			}
			this.session = session;
			grammarCombobox.getSelectionModel().select(session.getGrammar());
		};

		eventBus.subscribe("session-data-loaded", updateUI);

		eventBus.subscribe("session-terminated", event -> buses.clear());
		eventBus.subscribe("session-terminated", event -> panels.clear());

		grammarCombobox.setOnAction(e -> {
			if (session != null && !grammarCombobox.getSelectionModel().isEmpty() && !grammarCombobox.getSelectionModel().getSelectedItem().equals(session.getGrammar())) {
				eventBus.postEvent("editor-grammar-changed", grammarCombobox.getSelectionModel().getSelectedItem());
			}
		});
	}

	private Pane createParamsPane(EventBus eventBus, Session session) {
		return tryFindFactory(session.getPluginId()).map(factory -> factory.createParamsPane(eventBus, session)).orElse(null);
	}

	public void setParamsDisable(boolean disabled) {
		paramsBox.setDisable(disabled);
	}
}
