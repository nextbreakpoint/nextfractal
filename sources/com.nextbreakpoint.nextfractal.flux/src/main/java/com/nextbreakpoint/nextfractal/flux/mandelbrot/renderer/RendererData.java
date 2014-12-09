package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;
import com.nextbreakpoint.nextfractal.flux.render.RenderBuffer;
import com.nextbreakpoint.nextfractal.flux.render.RenderFactory;

/**
 * @author Andrea Medeghini
 */
public class RendererData {
	protected RenderFactory renderFactory;
	protected RenderBuffer renderBuffer;
	protected double[] positionX;
	protected double[] positionY;
	protected Number[] region;
	protected Number point;
	protected int[] newPixels;
	protected int[] oldPixels;
	protected Number[][] newCache;
	protected Number[][] oldCache;

	/**
	 * @param renderFactory
	 */
	public RendererData(RenderFactory renderFactory) {
		this.renderFactory = renderFactory;
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
	 * @param depth
	 */
	public void init(final int width, final int height) {
		free();
		renderBuffer = renderFactory.createBuffer(width, height);
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
		newCache = new Number[width * height][1];//TODO depth
		oldCache = new Number[width * height][1];//TODO depth
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

	/**
	 * 
	 */
	public void copy() {
		renderBuffer.update(newPixels);
	}

	/**
	 * @return
	 */
	public double left() {
		return region[0].r();
	}

	/**
	 * @return
	 */
	public double right() {
		return region[1].r();
	}

	/**
	 * @return
	 */
	public double bottom() {
		return region[0].i();
	}

	/**
	 * @return
	 */
	public double top() {
		return region[1].i();
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
		final int sizex = renderBuffer.getWidth();
		final int sizey = renderBuffer.getHeight();
		final double stepx = (right() - left()) / (sizex - 1);
		final double stepy = (bottom() - top()) / (sizey - 1);
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
}