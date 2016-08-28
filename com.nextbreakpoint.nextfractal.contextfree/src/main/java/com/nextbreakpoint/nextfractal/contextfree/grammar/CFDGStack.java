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

public class CFDGStack {
	private Object[] stack;

	public CFDGStack(Object[] stack) {
		this.stack = stack;
	}

	public Object[] getStack() {
		return stack;
	}

	public void setStack(Object[] stack) {
		this.stack = stack;
	}

	public static CFDGStack createStackRule(CFDGStack from) {
		//TODO controllare createStackRule
		Object[] fromStack = from.getStack();
		if (fromStack[0] == null) {
			return null;
		}
		List<ASTParameter> typeInfo = ((RuleHeader) fromStack[0]).getParamCount() > 0 ? (List<ASTParameter>) fromStack[1] : null;
		RuleHeader ruleHeader = new RuleHeader(((RuleHeader) fromStack[0]).getRuleName(), ((RuleHeader) fromStack[0]).getParamCount());
		Object[] newStack = new Object[2 + ruleHeader.getParamCount()];
		newStack[0] = ruleHeader;
		if (ruleHeader.getParamCount() > 0) {
			newStack[1] = typeInfo;
			((CFDGStack) fromStack[0]).copyParams(newStack, 2);
		}
		return new CFDGStack(newStack);
	}

	public static CFDGStack createStackRule(int nameIndex, int paramCount, List<ASTParameter> typeInfo) {
		//TODO controllare createStackRule
		CFDGRenderer.paramCount++;
		Object[] newStack = new Object[paramCount > 0 ? paramCount + 2 : 1];
		((RuleHeader)newStack[0]).setRuleName(nameIndex);
		((RuleHeader)newStack[0]).setParamCount(paramCount);
		if (paramCount > 0) {
			newStack[1] = typeInfo;
		}
		return new CFDGStack(newStack);
	}

	public void copyParams(Object[] destStack, int headerSize) {
		//TODO controllare copyParams
		int current = 0;
		for (int i = 0; i < stack.length;) {
			switch (((ASTExpression)stack[i]).getType()) {
				case NumericType:
				case FlagType:
				case ModType:
					System.arraycopy(stack, i, destStack, current, ((ASTExpression)stack[i]).getTupleSize());
					break;
				case RuleType:
					destStack[current] = stack[i];
					break;
				default:
					break;
			}
			current += ((ASTExpression)stack[i]).getTupleSize();
		}
	}
}
