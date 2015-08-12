/*
 * NextFractal 1.1.5
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

import org.antlr.v4.runtime.Token;

class ASTVariable extends ASTExpression {
	private String text;
	private int stringIndex;
	private int stackIndex;
	private int count;
	private boolean isParameter;

	public ASTVariable(int stringIndex, String text, Token location) {
		super(location);
		this.stringIndex = stringIndex;
		this.isParameter = false;
		this.stackIndex = 0;
		this.text = text;
		this.count = 0;
	}

	@Override
	public void entropy(StringBuilder e) {
		e.append(text);
	}
	
	@Override
	public ASTExpression compile(ECompilePhase ph) {
		switch (ph) {
			case TypeCheck: 
				{
					boolean isGlobal = false;
					ASTParameter bound = Builder.currentBuilder().findExpression(stringIndex, isGlobal);
					if (bound == null) {
						error("internal error.");
						return null;
					}
					String name = Builder.currentBuilder().shapeToString(stringIndex);
					if (bound.getStackIndex() == -1) {
						ASTExpression ret = bound.constCopy(name);
						if (ret == null) {
							error("internal error.");
						}
						return ret;
					} else {
						if (bound.getType() == EExpType.RuleType) {
							ASTRuleSpecifier ret = new ASTRuleSpecifier(stringIndex, name, location);
							ret.compile(ph);
							return ret;
						}
						count = bound.getType() == EExpType.NumericType ? bound.getTupleSize() : 1;
						stackIndex = bound.getStackIndex() - (isGlobal ? 0: Builder.currentBuilder().getLocalStackDepth());
						type = bound.getType();
						isNatural = bound.isNatural();
						locality = bound.getLocality();
						isParameter = bound.isParameter();
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
	public int evaluate(double[] result, int length, RTI rti) {
		if (type != EExpType.NumericType) {
			error("Non-numeric variable in a numeric context");
			return -1;
        }
		if (result != null && length < count) {
			return -1;
		}
        if (result != null) {
            if (rti == null) throw new DeferUntilRuntimeException();
            StackType stackItem = rti.stackItem(stackIndex);
            for (int i = 0; i < count; ++i) {
				result[i] = stackItem.getNumber();
            }
        }
        return count;
	}

	@Override
	public void evaluate(Modification[] result, boolean shapeDest, RTI rti) {
		if (type != EExpType.ModType) {
			error("Non-adjustment variable referenced in an adjustment context");
        }
		if (rti == null) throw new DeferUntilRuntimeException();
        StackType stackItem = rti.stackItem(stackIndex);
        Modification mod = stackItem.modification();
        if (shapeDest) {
        	result[0].concat(mod);
        } else {
        	if (result[0].merge(mod)) {
    			rti.colorConflict();
        	}
        }
	}

	public String getText() {
		return text;
	}

	public int getStringIndex() {
		return stringIndex;
	}

	public int getStackIndex() {
		return stackIndex;
	}

	public int getCount() {
		return count;
	}

	public boolean isParameter() {
		return isParameter;
	}
}
