package com.nextbreakpoint.nextfractal.renderer.javaFX;

import javafx.scene.text.Font;

import com.nextbreakpoint.nextfractal.renderer.RendererFont;
import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;

public class JavaFXRendererFont implements RendererFont {
	private Font font;
	
	@Override
	public void setFont(RendererGraphicsContext context) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().setFont(font);
	}
}
