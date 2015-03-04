package com.nextbreakpoint.nextfractal.contextfree.grammar;

enum ERepElemType { 
	rule(8), replacement(4), mixed(3), command(2), op(1), empty(0); 
	
	private int type;
	
	private ERepElemType(int type) { 
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ERepElemType typeByOrdinal(int ordinal) {
		for (ERepElemType type : ERepElemType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return empty;
	}
}