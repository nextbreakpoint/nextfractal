/*
 * NextFractal 1.1.5
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

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;

public class ExportPane extends Pane {
	private static final int CONTROL_SIZE = 250;
	private VBox box = new VBox();
	private ExportDelegate delegate; 

	public ExportPane() {
		ComboBox<Integer[]> presets = new ComboBox<>();
		presets.getItems().add(new Integer[] { 1900, 1080 });
		presets.getItems().add(new Integer[] { 1650, 1050 });
		presets.getItems().add(new Integer[] { 1024, 768 });
		presets.getItems().add(new Integer[] { 640, 480 });
		presets.getItems().add(new Integer[] { 0, 0 });
		presets.setMinWidth(CONTROL_SIZE);
		presets.setMaxWidth(CONTROL_SIZE);
		presets.setPrefWidth(CONTROL_SIZE);
		presets.getSelectionModel().select(0);
		Integer[] item0 = presets.getSelectionModel().getSelectedItem();
		AdvancedTextField widthField = new AdvancedTextField();
		widthField.setRestrict(getRestriction());
		widthField.setEditable(false);
		widthField.setMinWidth(CONTROL_SIZE);
		widthField.setMaxWidth(CONTROL_SIZE);
		widthField.setPrefWidth(CONTROL_SIZE);
		widthField.setText(String.valueOf(item0[0]));
		AdvancedTextField heightField = new AdvancedTextField();
		heightField.setRestrict(getRestriction());
		heightField.setEditable(false);
		heightField.setMinWidth(CONTROL_SIZE);
		heightField.setMaxWidth(CONTROL_SIZE);
		heightField.setPrefWidth(CONTROL_SIZE);
		heightField.setText(String.valueOf(item0[1]));
		Button cancelButton = new Button("Cancel");
		Button exportButton = new Button("Export...");

		HBox buttons = new HBox(10);
		buttons.getChildren().add(cancelButton);
		buttons.getChildren().add(exportButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.getStyleClass().add("buttons");

		VBox dimensionBox = new VBox(5);
		dimensionBox.setAlignment(Pos.CENTER);
		dimensionBox.getChildren().add(new Label("Dimension"));
		dimensionBox.getChildren().add(presets);
		
		VBox widthBox = new VBox(5);
		widthBox.setAlignment(Pos.CENTER);
		widthBox.getChildren().add(new Label("Width"));
		widthBox.getChildren().add(widthField);
		
		VBox heightBox = new VBox(5);
		heightBox.setAlignment(Pos.CENTER);
		heightBox.getChildren().add(new Label("Height"));
		heightBox.getChildren().add(heightField);

		VBox controls = new VBox(8);
		controls.setAlignment(Pos.CENTER);
		controls.getChildren().add(dimensionBox);
		controls.getChildren().add(widthBox);
		controls.getChildren().add(heightBox);
		controls.getStyleClass().add("controls");

		Label title = new Label("Export as PNG");
		title.getStyleClass().add("title");
		
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(title);
		box.getChildren().add(controls);
		box.getChildren().add(buttons);
		box.getStyleClass().add("popup");
		
		presets.setConverter(new StringConverter<Integer[]>() {
			@Override
			public String toString(Integer[] item) {
				if (item == null) {
					return null;
				} else {
					if (item[0] == 0 || item[1] == 0) {
						return "Custom";
					} else {
						return item[0] + " \u00D7 " + item[1] + " px";
					}
				}
			}

			@Override
			public Integer[] fromString(String preset) {
				return null;
			}
		});
		
		presets.setCellFactory(new Callback<ListView<Integer[]>, ListCell<Integer[]>>() {
			@Override
			public ListCell<Integer[]> call(ListView<Integer[]> p) {
				return new ListCell<Integer[]>() {
					private final Label label;
					{
						label = new Label();
					}
					
					@Override
					protected void updateItem(Integer[] item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							label.setText(presets.getConverter().toString(item));
							setGraphic(label);
						}
					}
				};
			}
		});

		presets.valueProperty().addListener(new ChangeListener<Integer[]>() {
	        @Override 
	        public void changed(ObservableValue<? extends Integer[]> value, Integer[] oldItem, Integer[] newItem) {
				if (newItem[0] == 0 || newItem[1] == 0) {
					widthField.setEditable(true);
					heightField.setEditable(true);
					widthField.setText("1024");
					heightField.setText("768");
				} else {
					widthField.setEditable(false);
					heightField.setEditable(false);
					widthField.setText(String.valueOf(newItem[0]));
					heightField.setText(String.valueOf(newItem[1]));
				}
	        }    
	    });
		
		cancelButton.setOnMouseClicked(e -> {
			hide();
		});
		
		exportButton.setOnMouseClicked(e -> {
			hide();
			int width = Integer.parseInt(widthField.getText());
			int height = Integer.parseInt(heightField.getText());
			if (delegate != null) {
				delegate.exportSession(new RendererSize(width, height));
			}
		});
		
		getChildren().add(box);
		
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
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
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

	public ExportDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ExportDelegate delegate) {
		this.delegate = delegate;
	}
}
