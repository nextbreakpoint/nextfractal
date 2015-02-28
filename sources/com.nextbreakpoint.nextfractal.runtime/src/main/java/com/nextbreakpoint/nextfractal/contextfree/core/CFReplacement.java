package com.nextbreakpoint.nextfractal.contextfree.core;

public class CFReplacement implements Cloneable {
	private int shapeType = -1;
	private int loopCount = 0;
	private CFModification modification;

	public CFReplacement(int shapeType, int loopCount, CFModification modification) {
		this.shapeType = shapeType;
		this.loopCount = loopCount;
		this.modification = modification;
	}

	public CFReplacement(int shapeType) {
		this.shapeType = shapeType;
		this.loopCount = 0;
		this.modification = new CFModification();
	}
	
	public final int getShapeType() {
		return shapeType;
	}

	public CFModification getModification() {
		return modification;
	}
	
	public int getLoopCount() {
		return loopCount;
	}

	@Override
	public CFReplacement clone() {
		return new CFReplacement(shapeType, loopCount, modification);
	}

	@Override
	public String toString() {
		return "CFReplacement [shapeType=" + shapeType + ", modification=" + modification + ", loopCount=" + loopCount + "]";
	}
}
