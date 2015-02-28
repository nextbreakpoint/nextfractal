package com.nextbreakpoint.nextfractal.render.javaFX;

import java.nio.IntBuffer;

import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.RenderImage;

public class JavaFXRenderImage implements RenderImage {
	private WritableImage image;
	
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

	@Override
	public void getPixels(IntBuffer pixels) {
		image.getPixelReader().getPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), WritablePixelFormat.getIntArgbInstance(), pixels, (int)image.getWidth());
	}
}
