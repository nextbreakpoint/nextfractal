package com.nextbreakpoint.nextfractal.twister.renderer.javaFX;

import javafx.scene.text.Font;

import com.nextbreakpoint.nextfractal.twister.renderer.RenderFont;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderGraphicsContext;

public class JavaFXRenderFont implements RenderFont {
	private Font font;
	
	@Override
	public void setFont(RenderGraphicsContext context) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
