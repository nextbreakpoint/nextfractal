/*
 * NextFractal 1.0.1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
