package com.nextbreakpoint.nextfractal.contextfree.core;

public class CFRuleSpecifier implements Comparable<CFRuleSpecifier> {
	protected int initialShapeType;
	protected double weight;

	public CFRuleSpecifier(int initialShapeType, double weight) {
		this.initialShapeType = initialShapeType;
		this.weight = weight;
	}

	public int getInitialShapeType() {
		return initialShapeType;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public int compareTo(CFRuleSpecifier s) {
		if (initialShapeType == s.initialShapeType) {
			return (weight - s.weight) < 0 ? -1 : +1;
		} else {
			return initialShapeType - s.initialShapeType;
		}
	}

	@Override
	public String toString() {
		return "CFRuleSpecifier [initialShapeType=" + initialShapeType + ", weight=" + weight + "]";
	}
}
