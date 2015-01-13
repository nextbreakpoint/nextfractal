package com.nextbreakpoint.nextfractal.mandelbrot.renderer.xaos;

/**
 * @author Andrea Medeghini
 */
class XaosDynamic {
	int[] delta;
	XaosPrice[] oldBest;
	XaosPrice[] newBest;
	XaosPrice[] calData;
	XaosPrice[] conData;

	/**
	 * @param size
	 */
	XaosDynamic(final int size) {
		delta = new int[size + 1];
		oldBest = new XaosPrice[size];
		newBest = new XaosPrice[size];
		calData = new XaosPrice[size];
		conData = new XaosPrice[size << XaosConstants.DSIZE];
		for (int i = 0; i < size; i++) {
			calData[i] = new XaosPrice();
		}
		for (int i = 0; i < (size << XaosConstants.DSIZE); i++) {
			conData[i] = new XaosPrice();
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		oldBest = null;
		newBest = null;
		calData = null;
		conData = null;
		super.finalize();
	}

	/**
	 * 
	 */
	public void swap() {
		final XaosPrice[] tmp_best = newBest;
		newBest = oldBest;
		oldBest = tmp_best;
	}
}
