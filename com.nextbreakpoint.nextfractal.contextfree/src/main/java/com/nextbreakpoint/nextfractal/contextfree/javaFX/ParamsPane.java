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

import com.nextbreakpoint.nextfractal.contextfree.ContextFreeMetadata;
import com.nextbreakpoint.nextfractal.contextfree.ContextFreeSession;
import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class ParamsPane extends Pane {
	private static final int SPACING = 5;

	private ContextFreeSession contextFreeSession;

	public ParamsPane(ContextFreeSession session, EventBus eventBus) {
		VBox box = new VBox(SPACING * 2);
		Label seedLabel = new Label("Seed");
		AdvancedTextField seedField = new AdvancedTextField();
		seedField.setRestrict(getRestriction());
		seedField.setTransform(t -> t.toUpperCase());
		VBox seedPane = new VBox(SPACING);
		seedPane.getChildren().add(seedLabel);
		seedPane.getChildren().add(seedField);
		box.getChildren().add(seedPane);
//		Button encodeCopyButton = new Button("Copy");
//		TextArea encodeTextArea;
//		if (Boolean.getBoolean("contextfree.encode.data")) {
//			encodeTextArea = new TextArea();
//			encodeTextArea.getStyleClass().add("encoded");
//			encodeTextArea.setWrapText(true);
//			encodeTextArea.setEditable(false);
//			box.getChildren().add(encodeTextArea);
//			box.getChildren().add(encodeCopyButton);
//			encodeCopyButton.setOnAction((e) -> {
//				String text = encodeTextArea.getText();
//				Clipboard clipboard = Clipboard.getSystemClipboard();
//				ClipboardContent content = new ClipboardContent();
//				content.putString(text);
//				clipboard.setContent(content);
//			});
//		} else {
//			encodeTextArea = null;
//		}

		getChildren().add(box);

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			box.setPrefWidth(width);
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
		});

		Function<ContextFreeMetadata, Object> updateAll = (metadata) -> {
			seedField.setText(metadata.getSeed());
//			if (encodeTextArea != null) {
//				updateEncodedData(encodeTextArea, data);
//			}
			return null;
		};

		Function<ContextFreeMetadata, Object> notifyAll = (metadata) -> {
			String seed = seedField.getText();
			ContextFreeMetadata newMetadata = new ContextFreeMetadata(seed);
			ContextFreeSession newSession = new ContextFreeSession(newMetadata, contextFreeSession.getScript());
			Platform.runLater(() -> eventBus.postEvent("editor-data-changed", new Object[] { newSession, false, true }));
			return null;
		};

		eventBus.subscribe("editor-params-action", event -> {
			if (event.equals("cancel")) updateAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
			if (event.equals("apply")) notifyAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
		});

		eventBus.subscribe("editor-data-changed", event -> updateData(updateAll, (Object[]) event));

		eventBus.subscribe("render-data-changed", event -> updateData(updateAll, (Object[]) event));

		updateAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
		
		seedField.setOnAction(e -> notifyAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata()));
	}

	private void updateData(Function<ContextFreeMetadata, Object> updateAll, Object[] event) {
		contextFreeSession = (ContextFreeSession) event[0];
		updateAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
	}

	protected String getRestriction() {
		return "[A-Z]*";
	}
}
