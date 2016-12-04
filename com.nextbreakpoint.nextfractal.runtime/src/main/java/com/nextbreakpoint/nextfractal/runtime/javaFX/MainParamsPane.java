/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.runtime.javaFX;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.EventListener;
import com.nextbreakpoint.nextfractal.core.session.Session;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.nextbreakpoint.nextfractal.core.Plugins.findFactory;
import static com.nextbreakpoint.nextfractal.core.Plugins.listGrammars;

public class MainParamsPane extends Pane {
	private static final int SPACING = 5;

	private EventBus localEventBus;

	private Session session;

	public MainParamsPane(EventBus eventBus) {
		VBox box = new VBox(SPACING * 2);
		box.getStyleClass().add("params");

		VBox grammarPane = new VBox(SPACING);

		Label grammarLabel = new Label("Grammar");
		grammarPane.getChildren().add(grammarLabel);

		ComboBox<String> grammarCombobox = new ComboBox<>();
		grammarCombobox.getItems().addAll(listGrammars());
		grammarCombobox.getStyleClass().add("text-small");
		grammarPane.getChildren().add(grammarCombobox);

		if (grammarCombobox.getItems().size() > 1) {
			box.getChildren().add(grammarPane);
		}

		BorderPane paramsPane = new BorderPane();
		box.getChildren().add(paramsPane);

		VBox buttons = new VBox(4);
		Button applyButton = new Button("Apply");
		Button cancelButton = new Button("Cancel");
		buttons.getChildren().add(applyButton);
		buttons.getChildren().add(cancelButton);
		box.getChildren().add(buttons);

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
			Session session = (Session) ((Object[])event)[0];
			if (this.session == null || !this.session.getPluginId().equals(session.getPluginId())) {
				paramsPane.setCenter(createParamsPane(eventBus, session));
			}
			this.session = session;
			grammarCombobox.getSelectionModel().select(session.getGrammar());
		};

		eventBus.subscribe("session-data-loaded", updateUI);

		grammarCombobox.setOnAction(e -> {
			if (session != null && !grammarCombobox.getSelectionModel().isEmpty() && !grammarCombobox.getSelectionModel().getSelectedItem().equals(session.getGrammar())) {
				eventBus.postEvent("editor-grammar-changed", grammarCombobox.getSelectionModel().getSelectedItem());
			}
		});
	}

	private Pane createParamsPane(EventBus eventBus, Session session) {
		return findFactory(session.getPluginId()).map(factory -> factory.createParamsPane(getLocalEventBus(eventBus), session)).orElse(null);
	}

	private EventBus getLocalEventBus(EventBus eventBus) {
		if (localEventBus != null) {
			localEventBus.detach();
		}
		localEventBus = new EventBus(eventBus);
		return localEventBus;
	}
}
