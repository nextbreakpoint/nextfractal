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
package com.nextbreakpoint.nextfractal.contextfree.javaFX;

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeData;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeDataStore;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeListener;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeSession;
import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParamsPane extends Pane {
	private static final Logger logger = Logger.getLogger(ParamsPane.class.getName());
	private static final int SPACING = 5;

	public ParamsPane(ContextFreeSession session) {
		VBox box = new VBox(SPACING * 2);
		box.getStyleClass().add("params");
		Label grammarLabel = new Label("Grammar");
		Label wLabel = new Label("Initial value of w");
		VBox grammarPane = new VBox(SPACING);
		grammarPane.getChildren().add(grammarLabel);
		ComboBox<String> grammarCombobox = new ComboBox<>();
		session.listGrammars().forEach(grammar -> grammarCombobox.getItems().add(grammar));
		grammarCombobox.getStyleClass().add("text-small");
		grammarPane.getChildren().add(grammarCombobox);
		box.getChildren().add(grammarPane);
		VBox buttons = new VBox(4);
		Button applyButton = new Button("Apply");
		Button cancelButton = new Button("Cancel"); 
		buttons.getChildren().add(applyButton);
		buttons.getChildren().add(cancelButton);
		box.getChildren().add(buttons);
		Button encodeCopyButton = new Button("Copy");
		TextArea encodeTextArea;
		if (Boolean.getBoolean("contextfree.encode.data")) {
			encodeTextArea = new TextArea();
			encodeTextArea.getStyleClass().add("encoded");
			encodeTextArea.setWrapText(true);
			encodeTextArea.setEditable(false);
			box.getChildren().add(encodeTextArea);
			box.getChildren().add(encodeCopyButton);
			encodeCopyButton.setOnAction((e) -> {
				String text = encodeTextArea.getText();
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(text);
				clipboard.setContent(content);
			});
		} else {
			encodeTextArea = null;
		}

		grammarCombobox.getSelectionModel().select(session.getGrammar());

		ScrollPane scrollPane = new ScrollPane(box);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		getChildren().add(scrollPane);

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			box.setPrefWidth(width);
			grammarCombobox.setPrefWidth(width);
			scrollPane.setPrefWidth(newValue.doubleValue());
			applyButton.setPrefWidth(newValue.doubleValue());
			cancelButton.setPrefWidth(newValue.doubleValue());
			encodeCopyButton.setPrefWidth(newValue.doubleValue());
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
			scrollPane.setPrefHeight(newValue.doubleValue());
		});

		Function<ContextFreeSession, Object> updateAll = (t) -> {
			ContextFreeData data = t.getDataAsCopy();
			if (encodeTextArea != null) {
				updateEncodedData(encodeTextArea, t);
			}
			return null;
		};
		
		cancelButton.setOnAction(e -> updateAll.apply(session));
		
		applyButton.setOnAction((e) -> {
			Platform.runLater(() -> {
			});
		});
		
		updateAll.apply(session);
		
		session.addContextFreeListener(new ContextFreeListener() {
			@Override
			public void dataChanged(ContextFreeSession session) {
				updateAll.apply(session);
			}
			
			@Override
			public void statusChanged(ContextFreeSession session) {
			}

			@Override
			public void errorChanged(ContextFreeSession session) {
			}

			@Override
			public void sourceChanged(ContextFreeSession session) {
			}

			@Override
			public void reportChanged(ContextFreeSession session) {
				if (encodeTextArea != null) {
					updateEncodedData(encodeTextArea, session);
				}
			}
		});
		
		grammarCombobox.setOnAction(e -> {
			if (!grammarCombobox.getSelectionModel().isEmpty() && !grammarCombobox.getSelectionModel().getSelectedItem().equals(session.getGrammar())) {
				session.selectGrammar(grammarCombobox.getSelectionModel().getSelectedItem());
			}
		});
	}

	protected void updateEncodedData(TextArea textArea, ContextFreeSession session) {
		if (Boolean.getBoolean("ContextFree.encode.data")) {
			try {
				ContextFreeDataStore service = new ContextFreeDataStore();
				ContextFreeData data = session.getDataAsCopy();
				StringWriter writer = new StringWriter();
				service.saveToWriter(writer, data);
				String plainData = writer.toString();
				String encodedData = Base64.getEncoder().encodeToString(plainData.getBytes());
				encodedData = URLEncoder.encode(encodedData, "UTF-8");
				logger.info("Update encoded data");
				textArea.setText(encodedData);
			} catch (Exception e) {
				logger.log(Level.FINE, "Cannot encode data", e);
			}
		}		
	}


	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}
}
