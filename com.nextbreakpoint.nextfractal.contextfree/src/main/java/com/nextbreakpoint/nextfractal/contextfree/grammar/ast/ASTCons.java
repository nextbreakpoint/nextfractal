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

import java.util.ArrayList;
import java.util.List;

import com.nextbreakpoint.nextfractal.contextfree.grammar.CFDGRenderer;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Logger;
import com.nextbreakpoint.nextfractal.contextfree.grammar.Modification;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTCons extends ASTExpression {
	private List<ASTExpression> children = new ArrayList<ASTExpression>();

	public ASTCons(List<ASTExpression> kids, Token location) {
		super(true, true, ExpType.NoType, location);
		locality = Locality.PureLocal;
		for (ASTExpression kid : kids) {
			append(kid);
		}
	}

	public ASTCons(Token location, ASTExpression... args) {
		super(true, true, ExpType.NoType, location);
		//TODO controllare
		locality = Locality.PureLocal;
		for (ASTExpression arg : args) {
			children.add(arg);
		}
	}

	@Override
	public ASTExpression simplify() {
		if (children.size() == 1) {
			//TODO controllare
			return children.get(0).simplify();
		}
		for (int i = 0; i < children.size(); i++) {
			ASTExpression element = children.get(i).simplify();
			if (element != null) {
				children.set(i, element);
			}
		}
        return this;
	}

	@Override
	public int evaluate(double[] result, int length, CFDGRenderer renderer) {
		if ((type.getType() & (ExpType.NumericType.getType() | ExpType.FlagType.getType())) == 0 || (type.getType() & (ExpType.ModType.getType() | ExpType.RuleType.getType())) != 0) {
			Logger.error("Non-numeric expression in a numeric context", null);
			return -1;
		}
		int count = 0;
		for (ASTExpression child : children) {
			int num = child.evaluate(result, length, renderer);
			if (num < 0) {
				return -1;
			}
			count += num;
			if (result != null) {
				result[0] += num;
				length -= num;
			}
		}
		return count;
	}		

	@Override
	public void evaluate(Modification result, boolean shapeDest, CFDGRenderer renderer) {
		for (ASTExpression child : children) {
			child.evaluate(result, shapeDest, renderer);
		}
	}

	@Override
	public void entropy(StringBuilder e) {
		for (ASTExpression child : children) {
			child.entropy(e);
		}
		e.append("\u00C5\u0060\u00A5\u00C5\u00C8\u0074");
	}

	@Override
	public ASTExpression compile(CompilePhase ph) {
		switch (ph) {
			case TypeCheck: {
				isConstant = isNatural = false;
				locality = Locality.PureLocal;
				type = ExpType.NoType;
				for (int i = 0; i < children.size(); i++) {
					ASTExpression child = children.get(i).compile(ph);
					children.set(i, child);
					isConstant = isConstant && child.isConstant();
					isNatural = isNatural && child.isNatural();
					locality = AST.combineLocality(locality, child.getLocality());
					type = ExpType.fromType(type.getType() | child.getType().getType());
				}
				break;
			}

			case Simplify:
				break;

			default:
				break;
		}
		return null;
	}

	public List<ASTExpression> getChildren() {
		return children;
	}

	@Override
	public ASTExpression getChild(int i) {
		if (i >= children.size()) {
			Logger.error("Expression list bounds exceeded", null);
		}
		return children.get(i);
	}

	public void setChild(int i, ASTExpression exp) {
		if (i >= children.size()) {
			Logger.error("Expression list bounds exceeded", null);
		}
		children.set(i, exp);
	}

	@Override
	public ASTExpression append(ASTExpression e) {
        if (e == null) return this;
        isConstant = isConstant && e.isConstant();
        isNatural = isNatural && e.isNatural();
		locality = AST.combineLocality(locality, e.getLocality());
        type = ExpType.fromType(type.getType() | e.getType().getType());
        
        // Cannot insert an ASTcons into children, it will be flattened away.
        // You must wrap the ASTcons in an ASTparen in order to insert it whole.
        if (e instanceof ASTCons) {
        	ASTCons c = (ASTCons)e;
        	children.addAll(c.getChildren());
        } else {
        	children.add(e);
        }

        return this;
	}
}
