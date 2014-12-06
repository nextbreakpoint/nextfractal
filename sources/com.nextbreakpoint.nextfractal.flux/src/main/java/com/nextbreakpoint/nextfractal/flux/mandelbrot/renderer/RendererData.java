package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;

/**
 * @author Andrea Medeghini
 */
public class RendererData {
	public double[] positionX;
	public double[] positionY;
	public Number[] region;
	public Number point;
	public int[] pixels;
	
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
		pixels = null;
		region = null;
		point = null;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void init(final int width, final int height) {
		free();
		region = new Number[2];
		point = new Number(0, 0);
		positionX = new double[width];
		positionY = new double[height];
		for (int i = 0; i < width; i++) {
			positionX[i] = 0;
		}
		for (int i = 0; i < height; i++) {
			positionY[i] = 0;
		}
		pixels = new int[width * height];
	}
}