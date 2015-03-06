package com.nextbreakpoint.nextfractal.core.renderer.java2D;

import java.awt.Color;

import com.nextbreakpoint.nextfractal.core.renderer.RendererColor;
import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;

public class Java2DRendererColor implements RendererColor {
	private Color color;
	
	public Java2DRendererColor(double red, double green, double blue, double opacity) {
		color = new Color((float)red, (float)green, (float)blue, (float)opacity);
	}

	@Override
	public void setStroke(RendererGraphicsContext context) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().setColor(color);
	}
	
	@Override
	public void setFill(RendererGraphicsContext context) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().setColor(color);
	}
}
