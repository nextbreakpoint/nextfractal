/*
 * NextFractal 2.1.5
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2024 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.contextfree.javafx;

import com.nextbreakpoint.nextfractal.contextfree.module.ContextFreeMetadata;
import com.nextbreakpoint.nextfractal.contextfree.module.ContextFreeSession;
import com.nextbreakpoint.nextfractal.core.event.EditorDataChanged;
import com.nextbreakpoint.nextfractal.core.event.EditorParamsActionFired;
import com.nextbreakpoint.nextfractal.core.event.SessionDataChanged;
import com.nextbreakpoint.nextfractal.core.javafx.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.javafx.PlatformEventBus;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class ParamsPane extends Pane {
	private static final int SPACING = 5;

	private ContextFreeSession contextFreeSession;

	public ParamsPane(ContextFreeSession session, PlatformEventBus eventBus) {
		VBox box = new VBox(SPACING * 2);
		//TODO abstract code and create fields from model
		Label seedLabel = new Label("Random seed");
		AdvancedTextField seedField = new AdvancedTextField();
		seedField.setTooltip(new Tooltip("Seed for generating random numbers"));
		seedField.setRestrict(getRestriction());
		seedField.setTransform(String::toUpperCase);
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

		contextFreeSession = session;

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
			ContextFreeSession newSession = new ContextFreeSession(contextFreeSession.getScript(), newMetadata);
			eventBus.postEvent(EditorDataChanged.builder().session(newSession).continuous(false).timeAnimation(true).build());
			return null;
		};

		eventBus.subscribe(EditorParamsActionFired.class.getSimpleName(), event -> {
			final String action = ((EditorParamsActionFired) event[0]).action();
			if (contextFreeSession != null && action.equals("cancel")) updateAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
			if (contextFreeSession != null && action.equals("apply")) notifyAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
		});

		eventBus.subscribe(SessionDataChanged.class.getSimpleName(), event -> updateData(updateAll, (ContextFreeSession) ((SessionDataChanged) event[0]).session(), false));

		seedField.setOnAction(e -> notifyAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata()));
	}

	private void updateData(Function<ContextFreeMetadata, Object> updateAll, ContextFreeSession session, boolean continuous) {
		contextFreeSession = session;
		if (continuous == Boolean.FALSE) {
			updateAll.apply((ContextFreeMetadata) contextFreeSession.getMetadata());
		}
	}

	protected String getRestriction() {
		return "[A-Z]*";
	}
}
