package com.nextbreakpoint.nextfractal.mandelbrot.renderer;

/**
 * @author Andrea Medeghini
 */
public class ChunkTable {
	/**
	 * 
	 */
	public Chunk[] data;

	/**
	 * @param width
	 */
	public ChunkTable(final int width) {
		data = new Chunk[width + 1];
		for (int i = 0; i <= width; i++) {
			data[i] = new Chunk();
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