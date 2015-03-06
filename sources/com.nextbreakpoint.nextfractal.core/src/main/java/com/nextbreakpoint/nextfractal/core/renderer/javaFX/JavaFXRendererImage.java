package com.nextbreakpoint.nextfractal.core.renderer.javaFX;

import java.nio.IntBuffer;

import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

import com.nextbreakpoint.nextfractal.core.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.core.renderer.RendererImage;

public class JavaFXRendererImage implements RendererImage {
	private WritableImage image;
	
	public JavaFXRendererImage(WritableImage image) {
		this.image = image;
	}

	@Override
	public void draw(RendererGraphicsContext context, int x, int y) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().drawImage(image, x, y);
	}
	
	@Override
	public void draw(RendererGraphicsContext context, int x, int y, int w, int h) {
		((JavaFXRendererGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, w, h);
	}

	@Override
	public void getPixels(IntBuffer pixels) {
		image.getPixelReader().getPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), WritablePixelFormat.getIntArgbInstance(), pixels, (int)image.getWidth());
	}
}
