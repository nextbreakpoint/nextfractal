/*
 * NextFractal 2.0.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2017 Andrea Medeghini
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

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledExpression;
import org.antlr.v4.runtime.Token;

public class ASTFunction extends ASTExpression {
	private String name;
	private ASTExpression[] arguments;

	public ASTFunction(Token location, String name, ASTExpression[] arguments) {
		super(location);
		this.name = name;
		this.arguments = arguments;
	}

	public ASTFunction(Token location, String name, ASTExpression argument) {
		this(location, name, new ASTExpression[] { argument });
	}

	public String getName() {
		return name;
	}

	public ASTExpression[] getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("(");
		for (int i = 0; i < arguments.length; i++) {
			builder.append(arguments[i]);
			if (i < arguments.length - 1) {
				builder.append(",");
			}
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	public CompiledExpression compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}

	@Override
	public boolean isReal() {
		if (name.equals("time") || name.equals("mod") || name.equals("mod2") || name.equals("pha") || name.equals("log") || name.equals("exp") || name.equals("atan2") || name.equals("hypot") || name.equals("sqrt") || name.equals("re") || name.equals("im") || name.equals("ceil") || name.equals("floor") || name.equals("abs")) {
			return true;
		}
		for (ASTExpression argument : arguments) {
			if (!argument.isReal()) {
				return false;
			}
		}
		return true;
	}
}
