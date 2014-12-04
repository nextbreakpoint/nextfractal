package com.nextbreakpoint.nextfractal.flux.render.xaos;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.nextbreakpoint.nextfractal.core.util.Surface;

/**
 * @author Andrea Medeghini
 */
public class XaosRendererData {
	/**
	 * 
	 */
	public BufferedImage newBuffer;
	/**
	 * 
	 */
	public BufferedImage oldBuffer;
	/**
	 * 
	 */
	public double[] newCacheZR;
	/**
	 * 
	 */
	public double[] newCacheZI;
	/**
	 * 
	 */
	public double[] newCacheTR;
	/**
	 * 
	 */
	public double[] newCacheTI;
	/**
	 * 
	 */
	public int[] newCacheTime;
	/**
	 * 
	 */
	public double[] oldCacheZR;
	/**
	 * 
	 */
	public double[] oldCacheZI;
	/**
	 * 
	 */
	public double[] oldCacheTR;
	/**
	 * 
	 */
	public double[] oldCacheTI;
	/**
	 * 
	 */
	public int[] oldCacheTime;
	/**
	 * 
	 */
	public int[] newRGB;
	/**
	 * 
	 */
	public int[] oldRGB;
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
	public XaosRealloc[] reallocX;
	/**
	 * 
	 */
	public XaosRealloc[] reallocY;
	/**
	 * 
	 */
	public XaosDynamic dynamicx;
	/**
	 * 
	 */
	public XaosDynamic dynamicy;
	/**
	 * 
	 */
	public XaosChunkTable moveTable;
	/**
	 * 
	 */
	public XaosChunkTable fillTable;
	/**
	 * 
	 */
	public XaosRealloc[] queue;
	/**
	 * 
	 */
	public double x0 = 0;
	/**
	 * 
	 */
	public double y0 = 0;
	/**
	 * 
	 */
	public long newTime;
	/**
	 * 
	 */
	public long oldTime;
	/**
	 * 
	 */
	public final int[] position = new int[XaosConstants.STEPS];
	/**
	 * 
	 */
	public final int[] offset = new int[XaosConstants.STEPS];

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
		reallocX = null;
		reallocY = null;
		dynamicx = null;
		dynamicy = null;
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
		if (newBuffer != null) {
			newBuffer.flush();
		}
		newBuffer = null;
		newRGB = null;
		if (oldBuffer != null) {
			oldBuffer.flush();
		}
		oldBuffer = null;
		oldRGB = null;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void reallocate(final int width, final int height) {
		positionX = new double[width];
		positionY = new double[height];
		reallocX = new XaosRealloc[width];
		reallocY = new XaosRealloc[height];
		dynamicx = new XaosDynamic(width);
		dynamicy = new XaosDynamic(height);
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
		newBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
		newRGB = ((DataBufferInt) newBuffer.getRaster().getDataBuffer()).getData();
		oldBuffer = new BufferedImage(width, height, Surface.DEFAULT_TYPE);
		oldRGB = ((DataBufferInt) oldBuffer.getRaster().getDataBuffer()).getData();
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
	 * 
	 */
	public void swap() {
		final int[] tmpRGB = oldRGB;
		final BufferedImage tmpBuffer = oldBuffer;
		oldRGB = newRGB;
		oldBuffer = newBuffer;
		newRGB = tmpRGB;
		newBuffer = tmpBuffer;
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
	}
}