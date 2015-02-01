package com.nextbreakpoint.nextfractal.mandelbrot.renderer.impl;

/**
 * @author Andrea Medeghini
 */
class XaosChunk {
	int length;
	int from;
	int to;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<from = " + from + ", to = " + to + ", length = " + length + ">";
	}
}