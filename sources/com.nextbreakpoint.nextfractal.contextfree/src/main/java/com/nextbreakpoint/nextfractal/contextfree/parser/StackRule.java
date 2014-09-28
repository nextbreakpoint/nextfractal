package com.nextbreakpoint.nextfractal.contextfree.parser;

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
}