package com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.plugin;

import javafx.scene.layout.Pane;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.editor.MandelbrotEditorPane;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.ui.render.MandelbrotRenderPane;
import com.nextbreakpoint.nextfractal.flux.ui.plugin.UIFactory;

public class MandelbrotUIFactory implements UIFactory {
	public String getId() {
		return "MandelbrotUIFactory";
	}
	
	@Override
	public Pane createEditorPane() {
		return new MandelbrotEditorPane();
	}

	@Override
	public Pane createRenderPane(int width, int height) {
		return new MandelbrotRenderPane(width, height);
	}
}
