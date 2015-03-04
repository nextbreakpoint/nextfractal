package com.nextbreakpoint.nextfractal.renderer.java2D;

import java.awt.Font;

import com.nextbreakpoint.nextfractal.renderer.RendererFont;
import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;

public class Java2DRendererFont implements RendererFont {
	private Font font;
	
	@Override
	public void setFont(RendererGraphicsContext context) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
