package com.nextbreakpoint.nextfractal.contextfree.parser;

enum EParam {
	Color(1), Alpha(2), Time(4), FrameTime(8);
	
	private int type;

	private EParam(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}