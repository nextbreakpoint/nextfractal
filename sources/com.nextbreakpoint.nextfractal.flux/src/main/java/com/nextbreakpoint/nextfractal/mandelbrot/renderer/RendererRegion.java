package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.mandelbrot.core.Number;

/**
 * @author Andrea Medeghini
 */
public class RendererRegion {
	private double x0;
	private double y0;
	private double x1;
	private double y1;
	private Number center;
	private Number size;

	public RendererRegion() {
	}
	
	public RendererRegion(Number a, Number b) {
		setPoints(a, b);
	}
	
	public RendererRegion(Number[] points) {
		setPoints(points[0], points[1]);
	}

	public void setPoints(Number a, Number b) {
		this.x0 = a.r();
		this.y0 = a.i();
		this.x1 = b.r();
		this.y1 = b.i();
		size = new Number(x1 - x0, y1 - y0);
		center = new Number((x0 + x1) / (2 * size.r()), (y0 + y1) / (2 * size.i()));
	}

	public Number getCenter() {
		return center;
	}

	public Number getSize() {
		return size;
	}
	
	/**
	 * @return
	 */
	public double left() {
		return x0;
	}

	/**
	 * @return
	 */
	public double right() {
		return x1;
	}

	/**
	 * @return
	 */
	public double bottom() {
		return y0;
	}

	/**
	 * @return
	 */
	public double top() {
		return y1;
	}

	@Override
	public String toString() {
		return "[a=(" + x0 + "," + y0 + "), b=(" + x1 + "," + y1 + "), center=" + center + ", size=" + size + "]";
	}
}