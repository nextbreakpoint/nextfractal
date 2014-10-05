package com.nextbreakpoint.nextfractal.twister.renderer.java2D;

import java.awt.Font;

import com.nextbreakpoint.nextfractal.twister.renderer.RenderFont;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;

public class Java2DRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
