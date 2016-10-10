/*
 * NextFractal 1.3.0
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2016 Andrea Medeghini
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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledTrap;

public class ASTOrbitTrap extends ASTObject {
	private String name;
	private ASTNumber center;
	private List<ASTOrbitTrapOp> operators = new ArrayList<>(); 

	public ASTOrbitTrap(Token location, String name, ASTNumber center) {
		super(location);
		this.center = center;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ASTNumber getCenter() {
		return center;
	}

	public void addOperator(ASTOrbitTrapOp operator) {
		if ((operator.getOp().equals("MOVETO") || operator.getOp().equals("MOVETOREL")) && operators.size() > 0) {
			throw new ASTException("Only one initial MOVETO or MOVETOREL operator is allowed", operator.location);
		}
		operators.add(operator);
	}

	public List<ASTOrbitTrapOp> getOperators() {
		return operators;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("name = ");
		builder.append(name);
		builder.append(",center = ");
		builder.append(center);
		builder.append(",operators = [");
		for (int i = 0; i < operators.size(); i++) {
			ASTOrbitTrapOp statement = operators.get(i);
			builder.append("{");
			builder.append(statement);
			builder.append("}");
			if (i < operators.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	public CompiledTrap compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}
}
