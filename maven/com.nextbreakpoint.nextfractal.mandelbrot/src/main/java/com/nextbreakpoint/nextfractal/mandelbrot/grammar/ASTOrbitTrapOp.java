/*
 * NextFractal 1.0.1
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

public class ASTOrbitTrapOp extends ASTObject {
	private String op;
	private ASTNumber c1;
	private ASTNumber c2;

	public ASTOrbitTrapOp(Token location, String op, ASTNumber c) {
		super(location);
		this.op = op;
		this.c1 = c;
		this.c2 = null;
	}

	public ASTOrbitTrapOp(Token location, String op, ASTNumber c1, ASTNumber c2) {
		super(location);
		this.op = op;
		this.c1 = c1;
		this.c2 = c2;
	}

	public String getOp() {
		return op;
	}
	
	public ASTNumber getC1() {
		return c1;
	}

	public ASTNumber getC2() {
		return c2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(op);
		builder.append("(");
		builder.append(c1);
		if (c2 != null) {
			builder.append(",");
			builder.append(c2);
		}
		builder.append(")");
		return builder.toString();
	}
}
