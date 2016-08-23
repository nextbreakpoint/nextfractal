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

import com.nextbreakpoint.nextfractal.mandelbrot.compiler.support.CompiledCondition;

public class ASTRuleLogicOp extends ASTRuleExpression {
	private String op;
	private ASTRuleExpression exp1;
	private ASTRuleExpression exp2;
	
	public ASTRuleLogicOp(Token location, String op, ASTRuleExpression exp1, ASTRuleExpression exp2) {
		super(location);
		this.op = op;
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	public String getOp() {
		return op;
	}
	
	public ASTRuleExpression getExp1() {
		return exp1;
	}

	public ASTRuleExpression getExp2() {
		return exp2;
	}

	@Override
	public String toString() {
		StringBuilder driver = new StringBuilder();
		driver.append(exp1);
		driver.append(op);
		if (exp2 != null) {
			driver.append(exp2);
		}
		return driver.toString();
	}

	@Override
	public CompiledCondition compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}
}