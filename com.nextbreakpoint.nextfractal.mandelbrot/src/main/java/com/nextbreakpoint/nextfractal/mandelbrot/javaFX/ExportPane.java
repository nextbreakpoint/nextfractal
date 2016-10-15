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
package com.nextbreakpoint.nextfractal.mandelbrot.javaFX;

import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.core.renderer.RendererSize;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ExportPane extends BorderPane {
	private ExportDelegate delegate;

	public ExportPane() {
		ComboBox<Integer[]> presetsCombobox = new ComboBox<>();
		presetsCombobox.getItems().add(new Integer[] { 0, 0 });
		presetsCombobox.getItems().add(new Integer[] { 8192, 8192 });
		presetsCombobox.getItems().add(new Integer[] { 4096, 4096 });
		presetsCombobox.getItems().add(new Integer[] { 2048, 2048 });
		presetsCombobox.getItems().add(new Integer[] { 1900, 1900 });
		presetsCombobox.getItems().add(new Integer[] { 1900, 1080 });
		presetsCombobox.getItems().add(new Integer[] { 1650, 1650 });
		presetsCombobox.getItems().add(new Integer[] { 1650, 1050 });
		presetsCombobox.getItems().add(new Integer[] { 1024, 1024 });
		presetsCombobox.getItems().add(new Integer[] { 1024, 768 });
		presetsCombobox.getItems().add(new Integer[] { 640, 640 });
		presetsCombobox.getItems().add(new Integer[] { 640, 480 });
		presetsCombobox.getItems().add(new Integer[] { 512, 512 });
		presetsCombobox.getItems().add(new Integer[] { 256, 256 });
		presetsCombobox.getSelectionModel().select(7);
		presetsCombobox.getStyleClass().add("text-small");
		Integer[] item0 = presetsCombobox.getSelectionModel().getSelectedItem();
		AdvancedTextField widthField = new AdvancedTextField();
		widthField.setRestrict(getRestriction());
		widthField.setEditable(false);
		widthField.setText(String.valueOf(item0[0]));
		AdvancedTextField heightField = new AdvancedTextField();
		heightField.setRestrict(getRestriction());
		heightField.setEditable(false);
		heightField.setText(String.valueOf(item0[1]));
		Button exportButton = new Button("Export");

		VBox buttons = new VBox(4);
		buttons.getChildren().add(exportButton);
		buttons.getStyleClass().add("buttons");

		VBox dimensionBox = new VBox(5);
		dimensionBox.setAlignment(Pos.CENTER);
		dimensionBox.getChildren().add(presetsCombobox);

		VBox sizeBox = new VBox(5);
		sizeBox.setAlignment(Pos.CENTER);
		sizeBox.getChildren().add(widthField);
		sizeBox.getChildren().add(heightField);

		VBox controls = new VBox(8);
		controls.setAlignment(Pos.CENTER_LEFT);
		controls.getChildren().add(new Label("Choose image size"));
		controls.getChildren().add(dimensionBox);
		controls.getChildren().add(new Label("Size in pixels"));
		controls.getChildren().add(sizeBox);

		VBox box = new VBox(8);
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().add(controls);
		box.getChildren().add(buttons);
		setCenter(box);

		getStyleClass().add("export");
		
		presetsCombobox.setConverter(new StringConverter<Integer[]>() {
			@Override
			public String toString(Integer[] item) {
				if (item == null) {
					return null;
				} else {
					if (item[0] == 0 || item[1] == 0) {
						return "Custom";
					} else {
						return item[0] + "\u00D7" + item[1];
					}
				}
			}

			@Override
			public Integer[] fromString(String preset) {
				return null;
			}
		});
		
		presetsCombobox.setCellFactory(new Callback<ListView<Integer[]>, ListCell<Integer[]>>() {
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
							label.setText(presetsCombobox.getConverter().toString(item));
							setGraphic(label);
						}
					}
				};
			}
		});

		presetsCombobox.valueProperty().addListener((value, oldItem, newItem) -> {
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
        });
		
		exportButton.setOnMouseClicked(e -> {
			if (delegate != null) {
				int renderWidth = Integer.parseInt(widthField.getText());
				int renderHeight = Integer.parseInt(heightField.getText());
				delegate.createSession(new RendererSize(renderWidth, renderHeight));
			}
		});

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			presetsCombobox.setPrefWidth(width);
			box.setPrefWidth(width);
			box.setMaxWidth(width);
			exportButton.setPrefWidth(width);
		});
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}
	
	public void setExportDelegate(ExportDelegate delegate) {
		this.delegate = delegate;
	}
}
