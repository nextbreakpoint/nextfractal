package com.nextbreakpoint.nextfractal.flux.mandelbrot.xaos;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData;

/**
 * @author Andrea Medeghini
 */
public class XaosRendererData extends RendererData {
	public double[] newCacheZR;
	public double[] newCacheZI;
	public double[] newCacheTR;
	public double[] newCacheTI;
	public int[] newCacheTime;
	public double[] oldCacheZR;
	public double[] oldCacheZI;
	public double[] oldCacheTR;
	public double[] oldCacheTI;
	public int[] oldCacheTime;
	public int[] newRGB;
	public int[] oldRGB;
	public long newTime;
	public long oldTime;
	public XaosRealloc[] reallocX;
	public XaosRealloc[] reallocY;
	public XaosDynamic dynamicX;
	public XaosDynamic dynamicY;
	public XaosChunkTable moveTable;
	public XaosChunkTable fillTable;
	public XaosRealloc[] queue;
	public final int[] position = new int[XaosConstants.STEPS];
	public final int[] offset = new int[XaosConstants.STEPS];

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData#free()
	 */
	public void free() {
		reallocX = null;
		reallocY = null;
		dynamicX = null;
		dynamicY = null;
		moveTable = null;
		fillTable = null;
		queue = null;
		newCacheZR = null;
		newCacheZI = null;
		newCacheTR = null;
		newCacheTI = null;
		newCacheTime = null;
		oldCacheZR = null;
		oldCacheZI = null;
		oldCacheTR = null;
		oldCacheTI = null;
		oldCacheTime = null;
		oldRGB = null;
		super.free();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData#init(int, int, int)
	 */
	public void init(final int width, final int height, final int depth) {
		super.init(width, height, depth);
		reallocX = new XaosRealloc[width];
		reallocY = new XaosRealloc[height];
		dynamicX = new XaosDynamic(width);
		dynamicY = new XaosDynamic(height);
		moveTable = new XaosChunkTable(width);
		fillTable = new XaosChunkTable(width);
		queue = new XaosRealloc[reallocX.length + reallocY.length];
		for (int i = 0; i < width; i++) {
			reallocX[i] = new XaosRealloc(false);
			reallocX[i].pos = i;
			positionX[i] = 0;
		}
		for (int i = 0; i < height; i++) {
			reallocY[i] = new XaosRealloc(true);
			reallocY[i].pos = i;
			positionY[i] = 0;
		}
		newRGB = new int[width * height];
		oldRGB = new int[width * height];
		newCacheZR = new double[width * height];
		newCacheZI = new double[width * height];
		newCacheTR = new double[width * height];
		newCacheTI = new double[width * height];
		newCacheTime = new int[width * height];
		oldCacheZR = new double[width * height];
		oldCacheZI = new double[width * height];
		oldCacheTR = new double[width * height];
		oldCacheTI = new double[width * height];
		oldCacheTime = new int[width * height];
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData#swap()
	 */
	public void swap() {
		final int[] tmpRGB = oldRGB;
		oldRGB = newRGB;
		newRGB = tmpRGB;
		final double[] tmpCacheZR = oldCacheZR;
		final double[] tmpCacheZI = oldCacheZI;
		final double[] tmpCacheTR = oldCacheTR;
		final double[] tmpCacheTI = oldCacheTI;
		final int[] tmpCacheTime = oldCacheTime;
		oldCacheZR = newCacheZR;
		oldCacheZI = newCacheZI;
		oldCacheTR = newCacheTR;
		oldCacheTI = newCacheTI;
		oldCacheTime = newCacheTime;
		newCacheZR = tmpCacheZR;
		newCacheZI = tmpCacheZI;
		newCacheTR = tmpCacheTR;
		newCacheTI = tmpCacheTI;
		newCacheTime = tmpCacheTime;
		super.swap();
	}
}