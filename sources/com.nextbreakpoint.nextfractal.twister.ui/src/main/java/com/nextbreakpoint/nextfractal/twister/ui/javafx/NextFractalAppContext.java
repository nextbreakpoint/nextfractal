package com.nextbreakpoint.nextfractal.twister.ui.javafx;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.Pane;

public interface NextFractalAppContext {
	public void showConfigNode(Pane node);

	public void discardConfigNode();

	public void showEditorNode(Pane node);

	public void discardEditorNode();

	public Dimension2D getConfigViewSize();

	public Dimension2D getEditorViewSize();
}
