package com.nextbreakpoint.nextfractal.twister.renderer.java2D;

import java.awt.Color;

import com.nextbreakpoint.nextfractal.twister.renderer.RenderColor;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;

public class Java2DRenderColor implements RenderColor {
	private Color color;
	
	public Java2DRenderColor(int red, int green, int blue, int opacity) {
		color = new Color(red, green, blue, opacity);
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
