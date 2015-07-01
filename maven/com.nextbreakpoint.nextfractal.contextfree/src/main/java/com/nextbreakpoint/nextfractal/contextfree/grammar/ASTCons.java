/*
 * NextFractal 1.1.1
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
package com.nextbreakpoint.nextfractal.contextfree.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;

class ASTCons extends ASTExpression {
	private List<ASTExpression> children = new ArrayList<ASTExpression>();

	public ASTCons(List<ASTExpression> kids, Token location) {
		super(true, true, EExpType.NoType, location);
		locality = ELocality.PureLocal;
		for (ASTExpression kid : kids) {
			append(kid);
		}
	}

	public ASTCons(Token location, ASTExpression... args) {
		super(true, true, EExpType.NoType, location);//TODO da controllare
		locality = ELocality.PureLocal;
		for (ASTExpression arg : args) {
			children.add(arg);
		}
	}

	@Override
	public void entropy(StringBuilder e) {
		for (ASTExpression child : children) {
			child.entropy(e);
		}
        e.append("\u00C5\u0060\u00A5\u00C5\u00C8\u0074");
	}

	public List<ASTExpression> getChildren() {
		return children;
	}
	
	@Override
	public ASTExpression simplify() {
		if (children.size() == 1) {
			return children.get(0).simplify();
		}
		for (ASTExpression child : children) {
			child.simplify();
		}
        return this;
	}

	@Override
	public int evaluate(double[] result, int length, RTI rti) { 
		if ((type.ordinal() & (EExpType.NumericType.ordinal() | EExpType.FlagType.ordinal())) == 0 || (type.ordinal() & (EExpType.ModType.ordinal() | EExpType.RuleType.ordinal())) != 0) {
			error("Non-numeric expression in a numeric context");
			return -1;
		}
		int count = 0;
		for (ASTExpression child : children) {
			int num = child.evaluate(result, length, rti);
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
	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
		for (ASTExpression child : children) {
			child.evaluate(result, shapeDest, rti);
		}
	}

	@Override
	public ASTExpression compile(ECompilePhase ph) {
		switch (ph) {
			case TypeCheck:
				{
					isConstant = isNatural = false;
					locality = ELocality.PureLocal;
					type = EExpType.NoType;
					for (ASTExpression child : children) {
						child.compile(ph);
						isConstant = isConstant && child.isConstant();
						isNatural = isNatural && child.isNatural();
						locality = combineLocality(locality, child.getLocality());
						type = EExpType.expTypeByOrdinal(type.ordinal() | child.getType().ordinal());
					}
				}
				break;
				
			case Simplify:
				break;

			default:
				break;
		}
		return null;
	}
	
	@Override
	public ASTExpression getChild(int i) {
		if (i >= children.size()) {
			error("Expression list bounds exceeded");
		}
		return children.get(i);
	}

	@Override
	public ASTExpression append(ASTExpression e) {
        if (e == null) return this;
        isConstant = isConstant && e.isConstant();
        isNatural = isNatural && e.isNatural();
        locality = combineLocality(locality, e.getLocality());
        type = EExpType.expTypeByOrdinal(type.ordinal() | e.getType().ordinal());
        
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
