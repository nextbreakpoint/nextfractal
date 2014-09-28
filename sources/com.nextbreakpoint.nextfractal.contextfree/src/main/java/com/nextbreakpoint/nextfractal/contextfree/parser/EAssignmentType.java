package com.nextbreakpoint.nextfractal.contextfree.parser;

enum EAssignmentType { 
	ColorTarget(1), Color2Value(3), ColorMask(3), HueTarget(1), Hue2Value(3), HueMask(3), SaturationTarget(1 << 2), Saturation2Value(3 << 2), SaturationMask(3 << 2),
	BrightnessTarget(1 << 4), Brightness2Value(3 << 4), BrightnessMask(3 << 4), AlphaTarget(1 << 6), Alpha2Value(3 << 6), AlphaMask(3 << 6), 
	HSBA2Value(3 << 0 | 3 << 2 | 3 << 4 | 3 << 6), HSBATarget(1 << 0 | 1 << 2 | 1 << 4 | 1 << 6); 
	
	private int type;

	private EAssignmentType(int type) { 
		this.type = type;
	}

	public int getType() {
		return type;
	}
}