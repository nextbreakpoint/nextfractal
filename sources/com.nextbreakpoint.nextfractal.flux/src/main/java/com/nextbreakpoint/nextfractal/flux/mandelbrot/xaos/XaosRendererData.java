package com.nextbreakpoint.nextfractal.flux.mandelbrot.xaos;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData;
import com.nextbreakpoint.nextfractal.flux.render.RenderFactory;

/**
 * @author Andrea Medeghini
 */
public class XaosRendererData extends RendererData {
	private long newTime;
	private long oldTime;
	private XaosRealloc[] reallocX;
	private XaosRealloc[] reallocY;
	private XaosDynamic dynamicX;
	private XaosDynamic dynamicY;
	private XaosChunkTable moveTable;
	private XaosChunkTable fillTable;
	private XaosRealloc[] queue;
	private final int[] position = new int[XaosConstants.STEPS];
	private final int[] offset = new int[XaosConstants.STEPS];

	public XaosRendererData(RenderFactory renderFactory) {
		super(renderFactory);
	}

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
		super.free();
	}

	/**
	 * @see com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.RendererData#init(int, int, int)
	 */
	public void init(final int width, final int height) {
		super.init(width, height);
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
	}

	public long newTime() {
		return newTime;
	}

	public long oldTime() {
		return oldTime;
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

	public void setNewTime(long newTime) {
		this.newTime = newTime;
	}

	public void setOldTime(long oldTime) {
		this.oldTime = oldTime;
	}
}