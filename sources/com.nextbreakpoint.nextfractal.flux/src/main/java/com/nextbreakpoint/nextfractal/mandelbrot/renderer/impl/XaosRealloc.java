package com.nextbreakpoint.nextfractal.mandelbrot.renderer.impl;

/**
 * @author Andrea Medeghini
 */
class XaosRealloc {
	boolean isCached;
	boolean refreshed;
	boolean recalculate;
	boolean changeDirty;
	boolean dirty;
	boolean line;
	int pos;
	int plus;
	int symTo;
	int symRef;
	double changePosition;
	double position;
	double priority;

	/**
	 * @param line
	 */
	public XaosRealloc(final boolean line) {
		this.line = line;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<pos = " + pos + ", symref = " + symRef + ", symto = " + symTo + ", plus = " + plus + ", dirty = " + dirty + ", recalculate = " + recalculate + ", line = " + line + ", priority = " + priority + ", position = " + position + ", iscached = " + isCached + ">";
	}
}
