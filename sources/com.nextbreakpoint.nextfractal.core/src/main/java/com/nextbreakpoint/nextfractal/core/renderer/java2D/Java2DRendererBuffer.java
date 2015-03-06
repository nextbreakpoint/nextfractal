package com.nextbreakpoint.nextfractal.core.renderer.java2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.renderer.RendererBuffer;
import com.nextbreakpoint.nextfractal.core.renderer.RendererImage;

public class Java2DRendererBuffer implements RendererBuffer {
	private final BufferedImage image;
	private final Graphics2D g2d;
	
	public Java2DRendererBuffer(int widh, int height) {
		image = new BufferedImage(widh, height, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
	}

	/**
	 * @throws Throwable
	 */
	protected void finalize() throws Throwable {
		dispose();
		super.finalize();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#dispose()
	 */
	@Override
	public void dispose() {
		if (g2d != null) {
			g2d.dispose();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#clear()
	 */
	@Override
	public void clear() {
		g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#update(int[])
	 */
	@Override
	public void update(int[] pixels) {
		if (pixels.length <= getWidth() * getHeight()) {
			System.arraycopy(pixels, 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, pixels.length);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getWidth()
	 */
	@Override
	public int getWidth() {
		return image.getWidth();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getHeight()
	 */
	@Override
	public int getHeight() {
		return image.getHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.RendererBuffer.renderer.RenderBuffer#getImage()
	 */
	@Override
	public RendererImage getImage() {
		return new Java2DRendererImage(image);
	}
}
