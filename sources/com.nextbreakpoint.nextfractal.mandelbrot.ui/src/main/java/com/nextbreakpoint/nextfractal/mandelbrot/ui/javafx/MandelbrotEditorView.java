package com.nextbreakpoint.nextfractal.mandelbrot.ui.javafx;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import com.nextbreakpoint.nextfractal.core.tree.NodeSession;
import com.nextbreakpoint.nextfractal.core.ui.javafx.View;
import com.nextbreakpoint.nextfractal.core.ui.javafx.ViewContext;
import com.nextbreakpoint.nextfractal.core.util.RenderContext;
import com.nextbreakpoint.nextfractal.mandelbrot.MandelbrotConfig;

public class MandelbrotEditorView extends View {

	public MandelbrotEditorView(MandelbrotConfig config, ViewContext viewContext, RenderContext context, NodeSession session) {
		// TODO Auto-generated constructor stub
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
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
