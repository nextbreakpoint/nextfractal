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
import com.nextbreakpoint.nextfractal.core.utils.Double2D;
import com.nextbreakpoint.nextfractal.core.utils.Double4D;
import com.nextbreakpoint.nextfractal.mandelbrot.*;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class ParamsPane extends Pane {
	private static final int SPACING = 5;

	private MandelbrotSession mandelbrotSession;

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

		mandelbrotSession = session;

		widthProperty().addListener((observable, oldValue, newValue) -> {
			double width = newValue.doubleValue() - getInsets().getLeft() - getInsets().getRight();
			box.setPrefWidth(width);
            algorithmCombobox.setPrefWidth(width);
        });
		
		heightProperty().addListener((observable, oldValue, newValue) -> {
			box.setPrefHeight(newValue.doubleValue() - getInsets().getTop() - getInsets().getBottom());
		});

		Function<MandelbrotMetadata, Object> updateAll = (metadata) -> {
			Double4D translation = metadata.getTranslation();
			Double4D rotation = metadata.getRotation();
			Double2D point = metadata.getPoint();
			xTraslationField.setText(String.valueOf(translation.getX()));
			yTraslationField.setText(String.valueOf(translation.getY()));
			zTraslationField.setText(String.valueOf(translation.getZ()));
			zRotationField.setText(String.valueOf(rotation.getZ()));
			wReField.setText(String.valueOf(point.getX()));
			wImField.setText(String.valueOf(point.getY()));
			xReField.setText(String.valueOf(0));
			xImField.setText(String.valueOf(0));
			if (metadata.isJulia()) {
				algorithmCombobox.getSelectionModel().select("Julia/Fatou");
			} else {
				algorithmCombobox.getSelectionModel().select("Mandelbrot");
			}
//			if (encodeTextArea != null) {
//				updateEncodedData(encodeTextArea, t);
//			}
			return null;
		};

		Function<MandelbrotMetadata, Object> notifyAll = (metadata) -> {
			double tx = Double.parseDouble(xTraslationField.getText());
			double ty = Double.parseDouble(yTraslationField.getText());
			double tz = Double.parseDouble(zTraslationField.getText());
			double tw = metadata.getTranslation().getW();
			Double4D translation = new Double4D(tx, ty, tz, tw);
			double rx = metadata.getRotation().getX();
			double ry = metadata.getRotation().getY();
			double rz = Double.parseDouble(zRotationField.getText());
			double rw = metadata.getRotation().getW();
			Double4D rotation = new Double4D(rx, ry, rz, rw);
			Double4D scale = metadata.getScale();
			double px = Double.parseDouble(wReField.getText());
			double py = Double.parseDouble(wImField.getText());
			Double2D point = new Double2D(px, py);
			boolean julia = !algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
			MandelbrotMetadata newMetadata = new MandelbrotMetadata(translation, rotation, scale, point, julia);
			MandelbrotSession newSession = new MandelbrotSession(newMetadata, mandelbrotSession.getScript());
			Platform.runLater(() -> {
				eventBus.postEvent("editor-view-changed", new Object[] { newSession, false, true });
			});
			return null;
		};

		eventBus.subscribe("editor-params-action", event -> {
			if (event.equals("cancel")) updateAll.apply((MandelbrotMetadata) mandelbrotSession.getMetadata());
			if (event.equals("apply")) notifyAll.apply((MandelbrotMetadata) mandelbrotSession.getMetadata());
		});

		eventBus.subscribe("session-data-changed", event -> updateData(updateAll, (Object[]) event));

		xTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(xTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] translation = metadata.getTranslation().toArray();
				translation[0] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(new Double4D(translation), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.isJulia());
				eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		yTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(yTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] translation = metadata.getTranslation().toArray();
				translation[1] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(new Double4D(translation), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.isJulia());
				eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		zTraslationField.setOnAction(e -> {
			double value = Double.parseDouble(zTraslationField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] translation = metadata.getTranslation().toArray();
				translation[2] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(new Double4D(translation), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), metadata.isJulia());
				eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		zRotationField.setOnAction(e -> {
			double value = Double.parseDouble(zRotationField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] rotation = metadata.getRotation().toArray();
				rotation[2] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), new Double4D(rotation), metadata.getScale(), metadata.getPoint(), metadata.isJulia());
				eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		wReField.setOnAction(e -> {
			double value = Double.parseDouble(wReField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] point = metadata.getPoint().toArray();
				point[0] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), new Double2D(point), metadata.isJulia());
				eventBus.postEvent("editor-point-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		wImField.setOnAction(e -> {
			double value = Double.parseDouble(wImField.getText());
			Platform.runLater(() -> {
				MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
				double[] point = metadata.getPoint().toArray();
				point[1] = value;
				MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), new Double2D(point), metadata.isJulia());
				eventBus.postEvent("editor-point-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
			});
		});
		
		algorithmCombobox.setOnAction(e -> {
			MandelbrotMetadata metadata = (MandelbrotMetadata) mandelbrotSession.getMetadata();
			boolean isJuliaView = metadata.isJulia();
			boolean isMandelbrotAlgorithm = algorithmCombobox.getSelectionModel().getSelectedItem().equals("Mandelbrot");
			if (isMandelbrotAlgorithm && isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), false);
					eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
				});
			} else if (!isMandelbrotAlgorithm && !isJuliaView) {
				Platform.runLater(() -> {
					MandelbrotMetadata newMetadata = new MandelbrotMetadata(metadata.getTranslation(), metadata.getRotation(), metadata.getScale(), metadata.getPoint(), true);
					eventBus.postEvent("editor-view-changed", new Object[] { new MandelbrotSession(newMetadata, mandelbrotSession.getScript()), false, true });
				});
			}
		});
	}

	private void updateData(Function<MandelbrotMetadata, Object> updateAll, Object[] event) {
		mandelbrotSession = (MandelbrotSession) event[0];
		if (event[1] == Boolean.FALSE) {
			updateAll.apply((MandelbrotMetadata) mandelbrotSession.getMetadata());
        }
	}

	protected String getRestriction() {
		return "-?\\d*\\.?\\d*";
	}
}
