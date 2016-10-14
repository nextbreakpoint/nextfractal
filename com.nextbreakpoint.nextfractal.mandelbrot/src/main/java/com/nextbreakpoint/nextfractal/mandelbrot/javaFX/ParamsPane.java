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
import com.nextbreakpoint.nextfractal.mandelbrot.*;
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

	public ParamsPane(MandelbrotSession session) {
		VBox box = new VBox(SPACING * 2);
		box.getStyleClass().add("params");
		Label translationLabel = new Label("Region translation");
		AdvancedTextField xTraslationField = new AdvancedTextField();
		AdvancedTextField yTraslationField = new AdvancedTextField();
		AdvancedTextField zTraslationField = new AdvancedTextField();
		xTraslationField.setRestrict(getRestriction());
		yTraslationField.setRestrict(getRestriction());
		zTraslationField.setRestrict(getRestriction());
		VBox translationPane = new VBox(SPACING);
		translationPane.getChildren().add(translationLabel);
		translationPane.getChildren().add(xTraslationField);
		translationPane.getChildren().add(yTraslationField);
		translationPane.getChildren().add(zTraslationField);
		Label rotationLabel = new Label("Region rotation");
		AdvancedTextField zRotationField = new AdvancedTextField();
		zRotationField.setRestrict(getRestriction());
		VBox rotationPane = new VBox(SPACING); 
		rotationPane.getChildren().add(rotationLabel);
		rotationPane.getChildren().add(zRotationField);
		Label algorithmLabel = new Label("Algorithm variant");
		Label grammarLabel = new Label("Grammar");
		Label wLabel = new Label("Initial value of w");
		AdvancedTextField wReField = new AdvancedTextField();
		AdvancedTextField wImField = new AdvancedTextField();
		wReField.setRestrict(getRestriction());
		wImField.setRestrict(getRestriction());
		VBox wPane = new VBox(SPACING);
		wPane.getChildren().add(wLabel);
		HBox whPane = new HBox(SPACING);
		whPane.getChildren().add(wReField);
		whPane.getChildren().add(wImField);
		wPane.getChildren().add(whPane);
		Label xLabel = new Label("Initial value of x");
		AdvancedTextField xReField = new AdvancedTextField();
		AdvancedTextField xImField = new AdvancedTextField();
		xReField.setRestrict(getRestriction());
		xImField.setRestrict(getRestriction());
		xReField.setEditable(false);
		xImField.setEditable(false);
		VBox xPane = new VBox(SPACING);
		xPane.getChildren().add(xLabel);
		HBox xhPane = new HBox(SPACING);
		xhPane.getChildren().add(xReField);
		xhPane.getChildren().add(xImField);
		xPane.getChildren().add(xhPane);
		VBox grammarPane = new VBox(SPACING);
		grammarPane.getChildren().add(grammarLabel);
		ComboBox<String> grammarCombobox = new ComboBox<>();
		session.listGrammars().forEach(grammar -> grammarCombobox.getItems().add(grammar));
		grammarCombobox.getStyleClass().add("text-small");
		grammarPane.getChildren().add(grammarCombobox);
		VBox algorithmPane = new VBox(SPACING);
		algorithmPane.getChildren().add(algorithmLabel);
		ComboBox<String> algorithmCombobox = new ComboBox<>();
		algorithmCombobox.getItems().add("Mandelbrot");
		algorithmCombobox.getItems().add("Julia/Fatou");
		algorithmCombobox.getStyleClass().add("text-small");
		algorithmPane.getChildren().add(algorithmCombobox);
		if (grammarCombobox.getItems().size() > 1) {
			box.getChildren().add(grammarPane);
		}
		box.getChildren().add(algorithmPane);
		box.getChildren().add(translationPane);
		box.getChildren().add(rotationPane);
		box.getChildren().add(wPane);
		box.getChildren().add(xPane);
		VBox buttons = new VBox(4);
		Button applyButton = new Button("Apply");
		Button cancelButton = new Button("Cancel"); 
		buttons.getChildren().add(applyButton);
		buttons.getChildren().add(cancelButton);
		box.getChildren().add(buttons);
		Button encodeCopyButton = new Button("Copy");
		TextArea encodeTextArea;
		if (Boolean.getBoolean("mandelbrot.encode.data")) {
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
            algorithmCombobox.setPrefWidth(width);
			scrollPane.setPrefWidth(newValue.doubleValue());
			applyButton.setPrefWidth(newValue.doubleValue());
			cancelButton.setPrefWidth(newValue.doubleValue());
			encodeCopyButton.setPrefWidth(newValue.doubleValue());
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
			scrollPane.setPrefHeight(newValue.doubleValue());
		});

		Function<MandelbrotSession, Object> updateAll = (t) -> {
			MandelbrotData data = t.getDataAsCopy();
			double[] translation = data.getTranslation();
			double[] rotation = data.getRotation();
			double[] point = data.getPoint();
			xTraslationField.setText(String.valueOf(translation[0]));
			yTraslationField.setText(String.valueOf(translation[1]));
			zTraslationField.setText(String.valueOf(translation[2]));
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
			double[] translation = new double[3];
			translation[0] = Double.parseDouble(xTraslationField.getText());
			translation[1] = Double.parseDouble(yTraslationField.getText());
			translation[2] = Double.parseDouble(zTraslationField.getText());
			double rotation = Double.parseDouble(zRotationField.getText());
			double[] point = new double[3];
			point[0] = Double.parseDouble(wReField.getText());
			point[1] = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[0] = translation[0];
				view.getTraslation()[1] = translation[1];
				view.getTraslation()[2] = translation[2];
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
					double[] translation = data.getTranslation();
					double[] rotation = data.getRotation();
					xTraslationField.setText(String.valueOf(translation[0]));
					yTraslationField.setText(String.valueOf(translation[1]));
					zTraslationField.setText(String.valueOf(translation[2]));
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
			public void statusChanged(MandelbrotSession session) {
			}

			@Override
			public void errorChanged(MandelbrotSession session) {
			}

			@Override
			public void sourceChanged(MandelbrotSession session) {
			}

			@Override
			public void reportChanged(MandelbrotSession session) {
				if (encodeTextArea != null) {
					updateEncodedData(encodeTextArea, session);
				}
			}
		});
		
		xTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(xTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[0] = value;
				session.setView(view, false);
			});
		});
		
		yTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(yTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[1] = value;
				session.setView(view, false);
			});
		});
		
		zTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(zTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getTraslation()[2] = value;
				session.setView(view, false);
			});
		});
		
		zRotationField.setOnAction(e -> {
			double value = Double.parseDouble(zRotationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = session.getViewAsCopy();
				view.getRotation()[2] = value;
				session.setView(view, false);
			});
		});
		
		wReField.setOnAction(e -> {
			double value = Double.parseDouble(wReField.getText());
			Platform.runLater(() -> {
				double[] wPoint = session.getPoint();
				wPoint[0] = value;
				session.setPoint(wPoint, false);
			});
		});
		
		wImField.setOnAction(e -> {
			double value = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				double[] wPoint = session.getPoint();
				wPoint[1] = value;
				session.setPoint(wPoint, false);
			});
		});
		
		algorithmCombobox.setOnAction(e -> {
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

		grammarCombobox.setOnAction(e -> {
			if (!grammarCombobox.getSelectionModel().isEmpty() && !grammarCombobox.getSelectionModel().getSelectedItem().equals(session.getGrammar())) {
				session.selectGrammar(grammarCombobox.getSelectionModel().getSelectedItem());
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
