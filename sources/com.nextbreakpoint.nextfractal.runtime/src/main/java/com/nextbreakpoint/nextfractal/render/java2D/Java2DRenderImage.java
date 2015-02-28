package com.nextbreakpoint.nextfractal.render.java2D;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.IntBuffer;

import com.nextbreakpoint.nextfractal.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.render.RenderImage;

public class Java2DRenderImage implements RenderImage {
	private BufferedImage image;
	
	public Java2DRenderImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void draw(RenderGraphicsContext context, int x, int y) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, null);
	}
	
	@Override
	public void draw(RenderGraphicsContext context, int x, int y, int w, int h) {
		((Java2DRenderGraphicsContext)context).getGraphicsContext().drawImage(image, x, y, w, h, null);
	}

	@Override
	public void getPixels(IntBuffer pixels) {
		pixels.put(((DataBufferInt)image.getRaster().getDataBuffer()).getData());
	}
}
