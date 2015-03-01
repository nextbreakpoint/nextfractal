package com.nextbreakpoint.nextfractal.render.java2D;

import java.awt.Color;

import com.nextbreakpoint.nextfractal.render.RenderColor;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

public class Java2DRenderColor implements RenderColor {
	private Color color;
	
	public Java2DRenderColor(double red, double green, double blue, double opacity) {
		color = new Color((float)red, (float)green, (float)blue, (float)opacity);
	}

	@Override
	public void setStroke(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setColor(color);
	}
	
	@Override
	public void setFill(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setColor(color);
	}
}
