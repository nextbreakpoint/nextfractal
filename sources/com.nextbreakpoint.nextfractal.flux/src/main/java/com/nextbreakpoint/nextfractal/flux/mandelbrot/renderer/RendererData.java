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
	public int[] newPixels;
	public int[] oldPixels;
	public Number[][] newCache;
	public Number[][] oldCache;
	
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
		newPixels = null;
		oldPixels = null;
		region = null;
		point = null;
		newCache = null;
		oldCache = null;
	}

	/**
	 * @param width
	 * @param height
	 * @param depth
	 */
	public void init(final int width, final int height, final int depth) {
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
		newPixels = new int[width * height];
		oldPixels = new int[width * height];
		newCache = new Number[width * height][depth];
		oldCache = new Number[width * height][depth];
	}

	/**
	 * 
	 */
	public void swap() {
		final Number[][] tmpCache = oldCache;
		oldCache = newCache;
		newCache = tmpCache;
		final int[] tmpPixels = oldPixels;
		oldPixels = newPixels;
		newPixels = tmpPixels;
	}
}