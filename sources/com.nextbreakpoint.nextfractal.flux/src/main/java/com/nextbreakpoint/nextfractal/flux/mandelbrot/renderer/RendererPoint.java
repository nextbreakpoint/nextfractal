/*
 * NextFractal 7.0 
 * http://www.nextbreakpoint.com
 *
 * Copyright 2001, 2015 Andrea Medeghini
 * andrea@nextbreakpoint.com
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
package com.nextbreakpoint.nextfractal.flux.mandelbrot.renderer;

import com.nextbreakpoint.nextfractal.flux.mandelbrot.MutableNumber;
import com.nextbreakpoint.nextfractal.flux.mandelbrot.Number;

/**
 * @author Andrea Medeghini
 */
public class RendererPoint {
	protected final MutableNumber[] vars;
	protected final MutableNumber x;
	protected final MutableNumber w;
	protected int n;
	
	public RendererPoint(int depth) {
		vars = new MutableNumber[depth];
		for (int i = 0; i < depth; i++) {
			vars[i] = new MutableNumber(0, 0);
		}
		x = new MutableNumber(0, 0);
		w = new MutableNumber(0, 0);
		n = 0;
	}
	
	public MutableNumber[] getVars() {
		return vars;
	}
	
	public int getN() {
		return n;
	}
	
	public void setN(int n) {
		this.n = n;
	}

	public Number getX() {
		return x;
	}

	public void setX(Number x) {
		this.x.set(x);
	}

	public void setX(double r, double i) {
		x.set(r, i);
	}

	public Number getW() {
		return w;
	}

	public void setW(Number w) {
		this.w.set(w);
	}

	public void setW(double r, double i) {
		w.set(r, i);
	}
}
