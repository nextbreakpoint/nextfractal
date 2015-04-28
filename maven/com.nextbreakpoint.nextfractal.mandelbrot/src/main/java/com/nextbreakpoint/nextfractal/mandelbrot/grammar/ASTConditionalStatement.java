/*
 * NextFractal 1.0.2
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

public class ASTConditionalStatement extends ASTStatement {
	private ASTConditionExpression conditionExp;
	private ASTStatement statement;

	public ASTConditionalStatement(Token location, ASTConditionExpression conditionExp, ASTStatement statement) {
		super(location);
		this.conditionExp = conditionExp;
		this.statement = statement;
	}

	public ASTConditionExpression getConditionExp() {
		return conditionExp;
	}

	public ASTStatement getStatement() {
		return statement;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("if (");
		builder.append(conditionExp);
		builder.append(") { ");
		builder.append(statement);
		builder.append(" }");
		return builder.toString();
	}

	@Override
	public void compile(ASTExpressionCompiler compiler) {
		compiler.compile(this);
	}
}
