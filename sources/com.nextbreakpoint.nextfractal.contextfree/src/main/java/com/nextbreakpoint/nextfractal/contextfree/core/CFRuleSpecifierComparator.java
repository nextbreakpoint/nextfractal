package com.nextbreakpoint.nextfractal.contextfree.core;

import java.util.Comparator;

public class CFRuleSpecifierComparator implements Comparator<CFRuleSpecifier> {
	@Override
	public int compare(CFRuleSpecifier o1, CFRuleSpecifier o2) {
		return o1.compareTo(o2);
	}
}
