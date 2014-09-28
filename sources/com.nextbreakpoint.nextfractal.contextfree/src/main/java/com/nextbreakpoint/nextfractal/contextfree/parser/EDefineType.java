package com.nextbreakpoint.nextfractal.contextfree.parser;

enum EDefineType {
	StackDefine(0), ConstDefine(1), ConfigDefine(2), FunctionDefine(4), LetDefine(8);
	
	private int ordinal;

	private EDefineType(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}
}