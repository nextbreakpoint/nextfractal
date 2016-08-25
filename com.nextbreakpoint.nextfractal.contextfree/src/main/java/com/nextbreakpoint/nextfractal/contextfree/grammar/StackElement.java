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

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTExpression;
import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;

import java.util.List;

public class StackElement {
	private double number;
	private StackRule rule;
	private StackRule ruleHeader;
	private List<ASTParameter> typeInfo;

	public StackElement(double number) {
		// TODO completare
	}

	public StackElement(StackRule rule) {
		// TODO completare
	}

	public double getNumber() {
		return number;
	}

	public StackRule getRule() {
		return rule;
	}

	public StackRule getRuleHeader() {
		return ruleHeader;
	}

	public List<ASTParameter> getTypeInfo() {
		return typeInfo;
	}

	public void evalArgs(CFDGRenderer renderer, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
		// TODO completare
	}

	public double[] getArray() {
		// TODO completare
		return null;
	}

	public Modification modification() {
		// TODO completare
		return null;
	}

	public void addNumber(double value) {
		this.number += value;
	}

	public void setNumber(double value) {
		// TODO completare
	}

	public void setModification(Modification modification) {
		// TODO completare
	}

	public void setRule(StackRule rule) {
		// TODO completare
	}
}
