/*
 * NextFractal 1.1.2
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.mandelbrot.renderer.xaos;

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
