package com.nextbreakpoint.nextfractal.flux.mandelbrot.xaos;

/**
 * @author Andrea Medeghini
 */
public class XaosChunk {
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