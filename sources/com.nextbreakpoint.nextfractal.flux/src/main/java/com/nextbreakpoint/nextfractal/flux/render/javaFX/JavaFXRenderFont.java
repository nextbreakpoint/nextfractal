package com.nextbreakpoint.nextfractal.flux.render.javaFX;

import javafx.scene.text.Font;

import com.nextbreakpoint.nextfractal.flux.render.RenderFont;
import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;

public class JavaFXRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
