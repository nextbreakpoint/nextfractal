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

import com.nextbreakpoint.nextfractal.contextfree.grammar.Modification;
import com.nextbreakpoint.nextfractal.contextfree.grammar.RTI;
import com.nextbreakpoint.nextfractal.contextfree.grammar.StackRule;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.CompilePhase;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.ExpType;
import com.nextbreakpoint.nextfractal.contextfree.grammar.enums.Locality;
import org.antlr.v4.runtime.Token;

public class ASTExpression {
	protected boolean isConstant;
	protected boolean isNatural;
	protected Locality locality;
	protected ExpType type;
	protected Token location;

	public ASTExpression(Token location) {
		this(false, false, Locality.UnknownLocal, ExpType.NoType, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, Token location) {
		this(isConstant, isNatural, Locality.UnknownLocal, ExpType.NoType, location);
	}
	
	public ASTExpression(boolean isConstant, boolean isNatural, ExpType type, Token location) {
		this(isConstant, isNatural, Locality.UnknownLocal, type, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, Locality locality, Token location) {
		this(isConstant, isNatural, locality, ExpType.NoType, location);
	}

	public ASTExpression(boolean isConstant, boolean isNatural, Locality locality, ExpType type, Token location) {
		this.isConstant = isConstant;
		this.isNatural = isNatural;
		this.locality = locality;
		this.type = type;
	}

	public boolean isConstant() {
		return isConstant;
	}

	public boolean isNatural() {
		return isNatural;
	}

	public void setIsNatural(boolean isNatural) {
		this.isNatural = isNatural;
	}
	
	public Locality getLocality() {
		return locality;
	}
	
	public ExpType getType() {
		return type;
	}

	public void setType(ExpType type) {
		this.type = type;
	}
	
	public int evaluate(double[] result, int length) {
		return evaluate(result, length, null);
	}

	public int evaluate(double[] result, int length, RTI rti) {
		return 0;
	}

	public void evaluate(Modification result, boolean shapeDest) {
		evaluate(result, shapeDest, null);
	}

	public void evaluate(Modification result, boolean shapeDest, RTI rti) {
		throw new RuntimeException("Cannot convert this expression into an adjustment"); 
	}

	public StackRule evalArgs(RTI rti, StackRule parent) {
		throw new RuntimeException("Cannot convert this expression into a shape"); 
	}
	
	public void entropy(StringBuilder e) {
	}
	
	public ASTExpression simplify() {
		return this;
	}
	
	public ASTExpression getChild(int i) {
		if (i > 0) {
			error("Expression list bounds exceeded");
		}
		return this;
	}

	public int size() {
		return 1;
	}

	public ASTExpression append(ASTExpression e) {
		return null;
	}

	public ASTExpression compile(CompilePhase ph) {
		switch (ph) {
			case TypeCheck: 
				break;
	
			case Simplify: 
				break;

			default:
				break;
		}
		return null;
	}

	// Always returns nullptr except during type check in the following cases:
	// * An ASTvariable bound to a constant returns a copy of the constant
	// * An ASTvariable bound to a rule spec returns an ASTruleSpec that
	//   acts as a stack variable
	// * A shape spec that was parsed as an ASTuserFunc because of grammar
	//   ambiguity will return the correct ASTruleSpec
	//
	// It is safe to ignore the return value if you can guarantee that none
	// of these conditions is possible. Otherwise you must replace the object
	// with the returned object. Using the original object after type check
	// will fail.
	public static ASTExpression append(ASTExpression le, ASTExpression re) {
		return null;
	}
	
	protected final void error(String message) {
		System.out.println(message);
	}

	protected Locality combineLocality(Locality locality1, Locality locality2) {
		return Locality.fromType(locality1.ordinal() | locality2.ordinal());
	}
	
	protected List<ASTExpression> extract(ASTExpression exp) {
		if (exp instanceof ASTCons) {
			return ((ASTCons)exp).getChildren();
		} else {
			List<ASTExpression> ret = new ArrayList<ASTExpression>();
			ret.add(exp);
			return ret;
		}
	}

	public Token getLocation() {
		return location;
	}

	public void setLocation(Token location) {
		this.location = location;
	}
}
