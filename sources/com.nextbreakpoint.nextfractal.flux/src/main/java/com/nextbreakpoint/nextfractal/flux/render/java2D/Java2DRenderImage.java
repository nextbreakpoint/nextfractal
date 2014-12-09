package com.nextbreakpoint.nextfractal.flux.render.java2D;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.nextbreakpoint.nextfractal.flux.render.RenderGraphicsContext;
import com.nextbreakpoint.nextfractal.flux.render.RenderImage;

public class Java2DRenderImage implements RenderImage {
	private Image image;
	
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
}
