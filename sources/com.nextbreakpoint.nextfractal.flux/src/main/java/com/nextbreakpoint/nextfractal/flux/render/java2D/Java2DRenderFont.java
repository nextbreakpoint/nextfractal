package com.nextbreakpoint.nextfractal.flux.render.java2D;

import java.awt.Font;

import com.nextbreakpoint.nextfractal.flux.render.RenderFont;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;

public class Java2DRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
