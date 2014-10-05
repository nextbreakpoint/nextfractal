package com.nextbreakpoint.nextfractal.twister.renderer.java2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.util.Surface;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer;
import com.nextbreakpoint.nextfractal.twister.renderer.RenderImage;

public class Java2DRenderBuffer implements RenderBuffer {
	private final BufferedImage image;
	private final Graphics2D g2d;
	
	public Java2DRenderBuffer(int widh, int height) {
		image = new BufferedImage(widh, height, Surface.DEFAULT_TYPE);
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
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#dispose()
	 */
	@Override
	public void dispose() {
		if (g2d != null) {
			g2d.dispose();
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#clear()
	 */
	@Override
	public void clear() {
		g2d.clearRect(0, 0, image.getWidth(), image.getHeight());
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#update(int[])
	 */
	@Override
	public void update(int[] pixels) {
		if (pixels.length <= getWidth() * getHeight()) {
			System.arraycopy(pixels, 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, pixels.length);
		}
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#getWidth()
	 */
	@Override
	public int getWidth() {
		return image.getWidth();
	}
	
	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#getHeight()
	 */
	@Override
	public int getHeight() {
		return image.getHeight();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.twister.renderer.RenderBuffer#getImage()
	 */
	@Override
	public RenderImage getImage() {
		return new Java2DRenderImage(image);
	}
}
