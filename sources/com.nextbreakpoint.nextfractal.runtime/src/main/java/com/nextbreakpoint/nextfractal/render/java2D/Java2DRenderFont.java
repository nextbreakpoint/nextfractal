package com.nextbreakpoint.nextfractal.render.java2D;

import java.awt.Font;

import com.nextbreakpoint.nextfractal.render.RenderFont;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

public class Java2DRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
