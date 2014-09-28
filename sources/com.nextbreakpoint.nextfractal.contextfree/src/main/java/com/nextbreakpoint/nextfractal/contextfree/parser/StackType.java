package com.nextbreakpoint.nextfractal.contextfree.parser;

import java.util.List;

class StackType {
	private double number;
	private StackRule rule;
	private StackRule ruleHeader;
	private List<ASTParameter> typeInfo;

	public StackType(double number) {
		// TODO Auto-generated constructor stub
	}

	public double getNumber() {
		return number;
	}

	public StackRule getRule() {
		return rule;
	}

	public StackRule getRuleHeader() {
		return ruleHeader;
	}

	public List<ASTParameter> getTypeInfo() {
		return typeInfo;
	}

	public void evalArgs(RTI rti, ASTExpression arguments, List<ASTParameter> parameters, boolean sequential) {
		// TODO Auto-generated method stub

	}

	public double[] getArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public Modification modification() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addNumber(double value) {
		this.number += value;
	}

	public void setNumber(double value) {
		// TODO Auto-generated method stub
		
	}

	public void setModification(Modification modification) {
		// TODO Auto-generated method stub
		
	}

	public void setRule(StackRule rule) {
		// TODO Auto-generated method stub
		
	}
}