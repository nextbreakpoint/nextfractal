package com.nextbreakpoint.nextfractal.contextfree.parser;

enum EModClass {
    InvalidClass(-1), 
    NotAClass(0), 
    GeomClass(1), 
    ZClass(2), 
    TimeClass(4),
    HueClass(8), 
    SatClass(16), 
    BrightClass(32), 
    AlphaClass(64),
    HueTargetClass(128), 
    SatTargetClass(256), 
    BrightTargetClass(512), 
    AlphaTargetClass(1024),
    StrokeClass(2048), 
    ParamClass(4096), 
    PathOpClass(8192);
    
	private int type;
	
	private EModClass(int type) { 
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static EModClass classByOrdinal(int ordinal) {
		for (EModClass type : EModClass.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return NotAClass;
	}
}