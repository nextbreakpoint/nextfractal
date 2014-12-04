package com.nextbreakpoint.nextfractal.flux.render.xaos;

/**
 * @author Andrea Medeghini
 */
public class XaosDynamic {
	/**
	 * 
	 */
	public int[] delta;
	/**
	 * 
	 */
	public Data[] oldBest;
	/**
	 * 
	 */
	public Data[] newBest;
	/**
	 * 
	 */
	public Data[] calData;
	/**
	 * 
	 */
	public Data[] conData;

	/**
	 * @param size
	 */
	public XaosDynamic(final int size) {
		delta = new int[size + 1];
		oldBest = new Data[size];
		newBest = new Data[size];
		calData = new Data[size];
		conData = new Data[size << XaosConstants.DSIZE];
		for (int i = 0; i < size; i++) {
			calData[i] = new Data();
		}
		for (int i = 0; i < (size << XaosConstants.DSIZE); i++) {
			conData[i] = new Data();
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
		final XaosDynamic.Data[] tmp_best = newBest;
		newBest = oldBest;
		oldBest = tmp_best;
	}

	/**
	 * @author Andrea Medeghini
	 */
	public class Data {
		Data previous;
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
}
