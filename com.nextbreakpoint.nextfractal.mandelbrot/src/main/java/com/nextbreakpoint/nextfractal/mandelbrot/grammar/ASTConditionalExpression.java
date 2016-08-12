/*
 * NextFractal 1.2.1
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

import org.antlr.v4.runtime.Token;

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledExpression;

public class ASTConditionalExpression extends ASTExpression {
	private ASTConditionExpression conditionExp;
	private ASTExpression thenExp;
	private ASTExpression elseExp;

	public ASTConditionalExpression(Token location, ASTConditionExpression conditionExp, ASTExpression thenExp, ASTExpression elseExp) {
		super(location);
		this.conditionExp = conditionExp;
		this.thenExp = thenExp;
		this.elseExp = elseExp;
	}

	@Override
	public String toString() {
		StringBuilder driver = new StringBuilder();
		driver.append(conditionExp);
		driver.append(" ? ");
		driver.append(thenExp);
		driver.append(" : ");
		driver.append(elseExp);
		return driver.toString();
	}

	@Override
	public CompiledExpression compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}

	public ASTConditionExpression getConditionExp() {
		return conditionExp;
	}

	public ASTExpression getThenExp() {
		return thenExp;
	}

	public ASTExpression getElseExp() {
		return elseExp;
	}
}
