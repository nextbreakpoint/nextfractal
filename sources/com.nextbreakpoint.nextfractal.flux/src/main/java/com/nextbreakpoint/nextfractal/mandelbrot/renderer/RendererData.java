package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.nextbreakpoint.nextfractal.mandelbrot.core.MutableNumber;
import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

/**
 * @author Andrea Medeghini
 */
public class RendererData {
	protected static final Logger logger = Logger.getLogger(RendererData.class.getName());
	protected double[] positionX;
	protected double[] positionY;
	protected RendererRegion region;
	protected MutableNumber point;
	protected int[] newPixels;
	protected int[] oldPixels;
	protected List<double[]> newCache;
	protected List<double[]> oldCache;
	protected int width; 
	protected int height;
	protected int depth;

	public RendererData() {
		region = new RendererRegion();
		point = new MutableNumber(0, 0);
	}

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
	 * @param size
	 */
	public void setSize(final int width, final int height, final int depth) {
		if (this.width != width || this.height != height) {
			realloc(width, height);
			this.width = width;
			this.height = height;
		}
		if (this.depth != depth) {
			newCache = new ArrayList<double[]>(depth);
			oldCache = new ArrayList<double[]>(depth);
			for (int i = 0; i < depth; i++) {
				newCache.add(new double[width * height * 2]);
				oldCache.add(new double[width * height * 2]);
			}
			this.depth = depth;
		}
	}

	/**
	 * @param width
	 * @param height
	 */
	protected void realloc(final int width, final int height) {
		positionX = new double[width];
		positionY = new double[height];
		newPixels = new int[width * height];
		oldPixels = new int[width * height];
	}

	/**
	 * 
	 */
	public void swap() {
		final List<double[]> tmpCache = oldCache;
		oldCache = newCache;
		newCache = tmpCache;
		final int[] tmpPixels = oldPixels;
		oldPixels = newPixels;
		newPixels = tmpPixels;
	}

	/**
	 * @return
	 */
	public double left() {
		return region.left();
	}

	/**
	 * @return
	 */
	public double right() {
		return region.right();
	}

	/**
	 * @return
	 */
	public double bottom() {
		return region.bottom();
	}

	/**
	 * @return
	 */
	public double top() {
		return region.top();
	}

	/**
	 * @param region
	 */
	public void setRegion(RendererRegion region) {
		this.region = region;
	}
	
	/**
	 * @return
	 */
	public Number point() {
		return point;
	}

	/**
	 * @param offset
	 * @param argb
	 */
	public void setPixel(int offset, int argb) {
		newPixels[offset] = argb;
	}

	/**
	 * @param offset
	 * @return
	 */
	public int getPixel(int offset) {
		return newPixels[offset];
	}

	/**
	 * @return
	 */
	public int[] getPixels() {
		return newPixels;
	}

	/**
	 * @param i
	 * @return
	 */
	public double positionX(int i) {
		return positionX[i];
	}

	/**
	 * @param i
	 * @return
	 */
	public double positionY(int i) {
		return positionY[i];
	}

	/**
	 * @return
	 */
	public double[] positionX() {
		return positionX;
	}

	/**
	 * @return
	 */
	public double[] positionY() {
		return positionY;
	}

	/**
	 * @param i
	 * @param position
	 */
	public void setPositionX(int i, double position) {
		positionX[i] = position;
	}

	/**
	 * @param i
	 * @param position
	 */
	public void setPositionY(int i, double position) {
		positionY[i] = position;
	}

	/**
	 * 
	 */
	public void initPositions() {
		final int sizex = width;
		final int sizey = height;
		final double stepx = (right() - left()) / (sizex - 1);
		final double stepy = (top() - bottom()) / (sizey - 1);
		double posx = left();
		double posy = bottom();
		for (int x = 0; x < sizex; x++) {
			positionX[x] = posx;
			posx += stepx;
		}
		for (int y = 0; y < sizey; y++) {
			positionY[y] = posy;
			posy += stepy;
		}
	}

	/**
	 * @return
	 */
	public RendererPoint newPoint() {
		return new RendererPoint(depth);
	}

	/**
	 * @param offset
	 * @param p
	 */
	public void getPoint(int offset, RendererPoint p) {
		for (int j = 0; j < depth; j++) {
			double[] cache = oldCache.get(j);
			double r = cache[offset * 2 + 0];
			double i = cache[offset * 2 + 1];
			p.vars()[j].set(r, i);
		}
	}

	/**
	 * @param offset
	 * @param p
	 */
	public void setPoint(int offset, RendererPoint p) {
		for (int j = 0; j < depth; j++) {
			MutableNumber var = p.vars()[j];
			double[] cache = newCache.get(j);
			cache[offset * 2 + 0] = var.r();
			cache[offset * 2 + 1] = var.i();
		}
	}

	/**
	 * @param from
	 * @param to
	 * @param length
	 */
	public void movePixels(int from, int to, int length) {
		System.arraycopy(newPixels, from, newPixels, to, length);
	}

	/**
	 * @param from
	 * @param to
	 * @param length
	 */
	public void moveCache(int from, int to, int length) {
		for (int i = 0; i < depth; i++) {
			System.arraycopy(newCache.get(i), from * 2, newCache.get(i), to * 2, length * 2);
		}
	}

	/**
	 * @param from
	 * @param to
	 * @param length
	 */
	public void copyPixels(int from, int to, int length) {
		System.arraycopy(oldPixels, from, newPixels, to, length);
	}

	/**
	 * @param from
	 * @param to
	 * @param length
	 */
	public void copyCache(int from, int to, int length) {
		for (int i = 0; i < depth; i++) {
			System.arraycopy(oldCache.get(i), from * 2, newCache.get(i), to * 2, length * 2);
		}
	}

	/**
	 * 
	 */
	public void clearPixels() {
		for (int i = 0; i < newPixels.length; i++) {
			newPixels[i] = 0xFF000000;
		}
	}
}