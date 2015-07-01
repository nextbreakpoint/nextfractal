/*
 * NextFractal 1.1.1
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
package com.nextbreakpoint.nextfractal.mandelbrot.grammar;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledExpression;

public class ASTNumber extends ASTExpression {
	private final double r;
	private final double i;
	private final boolean real;

	public ASTNumber(Token location, double r, double i) {
		super(location);
		this.r = r;
		this.i = i;
		real = false;
	}

	public ASTNumber(Token location, double r) {
		super(location);
		this.r = r;
		this.i = 0;
		real = true;
	}

	@Override
	public String toString() {
		return String.valueOf(r + "," + i);
	}

	@Override
	public CompiledExpression compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}

	@Override
	public boolean isReal() {
		return real;
	}

	public double r() {
		return r;
	}

	public double i() {
		return i;
	}
}
