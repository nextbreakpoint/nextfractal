package com.nextbreakpoint.nextfractal.mandelbrot;

import com.nextbreakpoint.nextfractal.mandelbrot.renderer.RendererSize;

public class ExportSession {
	private MandelbrotData data;
	private RendererSize size;
	private DataEncoder encoder;

	public MandelbrotData getData() {
		return data;
	}

	public void setData(MandelbrotData data) {
		this.data = data;
	}

	public RendererSize getSize() {
		return size;
	}

	public void setSize(RendererSize size) {
		this.size = size;
	}

	public DataEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(DataEncoder encoder) {
		this.encoder = encoder;
	}
}
