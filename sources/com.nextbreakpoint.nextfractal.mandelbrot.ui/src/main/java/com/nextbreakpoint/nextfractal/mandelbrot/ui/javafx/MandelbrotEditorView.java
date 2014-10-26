package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;
import com.nextbreakpoint.nextfractal.mandelbrot.extensions.image.MandelbrotImageConfig;

public class MandelbrotEditorView extends View {

	public MandelbrotEditorView(MandelbrotConfig config, ViewContext viewContext, RenderContext context, NodeSession session) {
		AnchorPane pane = new AnchorPane();
		getChildren().add(pane);
		pane.setPrefWidth(viewContext.getEditorViewSize().getWidth());
		pane.setPrefHeight(viewContext.getEditorViewSize().getHeight());
		HBox leftBottomButtons = new HBox(10);
		Button zoomButton = new Button("zoom");
		Button infoButton = new Button("info");
		leftBottomButtons.getChildren().add(zoomButton);
		leftBottomButtons.getChildren().add(infoButton);
		pane.getChildren().add(leftBottomButtons);
		AnchorPane.setLeftAnchor(leftBottomButtons, 10.0);
		AnchorPane.setBottomAnchor(leftBottomButtons, 10.0);
		HBox rightBottomButtons = new HBox(10);
		Button mandelbrotModeButton = new Button("Mandelbrot");
		Button juliaModeButton = new Button("Julia");
		rightBottomButtons.getChildren().add(mandelbrotModeButton);
		rightBottomButtons.getChildren().add(juliaModeButton);
		pane.getChildren().add(rightBottomButtons);
		AnchorPane.setRightAnchor(rightBottomButtons, 10.0);
		AnchorPane.setBottomAnchor(rightBottomButtons, 10.0);
		switch (config.getInputMode()) {
			case MandelbrotImageConfig.INPUT_MODE_ZOOM:
				zoomButton.setDisable(true);
				break;
	
			case MandelbrotImageConfig.INPUT_MODE_INFO:
				infoButton.setDisable(true);
				break;
		}
		switch (config.getImageMode()) {
			case MandelbrotImageConfig.IMAGE_MODE_MANDELBROT:
				mandelbrotModeButton.setDisable(true);
				break;
	
			case MandelbrotImageConfig.IMAGE_MODE_JULIA:
				juliaModeButton.setDisable(true);
				break;
		}
		zoomButton.setOnAction(e -> {
			context.execute(() -> {
				config.setInputMode(MandelbrotImageConfig.INPUT_MODE_ZOOM);
			});
			zoomButton.setDisable(true);
			infoButton.setDisable(false);
		});
		infoButton.setOnAction(e -> {
			context.execute(() -> {
				config.setInputMode(MandelbrotImageConfig.INPUT_MODE_INFO);
			});
			zoomButton.setDisable(false);
			infoButton.setDisable(true);
		});
		mandelbrotModeButton.setOnAction(e -> {
			context.execute(() -> {
				config.setImageMode(MandelbrotImageConfig.IMAGE_MODE_MANDELBROT);
			});
			mandelbrotModeButton.setDisable(true);
			juliaModeButton.setDisable(false);
		});
		juliaModeButton.setOnAction(e -> {
			context.execute(() -> {
				config.setImageMode(MandelbrotImageConfig.IMAGE_MODE_JULIA);
			});
			mandelbrotModeButton.setDisable(false);
			juliaModeButton.setDisable(true);
		});
	}

	@Override
	public void dispose() {
	}
}
