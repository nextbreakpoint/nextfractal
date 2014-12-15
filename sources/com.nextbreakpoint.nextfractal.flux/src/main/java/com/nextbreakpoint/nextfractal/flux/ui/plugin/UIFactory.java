package com.nextbreakpoint.nextfractal.flux.ui.plugin;

import javafx.scene.layout.Pane;

public interface UIFactory {
	public String getId();
	
	public Pane createEditorPane();

	public Pane createRenderPane(int width, int height);
}
