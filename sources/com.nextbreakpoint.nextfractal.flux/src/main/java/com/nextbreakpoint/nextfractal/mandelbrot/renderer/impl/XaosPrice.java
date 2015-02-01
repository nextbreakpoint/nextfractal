package com.nextbreakpoint.nextfractal.mandelbrot.renderer.impl;

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