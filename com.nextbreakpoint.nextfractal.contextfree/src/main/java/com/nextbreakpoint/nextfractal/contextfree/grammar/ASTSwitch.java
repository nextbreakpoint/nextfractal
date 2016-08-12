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

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;

class ASTSwitch extends ASTReplacement {
	private Map<Integer, ASTRepContainer> caseStatements = new HashMap<Integer, ASTRepContainer>();
	private ASTRepContainer elseBody;
	private ASTExpression switchExp;
	
	public ASTSwitch(CFDGDriver driver, ASTExpression caseVal, Token location) {
		super(driver, null, ERepElemType.empty, location);
		elseBody = new ASTRepContainer(driver);
		this.switchExp = caseVal;
	}

	public ASTRepContainer getElseBody() {
		return elseBody;
	}

	public Map<Integer, ASTRepContainer> getCaseStatements() {
		return caseStatements;
	}

	public ASTExpression getSwitchExp() {
		return switchExp;
	}
	
	@Override
	public void compile(ECompilePhase ph) {
		super.compile(ph);
		if (switchExp != null) {
			switchExp.compile(ph);
		}
		for (ASTRepContainer caseVal : caseStatements.values()) {
			caseVal.compile(ph, null, null);
		}
		elseBody.compile(ph, null, null);
		
		switch (ph) {
			case TypeCheck:
				if (switchExp.getType() != EExpType.NumericType || switchExp.evaluate(null, 0) != 1) {
					error("Switch selector must be a numeric scalar");
				}
				break;
	
			case Simplify:
				if (switchExp != null) {
					switchExp.simplify();
				}
				break;
	
			default:
				break;
		}
	}

	@Override
	public void traverse(Shape parent, boolean tr, RTI rti) {
		double[] caveValue = new double[1];
		if (switchExp.evaluate(caveValue, 1) != 1) {
			error("Error evaluating switch selector");
			return;
		}
		
		ASTRepContainer caseVal = caseStatements.get((int)Math.floor(caveValue[0]));
		if (caseVal != null) {
			caseVal.traverse(parent, tr, rti, false);
		} else {
			elseBody.traverse(parent, tr, rti, false);
		}
	}
	
	public void unify() {
		if (elseBody.getPathOp() != getPathOp()) {
			setPathOp(EPathOp.UNKNOWN);
		}
		for (ASTRepContainer caseVal : caseStatements.values()) {
			if (caseVal.getPathOp() != getPathOp()) {
				setPathOp(EPathOp.UNKNOWN);
			}
		}
	}
}
