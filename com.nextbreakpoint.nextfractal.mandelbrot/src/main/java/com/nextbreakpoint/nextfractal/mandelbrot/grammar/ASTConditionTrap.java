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

public class ASTConditionTrap extends ASTConditionExpression {
	private String name;
	private ASTExpression exp;
	private boolean contains;

	public ASTConditionTrap(Token location, String name, ASTExpression exp, boolean contains) {
		super(location);
		this.name = name;
		this.exp = exp;
		this.contains = contains;
	}

	public String getName() {
		return name;
	}
	
	public ASTExpression getExp() {
		return exp;
	}
	
	public boolean isContains() {
		return contains;
	}
	
	@Override
	public String toString() {
		StringBuilder driver = new StringBuilder();
		driver.append(name);
		driver.append("[");
		driver.append(exp);
		driver.append(",");
		driver.append(contains);
		driver.append("]");
		return driver.toString();
	}

	public CompiledCondition compile(ASTExpressionCompiler compiler) {
		return compiler.compile(this);
	}
}