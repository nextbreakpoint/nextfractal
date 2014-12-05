package com.nextbreakpoint.nextfractal.flux.render.simple;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.util.Surface;

/**
 * @author Andrea Medeghini
 */
public class RendererData {
	/**
	 * 
	 */
	public BufferedImage newBuffer;
	/**
	 * 
	 */
	public int[] newRGB;
	/**
	 * 
	 */
	public double[] positionX;
	/**
	 * 
	 */
	public double[] positionY;
	/**
	 * 
	 */
	public double x0 = 0;
	/**
	 * 
	 */
	public double y0 = 0;

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		free();
		super.finalize();
	}

	/**
	 * 
	 */
	public void free() {
		positionX = null;
		positionY = null;
		if (newBuffer != null) {
			newBuffer.flush();
		}
		newBuffer = null;
		newRGB = null;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void reallocate(final int width, final int height) {
		free();
		positionX = new double[width];
		positionY = new double[height];
		for (int i = 0; i < width; i++) {
			positionX[i] = 0;
		}
		for (int i = 0; i < height; i++) {
			positionY[i] = 0;
		}
		newBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
		newRGB = ((DataBufferInt) newBuffer.getRaster().getDataBuffer()).getData();
	}
}