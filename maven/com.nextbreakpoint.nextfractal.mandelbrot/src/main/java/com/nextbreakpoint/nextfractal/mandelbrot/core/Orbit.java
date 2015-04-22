/*
 * NextFractal 1.0
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
package com.nextbreakpoint.nextfractal.mandelbrot.core;

import java.util.List;


public abstract class Orbit {
	protected Number[] region = new Number[2];
	protected Number point = new Number(0, 0);
	protected Number x = new Number(0,0);
	protected Number w = new Number(0,0);
	protected Number n = new Number(0);
	protected Scope scope;

	public Orbit() {
		region[0] = new MutableNumber();
		region[1] = new MutableNumber();
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public void setX(Number x) {
		this.x = x;
	}

	public void setW(Number w) {
		this.w = w;
	}

	public Number getX() {
		return x;
	}

	public Number getW() {
		return w;
	}

	public Number getN() {
		return n;
	}

	protected Trap trap(Number center) {
		return new Trap(center);
	}
	
	public abstract void init();

	public abstract void render(List<Number[]> states);

	public void getState(MutableNumber[] state) {
		scope.getState(state);
	}

	public int stateSize() {
		return scope.stateSize();
	}

	public Number getVariable(int index) {
		return scope.getVariable(index);
	}

	public void setVariable(int index, Number value) {
		scope.setVariable(index, value);
	}

	public void createVariable(Number value) {
		scope.createVariable(value);
	}

	public Number[] getInitialRegion() {
		return region;
	}

	public Number getInitialPoint() {
		return point;
	}

	public void setInitialRegion(Number a, Number b) {
		this.region[0].set(a);
		this.region[1].set(b);
	}
}
