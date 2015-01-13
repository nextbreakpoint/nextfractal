package com.nextbreakpoint.nextfractal.render.javaFX;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.RenderImage;

public class JavaFXRenderImage implements RenderImage {
	private Image image;
	
	public JavaFXRenderImage(WritableImage image) {
		this.image = image;
	}

	@Override
	public void draw(RenderGraphicsContext context, int x, int y) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().drawImage(image, x, y);
	}
	
	@Override
	public void draw(RenderGraphicsContext context, int x, int y, int w, int h) {
		((JavaFXRenderGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, w, h);
	}
}
