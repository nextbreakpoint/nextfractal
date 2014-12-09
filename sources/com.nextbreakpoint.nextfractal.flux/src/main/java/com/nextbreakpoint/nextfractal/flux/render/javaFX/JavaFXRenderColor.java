package com.nextbreakpoint.nextfractal.flux.render.javaFX;

import javafx.scene.paint.Color;

import com.nextbreakpoint.nextfractal.flux.render.RenderColor;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;

public class JavaFXRenderColor implements RenderColor {
	private Color color;
	
	public JavaFXRenderColor(int red, int green, int blue, int opacity) {
		color = new Color(red, green, blue, opacity);
	}

	@Override
	public void setStroke(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setStroke(color);
	}
	
	@Override
	public void setFill(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setFill(color);
	}
}
