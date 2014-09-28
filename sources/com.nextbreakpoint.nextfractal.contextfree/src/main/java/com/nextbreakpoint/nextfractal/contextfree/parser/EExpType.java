package com.nextbreakpoint.nextfractal.contextfree.parser;

enum EExpType {
	NoType(0), NumericType(1), ModType(2), RuleType(4), FlagType(8), ReuseType(16);
	
	private int type;

	private EExpType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static EExpType expTypeByOrdinal(int ordinal) {
		for (EExpType type : EExpType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return NoType;
	}
}