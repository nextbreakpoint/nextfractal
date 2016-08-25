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

import java.util.Iterator;
import java.util.List;

public class StackRule extends StackElement {
    private int ruleName;
    private long refCount;
    private long paramCount;
    
	public StackRule(int ruleName, long paramCount) {
		this.ruleName = ruleName;
		this.paramCount = paramCount;
	}

	public StackRule(StackRule parent) {
		//TODO completare
//		if (from == nullptr)
//			return nullptr;
//    const StackType* src = reinterpret_cast<const StackType*>(from);
//    const AST::ASTparameters* ti = from->mParamCount ? src[1].typeInfo : nullptr;
//		StackRule* ret = alloc(from->mRuleName, from->mParamCount, ti);
//#ifdef EXTREME_PARAM_DEBUG
//		ParamMap[ret] = ++ParamUID;
//		if (ParamUID == ParamOfInterest)
//			ParamMap[ret] = ParamOfInterest;
//#endif
//		if (ret->mParamCount) {
//			StackType* data = reinterpret_cast<StackType*>(ret);
//			data[1].typeInfo = ti;
//			from->copyParams(data + HeaderSize);
//		}
//		return ret;
	}

	public StackRule(int shapeType, int argSize, List<ASTParameter> typeSignature) {
		//TODO completare
//		++Renderer::ParamCount;
//		StackType* newrule = reinterpret_cast<StackType*>(new double[size ? size + HeaderSize : 1]);
//		assert((reinterpret_cast<intptr_t>(newrule) & 3) == 0);   // confirm 32-bit alignment
//		newrule[0].ruleHeader.mRuleName = static_cast<int16_t>(name);
//		newrule[0].ruleHeader.mRefCount = 0;
//		newrule[0].ruleHeader.mParamCount = static_cast<uint16_t>(size);
//		if (size)
//			newrule[1].typeInfo = ti;
//#ifdef EXTREME_PARAM_DEBUG
//		ParamMap[&(newrule->ruleHeader)] = ++ParamUID;
//		if (ParamUID == ParamOfInterest)
//			ParamMap[&(newrule->ruleHeader)] = ParamOfInterest;
//#endif
//		return &(newrule->ruleHeader);
	}

	public int getRuleName() {
		return ruleName;
	}

	public void setRuleName(int ruleName) {
		this.ruleName = ruleName;
	}
	
	public long getParamCount() {
		return paramCount;
	}

	public void setParamCount(long paramCount) {
		this.paramCount = paramCount;
	}

	public long getRefCount() {
		return refCount;
	}

	public void setRefCount(long refCount) {
		this.refCount = refCount;
	}

	public void evalArgs(CFDGRenderer renderer, ASTExpression arguments, StackRule parent) {
		AST.evalArgs(renderer, parent, iterator(), arguments, false);
	}

	private Iterator<ASTParameter> iterator() {
		//TODO completare
		return null;
	}

	public void copyParams(StackElement[] stack, int size) {
		//TODO completare
		int current = 0;
		// Copy the POD and param_ptrs over
//		for (const_iterator it = begin(), e = end(); it != e; ++it) {
//			switch (it.type().mType) {
//				case AST::NumericType:
//				case AST::FlagType:
//				case AST::ModType:
//					// Copy over POD types
//					memcpy(static_cast<void*>(dest + current),
//						static_cast<const void*>(&*it),
//					it.type().mTuplesize * sizeof(StackType));
//					break;
//				case AST::RuleType:
//					// Placement copy ctor param_ptr
//					new (&(dest[current].rule)) param_ptr(it->rule);
//					break;
//				default:
//					break;
//			}
//			current += it.type().mTuplesize;
//		}
	}
}
