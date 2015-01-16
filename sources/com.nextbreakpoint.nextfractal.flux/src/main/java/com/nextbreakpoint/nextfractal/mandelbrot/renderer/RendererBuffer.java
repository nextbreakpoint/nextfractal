package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.render.RenderAffine;
import com.nextbreakpoint.nextfractal.render.RenderBuffer;

/**
 * @author amedeghini
 *
 */
public class RendererBuffer {
	private RenderBuffer buffer;
	private RenderAffine affine;
	private RendererSize size;
	private RendererTile tile;

	public RenderBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(RenderBuffer buffer) {
		this.buffer = buffer;
	}

	public RenderAffine getAffine() {
		return affine;
	}

	public void setAffine(RenderAffine affine) {
		this.affine = affine;
	}

	public RendererSize getSize() {
		return size;
	}

	public void setSize(RendererSize size) {
		this.size = size;
	}

	public RendererTile getTile() {
		return tile;
	}

	public void setTile(RendererTile tile) {
		this.tile = tile;
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
