package com.nextbreakpoint.nextfractal.core.renderer.javaFX;

import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.core.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;

public class JavaFXRendererColor implements RendererColor {
	private Color color;
	
	public JavaFXRendererColor(double red, double green, double blue, double opacity) {
		color = new Color(red, green, blue, opacity);
	}

	@Override
	public void setStroke(RendererGraphicsContext context) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().setStroke(color);
	}
	
	@Override
	public void setFill(RendererGraphicsContext context) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().setFill(color);
	}
}
