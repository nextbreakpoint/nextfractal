package com.nextbreakpoint.nextfractal.contextfree.renderer.support;

public class CFShape implements Cloneable {
	private CFModification modification;
	private int initialShapeType;
	private double area;

	public CFShape(int initialShapeType, CFModification modification) {
		this.modification = modification;
		this.initialShapeType = initialShapeType;
		area = Math.abs(modification.getTransform().getDeterminant());
	}

	public final int getInitialShapeType() {
		return initialShapeType;
	}

	public CFModification getModification() {
		return modification;
	}
	
	public double area() {
		return area;
	}

	@Override
	public CFShape clone() {
		return new CFShape(initialShapeType, modification.clone());
	}

	public void concatenate(CFReplacement replacement) {
		initialShapeType = replacement.getShapeType();
		modification.concatenate(replacement.getModification());
		area = Math.abs(modification.getTransform().getDeterminant());
	}

	@Override
	public String toString() {
		return "CFShape [initialShapeType=" + initialShapeType + ", modification=" + modification + ", area=" + area + "]";
	}
}
