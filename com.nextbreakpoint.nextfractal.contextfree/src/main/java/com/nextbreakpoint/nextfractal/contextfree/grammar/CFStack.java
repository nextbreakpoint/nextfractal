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

import com.nextbreakpoint.nextfractal.contextfree.grammar.ast.ASTParameter;

import java.util.List;

public class CFStack {
	private CFStackItem[] items;

	public CFStack(CFStackItem[] items) {
		this.items = items;
	}

	public CFStack(CFStackItem item) {
		this.items = new CFStackItem[] { item };
	}

	public CFStack(CFStack parameters) {
		this(parameters.items);
	}

	public static CFStack createStack(CFStack stack) {
		//TODO controllare createStack
		CFStackItem[] srcItems = stack.items;
		if (srcItems[0] == null) {
			return null;
		}
		CFStackRule stackRule = (CFStackRule) srcItems[0];
		CFStackParams typeInfo = stackRule.getParamCount() > 0 ? (CFStackParams) srcItems[1] : null;
		CFStackItem[] newItems = new CFStackItem[2 + stackRule.getParamCount()];
		newItems[0] = stackRule;
		if (stackRule.getParamCount() > 0) {
			newItems[1] = typeInfo;
			stack.copyItems(newItems, 2);
		}
		return new CFStack(newItems);
	}

	public static CFStack createStack(int nameIndex, int paramCount, List<ASTParameter> params) {
		//TODO controllare createStack
		CFStackItem[] newItems = new CFStackItem[paramCount > 0 ? paramCount + 2 : 1];
		newItems[0] = new CFStackRule(nameIndex, paramCount, params);
		if (paramCount > 0) {
			newItems[1] = new CFStackParams(params);
		}
		return new CFStack(newItems);
	}

	private void copyItems(CFStackItem[] destItems, int headerSize) {
		//TODO controllare copyItems
		int destIndex = 0;
		for (int srcIndex = headerSize; srcIndex < items.length;) {
			switch (items[srcIndex].getType()) {
				case NumericType:
				case FlagType:
				case ModType:
					System.arraycopy(items, srcIndex, destItems, destIndex, items[srcIndex].getTupleSize());
					break;
				case RuleType:
					destItems[destIndex] = items[srcIndex];
					break;
				default:
					break;
			}
			destIndex += items[srcIndex].getTupleSize();
		}
	}

	public int size() {
		return items.length;
	}

	public CFStackItem getItem(int index) {
		return items[index];
	}

	public void setItem(int index, CFStackItem item) {
		items[index] = item;
	}

	public void copyItems(CFStack stack, int headerSize) {
		copyItems(stack.items, headerSize);
	}
}
