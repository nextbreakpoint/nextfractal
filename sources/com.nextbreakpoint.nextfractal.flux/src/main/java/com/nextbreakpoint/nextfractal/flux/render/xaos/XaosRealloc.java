package com.nextbreakpoint.nextfractal.flux.render.xaos;

/**
 * @author Andrea Medeghini
 */
public class XaosRealloc {
	/**
	 * 
	 */
	public boolean isCached;
	/**
	 * 
	 */
	public boolean refreshed;
	/**
	 * 
	 */
	public boolean recalculate;
	/**
	 * 
	 */
	public boolean changeDirty;
	/**
	 * 
	 */
	public boolean dirty;
	/**
	 * 
	 */
	public boolean line;
	/**
	 * 
	 */
	public int pos;
	/**
	 * 
	 */
	public int plus;
	/**
	 * 
	 */
	public int symTo;
	/**
	 * 
	 */
	public int symRef;
	/**
	 * 
	 */
	public double changePosition;
	/**
	 * 
	 */
	public double position;
	/**
	 * 
	 */
	public double priority;

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
