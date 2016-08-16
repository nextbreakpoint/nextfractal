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

import java.util.List;

class StackRule {
    private int ruleName;
    private long refCount;
    private long paramCount;
    
	public StackRule(int ruleName, long paramCount) {
		this.ruleName = ruleName;
		this.paramCount = paramCount;
	}

	public StackRule(StackRule parent) {
		// TODO Auto-generated constructor stub
	}

	public StackRule(int shapeType, int argSize, List<ASTParameter> typeSignature) {
		// TODO Auto-generated constructor stub
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

	public void evalArgs(RTI rti, ASTExpression arguments, StackRule parent) {
		// TODO Auto-generated method stub
		
	}

	public void retain(RTI rti) {
		// TODO Auto-generated method stub
		
	}

	public void copyParams(int oldSize) {

	}
}
