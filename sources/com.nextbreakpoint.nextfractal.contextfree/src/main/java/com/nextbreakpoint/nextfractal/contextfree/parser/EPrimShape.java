package com.nextbreakpoint.nextfractal.contextfree.parser;

public enum EPrimShape {
	circleType(0), squareType(1), triangleType(2), fillTypr(3);
	
	private int type;

	private EPrimShape(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
