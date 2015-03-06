package com.nextbreakpoint.nextfractal.core.renderer;


/**
 * @author amedeghini
 *
 */
public class RendererSurface {
	private RendererBuffer buffer;
	private RendererAffine affine;
	private RendererSize size;
	private RendererTile tile;

	public RendererBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(RendererBuffer buffer) {
		this.buffer = buffer;
	}

	public RendererAffine getAffine() {
		return affine;
	}

	public void setAffine(RendererAffine affine) {
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
		if (buffer != null) {
			buffer.dispose();
			buffer = null;
		}
	}
}
