package com.nextbreakpoint.nextfractal.contextfree.parser;

enum ECFGParam {
	AllowOverlap(0), Alpha(1), Background(2), BorderDynamic(3), BorderFixed(4), Color(5), 
	ColorDepth(6), Frame(7), FrameTime(8), Impure(9), MaxNatural(10), MaxShapes(11), 
	MinimumSize(12), Size(13), StartShape(14), Symmetry(15), Tile(16), Time(17);
	
	private int ordinal;

	private ECFGParam(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getName() {
		return "CF::" + name().toUpperCase();
	}

	public static ECFGParam paramByOrdinal(int ordinal) {
		switch (ordinal) {
			case 0:
				return AllowOverlap; 
			case 1:
				return Alpha; 
			case 2:
				return Background; 
			case 3:
				return BorderDynamic; 
			case 4:
				return BorderFixed; 
			case 5:
				return Color; 
			case 6:
				return ColorDepth; 
			case 7:
				return Frame; 
			case 8:
				return FrameTime; 
			case 9:
				return Impure; 
			case 10:
				return MaxNatural; 
			case 11:
				return MaxShapes; 
			case 12:
				return MinimumSize; 
			case 13:
				return StartShape; 
			case 14:
				return Symmetry; 
			case 15:
				return Tile; 
			case 16:
				return Time; 
		}
		return null;
	}

	public static String nameByOrdinal(int ordinal) {
		ECFGParam param = paramByOrdinal(ordinal);
		return "CF::" + param.name().toUpperCase();
	}
}