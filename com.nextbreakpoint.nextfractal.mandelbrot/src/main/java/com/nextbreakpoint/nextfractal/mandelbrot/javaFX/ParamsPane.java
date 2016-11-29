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

import com.nextbreakpoint.nextfractal.core.EventBus;
import com.nextbreakpoint.nextfractal.core.javaFX.AdvancedTextField;
import com.nextbreakpoint.nextfractal.mandelbrot.*;
import javafx.application.Platform;
import javafx.scene.control.*;
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

	private MandelbrotData mandelbrotData;
	private MandelbrotView mandelbrotView;

	public ParamsPane(MandelbrotSession session, EventBus eventBus) {
		VBox box = new VBox(SPACING * 2);
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
		VBox algorithmPane = new VBox(SPACING);
		algorithmPane.getChildren().add(algorithmLabel);
		ComboBox<String> algorithmCombobox = new ComboBox<>();
		algorithmCombobox.getItems().add("Mandelbrot");
		algorithmCombobox.getItems().add("Julia/Fatou");
		algorithmCombobox.getStyleClass().add("text-small");
		algorithmPane.getChildren().add(algorithmCombobox);
		box.getChildren().add(algorithmPane);
		box.getChildren().add(translationPane);
		box.getChildren().add(rotationPane);
		box.getChildren().add(wPane);
		box.getChildren().add(xPane);
//		Button encodeCopyButton = new Button("Copy");
//		TextArea encodeTextArea;
//		if (Boolean.getBoolean("mandelbrot.encode.mandelbrotData")) {
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

		mandelbrotData = session.getDataAsCopy();
		mandelbrotView = createMandelbrotView(mandelbrotData);

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			box.setPrefWidth(width);
            algorithmCombobox.setPrefWidth(width);
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
		});

		Function<MandelbrotView, Object> updateAll = (view) -> {
			double[] translation = view.getTranslation();
			double[] rotation = view.getRotation();
			double[] point = view.getPoint();
			xTraslationField.setText(String.valueOf(translation[0]));
			yTraslationField.setText(String.valueOf(translation[1]));
			zTraslationField.setText(String.valueOf(translation[2]));
			zRotationField.setText(String.valueOf(rotation[2]));
			wReField.setText(String.valueOf(point[0]));
			wImField.setText(String.valueOf(point[1]));
			xReField.setText(String.valueOf(0));
			xImField.setText(String.valueOf(0));
			if (view.isJulia()) {
				algorithmCombobox.getSelectionModel().select("Julia/Fatou");
			} else {
				algorithmCombobox.getSelectionModel().select("Mandelbrot");
			}
//			if (encodeTextArea != null) {
//				updateEncodedData(encodeTextArea, t);
//			}
			return null;
		};

		Function<MandelbrotView, Object> notifyAll = (view) -> {
			double[] translation = new double[4];
			translation[0] = Double.parseDouble(xTraslationField.getText());
			translation[1] = Double.parseDouble(yTraslationField.getText());
			translation[2] = Double.parseDouble(zTraslationField.getText());
			translation[3] = view.getTranslation()[3];
			double[] rotation = new double[4];
			rotation[0] = view.getRotation()[0];
			rotation[1] = view.getRotation()[1];
			rotation[2] = Double.parseDouble(zRotationField.getText());
			rotation[3] = view.getRotation()[2];
			double[] scale = view.getScale().clone();
			double[] point = new double[2];
			point[0] = Double.parseDouble(wReField.getText());
			point[1] = Double.parseDouble(wImField.getText());
			boolean julia = !algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
			Platform.runLater(() -> eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotView(translation, rotation, scale, point, julia), false }));
			return null;
		};

		eventBus.subscribe("editor-params-action", event -> {
			if (event.equals("cancel")) updateAll.apply(mandelbrotView);
			if (event.equals("apply")) notifyAll.apply(mandelbrotView);
		});

		eventBus.subscribe("session-view-changed", event -> {
			mandelbrotView = (MandelbrotView) ((Object[])event)[0];
			if (((Object[])event)[1] == Boolean.FALSE) {
				updateAll.apply(mandelbrotView);
			}
		});

		eventBus.subscribe("session-data-changed", event -> {
			mandelbrotData = (MandelbrotData) ((Object[])event)[0];
			mandelbrotView = createMandelbrotView(mandelbrotData);
			if (((Object[])event)[1] == Boolean.FALSE) {
				updateAll.apply(mandelbrotView);
			}
		});

		eventBus.subscribe("session-point-changed", event -> {
			double[] point = (double[])((Object[])event)[0];
			mandelbrotView = createMandelbrotView(mandelbrotData);
			mandelbrotView.getPoint()[0] = point[0];
			mandelbrotView.getPoint()[1] = point[1];
			if (((Object[])event)[1] == Boolean.FALSE) {
				updateAll.apply(mandelbrotView);
			}
		});

		updateAll.apply(mandelbrotView);
		
		xTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(xTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				view.getTranslation()[0] = value;
				eventBus.postEvent("editor-view-changed", new Object[] { view, false });
			});
		});
		
		yTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(yTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				view.getTranslation()[1] = value;
				eventBus.postEvent("editor-view-changed", new Object[] { view, false });
			});
		});
		
		zTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(zTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				view.getTranslation()[2] = value;
				eventBus.postEvent("editor-view-changed", new Object[] { view, false });
			});
		});
		
		zRotationField.setOnAction(e -> {
			double value = Double.parseDouble(zRotationField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				view.getRotation()[2] = value;
				eventBus.postEvent("editor-view-changed", new Object[] { view, false });
			});
		});
		
		wReField.setOnAction(e -> {
			double value = Double.parseDouble(wReField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				double[] point = new double[2];
				point[0] = value;
				point[1] = view.getPoint()[1];
				eventBus.postEvent("editor-point-changed", new Object[] { point, false });
			});
		});
		
		wImField.setOnAction(e -> {
			double value = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				MandelbrotView view = new MandelbrotView(mandelbrotView);
				double[] point = new double[2];
				point[0] = view.getPoint()[0];
				point[1] = value;
				eventBus.postEvent("editor-point-changed", new Object[] { point, false });
			});
		});
		
		algorithmCombobox.setOnAction(e -> {
			boolean isJuliaView = mandelbrotView.isJulia();
			boolean isMandelbrotAlgorithm = algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
			if (isMandelbrotAlgorithm && isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotView newView = new MandelbrotView(mandelbrotView.getTranslation(), mandelbrotView.getRotation(), mandelbrotView.getScale(), mandelbrotView.getPoint(), false);
					eventBus.postEvent("editor-view-changed", new Object[] { newView, false });
				});
			} else if (!isMandelbrotAlgorithm && !isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotView newView = new MandelbrotView(mandelbrotView.getTranslation(), mandelbrotView.getRotation(), mandelbrotView.getScale(), mandelbrotView.getPoint(), true);
					eventBus.postEvent("editor-view-changed", new Object[] { newView, false });
				});
			}
		});
	}

	private MandelbrotView createMandelbrotView(MandelbrotData data) {
		return new MandelbrotView(data.getTranslation(), data.getRotation(), data.getScale(), data.getPoint(), data.isJulia());
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
