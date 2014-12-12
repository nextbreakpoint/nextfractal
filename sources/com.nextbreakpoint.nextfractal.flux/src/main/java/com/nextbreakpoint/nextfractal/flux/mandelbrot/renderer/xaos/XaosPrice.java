package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer.xaos;

class XaosPrice {
	XaosPrice previous;
	int pos;
	int price;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<price = " + price + ", pos = " + pos + ">";
	}
}