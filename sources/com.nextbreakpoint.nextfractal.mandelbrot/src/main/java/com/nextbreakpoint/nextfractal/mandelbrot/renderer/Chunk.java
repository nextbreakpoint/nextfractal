package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

/**
 * @author Andrea Medeghini
 */
public class Chunk {
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