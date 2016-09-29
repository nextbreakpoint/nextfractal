/*
 * NextFractal 1.2.1
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

import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.function.Function;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotData;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotDataStore;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotListener;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotSession;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotView;

public class MandelbrotParamsPane extends BorderPane {
	private static final int SPACING = 5;

	public MandelbrotParamsPane(MandelbrotSession session) {
		VBox box = new VBox(SPACING * 2);
		Label traslationLabel = new Label("Region traslation (x,y,z)");
		AdvancedTextField xTraslationField = new AdvancedTextField();
		AdvancedTextField yTraslationField = new AdvancedTextField();
		AdvancedTextField zTraslationField = new AdvancedTextField();
		xTraslationField.setRestrict(getRestriction());
		yTraslationField.setRestrict(getRestriction());
		zTraslationField.setRestrict(getRestriction());
		VBox traslationPane = new VBox(SPACING);
		traslationPane.getChildren().add(traslationLabel);
		traslationPane.getChildren().add(xTraslationField);
		traslationPane.getChildren().add(yTraslationField);
		traslationPane.getChildren().add(zTraslationField);
		Label rotationLabel = new Label("Region rotation");
		AdvancedTextField zRotationField = new AdvancedTextField();
		zRotationField.setRestrict(getRestriction());
		VBox rotationPane = new VBox(SPACING); 
		rotationPane.getChildren().add(rotationLabel);
		rotationPane.getChildren().add(zRotationField);
		Label algorithmLabel = new Label("Algorithm variant");
		Label wLabel = new Label("Initial value of w = wr + wi * i");
		AdvancedTextField wReField = new AdvancedTextField();
		AdvancedTextField wImField = new AdvancedTextField();
		wReField.setRestrict(getRestriction());
		wImField.setRestrict(getRestriction());
		VBox wPane = new VBox(SPACING); 
		wPane.getChildren().add(wLabel);
		wPane.getChildren().add(wReField);
		wPane.getChildren().add(wImField);
		Label xLabel = new Label("Initial value of x = xr + xi * i");
		AdvancedTextField xReField = new AdvancedTextField();
		AdvancedTextField xImField = new AdvancedTextField();
		xReField.setRestrict(getRestriction());
		xImField.setRestrict(getRestriction());
		xReField.setEditable(false);
		xImField.setEditable(false);
		VBox xPane = new VBox(SPACING);
		xPane.getChildren().add(xLabel);
		xPane.getChildren().add(xReField);
		xPane.getChildren().add(xImField);
		VBox algorithmPane = new VBox(SPACING); 
		algorithmPane.getChildren().add(algorithmLabel);
		ComboBox<String> algorithmCombobox = new ComboBox<>();
		algorithmCombobox.getItems().add("Mandelbrot");
		algorithmCombobox.getItems().add("Julia/Fatou");
		algorithmPane.getChildren().add(algorithmCombobox);
		box.getChildren().add(algorithmPane);
		box.getChildren().add(traslationPane);
		box.getChildren().add(rotationPane);
		box.getChildren().add(wPane);
		box.getChildren().add(xPane);
		HBox buttons = new HBox(10);
		Button applyButton = new Button("Apply"); 
		Button cancelButton = new Button("Cancel"); 
		buttons.getChildren().add(applyButton);
		buttons.getChildren().add(cancelButton);
		box.getChildren().add(buttons);
		TextArea encodeTextArea;
		if (Boolean.getBoolean("mandelbrot.encode.data")) {
			encodeTextArea = new TextArea();
			encodeTextArea.getStyleClass().add("encoded");
			encodeTextArea.setWrapText(true);
			encodeTextArea.setEditable(false);
			box.getChildren().add(encodeTextArea);
			Button encodeCopyButton = new Button("Copy"); 
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
		setCenter(new ScrollPane(box));
		getStyleClass().add("params");
		
		widthProperty().addListener((observable, oldValue, newValue) -> {
            box.setPrefWidth(newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight() - 5);
            algorithmCombobox.setPrefWidth(newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight() - 5);
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom() - 5);
		});

		Function<MandelbrotSession, Object> updateAll = (t) -> {
			MandelbrotData data = t.getDataAsCopy();
			double[] traslation = data.getTraslation();
			double[] rotation = data.getRotation();
			double[] point = data.getPoint();
			xTraslationField.setText(String.valueOf(traslation[0]));
			yTraslationField.setText(String.valueOf(traslation[1]));
			zTraslationField.setText(String.valueOf(traslation[2]));
			zRotationField.setText(String.valueOf(rotation[2]));
			wReField.setText(String.valueOf(point[0]));
			wImField.setText(String.valueOf(point[1]));
			xReField.setText(String.valueOf(0));
			xImField.setText(String.valueOf(0));
			if (data.isJulia()) {
				algorithmCombobox.getSelectionModel().select("Julia/Fatou");
			} else {
				algorithmCombobox.getSelectionModel().select("Mandelbrot");
			}
			if (encodeTextArea != null) {
				updateEncodedData(encodeTextArea, t);
			}
			return null;
		};
		
		cancelButton.setOnAction(e -> updateAll.apply(session));
		
		applyButton.setOnAction((e) -> {
			double[] traslation = new double[3];
			traslation[0] = Double.parseDouble(xTraslationField.getText());
			traslation[1] = Double.parseDouble(yTraslationField.getText());
			traslation[2] = Double.parseDouble(zTraslationField.getText());
			double rotation = Double.parseDouble(zRotationField.getText());
			double[] point = new double[3];
			point[0] = Double.parseDouble(wReField.getText());
			point[1] = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[0] = traslation[0];
				view.getTraslation()[1] = traslation[1];
				view.getTraslation()[2] = traslation[2];
				view.getRotation()[2] = rotation;
				view.getPoint()[0] = point[0];
				view.getPoint()[1] = point[1];
				boolean isMandelbrotAlgorithm = algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
				session.setView(new MandelbrotView(view.getTraslation(), view.getRotation(), view.getScale(), view.getPoint(), isMandelbrotAlgorithm ? false : true), false);
			});
		});
		
		updateAll.apply(session);
		
		session.addMandelbrotListener(new MandelbrotListener() {
			@Override
			public void dataChanged(MandelbrotSession session) {
				updateAll.apply(session);
			}
			
			@Override
			public void pointChanged(MandelbrotSession session, boolean continuous) {
				MandelbrotData data = session.getDataAsCopy();
				double[] point = data.getPoint();
				wReField.setText(String.valueOf(point[0]));
				wImField.setText(String.valueOf(point[1]));
				xReField.setText(String.valueOf(0));
				xImField.setText(String.valueOf(0));
				if (!continuous) {
					if (encodeTextArea != null) {
						updateEncodedData(encodeTextArea, session);
					}
				}
			}

			@Override
			public void viewChanged(MandelbrotSession session, boolean continuous) {
				if (!continuous) {
					MandelbrotData data = session.getDataAsCopy();
					double[] traslation = data.getTraslation();
					double[] rotation = data.getRotation();
					xTraslationField.setText(String.valueOf(traslation[0]));
					yTraslationField.setText(String.valueOf(traslation[1]));
					zTraslationField.setText(String.valueOf(traslation[2]));
					zRotationField.setText(String.valueOf(rotation[2]));
					if (data.isJulia()) {
						algorithmCombobox.getSelectionModel().select("Julia/Fatou");
					} else {
						algorithmCombobox.getSelectionModel().select("Mandelbrot");
					}
					if (encodeTextArea != null) {
						updateEncodedData(encodeTextArea, session);
					}
				}
			}

			@Override
			public void sourceChanged(MandelbrotSession session) {
			}

			@Override
			public void reportChanged(MandelbrotSession session) {
			}
		});
		
		xTraslationField.setOnAction((e) -> {
			double value = Double.parseDouble(xTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[0] = value;
				session.setView(view, false);
			});
		});
		
		yTraslationField.setOnAction((e) -> {
			double value = Double.parseDouble(yTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[1] = value;
				session.setView(view, false);
			});
		});
		
		zTraslationField.setOnAction((e) -> {
			double value = Double.parseDouble(zTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[2] = value;
				session.setView(view, false);
			});
		});
		
		zRotationField.setOnAction((e) -> {
			double value = Double.parseDouble(zRotationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getRotation()[2] = value;
				session.setView(view, false);
			});
		});
		
		wReField.setOnAction((e) -> {
			double value = Double.parseDouble(wReField.getText());
			Platform.runLater(() -> {
				double[] wPoint = session.getPoint();
				wPoint[0] = value;
				session.setPoint(wPoint, false);
			});
		});
		
		wImField.setOnAction((e) -> {
			double value = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				double[] wPoint = session.getPoint();
				wPoint[1] = value;
				session.setPoint(wPoint, false);
			});
		});
		
		algorithmCombobox.setOnAction((e) -> {
			boolean isJuliaView = session.getViewAsCopy().isJulia();
			boolean isMandelbrotAlgorithm = algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
			if (isMandelbrotAlgorithm && isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotView view = session.getViewAsCopy();
					session.setView(new MandelbrotView(view.getTraslation(), view.getRotation(), view.getScale(), view.getPoint(), false), false);
				});
			} else if (!isMandelbrotAlgorithm && !isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotView view = session.getViewAsCopy();
					session.setView(new MandelbrotView(view.getTraslation(), view.getRotation(), view.getScale(), view.getPoint(), true), false);
				});
			}
		});
	}

	protected void updateEncodedData(TextArea textArea, MandelbrotSession session) {
		if (Boolean.getBoolean("mandelbrot.encode.data")) {
			try {
				MandelbrotDataStore service = new MandelbrotDataStore();
				MandelbrotData data = session.getDataAsCopy();
				StringWriter writer = new StringWriter();
				service.saveToWriter(writer, data);
				String plainData = writer.toString();
				String encodedData = Base64.getEncoder().encodeToString(plainData.getBytes());
				encodedData = URLEncoder.encode(encodedData, "UTF-8");
				textArea.setText(encodedData);
			} catch (Exception e) {
			}
		}		
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}
}
