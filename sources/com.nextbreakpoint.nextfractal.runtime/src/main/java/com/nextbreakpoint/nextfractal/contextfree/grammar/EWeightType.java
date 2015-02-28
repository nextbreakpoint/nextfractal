package com.nextbreakpoint.nextfractal.contextfree.grammar;

enum EWeightType { 
	NoWeight(1), PercentWeight(2), ExplicitWeight(4);
	
	private int type;

	private EWeightType(int type) { 
		this.type = type;
	}

	public int getType() {
		return type;
	}
}