package com.nextbreakpoint.nextfractal.flux.render.xaos;

/**
 * @author Andrea Medeghini
 */
public class XaosChunkTable {
	/**
	 * 
	 */
	public XaosChunk[] data;

	/**
	 * @param width
	 */
	public XaosChunkTable(final int width) {
		data = new XaosChunk[width + 1];
		for (int i = 0; i <= width; i++) {
			data[i] = new XaosChunk();
		}
	}

	/**
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		data = null;
		super.finalize();
	}
}