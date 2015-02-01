package com.nextbreakpoint.nextfractal.mandelbrot.renderer.impl;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererData;

/**
 * @author Andrea Medeghini
 */
class XaosRendererData extends RendererData {
	private final int[] position = new int[XaosConstants.STEPS];
	private final int[] offset = new int[XaosConstants.STEPS];
	private XaosRealloc[] reallocX;
	private XaosRealloc[] reallocY;
	private XaosDynamic dynamicX;
	private XaosDynamic dynamicY;
	private XaosChunkTable moveTable;
	private XaosChunkTable fillTable;
	private XaosRealloc[] queue;

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererData#free()
	 */
	@Override
	public void free() {
		reallocX = null;
		reallocY = null;
		dynamicX = null;
		dynamicY = null;
		moveTable = null;
		fillTable = null;
		queue = null;
		super.free();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererData#realloc(int, int)
	 */
	@Override
	protected void realloc(final int width, final int height) {
		super.realloc(width, height);
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
		}
		for (int i = 0; i < height; i++) {
			reallocY[i] = new XaosRealloc(true);
			reallocY[i].pos = i;
		}
	}

	public XaosRealloc[] reallocX() {
		return reallocX;
	}

	public XaosRealloc[] reallocY() {
		return reallocY;
	}

	public XaosDynamic dynamicX() {
		return dynamicX;
	}

	public XaosDynamic dynamicY() {
		return dynamicY;
	}

	public XaosChunkTable moveTable() {
		return moveTable;
	}

	public XaosChunkTable fillTable() {
		return fillTable;
	}

	public XaosRealloc[] queue() {
		return queue;
	}

	public int[] position() {
		return position;
	}

	public int[] offset() {
		return offset;
	}
}