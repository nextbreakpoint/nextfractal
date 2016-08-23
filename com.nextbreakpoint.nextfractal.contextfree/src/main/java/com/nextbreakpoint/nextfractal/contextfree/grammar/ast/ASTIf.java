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
package com.nextbreakpoint.nextfractal.contextfree.grammar.ast;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGDriver;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Shape;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.RepElemType;
import org.antlr.v4.runtime.Token;

public class ASTIf extends ASTReplacement {
	private ASTExpression condition;
	private ASTRepContainer thenBody;
	private ASTRepContainer elseBody;
	
	public ASTIf(CFDGDriver driver, ASTExpression exp, Token location) {
		super(driver, null, RepElemType.empty, location);
		thenBody = new ASTRepContainer(driver);
		elseBody = new ASTRepContainer(driver);
		this.condition = exp;
	}
	
	public ASTRepContainer getThenBody() {
		return thenBody;
	}
	
	public ASTRepContainer getElseBody() {
		return elseBody;
	}

	@Override
	public void compile(CompilePhase ph) {
		super.compile(ph);
		if (condition != null) {
			condition.compile(ph);
		}
		thenBody.compile(ph, null, null);
		elseBody.compile(ph, null, null);
		
		switch (ph) {
			case TypeCheck:
				if (condition.getType() != ExpType.NumericType || condition.evaluate(null, 0) != 1) {
					error("If condition must be a numeric scalar");
				}
				break;
	
			case Simplify:
				if (condition != null) {
					condition.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		double[] cond = new double[1];
		if (condition.evaluate(cond, 1) != 1) {
			error("Error evaluating if condition");
			return;
		}
		if (cond[0] != 0) {
			thenBody.traverse(parent, tr, rti, false);
		} else {
			elseBody.traverse(parent, tr, rti, false);
		}
	}
}