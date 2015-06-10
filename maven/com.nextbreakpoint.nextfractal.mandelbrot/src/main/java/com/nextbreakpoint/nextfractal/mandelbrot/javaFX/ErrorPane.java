/*
 * NextFractal 1.1.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.core.javaFX.StringObservableValue;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ErrorPane extends Pane {
	private StringObservableValue messageProperty;
	private VBox box = new VBox();

	public ErrorPane() {
		messageProperty = new StringObservableValue();
		
		Button closeButton = new Button("Close");

		HBox buttons = new HBox(10);
		buttons.getChildren().add(closeButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.getStyleClass().add("buttons");
		
		Label title = new Label("Errors");
		title.getStyleClass().add("title");
		
		TextArea message = new TextArea();
		message.setEditable(false);

		VBox controls = new VBox(5);
		controls.setAlignment(Pos.CENTER);
		controls.getChildren().add(message);
		controls.getStyleClass().add("controls");

		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(title);
		box.getChildren().add(controls);
		box.getChildren().add(buttons);
		box.getStyleClass().add("popup");
		getChildren().add(box);

		closeButton.setOnMouseClicked(e -> {
			hide();
		});

		widthProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefWidth(newValue.doubleValue());
			}
		});
		
		heightProperty().addListener(new ChangeListener<java.lang.Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue, java.lang.Number newValue) {
				box.setPrefHeight(newValue.doubleValue());
				box.setLayoutY(-newValue.doubleValue());
			}
		});
		
		messageProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				message.setText(newValue);
			}
		});
	}

	public void setMessage(String message) {
		messageProperty.setValue(message);
	}

	public ObservableValue<String> messageProperty() {
		return messageProperty;
	}

	public void show() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromY(box.getTranslateY());
		tt.setToY(box.getHeight());
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(false);
			}
		});
		tt.play();
	}
	
	public void hide() {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.4));
		tt.setFromY(box.getTranslateY());
		tt.setToY(0);
		tt.setNode(box);
		tt.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setDisable(true);
			}
		});
		tt.play();
	}
}
