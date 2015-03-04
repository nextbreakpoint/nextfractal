package com.nextbreakpoint.nextfractal.renderer.java2D;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.renderer.RendererGraphicsContext;
import com.nextbreakpoint.nextfractal.renderer.RendererImage;

public class Java2DRendererImage implements RendererImage {
	private BufferedImage image;
	
	public Java2DRendererImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void draw(RendererGraphicsContext context, int x, int y) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, null);
	}
	
	@Override
	public void draw(RendererGraphicsContext context, int x, int y, int w, int h) {
		((Java2DRendererGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, w, h, null);
	}

	@Override
	public void getPixels(IntBuffer pixels) {
		pixels.put(((DataBufferInt)image.getRaster().getDataBuffer()).getData());
	}
}
