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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.AST;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;

import java.util.List;

public class StackElement {
	private double number;
	private double[] array;
	private StackRule rule;
	private Modification modification;
	private List<ASTParameter> typeInfo;

	//TODO rivedere

	public StackElement(double number) {
		this.number = number;
	}

	public StackElement(StackRule rule) {
		this.rule = rule;
	}

	public StackElement(double[] array) {
		this.array = array;
	}

	public StackElement(Modification modification) {
		this.modification = modification;
	}

	public StackElement(List<ASTParameter> typeInfo) {
		this.typeInfo = typeInfo;
	}

	public double getNumber() {
		return number;
	}

	public void addNumber(double value) {
		this.number += value;
	}

	public void setNumber(double value) {
		this.number = value;
	}

	public Modification modification() {
		return modification;
	}

	public void setModification(Modification modification) {
		this.modification = modification;
	}

	public StackRule getRule() {
		return rule;
	}

	public void setRule(StackRule rule) {
		this.rule = rule;
	}

	public double[] getArray() {
		return array;
	}

	public void setArray(double[] array) {
		this.array = array;
	}

	public List<ASTParameter> getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(List<ASTParameter> typeInfo) {
		this.typeInfo = typeInfo;
	}

	public void evalArgs(CFDGRenderer renderer, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
		AST.evalArgs(renderer, null, parameters.iterator(), arguments, sequential);
	}
}
