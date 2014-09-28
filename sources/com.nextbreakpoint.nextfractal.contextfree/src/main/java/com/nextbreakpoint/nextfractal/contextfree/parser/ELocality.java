package com.nextbreakpoint.nextfractal.contextfree.parser;

enum ELocality {
	UnknownLocal(0), ImpureNonlocal(1), PureNonlocal(3), PureLocal(7);
	
	private int type;

	private ELocality(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ELocality localityByOrdinal(int ordinal) {
		for (ELocality type : ELocality.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return UnknownLocal;
	}
}