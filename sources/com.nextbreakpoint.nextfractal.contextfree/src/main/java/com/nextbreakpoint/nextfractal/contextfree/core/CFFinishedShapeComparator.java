package com.nextbreakpoint.nextfractal.contextfree.core;

import java.util.Comparator;


public class CFFinishedShapeComparator implements Comparator<CFFinishedShape> {
	@Override
	public int compare(CFFinishedShape o1, CFFinishedShape o2) {
		return o1.compareTo(o2);
	}
}
