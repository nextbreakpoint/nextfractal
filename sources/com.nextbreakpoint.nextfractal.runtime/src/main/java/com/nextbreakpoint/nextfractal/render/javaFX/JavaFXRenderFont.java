package com.nextbreakpoint.nextfractal.render.javaFX;

import javafx.scene.text.Font;

import com.nextbreakpoint.nextfractal.render.RenderFont;
import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;

public class JavaFXRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
